package org.egov.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenRequestTest {

    @Test
    public void test_should_not_throw_validation_exception_when_mandatory_fields_are_present() {
        final TokenRequest token = new TokenRequest("identity", "tenant");
        token.validate();
    }

    @Test
    public void test_should_generate_5_digit_token() {
        final TokenRequest token = new TokenRequest("identity", "tenant");

        Assertions.assertNotNull(token.generateToken());
        Assertions.assertEquals(5, token.generateToken().length());
    }

}