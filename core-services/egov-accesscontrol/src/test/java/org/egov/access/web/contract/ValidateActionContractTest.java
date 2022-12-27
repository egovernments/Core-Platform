package org.egov.access.web.contract;

import org.egov.access.web.contract.role.RoleContract;
import org.egov.access.web.contract.validateaction.TenantRoleContract;
import org.egov.access.web.contract.validateaction.ValidateActionContract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ValidateActionContractTest {

	@Test
	public void testThatValidateActionContractContainsInfoAboutTenantRoleAndActionUrl() {
		RoleContract role = RoleContract.builder().name("role name").build();
		TenantRoleContract tenantRole = TenantRoleContract.builder().tenantId("ap.public").roles(Arrays.asList(role))
				.build();
		ValidateActionContract validateAction = ValidateActionContract.builder().tenantRole(tenantRole).actionUrl("url")
				.build();

		Assertions.assertEquals(tenantRole, validateAction.getTenantRole());
		Assertions.assertEquals("url", validateAction.getActionUrl());

	}
}
