package org.egov.wf.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class MigrationResponse {

    @NotNull
    @JsonProperty("id")
    String id;

    @JsonProperty("tenantId")
    String tenantId;

    @JsonProperty("message")
    String message;

}
