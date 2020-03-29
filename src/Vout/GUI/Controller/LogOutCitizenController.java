package Vout.GUI.Controller;

import javafx.event.ActionEvent;

/**
 * Controller pre poslednu triedu Obcana - riesi len prepinanie scen
 */
public class LogOutCitizenController extends BaseController {

    public void logOut(ActionEvent actionEvent) {
        this.changeScene("login.fxml");
    }

}
