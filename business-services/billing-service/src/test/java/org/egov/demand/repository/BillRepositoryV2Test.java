package org.egov.demand.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.model.AuditDetails;
import org.egov.demand.model.BillAccountDetailV2;
import org.egov.demand.model.BillDetailV2;
import org.egov.demand.model.BillSearchCriteria;
import org.egov.demand.model.BillV2;
import org.egov.demand.model.UpdateBillCriteria;
import org.egov.demand.repository.querybuilder.BillQueryBuilder;
import org.egov.demand.repository.rowmapper.BillRowMapperV2;
import org.egov.demand.util.Util;
import org.egov.demand.web.contract.BillRequestV2;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BillRepositoryV2.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class BillRepositoryV2Test {
    @MockBean
    private BillQueryBuilder billQueryBuilder;

    @Autowired
    private BillRepositoryV2 billRepositoryV2;

    @MockBean
    private BillRowMapperV2 billRowMapperV2;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private Util util;

    @Test
    void testFindBill2() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("Bill Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);

        BillSearchCriteria billSearchCriteria = new BillSearchCriteria();
        billSearchCriteria.setTenantId("42");
        List<BillV2> actualFindBillResult = billRepositoryV2.findBill(billSearchCriteria);
        assertSame(objectList, actualFindBillResult);
        assertTrue(actualFindBillResult.isEmpty());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testFindBill3() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("Bill Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        BillSearchCriteria billSearchCriteria = mock(BillSearchCriteria.class);
        when(billSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(billSearchCriteria).setTenantId((String) any());
        billSearchCriteria.setTenantId("42");
        List<BillV2> actualFindBillResult = billRepositoryV2.findBill(billSearchCriteria);
        assertSame(objectList, actualFindBillResult);
        assertTrue(actualFindBillResult.isEmpty());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(billSearchCriteria).getTenantId();
        verify(billSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testFindBill4() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("{schema}.");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        BillSearchCriteria billSearchCriteria = mock(BillSearchCriteria.class);
        when(billSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(billSearchCriteria).setTenantId((String) any());
        billSearchCriteria.setTenantId("42");
        List<BillV2> actualFindBillResult = billRepositoryV2.findBill(billSearchCriteria);
        assertSame(objectList, actualFindBillResult);
        assertTrue(actualFindBillResult.isEmpty());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(billSearchCriteria).getTenantId();
        verify(billSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testSaveBill8() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        BillV2 billV2 = new BillV2();
        billV2.setTenantId("42");

        ArrayList<BillV2> billV2List = new ArrayList<>();
        billV2List.add(billV2);
        BillRequestV2 billRequestV2 = mock(BillRequestV2.class);
        when(billRequestV2.getBills()).thenReturn(billV2List);
        assertThrows(CustomException.class, () -> billRepositoryV2.saveBill(billRequestV2));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequestV2, atLeast(1)).getBills();
    }

    @Test
    void testSaveBill9() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        BillV2 billV2 = new BillV2();
        billV2.setTenantId("{schema}.");

        ArrayList<BillV2> billV2List = new ArrayList<>();
        billV2List.add(billV2);
        BillRequestV2 billRequestV2 = mock(BillRequestV2.class);
        when(billRequestV2.getBills()).thenReturn(billV2List);
        assertThrows(CustomException.class, () -> billRepositoryV2.saveBill(billRequestV2));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequestV2, atLeast(1)).getBills();
    }

    @Test
    void testSaveBillDetails12() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        ArrayList<BillDetailV2> billDetailV2List = new ArrayList<>();
        BigDecimal amount = BigDecimal.valueOf(42L);
        BigDecimal amountPaid = BigDecimal.valueOf(42L);
        billDetailV2List.add(new BillDetailV2("42", "42", "42", "42", 1L, amount, amountPaid, 1L, 1L,
                "Additional Details", new ArrayList<>()));

        BillV2 billV2 = new BillV2();
        billV2.setTenantId("42");
        billV2.setBillDetails(billDetailV2List);

        ArrayList<BillV2> billV2List = new ArrayList<>();
        billV2List.add(billV2);
        BillRequestV2 billRequestV2 = mock(BillRequestV2.class);
        when(billRequestV2.getBills()).thenReturn(billV2List);
        assertThrows(CustomException.class, () -> billRepositoryV2.saveBillDetails(billRequestV2));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequestV2, atLeast(1)).getBills();
    }

    @Test
    void testSaveBillAccountDetail4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        BillAccountDetailV2 billAccountDetailV2 = new BillAccountDetailV2();
        billAccountDetailV2.setTenantId("42");

        ArrayList<BillAccountDetailV2> billAccountDetailV2List = new ArrayList<>();
        billAccountDetailV2List.add(billAccountDetailV2);
        billRepositoryV2.saveBillAccountDetail(billAccountDetailV2List, new AuditDetails());
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testSaveBillAccountDetail5() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        BillAccountDetailV2 billAccountDetailV2 = new BillAccountDetailV2();
        billAccountDetailV2.setTenantId("{schema}.");

        ArrayList<BillAccountDetailV2> billAccountDetailV2List = new ArrayList<>();
        billAccountDetailV2List.add(billAccountDetailV2);
        billRepositoryV2.saveBillAccountDetail(billAccountDetailV2List, new AuditDetails());
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testSaveBillAccountDetail6() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        BillAccountDetailV2 billAccountDetailV2 = new BillAccountDetailV2();
        billAccountDetailV2.setTenantId("42");

        ArrayList<BillAccountDetailV2> billAccountDetailV2List = new ArrayList<>();
        billAccountDetailV2List.add(billAccountDetailV2);
        assertThrows(CustomException.class,
                () -> billRepositoryV2.saveBillAccountDetail(billAccountDetailV2List, new AuditDetails()));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testUpdateBillStatus() {
        assertEquals(0, billRepositoryV2.updateBillStatus(new UpdateBillCriteria()).intValue());
    }

    @Test
    void testUpdateBillStatus3() {
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getConsumerCodes()).thenReturn(new HashSet<>());
        assertEquals(0, billRepositoryV2.updateBillStatus(updateBillCriteria).intValue());
        verify(updateBillCriteria).getConsumerCodes();
    }

    @Test
    void testUpdateBillStatus4() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("Bill Query");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getBusinessService()).thenReturn("Business Service");
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getConsumerCodes()).thenReturn(stringSet);
        assertThrows(CustomException.class, () -> billRepositoryV2.updateBillStatus(updateBillCriteria));
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(updateBillCriteria).getBusinessService();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getConsumerCodes();
    }

    @Test
    void testUpdateBillStatus5() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("Bill Query");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getBusinessService()).thenReturn("Business Service");
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getConsumerCodes()).thenReturn(stringSet);
        assertEquals(0, billRepositoryV2.updateBillStatus(updateBillCriteria).intValue());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(updateBillCriteria).getBusinessService();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getConsumerCodes();
    }

    @Test
    void testUpdateBillStatus6() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("{schema}.");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getBusinessService()).thenReturn("Business Service");
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getConsumerCodes()).thenReturn(stringSet);
        assertThrows(CustomException.class, () -> billRepositoryV2.updateBillStatus(updateBillCriteria));
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(updateBillCriteria).getBusinessService();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getConsumerCodes();
    }

    @Test
    void testUpdateBillStatus7() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("Bill Query");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getBusinessService()).thenReturn("Business Service");
        when(updateBillCriteria.getTenantId()).thenReturn("{schema}.");
        when(updateBillCriteria.getConsumerCodes()).thenReturn(stringSet);
        assertThrows(CustomException.class, () -> billRepositoryV2.updateBillStatus(updateBillCriteria));
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(updateBillCriteria).getBusinessService();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getConsumerCodes();
    }
}

