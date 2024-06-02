package Factory;

import Objects.Coordinates;
import java.io.PrintStream;
import java.util.Scanner;

public class CoordinatesFactory extends GenericFactory {
    private final LongFactory longFactory;
    private final FloatFactory floatFactory;

    public CoordinatesFactory(PrintStream ps, Scanner scanner, boolean stopIfError) {
        super(ps, scanner, stopIfError);
        this.longFactory = new LongFactory(ps, scanner, stopIfError);
        this.floatFactory = new FloatFactory(ps, scanner, stopIfError);
    }

    public Coordinates Produce(String invitation, boolean mayBeEmpty) {
        Say(invitation);
        Long x = null;
        while (x == null) {
            try {
                x = this.longFactory.Produce("x: ", mayBeEmpty);
            } catch (Exception e) {
                SayError(e.getMessage() + " Try again...");
            }
        }
        Float y = null;
        while (y == null) {
            try {
                y = this.floatFactory.Produce("y: ", false);
            } catch (Exception e) {
                SayError(e.getMessage() + " Try again...");
            }
        }
        return new Coordinates(x.intValue(), y);
    }
}
