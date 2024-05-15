package controllers;

import models.User;
import models.UserRole;

public class InsuranceManager extends User {

    public InsuranceManager(String id, String fullName, String email, String password, UserRole role) {
        super(id, fullName, email, password, role);
    }

}

