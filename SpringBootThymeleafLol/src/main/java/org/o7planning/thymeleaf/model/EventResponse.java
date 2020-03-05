package org.o7planning.thymeleaf.model;

import java.util.Date;

public class EventResponse {
    private Long eventId;
    private Date responseAt;
    private Date modifyAt;
    private Short status;
    private Short resultCode;
    private String resultMessage;
    private Short groupEvent;
    private String service;
    private String hostResponse;
    private String className;
    private String otherData;

    public EventResponse() {
    }

    public EventResponse(Long eventId, Date responseAt, Date modifyAt, Short status,
            Short resultCode,
            String resultMessage, Short groupEvent, String service, String hostResponse,
            String className, String otherData) {
        this.eventId = eventId;
        this.responseAt = responseAt;
        this.modifyAt = modifyAt;
        this.status = status;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.groupEvent = groupEvent;
        this.service = service;
        this.hostResponse = hostResponse;
        this.className = className;
        this.otherData = otherData;
    }

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long event_id) {
        this.eventId = event_id;
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

    public String getHostResponse() {
        return this.hostResponse;
    }

    public void setHostResponse(String hostProcess) {
        this.hostResponse = hostProcess;
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

}
