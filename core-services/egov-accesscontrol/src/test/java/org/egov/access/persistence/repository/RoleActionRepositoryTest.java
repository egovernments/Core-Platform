package org.egov.access.persistence.repository;

import org.egov.access.domain.model.Action;
import org.egov.access.domain.model.Role;
import org.egov.access.domain.model.RoleAction;
import org.egov.access.web.contract.action.RoleActionsRequest;
import org.egov.common.contract.request.RequestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RoleActionRepository.class})
@ExtendWith(SpringExtension.class)
class RoleActionRepositoryTest {
    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RoleActionRepository roleActionRepository;

    @Test
    void testCreateRoleActions3() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getInt((String) any())).thenReturn(1);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any()))
                .thenReturn(sqlRowSet);

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());
        RequestInfo requestInfo = ActionRepository.getRInfo();
        Role role = new Role();

        RoleActionsRequest roleActionsRequest = new RoleActionsRequest(requestInfo, role, "42", new ArrayList<>());
        roleActionsRequest.setActions(actionList);
        List<RoleAction> actualCreateRoleActionsResult = roleActionRepository.createRoleActions(roleActionsRequest);
        assertEquals(2, actualCreateRoleActionsResult.size());
        RoleAction getResult = actualCreateRoleActionsResult.get(0);
        assertEquals("42", getResult.getTenantId());
        RoleAction getResult1 = actualCreateRoleActionsResult.get(1);
        assertEquals("42", getResult1.getTenantId());
        assertNull(getResult1.getRoleCode());
        assertEquals(1L, getResult1.getActionId());
        assertNull(getResult.getRoleCode());
        assertEquals(1L, getResult.getActionId());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet, atLeast(1)).next();
        verify(sqlRowSet, atLeast(1)).getInt((String) any());
    }

    @Test
    void testCheckActionNamesAreExistOrNot2() {
        RoleActionsRequest roleActionsRequest = new RoleActionsRequest();
        roleActionsRequest.setActions(new ArrayList<>());
        assertFalse(roleActionRepository.checkActionNamesAreExistOrNot(roleActionsRequest));
    }

    @Test
    void testCheckActionNamesAreExistOrNot4() {
        RoleActionsRequest roleActionsRequest = mock(RoleActionsRequest.class);
        when(roleActionsRequest.getActions()).thenReturn(new ArrayList<>());
        assertFalse(roleActionRepository.checkActionNamesAreExistOrNot(roleActionsRequest));
        verify(roleActionsRequest).getActions();
    }

    @Test
    void testCheckActionNamesAreExistOrNot5() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getInt((String) any())).thenReturn(1);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any()))
                .thenReturn(sqlRowSet);

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());
        RoleActionsRequest roleActionsRequest = mock(RoleActionsRequest.class);
        when(roleActionsRequest.getActions()).thenReturn(actionList);
        assertFalse(roleActionRepository.checkActionNamesAreExistOrNot(roleActionsRequest));
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet, atLeast(1)).next();
        verify(sqlRowSet, atLeast(1)).getInt((String) any());
        verify(roleActionsRequest, atLeast(1)).getActions();
    }

    @Test
    void testCheckActionNamesAreExistOrNot6() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getInt((String) any())).thenReturn(1);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any()))
                .thenReturn(sqlRowSet);

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());
        actionList.add(new Action());
        RoleActionsRequest roleActionsRequest = mock(RoleActionsRequest.class);
        when(roleActionsRequest.getActions()).thenReturn(actionList);
        assertTrue(roleActionRepository.checkActionNamesAreExistOrNot(roleActionsRequest));
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet, atLeast(1)).next();
        verify(sqlRowSet, atLeast(1)).getInt((String) any());
        verify(roleActionsRequest, atLeast(1)).getActions();
    }

    @Test
    void testAddUniqueValidationForTenantAndRoleAndAction3() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getInt((String) any())).thenReturn(1);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any()))
                .thenReturn(sqlRowSet);

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());

        RoleActionsRequest roleActionsRequest = new RoleActionsRequest();
        roleActionsRequest.setRole(new Role());
        roleActionsRequest.setActions(actionList);
        assertTrue(roleActionRepository.addUniqueValidationForTenantAndRoleAndAction(roleActionsRequest));
        verify(namedParameterJdbcTemplate, atLeast(1)).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet, atLeast(1)).next();
        verify(sqlRowSet, atLeast(1)).getInt((String) any());
    }
}

