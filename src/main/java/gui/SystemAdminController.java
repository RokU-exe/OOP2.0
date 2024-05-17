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
import kotlin.jvm.internal.PackageReference;
import models.InsuranceCard;
import models.SystemAdmin;
import models.User;
import models.UserRole;
import utils.DBUtil;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private DatePicker datePicker;
    @FXML
    private Button doneAddingIC;

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

        datePicker = new DatePicker();
        datePicker.setLayoutX(227);
        datePicker.setLayoutY(282);

        doneAddingIC = new Button("ADD");
        doneAddingIC.setLayoutX(502);
        doneAddingIC.setLayoutY(168);
        doneAddingIC.setPrefHeight(47);
        doneAddingIC.setPrefWidth(106);
        doneAddingIC.setFont(new Font("Calibri", 18));
        doneAddingIC.setOnAction(event -> doneAddInsuranceCard());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, cardHolderLabel, policyOwnerLabel, expireDateLabel, policyHolderMenuButton, policyOwnerMenuButton, datePicker, doneAddingIC);

        populateMenuButtons();
        addIc.getScene().setRoot(root);
    }
    // Helper method to extract ID from the text (modify based on your actual format)
    private String extractIdFromText(String text) {
        if (text == null || text.isEmpty()) {
            return null; // Handle empty text
        }
        // Assuming the ID is at the end, separated by a space or hyphen
        int indexOfSeparator = text.lastIndexOf(" ") + 1; // Adjust for different separators
        if (indexOfSeparator > 0) {
            return text.substring(indexOfSeparator);
        } else {
            return text; // If no separator found, return the entire text (might need adjustment)
        }
    }
    private void doneAddInsuranceCard() {
        String cardHolder = extractIdFromText(policyHolderMenuButton.getText()); // Get selected card holder
        String policyOwner = extractIdFromText(policyOwnerMenuButton.getText()); // Get selected policy owner
        LocalDate expireDate = datePicker.getValue(); // Get selected expiration date

        // Convert LocalDate to java.util.Date (legacy approach)
        java.util.Date utilExpireDate = java.util.Date.from(expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Convert java.util.Date to java.sql.Date
        Date sqlExpireDate = new Date(utilExpireDate.getTime());

        String cardNumber = DBUtil.generateUniqueRandomCardNumber();
        InsuranceCard newIC = new InsuranceCard(cardNumber,cardHolder, policyOwner, sqlExpireDate);

        DBUtil.addInsuranceCard(newIC);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Add Insurance Card");
        alert.setHeaderText("Card: "+ cardNumber + " Added Successfully!");
        alert.setContentText(newIC.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = doneAddingIC; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
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
    }
    @FXML
    private Button read;
    // Read dependent
    @FXML
    private MenuButton readD;
    @FXML
    private Button readD1;

    @FXML
    private void readDependent() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("READ DEPENDENT");
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

        Label policyOwnerLabel = new Label("Dependent:");
        policyOwnerLabel.setLayoutX(77);
        policyOwnerLabel.setLayoutY(126);
        policyOwnerLabel.setFont(new Font("Calibri", 18));

        readD = new MenuButton("Select Dependent");
        readD.setLayoutX(248);
        readD.setLayoutY(124);
        readD.setPrefHeight(26);
        readD.setPrefWidth(367);

        readD1 = new Button("READ");
        readD1.setLayoutX(179);
        readD1.setLayoutY(303);
        readD1.setPrefHeight(47);
        readD1.setPrefWidth(106);
        readD1.setFont(new Font("Calibri", 18));
        readD1.setOnAction(event -> doneReadingDependent());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, policyOwnerLabel, readD, readD1);

        populatereadD();
        read.getScene().setRoot(root);
    }
    private void populatereadD() throws SQLException {
        List<String> dependent = DBUtil.getDependent();
        readD.getItems().clear();

        for (String is : dependent) {
            MenuItem menuItem = new MenuItem(is);
            menuItem.setOnAction(event -> readD.setText(is));
            readD.getItems().add(menuItem);
        }
    }
    public void doneReadingDependent(){
        User user = DBUtil.getUser(extractIdFromText(readD.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Read User:");
        alert.setHeaderText("User "+ user.getFullName());
        alert.setContentText(user.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = readD1; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    // Read insurance surveyor
    @FXML
    private MenuButton ReadIS1;
    @FXML
    private Button readIS;
    @FXML
    private void readInsuranceSurveyor() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("READ INSURANCE SURVEYOR");
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

        Label policyOwnerLabel = new Label("Insurance Surveyor:");
        policyOwnerLabel.setLayoutX(77);
        policyOwnerLabel.setLayoutY(126);
        policyOwnerLabel.setFont(new Font("Calibri", 18));

        ReadIS1 = new MenuButton("Select Insurance Surveyor");
        ReadIS1.setLayoutX(248);
        ReadIS1.setLayoutY(124);
        ReadIS1.setPrefHeight(26);
        ReadIS1.setPrefWidth(367);

        readIS = new Button("READ");
        readIS.setLayoutX(179);
        readIS.setLayoutY(303);
        readIS.setPrefHeight(47);
        readIS.setPrefWidth(106);
        readIS.setFont(new Font("Calibri", 18));
        readIS.setOnAction(event -> doneReadingInsuranceSurveyor());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, policyOwnerLabel, ReadIS1, readIS);

        populatereadIS();
        read.getScene().setRoot(root);
    }
    private void populatereadIS() throws SQLException {
        List<String> insuranceSurveyor = DBUtil.getInsuranceSurveyor();
        ReadIS1.getItems().clear();

        for (String is : insuranceSurveyor) {
            MenuItem menuItem = new MenuItem(is);
            menuItem.setOnAction(event -> ReadIS1.setText(is));
            ReadIS1.getItems().add(menuItem);
        }
    }
    public void doneReadingInsuranceSurveyor(){
        User user = DBUtil.getUser(extractIdFromText(ReadIS1.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Read User:");
        alert.setHeaderText("User "+ user.getFullName());
        alert.setContentText(user.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = readIS; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    //Read Insurance Manager
    @FXML
    private MenuButton readIM;
    @FXML
    private Button readIM1;

    @FXML
    private void readInsuranceManager() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("READ INSURANCE MANAGER");
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

        Label policyOwnerLabel = new Label("Insurance Manager:");
        policyOwnerLabel.setLayoutX(77);
        policyOwnerLabel.setLayoutY(126);
        policyOwnerLabel.setFont(new Font("Calibri", 18));

        readIM = new MenuButton("Select Insurance Manager");
        readIM.setLayoutX(248);
        readIM.setLayoutY(124);
        readIM.setPrefHeight(26);
        readIM.setPrefWidth(367);

        readIM1 = new Button("READ");
        readIM1.setLayoutX(179);
        readIM1.setLayoutY(303);
        readIM1.setPrefHeight(47);
        readIM1.setPrefWidth(106);
        readIM1.setFont(new Font("Calibri", 18));
        readIM1.setOnAction(event -> doneReadingInsuranceManager());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, policyOwnerLabel, readIM, readIM1);

        populatereadIM();
        read.getScene().setRoot(root);
    }
    private void populatereadIM() throws SQLException {
        List<String> insuranceManager = DBUtil.getInsuranceManager();

        readIM.getItems().clear();

        for (String im : insuranceManager) {
            MenuItem menuItem = new MenuItem(im);
            menuItem.setOnAction(event -> readIM.setText(im));
            readIM.getItems().add(menuItem);
        }
    }
    public void doneReadingInsuranceManager(){
        User user = DBUtil.getUser(extractIdFromText(readIM.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Read User:");
        alert.setHeaderText("User "+ user.getFullName());
        alert.setContentText(user.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = readIM1; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
    // Read policy owners
    @FXML
    private Button readPO; // Button to activate reading
    @FXML
    private MenuButton readPO1; // menu button to choose which policy holder to read
    @FXML
    private void readPolicyOwner() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("READ POLICY OWNER");
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

        Label policyOwnerLabel = new Label("Policy Owner:");
        policyOwnerLabel.setLayoutX(77);
        policyOwnerLabel.setLayoutY(126);
        policyOwnerLabel.setFont(new Font("Calibri", 18));

        readPO1 = new MenuButton("Select Policy Owner");
        readPO1.setLayoutX(248);
        readPO1.setLayoutY(124);
        readPO1.setPrefHeight(26);
        readPO1.setPrefWidth(367);

        readPO = new Button("READ");
        readPO.setLayoutX(179);
        readPO.setLayoutY(303);
        readPO.setPrefHeight(47);
        readPO.setPrefWidth(106);
        readPO.setFont(new Font("Calibri", 18));
        readPO.setOnAction(event -> doneReadingPolicyOwner());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, policyOwnerLabel, readPO1, readPO);

        populatereadPO1();
        read.getScene().setRoot(root);
    }
    private void populatereadPO1() throws SQLException {
        List<String> policyOwners = DBUtil.getPolicyOwners();

        readPO1.getItems().clear();

        for (String owner : policyOwners) {
            MenuItem menuItem = new MenuItem(owner);
            menuItem.setOnAction(event -> readPO1.setText(owner));
            readPO1.getItems().add(menuItem);
        }
    }
    public void doneReadingPolicyOwner(){
        User user = DBUtil.getUser(extractIdFromText(readPO1.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Read User:");
        alert.setHeaderText("User "+ user.getFullName());
        alert.setContentText(user.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = readPO; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }

    }
    // Read Policy Holder
    @FXML
    private MenuButton readPH1; // menu button to choose which policy holder to read
    @FXML
    private Button readPH; // Button to activate reading
    @FXML
    private void readPolicyHolder() throws SQLException {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #CDE8E5;");

        Label titleLabel = new Label("READ POLICY HOLDER");
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

        Label policyHolderLabel = new Label("Policy Holder:");
        policyHolderLabel.setLayoutX(77);
        policyHolderLabel.setLayoutY(126);
        policyHolderLabel.setFont(new Font("Calibri", 18));

        readPH1 = new MenuButton("Select Policy Holder");
        readPH1.setLayoutX(248);
        readPH1.setLayoutY(124);
        readPH1.setPrefHeight(26);
        readPH1.setPrefWidth(367);

        readPH = new Button("READ");
        readPH.setLayoutX(179);
        readPH.setLayoutY(303);
        readPH.setPrefHeight(47);
        readPH.setPrefWidth(106);
        readPH.setFont(new Font("Calibri", 18));
        readPH.setOnAction(event -> doneReadingPolicyHolder());

        root.getChildren().addAll(titleLabel, logoImageView, blendImageView, policyHolderLabel, readPH1, readPH);

        populatereadPH1();
        read.getScene().setRoot(root);
    }
    private void populatereadPH1() throws SQLException {
        List<String> policyHolders = DBUtil.getPH();

        readPH1.getItems().clear();

        for (String holder : policyHolders) {
            MenuItem menuItem = new MenuItem(holder);
            menuItem.setOnAction(event -> readPH1.setText(holder));
            readPH1.getItems().add(menuItem);
        }
    }
    public void doneReadingPolicyHolder(){
        User user = DBUtil.getUser(extractIdFromText(readPH1.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Read User:");
        alert.setHeaderText("User "+ user.getFullName());
        alert.setContentText(user.toString());
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = readPH; // Use any node from the current scene

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
