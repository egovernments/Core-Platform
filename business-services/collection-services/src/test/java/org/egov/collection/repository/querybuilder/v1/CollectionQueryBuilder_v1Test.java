package org.egov.collection.repository.querybuilder.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.egov.collection.model.v1.ReceiptSearchCriteria_v1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CollectionQueryBuilder_v1Test {

    @Test
    void testGetReceiptSearchQuery() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();
        ReceiptSearchCriteria_v1 searchCriteria = new ReceiptSearchCriteria_v1();
        assertEquals("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ",
                collectionQueryBuilder_v1.getReceiptSearchQuery(searchCriteria, new HashMap<>()));
    }

    @Test
    void testGetReceiptSearchQuery8() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  rh.fund = :fund AND"
                        + " rh.department = :department AND rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND"
                        + " rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND"
                        + " rh.payermobile = :payermobile AND rh.id in (select id from egcl_receiptheader_v1 where tenantid ="
                        + " :tenantId order by createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(10, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery9() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(0);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  rh.fund = :fund AND"
                        + " rh.department = :department AND rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND"
                        + " rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND"
                        + " rh.payermobile = :payermobile",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(8, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery10() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn("42");
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  rh.fund = :fund AND"
                        + " rh.department = :department AND rh.createdBy = :collectedBy AND rh.receiptDate >= :fromDate AND"
                        + " rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid"
                        + " = :transactionId  AND rh.payermobile = :payermobile AND rh.id in (select id from egcl_receiptheader_v1"
                        + " where tenantid = :tenantId order by createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery11() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        ArrayList<String> stringList = new ArrayList<>();
        stringList
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(stringList);
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType,"
                        + " rh.receiptNumber as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as"
                        + " rh_referenceDesc, rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function"
                        + " as rh_function, rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate,"
                        + " rh.businessDetails as rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as"
                        + " rh_stateId,rh.location as rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status"
                        + ",rh.reasonForCancellation as rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount"
                        + " as rh_totalAmount, rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd"
                        + ",rh.consumerCode as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel"
                        + " as rh_channel,rh.reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund"
                        + " as rh_fund,rh.fundSource as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department"
                        + ",rh.depositedBranch as rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg"
                        + ",rh.voucherheader as rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh"
                        + ".additionalDetails as rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as"
                        + " rh_createdDate,rh.lastModifiedBy as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate,"
                        + " rh.demandid as rh_demandid, rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate,"
                        + " rh.transactionid as rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as"
                        + " rd_adjustedamount, rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand"
                        + "  as rd_isActualDemand,  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails"
                        + " as rd_additionalDetails, rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid"
                        + " as rd_demanddetailid, ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate"
                        + " as ins_transactiondate, ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as"
                        + " ins_instrumenttype, ins .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid ,"
                        + " ins.branchname as ins_branchname , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode"
                        + " , ins.financialstatus as ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee"
                        + " as ins_payee , ins.drawer as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno"
                        + " as ins_serialno , ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,"
                        + "  ins.createddate as ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate"
                        + " as ins_lastmodifieddate , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate,"
                        + " ins.instrumentNumber as ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN"
                        + " egcl_receiptdetails_v1 rd ON rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins"
                        + " ON rh.id=recins.receiptheader LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id"
                        + "  WHERE  rh.fund = :fund AND rh.department = :department AND rh.receiptDate >= :fromDate AND"
                        + " rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid"
                        + " = :transactionId  AND rh.referencenumber IN (:billIds)  AND rh.payermobile = :payermobile AND rh.id"
                        + " in (select id from egcl_receiptheader_v1 where tenantid = :tenantId order by createddate offset"
                        + " :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery12() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        ArrayList<String> stringList = new ArrayList<>();
        stringList
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(stringList);
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType,"
                        + " rh.receiptNumber as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as"
                        + " rh_referenceDesc, rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function"
                        + " as rh_function, rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate,"
                        + " rh.businessDetails as rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as"
                        + " rh_stateId,rh.location as rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status"
                        + ",rh.reasonForCancellation as rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount"
                        + " as rh_totalAmount, rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd"
                        + ",rh.consumerCode as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel"
                        + " as rh_channel,rh.reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund"
                        + " as rh_fund,rh.fundSource as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department"
                        + ",rh.depositedBranch as rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg"
                        + ",rh.voucherheader as rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh"
                        + ".additionalDetails as rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as"
                        + " rh_createdDate,rh.lastModifiedBy as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate,"
                        + " rh.demandid as rh_demandid, rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate,"
                        + " rh.transactionid as rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as"
                        + " rd_adjustedamount, rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand"
                        + "  as rd_isActualDemand,  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails"
                        + " as rd_additionalDetails, rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid"
                        + " as rd_demanddetailid, ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate"
                        + " as ins_transactiondate, ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as"
                        + " ins_instrumenttype, ins .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid ,"
                        + " ins.branchname as ins_branchname , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode"
                        + " , ins.financialstatus as ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee"
                        + " as ins_payee , ins.drawer as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno"
                        + " as ins_serialno , ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,"
                        + "  ins.createddate as ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate"
                        + " as ins_lastmodifieddate , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate,"
                        + " ins.instrumentNumber as ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN"
                        + " egcl_receiptdetails_v1 rd ON rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins"
                        + " ON rh.id=recins.receiptheader LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id"
                        + "  WHERE  rh.fund = :fund AND rh.department = :department AND rh.receiptDate >= :fromDate AND"
                        + " rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND rh.businessDetails IN"
                        + " (:businessCodes)   AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND rh.payermobile ="
                        + " :payermobile AND rh.id in (select id from egcl_receiptheader_v1 where tenantid = :tenantId order by"
                        + " createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery13() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        ArrayList<String> stringList = new ArrayList<>();
        stringList
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(stringList);
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  rh.fund = :fund AND"
                        + " rh.department = :department AND rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND"
                        + " rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND"
                        + " rh.manualreceiptnumber IN (:manualReceiptNumbers)  AND rh.payermobile = :payermobile AND rh.id in"
                        + " (select id from egcl_receiptheader_v1 where tenantid = :tenantId order by createddate offset :offset"
                        + " limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery14() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        ArrayList<String> stringList = new ArrayList<>();
        stringList
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(stringList);
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  rh.fund = :fund AND"
                        + " rh.department = :department AND rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND"
                        + " rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND"
                        + " rh.payerid IN (:payerid)   AND rh.payermobile = :payermobile AND rh.id in (select id from egcl"
                        + "_receiptheader_v1 where tenantid = :tenantId order by createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery15() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(stringSet);
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType,"
                        + " rh.receiptNumber as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as"
                        + " rh_referenceDesc, rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function"
                        + " as rh_function, rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate,"
                        + " rh.businessDetails as rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as"
                        + " rh_stateId,rh.location as rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status"
                        + ",rh.reasonForCancellation as rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount"
                        + " as rh_totalAmount, rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd"
                        + ",rh.consumerCode as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel"
                        + " as rh_channel,rh.reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund"
                        + " as rh_fund,rh.fundSource as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department"
                        + ",rh.depositedBranch as rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg"
                        + ",rh.voucherheader as rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh"
                        + ".additionalDetails as rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as"
                        + " rh_createdDate,rh.lastModifiedBy as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate,"
                        + " rh.demandid as rh_demandid, rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate,"
                        + " rh.transactionid as rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as"
                        + " rd_adjustedamount, rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand"
                        + "  as rd_isActualDemand,  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails"
                        + " as rd_additionalDetails, rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid"
                        + " as rd_demanddetailid, ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate"
                        + " as ins_transactiondate, ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as"
                        + " ins_instrumenttype, ins .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid ,"
                        + " ins.branchname as ins_branchname , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode"
                        + " , ins.financialstatus as ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee"
                        + " as ins_payee , ins.drawer as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno"
                        + " as ins_serialno , ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,"
                        + "  ins.createddate as ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate"
                        + " as ins_lastmodifieddate , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate,"
                        + " ins.instrumentNumber as ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN"
                        + " egcl_receiptdetails_v1 rd ON rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins"
                        + " ON rh.id=recins.receiptheader LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id"
                        + "  WHERE  rh.consumerCode in (:consumerCodes) AND rh.fund = :fund AND rh.department = :department AND"
                        + " rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND"
                        + " rh.id IN (:ids) AND rh.transactionid = :transactionId  AND rh.payermobile = :payermobile AND rh.id"
                        + " in (select id from egcl_receiptheader_v1 where tenantid = :tenantId order by createddate offset"
                        + " :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery16() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(stringSet);
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  UPPER(ins.instrumenttype)"
                        + " in (:instrumenttype) AND rh.fund = :fund AND rh.department = :department AND rh.receiptDate >= :fromDate"
                        + " AND rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND rh.id IN (:ids) AND"
                        + " rh.transactionid = :transactionId  AND rh.payermobile = :payermobile AND rh.id in (select id from"
                        + " egcl_receiptheader_v1 where tenantid = :tenantId order by createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery17() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(stringSet);
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(new HashSet<>());
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType,"
                        + " rh.receiptNumber as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as"
                        + " rh_referenceDesc, rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function"
                        + " as rh_function, rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate,"
                        + " rh.businessDetails as rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as"
                        + " rh_stateId,rh.location as rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status"
                        + ",rh.reasonForCancellation as rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount"
                        + " as rh_totalAmount, rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd"
                        + ",rh.consumerCode as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel"
                        + " as rh_channel,rh.reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund"
                        + " as rh_fund,rh.fundSource as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department"
                        + ",rh.depositedBranch as rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg"
                        + ",rh.voucherheader as rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh"
                        + ".additionalDetails as rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as"
                        + " rh_createdDate,rh.lastModifiedBy as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate,"
                        + " rh.demandid as rh_demandid, rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate,"
                        + " rh.transactionid as rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as"
                        + " rd_adjustedamount, rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand"
                        + "  as rd_isActualDemand,  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails"
                        + " as rd_additionalDetails, rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid"
                        + " as rd_demanddetailid, ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate"
                        + " as ins_transactiondate, ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as"
                        + " ins_instrumenttype, ins .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid ,"
                        + " ins.branchname as ins_branchname , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode"
                        + " , ins.financialstatus as ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee"
                        + " as ins_payee , ins.drawer as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno"
                        + " as ins_serialno , ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,"
                        + "  ins.createddate as ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate"
                        + " as ins_lastmodifieddate , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate,"
                        + " ins.instrumentNumber as ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN"
                        + " egcl_receiptdetails_v1 rd ON rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins"
                        + " ON rh.id=recins.receiptheader LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id"
                        + "  WHERE  rh.receiptNumber IN (:receiptNumbers)   AND rh.fund = :fund AND rh.department = :department"
                        + " AND rh.receiptDate >= :fromDate AND rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode"
                        + " AND rh.id IN (:ids) AND rh.transactionid = :transactionId  AND rh.payermobile = :payermobile AND"
                        + " rh.id in (select id from egcl_receiptheader_v1 where tenantid = :tenantId order by createddate offset"
                        + " :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }

    @Test
    void testGetReceiptSearchQuery18() {

        CollectionQueryBuilder_v1 collectionQueryBuilder_v1 = new CollectionQueryBuilder_v1();

        HashSet<String> stringSet = new HashSet<>();
        stringSet
                .add("Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id ");
        ReceiptSearchCriteria_v1 receiptSearchCriteria_v1 = mock(ReceiptSearchCriteria_v1.class);
        when(receiptSearchCriteria_v1.getOffset()).thenReturn(2);
        when(receiptSearchCriteria_v1.getLimit()).thenReturn(1);
        when(receiptSearchCriteria_v1.getFromDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getToDate()).thenReturn(1L);
        when(receiptSearchCriteria_v1.getBusinessCode()).thenReturn("Business Code");
        when(receiptSearchCriteria_v1.getCollectedBy()).thenReturn(null);
        when(receiptSearchCriteria_v1.getDepartment()).thenReturn("Department");
        when(receiptSearchCriteria_v1.getFund()).thenReturn("Fund");
        when(receiptSearchCriteria_v1.getMobileNo()).thenReturn("Mobile No");
        when(receiptSearchCriteria_v1.getTenantId()).thenReturn("");
        when(receiptSearchCriteria_v1.getTransactionId()).thenReturn("42");
        when(receiptSearchCriteria_v1.getBillIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getBusinessCodes()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getManualReceiptNumbers()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getPayerIds()).thenReturn(new ArrayList<>());
        when(receiptSearchCriteria_v1.getConsumerCode()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getInstrumentType()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getReceiptNumbers()).thenReturn(new HashSet<>());
        when(receiptSearchCriteria_v1.getStatus()).thenReturn(stringSet);
        doNothing().when(receiptSearchCriteria_v1).setToDate((Long) any());
        HashMap<String, Object> stringObjectMap = new HashMap<>();
        assertEquals(
                "Select rh.id as rh_id,rh.payername as rh_payername,rh.payerAddress as rh_payerAddress, rh.payerEmail"
                        + " as rh_payerEmail, rh.payermobile as rh_payermobile, rh.paidBy as rh_paidBy, rh.referenceNumber as"
                        + " rh_referenceNumber, rh.referenceDate as rh_referenceDate,rh.receiptType as rh_receiptType, rh.receiptNumber"
                        + " as rh_receiptNumber, rh.receiptDate as rh_receiptDate, rh.referenceDesc as rh_referenceDesc,"
                        + " rh.manualReceiptNumber as rh_manualReceiptNumber, rh.fund as rh_fund, rh.function as rh_function,"
                        + " rh.department as rh_department,  rh.manualreceiptdate as rh_manualreceiptdate, rh.businessDetails as"
                        + " rh_businessDetails,  rh.collectionType as rh_collectionType,rh.stateId as rh_stateId,rh.location as"
                        + " rh_location,  rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation as"
                        + " rh_reasonForCancellation , rh.minimumAmount as rh_minimumAmount,rh.totalAmount as rh_totalAmount,"
                        + " rh.collectedamount as rh_collectedamount, rh.collModesNotAllwd as rh_collModesNotAllwd,rh.consumerCode"
                        + " as rh_consumerCode,rh.function as rh_function,  rh.version as rh_version,rh.channel as rh_channel,rh"
                        + ".reference_ch_id as rh_reference_ch_id,  rh.consumerType as rh_consumerType,rh.fund as rh_fund,rh.fundSource"
                        + " as rh_fundSource, rh.boundary as rh_boundary, rh.department as rh_department,rh.depositedBranch as"
                        + " rh_depositedBranch, rh.tenantId as rh_tenantId, rh.displayMsg as rh_displayMsg,rh.voucherheader as"
                        + " rh_voucherheader, rh.cancellationRemarks as rh_cancellationRemarks, rh.additionalDetails as"
                        + " rh_additionalDetails, rh.createdBy as  rh_createdBy, rh.createdDate as rh_createdDate,rh.lastModifiedBy"
                        + " as rh_lastModifiedBy, rh.lastModifiedDate as rh_lastModifiedDate, rh.demandid as rh_demandid,"
                        + " rh.demandFromDate as rh_demandfromdate, rh.demandToDate as rh_demandtodate, rh.transactionid as"
                        + " rh_transactionid, rd.id as rd_id,  rd.amount as rd_amount, rd.adjustedamount as rd_adjustedamount,"
                        + " rd.ordernumber as rd_ordernumber,  rd.description as rd_description,rd.isActualDemand  as rd_isActualDemand,"
                        + "  rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, rd.additionalDetails as rd_additionalDetails"
                        + ", rd.tenantId as rd_tenantId, rd.taxheadcode as rd_taxheadcode, rd.demanddetailid as rd_demanddetailid,"
                        + " ins.id as ins_instrumentheader, ins.amount as ins_amount, ins.transactionDate as ins_transactiondate,"
                        + " ins.transactionNumber as ins_transactionNumber, ins.instrumenttype as ins_instrumenttype, ins"
                        + " .instrumentstatus as ins_instrumentstatus,  ins.bankid as ins_bankid , ins.branchname as ins_branchname"
                        + " , ins.bankaccountid as ins_bankaccountid,  ins.ifsccode as ins_ifsccode , ins.financialstatus as"
                        + " ins_financialstatus ,  ins.transactiontype as ins_transactiontype , ins.payee as ins_payee , ins.drawer"
                        + " as ins_drawer ,  ins.surrenderreason as ins_surrenderreason , ins.serialno as ins_serialno ,"
                        + " ins.additionalDetails as ins_additionalDetails, ins.createdby as ins_createdby ,  ins.createddate as"
                        + " ins_createddate , ins.lastmodifiedby as ins_lastmodifiedby ,  ins.lastmodifieddate as ins_lastmodifieddate"
                        + " , ins.tenantid as ins_tenantid ,  ins.instrumentDate as ins_instrumentDate, ins.instrumentNumber as"
                        + " ins_instrumentNumber from egcl_receiptheader_v1 rh LEFT OUTER JOIN egcl_receiptdetails_v1 rd ON"
                        + " rh.id=rd.receiptheader LEFT OUTER JOIN egcl_receiptinstrument_v1 recins ON rh.id=recins.receiptheader"
                        + " LEFT JOIN egcl_instrumentheader_v1 ins ON recins.instrumentheader=ins.id  WHERE  UPPER(rh.status) in"
                        + " (:status) AND rh.fund = :fund AND rh.department = :department AND rh.receiptDate >= :fromDate AND"
                        + " rh.receiptDate <= :toDate AND rh.businessDetails = :businessCode AND rh.id IN (:ids) AND rh.transactionid"
                        + " = :transactionId  AND rh.payermobile = :payermobile AND rh.id in (select id from egcl_receiptheader_v1"
                        + " where tenantid = :tenantId order by createddate offset :offset limit :limit)",
                collectionQueryBuilder_v1.getReceiptSearchQuery(receiptSearchCriteria_v1, stringObjectMap));
        verify(receiptSearchCriteria_v1, atLeast(1)).getLimit();
        verify(receiptSearchCriteria_v1).getOffset();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFromDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getToDate();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCode();
        verify(receiptSearchCriteria_v1).getCollectedBy();
        verify(receiptSearchCriteria_v1, atLeast(1)).getDepartment();
        verify(receiptSearchCriteria_v1, atLeast(1)).getFund();
        verify(receiptSearchCriteria_v1, atLeast(1)).getMobileNo();
        verify(receiptSearchCriteria_v1).getTenantId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getTransactionId();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBillIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getBusinessCodes();
        verify(receiptSearchCriteria_v1, atLeast(1)).getIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getManualReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getPayerIds();
        verify(receiptSearchCriteria_v1, atLeast(1)).getConsumerCode();
        verify(receiptSearchCriteria_v1, atLeast(1)).getInstrumentType();
        verify(receiptSearchCriteria_v1, atLeast(1)).getReceiptNumbers();
        verify(receiptSearchCriteria_v1, atLeast(1)).getStatus();
        verify(receiptSearchCriteria_v1).setToDate((Long) any());
        assertEquals(11, stringObjectMap.size());
    }
}

