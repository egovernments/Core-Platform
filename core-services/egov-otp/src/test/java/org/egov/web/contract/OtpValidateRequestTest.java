package org.egov.web.contract;

import org.egov.common.contract.request.RequestInfo;
import org.egov.domain.model.ValidateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OtpValidateRequestTest {

    @Test
    public void test_should_create_domain_from_contract() {
        final RequestInfo requestInfo = RequestInfo.builder().build();
        final Otp otp = new Otp("otp", null, "identity", "tenant", false);
        final OtpValidateRequest validateRequest =
                new OtpValidateRequest(requestInfo, otp);

        final ValidateRequest domain = validateRequest.toDomainValidateRequest();

        Assertions.assertNotNull(domain);
        Assertions.assertEquals("otp", domain.getOtp());
        Assertions.assertEquals("identity", domain.getIdentity());
        Assertions.assertEquals("tenant", domain.getTenantId());
    }
}