package Vout.Service;

import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.State;

import java.util.List;

/**
 * Service pre vyhodnotenie volieb
 * Priebezne vyhodnocuje pocet hlasov jednotlivych kandidatov. Riesi tiez celkove vyhodnotenie volieb a spocitanie hlasov pri ich ukonceni.
 * Prekonava metodu findCandidates.
 */
public class ResultsService extends Service {

    public ResultsService(State state) {
        super(state);
    }

    @Override
    public List<Candidate> findCandidates(String election) {
        candidates = state.getElection().getCandidates();
        return candidates;
    }

    public void removeVotes(String election) {
        findCandidates(election);
        for (Candidate c : state.getElection().getCandidates()) {
            c.setNumberOfVotes(0);
            state.getDatabase().save();
        }
    }

    public Candidate countVotes() {

        int winnerVote = 0;
        int noWinners = 0;
        Candidate winner = null;
        for (Candidate c : findCandidates(state.getElection().getName())) {
            int votes = c.getNumberOfVotes();
            if (votes > winnerVote) {
                winner = c;
                noWinners++;
            }
        }
        if (noWinners > 1)
            return null;
        else return winner;
    }

    public Party countPartyVotes() {
        int noWinnersParty = 0;
        Party winnerParty = null;
        int winnerVote = 0;
        for (Party p : state.getPartyDatabase().parties) {
            int votes = p.getNumberOfVotes();
            if (votes > winnerVote) {
                winnerParty = p;
                noWinnersParty++;
            }
        }
        if (noWinnersParty > 1)
            return null;
        else return winnerParty;
    }
}
