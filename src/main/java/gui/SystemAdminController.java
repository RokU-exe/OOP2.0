package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.SystemAdmin;
import models.User;
import models.UserRole;
import utils.DBUtil;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static models.UserRole.*;

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
    @FXML
    private Button add;
    @FXML
    private TextField email;
    @FXML
    private TextField fullname;
    @FXML
    private TextField password;
    @FXML
    private MenuButton role;
    @FXML
    private Button addIc;

    private UserRole getRole(String role) {
        if (role.equals("Policy Owner")) {
            return UserRole.POLICY_OWNER;
        } else if (role.equals("Policy Holder")) {
            return UserRole.POLICY_HOLDER;
        } else if (role.equals("Dependent")) {
            return UserRole.DEPENDENT;
        } else if (role.equals("Insurance Surveyor")) {
            return UserRole.INSURANCE_SURVEYOR;
        } else if (role.equals("Insurance Manager")) {
            return UserRole.INSURANCE_MANAGER;
        } else {
            // Handle the case when the role is not recognized
            // For example, you can throw an IllegalArgumentException
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
    public void addUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/AddUser.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = add; // Use any node from the current scene

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
    private void selectRole(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String roleName = menuItem.getText();

        // Set the selected role as the text of the MenuButton
        role.setText(roleName);
    }
    @FXML
    private Button doneAdding;

   //add the user, go back to dashboard
   public void doneAddUser() {
       String roleName = role.getText();
       System.out.println(roleName);
       // Check if a role is selected
       if (roleName.equals("Select Role")) {
           System.out.println("Please select a valid role.");
           return;
       }

       UserRole userRole = getRole(roleName);
       String id = DBUtil.getLargestIdByUserRole(userRole);
       if (id == null) {
           // Handle case when no matching records are found
           System.out.println("No matching records found for role: " + roleName);
           return;
       }
       User user = new User(id, fullname.getText(), email.getText(), password.getText(), userRole);

       // Add a new user to the database
       DBUtil.addUser(user);
       Alert alert = new Alert(Alert.AlertType.INFORMATION);

       alert.setTitle("Add " + roleName);
       alert.setHeaderText(roleName + " Added Successfully!");
       alert.setContentText(user.toString());
       alert.showAndWait();

       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
           Parent adminDashboardRoot = loader.load();

           Node sourceNode = doneAdding; // Use any node from the current scene

           // Get the primary stage from the source node's scene
           Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

           primaryStage.setScene(new Scene(adminDashboardRoot));
           primaryStage.show();
       } catch (IOException e) {
           e.printStackTrace();
           // Handle any errors loading the admin dashboard FXML
       }
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
    private MenuButton policyHolderMenuButton;

    @FXML
    private MenuButton policyOwnerMenuButton;

    @FXML
    private void initializeUIComponents() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("ADD NEW INSURANCE CARD");
        titleLabel.setLayoutX(128);
        titleLabel.setPrefHeight(118);
        titleLabel.setPrefWidth(497);
        titleLabel.setStyle("-fx-background-color: #CDE8E5;");
        titleLabel.setFont(new Font("Calibri", 24));
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView logoImageView = new ImageView(new Image("https://1000logos.net/wp-content/uploads/2019/07/RMIT-Logo.png"));
        logoImageView.setFitHeight(88);
        logoImageView.setFitWidth(286);
        logoImageView.setPreserveRatio(true);
        logoImageView.setPickOnBounds(true);

        ImageView blendImageView = new ImageView(new Image("https://tse1.mm.bing.net/th?id=OIP.QqEXi7j5Z0ZMFu8pLgTxzAHaHa&amp;pid=Api&amp;P=0&amp;h=180"));
        blendImageView.setFitHeight(150);
        blendImageView.setFitWidth(200);
        blendImageView.setLayoutX(450);
        blendImageView.setLayoutY(242);
        blendImageView.setPreserveRatio(true);
        blendImageView.setPickOnBounds(true);
        blendImageView.setBlendMode(javafx.scene.effect.BlendMode.MULTIPLY);

        Label cardHolderLabel = new Label("Card Holder:");
        cardHolderLabel.setLayoutX(90);
        cardHolderLabel.setLayoutY(144);
        cardHolderLabel.setFont(new Font("Calibri", 18));

        Label policyOwnerLabel = new Label("Policy Owner:");
        policyOwnerLabel.setLayoutX(92);
        policyOwnerLabel.setLayoutY(215);
        policyOwnerLabel.setFont(new Font("Calibri", 18));

        Label expireDateLabel = new Label("Expire date:");
        expireDateLabel.setLayoutX(92);
        expireDateLabel.setLayoutY(283);
        expireDateLabel.setFont(new Font("Calibri", 18));

        policyHolderMenuButton = new MenuButton("Select Policy Holder");
        policyHolderMenuButton.setLayoutX(226);
        policyHolderMenuButton.setLayoutY(142);
        policyHolderMenuButton.setPrefHeight(26);
        policyHolderMenuButton.setPrefWidth(150);

        policyOwnerMenuButton = new MenuButton("Select Policy Owner");
        policyOwnerMenuButton.setLayoutX(227);
        policyOwnerMenuButton.setLayoutY(213);
        policyOwnerMenuButton.setPrefHeight(26);
        policyOwnerMenuButton.setPrefWidth(150);

        DatePicker datePicker = new DatePicker();
        datePicker.setLayoutX(227);
        datePicker.setLayoutY(282);

        Button doneButton = new Button("ADD");
        doneButton.setLayoutX(502);
        doneButton.setLayoutY(168);
        doneButton.setPrefHeight(47);
        doneButton.setPrefWidth(106);
        doneButton.setFont(new Font("Calibri", 18));
        doneButton.setOnAction(event -> doneAddUser());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, cardHolderLabel, policyOwnerLabel, expireDateLabel, policyHolderMenuButton, policyOwnerMenuButton, datePicker, doneButton);

        populateMenuButtons();
        addIc.getScene().setRoot(root);
    }

    private void populateMenuButtons() throws SQLException {
        List<String> policyHolders = DBUtil.getPolicyHolders();
        List<String> policyOwners = DBUtil.getPolicyOwners();

        policyHolderMenuButton.getItems().clear();
        policyOwnerMenuButton.getItems().clear();

        for (String holder : policyHolders) {
            MenuItem menuItem = new MenuItem(holder);
            menuItem.setOnAction(event -> policyHolderMenuButton.setText(holder));
            policyHolderMenuButton.getItems().add(menuItem);
        }

        for (String owner : policyOwners) {
            MenuItem menuItem = new MenuItem(owner);
            menuItem.setOnAction(event -> policyOwnerMenuButton.setText(owner));
            policyOwnerMenuButton.getItems().add(menuItem);
        }
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
    public void insuranceCardMenu() {
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
    public void backtoDashboard() {
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
    }}