package Vout.GUI.Controller;

import Vout.Entity.Candidate;
import Vout.Entity.Citizen;
import Vout.Service.VotingService;
import Vout.State;
import Vout.Vout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller pre hlasovanie obcanov. Prekonava metodu init.
 * Podla toho ake su vyhlasene volby ponukne zoznam kandidatov, z ktorych si obcan moze vybrat.
 * - Parlamentne volby - obcan si vyberie stranu, vyfiltruju sa mu kandidati len prislusnej strany a s nich ma moznost si vybrat
 * - Komunalne volby - vyfiltruju sa kandidati podla mesta, z ktoreho obcan pochazda
 * - Ostatne volby ponuknu automaticky zoznam kandidatov, ktori v nich kandiduju.
 */
public class VotingController extends BaseController {

    private VotingService votingService;

    private ObservableList<String> functionList = FXCollections.observableArrayList("Starosta/primátor",
            "Zástupca do okresného úradu");
    private int limit;
    private String selectedFunction;

    @FXML
    private TextArea voterArea;
    @FXML
    private ChoiceBox<String> partyChoiceBox;
    @FXML
    private ChoiceBox<String> functionChoiceBox;
    @FXML
    private TextArea candidatesArea;
    @FXML
    private TextField vote1Field;
    @FXML
    private TextField vote2Field;
    @FXML
    private TextField vote3Field;

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.votingService = new VotingService(this.application.getState());
        functionChoiceBox.setDisable(true);
        partyChoiceBox.setDisable(true);
        setComponents();
    }

    private void setComponents() {
        voterArea.setEditable(false);
        voterArea.setText("Hlasovací lístok pre: " + this.votingService.getState().getLoggedUser().getName());

        if (this.votingService.getState().getElection().getName().equals("Parlamentné voľby")) {
            partyChoiceBox.setDisable(false);
            ObservableList<String> partyList = FXCollections.observableArrayList(this.votingService.findPartiesString());
            partyChoiceBox.setItems(partyList);
        } else if (this.votingService.getState().getElection().getName().equals("Komunálne voľby")) {
            functionChoiceBox.setDisable(false);
            functionChoiceBox.setItems(functionList);
        } else {
            candidateList = votingService.findCandidates(votingService.getState().getElection().getName());
            candidatesArea.setText(null);
            int i = 0;
            for (Candidate c : candidateList) {
                i = i + 1;
                candidatesArea.appendText(i + ". " + c + "\n");
                System.out.println(c);
            }
        }

        State state = this.votingService.getState();
        limit = state.getElection().getVoteLimit();
        if (limit == 1) {
            vote2Field.setDisable(true);
            vote3Field.setDisable(true);
        } else {
            vote3Field.setDisable(false);
            vote2Field.setDisable(false);
        }
    }

    @FXML
    public void confirmVote(ActionEvent event) {
        String vote1 = vote1Field.getText();

        State state = votingService.getState();
        Citizen citizen = (Citizen) state.getLoggedUser();

        int index = Integer.parseInt(vote1) - 1;
        if (selectedFunction == null) {
            if (limit > 1) {
                String vote2 = vote2Field.getText();
                String vote3 = vote3Field.getText();
                int index2 = Integer.parseInt(vote2) - 1;
                int index3 = Integer.parseInt(vote3) - 1;
                citizen.assignVote(state, index, index2, index3, candidateList);
            } else {
                citizen.assignVote(state, index, candidateList);
            }
        } else if (selectedFunction.equals(functionList.get(1))) {
            citizen.assignVote(state, index, candidateList);
        } else if (selectedFunction.equals(functionList.get(2))) {
            citizen.assignVote(state, index, candidateList);
        }

        this.changeScene("logOutCitizen.fxml");
    }

    public void selectParty(ActionEvent event) {
        String selectedParty = partyChoiceBox.getValue();

        candidateList = votingService.findCandidatesP(selectedParty);

        candidatesArea.setText(null);
        int i = 0;
        for (Candidate c : candidateList) {
            i = i + 1;
            candidatesArea.appendText(i + ". " + c + "\n");
            System.out.println(c);
        }
    }

    public void selectFunction(ActionEvent event) {
        selectedFunction = functionChoiceBox.getValue();

        candidateList = votingService.findCandidatesF(selectedFunction);
        candidatesArea.setText(null);
        int i = 0;
        for (Candidate c : votingService.getState().getElection().getCandidates()) {
            i = i + 1;
            candidatesArea.appendText(i + ". " + c + "\n");
            System.out.println(c);
        }
    }
}


