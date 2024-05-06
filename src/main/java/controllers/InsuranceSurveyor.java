package controllers;

import models.User;
import models.UserRole;

public class InsuranceSurveyor extends User {
    public InsuranceSurveyor(String id, String fullName, String email, String password, UserRole role) {
        super(id, fullName, email, password, role);
    }
    // Additional methods specific to Insurance Surveyors
}

