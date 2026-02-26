package com.easyteeth.EasyTeeth.controller;

public class OdontogramRequest {

    private Long patientId;
    private Long toothId;
    private Long sideId;
    private Long pathologyId;

    private Boolean treated;
    private String note;

    public OdontogramRequest() {}

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getToothId() {
        return toothId;
    }

    public void setToothId(Long toothId) {
        this.toothId = toothId;
    }

    public Long getSideId() {
        return sideId;
    }

    public void setSideId(Long sideId) {
        this.sideId = sideId;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Boolean getTreated() {
        return treated;
    }

    public void setTreated(Boolean treated) {
        this.treated = treated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}