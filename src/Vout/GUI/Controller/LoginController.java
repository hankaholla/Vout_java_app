package Vout.GUI.Controller;

import Vout.Entity.Admin;
import Vout.Service.Exceptions.ElectionNotDeclared;
import Vout.Service.Exceptions.UserNotAuthenticatedException;
import Vout.Service.LoginService;
import Vout.State;
import Vout.User;
import Vout.Vout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller pre prvvu scenu - prihlasenie. Prekonava metodu init.
 * Overuje totoznost prihlasovanej osoby a rozhodne, ci sa prihlasuje Obcan alebo Admin.
 * Pouzitim RTTI (instanceof) po filtrovani databazy zavola metodu podla objektu, ktory vrati filter do premennej
 * typu User (interface). Nasledne zavola metodu login bud v triede Admin alebo Citizen.
 */
public class LoginController extends BaseController {
    @FXML
    private TextField pin;

    private LoginService loginService;

    @FXML
    private void confirm(ActionEvent event) throws ElectionNotDeclared {
        try {

            User findUser = this.loginService.auth(pin.getText());
            State state = loginService.getState();
            findUser.login(findUser, state);

            if (findUser instanceof Admin) {                 //RTTI
                this.changeScene("admin.fxml");
            } else {
                if (loginService.getState().getElection() == null) {
                    throw new ElectionNotDeclared();
                }
                this.changeScene("citizen.fxml");
            }
        } catch (UserNotAuthenticatedException e) {
            this.showUnsuccessfulAlert();
        } catch (ElectionNotDeclared e) {
            this.showNotFoundAlert();
        }
    }

    private void showUnsuccessfulAlert() {
        pin.clear();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Zlé prihlasovacie údaje");
        alert.setHeaderText("Zlé prihlasovacie údaje");
        alert.showAndWait();
    }

    private void showNotFoundAlert() {
        pin.clear();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("404 Not found");
        alert.setHeaderText("Momentálne nie sú dostupné žiadne voľby, v ktorých by " +
                "ste mohli hlasovať. Vráťte sa neskôr prosím.");

        alert.showAndWait();
    }

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.loginService = new LoginService(this.application.getState());
    }
}


