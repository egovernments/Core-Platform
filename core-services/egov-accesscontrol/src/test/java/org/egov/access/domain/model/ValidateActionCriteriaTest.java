package org.egov.access.domain.model;

import org.egov.access.domain.criteria.ValidateActionCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ValidateActionCriteriaTest {

	@Test
	public void testActionValidationCriteriaHasRolesToBeValidatedFor() {
		ValidateActionCriteria validateActionCriteria = ValidateActionCriteria.builder()
				.roleNames(Arrays.asList("role1, role2")).tenantId("tenant").actionUrl("url").build();

		Assertions.assertEquals(Arrays.asList("role1, role2"), validateActionCriteria.getRoleNames());
		Assertions.assertEquals("tenant", validateActionCriteria.getTenantId());
		Assertions.assertEquals("url", validateActionCriteria.getActionUrl());
	}
}
