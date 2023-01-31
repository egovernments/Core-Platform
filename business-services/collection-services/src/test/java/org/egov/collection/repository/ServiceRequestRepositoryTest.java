package org.egov.collection.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.egov.tracer.model.ServiceCallException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ServiceRequestRepository.class})
@ExtendWith(SpringExtension.class)
class ServiceRequestRepositoryTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Test
    void testFetchResult() throws RestClientException {
        HashMap<Object, Object> objectObjectMap = new HashMap<>();
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any())).thenReturn(objectObjectMap);
        Object actualFetchResultResult = serviceRequestRepository.fetchResult(new StringBuilder(), "Request");
        assertSame(objectObjectMap, actualFetchResultResult);
        assertTrue(((Map<Object, Object>) actualFetchResultResult).isEmpty());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any());
    }

    @Test
    void testFetchGetResult() throws RestClientException {
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        assertNull(serviceRequestRepository.fetchGetResult("Uri"));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any());
    }

    @Test
    void testFetchGetResult2() throws RestClientException {
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(null);
        assertThrows(ServiceCallException.class, () -> serviceRequestRepository.fetchGetResult("Uri"));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(),
                (Class<Object>) any(), (Object[]) any());
    }

    @Test
    void testFetchGetResult3() throws RestClientException {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("Body");
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(responseEntity);
        assertEquals("Body", serviceRequestRepository.fetchGetResult("Uri"));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(),
                (Class<Object>) any(), (Object[]) any());
        verify(responseEntity).getBody();
    }

    @Test
    void testFetchGetResult4() throws RestClientException {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenThrow(new HttpClientErrorException(HttpStatus.CONTINUE));
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(responseEntity);
        assertThrows(ServiceCallException.class,
                () -> serviceRequestRepository.fetchGetResult("org.egov.collection.repository.ServiceRequestRepository"));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(),
                (Class<Object>) any(), (Object[]) any());
        verify(responseEntity).getBody();
    }

    @Test
    void testFetchGetResult5() throws RestClientException {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenThrow(new ServiceCallException("An error occurred"));
        when(restTemplate.exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(), (Class<Object>) any(),
                (Object[]) any())).thenReturn(responseEntity);
        new HttpClientErrorException(HttpStatus.CONTINUE);
        assertThrows(ServiceCallException.class,
                () -> serviceRequestRepository.fetchGetResult("org.egov.collection.repository.ServiceRequestRepository"));
        verify(restTemplate).exchange((String) any(), (HttpMethod) any(), (HttpEntity<Object>) any(),
                (Class<Object>) any(), (Object[]) any());
        verify(responseEntity).getBody();
    }
}

