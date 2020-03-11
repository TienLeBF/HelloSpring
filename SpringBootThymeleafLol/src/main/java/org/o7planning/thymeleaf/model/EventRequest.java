package org.o7planning.thymeleaf.model;

import java.util.Date;

public class EventRequest {
    private Date requestAt;
    private Date modifyAt;
    private String eventCode;
    private String requestMessage;
    private Short status;
    private Short resultCode;
    private String resultMessage;
    private Short groupEvent;
    private String service;
    private String hostRequest;
    private String className;
    private String methodName;
    private String otherData;
    private Long lastRecordId;

    public EventRequest() {
    }

    public EventRequest(Date requestAt, Date modifyAt, String eventCode, String requestMessage,
            Short status, Short resultCode,
            String resultMessage, Short groupEvent, String service, String hostRequest,
            String className, String methodName, String otherData) {
        this.requestAt = requestAt;
        this.modifyAt = modifyAt;
        this.eventCode = eventCode;
        this.requestMessage = requestMessage;
        this.status = status;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.groupEvent = groupEvent;
        this.service = service;
        this.hostRequest = hostRequest;
        this.className = className;
        this.methodName = methodName;
        this.otherData = otherData;
    }

    public Date getRequestAt() {
        return this.requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    public Date getModifyAt() {
        return this.modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
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

    public String getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getRequestMessage() {
        return this.requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
