package Vout.Entity;

import Vout.Entity.Election.*;
import Vout.Service.Exceptions.ElectionNotDeclared;
import Vout.State;
import Vout.User;

import java.util.List;

/**
 * Trieda administrator
 * - implementuje interface User a jeho metodu login, kedze sa prihlasenie vykonava inak ako v triede Obcan, ktora tuto
 * metodu tiez implementuje. Okrem toho obsahuje metodu declare, cize vyhlasenie volieb, kedze prave v tom spociva
 * funkcia administratora = vyhlasuje volby.
 */
public class Admin implements User {

    public State state;

    public Admin() {
    }

    /**
     * Prihlasenie administratora, metoda z interface User
     *
     * @param admin
     * @param state - do objektu State sa uchova, ze prihlaseny je administrator
     */
    public void login(User admin, State state) {
        state.setLoggedUser(admin);
        System.out.println("Prihlaseny admin");
    }

    /**
     * Pretazena metoda vyhlasenia volieb, parametre pre Prezidentske volby
     *
     * @param state
     * @param selectedElection
     * @param date
     * @param candidateList
     * @param round
     * @param candidateLimit
     * @throws ElectionNotDeclared
     */
    public void declare(State state, String selectedElection, String date, List<Candidate> candidateList,
                        int round, int candidateLimit) {         //prezidentske

        System.out.println("Vyhlásia sa voľby: " + selectedElection);
        System.out.println("Voľby sa budú konať v dátume: " + date);
        Election finalElection = new PresidentElection(selectedElection, candidateList, date, candidateLimit, round);
        state.setElection(finalElection);
    }

    /**
     * Pretazena metoda vyhlasenia volieb - parametre pre parlamentne volby
     *
     * @param state
     * @param selectedElection
     * @param date
     * @param candidateList
     * @param candidateLimit
     * @param parties
     */
    public void declare(State state, String selectedElection, String date, List<Candidate> candidateList, int candidateLimit,
                        List<Party> parties) {

        System.out.println("Vyhlásia sa voľby: " + selectedElection);
        System.out.println("Voľby sa budú konať v dátume: " + date);
        Election finalElection = new ParliamentaryElection(selectedElection, candidateList, date, candidateLimit, parties);
        state.setElection(finalElection);
    }

    /**
     * Pretazena metoda vyhlasenia volieb - parametre pre ostatne volby
     *
     * @param state
     * @param selectedElection
     * @param date
     * @param candidateList
     * @param candidateLimit
     */
    public void declare(State state, String selectedElection, String date, List<Candidate> candidateList,
                        int candidateLimit) {
        System.out.println("Vyhlásia sa voľby: " + selectedElection);
        System.out.println("Voľby sa budú konať v dátume: " + date);
        if (selectedElection.equals("Komunálne voľby")) {
            Election finalElection = new MunicipalElection(selectedElection, candidateList, date, candidateLimit);
            state.setElection(finalElection);
        } else {
            Election finalElection = new EuropeanElection(selectedElection, candidateList, date, candidateLimit);
            state.setElection(finalElection);
        }
    }

    @Override
    public String getName() {
        return null;
    }
}
