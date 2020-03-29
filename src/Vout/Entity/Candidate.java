package Vout.Entity;

import java.io.Serializable;

/**
 * Trieda Kandidat
 * - oddedena od triedy Obcan. Ma navyse parametre pocet hlasov, nazov volieb, do ktorych dany kandidat kandiduje,
 * a stranu do ktorej patri. Tu je vyuzita agregacia, kedze strana je aj objektom triedy Party.
 * Kandidat ma okrem setterov a getterov aj prekonanu metodu toString, ktora poskytuje jeho meno a stranu, oproti
 * metode toString v obcanovi, ktora poskytuje meno a PIN obcana.
 */
public class Candidate extends Citizen implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8773890901491061007L;
    private Party party;
    private String election;
    private Integer numberOfVotes;

    public Candidate(String firstName, String lastName, String birth, String PIN, String city,
                     String district, String region, String nationality, Party party, String election) {
        super(firstName, lastName, birth, PIN, city, district, region, nationality);
        this.party = party;
        this.numberOfVotes = 0;
        this.election = election;
    }

    public String getElection() {
        return this.election;
    }

    public void setElection(String election) {
        this.election = election;
    }

    public Party getParty() {
        return this.party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Integer getNumberOfVotes() {
        return this.numberOfVotes;
    }

    public void setNumberOfVotes(Integer number) {
        this.numberOfVotes = number;
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getParty().getName();
    }

}