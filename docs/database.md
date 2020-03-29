```java
import Vout.*;

Citizen hanka1 = new Citizen("Hanka", "Hollá");
Citizen lala = new Citizen("Lala", "Tralala");
lala.setAge(20);

Citizen janko = new Citizen("Janko", "Hraško");
janko.setAge(15);

Vout.Database db = new Vout.Database();
db.add(hanka1);
db.add(lala);
db.add(janko);
db.save();

Vout.Database db1 = new Vout.Database();
db1.load();
Citizen hanka = (Citizen) db1.data.get(0);
System.out.println(hanka.getName());

Filter citizenFilter = new CitizenFilter();
citizenFilter.addAttribute("name", "Hanka Hollá", "==");
Citizen found = (Citizen) db1.findFirst(citizenFilter);
System.out.println("Nasiel som: " + found.getName());

Filter citizenFilterForAge = new CitizenFilter();
citizenFilterForAge.addAttribute("age", 15, "<=");
List<Object> resultsForAge = db1.find(citizenFilterForAge);

for (Object citizen: resultsForAge) {
    Citizen foundCitizen = (Citizen) citizen;
    System.out.println("For age: " + foundCitizen.getName());
}
```