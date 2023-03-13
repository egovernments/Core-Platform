package org.egov.wf.producer;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.wf.service.WorkflowMigrationService;
import org.egov.wf.web.models.MigrationRequest;
import org.egov.wf.web.models.ProcessInstanceSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class MigrationConsumer {

    @Autowired
    WorkflowMigrationService workflowMigrationService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = { "${wf.migrate.topic}"})
    public void listen(final HashMap<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info(record.toString());
        MigrationRequest migrationRequest = objectMapper.convertValue(record, MigrationRequest.class);
        try {
            workflowMigrationService.migrate(migrationRequest);
        }
        catch (Exception ex){
            log.error("Not able to process from topic" + ex);
        }

    }

}
