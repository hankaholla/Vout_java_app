package Vout;

import Vout.Database.Database;
import Vout.Database.PartyDatabase;
import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.GUI.Controller.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Hlavna trieda celeho programu, ktora obsahuje metodu main. Odtialto sa spusta cely program, nacitavaju sa databazy volanim metod pre deserializaciu,
 * a nacitavaju sa sceny potrebne pre GUI.
 * Taktiez je tu aj metoda ktora ukoncuje program.
 **/
public class Vout extends Application {

    private State state;

    public static void main(String[] args) {
            launch(args);
    }

    public State getState() {
        return state;
    }

    /**
     * Prekonana metoda start - pokyn pre naciatnie databaz a scen GUI
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            this.state = new State();
            Database database = new Database();
            database.load();
            this.state.setDatabase(database);

            PartyDatabase partyDatabase = new PartyDatabase();
            partyDatabase.load();
            this.state.setPartyDatabase(partyDatabase);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/Resources/login.fxml"));
            Parent root = loader.load();
            BaseController controller = loader.getController();
            controller.init(this, primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setTitle("Vout e-Voting");
            primaryStage.getIcons().add(new Image("Vout/GUI/Resources/login/check.png"));

            primaryStage.setScene(scene);
            primaryStage.show();
            //vlastna vynimka
        } catch (Exception sceneNotDisplayed) {
            this.stop();
        }
    }

    /**
     * Prekonana metoda stop - zastavenie programu, serializacia databaz, ak boli vyhlasene volby,
     * pocet hlasov pri jednotlivzch kandidatoch sa vynuluje
     */
    @Override
    public void stop() {
        System.out.println("Aplikácia sa zatvára");
        State state = this.getState();
        state.getPartyDatabase().save();
        state.getDatabase().save();

        if (state.getElection() != null) {
            for (Candidate c : state.getElection().getCandidates()) {
                c.setNumberOfVotes(0);
                state.getDatabase().save();
            }
            for (Party p : state.getPartyDatabase().parties) {
                p.setNumberOfVotes(0);
                state.getPartyDatabase().save();
            }
        }

    }
}