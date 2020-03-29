package Vout.Entity.Election;

import Vout.Entity.Candidate;

import java.util.List;

/**
 * Prezidentske volby
 * - okrem zakladnych atributov obsahuju navyse informaciu o tom, ktore kolo volieb prebieha
 */
public class PresidentElection extends Election {

    /**
     *
     */
    private static final long serialVersionUID = 941875539799981683L;
    private int round;

    public PresidentElection(String name, List<Candidate> candidates, String date, int voteLimit, int round) {
        super(name, candidates, date, voteLimit);
        this.round = round;
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}