package org.egov.collection.service.v1;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.egov.collection.model.v1.ReceiptSearchCriteria_v1;
import org.egov.collection.model.v1.Receipt_v1;
import org.egov.collection.repository.querybuilder.v1.CollectionQueryBuilder_v1;
import org.egov.collection.repository.querybuilder.v1.CollectionResultSetExtractor_v1;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CollectionService_v1.class})
@ExtendWith(SpringExtension.class)
class CollectionService_v1Test {
    @MockBean
    private CollectionQueryBuilder_v1 collectionQueryBuilder_v1;

    @MockBean
    private CollectionResultSetExtractor_v1 collectionResultSetExtractor_v1;

    @Autowired
    private CollectionService_v1 collectionService_v1;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void testFetchReceipts() throws DataAccessException {
        when(collectionQueryBuilder_v1.getReceiptSearchQuery((ReceiptSearchCriteria_v1) any(), (Map<String, Object>) any()))
                .thenReturn("Receipt Search Query");
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenThrow(new CustomException("Code", "An error occurred"));
        assertThrows(CustomException.class, () -> collectionService_v1.fetchReceipts(new ReceiptSearchCriteria_v1()));
        verify(collectionQueryBuilder_v1).getReceiptSearchQuery((ReceiptSearchCriteria_v1) any(),
                (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
    }

    @Test
    void testFetchReceipts2() throws DataAccessException {
        when(collectionQueryBuilder_v1.getReceiptSearchQuery((ReceiptSearchCriteria_v1) any(),
                (Map<String, Object>) any())).thenReturn("Receipt Search Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList);
        List<Receipt_v1> actualFetchReceiptsResult = collectionService_v1.fetchReceipts(new ReceiptSearchCriteria_v1());
        assertSame(objectList, actualFetchReceiptsResult);
        assertTrue(actualFetchReceiptsResult.isEmpty());
        verify(collectionQueryBuilder_v1).getReceiptSearchQuery((ReceiptSearchCriteria_v1) any(),
                (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
    }
}

