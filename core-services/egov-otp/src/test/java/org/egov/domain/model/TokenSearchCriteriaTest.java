package org.egov.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenSearchCriteriaTest {

    @Test
    public void test_should_not_throw_exception_when_search_criteria_has_mandatory_fields() {
        final TokenSearchCriteria searchCriteria = new TokenSearchCriteria("uuid", "tenant");

        searchCriteria.validate();
    }
}