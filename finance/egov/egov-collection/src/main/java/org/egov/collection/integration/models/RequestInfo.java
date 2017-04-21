/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.collection.integration.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestInfo {

	private String apiId;
	private String ver;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "IST")
	private Date ts;
	private String action;
	private String did;
	private String key;
	private String msgId;
	private String requesterId;
	private String authToken;
	private User userInfo;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(final String apiId) {
		this.apiId = apiId;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(final String ver) {
		this.ver = ver;
	}


	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getAction() {
		return action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String getDid() {
		return did;
	}

	public void setDid(final String did) {
		this.did = did;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(final String msgId) {
		this.msgId = msgId;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(final String requesterId) {
		this.requesterId = requesterId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(final String authToken) {
		this.authToken = authToken;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
}
