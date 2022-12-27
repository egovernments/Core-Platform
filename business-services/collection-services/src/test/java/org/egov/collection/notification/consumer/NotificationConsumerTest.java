package org.egov.collection.notification.consumer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import org.egov.collection.model.Payment;

import org.egov.collection.model.PaymentRequest;

import org.egov.collection.producer.CollectionProducer;
import org.egov.common.contract.request.PlainAccessRequest;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {NotificationConsumer.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class NotificationConsumerTest {
    @MockBean
    private CollectionProducer collectionProducer;

    @Autowired
    private NotificationConsumer notificationConsumer;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testListen() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn("Convert Value");
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen2() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(new PaymentRequest());
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen3() throws IllegalArgumentException {
        RequestInfo requestInfo = new RequestInfo();
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any()))
                .thenReturn(new PaymentRequest(requestInfo, new Payment()));
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen4() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(null);
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen5() throws IllegalArgumentException, RestClientException {
        Payment payment = new Payment();
        payment.setTenantId("42");
        PaymentRequest paymentRequest = new PaymentRequest(new RequestInfo(), payment);

        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(paymentRequest);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any())).thenReturn(new HashMap<>());
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any());
    }

    @Test
    void testListen6() throws IllegalArgumentException, RestClientException {
        Payment payment = new Payment();
        payment.setTenantId("42");
        PlainAccessRequest plainAccessRequest = new PlainAccessRequest();
        PaymentRequest paymentRequest = new PaymentRequest(
                new RequestInfo("42", "\\.", 1L, "\\.", "\\.", "\\.", "42", "ABC123", "42", plainAccessRequest, new User()),
                payment);

        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(paymentRequest);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any())).thenReturn(new HashMap<>());
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Map<Object, Object>>) any(),
                (Object[]) any());
    }
}

