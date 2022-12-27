package org.egov.access.domain.model;

import org.egov.access.domain.criteria.ActionSearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ActionSearchCriteriaTest {

	@Test
	public void testForEqualsAndHashCode() {
		List<String> roleCodesList = new ArrayList<String>();
		roleCodesList.add("CITIZEN");
		roleCodesList.add("SUPERUSER");
		ActionSearchCriteria searchCriteria1 = ActionSearchCriteria.builder().roleCodes(roleCodesList).build();
		ActionSearchCriteria searchCriteria2 = ActionSearchCriteria.builder().roleCodes(roleCodesList).build();

		Assertions.assertEquals(searchCriteria1, searchCriteria2);
		Assertions.assertEquals(searchCriteria1.hashCode(), searchCriteria2.hashCode());
	}

	@Test
	public void testForNotEqualObjects() {
		List<String> roleCodesList1 = new ArrayList<String>();
		roleCodesList1.add("CITIZEN");
		roleCodesList1.add("SUPERUSER");
		ActionSearchCriteria searchCriteria1 = ActionSearchCriteria.builder().roleCodes(roleCodesList1).build();
		List<String> roleCodesList2 = new ArrayList<String>();
		roleCodesList2.add("CITIZEN");
		roleCodesList2.add("TEST");
		ActionSearchCriteria searchCriteria2 = ActionSearchCriteria.builder().roleCodes(roleCodesList2).build();

		Assertions.assertNotEquals(searchCriteria1, searchCriteria2);
		Assertions.assertNotEquals(searchCriteria1.hashCode(), searchCriteria2.hashCode());
	}
}
