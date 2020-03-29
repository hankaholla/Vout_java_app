package Vout.Service;

import Vout.Database.CandidateFilter;
import Vout.Database.Filter;
import Vout.Database.PartyDatabase;
import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Service vyhlasovania volieb
 * Vykonava metody ktore pomocou filtrovania databaz vratia kandidatov a/alebo strany, ktore sa ulozia do atributu
 * objektu state,a bude nad nimi bezat cely program, aby tak boli dostupne pocas celeho behu.
 */
public class DeclareService extends Service {

    private List<Party> parties = new ArrayList<>();

    public DeclareService(State state) {
        super(state);
    }

    @Override
    public List<Candidate> findCandidates(String election) {
        Filter candidateFilter = new CandidateFilter();
        candidateFilter.addAttribute("election", election, "==");
        candidates = (List<Candidate>) (Object) state.getDatabase().find(candidateFilter);
        for (Candidate c : candidates)
            System.out.println(c);

        return candidates;
    }

    public List<Party> findParties() {
        PartyDatabase database = this.state.getPartyDatabase();
        database.save();
        parties = this.state.getPartyDatabase().parties;
        return parties;
    }
}
