package Vout.GUI.Controller;

import Vout.Entity.Candidate;
import Vout.Vout;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Hlavna trieda hierarchie controllerov, ktora obsahuje vlastne "event handlery" pre komponenty GUI.
 * Obsahuje prekonavanie metody init, pri ktorej sa vzdy preberie aktualny stav celeho programu.
 * Implementuje aj metodu changeScene, ktora prepina sceny.
 * Kazdy oddedeny controller obsahuje agregaciu - atribut service podla svojho nazvu, ktory pochadza z hierarchie Servic-ov.
 * Oddeluje sa tym aplikacna logika od GUI.
 */
public class BaseController {

    protected Vout application;
    protected Stage stage;
    protected List<Candidate> candidates;  //ti ktori su vyhlaseni
    protected List<Candidate> candidateList; //ti ktorych ma na vyber konkretny obcan pri hlasovani, podla svojich atributov/volby

    public void init(Vout application, Stage stage) {
        this.application = application;
        this.stage = stage;
    }

    public void changeScene(String resource) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Resources/" + resource));
        Scene scene = null;
        try {
            Parent root = loader.load();
            BaseController controller = loader.getController();
            controller.init(this.application, this.stage);
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }
}
