package org.egov.access.domain.service;

import org.egov.access.domain.model.RoleAction;
import org.egov.access.persistence.repository.BaseRepository;
import org.egov.access.persistence.repository.RoleActionRepository;
import org.egov.access.web.contract.action.RoleActionsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleActionServiceTest {

	@Mock
	private BaseRepository repository;

	@Mock
	private RoleActionRepository roleActionRepository;

	@InjectMocks
	private RoleActionService roleActionService;

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplat;

	@Test
	public void testShoudCreateRoleActions() {

		List<RoleAction> roleActions = new ArrayList<RoleAction>();

		RoleAction action = new RoleAction();

		action.setRoleCode("CITIZEN");
		action.setActionId(1);
		action.setTenantId("default");

		roleActions.add(action);

		RoleAction action1 = new RoleAction();

		action1.setRoleCode("CITIZEN");
		action1.setActionId(2);
		action1.setTenantId("default");

		roleActions.add(action);

		RoleActionsRequest roleRequest = new RoleActionsRequest();

		when(roleActionRepository.createRoleActions(any(RoleActionsRequest.class))).thenReturn(roleActions);

		List<RoleAction> rolesList = roleActionService.createRoleActions(roleRequest);

		assertThat(rolesList).isEqualTo(roleActions);

	}

	@Test
	public void testcheckActionNamesAreExistOrNot() {

		lenient().when(roleActionRepository.checkActionNamesAreExistOrNot(any(RoleActionsRequest.class))).thenReturn(false);

		boolean exist = roleActionService.checkActionNamesAreExistOrNot(any(RoleActionsRequest.class));

		assertThat(exist == false);
	}

	@Test
	public void TestAddUniqueValidationForTenantAndRoleAndAction() {

		lenient().when(roleActionRepository.addUniqueValidationForTenantAndRoleAndAction(any(RoleActionsRequest.class)))
				.thenReturn(false);

		boolean exist = roleActionService.addUniqueValidationForTenantAndRoleAndAction(any(RoleActionsRequest.class));

		assertThat(exist == false);

	}

}