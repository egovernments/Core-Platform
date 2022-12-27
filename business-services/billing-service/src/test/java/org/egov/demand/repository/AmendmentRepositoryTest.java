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

import com.fasterxml.jackson.databind.node.MissingNode;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.amendment.model.Amendment;
import org.egov.demand.amendment.model.AmendmentCriteria;
import org.egov.demand.amendment.model.AmendmentRequest;
import org.egov.demand.amendment.model.AmendmentUpdate;
import org.egov.demand.amendment.model.Document;
import org.egov.demand.amendment.model.ProcessInstance;
import org.egov.demand.amendment.model.enums.AmendmentReason;
import org.egov.demand.amendment.model.enums.AmendmentStatus;
import org.egov.demand.model.AuditDetails;
import org.egov.demand.model.DemandDetail;
import org.egov.demand.repository.querybuilder.AmendmentQueryBuilder;
import org.egov.demand.repository.rowmapper.AmendmentRowMapper;
import org.egov.demand.util.Util;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AmendmentRepository.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class AmendmentRepositoryTest {
    @MockBean
    private AmendmentQueryBuilder amendmentQueryBuilder;

    @Autowired
    private AmendmentRepository amendmentRepository;

    @MockBean
    private AmendmentRowMapper amendmentRowMapper;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private Util util;

    @Test
    void testSaveAmendment6() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setTenantId("{schema}.");
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        ArrayList<Document> documents = new ArrayList<>();
        AuditDetails auditDetails = new AuditDetails();
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        amendmentRepository.saveAmendment(amendmentRequest);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest, atLeast(1)).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
    }

    @Test
    void testSaveAmendment7() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenThrow(new CustomException("{schema}.", "An error occurred"));

        Amendment amendment = new Amendment();
        amendment.setTenantId("{schema}.");
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        ArrayList<Document> documents = new ArrayList<>();
        AuditDetails auditDetails = new AuditDetails();
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        assertThrows(CustomException.class, () -> amendmentRepository.saveAmendment(amendmentRequest));
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest, atLeast(1)).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
    }

    @Test
    void testSaveAmendment8() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setTenantId("{schema}.");

        ArrayList<DemandDetail> demandDetailList = new ArrayList<>();
        demandDetailList.add(new DemandDetail());
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<Document> documents = new ArrayList<>();
        AuditDetails auditDetails = new AuditDetails();
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetailList,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        amendmentRepository.saveAmendment(amendmentRequest);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest, atLeast(1)).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
    }

    @Test
    void testSaveAmendment9() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setTenantId("{schema}.");

        ArrayList<Document> documentList = new ArrayList<>();
        documentList.add(new Document());
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        AuditDetails auditDetails = new AuditDetails();
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documentList, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        amendmentRepository.saveAmendment(amendmentRequest);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest, atLeast(1)).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
    }

    @Test
    void testSaveAmendment11() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (SqlParameterSource) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setTenantId("{schema}.");
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getCreatedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        ArrayList<Document> documents = new ArrayList<>();
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                "42",
                "INSERT INTO {schema}.egbs_amendment (id, tenantid, amendmentid, businessservice, consumercode,"
                        + " amendmentreason, reasondocumentnumber, status, effectivetill, effectivefrom, amendeddemandid, createdby,"
                        + " createdtime, lastmodifiedby, lastmodifiedtime, additionaldetails) \tVALUES (:id, :tenantid, :amendmentid,"
                        + " :businessservice, :consumercode, :amendmentreason, :reasondocumentnumber, :status, :effectivetill,"
                        + " :effectivefrom, :amendeddemandid, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime,"
                        + " :additionaldetails);",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        amendmentRepository.saveAmendment(amendmentRequest);
        verify(namedParameterJdbcTemplate).update((String) any(), (SqlParameterSource) any());
        verify(namedParameterJdbcTemplate, atLeast(1)).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest, atLeast(1)).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
        verify(auditDetails).getCreatedTime();
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getCreatedBy();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testGetAmendments2() throws DataAccessException {
        when(amendmentQueryBuilder.getSearchQuery((AmendmentCriteria) any(), (MapSqlParameterSource) any()))
                .thenReturn("Search Query");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (SqlParameterSource) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList);

        AmendmentCriteria amendmentCriteria = new AmendmentCriteria();
        amendmentCriteria.setTenantId("42");
        List<Amendment> actualAmendments = amendmentRepository.getAmendments(amendmentCriteria);
        assertSame(objectList, actualAmendments);
        assertTrue(actualAmendments.isEmpty());
        verify(amendmentQueryBuilder).getSearchQuery((AmendmentCriteria) any(), (MapSqlParameterSource) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (SqlParameterSource) any(),
                (ResultSetExtractor<Object>) any());
    }

    @Test
    void testGetAmendments3() throws DataAccessException {
        when(amendmentQueryBuilder.getSearchQuery((AmendmentCriteria) any(), (MapSqlParameterSource) any()))
                .thenReturn("{schema}.");
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (SqlParameterSource) any(),
                (ResultSetExtractor<Object>) any())).thenReturn(objectList);
        AmendmentCriteria amendmentCriteria = mock(AmendmentCriteria.class);
        when(amendmentCriteria.getTenantId()).thenReturn("42");
        doNothing().when(amendmentCriteria).setTenantId((String) any());
        amendmentCriteria.setTenantId("42");
        List<Amendment> actualAmendments = amendmentRepository.getAmendments(amendmentCriteria);
        assertSame(objectList, actualAmendments);
        assertTrue(actualAmendments.isEmpty());
        verify(amendmentQueryBuilder).getSearchQuery((AmendmentCriteria) any(), (MapSqlParameterSource) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (SqlParameterSource) any(),
                (ResultSetExtractor<Object>) any());
        verify(amendmentCriteria).getTenantId();
        verify(amendmentCriteria).setTenantId((String) any());
    }

    @Test
    void testUpdateAmendment() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        amendmentRepository.updateAmendment(new ArrayList<>(), "42");
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateAmendment3() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        amendmentRepository.updateAmendment(new ArrayList<>(), "{schema}.");
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateAmendment4() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenThrow(new CustomException("{schema}.", "An error occurred"));
        assertThrows(CustomException.class, () -> amendmentRepository.updateAmendment(new ArrayList<>(), "42"));
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
    }

    @Test
    void testUpdateAmendment6() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());
        AuditDetails auditDetails = new AuditDetails();
        MissingNode additionalDetails = MissingNode.getInstance();
        ProcessInstance workflow = new ProcessInstance();

        AmendmentUpdate amendmentUpdate = new AmendmentUpdate("42", "42", "42", auditDetails, additionalDetails, workflow,
                AmendmentStatus.ACTIVE, new ArrayList<>());
        amendmentUpdate.setStatus(AmendmentStatus.ACTIVE);

        ArrayList<AmendmentUpdate> amendmentUpdateList = new ArrayList<>();
        amendmentUpdateList.add(amendmentUpdate);
        amendmentRepository.updateAmendment(amendmentUpdateList, "42");
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
    }

    @Test
    void testUpdateAmendment7() {
        when(namedParameterJdbcTemplate.batchUpdate((String) any(), (SqlParameterSource[]) any()))
                .thenReturn(new int[]{1, 1, 1, 1});
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        MissingNode additionalDetails = MissingNode.getInstance();
        ProcessInstance workflow = new ProcessInstance();

        AmendmentUpdate amendmentUpdate = new AmendmentUpdate("42", "42", "42", auditDetails, additionalDetails, workflow,
                AmendmentStatus.ACTIVE, new ArrayList<>());
        amendmentUpdate.setStatus(AmendmentStatus.ACTIVE);

        ArrayList<AmendmentUpdate> amendmentUpdateList = new ArrayList<>();
        amendmentUpdateList.add(amendmentUpdate);
        amendmentRepository.updateAmendment(amendmentUpdateList, "42");
        verify(namedParameterJdbcTemplate).batchUpdate((String) any(), (SqlParameterSource[]) any());
        verify(util).getPGObject((Object) any());
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getLastModifiedBy();
    }

    @Test
    void testGetAmendmentSqlParameter5() {
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setAmendmentReason(AmendmentReason.COURT_CASE_SETTLEMENT);
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        ArrayList<Document> documents = new ArrayList<>();
        AuditDetails auditDetails = new AuditDetails();
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42", "id", "42", "id",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        assertEquals(Short.SIZE,
                amendmentRepository.getAmendmentSqlParameter(amendmentRequest).getParameterNames().length);
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
    }

    @Test
    void testGetAmendmentSqlParameter7() {
        when(util.getPGObject((Object) any())).thenReturn(new PGobject());

        Amendment amendment = new Amendment();
        amendment.setAmendmentReason(AmendmentReason.COURT_CASE_SETTLEMENT);
        AuditDetails auditDetails = mock(AuditDetails.class);
        when(auditDetails.getCreatedTime()).thenReturn(1L);
        when(auditDetails.getLastModifiedTime()).thenReturn(1L);
        when(auditDetails.getCreatedBy()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(auditDetails.getLastModifiedBy()).thenReturn("Jan 1, 2020 9:00am GMT+0100");
        ProcessInstance workflow = new ProcessInstance();
        ArrayList<DemandDetail> demandDetails = new ArrayList<>();
        ArrayList<Document> documents = new ArrayList<>();
        AmendmentRequest amendmentRequest = mock(AmendmentRequest.class);
        when(amendmentRequest.getAmendment()).thenReturn(new Amendment("42", "42", "42", "id", "42", "id",
                AmendmentReason.COURT_CASE_SETTLEMENT, "Just cause", AmendmentStatus.ACTIVE, workflow, demandDetails,
                documents, 1L, 1L, auditDetails, MissingNode.getInstance()));
        doNothing().when(amendmentRequest).setAmendment((Amendment) any());
        amendmentRequest.setAmendment(amendment);
        assertEquals(Short.SIZE,
                amendmentRepository.getAmendmentSqlParameter(amendmentRequest).getParameterNames().length);
        verify(util).getPGObject((Object) any());
        verify(amendmentRequest).getAmendment();
        verify(amendmentRequest).setAmendment((Amendment) any());
        verify(auditDetails).getCreatedTime();
        verify(auditDetails).getLastModifiedTime();
        verify(auditDetails).getCreatedBy();
        verify(auditDetails).getLastModifiedBy();
    }
}

