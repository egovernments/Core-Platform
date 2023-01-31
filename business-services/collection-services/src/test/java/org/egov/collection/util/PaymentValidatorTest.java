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
    void testValidatePaymentForCreate7() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));

        Payment payment = new Payment();
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);
        PaymentRequest paymentRequest = mock(PaymentRequest.class);
        when(paymentRequest.getRequestInfo()).thenThrow(new CustomException("INVALID_REQUEST_INFO", "An error occurred"));
        when(paymentRequest.getPayment()).thenReturn(new Payment());
        doNothing().when(paymentRequest).setPayment((Payment) any());
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRequest, atLeast(1)).getPayment();
        verify(paymentRequest).getRequestInfo();
        verify(paymentRequest).setPayment((Payment) any());
    }

    @Test
    void testValidatePaymentForCreate9() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));

        Payment payment = new Payment();
        payment.setPaymentMode(PaymentModeEnum.CHEQUE);
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenThrow(new CustomException("Code", "An error occurred"));
        PaymentRequest paymentRequest = mock(PaymentRequest.class);
        when(paymentRequest.getRequestInfo()).thenReturn(requestInfo);
        when(paymentRequest.getPayment()).thenReturn(new Payment());
        doNothing().when(paymentRequest).setPayment((Payment) any());
        paymentRequest.setPayment(payment);
        assertThrows(CustomException.class, () -> paymentValidator.validatePaymentForCreate(paymentRequest));
        verify(paymentRequest, atLeast(1)).getPayment();
        verify(paymentRequest).getRequestInfo();
        verify(paymentRequest).setPayment((Payment) any());
        verify(requestInfo).getUserInfo();
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
        when(requestInfo.getUserInfo()).thenReturn(new User());
        HashMap<String, String> stringStringMap = new HashMap<>();
        paymentValidator.validateUserInfo(requestInfo, stringStringMap);
        verify(requestInfo, atLeast(1)).getUserInfo();
        assertEquals(1, stringStringMap.size());
    }

    @Test
    void testValidateUserInfo5() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenReturn(new User(123L, "janedoe", "INVALID_USER_ID", "INVALID_USER_ID", "42",
                "42", new ArrayList<>(), "42", "01234567-89AB-CDEF-FEDC-BA9876543210"));
        paymentValidator.validateUserInfo(requestInfo, new HashMap<>());
        verify(requestInfo, atLeast(1)).getUserInfo();
    }

    @Test
    void testValidateUserInfo6() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        User user = mock(User.class);
        when(user.getUuid()).thenThrow(new CustomException("Code", "An error occurred"));
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenReturn(user);
        assertThrows(CustomException.class, () -> paymentValidator.validateUserInfo(requestInfo, new HashMap<>()));
        verify(requestInfo, atLeast(1)).getUserInfo();
        verify(user).getUuid();
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
        when(requestInfo.getUserInfo()).thenReturn(new User());
        assertThrows(CustomException.class, () -> paymentValidator
                .validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, requestInfo, "Module Name"));
        verify(requestInfo, atLeast(1)).getUserInfo();
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig6() {

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentWorkflowService paymentWorkflowService = new PaymentWorkflowService(paymentRepository1,
                paymentWorkflowValidator, collectionProducer, new ApplicationProperties());

        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository, paymentWorkflowService,
                new ApplicationProperties(), mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        User user = mock(User.class);
        when(user.getType()).thenThrow(new CustomException("EMPLOYEE", "An error occurred"));
        when(user.getUuid()).thenReturn("01234567-89AB-CDEF-FEDC-BA9876543210");
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenReturn(user);
        assertThrows(CustomException.class, () -> paymentValidator
                .validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, requestInfo, "Module Name"));
        verify(requestInfo, atLeast(1)).getUserInfo();
        verify(user).getType();
        verify(user).getUuid();
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig7() {

        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.setSearchIgnoreStatus(new ArrayList<>());
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentValidator paymentValidator = new PaymentValidator(paymentRepository,
                new PaymentWorkflowService(paymentRepository1, null, collectionProducer, new ApplicationProperties()),
                applicationProperties, mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        User user = mock(User.class);
        when(user.getType()).thenReturn("Type");
        when(user.getUuid()).thenReturn("01234567-89AB-CDEF-FEDC-BA9876543210");
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenReturn(user);
        new CustomException("EMPLOYEE", "An error occurred");

        paymentValidator.validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, requestInfo, "Module Name");
        verify(requestInfo, atLeast(1)).getUserInfo();
        verify(user).getType();
        verify(user).getUuid();
        assertEquals(1, paymentSearchCriteria.getBusinessServices().size());
    }

    @Test
    void testValidateAndUpdateSearchRequestFromConfig8() {

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");

        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.setSearchIgnoreStatus(stringList);
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        PaymentRepository paymentRepository1 = mock(PaymentRepository.class);
        PaymentWorkflowValidator paymentWorkflowValidator = new PaymentWorkflowValidator();
        CollectionProducer collectionProducer = new CollectionProducer();
        PaymentValidator paymentValidator = new PaymentValidator(
                paymentRepository, new PaymentWorkflowService(paymentRepository1, paymentWorkflowValidator,
                collectionProducer, new ApplicationProperties()),
                applicationProperties, mock(ServiceRequestRepository.class));
        PaymentSearchCriteria paymentSearchCriteria = new PaymentSearchCriteria();
        User user = mock(User.class);
        when(user.getType()).thenReturn("Type");
        when(user.getUuid()).thenReturn("01234567-89AB-CDEF-FEDC-BA9876543210");
        RequestInfo requestInfo = mock(RequestInfo.class);
        when(requestInfo.getUserInfo()).thenReturn(user);
        new CustomException("EMPLOYEE", "An error occurred");

        paymentValidator.validateAndUpdateSearchRequestFromConfig(paymentSearchCriteria, requestInfo, "Module Name");
        verify(requestInfo, atLeast(1)).getUserInfo();
        verify(user).getType();
        verify(user).getUuid();
        assertEquals(5, paymentSearchCriteria.getStatus().size());
        assertEquals(1, paymentSearchCriteria.getBusinessServices().size());
    }
}

