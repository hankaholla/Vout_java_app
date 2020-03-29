package Vout.Entity.Election;

import Vout.Entity.Candidate;

import java.util.List;

/**
 * Volby do EU parlamentu
 * - maju len zakladne atributy
 */
public class EuropeanElection extends Election {

    /**
     *
     */
    private static final long serialVersionUID = -7317584596367755660L;

    public EuropeanElection(String name, List<Candidate> candidates, String date, int voteLimit) {
        super(name, candidates, date, voteLimit);
    }
}