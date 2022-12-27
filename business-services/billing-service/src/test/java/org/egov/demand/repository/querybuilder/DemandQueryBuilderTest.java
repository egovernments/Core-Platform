package org.egov.demand.repository.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.demand.model.DemandCriteria;
import org.egov.demand.model.enums.Type;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemandQueryBuilder.class, MultiStateInstanceUtil.class})
@ExtendWith(SpringExtension.class)
class DemandQueryBuilderTest {
    @Autowired
    private DemandQueryBuilder demandQueryBuilder;

    @Test
    void testGetDemandQueryForConsumerCodes() {
        HashMap<String, Set<String>> businessConsumercodeMap = new HashMap<>();
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE dmd.tenantid=? AND dmd.status='ACTIVE' ",
                demandQueryBuilder.getDemandQueryForConsumerCodes(businessConsumercodeMap, objectList, "42"));
        assertEquals(1, objectList.size());
    }

    @Test
    void testGetDemandQueryForConsumerCodes2() {
        HashMap<String, Set<String>> stringSetMap = new HashMap<>();
        stringSetMap.put(DemandQueryBuilder.BASE_DEMAND_QUERY, new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE dmd.tenantid=? AND dmd.status='ACTIVE' ",
                demandQueryBuilder.getDemandQueryForConsumerCodes(stringSetMap, objectList, "42"));
        assertEquals(1, objectList.size());
    }

    @Test
    void testGetDemandQueryForConsumerCodes3() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add(DemandQueryBuilder.BASE_DEMAND_QUERY);

        HashMap<String, Set<String>> stringSetMap = new HashMap<>();
        stringSetMap.put(DemandQueryBuilder.BASE_DEMAND_QUERY, stringSet);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE dmd.tenantid=? AND dmd.status='ACTIVE' AND dmd.businessservice='SELECT"
                        + " dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE ' AND dmd.consumercode IN ( ? )",
                demandQueryBuilder.getDemandQueryForConsumerCodes(stringSetMap, objectList, "42"));
        assertEquals(2, objectList.size());
    }

    @Test
    void testGetDemandQueryForConsumerCodes4() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        stringSet.add(DemandQueryBuilder.BASE_DEMAND_QUERY);

        HashMap<String, Set<String>> stringSetMap = new HashMap<>();
        stringSetMap.put(DemandQueryBuilder.BASE_DEMAND_QUERY, stringSet);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE dmd.tenantid=? AND dmd.status='ACTIVE' AND dmd.businessservice='SELECT"
                        + " dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE ' AND dmd.consumercode IN ( ? , ? )",
                demandQueryBuilder.getDemandQueryForConsumerCodes(stringSetMap, objectList, "42"));
        assertEquals(3, objectList.size());
    }

    @Test
    void testGetDemandQuery2() {
        DemandCriteria demandCriteria = new DemandCriteria();
        demandCriteria.setTenantId("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  ORDER BY dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        assertEquals(1, objectList.size());
    }

    @Test
    void testGetDemandQuery3() {
        HashSet<String> demandId = new HashSet<>();
        HashSet<String> payer = new HashSet<>();
        HashSet<String> consumerCode = new HashSet<>();
        BigDecimal demandFrom = BigDecimal.valueOf(42L);
        DemandCriteria demandCriteria = new DemandCriteria("42", demandId, payer, consumerCode,
                DemandQueryBuilder.BASE_DEMAND_QUERY, demandFrom, BigDecimal.valueOf(42L), 1L, 1L, Type.ARREARS, "42",
                "jane.doe@example.org", DemandQueryBuilder.BASE_DEMAND_QUERY, true, true);

        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode"
                        + ",dmdl.taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS"
                        + " dlcreatedby,dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime"
                        + " AS dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails"
                        + " FROM {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid"
                        + " AND dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  AND dmd.status=? AND dmd.businessservice=?"
                        + " AND dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo <= ? ORDER BY"
                        + " dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetDemandQuery5() {
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getIsPaymentCompleted()).thenReturn(true);
        when(demandCriteria.getPeriodFrom()).thenReturn(1L);
        when(demandCriteria.getPeriodTo()).thenReturn(1L);
        when(demandCriteria.getBusinessService()).thenReturn("Business Service");
        when(demandCriteria.getStatus()).thenReturn("Status");
        when(demandCriteria.getConsumerCode()).thenReturn(new HashSet<>());
        when(demandCriteria.getDemandId()).thenReturn(new HashSet<>());
        when(demandCriteria.getPayer()).thenReturn(new HashSet<>());
        when(demandCriteria.getTenantId()).thenReturn("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode"
                        + ",dmdl.taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS"
                        + " dlcreatedby,dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime"
                        + " AS dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails"
                        + " FROM {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid"
                        + " AND dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  AND dmd.status=? AND dmd.businessservice=?"
                        + " AND dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo <= ? ORDER BY"
                        + " dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        verify(demandCriteria, atLeast(1)).getIsPaymentCompleted();
        verify(demandCriteria, atLeast(1)).getPeriodFrom();
        verify(demandCriteria, atLeast(1)).getPeriodTo();
        verify(demandCriteria, atLeast(1)).getBusinessService();
        verify(demandCriteria, atLeast(1)).getStatus();
        verify(demandCriteria, atLeast(1)).getTenantId();
        verify(demandCriteria, atLeast(1)).getConsumerCode();
        verify(demandCriteria, atLeast(1)).getDemandId();
        verify(demandCriteria).getPayer();
        assertEquals(6, objectList.size());
    }

    @Test
    void testGetDemandQuery6() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add(DemandQueryBuilder.BASE_DEMAND_QUERY);
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getIsPaymentCompleted()).thenReturn(true);
        when(demandCriteria.getPeriodFrom()).thenReturn(1L);
        when(demandCriteria.getPeriodTo()).thenReturn(1L);
        when(demandCriteria.getBusinessService()).thenReturn("Business Service");
        when(demandCriteria.getStatus()).thenReturn("Status");
        when(demandCriteria.getConsumerCode()).thenReturn(stringSet);
        when(demandCriteria.getDemandId()).thenReturn(new HashSet<>());
        when(demandCriteria.getPayer()).thenReturn(new HashSet<>());
        when(demandCriteria.getTenantId()).thenReturn("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  AND dmd.status=? AND dmd.businessservice=? AND"
                        + " dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo <= ? AND dmd.consumercode"
                        + " IN ( ? ) ORDER BY dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        verify(demandCriteria, atLeast(1)).getIsPaymentCompleted();
        verify(demandCriteria, atLeast(1)).getPeriodFrom();
        verify(demandCriteria, atLeast(1)).getPeriodTo();
        verify(demandCriteria, atLeast(1)).getBusinessService();
        verify(demandCriteria, atLeast(1)).getStatus();
        verify(demandCriteria, atLeast(1)).getTenantId();
        verify(demandCriteria, atLeast(1)).getConsumerCode();
        verify(demandCriteria, atLeast(1)).getDemandId();
        verify(demandCriteria).getPayer();
        assertEquals(7, objectList.size());
    }

    @Test
    void testGetDemandQuery7() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add(DemandQueryBuilder.BASE_DEMAND_QUERY);
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getIsPaymentCompleted()).thenReturn(true);
        when(demandCriteria.getPeriodFrom()).thenReturn(1L);
        when(demandCriteria.getPeriodTo()).thenReturn(1L);
        when(demandCriteria.getBusinessService()).thenReturn("Business Service");
        when(demandCriteria.getStatus()).thenReturn("Status");
        when(demandCriteria.getConsumerCode()).thenReturn(new HashSet<>());
        when(demandCriteria.getDemandId()).thenReturn(stringSet);
        when(demandCriteria.getPayer()).thenReturn(new HashSet<>());
        when(demandCriteria.getTenantId()).thenReturn("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  AND dmd.status=? AND dmd.id IN ( ? ) AND"
                        + " dmd.businessservice=? AND dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo"
                        + " <= ? ORDER BY dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        verify(demandCriteria, atLeast(1)).getIsPaymentCompleted();
        verify(demandCriteria, atLeast(1)).getPeriodFrom();
        verify(demandCriteria, atLeast(1)).getPeriodTo();
        verify(demandCriteria, atLeast(1)).getBusinessService();
        verify(demandCriteria, atLeast(1)).getStatus();
        verify(demandCriteria, atLeast(1)).getTenantId();
        verify(demandCriteria, atLeast(1)).getConsumerCode();
        verify(demandCriteria, atLeast(1)).getDemandId();
        verify(demandCriteria).getPayer();
        assertEquals(7, objectList.size());
    }

    @Test
    void testGetDemandQuery8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add(DemandQueryBuilder.BASE_DEMAND_QUERY);
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getIsPaymentCompleted()).thenReturn(true);
        when(demandCriteria.getPeriodFrom()).thenReturn(1L);
        when(demandCriteria.getPeriodTo()).thenReturn(1L);
        when(demandCriteria.getBusinessService()).thenReturn("Business Service");
        when(demandCriteria.getStatus()).thenReturn("Status");
        when(demandCriteria.getConsumerCode()).thenReturn(new HashSet<>());
        when(demandCriteria.getDemandId()).thenReturn(new HashSet<>());
        when(demandCriteria.getPayer()).thenReturn(stringSet);
        when(demandCriteria.getTenantId()).thenReturn("42");
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode,dmdl"
                        + ".taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS dlcreatedby"
                        + ",dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime AS"
                        + " dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails FROM"
                        + " {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid AND"
                        + " dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid LIKE ?  AND dmd.status=? AND dmd.payer IN ( ? ) AND"
                        + " dmd.businessservice=? AND dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo"
                        + " <= ? ORDER BY dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        verify(demandCriteria, atLeast(1)).getIsPaymentCompleted();
        verify(demandCriteria, atLeast(1)).getPeriodFrom();
        verify(demandCriteria, atLeast(1)).getPeriodTo();
        verify(demandCriteria, atLeast(1)).getBusinessService();
        verify(demandCriteria, atLeast(1)).getStatus();
        verify(demandCriteria, atLeast(1)).getTenantId();
        verify(demandCriteria, atLeast(1)).getConsumerCode();
        verify(demandCriteria, atLeast(1)).getDemandId();
        verify(demandCriteria, atLeast(1)).getPayer();
        assertEquals(7, objectList.size());
    }

    @Test
    void testGetDemandQuery9() {
        DemandCriteria demandCriteria = mock(DemandCriteria.class);
        when(demandCriteria.getIsPaymentCompleted()).thenReturn(true);
        when(demandCriteria.getPeriodFrom()).thenReturn(1L);
        when(demandCriteria.getPeriodTo()).thenReturn(1L);
        when(demandCriteria.getBusinessService()).thenReturn("Business Service");
        when(demandCriteria.getStatus()).thenReturn("Status");
        when(demandCriteria.getConsumerCode()).thenReturn(new HashSet<>());
        when(demandCriteria.getDemandId()).thenReturn(new HashSet<>());
        when(demandCriteria.getPayer()).thenReturn(new HashSet<>());
        when(demandCriteria.getTenantId()).thenReturn(DemandQueryBuilder.BASE_DEMAND_QUERY);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT dmd.id AS did,dmd.consumercode AS dconsumercode,dmd.consumertype AS dconsumertype,dmd.businessservice"
                        + " AS dbusinessservice,dmd.payer,dmd.billexpirytime AS dbillexpirytime, dmd.fixedBillExpiryDate as"
                        + " dfixedBillExpiryDate, dmd.taxperiodfrom AS dtaxperiodfrom,dmd.taxperiodto AS dtaxperiodto,dmd"
                        + ".minimumamountpayable AS dminimumamountpayable,dmd.createdby AS dcreatedby,dmd.lastmodifiedby AS"
                        + " dlastmodifiedby,dmd.createdtime AS dcreatedtime,dmd.lastmodifiedtime AS dlastmodifiedtime,dmd.tenantid"
                        + " AS dtenantid,dmd.status,dmd.additionaldetails as demandadditionaldetails,dmd.ispaymentcompleted as"
                        + " ispaymentcompleted,dmdl.id AS dlid,dmdl.demandid AS dldemandid,dmdl.taxheadcode AS dltaxheadcode"
                        + ",dmdl.taxamount AS dltaxamount,dmdl.collectionamount AS dlcollectionamount,dmdl.createdby AS"
                        + " dlcreatedby,dmdl.lastModifiedby AS dllastModifiedby,dmdl.createdtime AS dlcreatedtime,dmdl.lastModifiedtime"
                        + " AS dllastModifiedtime,dmdl.tenantid AS dltenantid,dmdl.additionaldetails as detailadditionaldetails"
                        + " FROM {schema}.egbs_demand_v1 dmd INNER JOIN {schema}.egbs_demanddetail_v1 dmdl ON dmd.id=dmdl.demandid"
                        + " AND dmd.tenantid=dmdl.tenantid WHERE  dmd.tenantid = ?  AND dmd.status=? AND dmd.businessservice=?"
                        + " AND dmd.ispaymentcompleted = ? AND dmd.taxPeriodFrom >= ? AND dmd.taxPeriodTo <= ? ORDER BY"
                        + " dmd.taxperiodfrom",
                demandQueryBuilder.getDemandQuery(demandCriteria, objectList));
        verify(demandCriteria, atLeast(1)).getIsPaymentCompleted();
        verify(demandCriteria, atLeast(1)).getPeriodFrom();
        verify(demandCriteria, atLeast(1)).getPeriodTo();
        verify(demandCriteria, atLeast(1)).getBusinessService();
        verify(demandCriteria, atLeast(1)).getStatus();
        verify(demandCriteria, atLeast(1)).getTenantId();
        verify(demandCriteria, atLeast(1)).getConsumerCode();
        verify(demandCriteria, atLeast(1)).getDemandId();
        verify(demandCriteria).getPayer();
        assertEquals(6, objectList.size());
    }
}

