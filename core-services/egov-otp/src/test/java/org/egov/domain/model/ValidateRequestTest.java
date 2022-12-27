package org.egov.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateRequestTest {

    @Test
    public void test_should_not_throw_validation_exception_when_request_has_mandatory_parameters() {
        final ValidateRequest validateRequest = ValidateRequest.builder()
                .otp("otp")
                .tenantId("tenant")
                .identity("identity")
                .build();

        validateRequest.validate();
    }
}