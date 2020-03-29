package Vout.Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hlavna databaza
 * - trieda obsahuje metody serializacie a deserializacie (implementuje rozhranie Serializable) pre databazu obcanov a kandidatov. Taktiez obsahuje dve metody
 * pre filtrovanie databazy podla roznych kriterii (napriklad overovanie PIN pri prihlaseni, hladanie kandidatov
 * s konkretnym atributom, atd)
 */
public class Database implements Serializable {
    private static final long serialVersionUID = 119471405390212511L;
    public List<Object> data = new ArrayList<>();
    private String path = "/data/";
    private String databaseFile = "vout.db";


    public List<Object> find(Filter filter) {
        List<Object> results = new ArrayList<>();

        for (Object item : this.data) {
            String className = filter.getClass().getSimpleName().toLowerCase();
            if (className.startsWith(item.getClass().getSimpleName().toLowerCase()) && filter.compare(item)) {
                results.add(item);
            }
        }
        return results;
    }

    public Object findFirst(Filter filter) {
        for (Object item : this.data) {
            String className = filter.getClass().getSimpleName().toLowerCase();
            if (className.startsWith(item.getClass().getSimpleName().toLowerCase()) && filter.compare(item)) {
                return item;
            }
        }
        return null;
    }

    public void add(Object item) {
        this.data.add(item);
    }

    public void remove(Object item) {
        this.data.remove(item);
    }

    public void save() {
        try {
            FileOutputStream file = new FileOutputStream(this.getDatabase());
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(this.data);

            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private String getDatabase() {

        return System.getProperty("user.dir") + this.path + this.databaseFile;
    }

    public void load() {
        try {
            FileInputStream file = new FileInputStream(this.getDatabase());
            ObjectInputStream in = new ObjectInputStream(file);
            this.data = (List<Object>) in.readObject();
            for (Object i : data)
                System.out.println(i);
            in.close();
            file.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

}