package Vout.Service;

import Vout.Database.CandidateFilter;
import Vout.Database.Filter;
import Vout.Entity.Candidate;
import Vout.Entity.Party;
import Vout.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda Service
 * Hlavna trieda hierarchie Servicov, velmi dolezita z hladiska oddelenia aplikacnej logiky od GUI.
 * V triedach tejto hierarchie sa vykonavaju vsetky systemove metody, ktore nasledne volaju metody jednotlivych objektov, ktor
 * aktualne interaguju. Tato hierarchia implementuje princip agregacie, prekonavanie metody findCandidates ako aj generickost.
 * V ramci agregacie obsahuje atribut state, ktory si podavaju pocas behu programu jednotlive objekty Service.
 */
public class Service {

    public State state;
    protected List<Candidate> candidateList = new ArrayList<>();
    protected List<Party> partyList = new ArrayList<>();
    protected List<Candidate> candidates = new ArrayList<>();

    public Service(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Candidate> findCandidates(String election) {
        Filter candidateFilter = new CandidateFilter();
        candidateFilter.addAttribute("election", election, "==");
        candidates = (List<Candidate>) (Object) state.getDatabase().find(candidateFilter);
        return null;
    }
}

