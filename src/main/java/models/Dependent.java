package models;

import java.util.List;

public class Dependent extends Customer {
    private String policyHolderId;

    // Constructors, getters, and setters
    public Dependent(String id, String fullName, String email, String password, UserRole role, InsuranceCard insuranceCard, List<Claim> claims, String policyHolderId) {
        super(id, fullName, email, password, role, insuranceCard, claims);
        this.policyHolderId = policyHolderId;
    }

    public Dependent() {
    }

    public Dependent(String id, String fullName, String email, String password, UserRole userRole) {
    }


    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    @Override
    public String toString() {
        return "models.Dependent{" + super.toString() +
                "policyHolderId='" + policyHolderId + '\'' +
                '}';
    }
}
