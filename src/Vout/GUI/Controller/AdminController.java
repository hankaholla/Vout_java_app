package Vout.GUI.Controller;

import Vout.Service.AdminService;
import Vout.Service.Exceptions.ElectionNotDeclared;
import Vout.Vout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controller pre prihlasenie administratora, ponuka moznosti jeho pravomoci - vyhlasit volby, pridat ludi do databazy
 * zobrazit vysledky volieb. Prekonava metodu init.
 */
public class AdminController extends BaseController {

    AdminService adminService;

    @FXML
    private void declareElection(ActionEvent event) {
        this.changeScene("declaringForm.fxml");
    }

    @FXML
    private void generateData(ActionEvent event) {
        this.changeScene("generateCitizen.fxml");
    }

    @FXML
    private void displayStatistics(ActionEvent event) {
        try {
            if (adminService.getState().getElection() == null) {
                throw new ElectionNotDeclared();
            }
            this.changeScene("results.fxml");
        } catch (ElectionNotDeclared electionNotDeclared) {
            this.showNotFoundAlert();
        }
    }

    private void showNotFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("404 Not found");
        alert.setHeaderText("Ešte ste nevyhlásili voľby, výsledky nie sú dostupné");

        alert.showAndWait();
    }

    @FXML
    private void logOut(ActionEvent event) {
        this.changeScene("login.fxml");
    }

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.adminService = new AdminService(this.application.getState());
    }

}
