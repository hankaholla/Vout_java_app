package Vout.Entity.Election;

import Vout.Entity.Candidate;

import java.io.Serializable;
import java.util.List;

/**
 * Abstraktna trieda Election
 * - oddeduju sa od nej triedy konkretnych druhov volieb. Je abstraktna, lebo objekty volby sa pri vyhlaseni
 * vztvara priamo uz daneho typu volieb z oddedenych tried.
 * Kazde volby maju ale spolocne atributy - datum, limit, nazov a zoznam kandidatov
 */
public abstract class Election implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1769248294931974214L;
    protected String date;
    protected String name;
    protected int voteLimit;
    protected List<Candidate> candidates;

    public Election(String name, List<Candidate> candidates, String date, int voteLimit) {
        this.candidates = candidates;
        this.date = date;
        this.voteLimit = voteLimit;
        this.name = name;
    }

    public List<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVoteLimit() {
        return this.voteLimit;
    }

    public void setVoteLimit(int limit) {
        this.voteLimit = limit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName() + ": " + this.getDate();
    }
}