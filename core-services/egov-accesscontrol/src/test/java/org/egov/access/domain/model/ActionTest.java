package org.egov.access.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionTest {

	@Test
	public void testShouldCheckEqualAndHashCodeForObjects() {
		Action action1 = Action.builder().id(1L).name("Create Complaint").url("/createcomplaint")
				.displayName("Create Complaint").serviceCode("test").build();
		Action action2 = Action.builder().id(1L).name("Create Complaint").url("/createcomplaint")
				.displayName("Create Complaint").serviceCode("test").build();

		Assertions.assertTrue(action1.equals(action2));
		Assertions.assertEquals(action1.hashCode(), action2.hashCode());
	}

	@Test
	public void testShouldCheckNotEqualAndHashCodeForObjects() {
		Action action1 = Action.builder().id(1L).name("Create Complaint").url("/createcomplaint")
				.displayName("Create Complaint").serviceCode("test").build();
		Action action2 = Action.builder().id(2L).name("Update Complaint").url("/updatecomplaint")
				.displayName("Update Complaint").serviceCode("test").build();

		Assertions.assertFalse(action1.equals(action2));
		Assertions.assertNotEquals(action1.hashCode(), action2.hashCode());
	}
}
