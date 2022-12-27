package org.egov.demand.repository.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.egov.demand.model.AuditDetail;

import org.egov.demand.model.TaxPeriod;
import org.egov.demand.model.enums.PeriodCycle;
import org.egov.demand.web.contract.TaxPeriodCriteria;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaxPeriodQueryBuilder.class})
@ExtendWith(SpringExtension.class)
class TaxPeriodQueryBuilderTest {
    @Autowired
    private TaxPeriodQueryBuilder taxPeriodQueryBuilder;

    @Test
    void testPrepareSearchQuery() {
        TaxPeriodCriteria taxPeriodCriteria = new TaxPeriodCriteria();
        assertEquals("SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, new ArrayList<>()));
    }

    @Test
    void testPrepareSearchQuery3() {
        HashSet<String> service = new HashSet<>();
        TaxPeriodCriteria taxPeriodCriteria = new TaxPeriodCriteria("42", service, PeriodCycle.MONTH, new HashSet<>(),
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ", 1L, 1L, 1L);

        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.periodcycle"
                        + " = ?  and taxperiod.fromdate >= ?  and taxperiod.todate <= ?  and taxperiod.code = ?  and taxperiod.fromdate"
                        + " <= ? and taxperiod.todate >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        assertEquals(7, objectList.size());
    }

    @Test
    void testPrepareSearchQuery4() {
        TaxPeriodCriteria taxPeriodCriteria = mock(TaxPeriodCriteria.class);
        when(taxPeriodCriteria.getDate()).thenReturn(1L);
        when(taxPeriodCriteria.getFromDate()).thenReturn(1L);
        when(taxPeriodCriteria.getToDate()).thenReturn(1L);
        when(taxPeriodCriteria.getCode()).thenReturn("Code");
        when(taxPeriodCriteria.getTenantId()).thenReturn("42");
        when(taxPeriodCriteria.getId()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getService()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getPeriodCycle()).thenReturn(PeriodCycle.MONTH);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.periodcycle"
                        + " = ?  and taxperiod.fromdate >= ?  and taxperiod.todate <= ?  and taxperiod.code = ?  and taxperiod.fromdate"
                        + " <= ? and taxperiod.todate >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        verify(taxPeriodCriteria, atLeast(1)).getDate();
        verify(taxPeriodCriteria, atLeast(1)).getFromDate();
        verify(taxPeriodCriteria, atLeast(1)).getToDate();
        verify(taxPeriodCriteria, atLeast(1)).getCode();
        verify(taxPeriodCriteria, atLeast(1)).getTenantId();
        verify(taxPeriodCriteria).getId();
        verify(taxPeriodCriteria).getService();
        verify(taxPeriodCriteria).getPeriodCycle();
        assertEquals(7, objectList.size());
    }

    @Test
    void testPrepareSearchQuery5() {
        TaxPeriodCriteria taxPeriodCriteria = mock(TaxPeriodCriteria.class);
        when(taxPeriodCriteria.getDate()).thenReturn(1L);
        when(taxPeriodCriteria.getFromDate()).thenReturn(1L);
        when(taxPeriodCriteria.getToDate()).thenReturn(null);
        when(taxPeriodCriteria.getCode()).thenReturn("Code");
        when(taxPeriodCriteria.getTenantId()).thenReturn("42");
        when(taxPeriodCriteria.getId()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getService()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getPeriodCycle()).thenReturn(PeriodCycle.MONTH);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.periodcycle"
                        + " = ?  and taxperiod.code = ?  and taxperiod.fromdate <= ? and taxperiod.todate >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        verify(taxPeriodCriteria, atLeast(1)).getDate();
        verify(taxPeriodCriteria).getFromDate();
        verify(taxPeriodCriteria).getToDate();
        verify(taxPeriodCriteria, atLeast(1)).getCode();
        verify(taxPeriodCriteria, atLeast(1)).getTenantId();
        verify(taxPeriodCriteria).getId();
        verify(taxPeriodCriteria).getService();
        verify(taxPeriodCriteria).getPeriodCycle();
        assertEquals(5, objectList.size());
    }

    @Test
    void testPrepareSearchQuery6() {
        TaxPeriodCriteria taxPeriodCriteria = mock(TaxPeriodCriteria.class);
        when(taxPeriodCriteria.getDate()).thenReturn(1L);
        when(taxPeriodCriteria.getFromDate()).thenReturn(1L);
        when(taxPeriodCriteria.getToDate()).thenReturn(1L);
        when(taxPeriodCriteria.getCode()).thenReturn(" WHERE ");
        when(taxPeriodCriteria.getTenantId()).thenReturn("42");
        when(taxPeriodCriteria.getId()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getService()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getPeriodCycle()).thenReturn(PeriodCycle.MONTH);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.periodcycle"
                        + " = ?  and taxperiod.fromdate >= ?  and taxperiod.todate <= ?  and taxperiod.code = ?  and taxperiod.fromdate"
                        + " <= ? and taxperiod.todate >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        verify(taxPeriodCriteria, atLeast(1)).getDate();
        verify(taxPeriodCriteria, atLeast(1)).getFromDate();
        verify(taxPeriodCriteria, atLeast(1)).getToDate();
        verify(taxPeriodCriteria, atLeast(1)).getCode();
        verify(taxPeriodCriteria, atLeast(1)).getTenantId();
        verify(taxPeriodCriteria).getId();
        verify(taxPeriodCriteria).getService();
        verify(taxPeriodCriteria).getPeriodCycle();
        assertEquals(7, objectList.size());
    }

    @Test
    void testPrepareSearchQuery7() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ");
        TaxPeriodCriteria taxPeriodCriteria = mock(TaxPeriodCriteria.class);
        when(taxPeriodCriteria.getDate()).thenReturn(1L);
        when(taxPeriodCriteria.getFromDate()).thenReturn(1L);
        when(taxPeriodCriteria.getToDate()).thenReturn(1L);
        when(taxPeriodCriteria.getCode()).thenReturn("Code");
        when(taxPeriodCriteria.getTenantId()).thenReturn("42");
        when(taxPeriodCriteria.getId()).thenReturn(stringSet);
        when(taxPeriodCriteria.getService()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getPeriodCycle()).thenReturn(PeriodCycle.MONTH);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.periodcycle"
                        + " = ?  and taxperiod.fromdate >= ?  and taxperiod.todate <= ?  and taxperiod.code = ?  and taxperiod.id"
                        + " IN  ('SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ') and taxperiod.fromdate <= ? and taxperiod.todate"
                        + " >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        verify(taxPeriodCriteria, atLeast(1)).getDate();
        verify(taxPeriodCriteria, atLeast(1)).getFromDate();
        verify(taxPeriodCriteria, atLeast(1)).getToDate();
        verify(taxPeriodCriteria, atLeast(1)).getCode();
        verify(taxPeriodCriteria, atLeast(1)).getTenantId();
        verify(taxPeriodCriteria).getId();
        verify(taxPeriodCriteria).getService();
        verify(taxPeriodCriteria).getPeriodCycle();
        assertEquals(7, objectList.size());
    }

    @Test
    void testPrepareSearchQuery8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ");
        TaxPeriodCriteria taxPeriodCriteria = mock(TaxPeriodCriteria.class);
        when(taxPeriodCriteria.getDate()).thenReturn(1L);
        when(taxPeriodCriteria.getFromDate()).thenReturn(1L);
        when(taxPeriodCriteria.getToDate()).thenReturn(1L);
        when(taxPeriodCriteria.getCode()).thenReturn("Code");
        when(taxPeriodCriteria.getTenantId()).thenReturn("42");
        when(taxPeriodCriteria.getId()).thenReturn(new HashSet<>());
        when(taxPeriodCriteria.getService()).thenReturn(stringSet);
        when(taxPeriodCriteria.getPeriodCycle()).thenReturn(PeriodCycle.MONTH);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod  WHERE  taxperiod.tenantId = ?  and taxperiod.service"
                        + " IN  ('SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ') and taxperiod.periodcycle = ?  AND (fromdate"
                        + " >=  CASE WHEN ((SELECT fromdate FROM {schema}.egbs_taxperiod WHERE tenantId =? AND ( ? BETWEEN fromdate"
                        + " AND  todate)   AND service IN  ('SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ') AND periodcycle=?)"
                        + " NOTNULL) THEN ( SELECT fromdate FROM {schema}.egbs_taxperiod WHERE tenantId =? AND ( ? BETWEEN fromdate"
                        + " AND  todate) AND service IN  ('SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ') AND periodcycle=?)"
                        + " ELSE (SELECT min(fromdate) FROM {schema}.egbs_taxperiod WHERE tenantId =?) END AND todate <= ( SELECT"
                        + " todate FROM {schema}.egbs_taxperiod WHERE tenantId = ? AND (? BETWEEN fromdate AND  todate)  AND"
                        + " service IN  ('SELECT * FROM {schema}.EGBS_TAXPERIOD taxperiod ') AND periodcycle=?)) and taxperiod.code"
                        + " = ?  and taxperiod.fromdate <= ? and taxperiod.todate >= ? ",
                taxPeriodQueryBuilder.prepareSearchQuery(taxPeriodCriteria, objectList));
        verify(taxPeriodCriteria, atLeast(1)).getDate();
        verify(taxPeriodCriteria, atLeast(1)).getFromDate();
        verify(taxPeriodCriteria, atLeast(1)).getToDate();
        verify(taxPeriodCriteria, atLeast(1)).getCode();
        verify(taxPeriodCriteria, atLeast(1)).getTenantId();
        verify(taxPeriodCriteria).getId();
        verify(taxPeriodCriteria).getService();
        verify(taxPeriodCriteria).getPeriodCycle();
        assertEquals(15, objectList.size());
    }

    @Test
    void testPrepareQueryForValidation() {
        assertEquals("select exists (select * from {schema}.egbs_taxperiod taxperiod where  )",
                taxPeriodQueryBuilder.prepareQueryForValidation(new ArrayList<>(), "Mode"));
    }

    @Test
    void testPrepareQueryForValidation4() {
        TaxPeriod taxPeriod = new TaxPeriod("42", "42", 1L, 1L, PeriodCycle.MONTH, "Service", "Code", "Financial Year",
                new AuditDetail());
        taxPeriod.setFromDate(0L);

        ArrayList<TaxPeriod> taxPeriodList = new ArrayList<>();
        taxPeriodList.add(taxPeriod);
        assertEquals(
                "select exists (select * from {schema}.egbs_taxperiod taxperiod where  (  taxperiod.service = 'Service'"
                        + " and  taxperiod.code = 'Code' and  taxperiod.tenantId = '42' and (( 0 BETWEEN fromdate AND todate OR"
                        + " 1 BETWEEN fromdate AND todate) OR (fromdate BETWEEN 0 AND 1 OR todate BETWEEN 0 AND 1))) )",
                taxPeriodQueryBuilder.prepareQueryForValidation(taxPeriodList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation5() {
        TaxPeriod taxPeriod = new TaxPeriod("42", " ( ", 1L, 1L, PeriodCycle.MONTH, "Service", "Code", "Financial Year",
                new AuditDetail());
        taxPeriod.setFromDate(0L);

        ArrayList<TaxPeriod> taxPeriodList = new ArrayList<>();
        taxPeriodList.add(taxPeriod);
        assertEquals(
                "select exists (select * from {schema}.egbs_taxperiod taxperiod where  (  taxperiod.service = 'Service'"
                        + " and  taxperiod.code = 'Code' and  taxperiod.tenantId = ' ( ' and (( 0 BETWEEN fromdate AND todate OR"
                        + " 1 BETWEEN fromdate AND todate) OR (fromdate BETWEEN 0 AND 1 OR todate BETWEEN 0 AND 1))) )",
                taxPeriodQueryBuilder.prepareQueryForValidation(taxPeriodList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation6() {
        TaxPeriod taxPeriod = new TaxPeriod("42", "42", 1L, 1L, PeriodCycle.MONTH, "Service", "Code", "Financial Year",
                mock(AuditDetail.class));
        taxPeriod.setFromDate(0L);

        ArrayList<TaxPeriod> taxPeriodList = new ArrayList<>();
        taxPeriodList.add(taxPeriod);
        assertEquals(
                "select exists (select * from {schema}.egbs_taxperiod taxperiod where  (  taxperiod.service = 'Service'"
                        + " and  taxperiod.code = 'Code' and  taxperiod.tenantId = '42' and (( 0 BETWEEN fromdate AND todate OR"
                        + " 1 BETWEEN fromdate AND todate) OR (fromdate BETWEEN 0 AND 1 OR todate BETWEEN 0 AND 1))) )",
                taxPeriodQueryBuilder.prepareQueryForValidation(taxPeriodList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation7() {
        TaxPeriod taxPeriod = new TaxPeriod("42", "42", 1L, 1L, PeriodCycle.MONTH, "Service", "Code", "Financial Year",
                new AuditDetail());
        taxPeriod.setFromDate(0L);

        ArrayList<TaxPeriod> taxPeriodList = new ArrayList<>();
        taxPeriodList.add(taxPeriod);
        assertEquals(
                "select exists (select * from {schema}.egbs_taxperiod taxperiod where  (  taxperiod.service = 'Service'"
                        + " and  taxperiod.code = 'Code' and  taxperiod.id != '42' and  taxperiod.tenantId = '42' and (( 0 BETWEEN"
                        + " fromdate AND todate OR 1 BETWEEN fromdate AND todate) OR (fromdate BETWEEN 0 AND 1 OR todate BETWEEN"
                        + " 0 AND 1))) )",
                taxPeriodQueryBuilder.prepareQueryForValidation(taxPeriodList, "edit"));
    }
}

