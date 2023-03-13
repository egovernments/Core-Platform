package org.egov.wf.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.egov.wf.config.WorkflowConfig;
import org.egov.wf.repository.BusinessServiceRepository;
import org.egov.wf.web.models.Action;
import org.egov.wf.web.models.AuditDetails;
import org.egov.wf.web.models.BusinessService;
import org.egov.wf.web.models.Document;
import org.egov.wf.web.models.ProcessInstance;
import org.egov.wf.web.models.ProcessInstanceSearchCriteria;
import org.egov.wf.web.models.ProcessStateAndAction;
import org.egov.wf.web.models.State;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowUtil.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class WorkflowUtilTest {
    @MockBean
    private BusinessServiceRepository businessServiceRepository;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private WorkflowConfig workflowConfig;

    @Autowired
    private WorkflowUtil workflowUtil;

    /**
     * Method under test: {@link WorkflowUtil#getAuditDetails(String, Boolean)}
     */
    @Test
    void testGetAuditDetails() {
        AuditDetails actualAuditDetails = workflowUtil.getAuditDetails("By", true);
        assertEquals("By", actualAuditDetails.getCreatedBy());
        assertEquals("By", actualAuditDetails.getLastModifiedBy());
    }

    /**
     * Method under test: {@link WorkflowUtil#getAuditDetails(String, Boolean)}
     */
    @Test
    void testGetAuditDetailsWithNull() {
        AuditDetails actualAuditDetails = workflowUtil.getAuditDetails("By", false);
        assertNull(actualAuditDetails.getCreatedBy());
        assertEquals("By", actualAuditDetails.getLastModifiedBy());
        assertNull(actualAuditDetails.getCreatedTime());
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailable() {
        ArrayList<Role> userRoles = new ArrayList<>();
        assertFalse(workflowUtil.isRoleAvailable("42", userRoles, new ArrayList<>()));
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailableWithFalse() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        assertFalse(workflowUtil.isRoleAvailable("42", roleList, new ArrayList<>()));
    }


    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailableWithnull() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role(1L, "Name", "Code", "42"));
        assertFalse(workflowUtil.isRoleAvailable("42", roleList, new ArrayList<>()));
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailableVerifyWithCode() {
        Role role = mock(Role.class);
        when(role.getCode()).thenReturn("Code");
        when(role.getTenantId()).thenReturn("42");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        assertFalse(workflowUtil.isRoleAvailable("42", roleList, new ArrayList<>()));
        verify(role).getCode();
        verify(role).getTenantId();
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailableVerifyWithTenantId() {
        Role role = mock(Role.class);
        when(role.getCode()).thenThrow(new CustomException("*", "An error occurred"));
        when(role.getTenantId()).thenReturn("42");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        assertThrows(CustomException.class, () -> workflowUtil.isRoleAvailable("42", roleList, new ArrayList<>()));
        verify(role).getCode();
        verify(role).getTenantId();
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(String, List, List)}
     */
    @Test
    void testIsRoleAvailableVerifyRole() {
        Role role = mock(Role.class);
        when(role.getCode()).thenReturn("Code");
        when(role.getTenantId()).thenReturn("42");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        assertFalse(workflowUtil.isRoleAvailable("*", roleList, new ArrayList<>()));
        verify(role).getTenantId();
    }


    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(List, List)}
     */
    @Test
    void testIsRoleAvailableUserRolesFalse() {
        ArrayList<String> userRoles = new ArrayList<>();
        assertFalse(workflowUtil.isRoleAvailable(userRoles, new ArrayList<>()));
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(List, List)}
     */
    @Test
    void testIsRoleAvailableAddStringList() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");
        assertFalse(workflowUtil.isRoleAvailable(stringList, new ArrayList<>()));
    }

    /**
     * Method under test: {@link WorkflowUtil#isRoleAvailable(List, List)}
     */
    @Test
    void testIsRoleAvailableTrueWithList() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");

        ArrayList<String> stringList1 = new ArrayList<>();
        stringList1.add("foo");
        assertTrue(workflowUtil.isRoleAvailable(stringList, stringList1));
    }


    /**
     * Method under test: {@link WorkflowUtil#rolesAllowedInService(BusinessService)}
     */
    @Test
    void testRolesAllowedInService() {
        ArrayList<State> states = new ArrayList<>();
        assertTrue(workflowUtil
                .rolesAllowedInService(new BusinessService("42", "01234567-89AB-CDEF-FEDC-BA9876543210", "Business Service",
                        "Business", "Get Uri", "Post Uri", 1L, states, new AuditDetails()))
                .isEmpty());
    }



    /**
     * Method under test: {@link WorkflowUtil#rolesAllowedInService(BusinessService)}
     */
    @Test
    void testRolesAllowedInServiceWithBusinessService() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(new ArrayList<>());
        assertTrue(workflowUtil.rolesAllowedInService(businessService).isEmpty());
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#rolesAllowedInService(BusinessService)}
     */
    @Test
    void testRolesAllowedInServicewithAddNewState() {
        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(new State());
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(stateList);
        assertTrue(workflowUtil.rolesAllowedInService(businessService).isEmpty());
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#rolesAllowedInService(BusinessService)}
     */
    @Test
    void testRolesAllowedInServiceWithlistadd() {
        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(new State());
        stateList.add(new State());
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(stateList);
        assertTrue(workflowUtil.rolesAllowedInService(businessService).isEmpty());
        verify(businessService).getStates();
    }



    /**
     * Method under test: {@link WorkflowUtil#rolesAllowedInService(BusinessService)}
     */
    @Test
    void testRolesAllowedInServiceEqualtoRoleItem() {
        Action action = new Action();
        action.addRolesItem("Roles Item");

        State state = new State();
        state.addActionsItem(action);

        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(state);
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(stateList);
        List<String> actualRolesAllowedInServiceResult = workflowUtil.rolesAllowedInService(businessService);
        assertEquals(1, actualRolesAllowedInServiceResult.size());
        assertEquals("Roles Item", actualRolesAllowedInServiceResult.get(0));
        verify(businessService).getStates();
    }


    /**
     * Method under test: {@link WorkflowUtil#getStateToRoleMap(List)}
     */
    @Test
    void testGetStateToRoleMap() {
        assertTrue(workflowUtil.getStateToRoleMap(new ArrayList<>()).isEmpty());
    }



    /**
     * Method under test: {@link WorkflowUtil#getStateToRoleMap(List)}
     */
    @Test
    void testGetStateToRoleMapExpecttrue() {
        BusinessService businessService = new BusinessService();
        businessService.addStatesItem(new State());

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        assertEquals(1, workflowUtil.getStateToRoleMap(businessServiceList).size());
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateToRoleMap(List)}
     */
    @Test
    void testGetStateToRoleMapWithState() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(new ArrayList<>());
        when(businessService.addStatesItem((State) any())).thenReturn(new BusinessService());
        businessService.addStatesItem(new State());

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        assertTrue(workflowUtil.getStateToRoleMap(businessServiceList).isEmpty());
        verify(businessService).getStates();
        verify(businessService).addStatesItem((State) any());
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateToRoleMap(List)}
     */
    @Test
    void testGetStateToRoleMapStateItemAdd() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(new ArrayList<>());
        when(businessService.addStatesItem((State) any())).thenReturn(new BusinessService());
        businessService.addStatesItem(new State());

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        ArrayList<State> states = new ArrayList<>();
        businessServiceList.add(new BusinessService("42", "01234567-89AB-CDEF-FEDC-BA9876543210", "Business Service",
                "Business", "Get Uri", "Post Uri", 1L, states, new AuditDetails()));
        businessServiceList.add(businessService);
        assertTrue(workflowUtil.getStateToRoleMap(businessServiceList).isEmpty());
        verify(businessService).getStates();
        verify(businessService).addStatesItem((State) any());
    }


    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToUserRolesMap(RequestInfo)}
     */
    @Test
    void testGetTenantIdToUserRolesMaps() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(new User(1L, "janedoe", "Name", "Type", "42", "42", new ArrayList<>(), "42",
                "01234567-89AB-CDEF-FEDC-BA9876543210"));
        assertTrue(workflowUtil.getTenantIdToUserRolesMap(requestInfo).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToUserRolesMap(RequestInfo)}
     */
    @Test
    void testGetTenantIdToUserRolesMap() {
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(new ArrayList<>());

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        assertTrue(workflowUtil.getTenantIdToUserRolesMap(requestInfo).isEmpty());
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToUserRolesMap(RequestInfo)}
     */
    @Test
    void testGetTenantIdToUserRolesMap5() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        Map<String, List<String>> actualTenantIdToUserRolesMap = workflowUtil.getTenantIdToUserRolesMap(requestInfo);
        assertEquals(1, actualTenantIdToUserRolesMap.size());
        List<String> getResult = actualTenantIdToUserRolesMap.get(null);
        assertEquals(1, getResult.size());
        assertNull(getResult.get(0));
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToUserRolesMap(RequestInfo)}
     */
    @Test
    void testGetTenantIdToUserRolesMap6() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        Map<String, List<String>> actualTenantIdToUserRolesMap = workflowUtil.getTenantIdToUserRolesMap(requestInfo);
        assertEquals(1, actualTenantIdToUserRolesMap.size());
        List<String> getResult = actualTenantIdToUserRolesMap.get(null);
        assertEquals(2, getResult.size());
        assertNull(getResult.get(0));
        assertNull(getResult.get(1));
        verify(user).getRoles();
    }


    /**
     * Method under test: {@link WorkflowUtil#getRoleToTenantId(RequestInfo)}
     */
    @Test
    void testGetRoleToTenantId3() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(new User(1L, "janedoe", "Name", "Type", "42", "42", new ArrayList<>(), "42",
                "01234567-89AB-CDEF-FEDC-BA9876543210"));
        assertTrue(workflowUtil.getRoleToTenantId(requestInfo).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getRoleToTenantId(RequestInfo)}
     */
    @Test
    void testGetRoleToTenantId4() {
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(new ArrayList<>());

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        assertTrue(workflowUtil.getRoleToTenantId(requestInfo).isEmpty());
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getRoleToTenantId(RequestInfo)}
     */
    @Test
    void testGetRoleToTenantId5() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        Map<String, List<String>> actualRoleToTenantId = workflowUtil.getRoleToTenantId(requestInfo);
        assertEquals(1, actualRoleToTenantId.size());
        List<String> getResult = actualRoleToTenantId.get(null);
        assertEquals(1, getResult.size());
        assertNull(getResult.get(0));
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getRoleToTenantId(RequestInfo)}
     */
    @Test
    void testGetRoleToTenantId6() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(user);
        Map<String, List<String>> actualRoleToTenantId = workflowUtil.getRoleToTenantId(requestInfo);
        assertEquals(1, actualRoleToTenantId.size());
        List<String> getResult = actualRoleToTenantId.get(null);
        assertEquals(2, getResult.size());
        assertNull(getResult.get(0));
        assertNull(getResult.get(1));
        verify(user).getRoles();
    }


    /**
     * Method under test: {@link WorkflowUtil#enrichStatusesInSearchCriteria(RequestInfo, ProcessInstanceSearchCriteria)}
     */
    @Test
    void testEnrichStatusesInSearchCriteria3() {
        when(businessServiceRepository.getRoleTenantAndStatusMapping()).thenReturn(new HashMap<>());

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(new User(1L, "janedoe", "Name", "Type", "42", "42", new ArrayList<>(), "42",
                "01234567-89AB-CDEF-FEDC-BA9876543210"));

        ProcessInstanceSearchCriteria processInstanceSearchCriteria = new ProcessInstanceSearchCriteria();
        processInstanceSearchCriteria.setAssignee("Assignee");
        processInstanceSearchCriteria.setBusinessIds(new ArrayList<>());
        processInstanceSearchCriteria.setBusinessService("Business Service");
        processInstanceSearchCriteria.setFromDate(1L);
        processInstanceSearchCriteria.setHistory(true);
        processInstanceSearchCriteria.setIds(new ArrayList<>());
        processInstanceSearchCriteria.setIsAssignedToMeCount(true);
        processInstanceSearchCriteria.setIsEscalatedCount(true);
        processInstanceSearchCriteria.setIsNearingSlaCount(true);
        processInstanceSearchCriteria.setLimit(1);
        processInstanceSearchCriteria.setModuleName("Module Name");
        processInstanceSearchCriteria.setMultipleAssignees(new ArrayList<>());
        processInstanceSearchCriteria.setOffset(2);
        processInstanceSearchCriteria.setSlotPercentageSlaLimit(1L);
        processInstanceSearchCriteria.setStatesToIgnore(new ArrayList<>());
        processInstanceSearchCriteria.setStatus(new ArrayList<>());
        processInstanceSearchCriteria.setStatusesIrrespectiveOfTenant(new ArrayList<>());
        processInstanceSearchCriteria.setTenantId("42");
        processInstanceSearchCriteria.setTenantSpecifiStatus(new ArrayList<>());
        processInstanceSearchCriteria.setToDate(1L);
        workflowUtil.enrichStatusesInSearchCriteria(requestInfo, processInstanceSearchCriteria);
        verify(businessServiceRepository).getRoleTenantAndStatusMapping();
    }

    /**
     * Method under test: {@link WorkflowUtil#enrichStatusesInSearchCriteria(RequestInfo, ProcessInstanceSearchCriteria)}
     */
    @Test
    void testEnrichStatusesInSearchCriteria4() {
        when(businessServiceRepository.getRoleTenantAndStatusMapping()).thenReturn(new HashMap<>());

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        User userInfo = new User(1L, "janedoe", "Name", "Type", "42", "42", roleList, "42",
                "01234567-89AB-CDEF-FEDC-BA9876543210");

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(userInfo);

        ProcessInstanceSearchCriteria processInstanceSearchCriteria = new ProcessInstanceSearchCriteria();
        processInstanceSearchCriteria.setAssignee("Assignee");
        processInstanceSearchCriteria.setBusinessIds(new ArrayList<>());
        processInstanceSearchCriteria.setBusinessService("Business Service");
        processInstanceSearchCriteria.setFromDate(1L);
        processInstanceSearchCriteria.setHistory(true);
        processInstanceSearchCriteria.setIds(new ArrayList<>());
        processInstanceSearchCriteria.setIsAssignedToMeCount(true);
        processInstanceSearchCriteria.setIsEscalatedCount(true);
        processInstanceSearchCriteria.setIsNearingSlaCount(true);
        processInstanceSearchCriteria.setLimit(1);
        processInstanceSearchCriteria.setModuleName("Module Name");
        processInstanceSearchCriteria.setMultipleAssignees(new ArrayList<>());
        processInstanceSearchCriteria.setOffset(2);
        processInstanceSearchCriteria.setSlotPercentageSlaLimit(1L);
        processInstanceSearchCriteria.setStatesToIgnore(new ArrayList<>());
        processInstanceSearchCriteria.setStatus(new ArrayList<>());
        processInstanceSearchCriteria.setStatusesIrrespectiveOfTenant(new ArrayList<>());
        processInstanceSearchCriteria.setTenantId("42");
        processInstanceSearchCriteria.setTenantSpecifiStatus(new ArrayList<>());
        processInstanceSearchCriteria.setToDate(1L);
        workflowUtil.enrichStatusesInSearchCriteria(requestInfo, processInstanceSearchCriteria);
        verify(businessServiceRepository).getRoleTenantAndStatusMapping();
    }

    /**
     * Method under test: {@link WorkflowUtil#enrichStatusesInSearchCriteria(RequestInfo, ProcessInstanceSearchCriteria)}
     */
    @Test
    void testEnrichStatusesInSearchCriteria5() {
        when(businessServiceRepository.getRoleTenantAndStatusMapping()).thenReturn(new HashMap<>());

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());
        User userInfo = new User(1L, "janedoe", "Name", "Type", "42", "42", roleList, "42",
                "01234567-89AB-CDEF-FEDC-BA9876543210");

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUserInfo(userInfo);

        ProcessInstanceSearchCriteria processInstanceSearchCriteria = new ProcessInstanceSearchCriteria();
        processInstanceSearchCriteria.setAssignee("Assignee");
        processInstanceSearchCriteria.setBusinessIds(new ArrayList<>());
        processInstanceSearchCriteria.setBusinessService("Business Service");
        processInstanceSearchCriteria.setFromDate(1L);
        processInstanceSearchCriteria.setHistory(true);
        processInstanceSearchCriteria.setIds(new ArrayList<>());
        processInstanceSearchCriteria.setIsAssignedToMeCount(true);
        processInstanceSearchCriteria.setIsEscalatedCount(true);
        processInstanceSearchCriteria.setIsNearingSlaCount(true);
        processInstanceSearchCriteria.setLimit(1);
        processInstanceSearchCriteria.setModuleName("Module Name");
        processInstanceSearchCriteria.setMultipleAssignees(new ArrayList<>());
        processInstanceSearchCriteria.setOffset(2);
        processInstanceSearchCriteria.setSlotPercentageSlaLimit(1L);
        processInstanceSearchCriteria.setStatesToIgnore(new ArrayList<>());
        processInstanceSearchCriteria.setStatus(new ArrayList<>());
        processInstanceSearchCriteria.setStatusesIrrespectiveOfTenant(new ArrayList<>());
        processInstanceSearchCriteria.setTenantId("42");
        processInstanceSearchCriteria.setTenantSpecifiStatus(new ArrayList<>());
        processInstanceSearchCriteria.setToDate(1L);
        workflowUtil.enrichStatusesInSearchCriteria(requestInfo, processInstanceSearchCriteria);
        verify(businessServiceRepository).getRoleTenantAndStatusMapping();
    }


    /**
     * Method under test: {@link WorkflowUtil#getAllRolesFromState(State)}
     */
    @Test
    void testGetAllRolesFromState() {
        assertTrue(workflowUtil.getAllRolesFromState(new State()).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getAllRolesFromState(State)}
     */
    @Test
    void testGetAllRolesFromStates() {
        State state = mock(State.class);
        when(state.getActions()).thenReturn(new ArrayList<>());
        assertTrue(workflowUtil.getAllRolesFromState(state).isEmpty());
        verify(state).getActions();
    }


    /**
     * Method under test: {@link WorkflowUtil#getAllRolesFromState(State)}
     */
    @Test
    void testGetAllRolesFromStateWithRoleItem() {
        Action action = new Action();
        action.addRolesItem("Roles Item");

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(action);
        State state = mock(State.class);
        when(state.getActions()).thenReturn(actionList);
        List<String> actualAllRolesFromState = workflowUtil.getAllRolesFromState(state);
        assertEquals(1, actualAllRolesFromState.size());
        assertEquals("Roles Item", actualAllRolesFromState.get(0));
        verify(state).getActions();
    }


    /**
     * Method under test: {@link WorkflowUtil#getBusinessIds(List)}
     */
    @Test
    void testGetBusinessIds() {
        assertTrue(workflowUtil.getBusinessIds(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getBusinessIds(List)}
     */
    @Test
    void testGetBusinessIds2() {
        ProcessStateAndAction processStateAndAction = new ProcessStateAndAction();
        processStateAndAction.setAction(new Action());
        processStateAndAction.setCurrentState(new State());
        processStateAndAction.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction.setResultantState(new State());

        ArrayList<ProcessStateAndAction> processStateAndActionList = new ArrayList<>();
        processStateAndActionList.add(processStateAndAction);
        Set<String> actualBusinessIds = workflowUtil.getBusinessIds(processStateAndActionList);
        assertEquals(1, actualBusinessIds.size());
        assertTrue(actualBusinessIds.contains(null));
    }

    /**
     * Method under test: {@link WorkflowUtil#getBusinessIds(List)}
     */
    @Test
    void testGetBusinessIds3() {
        ProcessStateAndAction processStateAndAction = new ProcessStateAndAction();
        processStateAndAction.setAction(new Action());
        processStateAndAction.setCurrentState(new State());
        processStateAndAction.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction.setResultantState(new State());

        ProcessStateAndAction processStateAndAction1 = new ProcessStateAndAction();
        processStateAndAction1.setAction(new Action());
        processStateAndAction1.setCurrentState(new State());
        processStateAndAction1.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction1.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction1.setResultantState(new State());

        ArrayList<ProcessStateAndAction> processStateAndActionList = new ArrayList<>();
        processStateAndActionList.add(processStateAndAction1);
        processStateAndActionList.add(processStateAndAction);
        Set<String> actualBusinessIds = workflowUtil.getBusinessIds(processStateAndActionList);
        assertEquals(1, actualBusinessIds.size());
        assertTrue(actualBusinessIds.contains(null));
    }

    /**
     * Method under test: {@link WorkflowUtil#getBusinessIds(List)}
     */
    @Test
    void testGetBusinessIds4() {
        ProcessStateAndAction processStateAndAction = mock(ProcessStateAndAction.class);
        when(processStateAndAction.getProcessInstanceFromRequest()).thenReturn(new ProcessInstance());
        doNothing().when(processStateAndAction).setAction((Action) any());
        doNothing().when(processStateAndAction).setCurrentState((State) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        doNothing().when(processStateAndAction).setResultantState((State) any());
        processStateAndAction.setAction(new Action());
        processStateAndAction.setCurrentState(new State());
        processStateAndAction.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction.setResultantState(new State());

        ArrayList<ProcessStateAndAction> processStateAndActionList = new ArrayList<>();
        processStateAndActionList.add(processStateAndAction);
        Set<String> actualBusinessIds = workflowUtil.getBusinessIds(processStateAndActionList);
        assertEquals(1, actualBusinessIds.size());
        assertTrue(actualBusinessIds.contains(null));
        verify(processStateAndAction).getProcessInstanceFromRequest();
        verify(processStateAndAction).setAction((Action) any());
        verify(processStateAndAction).setCurrentState((State) any());
        verify(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        verify(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        verify(processStateAndAction).setResultantState((State) any());
    }


    /**
     * Method under test: {@link WorkflowUtil#getLatestProcessStateAndAction(String, List)}
     */
    @Test
    void testGetLatestProcessStateAndAction() {
        assertNull(workflowUtil.getLatestProcessStateAndAction("42", new ArrayList<>()));
    }


    /**
     * Method under test: {@link WorkflowUtil#getLatestProcessStateAndAction(String, List)}
     */
    @Test
    void testGetLatestProcessStateAndAction6() {
        ProcessInstance processInstance = mock(ProcessInstance.class);
        when(processInstance.getAuditDetails()).thenThrow(new CustomException("Code", "An error occurred"));
        when(processInstance.getBusinessId()).thenReturn("42");
        ProcessStateAndAction processStateAndAction = mock(ProcessStateAndAction.class);
        when(processStateAndAction.getProcessInstanceFromRequest()).thenReturn(processInstance);
        doNothing().when(processStateAndAction).setAction((Action) any());
        doNothing().when(processStateAndAction).setCurrentState((State) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        doNothing().when(processStateAndAction).setResultantState((State) any());
        processStateAndAction.setAction(new Action());
        processStateAndAction.setCurrentState(new State());
        processStateAndAction.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction.setResultantState(new State());

        ArrayList<ProcessStateAndAction> processStateAndActionList = new ArrayList<>();
        processStateAndActionList.add(processStateAndAction);
        assertThrows(CustomException.class,
                () -> workflowUtil.getLatestProcessStateAndAction("42", processStateAndActionList));
        verify(processStateAndAction, atLeast(1)).getProcessInstanceFromRequest();
        verify(processStateAndAction).setAction((Action) any());
        verify(processStateAndAction).setCurrentState((State) any());
        verify(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        verify(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        verify(processStateAndAction).setResultantState((State) any());
        verify(processInstance).getBusinessId();
        verify(processInstance).getAuditDetails();
    }

    /**
     * Method under test: {@link WorkflowUtil#getLatestProcessStateAndAction(String, List)}
     */
    @Test
    void testGetLatestProcessStateAndAction7() {
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setLastModifiedTime(1L);
        ProcessInstance processInstance = mock(ProcessInstance.class);
        when(processInstance.getAuditDetails()).thenReturn(auditDetails);
        when(processInstance.getBusinessId()).thenReturn("42");
        ProcessStateAndAction processStateAndAction = mock(ProcessStateAndAction.class);
        when(processStateAndAction.getProcessInstanceFromRequest()).thenReturn(processInstance);
        doNothing().when(processStateAndAction).setAction((Action) any());
        doNothing().when(processStateAndAction).setCurrentState((State) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        doNothing().when(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        doNothing().when(processStateAndAction).setResultantState((State) any());
        processStateAndAction.setAction(new Action());
        processStateAndAction.setCurrentState(new State());
        processStateAndAction.setProcessInstanceFromDb(new ProcessInstance());
        processStateAndAction.setProcessInstanceFromRequest(new ProcessInstance());
        processStateAndAction.setResultantState(new State());

        ArrayList<ProcessStateAndAction> processStateAndActionList = new ArrayList<>();
        processStateAndActionList.add(processStateAndAction);
        new CustomException("Code", "An error occurred");

        workflowUtil.getLatestProcessStateAndAction("42", processStateAndActionList);
        verify(processStateAndAction, atLeast(1)).getProcessInstanceFromRequest();
        verify(processStateAndAction).setAction((Action) any());
        verify(processStateAndAction).setCurrentState((State) any());
        verify(processStateAndAction).setProcessInstanceFromDb((ProcessInstance) any());
        verify(processStateAndAction).setProcessInstanceFromRequest((ProcessInstance) any());
        verify(processStateAndAction).setResultantState((State) any());
        verify(processInstance).getBusinessId();
        verify(processInstance, atLeast(1)).getAuditDetails();
    }


    /**
     * Method under test: {@link WorkflowUtil#getTenantIds(User)}
     */
    @Test
    void testGetTenantIds4() {
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(new ArrayList<>());
        assertTrue(workflowUtil.getTenantIds(user).isEmpty());
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIds(User)}
     */
    @Test
    void testGetTenantIds5() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);
        List<String> actualTenantIds = workflowUtil.getTenantIds(user);
        assertEquals(1, actualTenantIds.size());
        assertNull(actualTenantIds.get(0));
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIds(User)}
     */
    @Test
    void testGetTenantIds6() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());
        User user = mock(User.class);
        when(user.getRoles()).thenReturn(roleList);
        List<String> actualTenantIds = workflowUtil.getTenantIds(user);
        assertEquals(1, actualTenantIds.size());
        assertNull(actualTenantIds.get(0));
        verify(user).getRoles();
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIds(User)}
     */

    @Test
    void testGetTenantIdToBuisnessSevicesMap() {
        assertTrue(workflowUtil.getTenantIdToBuisnessSevicesMap(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToBuisnessSevicesMap(List)}
     */
    @Test
    void testGetTenantIdToBuisnessSevicesMap2() {
        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(new BusinessService());
        Map<String, List<BusinessService>> actualTenantIdToBuisnessSevicesMap = workflowUtil
                .getTenantIdToBuisnessSevicesMap(businessServiceList);
        assertEquals(1, actualTenantIdToBuisnessSevicesMap.size());
        assertEquals(1, actualTenantIdToBuisnessSevicesMap.get(null).size());
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToBuisnessSevicesMap(List)}
     */
    @Test
    void testGetTenantIdToBuisnessSevicesMap3() {
        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(new BusinessService());
        businessServiceList.add(new BusinessService());
        Map<String, List<BusinessService>> actualTenantIdToBuisnessSevicesMap = workflowUtil
                .getTenantIdToBuisnessSevicesMap(businessServiceList);
        assertEquals(1, actualTenantIdToBuisnessSevicesMap.size());
        assertEquals(2, actualTenantIdToBuisnessSevicesMap.get(null).size());
    }

    /**
     * Method under test: {@link WorkflowUtil#getTenantIdToBuisnessSevicesMap(List)}
     */
    @Test
    void testGetTenantIdToBuisnessSevicesMap5() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getTenantId()).thenReturn("42");

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        Map<String, List<BusinessService>> actualTenantIdToBuisnessSevicesMap = workflowUtil
                .getTenantIdToBuisnessSevicesMap(businessServiceList);
        assertEquals(1, actualTenantIdToBuisnessSevicesMap.size());
        assertEquals(1, actualTenantIdToBuisnessSevicesMap.get("42").size());
        verify(businessService, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap() {
        assertTrue(workflowUtil.getStateUuidToTenantIdMap(new ArrayList<>()).isEmpty());
    }


    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap3() {
        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        ArrayList<State> states = new ArrayList<>();
        businessServiceList.add(new BusinessService("42", "01234567-89AB-CDEF-FEDC-BA9876543210", "Business Service",
                "Business", "Get Uri", "Post Uri", 1L, states, new AuditDetails()));
        assertTrue(workflowUtil.getStateUuidToTenantIdMap(businessServiceList).isEmpty());
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap5() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(new ArrayList<>());

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        assertTrue(workflowUtil.getStateUuidToTenantIdMap(businessServiceList).isEmpty());
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap6() {
        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(new State());
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(stateList);

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        Map<String, String> actualStateUuidToTenantIdMap = workflowUtil.getStateUuidToTenantIdMap(businessServiceList);
        assertEquals(1, actualStateUuidToTenantIdMap.size());
        assertNull(actualStateUuidToTenantIdMap.get(null));
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap7() {
        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(new State());
        stateList.add(new State());
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(stateList);

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        businessServiceList.add(businessService);
        Map<String, String> actualStateUuidToTenantIdMap = workflowUtil.getStateUuidToTenantIdMap(businessServiceList);
        assertEquals(1, actualStateUuidToTenantIdMap.size());
        assertNull(actualStateUuidToTenantIdMap.get(null));
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#getStateUuidToTenantIdMap(List)}
     */
    @Test
    void testGetStateUuidToTenantIdMap9() {
        BusinessService businessService = mock(BusinessService.class);
        when(businessService.getStates()).thenReturn(new ArrayList<>());

        ArrayList<BusinessService> businessServiceList = new ArrayList<>();
        ArrayList<State> states = new ArrayList<>();
        businessServiceList.add(new BusinessService("42", "01234567-89AB-CDEF-FEDC-BA9876543210", "Business Service",
                "Business", "Get Uri", "Post Uri", 1L, states, new AuditDetails()));
        businessServiceList.add(businessService);
        assertTrue(workflowUtil.getStateUuidToTenantIdMap(businessServiceList).isEmpty());
        verify(businessService).getStates();
    }

    /**
     * Method under test: {@link WorkflowUtil#replaceSchemaPlaceholder(String, String)}
     */
    @Test
    void testReplaceSchemaPlaceholder() {
        when(workflowConfig.getIsEnvironmentCentralInstance()).thenReturn(true);
        assertEquals("Query", workflowUtil.replaceSchemaPlaceholder("Query", "42"));
        verify(workflowConfig).getIsEnvironmentCentralInstance();
    }

    /**
     * Method under test: {@link WorkflowUtil#replaceSchemaPlaceholder(String, String)}
     */
    @Test
    void testReplaceSchemaPlaceholder2() {
        when(workflowConfig.getIsEnvironmentCentralInstance()).thenReturn(false);
        assertEquals("Query", workflowUtil.replaceSchemaPlaceholder("Query", "42"));
        verify(workflowConfig).getIsEnvironmentCentralInstance();
    }

    /**
     * Method under test: {@link WorkflowUtil#replaceSchemaPlaceholder(String, String)}
     */
    @Test
    void testReplaceSchemaPlaceholder3() {
        when(workflowConfig.getIsEnvironmentCentralInstance()).thenReturn(true);
        assertEquals("", workflowUtil.replaceSchemaPlaceholder("{schema}.", "42"));
        verify(workflowConfig).getIsEnvironmentCentralInstance();
    }

    /**
     * Method under test: {@link WorkflowUtil#replaceSchemaPlaceholder(String, String)}
     */
    @Test
    void testReplaceSchemaPlaceholder5() {
        when(workflowConfig.getIsEnvironmentCentralInstance()).thenThrow(new CustomException(".", "An error occurred"));
        assertThrows(CustomException.class, () -> workflowUtil.replaceSchemaPlaceholder("Query", "42"));
        verify(workflowConfig).getIsEnvironmentCentralInstance();
    }
}

