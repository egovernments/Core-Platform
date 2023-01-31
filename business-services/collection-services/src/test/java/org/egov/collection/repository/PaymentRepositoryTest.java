package org.egov.collection.repository;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.collection.model.AuditDetails;

import org.egov.collection.model.Payment;
import org.egov.collection.model.PaymentDetail;

import org.egov.collection.model.PaymentSearchCriteria;
import org.egov.collection.model.enums.InstrumentStatusEnum;
import org.egov.collection.model.enums.PaymentModeEnum;
import org.egov.collection.model.enums.PaymentStatusEnum;

import org.egov.collection.repository.querybuilder.PaymentQueryBuilder;
import org.egov.collection.repository.rowmapper.BillRowMapper;
import org.egov.collection.repository.rowmapper.PaymentRowMapper;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class PaymentRepositoryTest {
    @MockBean
    private BillRowMapper billRowMapper;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private PaymentQueryBuilder paymentQueryBuilder;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentRowMapper paymentRowMapper;

    @Test
    void testSavePayment() {
        assertThrows(CustomException.class, () -> paymentRepository.savePayment(new Payment()));
    }

    @Test
    void testSavePayment2() {
        Payment payment = new Payment();
        payment.addpaymentDetailsItem(new PaymentDetail());
        assertThrows(CustomException.class, () -> paymentRepository.savePayment(payment));
    }

    @Test
    void testSavePayment3() {
        Payment payment = new Payment();
        payment.setPaymentDetails(new ArrayList<>());
        assertThrows(CustomException.class, () -> paymentRepository.savePayment(payment));
    }

    @Test
    void testSavePayment4() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        Payment payment = mock(Payment.class);
        when(payment.getAuditDetails()).thenReturn(new AuditDetails());
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getMobileNumber()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerId()).thenReturn("42");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getPaymentStatus()).thenReturn(PaymentStatusEnum.NEW);
        when(payment.getInstrumentDate()).thenReturn(1L);
        when(payment.getInstrumentNumber()).thenReturn("42");
        when(payment.getInstrumentStatus()).thenReturn(InstrumentStatusEnum.APPROVED);
        when(payment.getTransactionDate()).thenReturn(1L);
        when(payment.getId()).thenReturn("42");
        when(payment.getTransactionNumber()).thenReturn("42");
        when(payment.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getTotalDue()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        doNothing().when(payment).setPaymentDetails((List<PaymentDetail>) any());
        payment.setPaymentDetails(paymentDetailList);
        paymentRepository.savePayment(payment);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(payment).getAdditionalDetails();
        verify(payment).getInstrumentDate();
        verify(payment).getTransactionDate();
        verify(payment).getId();
        verify(payment).getIfscCode();
        verify(payment).getInstrumentNumber();
        verify(payment).getMobileNumber();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerId();
        verify(payment).getPayerName();
        verify(payment, atLeast(1)).getTenantId();
        verify(payment).getTransactionNumber();
        verify(payment).getTotalAmountPaid();
        verify(payment).getTotalDue();
        verify(payment).getPaymentDetails();
        verify(payment, atLeast(1)).getAuditDetails();
        verify(payment).getInstrumentStatus();
        verify(payment).getPaymentMode();
        verify(payment).getPaymentStatus();
        verify(payment).setPaymentDetails((List<PaymentDetail>) any());
    }

    @Test
    void testSavePayment5() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        Payment payment = mock(Payment.class);
        when(payment.getAuditDetails()).thenReturn(new AuditDetails());
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getMobileNumber()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerId()).thenReturn("42");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getPaymentStatus()).thenReturn(PaymentStatusEnum.NEW);
        when(payment.getInstrumentDate()).thenReturn(1L);
        when(payment.getInstrumentNumber()).thenReturn("42");
        when(payment.getInstrumentStatus()).thenReturn(InstrumentStatusEnum.APPROVED);
        when(payment.getTransactionDate()).thenReturn(1L);
        when(payment.getId()).thenReturn("42");
        when(payment.getTransactionNumber()).thenReturn("42");
        when(payment.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getTotalDue()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        doNothing().when(payment).setPaymentDetails((List<PaymentDetail>) any());
        payment.setPaymentDetails(paymentDetailList);
        assertThrows(CustomException.class, () -> paymentRepository.savePayment(payment));
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(payment).getAdditionalDetails();
        verify(payment).getInstrumentDate();
        verify(payment).getTransactionDate();
        verify(payment).getId();
        verify(payment).getIfscCode();
        verify(payment).getInstrumentNumber();
        verify(payment).getMobileNumber();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerId();
        verify(payment).getPayerName();
        verify(payment, atLeast(1)).getTenantId();
        verify(payment).getTransactionNumber();
        verify(payment).getTotalAmountPaid();
        verify(payment).getTotalDue();
        verify(payment).getPaymentDetails();
        verify(payment, atLeast(1)).getAuditDetails();
        verify(payment).getInstrumentStatus();
        verify(payment).getPaymentMode();
        verify(payment).getPaymentStatus();
        verify(payment).setPaymentDetails((List<PaymentDetail>) any());
    }

    @Test
    void testSavePayment6() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        Payment payment = mock(Payment.class);
        when(payment.getAuditDetails()).thenReturn(null);
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getMobileNumber()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerId()).thenReturn("42");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getPaymentStatus()).thenReturn(PaymentStatusEnum.NEW);
        when(payment.getInstrumentDate()).thenReturn(1L);
        when(payment.getInstrumentNumber()).thenReturn("42");
        when(payment.getInstrumentStatus()).thenReturn(InstrumentStatusEnum.APPROVED);
        when(payment.getTransactionDate()).thenReturn(1L);
        when(payment.getId()).thenReturn("42");
        when(payment.getTransactionNumber()).thenReturn("42");
        when(payment.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getTotalDue()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        doNothing().when(payment).setPaymentDetails((List<PaymentDetail>) any());
        payment.setPaymentDetails(paymentDetailList);
        assertThrows(CustomException.class, () -> paymentRepository.savePayment(payment));
        verify(payment).getAdditionalDetails();
        verify(payment).getInstrumentDate();
        verify(payment).getTransactionDate();
        verify(payment).getId();
        verify(payment).getIfscCode();
        verify(payment).getInstrumentNumber();
        verify(payment).getMobileNumber();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerId();
        verify(payment).getPayerName();
        verify(payment, atLeast(1)).getTenantId();
        verify(payment).getTransactionNumber();
        verify(payment).getTotalAmountPaid();
        verify(payment).getTotalDue();
        verify(payment).getPaymentDetails();
        verify(payment).getAuditDetails();
        verify(payment).getInstrumentStatus();
        verify(payment).getPaymentMode();
        verify(payment).getPaymentStatus();
        verify(payment).setPaymentDetails((List<PaymentDetail>) any());
    }

    @Test
    void testSavePayment7() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        Payment payment = mock(Payment.class);
        when(payment.getAuditDetails()).thenReturn(new AuditDetails());
        when(payment.getAdditionalDetails()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getMobileNumber()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerId()).thenReturn("42");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getPaymentStatus()).thenReturn(PaymentStatusEnum.NEW);
        when(payment.getInstrumentDate()).thenReturn(1L);
        when(payment.getInstrumentNumber()).thenReturn("42");
        when(payment.getInstrumentStatus()).thenReturn(InstrumentStatusEnum.APPROVED);
        when(payment.getTransactionDate()).thenReturn(1L);
        when(payment.getId()).thenReturn("42");
        when(payment.getTransactionNumber()).thenReturn("42");
        when(payment.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getTotalDue()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        doNothing().when(payment).setPaymentDetails((List<PaymentDetail>) any());
        payment.setPaymentDetails(paymentDetailList);
        paymentRepository.savePayment(payment);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(payment).getAdditionalDetails();
        verify(payment).getInstrumentDate();
        verify(payment).getTransactionDate();
        verify(payment).getId();
        verify(payment).getIfscCode();
        verify(payment).getInstrumentNumber();
        verify(payment).getMobileNumber();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerId();
        verify(payment).getPayerName();
        verify(payment, atLeast(1)).getTenantId();
        verify(payment).getTransactionNumber();
        verify(payment).getTotalAmountPaid();
        verify(payment).getTotalDue();
        verify(payment).getPaymentDetails();
        verify(payment, atLeast(1)).getAuditDetails();
        verify(payment).getInstrumentStatus();
        verify(payment).getPaymentMode();
        verify(payment).getPaymentStatus();
        verify(payment).setPaymentDetails((List<PaymentDetail>) any());
    }

    @Test
    void testSavePayment8() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        Payment payment = mock(Payment.class);
        when(payment.getAuditDetails()).thenReturn(new AuditDetails());
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getMobileNumber()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerId()).thenReturn("42");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getPaymentStatus()).thenReturn(PaymentStatusEnum.NEW);
        when(payment.getInstrumentDate()).thenReturn(1L);
        when(payment.getInstrumentNumber()).thenReturn("42");
        when(payment.getInstrumentStatus()).thenReturn(InstrumentStatusEnum.APPROVED);
        when(payment.getTransactionDate()).thenReturn(1L);
        when(payment.getId()).thenReturn("42");
        when(payment.getTransactionNumber()).thenReturn("42");
        when(payment.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getTotalDue()).thenReturn(BigDecimal.valueOf(1L));
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        when(payment.getTenantId()).thenReturn("{schema}.");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        doNothing().when(payment).setPaymentDetails((List<PaymentDetail>) any());
        payment.setPaymentDetails(paymentDetailList);
        paymentRepository.savePayment(payment);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(payment).getAdditionalDetails();
        verify(payment).getInstrumentDate();
        verify(payment).getTransactionDate();
        verify(payment).getId();
        verify(payment).getIfscCode();
        verify(payment).getInstrumentNumber();
        verify(payment).getMobileNumber();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerId();
        verify(payment).getPayerName();
        verify(payment, atLeast(1)).getTenantId();
        verify(payment).getTransactionNumber();
        verify(payment).getTotalAmountPaid();
        verify(payment).getTotalDue();
        verify(payment).getPaymentDetails();
        verify(payment, atLeast(1)).getAuditDetails();
        verify(payment).getInstrumentStatus();
        verify(payment).getPaymentMode();
        verify(payment).getPaymentStatus();
        verify(payment).setPaymentDetails((List<PaymentDetail>) any());
    }

    @Test
    void testFetchPayments2() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        assertTrue(paymentRepository.fetchPayments(paymentSearchCriteria).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPayments3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenThrow(new CustomException("{schema}.", "An error occurred"));
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any()))
                .thenReturn("Payment Search Query");
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        assertThrows(CustomException.class, () -> paymentRepository.fetchPayments(paymentSearchCriteria));
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
        verify(paymentQueryBuilder).getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPayments4() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        ArrayList<Object> objectList1 = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList1);
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any()))
                .thenReturn("Payment Search Query");
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        List<Payment> actualFetchPaymentsResult = paymentRepository.fetchPayments(paymentSearchCriteria);
        assertSame(objectList1, actualFetchPaymentsResult);
        assertTrue(actualFetchPaymentsResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
        verify(paymentQueryBuilder).getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPayments5() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        ArrayList<Object> objectList1 = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList1);
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any()))
                .thenReturn("Payment Search Query");
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");
        PaymentSearchCriteria paymentSearchCriteria = mock(PaymentSearchCriteria.class);
        when(paymentSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(paymentSearchCriteria).setTenantId((String) any());
        paymentSearchCriteria.setTenantId("42");
        List<Payment> actualFetchPaymentsResult = paymentRepository.fetchPayments(paymentSearchCriteria);
        assertSame(objectList1, actualFetchPaymentsResult);
        assertTrue(actualFetchPaymentsResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
        verify(paymentQueryBuilder).getPaymentSearchQuery((List<String>) any(), (Map<String, Object>) any());
        verify(paymentSearchCriteria, atLeast(1)).getTenantId();
        verify(paymentSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testGetPaymentsCount() throws DataAccessException {
        when(
                namedParameterJdbcTemplate.queryForObject((String) any(), (Map<String, Object>) any(), (Class<Object>) any()))
                .thenReturn("Query For Object");
        when(paymentQueryBuilder.getPaymentCountQuery((String) any(), (String) any(), (Map<String, Object>) any()))
                .thenThrow(new CustomException("Code", "An error occurred"));
        assertThrows(CustomException.class, () -> paymentRepository.getPaymentsCount("42", "Business Service"));
        verify(paymentQueryBuilder).getPaymentCountQuery((String) any(), (String) any(), (Map<String, Object>) any());
    }

    @Test
    void testGetPaymentsCount2() throws DataAccessException {
        when(
                namedParameterJdbcTemplate.queryForObject((String) any(), (Map<String, Object>) any(), (Class<Object>) any()))
                .thenReturn(1L);
        when(paymentQueryBuilder.getPaymentCountQuery((String) any(), (String) any(), (Map<String, Object>) any()))
                .thenReturn("3");
        assertEquals(1L, paymentRepository.getPaymentsCount("42", "Business Service").longValue());
        verify(namedParameterJdbcTemplate).queryForObject((String) any(), (Map<String, Object>) any(),
                (Class<Object>) any());
        verify(paymentQueryBuilder).getPaymentCountQuery((String) any(), (String) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPaymentsForPlainSearch2() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList);
        when(paymentQueryBuilder.getPaymentSearchQueryForPlainSearch((PaymentSearchCriteria) any(),
                (Map<String, Object>) any())).thenReturn("Payment Search Query For Plain Search");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        List<Payment> actualFetchPaymentsForPlainSearchResult = paymentRepository
                .fetchPaymentsForPlainSearch(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentsForPlainSearchResult);
        assertTrue(actualFetchPaymentsForPlainSearchResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
        verify(paymentQueryBuilder).getPaymentSearchQueryForPlainSearch((PaymentSearchCriteria) any(),
                (Map<String, Object>) any());
    }

    @Test
    void testFetchPaymentsForPlainSearch3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList);
        when(paymentQueryBuilder.getPaymentSearchQueryForPlainSearch((PaymentSearchCriteria) any(),
                (Map<String, Object>) any())).thenReturn("Payment Search Query For Plain Search");
        PaymentSearchCriteria paymentSearchCriteria = mock(PaymentSearchCriteria.class);
        when(paymentSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(paymentSearchCriteria).setTenantId((String) any());
        paymentSearchCriteria.setTenantId("42");
        List<Payment> actualFetchPaymentsForPlainSearchResult = paymentRepository
                .fetchPaymentsForPlainSearch(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentsForPlainSearchResult);
        assertTrue(actualFetchPaymentsForPlainSearchResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(),
                (ResultSetExtractor<Object>) any());
        verify(paymentQueryBuilder).getPaymentSearchQueryForPlainSearch((PaymentSearchCriteria) any(),
                (Map<String, Object>) any());
        verify(paymentSearchCriteria).getTenantId();
        verify(paymentSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testUpdateStatus() {
        assertThrows(CustomException.class, () -> paymentRepository.updateStatus(new ArrayList<>()));
    }

    @Test
    void testUpdateStatus2() {
        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment());
        assertThrows(CustomException.class, () -> paymentRepository.updateStatus(paymentList));
    }

    @Test
    void testUpdateStatus3() {
        Payment payment = new Payment();
        payment.setInstrumentStatus(InstrumentStatusEnum.APPROVED);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updateStatus(paymentList));
    }

    @Test
    void testUpdateStatus4() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setInstrumentStatus(InstrumentStatusEnum.APPROVED);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updateStatus(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateStatus5() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "{schema}.", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L,
                "42", InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(),
                "Paid By", "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setInstrumentStatus(InstrumentStatusEnum.APPROVED);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updateStatus(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateStatus6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", null, additionalDetails, new ArrayList<>(), "Paid By", "42",
                "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setInstrumentStatus(InstrumentStatusEnum.APPROVED);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updateStatus(paymentList));
    }

    @Test
    void testUpdateStatus7() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setInstrumentStatus(InstrumentStatusEnum.APPROVED);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updateStatus(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment() {
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(new ArrayList<>()));
    }

    @Test
    void testUpdatePayment2() {
        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment());
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(paymentList));
    }

    @Test
    void testUpdatePayment3() {
        Payment payment = new Payment();
        payment.setAuditDetails(new AuditDetails());

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(paymentList));
    }

    @Test
    void testUpdatePayment4() {
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");

        Payment payment = new Payment();
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(paymentList));
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment5() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails1 = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "id", auditDetails1, additionalDetails, new ArrayList<>(), "id", "42", "id",
                "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updatePayment(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails1 = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "{schema}.", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L,
                "42", InstrumentStatusEnum.APPROVED, "id", auditDetails1, additionalDetails, new ArrayList<>(), "id", "42",
                "id", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updatePayment(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment7() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails1 = new AuditDetails();
        NullNode additionalDetails = NullNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "id", auditDetails1, additionalDetails, new ArrayList<>(), "id", "42", "id",
                "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentRepository.updatePayment(paymentList);
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment8() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");

        ArrayList<PaymentDetail> paymentDetailList = new ArrayList<>();
        paymentDetailList.add(new PaymentDetail());
        BigDecimal totalDue = BigDecimal.valueOf(1L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(1L);
        AuditDetails auditDetails1 = new AuditDetails();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "id", auditDetails1, MissingNode.getInstance(), paymentDetailList, "id", "42",
                "id", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(paymentList));
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testUpdatePayment9() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        Payment payment = mock(Payment.class);
        when(payment.getTenantId()).thenThrow(new CustomException("id", "An error occurred"));
        when(payment.getPaymentDetails()).thenThrow(new CustomException("id", "An error occurred"));
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getId()).thenReturn("42");
        when(payment.getPaidBy()).thenReturn("Paid By");
        when(payment.getPayerAddress()).thenReturn("42 Main St");
        when(payment.getPayerEmail()).thenReturn("jane.doe@example.org");
        when(payment.getPayerName()).thenReturn("Payer Name");
        when(payment.getAuditDetails()).thenReturn(new AuditDetails());
        doNothing().when(payment).setAuditDetails((AuditDetails) any());
        payment.setAuditDetails(auditDetails);

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        assertThrows(CustomException.class, () -> paymentRepository.updatePayment(paymentList));
        verify(payment).getAdditionalDetails();
        verify(payment).getId();
        verify(payment).getPaidBy();
        verify(payment).getPayerAddress();
        verify(payment).getPayerEmail();
        verify(payment).getPayerName();
        verify(payment).getPaymentDetails();
        verify(payment, atLeast(1)).getAuditDetails();
        verify(payment).setAuditDetails((AuditDetails) any());
    }

    @Test
    void testUpdateFileStoreId() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        paymentRepository.updateFileStoreId(new ArrayList<>());
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateFileStoreId2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        paymentRepository.updateFileStoreId(mapList);
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateFileStoreId3() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        mapList.add(new HashMap<>());
        paymentRepository.updateFileStoreId(mapList);
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateFileStoreId4() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenThrow(new CustomException("UPDATE {schema}.egcl_payment SET filestoreid=:filestoreid WHERE id=:id;",
                        "An error occurred"));
        assertThrows(CustomException.class, () -> paymentRepository.updateFileStoreId(new ArrayList<>()));
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testFetchPaymentIds() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<String> actualFetchPaymentIdsResult = paymentRepository.fetchPaymentIds(new PaymentSearchCriteria());
        assertSame(objectList, actualFetchPaymentIdsResult);
        assertTrue(actualFetchPaymentIdsResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFetchPaymentIds3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        PaymentSearchCriteria paymentSearchCriteria = mock(PaymentSearchCriteria.class);
        when(paymentSearchCriteria.getLimit()).thenReturn(1);
        when(paymentSearchCriteria.getOffset()).thenReturn(2);
        List<String> actualFetchPaymentIdsResult = paymentRepository.fetchPaymentIds(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentIdsResult);
        assertTrue(actualFetchPaymentIdsResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentSearchCriteria).getLimit();
        verify(paymentSearchCriteria).getOffset();
    }

    @Test
    void testFetchPaymentIds4() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenThrow(new CustomException("offset", "An error occurred"));
        assertThrows(CustomException.class, () -> paymentRepository.fetchPaymentIds(new PaymentSearchCriteria()));
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFetchPaymentIdsByCriteria2() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        List<String> actualFetchPaymentIdsByCriteriaResult = paymentRepository
                .fetchPaymentIdsByCriteria(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentIdsByCriteriaResult);
        assertTrue(actualFetchPaymentIdsByCriteriaResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPaymentIdsByCriteria3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("{schema}.");

        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        paymentSearchCriteria.setTenantId("42");
        List<String> actualFetchPaymentIdsByCriteriaResult = paymentRepository
                .fetchPaymentIdsByCriteria(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentIdsByCriteriaResult);
        assertTrue(actualFetchPaymentIdsByCriteriaResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
    }

    @Test
    void testFetchPaymentIdsByCriteria4() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");
        PaymentSearchCriteria paymentSearchCriteria = mock(PaymentSearchCriteria.class);
        when(paymentSearchCriteria.getTenantId()).thenReturn("42");
        doNothing().when(paymentSearchCriteria).setTenantId((String) any());
        paymentSearchCriteria.setTenantId("42");
        List<String> actualFetchPaymentIdsByCriteriaResult = paymentRepository
                .fetchPaymentIdsByCriteria(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentIdsByCriteriaResult);
        assertTrue(actualFetchPaymentIdsByCriteriaResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
        verify(paymentSearchCriteria).getTenantId();
        verify(paymentSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testFetchPaymentIdsByCriteria5() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        when(paymentQueryBuilder.getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any()))
                .thenReturn("Id Query");
        PaymentSearchCriteria paymentSearchCriteria = mock(PaymentSearchCriteria.class);
        when(paymentSearchCriteria.getTenantId()).thenReturn("{schema}.");
        doNothing().when(paymentSearchCriteria).setTenantId((String) any());
        paymentSearchCriteria.setTenantId("42");
        List<String> actualFetchPaymentIdsByCriteriaResult = paymentRepository
                .fetchPaymentIdsByCriteria(paymentSearchCriteria);
        assertSame(objectList, actualFetchPaymentIdsByCriteriaResult);
        assertTrue(actualFetchPaymentIdsByCriteriaResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(paymentQueryBuilder).getIdQuery((PaymentSearchCriteria) any(), (Map<String, Object>) any());
        verify(paymentSearchCriteria).getTenantId();
        verify(paymentSearchCriteria).setTenantId((String) any());
    }

    @Test
    void testFetchIfsccode() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (RowMapper<Object>) any())).thenReturn(objectList);
        List<String> actualFetchIfsccodeResult = paymentRepository.fetchIfsccode();
        assertSame(objectList, actualFetchIfsccodeResult);
        assertTrue(actualFetchIfsccodeResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFetchIfsccode2() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (RowMapper<Object>) any())).thenThrow(new CustomException(
                "SELECT distinct ifsccode from egcl_payment where ifsccode is not null ", "An error occurred"));
        assertThrows(CustomException.class, () -> paymentRepository.fetchIfsccode());
        verify(namedParameterJdbcTemplate).query((String) any(), (RowMapper<Object>) any());
    }

    @Test
    void testUpdatePaymentBankDetail() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        paymentRepository.updatePaymentBankDetail(MissingNode.getInstance(), "Ifsccode");
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdatePaymentBankDetail2() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        paymentRepository.updatePaymentBankDetail(null, "Ifsccode");
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdatePaymentBankDetail3() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenThrow(new CustomException("additionaldetails", "An error occurred"));
        assertThrows(CustomException.class,
                () -> paymentRepository.updatePaymentBankDetail(MissingNode.getInstance(), "Ifsccode"));
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }
}

