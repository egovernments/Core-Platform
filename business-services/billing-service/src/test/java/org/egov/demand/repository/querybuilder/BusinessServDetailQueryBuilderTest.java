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

import org.egov.demand.model.BusinessServiceDetail;
import org.egov.demand.web.contract.BusinessServiceDetailCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BusinessServDetailQueryBuilder.class})
@ExtendWith(SpringExtension.class)
class BusinessServDetailQueryBuilderTest {
    @Autowired
    private BusinessServDetailQueryBuilder businessServDetailQueryBuilder;

    @Test
    void testPrepareSearchQuery() {
        BusinessServiceDetailCriteria businessServiceDetailCriteria = new BusinessServiceDetailCriteria();
        Assertions.assertEquals("SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE ",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, new ArrayList<>()));
    }

    @Test
    void testPrepareSearchQuery3() {
        HashSet<String> businessService = new HashSet<>();
        BusinessServiceDetailCriteria businessServiceDetailCriteria = new BusinessServiceDetailCriteria("42",
                businessService, new HashSet<>());

        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ? ",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareSearchQuery4() {
        BusinessServiceDetailCriteria businessServiceDetailCriteria = mock(BusinessServiceDetailCriteria.class);
        when(businessServiceDetailCriteria.getTenantId()).thenReturn("42");
        when(businessServiceDetailCriteria.getBusinessService()).thenReturn(new HashSet<>());
        when(businessServiceDetailCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ? ",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        verify(businessServiceDetailCriteria, atLeast(1)).getTenantId();
        verify(businessServiceDetailCriteria).getBusinessService();
        verify(businessServiceDetailCriteria, atLeast(1)).getId();
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareSearchQuery5() {
        BusinessServiceDetailCriteria businessServiceDetailCriteria = mock(BusinessServiceDetailCriteria.class);
        when(businessServiceDetailCriteria.getTenantId()).thenReturn(" WHERE ");
        when(businessServiceDetailCriteria.getBusinessService()).thenReturn(new HashSet<>());
        when(businessServiceDetailCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ? ",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        verify(businessServiceDetailCriteria, atLeast(1)).getTenantId();
        verify(businessServiceDetailCriteria).getBusinessService();
        verify(businessServiceDetailCriteria, atLeast(1)).getId();
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareSearchQuery6() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice ");
        BusinessServiceDetailCriteria businessServiceDetailCriteria = mock(BusinessServiceDetailCriteria.class);
        when(businessServiceDetailCriteria.getTenantId()).thenReturn("42");
        when(businessServiceDetailCriteria.getBusinessService()).thenReturn(stringSet);
        when(businessServiceDetailCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ?  and businessservice.businessservice IN  ('SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS"
                        + " businessservice ')",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        verify(businessServiceDetailCriteria, atLeast(1)).getTenantId();
        verify(businessServiceDetailCriteria, atLeast(1)).getBusinessService();
        verify(businessServiceDetailCriteria, atLeast(1)).getId();
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareSearchQuery7() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice ");
        BusinessServiceDetailCriteria businessServiceDetailCriteria = mock(BusinessServiceDetailCriteria.class);
        when(businessServiceDetailCriteria.getTenantId()).thenReturn("42");
        when(businessServiceDetailCriteria.getBusinessService()).thenReturn(new HashSet<>());
        when(businessServiceDetailCriteria.getId()).thenReturn(stringSet);
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ?  and businessservice.id IN  ('SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice"
                        + " ')",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        verify(businessServiceDetailCriteria, atLeast(1)).getTenantId();
        verify(businessServiceDetailCriteria).getBusinessService();
        verify(businessServiceDetailCriteria, atLeast(1)).getId();
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareSearchQuery8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("foo");
        stringSet.add("SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice ");
        stringSet.add("SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice ");
        BusinessServiceDetailCriteria businessServiceDetailCriteria = mock(BusinessServiceDetailCriteria.class);
        when(businessServiceDetailCriteria.getTenantId()).thenReturn("42");
        when(businessServiceDetailCriteria.getBusinessService()).thenReturn(stringSet);
        when(businessServiceDetailCriteria.getId()).thenReturn(new HashSet<>());
        ArrayList<Object> objectList = new ArrayList<>();
        assertEquals(
                "SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice  WHERE  businessservice.tenantId"
                        + " = ?  and businessservice.businessservice IN  ('foo','SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS"
                        + " businessservice ')",
                businessServDetailQueryBuilder.prepareSearchQuery(businessServiceDetailCriteria, objectList));
        verify(businessServiceDetailCriteria, atLeast(1)).getTenantId();
        verify(businessServiceDetailCriteria, atLeast(1)).getBusinessService();
        verify(businessServiceDetailCriteria, atLeast(1)).getId();
        assertEquals(1, objectList.size());
    }

    @Test
    void testPrepareQueryForValidation() {
        assertEquals("select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  )",
                businessServDetailQueryBuilder.prepareQueryForValidation(new ArrayList<>(), "Mode"));
    }

    @Test
    void testPrepareQueryForValidation2() {
        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(new BusinessServiceDetail());
        assertEquals("select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  (  )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation3() {
        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(new BusinessServiceDetail());
        businessServiceDetailList.add(new BusinessServiceDetail());
        assertEquals(
                "select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  (" + "  or  (  )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation4() {
        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        ArrayList<String> collectionModesNotAllowed = new ArrayList<>();
        businessServiceDetailList.add(new BusinessServiceDetail("42", "42",
                "select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where ",
                "select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where ",
                collectionModesNotAllowed, true, true, true, 1L, true, "https://example.org/example", new AuditDetail()));
        assertEquals("select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  ("
                        + " businessservice.businessservice = 'select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS"
                        + " businessservice where ' and  businessservice.tenantId = '42') )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "Mode"));
    }

    @Test
    void testPrepareQueryForValidation6() {
        BusinessServiceDetail businessServiceDetail = mock(BusinessServiceDetail.class);
        when(businessServiceDetail.getBusinessService()).thenReturn("Business Service");
        when(businessServiceDetail.getTenantId()).thenReturn("42");

        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(businessServiceDetail);
        assertEquals(
                "select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  ("
                        + " businessservice.businessservice = 'Business Service' and  businessservice.tenantId = '42') )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "Mode"));
        verify(businessServiceDetail, atLeast(1)).getBusinessService();
        verify(businessServiceDetail, atLeast(1)).getTenantId();
    }

    @Test
    void testPrepareQueryForValidation7() {
        BusinessServiceDetail businessServiceDetail = mock(BusinessServiceDetail.class);
        when(businessServiceDetail.getBusinessService()).thenReturn(" ( ");
        when(businessServiceDetail.getTenantId()).thenReturn("42");

        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(businessServiceDetail);
        assertEquals(
                "select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  ("
                        + " businessservice.businessservice = ' ( ' and  businessservice.tenantId = '42') )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "Mode"));
        verify(businessServiceDetail, atLeast(1)).getBusinessService();
        verify(businessServiceDetail, atLeast(1)).getTenantId();
    }

    @Test
    void testPrepareQueryForValidation8() {
        BusinessServiceDetail businessServiceDetail = mock(BusinessServiceDetail.class);
        when(businessServiceDetail.getId()).thenReturn("42");
        when(businessServiceDetail.getBusinessService()).thenReturn("Business Service");
        when(businessServiceDetail.getTenantId()).thenReturn("42");

        ArrayList<BusinessServiceDetail> businessServiceDetailList = new ArrayList<>();
        businessServiceDetailList.add(businessServiceDetail);
        assertEquals("select exists (SELECT * FROM {schema}.EGBS_BUSINESS_SERVICE_DETAILS businessservice where  ("
                        + " businessservice.businessservice = 'Business Service' and  businessservice.id != '42' and  businessservice"
                        + ".tenantId = '42') )",
                businessServDetailQueryBuilder.prepareQueryForValidation(businessServiceDetailList, "edit"));
        verify(businessServiceDetail, atLeast(1)).getBusinessService();
        verify(businessServiceDetail).getId();
        verify(businessServiceDetail, atLeast(1)).getTenantId();
    }

    @Test
    void testConstructor() {
        BusinessServDetailQueryBuilder actualBusinessServDetailQueryBuilder = new BusinessServDetailQueryBuilder();
        assertEquals(
                "INSERT INTO {schema}.EGBS_BUSINESS_SERVICE_DETAILS(id,businessservice,collectionModesNotAllowed"
                        + ",partPaymentAllowed,callBackForApportioning,callBackApportionURL,createddate,lastmodifieddate,createdby"
                        + ",lastmodifiedby,tenantid) values (?,?,?,?,?,?,?,?,?,?,?);",
                actualBusinessServDetailQueryBuilder.getInsertQuery());
        assertEquals(
                "UPDATE {schema}.EGBS_BUSINESS_SERVICE_DETAILS SET businessservice = ?, collectionModesNotAllowed = ?,"
                        + " partPaymentAllowed = ?, callBackForApportioning = ?, callBackApportionURL = ?, lastmodifieddate = ?,"
                        + " lastmodifiedby = ? WHERE tenantid = ? and id = ?;",
                actualBusinessServDetailQueryBuilder.getUpdateQuery());
    }
}

