package org.egov.errorretryservice.models;

import lombok.*;
import org.egov.common.contract.request.RequestInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorRetryRequest {

    private String uuid;

    private RequestInfo requestInfo;

}
