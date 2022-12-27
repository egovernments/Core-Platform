package org.egov.collection.util;

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

import com.fasterxml.jackson.databind.node.MissingNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.collection.config.ApplicationProperties;
import org.egov.collection.model.AuditDetails;
import org.egov.collection.model.Payment;
import org.egov.collection.model.PaymentDetail;
import org.egov.collection.model.PaymentRequest;
import org.egov.collection.model.PaymentSearchCriteria;
import org.egov.collection.model.enums.InstrumentStatusEnum;
import org.egov.collection.model.enums.PaymentModeEnum;
import org.egov.collection.model.enums.PaymentStatusEnum;
import org.egov.collection.producer.CollectionProducer;
import org.egov.collection.repository.PaymentRepository;
import org.egov.collection.repository.ServiceRequestRepository;
import org.egov.collection.service.PaymentWorkflowService;
import org.egov.common.contract.request.PlainAccessRequest;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PaymentValidatorTest {

    @Test
    void testValidatePaymentForCreate11() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(new ArrayList<>());
        ServiceRequestRepository serviceRequestRepository = mock(ServiceRequestRepository.class);
        when(serviceRequestRepository.fetchGetResult((String) any())).thenReturn("Fetch Get Result");
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), serviceRequestRepository);
        BigDecimal totalDue = BigDecimal.valueOf(42L);
        BigDecimal totalAmountPaid = BigDecimal.valueOf(42L);
        AuditDetails auditDetails = new AuditDetails();

        Payment payment = new Payment("42", "42", totalDue, totalAmountPaid, "42", 10L, PaymentModeEnum.CASH, 10L, "42",
                InstrumentStatusEnum.APPROVED, "INVALID_REQUEST_INFO", auditDetails, null, new ArrayList<>(),
                "INVALID_REQUEST_INFO", "42", "INVALID_REQUEST_INFO", "42 Main St", "jane.doe@example.org", "42",
                PaymentStatusEnum.NEW, "42");
        payment.addpaymentDetailsItem(new PaymentDetail());
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
        verify(serviceRequestRepository).fetchGetResult((String) any());
    }

    @Test
    void testValidatePaymentForCreate12() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(new ArrayList<>());
        ServiceRequestRepository serviceRequestRepository = mock(ServiceRequestRepository.class);
        when(serviceRequestRepository.fetchGetResult((String) any())).thenReturn("Fetch Get Result");
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), serviceRequestRepository);
        Payment payment = mock(Payment.class);
        when(payment.getAdditionalDetails()).thenThrow(new CustomException("INVALID_REQUEST_INFO", "An error occurred"));
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        when(payment.addpaymentDetailsItem((PaymentDetail) any())).thenReturn(new Payment());
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        doNothing().when(payment).setPaymentMode((PaymentModeEnum) any());
        payment.addpaymentDetailsItem(new PaymentDetail());
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
        verify(serviceRequestRepository).fetchGetResult((String) any());
        verify(payment).getAdditionalDetails();
        verify(payment, atLeast(1)).getIfscCode();
        verify(payment).getTenantId();
        verify(payment, atLeast(1)).getPaymentDetails();
        verify(payment).addpaymentDetailsItem((PaymentDetail) any());
        verify(payment).getPaymentMode();
        verify(payment).setPaymentMode((PaymentModeEnum) any());
    }

    @Test
    void testValidatePaymentForCreate13() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(new ArrayList<>());
        ServiceRequestRepository serviceRequestRepository = mock(ServiceRequestRepository.class);
        when(serviceRequestRepository.fetchGetResult((String) any())).thenReturn("Fetch Get Result");
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), serviceRequestRepository);
        Payment payment = mock(Payment.class);
        when(payment.getAdditionalDetails()).thenReturn(null);
        when(payment.getIfscCode()).thenReturn("Ifsc Code");
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        when(payment.addpaymentDetailsItem((PaymentDetail) any())).thenReturn(new Payment());
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        doNothing().when(payment).setPaymentMode((PaymentModeEnum) any());
        payment.addpaymentDetailsItem(new PaymentDetail());
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
        verify(serviceRequestRepository).fetchGetResult((String) any());
        verify(payment).getAdditionalDetails();
        verify(payment, atLeast(1)).getIfscCode();
        verify(payment).getTenantId();
        verify(payment, atLeast(1)).getPaymentDetails();
        verify(payment).addpaymentDetailsItem((PaymentDetail) any());
        verify(payment).getPaymentMode();
        verify(payment).setPaymentMode((PaymentModeEnum) any());
    }

    @Test
    void testValidatePaymentForCreate14() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(new ArrayList<>());
        ServiceRequestRepository serviceRequestRepository = mock(ServiceRequestRepository.class);
        when(serviceRequestRepository.fetchGetResult((String) any())).thenReturn("Fetch Get Result");
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), serviceRequestRepository);
        Payment payment = mock(Payment.class);
        when(payment.getAdditionalDetails()).thenReturn(MissingNode.getInstance());
        when(payment.getIfscCode()).thenReturn(null);
        when(payment.getTenantId()).thenReturn("42");
        when(payment.getPaymentDetails()).thenReturn(new ArrayList<>());
        when(payment.addpaymentDetailsItem((PaymentDetail) any())).thenReturn(new Payment());
        when(payment.getPaymentMode()).thenReturn(PaymentModeEnum.CASH);
        doNothing().when(payment).setPaymentMode((PaymentModeEnum) any());
        payment.addpaymentDetailsItem(new PaymentDetail());
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
        verify(payment).getIfscCode();
        verify(payment).getTenantId();
        verify(payment, atLeast(1)).getPaymentDetails();
        verify(payment).addpaymentDetailsItem((PaymentDetail) any());
        verify(payment).getPaymentMode();
        verify(payment).setPaymentMode((PaymentModeEnum) any());
    }

    @Test
    void testValidateUserInfo() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        RequestInfo requestInfo = new RequestInfo();
        HashMap<String, String> stringStringMap = new HashMap<>();
        paymentValidator.validateUserInfo(requestInfo, stringStringMap);
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testValidateUserInfo2() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        PlainAccessRequest plainAccessRequest = new PlainAccessRequest();
        RequestInfo requestInfo = new RequestInfo("42", "INVALID_USER_INFO", 1L, "INVALID_USER_INFO", "INVALID_USER_INFO",
                "INVALID_USER_INFO", "42", "ABC123", "42", plainAccessRequest, new User());

        HashMap<String, String> stringStringMap = new HashMap<>();
        paymentValidator.validateUserInfo(requestInfo, stringStringMap);
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testValidateUserInfo3() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        HashMap<String, String> stringStringMap = new HashMap<>();
        paymentValidator.validateUserInfo(null, stringStringMap);
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testValidateUserInfo4() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenThrow(new CustomException("Code", "An error occurred"));
        assertThrows(CustomException.class, () -> paymentValidator.validateUserInfo(requestInfo, new HashMap<>()));
        verify(requestInfo).getUserInfo();
    }

    @Test
    void testValidateAndEnrichPaymentsForUpdate() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        ArrayList<Payment> paymentList = new ArrayList<>();
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(paymentList);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        ArrayList<Payment> payments = new ArrayList<>();
        List<Payment> actualValidateAndEnrichPaymentsForUpdateResult = paymentValidator
                .validateAndEnrichPaymentsForUpdate(payments, new RequestInfo());
        assertSame(paymentList, actualValidateAndEnrichPaymentsForUpdateResult);
        assertTrue(actualValidateAndEnrichPaymentsForUpdateResult.isEmpty());
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
    }

    @Test
    void testValidateAndEnrichPaymentsForUpdate3() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any())).thenReturn(new ArrayList<>());
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));

        ArrayList<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment());
        assertThrows(CustomException.class,
                () -> paymentValidator.validateAndEnrichPaymentsForUpdate(paymentList, new RequestInfo()));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
    }

    @Test
    void testValidateAndEnrichPaymentsForUpdate4() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.fetchPayments((PaymentSearchCriteria) any()))
                .thenThrow(new CustomException("Code", "An error occurred"));
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        ArrayList<Payment> payments = new ArrayList<>();
        assertThrows(CustomException.class,
                () -> paymentValidator.validateAndEnrichPaymentsForUpdate(payments, new RequestInfo()));
        verify(paymentRepository).fetchPayments((PaymentSearchCriteria) any());
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        assertThrows(CustomException.class, () -> paymentValidator
                .validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, new RequestInfo(), "Module Name"));
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig2() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        PlainAccessRequest plainAccessRequest = new PlainAccessRequest();
        assertThrows(CustomException.class,
                () -> paymentValidator.validateAndUpdateSearchRequestFromConfig(
                        paymentSearchCriteria, new RequestInfo("42", "INVALID_USER_INFO", 1L, "INVALID_USER_INFO",
                                "INVALID_USER_INFO", "INVALID_USER_INFO", "42", "ABC123", "42", plainAccessRequest, new User()),
                        "Module Name"));
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig3() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        assertThrows(CustomException.class, () -> paymentValidator
                .validateAndUpdateSearchRequestFromConfig(new PaymentSearchCriteria(), null, "Module Name"));
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig4() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenThrow(new CustomException("Code", "An error occurred"));
        assertThrows(CustomException.class, () -> paymentValidator
                .validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, requestInfo, "Module Name"));
        verify(requestInfo).getUserInfo();
    }
}

