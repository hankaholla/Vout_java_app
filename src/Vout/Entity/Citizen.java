package Vout.Entity;

import Vout.State;
import Vout.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Trieda obcan je hlavna trieda v hierarchii, je od nej oddedena trieda Kandidat, kedze kazdy kandidat je aj obcan,
 * a ma moznost hlasovat vo volbach.
 * Obcan ma svoje atributy, ktore dostava pri svojom vytvoreni - pridani do databazy, pomocou metody, ktoru vykonava
 * administrator (trieda Admin).
 * Okrem setterov a getterov ma obcan este dve metody, login, ktoru implementuje z interface User, a assignVote,
 * ktora uoznuje jeho hlasovanie vo volbach. Tato metoda je aj pretazena.
 */

public class Citizen implements Serializable, User {

    /**
     *
     */
    private static final long serialVersionUID = -4296174115240420084L;
    public State state;
    protected String firstName;
    protected String lastName;
    protected String birth;
    protected String PIN;           // cislo OP - udaj na prihlasenie
    protected String city;          //mesto
    protected String district;      // okres
    protected String region;        //kraj
    protected boolean canVote;
    protected String nationality;
    protected int age;

    public Citizen(String firstName, String lastName, String birth, String PIN,
                   String city, String district, String region, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PIN = PIN;
        this.birth = birth;
        this.PIN = PIN;
        this.city = city;
        this.district = district;
        this.region = region;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getPIN();
    }

    /**
     * Prihlasenie - implementovanie metody z interface User
     *
     * @param citizen - prihlaseny obcan
     * @param state   - aktualny stav programu, ktory drzi jeho hlavne atributy (election, user, database, partyDatabase)
     */
    public void login(User citizen, State state) {
        state.setLoggedUser(citizen);
        System.out.println("Prihlaseny obcan");
    }

    /**
     * Hlasovanie obcana - ked je limit zvolenych kandidatov 1
     *
     * @param state
     * @param index         - pozicia kandidata, ktoremu sa pridava hlas
     * @param candidateList - list kandidatov generovany pre prihlaseneho obcana, podla jeho volby
     */
    public void assignVote(State state, int index, List<Candidate> candidateList) {

        for (Candidate c : state.getElection().getCandidates()) {
            if (c.getPIN().equals(candidateList.get(index).PIN)) {
                c.setNumberOfVotes(c.getNumberOfVotes() + 1);
                break;
            }
        }
        System.out.println(state.getElection().getCandidates().get(index).getNumberOfVotes());

    }

    /**
     * Pretazena metoda hlasovanie obcana, ked je limit 3
     *
     * @param state
     * @param index
     * @param index2
     * @param index3
     * @param candidateList
     */
    public void assignVote(State state, int index, int index2, int index3, List<Candidate> candidateList) {

        for (Candidate c : state.getElection().getCandidates()) {
            if (c.getPIN().equals(candidateList.get(index).getPIN())) {
                c.setNumberOfVotes(c.getNumberOfVotes() + 1);
            }
            if (c.getPIN().equals(candidateList.get(index2).getPIN())) {
                c.setNumberOfVotes(c.getNumberOfVotes() + 1);
            }
            if (c.getPIN().equals(candidateList.get(index3).getPIN())) {
                c.setNumberOfVotes(c.getNumberOfVotes() + 1);
            }
        }
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getPIN() {
        return this.PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int birth) {
        int month = ((birth / 10000) % 100);
        int year = (birth % 10000);
        int day = (birth / 1000000);

        LocalDate today = LocalDate.now();                          //dnesny datum
        LocalDate birthday = LocalDate.of(year, month, day);  //datum narodenia
        Period p = Period.between(birthday, today);
        age = p.getYears();
        System.out.println(age);
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}