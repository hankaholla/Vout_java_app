package Vout.GUI.Controller;

import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.Service.ResultsService;
import Vout.Vout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller pre zobrazenie vysledkov volieb. Prekonava metodu init.
 * Zobrazi priebezne vysledky volieb. Po kliknuti na tlacidlo system automaticky vyhdnoti volby a
 * vynuluje pocet hlasov jednotlivych stran a kandidatov.
 */
public class ResultsController extends BaseController {

    private ResultsService resultsService;
    private Candidate winnerCandidate;
    private Party winnerParty;


    @FXML
    private TextArea electionName;
    @FXML
    private TextArea results;

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.resultsService = new ResultsService(this.application.getState());
        setComponents();
    }

    private void setComponents() {
        String election = resultsService.getState().getElection().toString();
        electionName.appendText(election);
        for (Candidate c : resultsService.findCandidates(election))
            results.appendText(c.getName() + ": " + c.getNumberOfVotes() + "\n");
    }

    public void closeElection(ActionEvent actionEvent) {
        if (resultsService.getState().getElection().getName().equals("Parlamentné voľby")) {
            winnerParty = resultsService.countPartyVotes();
            winnerCandidate = resultsService.countVotes();
            showMessageDialog(winnerParty, winnerCandidate);
        } else {
            winnerCandidate = resultsService.countVotes();
            showMessageDialog(winnerCandidate);
        }
        resultsService.removeVotes(resultsService.getState().getElection().getName());
    }

    private void showMessageDialog(Party winnerParty, Candidate winnerCandidate) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oznam o výsledku");
        alert.setHeaderText("Voľby boli ukončené. Víťazom je:");
        if (winnerCandidate == null) {
            alert.setContentText("STRANA: " + winnerParty.getName() + "\n" + "Nie je jednoznačný víťaz, zopakujte voľby");
        } else {
            alert.setContentText("STRANA: " + winnerParty.getName() + "\n" + "KANDIDÁTI: " + winnerCandidate.toString());
        }

        alert.showAndWait();
    }

    public void showMessageDialog(Candidate winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oznam o výsledku");
        alert.setHeaderText("Voľby boli ukončené. Víťazom je:");
        alert.setContentText("Výsledok je nejednoznačný, opakujte voľby");
        if (winnerCandidate == null) {
            alert.setContentText("Nie je jednoznačný víťaz, zopakujte voľby");
        } else {
            alert.setContentText(winner.toString());
        }

        alert.showAndWait();
    }

    public void goHome(ActionEvent actionEvent) {
        this.changeScene("admin.fxml");
    }
}
