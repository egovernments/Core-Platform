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

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.model.AuditDetails;
import org.egov.demand.model.Bill;
import org.egov.demand.model.BillAccountDetail;
import org.egov.demand.model.BillDetail;
import org.egov.demand.model.BillSearchCriteria;
import org.egov.demand.model.BusinessServiceDetail;
import org.egov.demand.repository.querybuilder.BillQueryBuilder;
import org.egov.demand.repository.rowmapper.BillRowMapper;
import org.egov.demand.util.Util;
import org.egov.demand.web.contract.BillRequest;
import org.egov.demand.web.contract.BillResponse;
import org.egov.demand.web.contract.BusinessServiceDetailCriteria;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {BillRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class BillRepositoryTest {
    @MockBean
    private BillQueryBuilder billQueryBuilder;

    @Autowired
    private BillRepository billRepository;

    @MockBean
    private BillRowMapper billRowMapper;

    @MockBean
    private BusinessServiceDetailRepository businessServiceDetailRepository;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private RestTemplate restTemplate;

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
        List<Bill> actualFindBillResult = billRepository.findBill(billSearchCriteria);
        assertSame(objectList, actualFindBillResult);
        assertTrue(actualFindBillResult.isEmpty());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testFindBill3() throws DataAccessException {
        when(billQueryBuilder.getBillQuery((BillSearchCriteria) any(), (List<Object>) any())).thenReturn("{schema}.");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        BillSearchCriteria billSearchCriteria = mock(BillSearchCriteria.class);
        when(billSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(billSearchCriteria).setTenantId((String) any());
        billSearchCriteria.setTenantId("42");
        List<Bill> actualFindBillResult = billRepository.findBill(billSearchCriteria);
        assertSame(objectList, actualFindBillResult);
        assertTrue(actualFindBillResult.isEmpty());
        verify(billQueryBuilder).getBillQuery((BillSearchCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(billSearchCriteria).getTenantId();
        verify(billSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testSaveBill4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        billRepository.saveBill(billRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequest, atLeast(1)).getBills();
        verify(billRequest).getRequestInfo();
    }

    @Test
    void testSaveBill6() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        Bill bill = new Bill();
        bill.addBillDetailsItem(new BillDetail());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        billRepository.saveBill(billRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequest, atLeast(1)).getBills();
        verify(billRequest).getRequestInfo();
    }

    @Test
    void testSaveBillDetails4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        billRepository.saveBillDetails(billRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequest).getBills();
    }

    @Test
    void testSaveBillDetails6() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        Bill bill = new Bill();
        bill.addBillDetailsItem(new BillDetail());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        billRepository.saveBillDetails(billRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequest).getBills();
    }

    @Test
    void testSaveBillDetails7() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("saveBillDeails tempBillDetails:", "An error occurred"));

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        assertThrows(CustomException.class, () -> billRepository.saveBillDetails(billRequest));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(billRequest).getBills();
    }

    @Test
    void testSaveBillAccountDetail() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<BillAccountDetail> billAccountDetails = new ArrayList<>();
        billRepository.saveBillAccountDetail(billAccountDetails, new AuditDetails());
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testSaveBillAccountDetail2() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("saveBillAccountDetail billAccountDetails:", "An error occurred"));
        ArrayList<BillAccountDetail> billAccountDetails = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> billRepository.saveBillAccountDetail(billAccountDetails, new AuditDetails()));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testApportion4() {
        when(businessServiceDetailRepository.getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any())).thenReturn(new ArrayList<>());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        List<Bill> actualApportionResult = billRepository.apportion(billRequest);
        assertSame(billList, actualApportionResult);
        assertEquals(1, actualApportionResult.size());
        verify(businessServiceDetailRepository).getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any());
        verify(billRequest).getBills();
        verify(billRequest).getRequestInfo();
    }

    @Test
    void testApportion7() {
        when(businessServiceDetailRepository.getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any())).thenReturn(new ArrayList<>());

        Bill bill = new Bill();
        bill.addBillDetailsItem(new BillDetail());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(bill);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        List<Bill> actualApportionResult = billRepository.apportion(billRequest);
        assertSame(billList, actualApportionResult);
        assertEquals(1, actualApportionResult.size());
        verify(businessServiceDetailRepository).getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any());
        verify(billRequest).getBills();
        verify(billRequest).getRequestInfo();
    }

    @Test
    void testApportion8() {
        when(businessServiceDetailRepository.getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any())).thenThrow(new CustomException("Code", "An error occurred"));

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        assertThrows(CustomException.class, () -> billRepository.apportion(billRequest));
        verify(businessServiceDetailRepository).getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any());
        verify(billRequest).getBills();
        verify(billRequest).getRequestInfo();
    }

    @Test
    void testApportion9() throws RestClientException {
        BusinessServiceDetail businessServiceDetail = mock(BusinessServiceDetail.class);
        when(businessServiceDetail.getBusinessService()).thenReturn("Business Service");
        when(businessServiceDetail.getCallBackApportionURL()).thenReturn("https://example.org/example");
        when(businessServiceDetail.getCallBackForApportioning()).thenReturn(true);

        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(businessServiceDetail);
        when(businessServiceDetailRepository.getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any())).thenReturn(businessServiceDetailList);
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any()))
                .thenReturn(new BillResponse());

        ArrayList<Bill> billList = new ArrayList<>();
        billList.add(new Bill());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getBills()).thenReturn(billList);
        when(billRequest.getRequestInfo()).thenReturn(new RequestInfo());
        List<Bill> actualApportionResult = billRepository.apportion(billRequest);
        assertSame(billList, actualApportionResult);
        assertEquals(1, actualApportionResult.size());
        verify(businessServiceDetailRepository).getBussinessServiceDetail((RequestInfo) any(),
                (BusinessServiceDetailCriteria) any());
        verify(businessServiceDetail).getCallBackForApportioning();
        verify(businessServiceDetail).getBusinessService();
        verify(businessServiceDetail).getCallBackApportionURL();
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<Object>) any(), (Object[]) any());
        verify(billRequest).getBills();
        verify(billRequest).getRequestInfo();
    }
}

