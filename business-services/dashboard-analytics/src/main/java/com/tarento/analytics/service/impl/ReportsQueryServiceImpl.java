package com.tarento.analytics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tarento.analytics.constant.Constants;
import com.tarento.analytics.dao.ElasticSearchDao;
import com.tarento.analytics.dto.AggregateRequestDto;
import com.tarento.analytics.model.ElasticSearchDictator;
import org.elasticsearch.action.search.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class ReportsQueryServiceImpl {

    public static final Logger logger = LoggerFactory.getLogger(ReportsQueryServiceImpl.class);

    @Autowired
    private ElasticSearchDao elasticSearchDao;

    public ObjectNode getChartConfigurationReportQuery(AggregateRequestDto request, JsonNode query, JsonNode chartNode, String indexName, String interval) {
        Boolean hasAggQuery = query.has(Constants.JsonPaths.AGGREGATION_QUERY);
        String aggrQuery = null;

        if (hasAggQuery)
            aggrQuery = query.get(Constants.JsonPaths.AGGREGATION_QUERY).asText();

        if(interval!=null && !interval.isEmpty() && hasAggQuery && aggrQuery != null && !aggrQuery.isEmpty())
            aggrQuery = aggrQuery.replace(Constants.JsonPaths.INTERVAL_VAL, interval);

        // Get search query
        ObjectNode objectNode = getQuery(request, query, indexName, hasAggQuery);

        try {
            if (hasAggQuery && aggrQuery != null && !aggrQuery.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode aggrNode = mapper.readTree(aggrQuery).get(Constants.JsonPaths.AGGS);
                objectNode.put(Constants.JsonPaths.AGGS, mapper.readTree(aggrQuery).get(Constants.JsonPaths.AGGS));
            }
        } catch (Exception ex) {
            logger.error("Encountered an Exception while parsing the JSON : " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return objectNode;

    }

    private ObjectNode getQuery(AggregateRequestDto request, JsonNode query, String indexName, Boolean hasAggQuery) {
        String rqMs = query.get(Constants.JsonPaths.REQUEST_QUERY_MAP).asText();
        String dateReferenceField = query.get(Constants.JsonPaths.DATE_REF_FIELD).asText();
        JsonNode requestQueryMaps = null;
        ObjectNode objectNode = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> esFilterMap = new HashMap<>();
        try {
            requestQueryMaps = new ObjectMapper().readTree(rqMs);
            request.setEsFilters(esFilterMap);
            if (query.get(Constants.JsonPaths.MODULE).asText().equals(Constants.Modules.COMMON) &&
                    !request.getModuleLevel().equals(Constants.Modules.HOME_REVENUE) &&
                    !request.getModuleLevel().equals(Constants.Modules.HOME_SERVICES)) {
                request.getFilters().put(Constants.Filters.MODULE, request.getModuleLevel());
            }
            Iterator<Map.Entry<String, Object>> filtersItr = request.getFilters().entrySet().iterator();
            while (filtersItr.hasNext()) {
                Map.Entry<String, Object> entry = filtersItr.next();
                if (null != requestQueryMaps.get(entry.getKey()) && !String.valueOf(entry.getValue()).equals(Constants.Filters.FILTER_ALL)) {
                    // Filters in put filters are added as esfilters usign mapping in requestQueryMap
                    String esQueryKey = requestQueryMaps.get(entry.getKey()).asText();
                    request.getEsFilters().put(esQueryKey, entry.getValue());
                }
            }

            ElasticSearchDictator dictator = elasticSearchDao.createSearchDictatorV2(request, indexName, "", dateReferenceField);

            SearchRequest searchRequest = elasticSearchDao.buildElasticSearchQuery(dictator);
            JsonNode querySegment = mapper.readTree(searchRequest.source().toString());
            objectNode = (ObjectNode) querySegment;

            // Put limit and offset values
            if (!hasAggQuery) {
                // Put size in query
                if (request.getLimit() > 0) {
                    objectNode.put(Constants.JsonPaths.PAGINATION_ES_QUERY_LIMIT, request.getLimit());
                } else if (query.get(Constants.JsonPaths.PAGINATION_REQUEST_LIMIT).asLong() > 0) {
                    objectNode.put(Constants.JsonPaths.PAGINATION_ES_QUERY_LIMIT, query.get(Constants.JsonPaths.PAGINATION_REQUEST_LIMIT).asLong());
                }

                // Put offset in query
                if (request.getOffset() > 0) {
                    objectNode.put(Constants.JsonPaths.PAGINATION_ES_QUERY_OFFSET, request.getOffset());
                } else if (query.get(Constants.JsonPaths.PAGINATION_REQUEST_OFFSET).asLong() > 0) {
                    objectNode.put(Constants.JsonPaths.PAGINATION_ES_QUERY_OFFSET, query.get(Constants.JsonPaths.PAGINATION_REQUEST_OFFSET).asLong());
                }
                // Put sort query if sortBy and sortOrder both exists
                if (query.has(Constants.JsonPaths.CHART_CONFIG_SORTBY) && query.has(Constants.JsonPaths.CHART_CONFIG_SORT_ORDER)) {
                    String sortBy = query.get(Constants.JsonPaths.CHART_CONFIG_SORTBY).asText();
                    String sortOrder = query.get(Constants.JsonPaths.CHART_CONFIG_SORT_ORDER).asText();
                    JsonNode sortQuery = getSortQuery(sortBy, sortOrder);
                    objectNode.put(Constants.JsonPaths.PAGINATION_ES_QUERY_SORT, sortQuery);
                }
            }
            return objectNode;
        } catch (Exception ex) {
            logger.error("Encountered an Exception while parsing the JSON : " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private JsonNode getSortQuery(String sortBy, String sortOrder) {
        JsonNode node = null;
        String query = "[{\"{sortBy}\":{\"order\":\"{sortOrder}\"}}]".replace("{sortBy}", sortBy).replace("{sortOrder}", sortOrder);
        ObjectMapper mapper = new ObjectMapper();
        try {
            node = mapper.readValue(query, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}