package com.jjdf.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName ="applicant-track-record")

public class ApplicantTrackRecord {

    @DynamoDBHashKey(attributeName = "customerId")
    @DynamoDBAttribute(attributeName = "customerId")
    private String customerId;

    @DynamoDBAttribute(attributeName = "loanId")
    private String  loanId;

    @DynamoDBRangeKey(attributeName = "contractId")
    @DynamoDBAttribute(attributeName = "contractId")
    private String contractId;

    @DynamoDBAttribute(attributeName = "primaryApplicantName")
    private String primaryApplicantName;
    @DynamoDBAttribute(attributeName = "trackRecord")
    private String trackRecord;
    @DynamoDBAttribute(attributeName = "eligibility")
    private String eligibility;
    @DynamoDBAttribute(attributeName = "areaOffice")
    private String areaOffice;
    @DynamoDBAttribute(attributeName = "status")
    private String status;
    @DynamoDBAttribute(attributeName = "tbe")
    private String tbe;
    @DynamoDBAttribute(attributeName = "abm")
    private String abm;
    @DynamoDBAttribute(attributeName = "tbeName")
    private String tbeName;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPrimaryApplicantName() {
        return primaryApplicantName;
    }

    public void setPrimaryApplicantName(String primaryApplicantName) {
        this.primaryApplicantName = primaryApplicantName;
    }

    public String getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getAreaOffice() {
        return areaOffice;
    }

    public void setAreaOffice(String areaOffice) {
        this.areaOffice = areaOffice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTbe() {
        return tbe;
    }

    public void setTbe(String tbe) {
        this.tbe = tbe;
    }

    public String getAbm() {
        return abm;
    }

    public void setAbm(String abm) {
        this.abm = abm;
    }

    public String getTbeName() {
        return tbeName;
    }

    public void setTbeName(String tbeName) {
        this.tbeName = tbeName;
    }
}
