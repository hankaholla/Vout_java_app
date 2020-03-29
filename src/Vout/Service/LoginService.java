package Vout.Service;

import Vout.Database.CandidateFilter;
import Vout.Database.CitizenFilter;
import Vout.Database.Filter;
import Vout.Entity.Admin;
import Vout.Entity.Citizen;
import Vout.Service.Exceptions.UserNotAuthenticatedException;
import Vout.State;
import Vout.User;

/**
 * Service pre prihlasovanie
 * Prebieha tu autentifikacia pouzivatela, ktory sa chce prihlasit. Zavola sa metoda filtra, ktory prejde databazu
 * az kym nenajde zhodu v PIN-e. Nasledne vrati objekt typu bud Obcan alebo Kandidat.
 * Ak nenajde zhodi, vyhodi vynimku - zle prihlasovacie udaje.
 */
public class LoginService extends Service {

    public LoginService(State state) {
        super(state);
    }

    public User auth(String PIN) throws UserNotAuthenticatedException {

        User findUser = null;

        if (PIN.equals("admin")) {
            findUser = new Admin();
        }
        if (findUser == null) {
            Filter citizenFilter = new CitizenFilter();
            citizenFilter.addAttribute("PIN", PIN, "==");
            findUser = (Citizen) this.state.getDatabase().findFirst(citizenFilter);
        }
        if (findUser == null) {
            Filter candidateFilter = new CandidateFilter();
            candidateFilter.addAttribute("PIN", PIN, "==");
            findUser = (Citizen) this.state.getDatabase().findFirst(candidateFilter);
        }
        if (findUser == null) {
            throw new UserNotAuthenticatedException();
        }
        return findUser;
    }
}
