package Factory;

import Objects.*;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanBeingFactory extends GenericFactory {
    private final StringFactory stringFactory;

    private final CoordinatesFactory coordinatesFactory;

    private final BooleanFactory booleanFactory;

    private final MoodFactory moodFactory;

    private final WeaponTypeFactory weaponTypeFactory;

    private final FloatFactory floatFactory;

    public HumanBeingFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
        this.moodFactory = new MoodFactory(ps, scanner, stopIfError);
        this.weaponTypeFactory = new WeaponTypeFactory(ps, scanner, stopIfError);
        this.stringFactory = new StringFactory(ps, scanner, stopIfError);
        this.coordinatesFactory = new CoordinatesFactory(ps, scanner, stopIfError);
        this.booleanFactory = new BooleanFactory(ps, scanner, stopIfError);
        this.floatFactory = new FloatFactory(ps, scanner, stopIfError);
    }

    public HumanBeing Produce(String invitation) throws Exception {
        Say(invitation);
        String name = this.stringFactory.Produce("Name: ", false);
        Coordinates coordinates = this.coordinatesFactory.Produce("Coordinates:\n", false);
        Boolean hasToothpick = this.booleanFactory.Produce("Does the human have a toothpick? Enter true/false: ", true);
        boolean realHero = this.booleanFactory.Produce("Is the human a real hero? Enter true/false: ", false);
        float impactSpeed = this.floatFactory.Produce("Human's impact speed: ", false);
        WeaponType weaponType = this.weaponTypeFactory.Produce("Weapon Type: ", true);
        Mood mood = this.moodFactory.Produce("Mood: ", true);
        boolean cool = this.booleanFactory.Produce("Is human's car cool? Enter true/false: ", false);
        return new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, cool);
    }
}