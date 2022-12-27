package org.egov.collection.repository.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.egov.collection.model.AuditDetails;
import org.egov.collection.model.Payment;
import org.egov.collection.model.PaymentDetail;
import org.egov.collection.model.enums.InstrumentStatusEnum;
import org.egov.collection.model.enums.PaymentModeEnum;
import org.egov.collection.model.enums.PaymentStatusEnum;
import org.egov.collection.model.enums.Purpose;
import org.egov.collection.web.contract.Bill;

import org.egov.collection.web.contract.BillAccountDetail;

import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

class PaymentQueryBuilderTest {

    @Test
    void testGetParametersForPaymentCreate3() {
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        AuditDetails auditDetails = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setPaymentMode(PaymentModeEnum.CASH);
        assertEquals(23, PaymentQueryBuilder.getParametersForPaymentCreate(payment).getParameterNames().length);
    }

    @Test
    void testGetParametersForPaymentCreate5() {
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getCreatedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        MissingNode additionalDetails = MissingNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setPaymentMode(PaymentModeEnum.CASH);
        assertEquals(23, PaymentQueryBuilder.getParametersForPaymentCreate(payment).getParameterNames().length);
        verify(auditDetails).getCreatedTime();
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getCreatedBy();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testGetParametersForPaymentCreate6() {
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getCreatedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        NullNode additionalDetails = NullNode.getInstance();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setPaymentMode(PaymentModeEnum.CASH);
        assertEquals(23, PaymentQueryBuilder.getParametersForPaymentCreate(payment).getParameterNames().length);
        verify(auditDetails).getCreatedTime();
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getCreatedBy();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testGetParametersForPaymentCreate7() {
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getCreatedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        BooleanNode additionalDetails = BooleanNode.getFalse();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 1L, PaymentModeEnum.CASH, 1L, "42",
                InstrumentStatusEnum.APPROVED, "Ifsc Code", auditDetails, additionalDetails, new ArrayList<>(), "Paid By",
                "42", "Payer Name", "42 Main St", "jane.doe@example.org", "42", PaymentStatusEnum.NEW, "42");
        payment.setPaymentMode(PaymentModeEnum.CASH);
        assertEquals(23, PaymentQueryBuilder.getParametersForPaymentCreate(payment).getParameterNames().length);
        verify(auditDetails).getCreatedTime();
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getCreatedBy();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testGetParametersForPaymentDetailCreate2() {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setAuditDetails(new AuditDetails());
        assertEquals(17,
                PaymentQueryBuilder.getParametersForPaymentDetailCreate("42", paymentDetail).getParameterNames().length);
    }

    @Test
    void testGetParametersForPaymentDetailCreate3() {
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        Bill bill = new Bill();
        MissingNode additionalDetails = MissingNode.getInstance();
        assertEquals(17,
                PaymentQueryBuilder
                        .getParametersForPaymentDetailCreate("42",
                                new PaymentDetail("42", "42", "42", totalDue, totalAmountPaid, "42", "42", 1L, 1L, "id", "id", "42",
                                        bill, additionalDetails, new AuditDetails()))
                        .getParameterNames().length);
    }

    @Test
    void testGetParametersForPaymentDetailCreate5() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(paymentDetail.getManualReceiptDate()).thenReturn(1L);
        when(paymentDetail.getReceiptDate()).thenReturn(1L);
        when(paymentDetail.getBillId()).thenReturn("42");
        when(paymentDetail.getBusinessService()).thenReturn("Business Service");
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getManualReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptType()).thenReturn("Receipt Type");
        when(paymentDetail.getTenantId()).thenReturn("42");
        when(paymentDetail.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getTotalDue()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        assertEquals(17,
                PaymentQueryBuilder.getParametersForPaymentDetailCreate("42", paymentDetail).getParameterNames().length);
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getManualReceiptDate();
        verify(paymentDetail).getReceiptDate();
        verify(paymentDetail).getBillId();
        verify(paymentDetail).getBusinessService();
        verify(paymentDetail).getId();
        verify(paymentDetail).getManualReceiptNumber();
        verify(paymentDetail).getReceiptNumber();
        verify(paymentDetail).getReceiptType();
        verify(paymentDetail).getTenantId();
        verify(paymentDetail).getTotalAmountPaid();
        verify(paymentDetail).getTotalDue();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailCreate6() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
        when(paymentDetail.getManualReceiptDate()).thenReturn(1L);
        when(paymentDetail.getReceiptDate()).thenReturn(1L);
        when(paymentDetail.getBillId()).thenReturn("42");
        when(paymentDetail.getBusinessService()).thenReturn("Business Service");
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getManualReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptType()).thenReturn("Receipt Type");
        when(paymentDetail.getTenantId()).thenReturn("42");
        when(paymentDetail.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getTotalDue()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        assertEquals(17,
                PaymentQueryBuilder.getParametersForPaymentDetailCreate("42", paymentDetail).getParameterNames().length);
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getManualReceiptDate();
        verify(paymentDetail).getReceiptDate();
        verify(paymentDetail).getBillId();
        verify(paymentDetail).getBusinessService();
        verify(paymentDetail).getId();
        verify(paymentDetail).getManualReceiptNumber();
        verify(paymentDetail).getReceiptNumber();
        verify(paymentDetail).getReceiptType();
        verify(paymentDetail).getTenantId();
        verify(paymentDetail).getTotalAmountPaid();
        verify(paymentDetail).getTotalDue();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailCreate7() throws UnsupportedEncodingException {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new BinaryNode("AAAAAAAA".getBytes("UTF-8")));
        when(paymentDetail.getManualReceiptDate()).thenReturn(1L);
        when(paymentDetail.getReceiptDate()).thenReturn(1L);
        when(paymentDetail.getBillId()).thenReturn("42");
        when(paymentDetail.getBusinessService()).thenReturn("Business Service");
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getManualReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptType()).thenReturn("Receipt Type");
        when(paymentDetail.getTenantId()).thenReturn("42");
        when(paymentDetail.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getTotalDue()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        assertEquals(17,
                PaymentQueryBuilder.getParametersForPaymentDetailCreate("42", paymentDetail).getParameterNames().length);
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getManualReceiptDate();
        verify(paymentDetail).getReceiptDate();
        verify(paymentDetail).getBillId();
        verify(paymentDetail).getBusinessService();
        verify(paymentDetail).getId();
        verify(paymentDetail).getManualReceiptNumber();
        verify(paymentDetail).getReceiptNumber();
        verify(paymentDetail).getReceiptType();
        verify(paymentDetail).getTenantId();
        verify(paymentDetail).getTotalAmountPaid();
        verify(paymentDetail).getTotalDue();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailCreate8() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(NullNode.getInstance());
        when(paymentDetail.getManualReceiptDate()).thenReturn(1L);
        when(paymentDetail.getReceiptDate()).thenReturn(1L);
        when(paymentDetail.getBillId()).thenReturn("42");
        when(paymentDetail.getBusinessService()).thenReturn("Business Service");
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getManualReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptNumber()).thenReturn("42");
        when(paymentDetail.getReceiptType()).thenReturn("Receipt Type");
        when(paymentDetail.getTenantId()).thenReturn("42");
        when(paymentDetail.getTotalAmountPaid()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getTotalDue()).thenReturn(BigDecimal.valueOf(42L));
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        assertEquals(17,
                PaymentQueryBuilder.getParametersForPaymentDetailCreate("42", paymentDetail).getParameterNames().length);
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getManualReceiptDate();
        verify(paymentDetail).getReceiptDate();
        verify(paymentDetail).getBillId();
        verify(paymentDetail).getBusinessService();
        verify(paymentDetail).getId();
        verify(paymentDetail).getManualReceiptNumber();
        verify(paymentDetail).getReceiptNumber();
        verify(paymentDetail).getReceiptType();
        verify(paymentDetail).getTenantId();
        verify(paymentDetail).getTotalAmountPaid();
        verify(paymentDetail).getTotalDue();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate() {
        assertEquals(10, PaymentQueryBuilder.getParametersForBillAccountDetailCreate(new BillAccountDetail())
                .getParameterNames().length);
    }

    @Test
    void testGetParametersForBillAccountDetailCreate2() {
        BigDecimal amount = BigDecimal.valueOf(42L);
        BigDecimal adjustedAmount = BigDecimal.valueOf(42L);
        MissingNode additionalDetails = MissingNode.getInstance();
        assertEquals(10,
                PaymentQueryBuilder
                        .getParametersForBillAccountDetailCreate(new BillAccountDetail("42", "42", "42", "42", 1, amount,
                                adjustedAmount, true, "id", additionalDetails, Purpose.ARREAR, new AuditDetails()))
                        .getParameterNames().length);
    }

    @Test
    void testGetParametersForBillAccountDetailCreate4() {
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate5() {
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails())
                .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate6() throws UnsupportedEncodingException {
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(new BinaryNode("AAAAAAAA".getBytes("UTF-8")));
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate7() {
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(NullNode.getInstance());
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate8() {
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(LongNode.valueOf(1L));
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate9() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForBillAccountDetailCreate10() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addPOJO("Pojo");
        BillAccountDetail billAccountDetail = mock(BillAccountDetail.class);
        when(billAccountDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(billAccountDetail.getIsActualDemand()).thenReturn(true);
        when(billAccountDetail.getOrder()).thenReturn(1);
        when(billAccountDetail.getBillDetailId()).thenReturn("42");
        when(billAccountDetail.getDemandDetailId()).thenReturn("42");
        when(billAccountDetail.getId()).thenReturn("42");
        when(billAccountDetail.getTaxHeadCode()).thenReturn("Tax Head Code");
        when(billAccountDetail.getTenantId()).thenReturn("42");
        when(billAccountDetail.getAdjustedAmount()).thenReturn(BigDecimal.valueOf(42L));
        when(billAccountDetail.getAmount()).thenReturn(BigDecimal.valueOf(42L));
        assertEquals(10,
                PaymentQueryBuilder.getParametersForBillAccountDetailCreate(billAccountDetail).getParameterNames().length);
        verify(billAccountDetail).getAdditionalDetails();
        verify(billAccountDetail).getIsActualDemand();
        verify(billAccountDetail).getOrder();
        verify(billAccountDetail).getBillDetailId();
        verify(billAccountDetail).getDemandDetailId();
        verify(billAccountDetail).getId();
        verify(billAccountDetail).getTaxHeadCode();
        verify(billAccountDetail).getTenantId();
        verify(billAccountDetail).getAdjustedAmount();
        verify(billAccountDetail).getAmount();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate2() {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setAuditDetails(new AuditDetails());
        assertEquals(4,
                PaymentQueryBuilder.getParametersForPaymentDetailStatusUpdate(paymentDetail).getParameterNames().length);
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate3() {
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        Bill bill = new Bill();
        MissingNode additionalDetails = MissingNode.getInstance();
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(new PaymentDetail("42", "42", "42", totalDue, totalAmountPaid,
                        "42", "42", 1L, 1L, "id", "id", "42", bill, additionalDetails, new AuditDetails()));
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate5() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate6() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate7() throws UnsupportedEncodingException {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new BinaryNode("AAAAAAAA".getBytes("UTF-8")));
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate8() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(NullNode.getInstance());
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate9() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate10() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addPOJO("Pojo");
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate11() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addObject();
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailStatusUpdate12() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addPOJO("Pojo");
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailStatusUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailStatusUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailStatusUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(
                ((PGobject) actualParametersForPaymentDetailStatusUpdate.getValues().get("additionaldetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate2() {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setAuditDetails(new AuditDetails());
        assertEquals(4,
                PaymentQueryBuilder.getParametersForPaymentDetailUpdate(paymentDetail).getParameterNames().length);
    }

    @Test
    void testGetParametersForPaymentDetailUpdate3() {
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        Bill bill = new Bill();
        MissingNode additionalDetails = MissingNode.getInstance();
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(new PaymentDetail("42", "42", "42", totalDue, totalAmountPaid, "42",
                        "42", 1L, 1L, "id", "id", "42", bill, additionalDetails, new AuditDetails()));
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
    }

    @Test
    void testGetParametersForPaymentDetailUpdate5() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate6() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate7() throws UnsupportedEncodingException {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(new BinaryNode("AAAAAAAA".getBytes("UTF-8")));
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate8() {
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(NullNode.getInstance());
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate9() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate10() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addPOJO("Pojo");
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate11() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addObject();
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForPaymentDetailUpdate12() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addPOJO("Pojo");
        PaymentDetail paymentDetail = mock(PaymentDetail.class);
        when(paymentDetail.getAdditionalDetails()).thenReturn(arrayNode);
        when(paymentDetail.getId()).thenReturn("42");
        when(paymentDetail.getAuditDetails()).thenReturn(new AuditDetails());
        MapSqlParameterSource actualParametersForPaymentDetailUpdate = PaymentQueryBuilder
                .getParametersForPaymentDetailUpdate(paymentDetail);
        assertEquals(4, actualParametersForPaymentDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).getType());
        assertFalse(((PGobject) actualParametersForPaymentDetailUpdate.getValues().get("additionalDetails")).isNull());
        verify(paymentDetail).getAdditionalDetails();
        verify(paymentDetail).getId();
        verify(paymentDetail, atLeast(1)).getAuditDetails();
    }

    @Test
    void testGetParametersForBankDetailUpdate() {
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(MissingNode.getInstance(), "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
    }

    @Test
    void testGetParametersForBankDetailUpdate2() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate3() {
        assertEquals(2,
                PaymentQueryBuilder.getParametersForBankDetailUpdate(null, "Ifsccode").getParameterNames().length);
    }

    @Test
    void testGetParametersForBankDetailUpdate4() {
        BigIntegerNode bigIntegerNode = new BigIntegerNode(BigInteger.valueOf(42L));
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(bigIntegerNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("42", bigIntegerNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate5() throws UnsupportedEncodingException {
        BinaryNode binaryNode = new BinaryNode("AAAAAAAA".getBytes("UTF-8"));
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(binaryNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("\"QUFBQUFBQUE=\"", binaryNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate6() {
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(mock(ArrayNode.class), "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
    }

    @Test
    void testGetParametersForBankDetailUpdate7() {
        NullNode instance = NullNode.getInstance();
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(instance, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("null", instance.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate8() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ { } ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate9() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addPOJO("Pojo");
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ \"Pojo\" ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate10() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addObject();
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ { }, { } ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate11() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addObject();
        arrayNode.addPOJO("Pojo");
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ { }, \"Pojo\" ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersForBankDetailUpdate12() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        arrayNode.addPOJO(2);
        MapSqlParameterSource actualParametersForBankDetailUpdate = PaymentQueryBuilder
                .getParametersForBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersForBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersForBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ 2 ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersEmptyDtlBankDetailUpdate() {
        MapSqlParameterSource actualParametersEmptyDtlBankDetailUpdate = PaymentQueryBuilder
                .getParametersEmptyDtlBankDetailUpdate(MissingNode.getInstance(), "Ifsccode");
        assertEquals(2, actualParametersEmptyDtlBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).isNull());
    }

    @Test
    void testGetParametersEmptyDtlBankDetailUpdate2() {
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
        MapSqlParameterSource actualParametersEmptyDtlBankDetailUpdate = PaymentQueryBuilder
                .getParametersEmptyDtlBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersEmptyDtlBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("[ ]", arrayNode.toPrettyString());
    }

    @Test
    void testGetParametersEmptyDtlBankDetailUpdate3() {
        MapSqlParameterSource actualParametersEmptyDtlBankDetailUpdate = PaymentQueryBuilder
                .getParametersEmptyDtlBankDetailUpdate(null, "Ifsccode");
        assertEquals(2, actualParametersEmptyDtlBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).isNull());
    }

    @Test
    void testGetParametersEmptyDtlBankDetailUpdate4() throws UnsupportedEncodingException {
        BinaryNode binaryNode = new BinaryNode("AAAAAAAA".getBytes("UTF-8"));
        MapSqlParameterSource actualParametersEmptyDtlBankDetailUpdate = PaymentQueryBuilder
                .getParametersEmptyDtlBankDetailUpdate(binaryNode, "Ifsccode");
        assertEquals(2, actualParametersEmptyDtlBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        assertEquals("\"QUFBQUFBQUE=\"", binaryNode.toPrettyString());
    }

    @Test
    void testGetParametersEmptyDtlBankDetailUpdate5() throws IOException {
        ArrayNode arrayNode = mock(ArrayNode.class);
        doNothing().when(arrayNode).serialize((JsonGenerator) any(), (SerializerProvider) any());
        MapSqlParameterSource actualParametersEmptyDtlBankDetailUpdate = PaymentQueryBuilder
                .getParametersEmptyDtlBankDetailUpdate(arrayNode, "Ifsccode");
        assertEquals(2, actualParametersEmptyDtlBankDetailUpdate.getParameterNames().length);
        assertEquals("jsonb",
                ((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).getType());
        assertFalse(((PGobject) actualParametersEmptyDtlBankDetailUpdate.getValues().get("additionaldetails")).isNull());
        verify(arrayNode).serialize((JsonGenerator) any(), (SerializerProvider) any());
    }

    @Test
    void testConstructor() {
        assertEquals(PaymentQueryBuilder.BILL_BASE_QUERY, (new PaymentQueryBuilder()).getBillQuery());
    }
}

