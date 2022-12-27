package org.egov.access.persistence.repository;

import org.egov.access.domain.model.Role;
import org.egov.access.web.contract.role.RoleRequest;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RoleRepository.class})
@ExtendWith(SpringExtension.class)
class RoleRepositoryTest {
    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testCreateRole2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        RoleRequest roleRequest = new RoleRequest();
        ArrayList<Role> roleList = new ArrayList<>();
        roleRequest.setRoles(roleList);
        List<Role> actualCreateRoleResult = roleRepository.createRole(roleRequest);
        assertSame(roleList, actualCreateRoleResult);
        assertTrue(actualCreateRoleResult.isEmpty());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testCreateRole6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRequestInfo(rInfo);
        roleRequest.setRoles(roleList);
        List<Role> actualCreateRoleResult = roleRepository.createRole(roleRequest);
        assertSame(roleList, actualCreateRoleResult);
        assertEquals(1, actualCreateRoleResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testUpdateRole2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        RoleRequest roleRequest = new RoleRequest();
        ArrayList<Role> roleList = new ArrayList<>();
        roleRequest.setRoles(roleList);
        List<Role> actualUpdateRoleResult = roleRepository.updateRole(roleRequest);
        assertSame(roleList, actualUpdateRoleResult);
        assertTrue(actualUpdateRoleResult.isEmpty());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testUpdateRole6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new Role());

        User user = new User();
        user.setId(123L);
        RequestInfo rInfo = ActionRepository.getRInfo();
        rInfo.setUserInfo(user);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRequestInfo(rInfo);
        roleRequest.setRoles(roleList);
        List<Role> actualUpdateRoleResult = roleRepository.updateRole(roleRequest);
        assertSame(roleList, actualUpdateRoleResult);
        assertEquals(1, actualUpdateRoleResult.size());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testCheckRoleNameDuplicationValidationErrors() throws DataAccessException {
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        when(sqlRowSet.getString((String) any())).thenReturn("String");
        when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(namedParameterJdbcTemplate.queryForRowSet((String) any(), (Map<String, Object>) any())).thenReturn(sqlRowSet);
        assertTrue(roleRepository.checkRoleNameDuplicationValidationErrors("Role Name"));
        verify(namedParameterJdbcTemplate).queryForRowSet((String) any(), (Map<String, Object>) any());
        verify(sqlRowSet).next();
        verify(sqlRowSet, atLeast(1)).getString((String) any());
    }

    @Test
    void testGetRInfo() {
        RequestInfo actualRInfo = RoleRepository.getRInfo();
        assertEquals("action", actualRInfo.getAction());
        assertEquals("version", actualRInfo.getVer());
        assertEquals("msgId", actualRInfo.getMsgId());
        assertEquals("key", actualRInfo.getKey());
        assertEquals("did", actualRInfo.getDid());
        assertEquals("a487e887-cafd-41cf-bb8a-2245acbb6c01", actualRInfo.getAuthToken());
        assertEquals("apiId", actualRInfo.getApiId());
    }
}

