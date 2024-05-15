package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Claim;
import utils.DBUtil;

import java.io.IOException;

public class ExamClaimsController {

    @FXML
    private TableView<Claim> claimsTable;

    @FXML
    private TableColumn<Claim, Integer> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> policyHolderColumn;

    @FXML
    private TableColumn<Claim, String> statusColumn;

    @FXML
    private Button backButton;

    @FXML
    private Button logoutButton;

    private ObservableList<Claim> claimsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        claimIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        policyHolderColumn.setCellValueFactory(new PropertyValueFactory<>("policyHolderName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        claimsTable.setItems(claimsData);

        loadClaimsToExamine();
    }

    private void loadClaimsToExamine() {
        claimsData.setAll(DBUtil.getClaimsToExamine());
    }

    @FXML
    private void handleBack() throws IOException {
        navigateToPage("/gui/ManagerGUI/Manager.fxml");
    }

    @FXML
    private void handleLogout() throws IOException {
        navigateToPage("/gui/Login.fxml");
    }

    private void navigateToPage(String fxmlFile) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the requested page: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
