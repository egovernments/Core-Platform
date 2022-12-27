package org.egov.demand.consumer.notification;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import org.egov.common.contract.request.RequestInfo;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.model.AuditDetails;
import org.egov.demand.model.Bill;
import org.egov.demand.model.BillDetail;
import org.egov.demand.model.TaxAndPayment;
import org.egov.demand.web.contract.BillRequest;
import org.egov.demand.web.contract.BillRequestV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {NotificationConsumer.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class NotificationConsumerTest {
    @MockBean
    private KafkaTemplate<String, Object> kafkaTemplate;

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
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(new BillRequest());
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen3() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(null);
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen4() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn("Convert Value");
        notificationConsumer.listen(new HashMap<>(), "${kafka.topics.cancel.bill.topic.name}");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen5() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(new BillRequestV2());
        notificationConsumer.listen(new HashMap<>(), "${kafka.topics.cancel.bill.topic.name}");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen6() throws IllegalArgumentException {
        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(null);
        notificationConsumer.listen(new HashMap<>(), "${kafka.topics.cancel.bill.topic.name}");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen7() throws IllegalArgumentException {
        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = new BillRequest(new RequestInfo(), billList);

        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(billRequest);
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen8() throws IllegalArgumentException {
        Bill bill = new Bill();
        bill.addBillDetailsItem(new BillDetail());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);
        BillRequest billRequest = new BillRequest(new RequestInfo(), billList);

        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(billRequest);
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }

    @Test
    void testListen9() throws IllegalArgumentException {
        ArrayList<TaxAndPayment> taxAndPayments = new ArrayList<>();
        ArrayList<BillDetail> billDetails = new ArrayList<>();

        Bill bill = new Bill("42", "42", "Topic", "42 Main St", "jane.doe@example.org", true, true, "Additional Details",
                taxAndPayments, billDetails, "42", new AuditDetails());
        bill.addBillDetailsItem(new BillDetail());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);
        BillRequest billRequest = new BillRequest(new RequestInfo(), billList);

        when(objectMapper.convertValue((Object) any(), (Class<Object>) any())).thenReturn(billRequest);
        notificationConsumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).convertValue((Object) any(), (Class<Object>) any());
    }
}

