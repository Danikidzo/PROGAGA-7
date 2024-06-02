package Factory;

import java.io.PrintStream;
import java.util.Scanner;

public class FloatFactory extends FactoryOfValue<Float> {
    public FloatFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
    }

    public void Parse(String input) {
        this.value = Float.parseFloat(input);
    }

    public String toString() {
        return "FloatFactory";
    }

    public String ValueType() {
        return "Float";
    }
}
