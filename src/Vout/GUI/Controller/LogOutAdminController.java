package Vout.GUI.Controller;

import javafx.event.ActionEvent;

/**
 * Controller pre poslednu scenu administratora - riesi len prepinanie scen
 */
public class LogOutAdminController extends BaseController {
    public void logOut(ActionEvent actionEvent) {
        this.changeScene("login.fxml");
    }

    public void home(ActionEvent actionEvent) {
        this.changeScene("admin.fxml");
    }
}
