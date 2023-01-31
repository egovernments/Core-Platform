package org.egov.collection.repository;

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

import org.egov.collection.model.BankAccountServiceMapping;
import org.egov.collection.model.BankAccountServiceMappingSearchCriteria;

import org.egov.collection.repository.querybuilder.BankAccountServiceQueryBuilder;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BankAccountMappingRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class BankAccountMappingRepositoryTest {
    @Autowired
    private BankAccountMappingRepository bankAccountMappingRepository;

    @MockBean
    private BankAccountServiceQueryBuilder bankAccountServiceQueryBuilder;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void testPersistBankAccountServiceMapping() {
        when(bankAccountServiceQueryBuilder.insertBankAccountServiceDetailsQuery()).thenReturn("3");
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        bankAccountMappingRepository.persistBankAccountServiceMapping(new ArrayList<>());
        verify(bankAccountServiceQueryBuilder).insertBankAccountServiceDetailsQuery();
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testPersistBankAccountServiceMapping2() {
        when(bankAccountServiceQueryBuilder.insertBankAccountServiceDetailsQuery()).thenReturn("3");
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenThrow(new CustomException("Create BankAccount Service Mapping Repository::", "An error occurred"));
        bankAccountMappingRepository.persistBankAccountServiceMapping(new ArrayList<>());
        verify(bankAccountServiceQueryBuilder).insertBankAccountServiceDetailsQuery();
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testPersistBankAccountServiceMapping3() {
        when(bankAccountServiceQueryBuilder.insertBankAccountServiceDetailsQuery()).thenReturn("3");
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new BankAccountServiceMapping());
        bankAccountMappingRepository.persistBankAccountServiceMapping(bankAccountServiceMappingList);
        verify(bankAccountServiceQueryBuilder).insertBankAccountServiceDetailsQuery();
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
    }

    @Test
    void testPersistBankAccountServiceMapping5() {
        when(bankAccountServiceQueryBuilder.insertBankAccountServiceDetailsQuery()).thenReturn("3");
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (Map<String, ?>[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        BankAccountServiceMapping bankAccountServiceMapping = mock(BankAccountServiceMapping.class);
        when(bankAccountServiceMapping.getCreatedBy()).thenReturn(1L);
        when(bankAccountServiceMapping.getLastModifiedBy()).thenReturn(1L);
        when(bankAccountServiceMapping.getBank()).thenReturn("Bank");
        when(bankAccountServiceMapping.getBankAccount()).thenReturn("3");
        when(bankAccountServiceMapping.getBankBranch()).thenReturn("janedoe/featurebranch");
        when(bankAccountServiceMapping.getBusinessDetails()).thenReturn("Business Details");
        when(bankAccountServiceMapping.getTenantId()).thenReturn("42");

        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(bankAccountServiceMapping);
        bankAccountMappingRepository.persistBankAccountServiceMapping(bankAccountServiceMappingList);
        verify(bankAccountServiceQueryBuilder).insertBankAccountServiceDetailsQuery();
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (Map<String, ?>[]) any());
        verify(bankAccountServiceMapping).getCreatedBy();
        verify(bankAccountServiceMapping).getLastModifiedBy();
        verify(bankAccountServiceMapping).getBank();
        verify(bankAccountServiceMapping).getBankAccount();
        verify(bankAccountServiceMapping).getBankBranch();
        verify(bankAccountServiceMapping).getBusinessDetails();
        verify(bankAccountServiceMapping).getTenantId();
    }

    @Test
    void testSearchBankAccountServicemapping2() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any())).thenReturn("3");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);

        BankAccountServiceMappingSearchCriteria bankAccountServiceMappingSearchCriteria = new BankAccountServiceMappingSearchCriteria();
        bankAccountServiceMappingSearchCriteria.setTenantId("42");
        List<BankAccountServiceMapping> actualSearchBankAccountServicemappingResult = bankAccountMappingRepository
                .searchBankAccountServicemapping(bankAccountServiceMappingSearchCriteria);
        assertSame(objectList, actualSearchBankAccountServicemappingResult);
        assertTrue(actualSearchBankAccountServicemappingResult.isEmpty());
        verify(bankAccountServiceQueryBuilder).BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testSearchBankAccountServicemapping3() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any())).thenReturn("{schema}.");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);

        BankAccountServiceMappingSearchCriteria bankAccountServiceMappingSearchCriteria = new BankAccountServiceMappingSearchCriteria();
        bankAccountServiceMappingSearchCriteria.setTenantId("42");
        List<BankAccountServiceMapping> actualSearchBankAccountServicemappingResult = bankAccountMappingRepository
                .searchBankAccountServicemapping(bankAccountServiceMappingSearchCriteria);
        assertSame(objectList, actualSearchBankAccountServicemappingResult);
        assertTrue(actualSearchBankAccountServicemappingResult.isEmpty());
        verify(bankAccountServiceQueryBuilder).BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testSearchBankAccountServicemapping4() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any())).thenReturn("3");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);

        BankAccountServiceMappingSearchCriteria bankAccountServiceMappingSearchCriteria = new BankAccountServiceMappingSearchCriteria();
        bankAccountServiceMappingSearchCriteria.setTenantId("{schema}.");
        List<BankAccountServiceMapping> actualSearchBankAccountServicemappingResult = bankAccountMappingRepository
                .searchBankAccountServicemapping(bankAccountServiceMappingSearchCriteria);
        assertSame(objectList, actualSearchBankAccountServicemappingResult);
        assertTrue(actualSearchBankAccountServicemappingResult.isEmpty());
        verify(bankAccountServiceQueryBuilder).BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testSearchBankAccountServicemapping5() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any())).thenReturn("3");
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        BankAccountServiceMappingSearchCriteria bankAccountServiceMappingSearchCriteria = new BankAccountServiceMappingSearchCriteria();
        bankAccountServiceMappingSearchCriteria.setTenantId("42");
        assertTrue(bankAccountMappingRepository.searchBankAccountServicemapping(bankAccountServiceMappingSearchCriteria)
                .isEmpty());
        verify(bankAccountServiceQueryBuilder).BankAccountServiceMappingSearchQuery(
                (BankAccountServiceMappingSearchCriteria) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testSearchBankAccountBranches() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.getAllBankAccountsForServiceQuery()).thenReturn("3");
        ArrayList<Long> resultLongList = new ArrayList<>();
        when(jdbcTemplate.queryForList((String) any(), (Class<Long>) any(), (Object[]) any())).thenReturn(resultLongList);
        List<Long> actualSearchBankAccountBranchesResult = bankAccountMappingRepository.searchBankAccountBranches("42");
        assertSame(resultLongList, actualSearchBankAccountBranchesResult);
        assertTrue(actualSearchBankAccountBranchesResult.isEmpty());
        verify(bankAccountServiceQueryBuilder).getAllBankAccountsForServiceQuery();
        verify(jdbcTemplate).queryForList((String) any(), (Class<Long>) any(), (Object[]) any());
    }

    @Test
    void testSearchBankAccountBranches2() throws DataAccessException {
        when(bankAccountServiceQueryBuilder.getAllBankAccountsForServiceQuery()).thenReturn("3");
        when(jdbcTemplate.queryForList((String) any(), (Class<Long>) any(), (Object[]) any()))
                .thenThrow(new CustomException("Code", "An error occurred"));
        assertThrows(CustomException.class, () -> bankAccountMappingRepository.searchBankAccountBranches("42"));
        verify(bankAccountServiceQueryBuilder).getAllBankAccountsForServiceQuery();
        verify(jdbcTemplate).queryForList((String) any(), (Class<Long>) any(), (Object[]) any());
    }
}

