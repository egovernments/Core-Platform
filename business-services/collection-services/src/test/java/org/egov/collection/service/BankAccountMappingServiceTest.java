package org.egov.collection.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.collection.model.BankAccountServiceMapping;
import org.egov.collection.model.BankAccountServiceMappingSearchCriteria;
import org.egov.collection.repository.BankAccountMappingRepository;
import org.egov.tracer.config.ObjectMapperFactory;
import org.egov.tracer.config.TracerProperties;
import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

class BankAccountMappingServiceTest {

    @Test
    void testCreateBankAccountToServiceMapping() {

        BankAccountMappingRepository bankAccountMappingRepository = mock(BankAccountMappingRepository.class);
        doNothing().when(bankAccountMappingRepository)
                .persistBankAccountServiceMapping((List<BankAccountServiceMapping>) any());
        ProducerFactory<String, Object> producerFactory = (ProducerFactory<String, Object>) mock(ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        TracerProperties tracerProperties = new TracerProperties();
        TracerProperties tracerProperties1 = new TracerProperties();
        BankAccountMappingService bankAccountMappingService = new BankAccountMappingService(bankAccountMappingRepository,
                new LogAwareKafkaTemplate<>(tracerProperties, kafkaTemplate,
                        new ObjectMapperFactory(tracerProperties1, new StandardReactiveWebEnvironment())));
        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        List<BankAccountServiceMapping> actualCreateBankAccountToServiceMappingResult = bankAccountMappingService
                .createBankAccountToServiceMapping(bankAccountServiceMappingList);
        assertSame(bankAccountServiceMappingList, actualCreateBankAccountToServiceMappingResult);
        assertTrue(actualCreateBankAccountToServiceMappingResult.isEmpty());
        verify(bankAccountMappingRepository).persistBankAccountServiceMapping((List<BankAccountServiceMapping>) any());
        verify(producerFactory).transactionCapable();
    }

    @Test
    void testSearchBankAccountService() {

        BankAccountMappingRepository bankAccountMappingRepository = mock(BankAccountMappingRepository.class);
        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        when(
                bankAccountMappingRepository.searchBankAccountServicemapping((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(bankAccountServiceMappingList);
        ProducerFactory<String, Object> producerFactory = (ProducerFactory<String, Object>) mock(ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        TracerProperties tracerProperties = new TracerProperties();
        TracerProperties tracerProperties1 = new TracerProperties();
        BankAccountMappingService bankAccountMappingService = new BankAccountMappingService(bankAccountMappingRepository,
                new LogAwareKafkaTemplate<>(tracerProperties, kafkaTemplate,
                        new ObjectMapperFactory(tracerProperties1, new StandardReactiveWebEnvironment())));
        List<BankAccountServiceMapping> actualSearchBankAccountServiceResult = bankAccountMappingService
                .searchBankAccountService(new BankAccountServiceMappingSearchCriteria());
        assertSame(bankAccountServiceMappingList, actualSearchBankAccountServiceResult);
        assertTrue(actualSearchBankAccountServiceResult.isEmpty());
        verify(bankAccountMappingRepository)
                .searchBankAccountServicemapping((BankAccountServiceMappingSearchCriteria) any());
        verify(producerFactory).transactionCapable();
    }

    @Test
    void testSearchBankAccountsMappedToServices() {

        BankAccountMappingRepository bankAccountMappingRepository = mock(BankAccountMappingRepository.class);
        ArrayList<Long> resultLongList = new ArrayList<>();
        when(bankAccountMappingRepository.searchBankAccountBranches((String) any())).thenReturn(resultLongList);
        ProducerFactory<String, Object> producerFactory = (ProducerFactory<String, Object>) mock(ProducerFactory.class);
        when(producerFactory.transactionCapable()).thenReturn(true);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        TracerProperties tracerProperties = new TracerProperties();
        TracerProperties tracerProperties1 = new TracerProperties();
        List<Long> actualSearchBankAccountsMappedToServicesResult = (new BankAccountMappingService(
                bankAccountMappingRepository,
                new LogAwareKafkaTemplate<>(tracerProperties, kafkaTemplate,
                        new ObjectMapperFactory(tracerProperties1, new StandardReactiveWebEnvironment()))))
                .searchBankAccountsMappedToServices("42");
        assertSame(resultLongList, actualSearchBankAccountsMappedToServicesResult);
        assertTrue(actualSearchBankAccountsMappedToServicesResult.isEmpty());
        verify(bankAccountMappingRepository).searchBankAccountBranches((String) any());
        verify(producerFactory).transactionCapable();
    }
}

