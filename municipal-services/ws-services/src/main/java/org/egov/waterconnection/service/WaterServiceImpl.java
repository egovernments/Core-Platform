package org.egov.waterconnection.service;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.waterconnection.config.WSConfiguration;
import org.egov.waterconnection.constants.WCConstants;
import org.egov.waterconnection.repository.WaterDao;
import org.egov.waterconnection.repository.WaterDaoImpl;
import org.egov.waterconnection.util.EncryptionDecryptionUtil;
import org.egov.waterconnection.util.WaterServicesUtil;
import org.egov.waterconnection.validator.ActionValidator;
import org.egov.waterconnection.validator.MDMSValidator;
import org.egov.waterconnection.validator.ValidateProperty;
import org.egov.waterconnection.validator.WaterConnectionValidator;
import org.egov.waterconnection.web.models.*;
import org.egov.waterconnection.web.models.workflow.BusinessService;
import org.egov.waterconnection.workflow.WorkflowIntegrator;
import org.egov.waterconnection.workflow.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import static org.egov.waterconnection.constants.WCConstants.*;

@Slf4j
@Component
public class WaterServiceImpl implements WaterService {

	@Autowired
	private WaterDao waterDao;

	@Autowired
	private WaterConnectionValidator waterConnectionValidator;

	@Autowired
	private ValidateProperty validateProperty;

	@Autowired
	private MDMSValidator mDMSValidator;

	@Autowired
	private EnrichmentService enrichmentService;

	@Autowired
	private WorkflowIntegrator wfIntegrator;

	@Autowired
	private WSConfiguration config;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private ActionValidator actionValidator;

	@Autowired
	private WaterServicesUtil waterServiceUtil;

	@Autowired
	private CalculationService calculationService;

	@Autowired
	private WaterDaoImpl waterDaoImpl;

	@Autowired
	private UserService userService;

	@Autowired
	private WaterServicesUtil wsUtil;

	@Autowired
	EncryptionDecryptionUtil encryptionDecryptionUtil;

	/**
	 *
	 * @param waterConnectionRequest
	 *            WaterConnectionRequest contains water connection to be created
	 * @return List of WaterConnection after create
	 */
	@Override
	public List<WaterConnection> createWaterConnection(WaterConnectionRequest waterConnectionRequest) {

		int reqType = WCConstants.CREATE_APPLICATION;

		if (waterConnectionRequest.isDisconnectRequest()) {
			reqType = WCConstants.DISCONNECT_CONNECTION;
			validateDisconnectionRequest(waterConnectionRequest);
		}

		else if (wsUtil.isModifyConnectionRequest(waterConnectionRequest)) {
			List<WaterConnection> previousConnectionsList = getAllWaterApplications(waterConnectionRequest);

			// Validate any process Instance exists with WF
			if (!CollectionUtils.isEmpty(previousConnectionsList)) {
				workflowService.validateInProgressWF(previousConnectionsList, waterConnectionRequest.getRequestInfo(),
						waterConnectionRequest.getWaterConnection().getTenantId());
			}
			reqType = WCConstants.MODIFY_CONNECTION;
		}
		waterConnectionValidator.validateWaterConnection(waterConnectionRequest, reqType);
		Property property = validateProperty.getOrValidateProperty(waterConnectionRequest);
		validateProperty.validatePropertyFields(property,waterConnectionRequest.getRequestInfo());
		mDMSValidator.validateMasterForCreateRequest(waterConnectionRequest);
		enrichmentService.enrichWaterConnection(waterConnectionRequest, reqType);
		userService.createUser(waterConnectionRequest);
		// call work-flow
		if (config.getIsExternalWorkFlowEnabled())
			wfIntegrator.callWorkFlow(waterConnectionRequest, property);

		/* encrypt here */
	/*	waterConnectionRequest.setWaterConnection(encryptionDecryptionUtil.encryptObject(waterConnectionRequest.getWaterConnection(), WNS_ENCRYPTION_MODEL, WaterConnection.class));
		//Encrypting connectionHolder details(if exists) before pushing the data to indexes
		if (!CollectionUtils.isEmpty(waterConnectionRequest.getWaterConnection().getConnectionHolders())) {
			int k = 0;
			for (OwnerInfo holderInfo : waterConnectionRequest.getWaterConnection().getConnectionHolders()) {
				waterConnectionRequest.getWaterConnection().getConnectionHolders().set(k, encryptionDecryptionUtil.encryptObject(holderInfo, WNS_OWNER_ENCRYPTION_MODEL, OwnerInfo.class));
				k++;
			}
		}*/
		waterDao.saveWaterConnection(waterConnectionRequest);

		/* decrypt here */
		/*waterConnectionRequest.setWaterConnection(encryptionDecryptionUtil.decryptObject(waterConnectionRequest.getWaterConnection(), WNS_ENCRYPTION_MODEL, WaterConnection.class, waterConnectionRequest.getRequestInfo()));
		if (!CollectionUtils.isEmpty(waterConnectionRequest.getWaterConnection().getConnectionHolders()))
			waterConnectionRequest.getWaterConnection().setConnectionHolders(encryptionDecryptionUtil.decryptObject(waterConnectionRequest.getWaterConnection().getConnectionHolders(), WNS_OWNER_ENCRYPTION_MODEL, OwnerInfo.class, waterConnectionRequest.getRequestInfo()));
*/
		return Arrays.asList(waterConnectionRequest.getWaterConnection());
	}

	private void validateDisconnectionRequest(WaterConnectionRequest waterConnectionRequest) {
		if (!waterConnectionRequest.getWaterConnection().getStatus().toString().equalsIgnoreCase(WCConstants.ACTIVE)) {
			throw new CustomException("INVALID_REQUEST",
					"Water connection must be active for disconnection request");
		}

		List<WaterConnection> previousConnectionsList = getAllWaterApplications(waterConnectionRequest);

		for(WaterConnection connection : previousConnectionsList) {
			if(!(connection.getApplicationStatus().equalsIgnoreCase(WCConstants.STATUS_APPROVED) || connection.getApplicationStatus().equalsIgnoreCase(WCConstants.MODIFIED_FINAL_STATE))) {
				throw new CustomException("INVALID_REQUEST",
						"No application should be in progress while applying for disconnection");
			}
		}

	}

	/**
	 *
	 * @param criteria
	 *            WaterConnectionSearchCriteria contains search criteria on water
	 *            connection
	 * @param requestInfo
	 * @return List of matching water connection
	 */
	public List<WaterConnection> search(SearchCriteria criteria, RequestInfo requestInfo) {
		List<WaterConnection> waterConnectionList;

		/* encrypt here */
		/*criteria = encryptionDecryptionUtil.encryptObject(criteria, WNS_ENCRYPTION_MODEL, SearchCriteria.class);
*/
		waterConnectionList = getWaterConnectionsList(criteria, requestInfo);
		if (!StringUtils.isEmpty(criteria.getSearchType()) &&
				criteria.getSearchType().equals(WCConstants.SEARCH_TYPE_CONNECTION)) {
			waterConnectionList = enrichmentService.filterConnections(waterConnectionList);
			/*if(criteria.getIsPropertyDetailsRequired()){
				waterConnectionList = enrichmentService.enrichPropertyDetails(waterConnectionList, criteria, requestInfo);
			}*/
		}
		if ((criteria.getIsPropertyDetailsRequired() != null) && criteria.getIsPropertyDetailsRequired()) {
			waterConnectionList = enrichmentService.enrichPropertyDetails(waterConnectionList, criteria, requestInfo);
		}
		waterConnectionValidator.validatePropertyForConnection(waterConnectionList);
		enrichmentService.enrichConnectionHolderDeatils(waterConnectionList, criteria, requestInfo);
		enrichmentService.enrichProcessInstance(waterConnectionList, criteria, requestInfo);
//		enrichmentService.enrichDocumentDetails(waterConnectionList, criteria, requestInfo);

		/* decrypt here */
		/*return encryptionDecryptionUtil.decryptObject(waterConnectionList, WNS_ENCRYPTION_MODEL, WaterConnection.class, requestInfo);*/
		return waterConnectionList;
	}

	/**
	 *
	 * @param criteria
	 *            WaterConnectionSearchCriteria contains search criteria on water
	 *            connection
	 * @param requestInfo
	 * @return List of matching water connection
	 */
	public List<WaterConnection> getWaterConnectionsList(SearchCriteria criteria, RequestInfo requestInfo) {
		return waterDao.getWaterConnectionList(criteria, requestInfo);
	}

	/**
	 *
	 * @param criteria
	 *            WaterConnectionSearchCriteria contains search criteria on water
	 *            connection
	 * @param requestInfo
	 * @return Count of List of matching water connection
	 */
	@Override
	public Integer countAllWaterApplications(SearchCriteria criteria, RequestInfo requestInfo) {
		criteria.setIsCountCall(Boolean.TRUE);
		return getWaterConnectionsCount(criteria, requestInfo);
	}

	/**
	 *
	 * @param criteria
	 *            WaterConnectionSearchCriteria contains search criteria on water
	 *            connection
	 * @param requestInfo
	 * @return count of matching water connection
	 */
	public Integer getWaterConnectionsCount(SearchCriteria criteria, RequestInfo requestInfo) {
		return waterDao.getWaterConnectionsCount(criteria, requestInfo);
	}

	/**
	 *
	 * @param waterConnectionRequest
	 *            WaterConnectionRequest contains water connection to be updated
	 * @return List of WaterConnection after update
	 */
	@Override
	public List<WaterConnection> updateWaterConnection(WaterConnectionRequest waterConnectionRequest) {
		if(waterConnectionRequest.isDisconnectRequest()) {
			return updateWaterConnectionForDisconnectFlow(waterConnectionRequest);
		}
		SearchCriteria criteria = new SearchCriteria();
		if(wsUtil.isModifyConnectionRequest(waterConnectionRequest)) {
			// Received request to update the connection for modifyConnection WF
			return updateWaterConnectionForModifyFlow(waterConnectionRequest);
		}
		waterConnectionValidator.validateWaterConnection(waterConnectionRequest, WCConstants.UPDATE_APPLICATION);
		mDMSValidator.validateMasterData(waterConnectionRequest,WCConstants.UPDATE_APPLICATION );
		Property property = validateProperty.getOrValidateProperty(waterConnectionRequest);
		validateProperty.validatePropertyFields(property,waterConnectionRequest.getRequestInfo());
		BusinessService businessService = workflowService.getBusinessService(waterConnectionRequest.getWaterConnection().getTenantId(), 
				waterConnectionRequest.getRequestInfo(), config.getBusinessServiceValue());
		WaterConnection searchResult = getConnectionForUpdateRequest(waterConnectionRequest.getWaterConnection().getId(), waterConnectionRequest.getRequestInfo());
		String previousApplicationStatus = workflowService.getApplicationStatus(waterConnectionRequest.getRequestInfo(),
				waterConnectionRequest.getWaterConnection().getApplicationNo(),
				waterConnectionRequest.getWaterConnection().getTenantId(),
				config.getBusinessServiceValue());
		enrichmentService.enrichUpdateWaterConnection(waterConnectionRequest);
		actionValidator.validateUpdateRequest(waterConnectionRequest, businessService, previousApplicationStatus);
		waterConnectionValidator.validateUpdate(waterConnectionRequest, searchResult, WCConstants.UPDATE_APPLICATION);
		userService.updateUser(waterConnectionRequest, searchResult);
		//Enriching the property details
		List<WaterConnection> waterConnectionList = new ArrayList<>();
		waterConnectionList.add(waterConnectionRequest.getWaterConnection());
		criteria.setTenantId(waterConnectionRequest.getWaterConnection().getTenantId());
		waterConnectionRequest.setWaterConnection(enrichmentService.enrichPropertyDetails(waterConnectionList, criteria, waterConnectionRequest.getRequestInfo()).get(0));

		//Call workflow
		wfIntegrator.callWorkFlow(waterConnectionRequest, property);
		//call calculator service to generate the demand for one time fee
		calculationService.calculateFeeAndGenerateDemand(waterConnectionRequest, property);
		//check for edit and send edit notification
		waterDaoImpl.pushForEditNotification(waterConnectionRequest);
		//Enrich file store Id After payment
		enrichmentService.enrichFileStoreIds(waterConnectionRequest);
//		userService.createUser(waterConnectionRequest);
		enrichmentService.postStatusEnrichment(waterConnectionRequest);
		boolean isStateUpdatable = waterServiceUtil.getStatusForUpdate(businessService, previousApplicationStatus);

		/* encrypt here */
		/*waterConnectionRequest.setWaterConnection(encryptionDecryptionUtil.encryptObject(waterConnectionRequest.getWaterConnection(), WNS_ENCRYPTION_MODEL, WaterConnection.class));
		if (!CollectionUtils.isEmpty(waterConnectionRequest.getWaterConnection().getConnectionHolders())) {
			int k = 0;
			for (OwnerInfo holderInfo : waterConnectionRequest.getWaterConnection().getConnectionHolders()) {
				waterConnectionRequest.getWaterConnection().getConnectionHolders().set(k, encryptionDecryptionUtil.encryptObject(holderInfo, WNS_OWNER_ENCRYPTION_MODEL, OwnerInfo.class));
				k++;
			}
		}*/

		waterDao.updateWaterConnection(waterConnectionRequest, isStateUpdatable);
		enrichmentService.postForMeterReading(waterConnectionRequest,  WCConstants.UPDATE_APPLICATION);
		if (!StringUtils.isEmpty(waterConnectionRequest.getWaterConnection().getTenantId()))
			criteria.setTenantId(waterConnectionRequest.getWaterConnection().getTenantId());
		enrichmentService.enrichProcessInstance(Arrays.asList(waterConnectionRequest.getWaterConnection()), criteria, waterConnectionRequest.getRequestInfo());

        /* decrypt here */
		/*waterConnectionRequest.setWaterConnection(encryptionDecryptionUtil.decryptObject(waterConnectionRequest.getWaterConnection(), WNS_ENCRYPTION_MODEL, WaterConnection.class, waterConnectionRequest.getRequestInfo()));
*/		/*if (!CollectionUtils.isEmpty(waterConnectionRequest.getWaterConnection().getConnectionHolders()))
			waterConnectionRequest.getWaterConnection().setConnectionHolders(encryptionDecryptionUtil.decryptObject(waterConnectionRequest.getWaterConnection().getConnectionHolders(), WNS_OWNER_ENCRYPTION_MODEL, OwnerInfo.class, waterConnectionRequest.getRequestInfo()));*/
        return Arrays.asList(waterConnectionRequest.getWaterConnection());
    }

	public List<WaterConnection> updateWaterConnectionForDisconnectFlow(WaterConnectionRequest waterConnectionRequest) {

		SearchCriteria criteria = new SearchCriteria();
		waterConnectionValidator.validateWaterConnection(waterConnectionRequest, WCConstants.DISCONNECT_CONNECTION);
		mDMSValidator.validateMasterData(waterConnectionRequest,WCConstants.DISCONNECT_CONNECTION );
		Property property = validateProperty.getOrValidateProperty(waterConnectionRequest);
		validateProperty.validatePropertyFields(property,waterConnectionRequest.getRequestInfo());
		BusinessService businessService = workflowService.getBusinessService(waterConnectionRequest.getWaterConnection().getTenantId(), 
				waterConnectionRequest.getRequestInfo(), config.getDisconnectBusinessServiceName());
		WaterConnection searchResult = getConnectionForUpdateRequest(waterConnectionRequest.getWaterConnection().getId(), waterConnectionRequest.getRequestInfo());
		String previousApplicationStatus = workflowService.getApplicationStatus(waterConnectionRequest.getRequestInfo(),
				waterConnectionRequest.getWaterConnection().getApplicationNo(),
				waterConnectionRequest.getWaterConnection().getTenantId(),
				config.getDisconnectBusinessServiceName());
		enrichmentService.enrichUpdateWaterConnection(waterConnectionRequest);
		actionValidator.validateUpdateRequest(waterConnectionRequest, businessService, previousApplicationStatus);
		waterConnectionValidator.validateUpdate(waterConnectionRequest, searchResult, WCConstants.DISCONNECT_CONNECTION);
		userService.updateUser(waterConnectionRequest, searchResult);
		//Call workflow
		wfIntegrator.callWorkFlow(waterConnectionRequest, property);
		//call calculator service to generate the demand for one time fee
		calculationService.calculateFeeAndGenerateDemand(waterConnectionRequest, property);
		//check for edit and send edit notification
		waterDaoImpl.pushForEditNotification(waterConnectionRequest);
		//Enrich file store Id After payment
		enrichmentService.enrichFileStoreIds(waterConnectionRequest);
		userService.createUser(waterConnectionRequest);
		enrichmentService.postStatusEnrichment(waterConnectionRequest);
		boolean isStateUpdatable = waterServiceUtil.getStatusForUpdate(businessService, previousApplicationStatus);
		waterDao.updateWaterConnection(waterConnectionRequest, isStateUpdatable);
		enrichmentService.postForMeterReading(waterConnectionRequest,  WCConstants.DISCONNECT_CONNECTION);
		if (!StringUtils.isEmpty(waterConnectionRequest.getWaterConnection().getTenantId()))
			criteria.setTenantId(waterConnectionRequest.getWaterConnection().getTenantId());
		enrichmentService.enrichProcessInstance(Arrays.asList(waterConnectionRequest.getWaterConnection()), criteria, waterConnectionRequest.getRequestInfo());
		return Arrays.asList(waterConnectionRequest.getWaterConnection());
	}

	/**
	 * Search Water connection to be update
	 *
	 * @param id
	 * @param requestInfo
	 * @return water connection
	 */
	public WaterConnection getConnectionForUpdateRequest(String id, RequestInfo requestInfo) {
		Set<String> ids = new HashSet<>(Arrays.asList(id));
		SearchCriteria criteria = new SearchCriteria();
		criteria.setIds(ids);
		List<WaterConnection> connections = getWaterConnectionsList(criteria, requestInfo);
		if (CollectionUtils.isEmpty(connections)) {
			StringBuilder builder = new StringBuilder();
			builder.append("WATER CONNECTION NOT FOUND FOR: ").append(id).append(" :ID");
			throw new CustomException("INVALID_WATERCONNECTION_SEARCH", builder.toString());
		}

		return connections.get(0);
	}

	private List<WaterConnection> getAllWaterApplications(WaterConnectionRequest waterConnectionRequest) {
		SearchCriteria criteria = SearchCriteria.builder()
				.connectionNumber(Stream.of(waterConnectionRequest.getWaterConnection().getConnectionNo().toString()).collect(Collectors.toSet())).build();
		return search(criteria, waterConnectionRequest.getRequestInfo());
	}

	private List<WaterConnection> updateWaterConnectionForModifyFlow(WaterConnectionRequest waterConnectionRequest) {
		waterConnectionValidator.validateWaterConnection(waterConnectionRequest, WCConstants.MODIFY_CONNECTION);
		mDMSValidator.validateMasterData(waterConnectionRequest, WCConstants.MODIFY_CONNECTION);
		BusinessService businessService = workflowService.getBusinessService(
				waterConnectionRequest.getWaterConnection().getTenantId(), waterConnectionRequest.getRequestInfo(),
				config.getModifyWSBusinessServiceName());
		WaterConnection searchResult = getConnectionForUpdateRequest(
				waterConnectionRequest.getWaterConnection().getId(), waterConnectionRequest.getRequestInfo());
		Property property = validateProperty.getOrValidateProperty(waterConnectionRequest);
		validateProperty.validatePropertyFields(property,waterConnectionRequest.getRequestInfo());
		String previousApplicationStatus = workflowService.getApplicationStatus(waterConnectionRequest.getRequestInfo(),
				waterConnectionRequest.getWaterConnection().getApplicationNo(),
				waterConnectionRequest.getWaterConnection().getTenantId(), config.getModifyWSBusinessServiceName());
		enrichmentService.enrichUpdateWaterConnection(waterConnectionRequest);
		actionValidator.validateUpdateRequest(waterConnectionRequest, businessService, previousApplicationStatus);
		userService.updateUser(waterConnectionRequest, searchResult);
		waterConnectionValidator.validateUpdate(waterConnectionRequest, searchResult, WCConstants.MODIFY_CONNECTION);
		wfIntegrator.callWorkFlow(waterConnectionRequest, property);
		boolean isStateUpdatable = waterServiceUtil.getStatusForUpdate(businessService, previousApplicationStatus);
		waterDao.updateWaterConnection(waterConnectionRequest, isStateUpdatable);
		// setting oldApplication Flag
		markOldApplication(waterConnectionRequest);
		//check for edit and send edit notification
		waterDaoImpl.pushForEditNotification(waterConnectionRequest);
		enrichmentService.postForMeterReading(waterConnectionRequest, WCConstants.MODIFY_CONNECTION);
		return Arrays.asList(waterConnectionRequest.getWaterConnection());
	}

	public void markOldApplication(WaterConnectionRequest waterConnectionRequest) {
		if (waterConnectionRequest.getWaterConnection().getProcessInstance().getAction().equalsIgnoreCase(APPROVE_CONNECTION)) {
			String currentModifiedApplicationNo = waterConnectionRequest.getWaterConnection().getApplicationNo();
			List<WaterConnection> previousConnectionsList = getAllWaterApplications(waterConnectionRequest);

			for(WaterConnection waterConnection:previousConnectionsList){
				if(!waterConnection.getOldApplication() && !(waterConnection.getApplicationNo().equalsIgnoreCase(currentModifiedApplicationNo))){
					waterConnection.setOldApplication(Boolean.TRUE);
					WaterConnectionRequest previousWaterConnectionRequest = WaterConnectionRequest.builder().requestInfo(waterConnectionRequest.getRequestInfo()).waterConnection(waterConnection).build();
					waterDao.updateWaterConnection(previousWaterConnectionRequest,Boolean.TRUE);
				}
			}
		}
	}
	
	public WaterConnectionResponse planeSearch(SearchCriteria criteria, RequestInfo requestInfo) {
		WaterConnectionResponse waterConnection = getWaterConnectionsListForPlaneSearch(criteria, requestInfo);
		waterConnectionValidator.validatePropertyForConnection(waterConnection.getWaterConnection());
		enrichmentService.enrichConnectionHolderDeatils(waterConnection.getWaterConnection(), criteria, requestInfo);
		return waterConnection;
	}

	public WaterConnectionResponse getWaterConnectionsListForPlaneSearch(SearchCriteria criteria,
																		 RequestInfo requestInfo) {
		return waterDao.getWaterConnectionListForPlaneSearch(criteria, requestInfo);
	}

}