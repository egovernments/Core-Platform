package org.egov.boundary.persistence.repository;

import net.minidev.json.JSONArray;
import org.egov.boundary.domain.model.Boundary;
import org.egov.boundary.domain.model.BoundarySearchRequest;
import org.egov.boundary.web.contract.BoundaryType;
import org.egov.boundary.web.contract.HierarchyType;
import org.egov.common.contract.request.RequestInfo;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BoundaryRepository.class})
@ExtendWith(SpringExtension.class)
class BoundaryRepositoryTest {
    @Autowired
    private BoundaryRepository boundaryRepository;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private MdmsRepository mdmsRepository;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void testUpdate6() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        Boundary boundary = mock(Boundary.class);
        when(boundary.getId()).thenReturn(123L);

        Boundary boundary1 = new Boundary();
        HierarchyType hierarchyType = new HierarchyType();
        BoundaryType parent = new BoundaryType();
        boundary1.setBoundaryType(new BoundaryType("42", "Name", "Code", hierarchyType, parent, 1L, "Local Name",
                "Parent Name", new HashSet<>(), "42", 1L, 1L, 1L, 1L));
        boundary1.setParent(boundary);
        assertNull(boundaryRepository.update(boundary1));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundary, atLeast(1)).getId();
    }

    @Test
    void testUpdate7() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        Boundary boundary = mock(Boundary.class);
        when(boundary.getId()).thenReturn(123L);
        BoundaryType boundaryType = new BoundaryType();
        Boundary parent = new Boundary();
        HashSet<Boundary> children = new HashSet<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();

        Boundary boundary1 = new Boundary(123L, "boundarynum", 3L, "boundarynum", "boundarynum", "boundarynum",
                boundaryType, parent, children, fromDate, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()),
                true, 123L, "boundarynum", 10.0f, 10.0f, "boundarynum", "42", 3L, 3L, 3L, 3L);
        HierarchyType hierarchyType = new HierarchyType();
        BoundaryType parent1 = new BoundaryType();
        boundary1.setBoundaryType(new BoundaryType("42", "Name", "Code", hierarchyType, parent1, 1L, "Local Name",
                "Parent Name", new HashSet<>(), "42", 1L, 1L, 1L, 1L));
        boundary1.setParent(boundary);
        assertNull(boundaryRepository.update(boundary1));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundary, atLeast(1)).getId();
    }

    @Test
    void testFindByTenantIdAndId() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertNull(boundaryRepository.findByTenantIdAndId("42", 123L));
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindByTenantIdAndCodes() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualFindByTenantIdAndCodesResult = boundaryRepository.findByTenantIdAndCodes("42",
                new ArrayList<>());
        assertSame(objectList, actualFindByTenantIdAndCodesResult);
        assertTrue(actualFindByTenantIdAndCodesResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindByTenantIdAndCode() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertNull(boundaryRepository.findByTenantIdAndCode("42", "Code"));
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindAllByTenantId() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualFindAllByTenantIdResult = boundaryRepository.findAllByTenantId("42");
        assertSame(objectList, actualFindAllByTenantIdResult);
        assertTrue(actualFindAllByTenantIdResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllBoundariesByBoundaryTypeIdAndTenantId() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualAllBoundariesByBoundaryTypeIdAndTenantId = boundaryRepository
                .getAllBoundariesByBoundaryTypeIdAndTenantId(123L, "42");
        assertSame(objectList, actualAllBoundariesByBoundaryTypeIdAndTenantId);
        assertTrue(actualAllBoundariesByBoundaryTypeIdAndTenantId.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId = boundaryRepository
                .getBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId("Boundary Type Name", "Hierarchy Type Name",
                        "42");
        assertSame(objectList, actualBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId);
        assertTrue(actualBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetBoundaryByTypeAndNumber() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualBoundaryByTypeAndNumber = boundaryRepository.getBoundaryByTypeAndNumber(1L, 1L);
        assertSame(objectList, actualBoundaryByTypeAndNumber);
        assertTrue(actualBoundaryByTypeAndNumber.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllBoundaryByTenantIdAndTypeIds() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualAllBoundaryByTenantIdAndTypeIds = boundaryRepository.getAllBoundaryByTenantIdAndTypeIds("42",
                new ArrayList<>());
        assertSame(objectList, actualAllBoundaryByTenantIdAndTypeIds);
        assertTrue(actualAllBoundaryByTenantIdAndTypeIds.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindAllBoundariesByIdsAndTenant() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualFindAllBoundariesByIdsAndTenantResult = boundaryRepository
                .findAllBoundariesByIdsAndTenant("42", new ArrayList<>());
        assertSame(objectList, actualFindAllBoundariesByIdsAndTenantResult);
        assertTrue(actualFindAllBoundariesByIdsAndTenantResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        List<Boundary> actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant = boundaryRepository
                .getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest);
        assertSame(objectList, actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant);
        assertTrue(actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant2() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn(null);
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        List<Boundary> actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant = boundaryRepository
                .getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest);
        assertSame(objectList, actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant);
        assertTrue(actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        List<Boundary> actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant = boundaryRepository
                .getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest);
        assertSame(objectList, actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant);
        assertTrue(actualAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant4() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(resultLongList);
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        assertTrue(
                boundaryRepository.getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant5() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(resultLongList);
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        assertTrue(
                boundaryRepository.getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant6() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(resultLongList);
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        assertTrue(
                boundaryRepository.getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant7() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<String> stringList = new ArrayList<>();
        stringList
                .add("(WITH RECURSIVE nodes(bId,bBoundaryNum,bParent,bCode,bName,bBoundaryType,bLocalName,bFromdate,bToDate"
                        + ",bBndryid,bLongitude,bLatitude,bMaterialiedPath,bHistory,bCreatedDate,bLastModifiedDate,bCreatedBy"
                        + ",bLastModifiedBy, bTenantId,btId,btHierarchy,btParent,btName,btHierarchyType,btCreatedDate,btLastModifiedDate"
                        + ",btCreatedBy,btLastModifiedBy,btLocalName,btCode,btTenantId) AS (SELECT b.id,b.boundarynum,b.parent,b"
                        + ".code,b.name,b.boundarytype,b.localname,b.fromdate,b.todate,b.bndryid,b.longitude,b.latitude,b"
                        + ".materializedpath,b.ishistory,b.createddate,b.lastmodifieddate,b.createdby, b.lastmodifiedby,b.tenantid"
                        + ",bt.id,bt.hierarchy,bt.parent,bt.name,bt.hierarchytype,bt.createddate,bt.lastmodifieddate,bt.createdby"
                        + ",bt.lastmodifiedby,bt.localname,bt.code,bt.tenantid FROM eg_boundary b,eg_boundary_type bt WHERE"
                        + " b.boundarytype = bt.id and b.tenantid = :tenantId and bt.tenantid =:tenantId");
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(stringList);
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(new ArrayList<>());
        assertTrue(
                boundaryRepository.getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testGetAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant8() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        when(boundarySearchRequest.getBoundaryIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryNumbers()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getBoundaryTypeIds()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getCodes()).thenReturn(new ArrayList<>());
        when(boundarySearchRequest.getHierarchyTypeIds()).thenReturn(resultLongList);
        assertTrue(
                boundaryRepository.getAllBoundariesByIdsAndTypeAndNumberAndCodeAndTenant(boundarySearchRequest).isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
        verify(boundarySearchRequest, atLeast(1)).getTenantId();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryIds();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryNumbers();
        verify(boundarySearchRequest, atLeast(1)).getBoundaryTypeIds();
        verify(boundarySearchRequest, atLeast(1)).getCodes();
        verify(boundarySearchRequest, atLeast(1)).getHierarchyTypeIds();
    }

    @Test
    void testFindAllParents() throws DataAccessException {
        ArrayList<Boundary> boundaryList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (RowMapper<Boundary>) any())).thenReturn(boundaryList);
        List<Boundary> actualFindAllParentsResult = boundaryRepository.findAllParents();
        assertSame(boundaryList, actualFindAllParentsResult);
        assertTrue(actualFindAllParentsResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (RowMapper<Boundary>) any());
    }

    @Test
    void testFindActiveImmediateChildrenWithOutParent() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(objectList);
        List<Boundary> actualFindActiveImmediateChildrenWithOutParentResult = boundaryRepository
                .findActiveImmediateChildrenWithOutParent(123L);
        assertSame(objectList, actualFindActiveImmediateChildrenWithOutParentResult);
        assertTrue(actualFindActiveImmediateChildrenWithOutParentResult.isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testSetBoundariesWithParents() {
        ArrayList<Boundary> boundaryList = new ArrayList<>();
        List<Boundary> actualSetBoundariesWithParentsResult = boundaryRepository.setBoundariesWithParents(boundaryList);
        assertSame(boundaryList, actualSetBoundariesWithParentsResult);
        assertTrue(actualSetBoundariesWithParentsResult.isEmpty());
    }

    @Test
    void testSetBoundariesWithParents2() {
        ArrayList<Boundary> boundaryList = new ArrayList<>();
        boundaryList.add(new Boundary());
        List<Boundary> actualSetBoundariesWithParentsResult = boundaryRepository.setBoundariesWithParents(boundaryList);
        assertSame(boundaryList, actualSetBoundariesWithParentsResult);
        assertEquals(1, actualSetBoundariesWithParentsResult.size());
    }

    @Test
    void testSetBoundariesWithParents6() {
        Boundary boundary = mock(Boundary.class);
        doNothing().when(boundary).setParent((Boundary) any());
        when(boundary.getId()).thenReturn(123L);
        BoundaryType boundaryType = new BoundaryType();
        Boundary parent = new Boundary();
        HashSet<Boundary> children = new HashSet<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(boundary.getParent()).thenReturn(new Boundary(123L, "Name", 1L, "Code", "Area", "Codes", boundaryType,
                parent, children, fromDate, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()), true, 123L,
                "Local Name", 10.0f, 10.0f, "Materialized Path", "42", 1L, 1L, 1L, 1L));

        ArrayList<Boundary> boundaryList = new ArrayList<>();
        boundaryList.add(boundary);
        List<Boundary> actualSetBoundariesWithParentsResult = boundaryRepository.setBoundariesWithParents(boundaryList);
        assertSame(boundaryList, actualSetBoundariesWithParentsResult);
        assertEquals(1, actualSetBoundariesWithParentsResult.size());
        verify(boundary).getId();
        verify(boundary, atLeast(1)).getParent();
        verify(boundary).setParent((Boundary) any());
    }

    @Test
    void testSetBoundariesWithParents7() {
        Boundary boundary = mock(Boundary.class);
        doNothing().when(boundary).setParent((Boundary) any());
        when(boundary.getId()).thenReturn(123L);
        BoundaryType boundaryType = new BoundaryType();
        Boundary parent = new Boundary();
        HashSet<Boundary> children = new HashSet<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(boundary.getParent()).thenReturn(new Boundary(123L, "Name", 1L, "Code", "Area", "Codes", boundaryType,
                parent, children, fromDate, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()), true, 123L,
                "Local Name", 10.0f, 10.0f, "Materialized Path", "42", 1L, 1L, 1L, 1L));

        ArrayList<Boundary> boundaryList = new ArrayList<>();
        boundaryList.add(new Boundary());
        boundaryList.add(boundary);
        List<Boundary> actualSetBoundariesWithParentsResult = boundaryRepository.setBoundariesWithParents(boundaryList);
        assertSame(boundaryList, actualSetBoundariesWithParentsResult);
        assertEquals(2, actualSetBoundariesWithParentsResult.size());
        verify(boundary).getId();
        verify(boundary, atLeast(1)).getParent();
        verify(boundary).setParent((Boundary) any());
    }

    @Test
    void testGetBoundariesByTenantAndHierarchyType() {
        when(mdmsRepository.getByCriteria((String) any(), (String) any(), (RequestInfo) any()))
                .thenReturn(new JSONArray());
        BoundarySearchRequest boundarySearchRequest = mock(BoundarySearchRequest.class);
        when(boundarySearchRequest.getHierarchyTypeName()).thenReturn("Hierarchy Type Name");
        when(boundarySearchRequest.getTenantId()).thenReturn("42");
        assertTrue(
                boundaryRepository.getBoundariesByTenantAndHierarchyType(boundarySearchRequest, new RequestInfo()).isEmpty());
        verify(mdmsRepository).getByCriteria((String) any(), (String) any(), (RequestInfo) any());
        verify(boundarySearchRequest).getHierarchyTypeName();
        verify(boundarySearchRequest).getTenantId();
    }
}

