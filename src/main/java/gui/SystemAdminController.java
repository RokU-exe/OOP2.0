package gui;

import models.User;
import utils.DBUtil;

import java.util.List;

public class SystemAdminController {
    private User systemAdmin;

    public SystemAdminController(User systemAdmin) {
        this.systemAdmin = systemAdmin;
    }

    public List<User> getUsers() {
        // Retrieve all users from the database
        return DBUtil.getAllUsers();
    }

    public void addUser(User user) {
        // Add a new user to the database
        DBUtil.addUser(user);
    }

    public void updateUser(User updatedUser) {
        // Update a user's information in the database
        DBUtil.updateUser(updatedUser);
    }

    public void deleteUser(String userId) {
        // Delete a user by ID from the database
        DBUtil.deleteUser(userId);
    }

    public List<User> getFilteredUsers(String filterCriteria) {
        // Retrieve all users based on a filter
        return DBUtil.getFilteredCustomers(filterCriteria);
    }

    public double calculateTotalClaimedAmount() {
        // Sum up the successfully claimed amount with different parameters
        return DBUtil.calculateTotalClaimedAmount();
    }
}
