package org.egov.demand.repository.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.egov.demand.model.TaxHeadMasterCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaxHeadMasterQueryBuilder.class})
@ExtendWith(SpringExtension.class)
class TaxHeadMasterQueryBuilderTest {
    @Autowired
    private TaxHeadMasterQueryBuilder taxHeadMasterQueryBuilder;

    @Test
    void testGetQuery() {
        TaxHeadMasterCriteria searchTaxHead = new TaxHeadMasterCriteria();
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  ORDER BY taxhead"
                        + ".validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(searchTaxHead, objectList));
        assertEquals(1, objectList.size());
    }

    @Test
    void testGetQuery2() {
        TaxHeadMasterCriteria taxHeadMasterCriteria = new TaxHeadMasterCriteria();
        taxHeadMasterCriteria.setTenantId("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  ORDER BY taxhead"
                        + ".validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        assertEquals(1, objectList.size());
    }

    @Test
    void testGetQuery4() {
        HashSet<String> code = new HashSet<>();
        TaxHeadMasterCriteria searchTaxHead = new TaxHeadMasterCriteria("42",
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ? ",
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ? ",
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ? ",
                code, true, true, new HashSet<>());

        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.service"
                        + " = ? AND taxhead.name like ? AND taxhead.category = ? AND taxhead.isActualDemand = ? AND taxhead.isDebit"
                        + " = ? ORDER BY taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(searchTaxHead, objectList));
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetQuery5() {
        TaxHeadMasterCriteria taxHeadMasterCriteria = mock(TaxHeadMasterCriteria.class);
        when(taxHeadMasterCriteria.getIsActualDemand()).thenReturn(true);
        when(taxHeadMasterCriteria.getIsDebit()).thenReturn(true);
        when(taxHeadMasterCriteria.getCategory()).thenReturn("Category");
        when(taxHeadMasterCriteria.getName()).thenReturn("Name");
        when(taxHeadMasterCriteria.getService()).thenReturn("Service");
        when(taxHeadMasterCriteria.getTenantId()).thenReturn("42");
        when(taxHeadMasterCriteria.getCode()).thenReturn(new HashSet<>());
        when(taxHeadMasterCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.service"
                        + " = ? AND taxhead.name like ? AND taxhead.category = ? AND taxhead.isActualDemand = ? AND taxhead.isDebit"
                        + " = ? ORDER BY taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        verify(taxHeadMasterCriteria, atLeast(1)).getIsActualDemand();
        verify(taxHeadMasterCriteria, atLeast(1)).getIsDebit();
        verify(taxHeadMasterCriteria, atLeast(1)).getCategory();
        verify(taxHeadMasterCriteria, atLeast(1)).getName();
        verify(taxHeadMasterCriteria, atLeast(1)).getService();
        verify(taxHeadMasterCriteria, atLeast(1)).getTenantId();
        verify(taxHeadMasterCriteria, atLeast(1)).getCode();
        verify(taxHeadMasterCriteria, atLeast(1)).getId();
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetQuery6() {
        TaxHeadMasterCriteria taxHeadMasterCriteria = mock(TaxHeadMasterCriteria.class);
        when(taxHeadMasterCriteria.getIsActualDemand()).thenReturn(true);
        when(taxHeadMasterCriteria.getIsDebit()).thenReturn(true);
        when(taxHeadMasterCriteria.getCategory()).thenReturn("Category");
        when(taxHeadMasterCriteria.getName()).thenReturn("Name");
        when(taxHeadMasterCriteria.getService()).thenReturn("Service");
        when(taxHeadMasterCriteria.getTenantId()).thenReturn(null);
        when(taxHeadMasterCriteria.getCode()).thenReturn(new HashSet<>());
        when(taxHeadMasterCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.service"
                        + " = ? AND taxhead.name like ? AND taxhead.category = ? AND taxhead.isActualDemand = ? AND taxhead.isDebit"
                        + " = ? ORDER BY taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        verify(taxHeadMasterCriteria, atLeast(1)).getIsActualDemand();
        verify(taxHeadMasterCriteria, atLeast(1)).getIsDebit();
        verify(taxHeadMasterCriteria, atLeast(1)).getCategory();
        verify(taxHeadMasterCriteria, atLeast(1)).getName();
        verify(taxHeadMasterCriteria, atLeast(1)).getService();
        verify(taxHeadMasterCriteria, atLeast(1)).getTenantId();
        verify(taxHeadMasterCriteria, atLeast(1)).getCode();
        verify(taxHeadMasterCriteria, atLeast(1)).getId();
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetQuery7() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ? ");
        TaxHeadMasterCriteria taxHeadMasterCriteria = mock(TaxHeadMasterCriteria.class);
        when(taxHeadMasterCriteria.getIsActualDemand()).thenReturn(true);
        when(taxHeadMasterCriteria.getIsDebit()).thenReturn(true);
        when(taxHeadMasterCriteria.getCategory()).thenReturn("Category");
        when(taxHeadMasterCriteria.getName()).thenReturn("Name");
        when(taxHeadMasterCriteria.getService()).thenReturn("Service");
        when(taxHeadMasterCriteria.getTenantId()).thenReturn("42");
        when(taxHeadMasterCriteria.getCode()).thenReturn(stringSet);
        when(taxHeadMasterCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.service"
                        + " = ? AND taxhead.name like ? AND taxhead.code IN ('SELECT *,taxhead.id AS taxheadId, taxhead.tenantid"
                        + " AS taxheadTenantid, taxhead.service taxheadService, taxhead.createdby AS taxcreatedby, taxhead.createdtime"
                        + " AS taxcreatedtime, taxhead.lAStmodifiedby AS taxlAStmodifiedby, taxhead.lAStmodifiedtime AS"
                        + " taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid AS glCodeTenantId,glcode.service AS"
                        + " glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime AS glcreatedtime, glcode.lastmodifiedby"
                        + " AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime FROM {schema}.egbs_taxheadmaster"
                        + " taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code=glcode.taxhead and taxhead.tenantid"
                        + "=glcode.tenantid  WHERE taxhead.tenantId = ? ') AND taxhead.category = ? AND taxhead.isActualDemand ="
                        + " ? AND taxhead.isDebit = ? ORDER BY taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        verify(taxHeadMasterCriteria, atLeast(1)).getIsActualDemand();
        verify(taxHeadMasterCriteria, atLeast(1)).getIsDebit();
        verify(taxHeadMasterCriteria, atLeast(1)).getCategory();
        verify(taxHeadMasterCriteria, atLeast(1)).getName();
        verify(taxHeadMasterCriteria, atLeast(1)).getService();
        verify(taxHeadMasterCriteria, atLeast(1)).getTenantId();
        verify(taxHeadMasterCriteria, atLeast(1)).getCode();
        verify(taxHeadMasterCriteria, atLeast(1)).getId();
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetQuery8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ? ");
        TaxHeadMasterCriteria taxHeadMasterCriteria = mock(TaxHeadMasterCriteria.class);
        when(taxHeadMasterCriteria.getIsActualDemand()).thenReturn(true);
        when(taxHeadMasterCriteria.getIsDebit()).thenReturn(true);
        when(taxHeadMasterCriteria.getCategory()).thenReturn("Category");
        when(taxHeadMasterCriteria.getName()).thenReturn("Name");
        when(taxHeadMasterCriteria.getService()).thenReturn("Service");
        when(taxHeadMasterCriteria.getTenantId()).thenReturn("42");
        when(taxHeadMasterCriteria.getCode()).thenReturn(new HashSet<>());
        when(taxHeadMasterCriteria.getId()).thenReturn(stringSet);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.service"
                        + " = ? AND taxhead.name like ? AND taxhead.id IN ('SELECT *,taxhead.id AS taxheadId, taxhead.tenantid"
                        + " AS taxheadTenantid, taxhead.service taxheadService, taxhead.createdby AS taxcreatedby, taxhead.createdtime"
                        + " AS taxcreatedtime, taxhead.lAStmodifiedby AS taxlAStmodifiedby, taxhead.lAStmodifiedtime AS"
                        + " taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid AS glCodeTenantId,glcode.service AS"
                        + " glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime AS glcreatedtime, glcode.lastmodifiedby"
                        + " AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime FROM {schema}.egbs_taxheadmaster"
                        + " taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code=glcode.taxhead and taxhead.tenantid"
                        + "=glcode.tenantid  WHERE taxhead.tenantId = ? ') AND taxhead.category = ? AND taxhead.isActualDemand ="
                        + " ? AND taxhead.isDebit = ? ORDER BY taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        verify(taxHeadMasterCriteria, atLeast(1)).getIsActualDemand();
        verify(taxHeadMasterCriteria, atLeast(1)).getIsDebit();
        verify(taxHeadMasterCriteria, atLeast(1)).getCategory();
        verify(taxHeadMasterCriteria, atLeast(1)).getName();
        verify(taxHeadMasterCriteria, atLeast(1)).getService();
        verify(taxHeadMasterCriteria, atLeast(1)).getTenantId();
        verify(taxHeadMasterCriteria, atLeast(1)).getId();
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetQuery9() {
        TaxHeadMasterCriteria taxHeadMasterCriteria = mock(TaxHeadMasterCriteria.class);
        when(taxHeadMasterCriteria.getIsActualDemand()).thenReturn(true);
        when(taxHeadMasterCriteria.getIsDebit()).thenReturn(true);
        when(taxHeadMasterCriteria.getCategory()).thenReturn("Category");
        when(taxHeadMasterCriteria.getName()).thenReturn("Name");
        when(taxHeadMasterCriteria.getService()).thenReturn(null);
        when(taxHeadMasterCriteria.getTenantId()).thenReturn(null);
        when(taxHeadMasterCriteria.getCode()).thenReturn(new HashSet<>());
        when(taxHeadMasterCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT *,taxhead.id AS taxheadId, taxhead.tenantid AS taxheadTenantid, taxhead.service taxheadService,"
                        + " taxhead.createdby AS taxcreatedby, taxhead.createdtime AS taxcreatedtime, taxhead.lAStmodifiedby AS"
                        + " taxlAStmodifiedby, taxhead.lAStmodifiedtime AS taxlAStmodifiedtime,glcode.id AS glCodeId, glcode.tenantid"
                        + " AS glCodeTenantId,glcode.service AS glCodeService, glcode.createdby AS glcreatedby, glcode.createdtime"
                        + " AS glcreatedtime, glcode.lastmodifiedby AS gllastmodifiedby, glcode.lastmodifiedtime AS gllastmodifiedtime"
                        + " FROM {schema}.egbs_taxheadmaster taxhead LEFT OUTER Join egbs_glcodemaster glcode  ON taxhead.code"
                        + "=glcode.taxhead and taxhead.tenantid=glcode.tenantid  WHERE taxhead.tenantId = ?  AND taxhead.name"
                        + " like ? AND taxhead.category = ? AND taxhead.isActualDemand = ? AND taxhead.isDebit = ? ORDER BY"
                        + " taxhead.validfrom,taxhead.code LIMIT ?",
                taxHeadMasterQueryBuilder.getQuery(taxHeadMasterCriteria, objectList));
        verify(taxHeadMasterCriteria, atLeast(1)).getIsActualDemand();
        verify(taxHeadMasterCriteria, atLeast(1)).getIsDebit();
        verify(taxHeadMasterCriteria, atLeast(1)).getCategory();
        verify(taxHeadMasterCriteria, atLeast(1)).getName();
        verify(taxHeadMasterCriteria, atLeast(1)).getService();
        verify(taxHeadMasterCriteria, atLeast(1)).getTenantId();
        verify(taxHeadMasterCriteria, atLeast(1)).getCode();
        verify(taxHeadMasterCriteria, atLeast(1)).getId();
        Assertions.assertEquals(5, objectList.size());
    }
}

