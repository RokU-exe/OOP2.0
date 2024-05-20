package models;

import java.util.Date;
import java.sql.Timestamp;

public class Claim {
    private String id;
    private Date claimDate;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private Double claimAmount;
    private ClaimStatus status;
    private String receiverBank;
    private String receiverName;
    private String receiverNumber;
    private String policyHolderName;
    private Timestamp createdAt;

    // Constructor
    public Claim(String id, Date claimDate, String insuredPerson, String cardNumber, Date examDate,
                 Double claimAmount, ClaimStatus status, String receiverBank, String receiverName,
                 String receiverNumber, String policyHolderName, Timestamp createdAt) {
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBank = receiverBank;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
        this.policyHolderName = policyHolderName;
        this.createdAt = createdAt;
    }

    public Claim(String id, java.sql.Date claimDate, String insuredPerson, String cardNumber, java.sql.Date examDate, double claimAmount, ClaimStatus status, String receiverBank, String receiverName, String receiverNumber) {
    }

    public Claim(String id, String policyHolderName, String status) {
    }

    public Claim(String id, java.sql.Date claimDate, String insuredPerson, String cardNumber, java.sql.Date examDate, double claimAmount, ClaimStatus status, String receiverBank, String receiverName, String receiverNumber, String policyHolderName) {
    }

    public Claim(String id, java.sql.Date claimDate, String insuredPerson, String cardNumber, java.sql.Date examDate, Double claimAmount, double claimAmount1, String status, String receiverBank, String receiverName, String receiverNumber, String policyHolderName) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", claimDate=" + claimDate +
                ", insuredPerson='" + insuredPerson + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", examDate=" + examDate +
                ", claimAmount=" + claimAmount +
                ", status=" + status +
                ", receiverBank='" + receiverBank + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverNumber='" + receiverNumber + '\'' +
                ", policyHolderName='" + policyHolderName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
