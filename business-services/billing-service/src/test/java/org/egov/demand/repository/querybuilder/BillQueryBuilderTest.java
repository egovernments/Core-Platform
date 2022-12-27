package org.egov.demand.repository.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.node.MissingNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.egov.demand.model.BillV2;

import org.egov.demand.model.UpdateBillCriteria;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BillQueryBuilderTest {

    @Test
    void testGetBillStatusUpdateQuery2() {

        BillQueryBuilder billQueryBuilder = new BillQueryBuilder();

        UpdateBillCriteria updateBillCriteria = new UpdateBillCriteria();
        updateBillCriteria.setStatusToBeUpdated(BillV2.BillStatus.ACTIVE);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals("UPDATE {schema}.egbs_bill_v1 SET status=?  WHERE status='ACTIVE' AND tenantId = ? ",
                billQueryBuilder.getBillStatusUpdateQuery(updateBillCriteria, objectList));
        assertEquals(2, objectList.size());
    }

    @Test
    void testGetBillStatusUpdateQuery4() {

        BillQueryBuilder billQueryBuilder = new BillQueryBuilder();
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getBillIds()).thenReturn(new HashSet<>());
        when(updateBillCriteria.getStatusToBeUpdated()).thenReturn(BillV2.BillStatus.ACTIVE);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals("UPDATE {schema}.egbs_bill_v1 SET status=?  WHERE status='ACTIVE' AND tenantId = ? ",
                billQueryBuilder.getBillStatusUpdateQuery(updateBillCriteria, objectList));
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getBillIds();
        verify(updateBillCriteria, atLeast(1)).getStatusToBeUpdated();
        assertEquals(2, objectList.size());
    }

    @Test
    void testGetBillStatusUpdateQuery5() {

        BillQueryBuilder billQueryBuilder = new BillQueryBuilder();

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add(", additionaldetails = ?");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getBillIds()).thenReturn(stringSet);
        when(updateBillCriteria.getStatusToBeUpdated()).thenReturn(BillV2.BillStatus.ACTIVE);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals("UPDATE {schema}.egbs_bill_v1 SET status=?  WHERE status='ACTIVE' AND tenantId = ?  AND id IN (  ?)",
                billQueryBuilder.getBillStatusUpdateQuery(updateBillCriteria, objectList));
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria, atLeast(1)).getBillIds();
        verify(updateBillCriteria, atLeast(1)).getStatusToBeUpdated();
        assertEquals(3, objectList.size());
    }

    @Test
    void testGetBillStatusUpdateQuery7() {

        BillQueryBuilder billQueryBuilder = new BillQueryBuilder();
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getAdditionalDetails()).thenReturn(null);
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getBillIds()).thenReturn(new HashSet<>());
        when(updateBillCriteria.getStatusToBeUpdated()).thenReturn(BillV2.BillStatus.CANCELLED);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals("UPDATE {schema}.egbs_bill_v1 SET status=?  WHERE status='ACTIVE' AND tenantId = ? ",
                billQueryBuilder.getBillStatusUpdateQuery(updateBillCriteria, objectList));
        verify(updateBillCriteria).getAdditionalDetails();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria).getBillIds();
        verify(updateBillCriteria, atLeast(1)).getStatusToBeUpdated();
        assertEquals(2, objectList.size());
    }

    @Test
    void testGetBillStatusUpdateQuery8() {

        BillQueryBuilder billQueryBuilder = new BillQueryBuilder();

        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        stringSet.add(", additionaldetails = ?");
        UpdateBillCriteria updateBillCriteria = mock(UpdateBillCriteria.class);
        when(updateBillCriteria.getAdditionalDetails()).thenReturn(null);
        when(updateBillCriteria.getTenantId()).thenReturn("42");
        when(updateBillCriteria.getBillIds()).thenReturn(stringSet);
        when(updateBillCriteria.getStatusToBeUpdated()).thenReturn(BillV2.BillStatus.CANCELLED);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "UPDATE {schema}.egbs_bill_v1 SET status=?  WHERE status='ACTIVE' AND tenantId = ?  AND id IN" + " (  ?, ?)",
                billQueryBuilder.getBillStatusUpdateQuery(updateBillCriteria, objectList));
        verify(updateBillCriteria).getAdditionalDetails();
        verify(updateBillCriteria).getTenantId();
        verify(updateBillCriteria, atLeast(1)).getBillIds();
        verify(updateBillCriteria, atLeast(1)).getStatusToBeUpdated();
        assertEquals(4, objectList.size());
    }
}

