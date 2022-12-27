package org.egov.demand.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.model.Demand;
import org.egov.demand.model.DemandCriteria;
import org.egov.demand.model.DemandDetail;
import org.egov.demand.model.PaymentBackUpdateAudit;
import org.egov.demand.repository.querybuilder.DemandQueryBuilder;
import org.egov.demand.repository.rowmapper.DemandRowMapper;
import org.egov.demand.util.Util;
import org.egov.demand.web.contract.DemandRequest;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemandRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class DemandRepositoryTest {
    @MockBean
    private DemandQueryBuilder demandQueryBuilder;

    @Autowired
    private DemandRepository demandRepository;

    @MockBean
    private DemandRowMapper demandRowMapper;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private Util util;

    @Test
    void testGetDemands2() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("Demand Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);

        DemandCriteria demandCriteria = new DemandCriteria();
        demandCriteria.setTenantId("42");
        List<Demand> actualDemands = demandRepository.getDemands(demandCriteria);
        assertSame(objectList, actualDemands);
        assertTrue(actualDemands.isEmpty());
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testGetDemands3() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("Demand Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getTenantId()).thenReturn("42");
        doNothing().when(demandCriteria).setTenantId((String) any());
        demandCriteria.setTenantId("42");
        List<Demand> actualDemands = demandRepository.getDemands(demandCriteria);
        assertSame(objectList, actualDemands);
        assertTrue(actualDemands.isEmpty());
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(demandCriteria).getTenantId();
        verify(demandCriteria).setTenantId((String) any());
    }

    @Test
    void testGetDemands4() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("{schema}.");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getTenantId()).thenReturn("42");
        doNothing().when(demandCriteria).setTenantId((String) any());
        demandCriteria.setTenantId("42");
        List<Demand> actualDemands = demandRepository.getDemands(demandCriteria);
        assertSame(objectList, actualDemands);
        assertTrue(actualDemands.isEmpty());
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(demandCriteria).getTenantId();
        verify(demandCriteria).setTenantId((String) any());
    }

    @Test
    void testGetDemandsForConsumerCodes() throws DataAccessException {
        when(demandQueryBuilder.getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any())).thenReturn("Demand Query For Consumer Codes");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        assertThrows(CustomException.class, () -> demandRepository.getDemandsForConsumerCodes(new HashMap<>(), "42"));
        verify(demandQueryBuilder).getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testGetDemandsForConsumerCodes2() throws DataAccessException {
        when(demandQueryBuilder.getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any())).thenReturn("Demand Query For Consumer Codes");
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(objectList);
        List<Demand> actualDemandsForConsumerCodes = demandRepository.getDemandsForConsumerCodes(new HashMap<>(), "42");
        assertSame(objectList, actualDemandsForConsumerCodes);
        assertTrue(actualDemandsForConsumerCodes.isEmpty());
        verify(demandQueryBuilder).getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testGetDemandsForConsumerCodes3() throws DataAccessException {
        when(demandQueryBuilder.getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any())).thenReturn("{schema}.");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        assertThrows(CustomException.class, () -> demandRepository.getDemandsForConsumerCodes(new HashMap<>(), "42"));
        verify(demandQueryBuilder).getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testGetDemandsForConsumerCodes4() throws DataAccessException {
        when(demandQueryBuilder.getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any())).thenReturn("Demand Query For Consumer Codes");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        assertThrows(CustomException.class,
                () -> demandRepository.getDemandsForConsumerCodes(new HashMap<>(), "{schema}."));
        verify(demandQueryBuilder).getDemandQueryForConsumerCodes((Map<String, Set<String>>) any(), (List<Object>) any(),
                (String) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testSave7() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        Demand demand = new Demand();
        demand.addDemandDetailsItem(demandDetail);

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        demandRepository.save(demandRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(demandRequest).getDemands();
    }

    @Test
    void testSave8() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("{schema}.");

        Demand demand = new Demand();
        demand.addDemandDetailsItem(demandDetail);

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        demandRepository.save(demandRequest);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(demandRequest).getDemands();
    }

    @Test
    void testSave9() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        Demand demand = new Demand();
        demand.addDemandDetailsItem(demandDetail);

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        assertThrows(CustomException.class, () -> demandRepository.save(demandRequest));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
        verify(demandRequest).getDemands();
    }

    @Test
    void testUpdate7() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("Demand Query");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());
        Demand demand = mock(Demand.class);
        when(demand.getDemandDetails()).thenThrow(new CustomException("Code", "An error occurred"));
        when(demand.getId()).thenReturn("42");
        when(demand.getTenantId()).thenReturn("42");
        doNothing().when(demand).setTenantId((String) any());
        demand.setTenantId("42");

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        assertThrows(CustomException.class, () -> demandRepository.update(demandRequest, new PaymentBackUpdateAudit()));
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(demandRequest).getDemands();
        verify(demand, atLeast(1)).getId();
        verify(demand).getTenantId();
        verify(demand).getDemandDetails();
        verify(demand).setTenantId((String) any());
    }

    @Test
    void testUpdate8() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("{schema}.");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());
        Demand demand = mock(Demand.class);
        when(demand.getDemandDetails()).thenThrow(new CustomException("Code", "An error occurred"));
        when(demand.getId()).thenReturn("42");
        when(demand.getTenantId()).thenReturn("42");
        doNothing().when(demand).setTenantId((String) any());
        demand.setTenantId("42");

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        assertThrows(CustomException.class, () -> demandRepository.update(demandRequest, new PaymentBackUpdateAudit()));
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(demandRequest).getDemands();
        verify(demand, atLeast(1)).getId();
        verify(demand).getTenantId();
        verify(demand).getDemandDetails();
        verify(demand).setTenantId((String) any());
    }

    @Test
    void testUpdate10() throws DataAccessException {
        when(demandQueryBuilder.getDemandQuery((DemandCriteria) any(), (List<Object>) any())).thenReturn("Demand Query");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());
        Demand demand = mock(Demand.class);
        when(demand.getDemandDetails()).thenThrow(new CustomException("Code", "An error occurred"));
        when(demand.getId()).thenReturn("42");
        when(demand.getTenantId()).thenReturn("{schema}.");
        doNothing().when(demand).setTenantId((String) any());
        demand.setTenantId("42");

        ArrayList<Demand> demandList = new ArrayList<>();
        demandList.add(demand);
        DemandRequest demandRequest = mock(DemandRequest.class);
        when(demandRequest.getDemands()).thenReturn(demandList);
        assertThrows(CustomException.class, () -> demandRepository.update(demandRequest, new PaymentBackUpdateAudit()));
        verify(demandQueryBuilder).getDemandQuery((DemandCriteria) any(), (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(demandRequest).getDemands();
        verify(demand, atLeast(1)).getId();
        verify(demand).getTenantId();
        verify(demand).getDemandDetails();
        verify(demand).setTenantId((String) any());
    }

    @Test
    void testInsertBatch3() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> newDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.insertBatch(newDemands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBatch4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> newDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("{schema}.");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.insertBatch(newDemands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBatch5() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        ArrayList<Demand> newDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        assertThrows(CustomException.class, () -> demandRepository.insertBatch(newDemands, demandDetailList));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testUpdateBatch3() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> oldDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.updateBatch(oldDemands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testUpdateBatch4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> oldDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("{schema}.");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.updateBatch(oldDemands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testUpdateBatch5() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        ArrayList<Demand> oldDemands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        assertThrows(CustomException.class, () -> demandRepository.updateBatch(oldDemands, demandDetailList));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBatchForAudit3() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> demands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.insertBatchForAudit(demands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBatchForAudit4() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        ArrayList<Demand> demands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("{schema}.");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        demandRepository.insertBatchForAudit(demands, demandDetailList);
        verify(jdbcTemplate, atLeast(1)).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBatchForAudit5() throws DataAccessException {
        when(jdbcTemplate.batchUpdate((String) any(), (BatchPreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        ArrayList<Demand> demands = new ArrayList<>();

        DemandDetail demandDetail = new DemandDetail();
        demandDetail.setTenantId("42");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(demandDetail);
        assertThrows(CustomException.class, () -> demandRepository.insertBatchForAudit(demands, demandDetailList));
        verify(jdbcTemplate).batchUpdate((String) any(), (BatchPreparedStatementSetter) any());
    }

    @Test
    void testInsertBackUpdateForPayment2() throws DataAccessException {
        when(jdbcTemplate.update((String) any(), (PreparedStatementSetter) any())).thenReturn(1);

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("42");
        demandRepository.insertBackUpdateForPayment(paymentBackUpdateAudit);
        verify(jdbcTemplate).update((String) any(), (PreparedStatementSetter) any());
    }

    @Test
    void testInsertBackUpdateForPayment3() throws DataAccessException {
        when(jdbcTemplate.update((String) any(), (PreparedStatementSetter) any())).thenReturn(1);

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("{schema}.");
        demandRepository.insertBackUpdateForPayment(paymentBackUpdateAudit);
        verify(jdbcTemplate).update((String) any(), (PreparedStatementSetter) any());
    }

    @Test
    void testInsertBackUpdateForPayment4() throws DataAccessException {
        when(jdbcTemplate.update((String) any(), (PreparedStatementSetter) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("42");
        assertThrows(CustomException.class, () -> demandRepository.insertBackUpdateForPayment(paymentBackUpdateAudit));
        verify(jdbcTemplate).update((String) any(), (PreparedStatementSetter) any());
    }

    @Test
    void testSearchPaymentBackUpdateAudit2() throws DataAccessException {
        when(jdbcTemplate.queryForObject((String) any(), (Object[]) any(), (Class<String>) any()))
                .thenReturn("Query For Object");

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("42");
        assertEquals("Query For Object", demandRepository.searchPaymentBackUpdateAudit(paymentBackUpdateAudit));
        verify(jdbcTemplate).queryForObject((String) any(), (Object[]) any(), (Class<String>) any());
    }

    @Test
    void testSearchPaymentBackUpdateAudit3() throws DataAccessException {
        when(jdbcTemplate.queryForObject((String) any(), (Object[]) any(), (Class<String>) any()))
                .thenReturn("Query For Object");

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("{schema}.");
        assertEquals("Query For Object", demandRepository.searchPaymentBackUpdateAudit(paymentBackUpdateAudit));
        verify(jdbcTemplate).queryForObject((String) any(), (Object[]) any(), (Class<String>) any());
    }

    @Test
    void testSearchPaymentBackUpdateAudit4() throws DataAccessException {
        when(jdbcTemplate.queryForObject((String) any(), (Object[]) any(), (Class<String>) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("42");
        assertThrows(CustomException.class, () -> demandRepository.searchPaymentBackUpdateAudit(paymentBackUpdateAudit));
        verify(jdbcTemplate).queryForObject((String) any(), (Object[]) any(), (Class<String>) any());
    }

    @Test
    void testSearchPaymentBackUpdateAudit5() throws DataAccessException {
        when(jdbcTemplate.queryForObject((String) any(), (Object[]) any(), (Class<String>) any()))
                .thenThrow(new EmptyResultDataAccessException(3));

        PaymentBackUpdateAudit paymentBackUpdateAudit = new PaymentBackUpdateAudit();
        paymentBackUpdateAudit.setTenantId("42");
        assertNull(demandRepository.searchPaymentBackUpdateAudit(paymentBackUpdateAudit));
        verify(jdbcTemplate).queryForObject((String) any(), (Object[]) any(), (Class<String>) any());
    }
}

