package Vout;

import Vout.Database.Database;
import Vout.Database.PartyDatabase;
import Vout.Entity.Election.Election;

/**
 * Trieda state
 * Pri spusteni programu sa vytvori objekt tejto triedy, ktory si pocas jeho behu predavaju medzi sebou
 * triedy hierarchie Service. Tento objekt ma atributy, ktore su dolezite pre beh celeho programu -
 * ake volby su vyhlasene, databazu ludi, kto je prihlaseny a databazu stran. Tato trieda obsahuje settery
 * a gettery pre tieto atributy, aby k nim mohli triedy Service pristupovat.
 */
public class State {

    protected Election election;
    protected Database database = new Database();
    protected User loggedUser;
    protected PartyDatabase partyDatabase;

    public Database getDatabase() {
        return this.database;
    }

    protected void setDatabase(Database database) {
        this.database = database;
    }

    public PartyDatabase getPartyDatabase() {
        return this.partyDatabase;
    }

    public void setPartyDatabase(PartyDatabase partyDatabase) {
        this.partyDatabase = partyDatabase;
    }

    public Election getElection() {
        return this.election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

}
