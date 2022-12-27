package org.egov.boundary.domain.service;

import org.egov.boundary.persistence.repository.BoundaryTypeRepository;
import org.egov.boundary.web.contract.BoundaryType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoundaryTypeServiceTest {

	@Mock
	private BoundaryTypeRepository boundaryTypeRepository;

	@InjectMocks
	private BoundaryTypeService boundaryTypeService;

	@Test
	public void testshouldGetAllBoundaryByTenantIdAndTypeIds(){
		
		when(boundaryTypeRepository.findAllByTenantIdAndName(any(String.class),any(String.class)))
		.thenReturn(getExpectedBoundaryTypeDetails());
		
		List<Long> list = new ArrayList<Long>();
		list.add(1l);
		list.add(2l);
		
		List<BoundaryType> boundaryTypeList = boundaryTypeService.getAllBoundarytypesByNameAndTenant("default","Test");
		
		Assertions.assertTrue(boundaryTypeList.size() == 2);
		Assertions.assertFalse(boundaryTypeList.isEmpty());
		Assertions.assertTrue(boundaryTypeList != null);
		Assertions.assertTrue(boundaryTypeList.get(0).getName().equals("City 1"));
	}

	

	private List<BoundaryType> getExpectedBoundaryTypeDetails() {
		final List<BoundaryType> boundaryTypeList = new ArrayList<>();
		final BoundaryType boundaryType1 = BoundaryType.builder().id("1L").name("City 1").build();
		final BoundaryType boundaryType2 = BoundaryType.builder().id("2L").name("City 2").build();
		boundaryTypeList.add(boundaryType1);
		boundaryTypeList.add(boundaryType2);
		return boundaryTypeList;
	}
}