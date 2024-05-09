package controllers;

import models.User;
import models.UserRole;

public class SystemAdmin extends User {
    private static SystemAdmin instance; // singleton

    public SystemAdmin(String id, String fullName, String email, String password, UserRole role) {
        super(id, fullName, email, password, role);
    }

    public SystemAdmin() {
        super();
    }

    // Additional methods specific to System Admins
    public static SystemAdmin getInstance() {
        if (instance == null) {
            instance = new SystemAdmin();
        }
        return instance;
    }
}
