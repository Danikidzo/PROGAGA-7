package DataBase;

import Objects.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DataBase {
    private TreeSet<HumanBeing> humans;

    private final DataBaseManager dataBaseManager;

    public DataBase(DataBaseManager dataBaseManager) throws Exception {
        this.dataBaseManager = dataBaseManager;
        this.humans = new TreeSet<>();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            this.humans = this.dataBaseManager.readFromDataBase();
            updateIds();
        } catch (Exception e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public void save(){
        this.dataBaseManager.saveToDB(this.humans);
    }

    private void updateIds() {
        long id = 1L;
        IdManager.ListID.clear();
        for (HumanBeing human : this.humans) {
            human.setId(id);
            IdManager.AddId(id);
            id++;
        }
    }

    public void Add(HumanBeing human) {
        human.setId(IdManager.GetNewId());
        IdManager.AddId(human.getId());
        this.humans.add(human);
    }

    public void Update(long id, HumanBeing updatedHuman, String username) {
        Iterator<HumanBeing> iterator = this.humans.iterator();
        while (iterator.hasNext()) {
            HumanBeing human = iterator.next();
            if (human.getId() == id) {
                if (human.getUser_name().equals(username)) {
                    iterator.remove();
                    updatedHuman.setId(id);
                    this.humans.add(updatedHuman);
                    return;
                }
                System.out.println("You don't have access to this ID");
            }
        }
        System.out.println("Human with id " + id + " not found.");
    }

    public void RemoveById(long id, String username) {
        Iterator<HumanBeing> iterator = this.humans.iterator();
        boolean removeCheck = false;
        while (iterator.hasNext()) {
            HumanBeing human = iterator.next();
            if (human.getId() == id) {
                if (human.getUser_name().equals(username)) {
                    IdManager.RemoveId(Long.valueOf(human.getId()));
                    iterator.remove();
                    updateIds();
                } else {
                    System.out.println("You don't have access to this ID");
                }
                removeCheck = true;
                break;
            }
        }
        if (!removeCheck)
            System.out.println("No humans with ID = " + id);
    }



    public void Clear(String userName) {
        Iterator<HumanBeing> iterator = this.humans.iterator();
        while (iterator.hasNext()) {
            HumanBeing human = iterator.next();
            if (human.getUser_name().equals(userName)) {
                IdManager.RemoveId(human.getId());
                iterator.remove();
            }
        }
        updateIds();
    }

    public String Info() {
        return String.format("Collection type: %s\nNumber of elements: %d", this.humans
                .getClass().getName(), this.humans.size());
    }

    public ArrayList<String> getUsers() {
        return this.dataBaseManager.getUsers();
    }

    public String toString() {
        StringBuilder show = new StringBuilder(String.format("There are %d Humans in the collection.\n", this.humans.size()));
        this.humans.forEach(dragon -> show.append(dragon).append("\n------------\n"));
        return show.toString();
    }

    public long countLessThanImpactSpeed(float impactSpeed) {
        return this.humans.stream().filter(HumanBeing -> (HumanBeing.getImpactSpeed() < impactSpeed)).count();
    }

    public float averageImpactSpeed(){
        float x = 0;
        for (HumanBeing human : humans) {
            x += human.getImpactSpeed();
        }if(humans.isEmpty()) {
            return 0;
        }
        return x/humans.size();
    }

    public List<HumanBeing> filterContainsName(String substring) {
        return (List<HumanBeing>)this.humans.stream()
                .filter(HumanBeing -> HumanBeing.getName().contains(substring))
                .collect(Collectors.toList());
    }

    public List<HumanBeing> filterByWeaponType(WeaponType inpEnum) {
        return (List<HumanBeing>)this.humans.stream()
                .filter(HumanBeing -> HumanBeing.getWeaponType()==inpEnum)
                .collect(Collectors.toList());
    }

    public void AddIfMin(HumanBeing human) throws IllegalArgumentException {
        if (this.humans.isEmpty()) {
            human.setId(IdManager.GetNewId());
            IdManager.AddId(human.getId());
            this.humans.add(human);
            return;
        }
        HumanBeing minHuman = Collections.max(this.humans);
        if (human.compareTo(minHuman) > 0) {
            human.setId(IdManager.GetNewId());
            IdManager.AddId(human.getId());
            this.humans.add(human);
        } else {
            throw new IllegalArgumentException("Error: This human is not minimum!");
        }
    }

    public void AddIfMax(HumanBeing human) throws IllegalArgumentException {
        if (this.humans.isEmpty()) {
            human.setId(IdManager.GetNewId());
            IdManager.AddId(human.getId());
            this.humans.add(human);
            return;
        }
        HumanBeing maxHuman = Collections.max(this.humans);
        if (human.compareTo(maxHuman) < 0) {
            human.setId(IdManager.GetNewId());
            IdManager.AddId(human.getId());
            this.humans.add(human);
        } else {
            throw new IllegalArgumentException("Error: This human is not maximum!");
        }
    }

    public static byte[] serializeTreeSet(TreeSet<HumanBeing> treeSet) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(treeSet);
            return baos.toByteArray();
        }
    }
}
