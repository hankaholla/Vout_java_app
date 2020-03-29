package Vout.Entity;

import java.io.Serializable;

/**
 * Trieda Party
 * - vytvaraj objekty Party, ktore sa vkladaju do databazy stran, a priraduju sa jednotlivym kandidatom
 */
public class Party implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2510108062666791269L;
    private String name;
    private int numberOfVotes;

    public Party(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public String toString() {
        return this.name;
    }


}