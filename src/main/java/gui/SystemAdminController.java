package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import controllers.SystemAdmin;
import models.User;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SystemAdminController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private SystemAdmin systemAdmin;
    public SystemAdminController() {
        // Default constructor
    }


    public SystemAdminController(SystemAdmin systemAdmin) {
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
    @FXML
// Open the Admin Dashboard
    public void openAdminDashboard(Button loginButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = loginButton; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    @FXML
    private Button ph;
    @FXML
    public void policyHolderMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysPolicyHolderMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = ph; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    @FXML
    private Button de;
    public void dependentMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysDependentMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = de; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    @FXML
    private Button po;
    public void policyOwnerMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysPolicyOwnerMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = po; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    @FXML
    private Button is;
    public void insuranceSurveyorMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysInsuranceSurveyorMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = is; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    @FXML
    private Button im;
    public void insuranceManagerMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysInsuranceManagerMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = im; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    @FXML
    private Button ic;
    public void insuranceCardMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/sysInsuranceCardMenu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = ic; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    @FXML
    private Button back;
    public void backtoDashboard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = back; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    }

