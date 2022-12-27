package org.egov.access.persistence.repository;

import org.egov.access.domain.model.Action;
import org.egov.access.web.contract.action.ActionRequest;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActionRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class ActionRepositoryTest {
    @Autowired
    private ActionRepository actionRepository;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testCreateAction2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ActionRequest actionRequest = new ActionRequest();
        ArrayList<Action> actionList = new ArrayList<>();
        actionRequest.setActions(actionList);
        List<Action> actualCreateActionResult = actionRepository.createAction(actionRequest);
        assertSame(actionList, actualCreateActionResult);
        assertTrue(actualCreateActionResult.isEmpty());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testCreateAction6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setRequestInfo(rInfo);
        actionRequest.setActions(actionList);
        List<Action> actualCreateActionResult = actionRepository.createAction(actionRequest);
        assertSame(actionList, actualCreateActionResult);
        assertEquals(1, actualCreateActionResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testCreateAction7() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Action> actionList = new ArrayList<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date createdDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actionList.add(new Action(123L, "Create Action Repository::", "https://example.org/example",
                "Create Action Repository::", 10, "Create Action Repository::", "Create Action Repository::", true,
                "Create Action Repository::", "42", createdDate, 59L,
                Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()), 59L, "Create Action Repository::",
                "https://example.org/example", "Create Action Repository::", "Create Action Repository::"));

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setRequestInfo(rInfo);
        actionRequest.setActions(actionList);
        List<Action> actualCreateActionResult = actionRepository.createAction(actionRequest);
        assertSame(actionList, actualCreateActionResult);
        assertEquals(1, actualCreateActionResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testUpdateAction2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ActionRequest actionRequest = new ActionRequest();
        ArrayList<Action> actionList = new ArrayList<>();
        actionRequest.setActions(actionList);
        List<Action> actualUpdateActionResult = actionRepository.updateAction(actionRequest);
        assertSame(actionList, actualUpdateActionResult);
        assertTrue(actualUpdateActionResult.isEmpty());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testUpdateAction6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setRequestInfo(rInfo);
        actionRequest.setActions(actionList);
        List<Action> actualUpdateActionResult = actionRepository.updateAction(actionRequest);
        assertSame(actionList, actualUpdateActionResult);
        assertEquals(1, actualUpdateActionResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testUpdateAction7() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Action> actionList = new ArrayList<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date createdDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actionList.add(new Action(123L, "update Action Repository::", "https://example.org/example",
                "update Action Repository::", 10, "update Action Repository::", "update Action Repository::", true,
                "update Action Repository::", "42", createdDate, 59L,
                Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()), 59L, "update Action Repository::",
                "https://example.org/example", "update Action Repository::", "update Action Repository::"));

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setRequestInfo(rInfo);
        actionRequest.setActions(actionList);
        List<Action> actualUpdateActionResult = actionRepository.updateAction(actionRequest);
        assertSame(actionList, actualUpdateActionResult);
        assertEquals(1, actualUpdateActionResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testCheckActionNameExit() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getLong((String) any())).thenReturn(1L);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any())).thenReturn(sqlRowSet);
        assertTrue(actionRepository.checkActionNameExit("Action Name"));
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet).next();
        verify(sqlRowSet).getLong((String) any());
    }

    @Test
    void testCheckCombinationOfUrlAndqueryparamsExist() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getLong((String) any())).thenReturn(1L);
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any()))
                .thenReturn(sqlRowSet);
        assertTrue(actionRepository.checkCombinationOfUrlAndqueryparamsExist("https://example.org/example",
                "https://example.org/example"));
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet).next();
        verify(sqlRowSet).getLong((String) any());
    }

    @Test
    void testGetAllActionsBasedOnRoles() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        assertEquals(objectList, actionRepository.getAllActionsBasedOnRoles(new ActionRequest()).getModules());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllActionsBasedOnRoles3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        RequestInfo requestInfo = ActionRepository.getRInfo();
        ArrayList<String> roleCodes = new ArrayList<>();
        ArrayList<Long> featureIds = new ArrayList<>();
        assertEquals(objectList,
                actionRepository
                        .getAllActionsBasedOnRoles(new ActionRequest(requestInfo, roleCodes, featureIds, "42", true,
                                new ArrayList<>(), "code", "https://example.org/example", "code", "code"))
                        .getModules());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllActions() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(actionRepository.getAllActions(new ActionRequest()).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllActions3() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        RequestInfo requestInfo = ActionRepository.getRInfo();
        ArrayList<String> roleCodes = new ArrayList<>();
        ArrayList<Long> featureIds = new ArrayList<>();
        assertTrue(
                actionRepository
                        .getAllActions(new ActionRequest(requestInfo, roleCodes, featureIds, "42", true, new ArrayList<>(),
                                "code", "https://example.org/example", "code", "code"))
                        .isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetRInfo() {
        RequestInfo actualRInfo = ActionRepository.getRInfo();
        assertEquals("action", actualRInfo.getAction());
        assertEquals("version", actualRInfo.getVer());
        assertEquals("msgId", actualRInfo.getMsgId());
        assertEquals("key", actualRInfo.getKey());
        assertEquals("did", actualRInfo.getDid());
        assertEquals("a487e887-cafd-41cf-bb8a-2245acbb6c01", actualRInfo.getAuthToken());
        assertEquals("apiId", actualRInfo.getApiId());
    }
}

