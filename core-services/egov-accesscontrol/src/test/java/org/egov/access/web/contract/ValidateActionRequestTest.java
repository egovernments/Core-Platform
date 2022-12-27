package org.egov.access.web.contract;

import org.egov.access.domain.criteria.ValidateActionCriteria;
import org.egov.access.web.contract.role.RoleContract;
import org.egov.access.web.contract.validateaction.TenantRoleContract;
import org.egov.access.web.contract.validateaction.ValidateActionContract;
import org.egov.access.web.contract.validateaction.ValidateActionRequest;
import org.egov.common.contract.request.RequestInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ValidateActionRequestTest {

	@Test
	public void testThatValidateActionRequestContractContainsInfoAboutRequestInfoAndValidateAction() {
		RequestInfo requestInfo = RequestInfo.builder().build();
		RoleContract role = RoleContract.builder().name("role name").build();
		TenantRoleContract tenantRole = TenantRoleContract.builder().roles(Arrays.asList(role)).tenantId("tenantId")
				.build();
		ValidateActionContract validateAction = ValidateActionContract.builder().tenantRole(tenantRole).actionUrl("url")
				.build();

		ValidateActionRequest validateActionRequest = ValidateActionRequest.builder().requestInfo(requestInfo)
				.validateAction(validateAction).build();

		Assertions.assertEquals(requestInfo, validateActionRequest.getRequestInfo());
		Assertions.assertEquals(validateAction, validateActionRequest.getValidateAction());
	}

	@Test
	public void testToDomainReturnsActionValidationCriteria() {
		RequestInfo requestInfo = RequestInfo.builder().build();
		RoleContract role = RoleContract.builder().name("role name").build();
		TenantRoleContract tenantRole = TenantRoleContract.builder().roles(Arrays.asList(role)).tenantId("tenantId")
				.build();
		ValidateActionContract validateAction = ValidateActionContract.builder().tenantRole(tenantRole).actionUrl("url")
				.build();

		ValidateActionRequest validateActionRequest = ValidateActionRequest.builder().requestInfo(requestInfo)
				.validateAction(validateAction).build();
		ValidateActionCriteria criteria = validateActionRequest.toDomain();

		Assertions.assertEquals(Arrays.asList("role name"), criteria.getRoleNames());
		Assertions.assertEquals("tenantId", criteria.getTenantId());
		Assertions.assertEquals("url", criteria.getActionUrl());

	}
}
