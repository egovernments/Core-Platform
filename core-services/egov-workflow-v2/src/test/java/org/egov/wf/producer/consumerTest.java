package org.egov.wf.producer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {consumer.class})
@ExtendWith(SpringExtension.class)
class consumerTest {
    @Autowired
    private consumer consumer;

    @MockBean
    private ObjectMapper objectMapper;

    /**
     * Method under test: {@link consumer#listen(HashMap, String)}
     */
    @Test
    void testListen() throws JsonProcessingException {
        when(objectMapper.writeValueAsString((Object) any())).thenReturn("42");
        consumer.listen(new HashMap<>(), "Topic");
        verify(objectMapper).writeValueAsString((Object) any());
    }
}

