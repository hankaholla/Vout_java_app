package Vout.Entity.Election;

import Vout.Entity.Candidate;

import java.util.List;

/**
 * Komunalne volby
 * - maju len zakladne atributy
 */
public class MunicipalElection extends Election {

    /**
     *
     */
    private static final long serialVersionUID = 6829661902432265308L;

    public MunicipalElection(String name, List<Candidate> candidates, String date, int voteLimit) {
        super(name, candidates, date, voteLimit);
    }
}