package models;

public class Dependent extends Customer {
    private String policyHolderId;

    // Constructors, getters, and setters
    public Dependent(String id, String fullName, InsuranceCard insuranceCard, String address,
                     String phone, String email, String policyHolderId) {
        super(id, fullName, insuranceCard, null, address, phone, email);
        this.policyHolderId = policyHolderId;
    }

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    @Override
    public String toString() {
        return "models.Dependent{" +
                "policyHolderId='" + policyHolderId + '\'' +
                ", " + super.toString() +
                '}';
    }
}
