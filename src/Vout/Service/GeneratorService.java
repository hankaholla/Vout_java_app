package Vout.Service;

import Vout.Database.Database;
import Vout.Entity.Candidate;
import Vout.Entity.Citizen;
import Vout.Entity.Party;
import Vout.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Service pre pridavanie do databazy
 * Na zaklade parametrov z Controllera prida objekt noveho obcana/kandidata do databazy.
 * Sluzi tiez na pridavanie/odtstranovanie objektov Party s databazy stran.
 */
public class GeneratorService extends Service {

    private List<Party> parties = new ArrayList<>();

    public GeneratorService(State state) {
        super(state);
    }

    public void generateData(String firstName, String lastName, String birth, String PIN,
                             String city, String district, String region, String nationality) {
        Database database = this.state.getDatabase();
        Citizen newCitizen = new Citizen(firstName, lastName, birth, PIN, city, district, region, nationality);
        database.add(newCitizen);
        this.state.getDatabase().save();
    }

    public void generateData(String firstName, String lastName, String birth, String PIN,
                             String city, String district, String region, String nationality, String party,
                             String election) {
        Database database = this.state.getDatabase();
        Party partyTemp = null;
        parties = this.state.getPartyDatabase().parties;
        for (Party p : parties) {
            if (p.getName().equals(party)) {
                partyTemp = p;
                break;
            }
        }
        Candidate newCandidate = new Candidate(firstName, lastName, birth, PIN, city, district, region, nationality,
                partyTemp, election);
        database.add(newCandidate);
        this.state.getDatabase().save();
    }

    public ArrayList<String> findParties() {
        parties = state.getPartyDatabase().parties;
        ArrayList<String> foundParties = new ArrayList<>();
        for (Party p : parties) {
            foundParties.add(p.getName());
        }
        return foundParties;
    }

    public void removeParty(String removedParty) {
        parties = state.getPartyDatabase().parties;
        for (Party p : parties) {
            if (p.toString().equals(removedParty)) {
                state.getPartyDatabase().remove(p);
                state.getPartyDatabase().save();
                break;
            }
        }
    }

    public void addParty(String addedParty) {
        state.getPartyDatabase().add(new Party(addedParty));
        state.getPartyDatabase().save();
    }
}
