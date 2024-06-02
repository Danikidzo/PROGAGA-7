package Factory;

import java.io.PrintStream;
import java.util.Scanner;

public abstract class FactoryOfEnum<T> extends GenericFactory {
    private final StringFactory stringFactory;

    public abstract String Values();

    public abstract T ValueOf(String paramString);

    public FactoryOfEnum(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
        this.stringFactory = new StringFactory(ps, scanner, stopIfError);
    }

    public T Produce(String invitation, boolean mayBeEmpty) throws Exception {
        T value;
        Say(invitation);
        String listOfEnums = Values();
        do {
            String input = this.stringFactory.Produce(listOfEnums + ": ", mayBeEmpty);
            if (input == null || input.isBlank())
                return null;
            value = ValueOf(input.toUpperCase());
            if (value != null)
                continue;
            if (this.stopIfError)
                throw new Exception("" + this + ": The entered value is not correct!");
            SayError("The entered value is not correct! Try again...");
        } while (value == null);
        return value;
    }
}
