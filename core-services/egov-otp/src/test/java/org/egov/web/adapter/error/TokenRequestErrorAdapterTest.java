package org.egov.web.adapter.error;

import org.egov.common.contract.response.ErrorField;
import org.egov.common.contract.response.ErrorResponse;
import org.egov.domain.model.TokenRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TokenRequestErrorAdapterTest {

    @Test
    public void test_should_add_error_field_when_identity_is_not_present() {
        final TokenRequestErrorAdapter errorAdapter = new TokenRequestErrorAdapter();
        final TokenRequest tokenRequest = new TokenRequest(null, "tenantId");

        final ErrorResponse errorResponse = errorAdapter.adapt(tokenRequest);

        Assertions.assertNotNull(errorResponse);
        final List<ErrorField> errorFields = errorResponse.getError().getFields();
        Assertions.assertNotNull(errorFields);
        Assertions.assertEquals(1, errorFields.size());
        Assertions.assertEquals("OTP.IDENTITY_MANDATORY", errorFields.get(0).getCode());
        Assertions.assertEquals("otp.identity", errorFields.get(0).getField());
        Assertions.assertEquals("Identity field is mandatory", errorFields.get(0).getMessage());
    }

    @Test
    public void test_should_add_error_field_when_tenant_is_not_present() {
        final TokenRequestErrorAdapter errorAdapter = new TokenRequestErrorAdapter();
        final TokenRequest tokenRequest = new TokenRequest("identity", null);

        final ErrorResponse errorResponse = errorAdapter.adapt(tokenRequest);

        Assertions.assertNotNull(errorResponse);
        final List<ErrorField> errorFields = errorResponse.getError().getFields();
        Assertions.assertNotNull(errorFields);
        Assertions.assertEquals(1, errorFields.size());
        Assertions.assertEquals("OTP.TENANT_ID_MANDATORY", errorFields.get(0).getCode());
        Assertions.assertEquals("otp.tenantId", errorFields.get(0).getField());
        Assertions.assertEquals("Tenant field is mandatory", errorFields.get(0).getMessage());
    }

}