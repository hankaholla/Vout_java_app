package Vout.Entity.Election;

import Vout.Entity.Candidate;
import Vout.Entity.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * Parlamentne volby
 * - okrem zakladnych obsahuju navyse atribut parties, zoznam stran, ktore kandiduju (povolene administratotrom)
 */
public class ParliamentaryElection extends Election {

    /**
     *
     */
    private static final long serialVersionUID = -6434876005342818447L;
    private List<Party> parties = new ArrayList<>();

    public ParliamentaryElection(String name, List<Candidate> candidates, String date, int voteLimit, List<Party> parties) {
        super(name, candidates, date, voteLimit);
        this.parties = parties;
    }


    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }
}