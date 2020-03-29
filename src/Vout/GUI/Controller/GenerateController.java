package Vout.GUI.Controller;

import Vout.Service.GeneratorService;
import Vout.Vout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller pre pridavanie do objektov do hlavnej databazy. Prekonava metodu init.
 * Podla prvotnej volby administratora sa rozhodne, ci sa generuje Kandidat alebo obcan.
 * Nasledne sa nacitaju atributy zo vstupu, a zavola sa metoda generateData, ktora je obsiahnuta v prislusnom Service
 * pre tento controller.
 * Taktiez obsahuje metody pre pridavanie a mazanie objektov Party (stran) z databazy.
 */
public class GenerateController extends BaseController {

    private GeneratorService generatorService;
    private String firstName;
    private String lastName;
    private String birth;
    private String PIN;
    private String city;
    private String district;
    private String region;
    private String nationality;
    private String party;
    private String election;
    private ObservableList<String> types = FXCollections.observableArrayList("Občan",
            "Občan - kandidát");
    private ObservableList<String> electionList = FXCollections.observableArrayList("Prezidentské voľby",
            "Parlamentné voľby", "Voľby do EU parlamentu", "Komunálne voľby");
    private ObservableList<String> regionList = FXCollections.observableArrayList("Bratislavský",
            "Trnavský", "Nitriansky", "Trenčiansky", "Žilinský", "Banskobystrický", "Prešovský", "Košický");
    private String selectedType;

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField pinField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField districtField;
    @FXML
    private ChoiceBox<String> regionChoiceBox;
    @FXML
    private TextField nationalityField;
    @FXML
    private ChoiceBox<String> partyChoiceBox;
    @FXML
    private ChoiceBox<String> electionChoiceBox;
    @FXML
    private ChoiceBox<String> whoChoiceBox;
    @FXML
    private TextField actionParty;

    @FXML
    private void selectType(ActionEvent event) {
        selectedType = whoChoiceBox.getValue();
        System.out.println("Typ osoby: " + selectedType);

        if (selectedType.equals("Občan")) {
            partyChoiceBox.setDisable(true);
            electionChoiceBox.setDisable(true);
        } else if (selectedType.equals("Občan - kandidát")) {
            partyChoiceBox.setDisable(false);
            electionChoiceBox.setDisable(false);

            ObservableList<String> partyList = FXCollections.observableArrayList(this.generatorService.findParties());
            partyChoiceBox.setItems(partyList);
        }
    }

    public void addToDatabase(ActionEvent actionEvent) {

        firstName = fNameField.getText();
        lastName = lNameField.getText();
        birth = dateField.getText();
        PIN = pinField.getText();
        city = cityField.getText();
        district = districtField.getText();
        region = regionChoiceBox.getValue();
        nationality = nationalityField.getText();
        party = partyChoiceBox.getValue();
        election = electionChoiceBox.getValue();

        if (selectedType.equals("Občan")) {

            this.generatorService.generateData(firstName, lastName, birth, PIN, city, district, region, nationality);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nový občan: " + firstName + " " + lastName);
            alert.setHeaderText("Vygenerované dáta boli uložené do databázy");
            alert.showAndWait();
        }
        if (selectedType.equals("Občan - kandidát")) {

            this.generatorService.generateData(firstName, lastName, birth, PIN, city, district, region, nationality,
                    party, election);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nový občan - kandidát: " + firstName + " " + lastName);
            alert.setHeaderText("Vygenerované dáta boli uložené do databázy");
            alert.showAndWait();
        }
        this.changeScene("admin.fxml");
    }

    @Override
    public void init(Vout application, Stage stage) {
        super.init(application, stage);
        this.generatorService = new GeneratorService(this.application.getState());
    }

    @FXML
    private void initialize() {
        electionChoiceBox.setItems(electionList);
        whoChoiceBox.setItems(types);
        regionChoiceBox.setItems(regionList);
    }

    @FXML
    private void addParty(ActionEvent event) {
        String addedParty = actionParty.getText();
        generatorService.addParty(addedParty);

        ObservableList<String> partyList = FXCollections.observableArrayList(this.generatorService.findParties());
        partyChoiceBox.setItems(partyList);
        actionParty.clear();
    }

    public void removeParty(ActionEvent actionEvent) {
        String removedParty = actionParty.getText();
        generatorService.removeParty(removedParty);

        ObservableList<String> partyList = FXCollections.observableArrayList(this.generatorService.findParties());
        partyChoiceBox.setItems(partyList);
        actionParty.clear();
    }
}
