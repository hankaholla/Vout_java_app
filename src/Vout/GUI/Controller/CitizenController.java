package Vout.GUI.Controller;

import Vout.Service.CitizenService;
import Vout.Vout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller pre prihlasenie obcana. Prekonava metodu init. Zobrazi obcanovi ake aktualne volby su vyhlasene.
 * Nasledne po kliknuti na tlacidlo vygeneruje hlasovaci listok pre daneho obcana.
 */
public class CitizenController extends BaseController {

    @FXML
    public TextArea electionName;
    @FXML
    public TextArea instructions;
    private CitizenService citizenService;
    private String instr;

    @FXML
    private void printVotingTicket(ActionEvent event) {
        System.out.println("Hlasovaci listok pre " + this.citizenService.getState().getLoggedUser().getName());
        this.changeScene("votingTicket.fxml");
    }

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.citizenService = new CitizenService(this.application.getState());
        setComponents();
    }

    public void setComponents() {
        electionName.appendText(citizenService.getState().getElection().toString());
        int limit = citizenService.getState().getElection().getVoteLimit();
        if (limit > 1) instr = " kandidátom";
        else instr = " kandidátovi";
        instructions.appendText("V týchto voľbách môžte dať svoj hlas " + limit + instr);
    }


}
