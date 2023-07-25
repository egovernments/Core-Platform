package org.egov.infra.mdms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * SchemaDefCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-05-30T09:26:57.838+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchemaDefCriteria {
    @JsonProperty("tenantId")

    @Size(min = 1, max = 100)
    @NotNull
    private String tenantId = null;

    @JsonProperty("codes")
    @NotNull
    private List<String> codes = null;


    public SchemaDefCriteria addCodesItem(String codesItem) {
        if (this.codes == null) {
            this.codes = new ArrayList<>();
        }
        this.codes.add(codesItem);
        return this;
    }

}