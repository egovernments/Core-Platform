package org.egov.access.domain.model;

import org.egov.access.domain.criteria.RoleSearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RoleSearchCriteriaTest {

	@Test
	public void testForEqualsAndHashCode() {
		RoleSearchCriteria searchCriteria1 = RoleSearchCriteria.builder().codes(Arrays.asList("EMP")).build();
		RoleSearchCriteria searchCriteria2 = RoleSearchCriteria.builder().codes(Arrays.asList("EMP")).build();

		Assertions.assertEquals(searchCriteria1, searchCriteria2);
		Assertions.assertEquals(searchCriteria1.hashCode(), searchCriteria2.hashCode());
	}

	@Test
	public void testForNotEqualObjects() {
		RoleSearchCriteria searchCriteria1 = RoleSearchCriteria.builder().codes(Arrays.asList("CITIZEN,EMPLOYEE"))
				.build();
		RoleSearchCriteria searchCriteria2 = RoleSearchCriteria.builder().codes(Arrays.asList("CITIZEN")).build();

		Assertions.assertNotEquals(searchCriteria1, searchCriteria2);
		Assertions.assertNotEquals(searchCriteria1.hashCode(), searchCriteria2.hashCode());
	}

	@Test
	public void testGetCodes() {
		RoleSearchCriteria searchCriteria = RoleSearchCriteria.builder().codes(Arrays.asList("CITIZEN", "EMPLOYEE"))
				.build();
		Assertions.assertEquals(Arrays.asList("CITIZEN", "EMPLOYEE"), searchCriteria.getCodes());
	}
}
