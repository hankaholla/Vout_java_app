package Vout.Database;

import Vout.Entity.Party;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PartyDatabase extends Database implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7067586127040078641L;
    public List<Party> parties = new ArrayList<>();       //databaza
    private String path = "/data/";
    private String databaseFile = "partyData.db";


    private String getDatabaseFile() {
        return System.getProperty("user.dir") + this.path + this.databaseFile;
    }

    public void add(Party item) {
        this.parties.add(item);
    }

    public void remove(Party item) {
        this.parties.remove(item);
    }

    public void save() {
        try {
            FileOutputStream fileP = new FileOutputStream(this.getDatabaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileP);

            out.writeObject(parties);

            out.close();
            fileP.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught, PARTY");
        }
    }

    public void load() {
        try {
            FileInputStream fileP = new FileInputStream(this.getDatabaseFile());
            ObjectInputStream in = new ObjectInputStream(fileP);
            parties = (ArrayList<Party>) in.readObject();

            in.close();
            fileP.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }
}
