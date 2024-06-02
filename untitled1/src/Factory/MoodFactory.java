package Factory;

import Objects.Mood;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class MoodFactory extends FactoryOfEnum<Mood> {
    public MoodFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
    }

    public String Values() {
        return Arrays.toString(Mood.values());
    }

    public Mood ValueOf(String input) {
        try {
            return Mood.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString() {
        return "MoodFactory";
    }
}
