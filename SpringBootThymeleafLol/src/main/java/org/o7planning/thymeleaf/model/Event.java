package org.o7planning.thymeleaf.model;

import java.util.Date;

public class Event {
    private Long event_id;
    private Date requestAt;
    private Date responseAt;
    private Date modifyAt;
    private Date requestDay;
    private Short status;
    private Short resultCode;
    private String resultMessage;
    private Short groupEvent;
    private String service;
    private String hostRequest;
    private String hostProcess;
    private String className;
    private String otherData;
    private Long lastRecordId;

    public Event() {
    }

    public Event(Date requestAt, Date responseAt, Date modifyAt, Date requestDay,
            Short status, Short resultCode, String resultMessage, Short groupEvent, String service,
            String hostRequest, String hostProcess, String className, String otherData,
            Long lastRecordId) {
        this.requestAt = requestAt;
        this.responseAt = responseAt;
        this.modifyAt = modifyAt;
        this.requestDay = requestDay;
        this.status = status;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.groupEvent = groupEvent;
        this.service = service;
        this.hostRequest = hostRequest;
        this.hostProcess = hostProcess;
        this.className = className;
        this.otherData = otherData;
        this.lastRecordId = lastRecordId;
    }

    public Long getEvent_id() {
        return this.event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Date getRequestAt() {
        return this.requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    public Date getResponseAt() {
        return this.responseAt;
    }

    public void setResponseAt(Date responseAt) {
        this.responseAt = responseAt;
    }

    public Date getModifyAt() {
        return this.modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Date getRequestDay() {
        return this.requestDay;
    }

    public void setRequestDay(Date requestDay) {
        this.requestDay = requestDay;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(Short resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Short getGroupEvent() {
        return this.groupEvent;
    }

    public void setGroupEvent(Short groupEvent) {
        this.groupEvent = groupEvent;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHostRequest() {
        return this.hostRequest;
    }

    public void setHostRequest(String hostRequest) {
        this.hostRequest = hostRequest;
    }

    public String getHostProcess() {
        return this.hostProcess;
    }

    public void setHostProcess(String hostProcess) {
        this.hostProcess = hostProcess;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOtherData() {
        return this.otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public Long getLastRecordId() {
        return this.lastRecordId;
    }

    public void setLastRecordId(Long lastRecordId) {
        this.lastRecordId = lastRecordId;
    }

}
