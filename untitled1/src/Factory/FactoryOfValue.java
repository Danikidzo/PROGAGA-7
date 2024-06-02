package Factory;

import java.io.PrintStream;
import java.util.Scanner;

public abstract class FactoryOfValue<T> extends GenericFactory {
    T value;

    public abstract void Parse(String paramString) throws Exception;

    public abstract String ValueType();

    public FactoryOfValue(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
    }

    public T Produce(String invitation, boolean mayBeEmpty) throws Exception {
        while (true) {
            Say(invitation);
            String input = "";
            if (this.scanner.hasNextLine())
                input = this.scanner.nextLine();
            if (input.isBlank()) {
                if (mayBeEmpty)
                    return null;
                if (this.stopIfError)
                    throw new Exception("" + this + ": The entered can't be an empty!");
                SayError("The entered can't be an empty! Try again...");
                continue;
            }
            try {
                Parse(input);
                return this.value;
            } catch (NumberFormatException e) {
                if (this.stopIfError)
                    throw new Exception("" + this + this);
                SayError(String.format("The entered is not a %s! Try again...", new Object[] { ValueType() }));
            }
        }
    }
}
