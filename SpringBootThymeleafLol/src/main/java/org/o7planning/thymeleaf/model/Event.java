package org.o7planning.thymeleaf.model;

import java.util.Date;

public class Event {
	private Date requestAt;
	private Date responseAt;
	private Date modifyAt;
	private Date requestDay;
	private short status;
	private short resultCode;
	private String resultMessage;
	private short groupEvent;
	private String service;
	private String hostRequest;
	private String hostProcess;
	private String className;
	private String otherData;
	private long lastRecordId;

	public Event() {
	}

	
	public Date getRequestAt() {
		return requestAt;
	}


	public void setRequestAt(Date requestAt) {
		this.requestAt = requestAt;
	}


	public Date getResponseAt() {
		return responseAt;
	}

	public void setResponseAt(Date responseAt) {
		this.responseAt = responseAt;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public Date getRequestDay() {
		return requestDay;
	}

	public void setRequestDay(Date requestDay) {
		this.requestDay = requestDay;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getResultCode() {
		return resultCode;
	}

	public void setResultCode(short resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public short getGroupEvent() {
		return groupEvent;
	}

	public void setGroupEvent(short groupEvent) {
		this.groupEvent = groupEvent;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getHostRequest() {
		return hostRequest;
	}

	public void setHostRequest(String hostRequest) {
		this.hostRequest = hostRequest;
	}

	public String getHostProcess() {
		return hostProcess;
	}

	public void setHostProcess(String hostProcess) {
		this.hostProcess = hostProcess;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public long getLastRecordId() {
		return lastRecordId;
	}

	public void setLastRecordId(long lastRecordId) {
		this.lastRecordId = lastRecordId;
	}

}
