package org.egov.collection.repository;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.collection.web.contract.UserResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {UserRepository.class, String.class})
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUsersById() throws RestClientException {
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<UserResponse>) any(), (Object[]) any()))
                .thenReturn(new UserResponse());
        ArrayList<Long> userIds = new ArrayList<>();
        assertTrue(userRepository.getUsersById(userIds, new RequestInfo(), "42").isEmpty());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<UserResponse>) any(), (Object[]) any());
    }

    @Test
    void testGetUsersById3() throws RestClientException {
        UserResponse userResponse = mock(UserResponse.class);
        ArrayList<User> userList = new ArrayList<>();
        when(userResponse.getReceiptCreators()).thenReturn(userList);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<UserResponse>) any(), (Object[]) any()))
                .thenReturn(userResponse);
        ArrayList<Long> userIds = new ArrayList<>();
        List<User> actualUsersById = userRepository.getUsersById(userIds, new RequestInfo(), "42");
        assertSame(userList, actualUsersById);
        assertTrue(actualUsersById.isEmpty());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<UserResponse>) any(), (Object[]) any());
        verify(userResponse).getReceiptCreators();
    }
}

