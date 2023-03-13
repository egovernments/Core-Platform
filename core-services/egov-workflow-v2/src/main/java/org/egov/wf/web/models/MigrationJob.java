package org.egov.wf.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class MigrationJob {


    @NotNull
    @JsonProperty("id")
    private String id;

    private Status status;

    @JsonProperty("startOffset")
    private Integer startOffset;

    @JsonProperty("endOffset")
    private Integer endOffset;

    @JsonProperty("totalNumberOfRecordsMigrated")
    private Integer totalNumberOfRecordsMigrated;

    @JsonProperty("createdTime")
    private Long createdTime;

}
