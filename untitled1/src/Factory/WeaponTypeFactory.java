package Factory;

import Objects.WeaponType;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class WeaponTypeFactory extends FactoryOfEnum<WeaponType> {
    public WeaponTypeFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
    }

    public String Values() {
        return Arrays.toString(WeaponType.values());
    }

    public WeaponType ValueOf(String input) {
        try {
            return WeaponType.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString() {
        return "WeaponTypeFactory";
    }
}
