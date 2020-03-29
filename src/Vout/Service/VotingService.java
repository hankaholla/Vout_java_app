package Vout.Service;

import Vout.Entity.Candidate;
import Vout.Entity.Citizen;
import Vout.Entity.Party;
import Vout.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Service pre samotny akt volby Obcana
 * pomocou filtrovania najde kandidata zo zoznamu kandidatov ulozenych v objekte State
 * , ktoremu chce obcan pridat hlas, a zvysi mu pocet hlasov.
 * To iste robi v pripade parlamentnych volieb pre jednotlive strany.
 * Prekonava metodu findCandidates.
 */
public class VotingService extends Service {

    public VotingService(State state) {
        super(state);
    }

    public ArrayList<String> findPartiesString() {
        List<Party> parties = state.getPartyDatabase().parties;
        ArrayList<String> foundParties = new ArrayList<>();
        for (Party p : parties) {
            if (!p.getName().equals("nezávislý")) {
                foundParties.add(p.getName());
            }
        }
        return foundParties;
    }

    public List<Candidate> findCandidatesP(String selectedParty) {
        candidates = findCandidates(state.getElection().getName());
        for (Candidate c : candidates) {
            if (c.getParty().toString().equals(selectedParty)) {
                candidateList.add(c);
            }
        }
        for (Party p : state.getPartyDatabase().parties) {
            if (p.toString().equals(selectedParty)) {
                p.setNumberOfVotes(p.getNumberOfVotes() + 1);
                state.getPartyDatabase().save();
            }
        }
        return candidateList;
    }

    public List<Candidate> findCandidatesF(String selectedFunction) {
        Citizen citizen = (Citizen) state.getLoggedUser();

        String city = citizen.getCity();
        String district = citizen.getDistrict();

        candidates = findCandidates(state.getElection().getName());
        if (selectedFunction.equals("Starosta/primátor")) {
            for (Candidate c : candidates)
                if (c.getCity().equals(city))
                    candidateList.add(c);
        } else if (selectedFunction.equals("Zástupca do okresného úradu")) {
            for (Candidate c : candidates)
                if (c.getDistrict().equals(district))
                    candidateList.remove(c);
        }
        return candidateList;
    }

    @Override
    public List<Candidate> findCandidates(String election) {
        candidates = state.getElection().getCandidates();
        return candidates;
    }

}
