package org.egov.collection.producer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CollectionProducer.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class CollectionProducerTest {
    @Autowired
    private CollectionProducer collectionProducer;

    @MockBean(name = "customKafkaTemplate")
    private CustomKafkaTemplate<String, Object> customKafkaTemplate;

    @Test
    void testPush() {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>("Topic", "Value");

        when(customKafkaTemplate.send((String) any(), (Object) any())).thenReturn(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1L, 10L, 1L, 3, 3)));
        collectionProducer.push("42", "Topic", "Value");
        verify(customKafkaTemplate).send((String) any(), (Object) any());
    }
}

