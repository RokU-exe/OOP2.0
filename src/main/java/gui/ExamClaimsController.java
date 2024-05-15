package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Claim;
import utils.DBUtil;

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
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();  // or navigate to the previous scene
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
