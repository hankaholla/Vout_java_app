package Vout.GUI.Controller;

import Vout.Entity.Admin;
import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.Service.DeclareService;
import Vout.Service.Exceptions.ElectionNotDeclared;
import Vout.State;
import Vout.Vout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller pre vyhlasovanie volieb. Prekonava metodu init. Zoberie zo vstupu vsetky atributy pre prave vyhlasovane volby
 * a zavola metodu declare v triede Admin s parametrami danych volieb. Kedze kazde volby maju ine parametre, metoda
 * declare v triede Admin je pretazena na zaklade toho, ake parametre sa posielaju.
 */
public class DeclareController extends BaseController {

    private DeclareService declareService;

    private ObservableList<String> electionList = FXCollections.observableArrayList("Prezidentské voľby",
            "Parlamentné voľby", "Voľby do EU parlamentu", "Komunálne voľby");
    private List<Party> partyList = new ArrayList<>();

    private String selectedElection;
    private String date;
    private Integer roundNo;
    private int candidateLimit;

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField limit;
    @FXML
    private TextField round;
    @FXML
    private TextArea candidatesArea;
    @FXML
    private TextArea partyArea;

    @FXML
    private void initialize() {
        choiceBox.setItems(electionList);
        partyArea.setDisable(true);
        round.setDisable(true);
    }

    @FXML
    private void selectElection(ActionEvent event) {
        selectedElection = choiceBox.getValue();
        if (selectedElection.equals("Parlamentné voľby")) {
            partyArea.setDisable(false);
            round.setDisable(true);

            partyList = declareService.findParties();
            for (Party p : partyList) {
                partyArea.appendText(p + "\n");
                System.out.println(p);
            }
        }
        if (selectedElection.equals("Prezidentské voľby")) {
            round.setDisable(false);
            partyArea.setDisable(true);
            partyArea.clear();
        }
        if (selectedElection.equals("Voľby do EU parlamentu")) {
            round.setDisable(true);
            partyArea.setDisable(true);
            partyArea.clear();
        }
        if (selectedElection.equals("Komunálne voľby")) {
            round.setDisable(true);
            partyArea.setDisable(true);
            partyArea.clear();
        }
        System.out.println("Vyhlásia sa voľby: " + selectedElection);

        candidates = declareService.findCandidates(selectedElection);
        candidatesArea.setText(null);
        for (Candidate c : candidates) {
            candidatesArea.appendText(c + "\n");
        }
    }

    @FXML
    private void selectDate() {
        date = datePicker.getValue().toString();
        System.out.println("Voľby sa budú konať v dátume: " + date);
    }

    @FXML
    private void confirmForm(ActionEvent event) throws ElectionNotDeclared {
        Admin admin = (Admin) this.declareService.getState().getLoggedUser();
        candidateLimit = Integer.valueOf(limit.getText());

        State state = this.declareService.getState();

        if (selectedElection.equals("Prezidentské voľby")) {
            roundNo = Integer.valueOf(round.getText());
            admin.declare(state, selectedElection, date, candidates, roundNo, candidateLimit);
        } else if (selectedElection.equals("Parlamentné voľby")) {
            admin.declare(state, selectedElection, date, candidates, candidateLimit, declareService.getState().getPartyDatabase().parties);
        } else {
            admin.declare(state, selectedElection, date, candidates, candidateLimit);
        }

        this.changeScene("logoutAdmin.fxml");
        System.out.println("VOĽBY: " + selectedElection + " " + date + " " + roundNo + " " + candidateLimit);
    }

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.declareService = new DeclareService(this.application.getState());
    }
}

