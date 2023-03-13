package org.egov.wf.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.wf.config.WorkflowConfig;
import org.egov.wf.producer.Producer;
import org.egov.wf.repository.WorKflowRepository;
import org.egov.wf.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WorkflowMigrationService {

    @Autowired
    private WorKflowRepository workflowRepository;
    @Autowired
    private WorkflowConfig config;
    @Autowired
    private Producer producer;


    /**
     * Function migrates current processInstances from existing history table to current table.
     * @param migrationRequest
     * @return
     */

    public void migrate(MigrationRequest migrationRequest) {
        Integer count = 0;
        migrationRequest.getCriteria().setOffset(count);
        migrationRequest.getCriteria().setLimit(config.getMaxSearchLimit());

        while(true) {
            migrationRequest.getCriteria().setOffset(count);
            List<ProcessInstance> processInstances = workflowRepository.getProcessInstancesForMigration(migrationRequest.getCriteria());
            count += processInstances.size();
            ProcessInstanceRequest processInstanceRequest = ProcessInstanceRequest.builder().processInstances(processInstances).build();
            producer.push(processInstances.get(0).getTenantId(), config.getPersisterMigrationTopic(), processInstanceRequest);

            if(CollectionUtils.isEmpty(processInstances))
                break;

            MigrationJob migrationJob = MigrationJob.builder().id(migrationRequest.getJobId())
                    .startOffset(migrationRequest.getCriteria().getOffset())
                    .endOffset(count - 1)
                    .createdTime(System.currentTimeMillis())
                    .totalNumberOfRecordsMigrated(count).build();
            log.info(migrationJob.toString());
            producer.push(processInstances.get(0).getTenantId(), config.getJobAuditDetailsTopic(), migrationJob);
        }

    }

    public MigrationResponse migrateToActiveDB(MigrationRequest migrationRequest) {
        if(ObjectUtils.isEmpty(migrationRequest.getCriteria().getTenantId()))
            throw new CustomException("EG_WF_CRITERIA_ERR", "TenantId is mandatory for searching workflow");

        migrationRequest.setJobId(UUID.randomUUID().toString());
        producer.push(migrationRequest.getCriteria().getTenantId(), config.getMigrationTopic(), migrationRequest);

        MigrationResponse migrationResponse = MigrationResponse.builder().id(migrationRequest.getJobId())
                .message("Migration started successfully with job ID " + migrationRequest.getJobId()).tenantId(migrationRequest.getCriteria().getTenantId()).build();

        return migrationResponse;
    }
}


