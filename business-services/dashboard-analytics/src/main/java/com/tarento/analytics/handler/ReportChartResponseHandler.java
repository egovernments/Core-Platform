package com.tarento.analytics.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.tarento.analytics.constant.Constants;
import com.tarento.analytics.dto.AggregateDto;
import com.tarento.analytics.dto.AggregateRequestDto;
import com.tarento.analytics.dto.Data;
import com.tarento.analytics.dto.Plot;
import com.tarento.analytics.helper.ComputedFieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.tarento.analytics.constant.Constants.STRING_DATATYPE;

/**
 * This handles ES response for single index, multiple index to compute performance
 * Creates plots by performing ordered (ex: top n performance or last n performance)
 * AGGS_PATH : configurable to this defines the path/key to be used to search the tree
 * VALUE_TYPE : configurable to define the data type for the value formed, this could be amount, percentage, number
 * PLOT_LABEL :  configurable to define the label for the plot
 * TYPE_MAPPING : defines for a plot data type
 */
@Component
public class ReportChartResponseHandler implements IResponseHandler {
    public static final Logger logger = LoggerFactory.getLogger(ReportChartResponseHandler.class);

    @Autowired
    ComputedFieldHelper computedFieldHelper;
    @Override
    public AggregateDto translate(AggregateRequestDto requestDto, ObjectNode aggregations) throws IOException {
        Boolean isAggregation = false;
        if (aggregations.has(AGGREGATIONS)) {
            return getAggregationData(requestDto, aggregations);
        } else if (aggregations.has(Constants.JsonPaths.HITS)) {
            return getDataFromHits(requestDto, aggregations);
        }
        return null;
    }

    private AggregateDto getDataFromHits(AggregateRequestDto requestDto, ObjectNode aggregations) {
        JsonNode resHits = aggregations.get(Constants.JsonPaths.HITS);
        JsonNode chartNode = requestDto.getChartNode();
        ArrayNode outputJSONPaths = (ArrayNode) chartNode.get(Constants.JsonPaths.REPORT_NO_AGGS_JSON_PATH);
        ArrayNode hits = (ArrayNode) resHits.findValue(Constants.JsonPaths.HITS);
//        Long total = resHits.findValue("total").asLong();
        List<Data> dataList = new ArrayList<>();
        hits.forEach((hit) -> {
            JsonNode sourceData = hit.get(Constants.JsonPaths.HIT_SOURCE);
            List<Plot> plotList = new ArrayList<Plot>();
            long srNo = dataList.size() + 1;
            // Create Serial no plot
            Plot sno = new Plot(SERIAL_NUMBER, TABLE_TEXT);
            sno.setLabel(String.valueOf(srNo));
            plotList.add(sno);
            outputJSONPaths.forEach((jsonPathObj) -> {
                String path = jsonPathObj.get(Constants.JsonPaths.NO_AGGR_JSON_FIELD_PATH).asText();
                String dataType = jsonPathObj.get(Constants.JsonPaths.NO_AGGR_JSON_FIELD_DATA_TYPE).asText();
                String columnName = jsonPathObj.get(Constants.JsonPaths.NO_AGGR_JSON_FIELD_COLUMN_NAME).asText();
                String val = null;
                try {
                    val = JsonPath.read(sourceData.toString(), "$."+path).toString();
                } catch (PathNotFoundException e) {
                    val = null;
                }
                if (dataType.equals(STRING_DATATYPE)) {
                    Plot plot = new Plot(columnName, val==null? val: String.valueOf(val), STRING_DATATYPE);
                    plotList.add(plot);

                } else {
                    Plot plot = new Plot(columnName, val==null? null : Double.valueOf(val), dataType);
                    plotList.add(plot);
                }
            });
            Data data = new Data(String.valueOf(srNo), srNo, null);
            data.setPlots(plotList);
            dataList.add(data);
        });
        AggregateDto aggregateDto= getAggregatedDto(chartNode, dataList, requestDto.getVisualizationCode());
//        aggregateDto.setTotal(total);
        return aggregateDto;
    }

    private AggregateDto getAggregationData(AggregateRequestDto requestDto, ObjectNode aggregations) {
        JsonNode aggregationNode = aggregations.get(AGGREGATIONS);
        JsonNode chartNode = requestDto.getChartNode();
        ArrayNode pathDataTypeMap = (ArrayNode) chartNode.get(TYPE_MAPPING);
        Map<String, String> dataTypeMap = new HashMap<String, String>();
        pathDataTypeMap.forEach(type -> {
            String key = type.fields().next().getKey();
            JsonNode value = type.fields().next().getValue();
            dataTypeMap.put(key, value.asText());
        });
        ArrayNode aggrsPaths = (ArrayNode) chartNode.get(IResponseHandler.AGGS_PATH);
        List<Data> dataList = new ArrayList<>();
        if (aggrsPaths != null && !aggrsPaths.isEmpty()) {
            List<JsonNode> aggrNodes = aggregationNode.findValues(BUCKETS);
            Map<String, String> levelValues = new HashMap<String, String>();
            aggrNodes.stream().forEach(node -> {
                ArrayNode buckets = (ArrayNode) node;
                buckets.forEach(bucket -> {
                    getAggregationBuckets(bucket, aggrsPaths, 0, dataList, levelValues, dataTypeMap);
                });
            });
        }

        return getAggregatedDto(chartNode, dataList, requestDto.getVisualizationCode());
    }

    private void getAggregationBuckets(JsonNode aggregationNode, ArrayNode aggrsPaths, int idx, List<Data> dataList, Map<String, String> levelValues, Map<String, String> dataTypeMap) {
        //
        levelValues.put(aggrsPaths.get(idx).asText(), aggregationNode.get(KEY).asText());

        List<JsonNode> aggrNodes = aggregationNode.findValues(BUCKETS);
        if (CollectionUtils.isEmpty(aggrNodes)) {
            int srNo = dataList.size() + 1;
            List<Plot> plotList = createPlotList(aggregationNode, aggrsPaths, levelValues, dataTypeMap, srNo);
            Data data = new Data(String.valueOf(srNo), srNo, null);
            data.setPlots(plotList);
            dataList.add(data);
        } else {
            aggrNodes.stream().forEach(node -> {
                ArrayNode buckets = (ArrayNode) node;
                buckets.forEach(bucket -> {
                    getAggregationBuckets(bucket, aggrsPaths, idx + 1, dataList, levelValues, dataTypeMap);
                });
            });
        }
    }

    private List<Plot> createPlotList(JsonNode bucket, ArrayNode aggrsPaths, Map<String, String> levelValues, Map<String, String> dataTypeMap, int srNo) {
        List<Plot> plotList = new ArrayList<Plot>();
        // Create Serial no plot
        Plot sno = new Plot(SERIAL_NUMBER, TABLE_TEXT);
        sno.setLabel(String.valueOf(srNo));
        plotList.add(sno);

        // Go through each aggregation paths and generate plot for that
        for (JsonNode path : aggrsPaths) {
            String nodeDataType = dataTypeMap.get(path.asText());
            if (levelValues.containsKey(path.asText())) {
                if (nodeDataType.equals(STRING_DATATYPE)) {
                    Plot plot = new Plot(path.asText(), levelValues.get(path.asText()), STRING_DATATYPE);
                    plotList.add(plot);
                } else {
                    Plot plot = new Plot(path.asText(), Double.valueOf(levelValues.get(path.asText())), nodeDataType);
                    plotList.add(plot);
                }
            } else {
                JsonNode valueNode = bucket.findValue(path.asText());
                Double doc_value = 0.0;
                if(valueNode!=null)
                    doc_value = (null == valueNode.findValue(DOC_COUNT)) ? 0.0 : valueNode.findValue(DOC_COUNT).asDouble();
                Double value = (null == valueNode || null == valueNode.findValue(VALUE)) ? doc_value : valueNode.findValue(VALUE).asDouble();
                if (nodeDataType == STRING_DATATYPE) {
                    Plot plot = new Plot(path.asText(), String.valueOf(value), STRING_DATATYPE);
                    plotList.add(plot);
                } else {
                    Plot plot = new Plot(path.asText(), value, nodeDataType);
                    plotList.add(plot);
                }
            }
        }
        return plotList;
    }

}