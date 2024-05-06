package models;

import java.util.List;

public class PolicyHolder extends Customer {
    private List<Dependent> dependents;

    // Constructors, getters, and setters
    public PolicyHolder(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims,
                        String address, String phone, String email, List<Dependent> dependents) {
        super(id, fullName, insuranceCard, claims, address, phone, email);
        this.dependents = dependents;
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    public double calculateTotalCost(double rate) {
        double cost = rate;
        for (Dependent dependent : dependents) {
            cost += rate * 0.6;
        }
        return cost;
    }

    @Override
    public String toString() {
        return "models.PolicyHolder{" +
                "dependents=" + dependents +
                ", " + super.toString() +
                '}';
    }
}
