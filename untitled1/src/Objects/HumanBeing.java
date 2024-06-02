package Objects;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public final class HumanBeing implements Comparable<HumanBeing>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле может быть null
    private float impactSpeed;
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле может быть null
    private Car carCool; //Поле не может быть null
    private String user_name;

    public String getName() {return name;}

    public HumanBeing(String name, Coordinates coordinates, boolean realHero, Boolean hasToothpick, float impactSpeed, WeaponType weaponType, Mood mood, boolean cool) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date(System.currentTimeMillis());
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.carCool = new Car(cool);
    }

    public HumanBeing() {}

    @Override
    public int compareTo(HumanBeing o) {
        if (this.getImpactSpeed() - o.getImpactSpeed() != 0) {
            if(o.getImpactSpeed()-this.getImpactSpeed()>0){
                return 1;
            }
            return -1;
        } else if (o.getName().length() - this.getName().length() != 0) {
            return this.getName().length()-o.getName().length();
        }else if(!(o.getName().equals(this.getName()))){
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanBeing that = (HumanBeing) o;
        return realHero == that.realHero && Float.compare(impactSpeed, that.impactSpeed) == 0 && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(hasToothpick, that.hasToothpick) && weaponType == that.weaponType && mood == that.mood && carCool.isCool()== that.carCool.isCool() && Objects.equals(user_name, that.user_name);
    }

    @Override
    public int hashCode() {return Objects.hash(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, carCool, user_name);}

    @Override
    public String toString() {
        return "HumanBeing" +
                "\nid=" + id +
                "\nname=" + name +
                "\ncoordinates=" + coordinates +
                "\ncreationDate=" + creationDate +
                "\nrealHero=" + realHero +
                "\nhasToothpick=" + hasToothpick +
                "\nimpactSpeed=" + impactSpeed +
                "\nweaponType=" + weaponType +
                "\nmood=" + mood +
                "\nisCarCool=" + carCool.isCool() +
                "\nusername=" + user_name;
    }

    public long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}
    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}
    public void setRealHero(boolean realHero) {this.realHero = realHero;}
    public void setHasToothpick(Boolean hasToothpick) {this.hasToothpick = hasToothpick;}
    public void setImpactSpeed(float impactSpeed) {this.impactSpeed = impactSpeed;}
    public void setWeaponType(WeaponType weaponType) {this.weaponType = weaponType;}
    public void setMood(Mood mood) {this.mood = mood;}
    public void setCar(Car car) {this.carCool = car;}
    public Coordinates getCoordinates() {return coordinates;}
    public Date getCreationDate() {return creationDate;}
    public boolean isRealHero() {return realHero;}
    public Boolean getHasToothpick() {return hasToothpick;}
    public float getImpactSpeed() {return impactSpeed;}
    public WeaponType getWeaponType() {return weaponType;}
    public Mood getMood() {return mood;}
    public Car getCar() {return carCool;}
    public String getUser_name(){return user_name;}
    public void setUser_name(String user_name) {this.user_name = user_name;}
}
