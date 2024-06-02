package Factory;

import java.io.PrintStream;
import java.util.Scanner;

public class BooleanFactory extends FactoryOfValue<Boolean> {
    public BooleanFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
    }

    public void Parse(String input) throws Exception {
        input = input.trim().toLowerCase();
        if (input.equals("true")) {
            this.value = Boolean.TRUE;
        } else if (input.equals("false")) {
            this.value = Boolean.FALSE;
        } else {
            throw new Exception("Input is not a valid boolean value");
        }
    }

    public String ValueType() {
        return "boolean";
    }

    public String toString() {
        return "BooleanFactory";
    }

    public Boolean Produce(String invitation, boolean mayBeEmpty) throws Exception {
        while (true) {
            Say(invitation);
            String input = this.scanner.hasNextLine() ? this.scanner.nextLine() : "";
            if (input.isBlank()) {
                if (mayBeEmpty)
                    return null;
                if (this.stopIfError)
                    throw new Exception("" + this + ": The entered value can't be empty!");
                SayError("The entered value can't be empty! Try again...");
                continue;
            }
            try {
                Parse(input);
                return this.value;
            } catch (Exception e) {
                if (this.stopIfError)
                    throw new Exception("" + this + ": " + this);
                SayError(e.getMessage() + " Try again...");
            }
        }
    }
}
