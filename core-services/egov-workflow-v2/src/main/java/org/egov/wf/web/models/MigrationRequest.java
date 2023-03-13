package org.egov.wf.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;


@ApiModel(description = "Contract to receive migration request")
@Validated

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MigrationRequest {

    @JsonProperty("RequestInfo")
    RequestInfo requestInfo;

    @JsonProperty("ProcessInstanceSearchCriteria")
    ProcessInstanceSearchCriteria criteria;

    @JsonProperty("jobId")
    private String jobId;

}
