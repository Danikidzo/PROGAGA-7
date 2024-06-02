package Commands;

import java.io.PrintStream;

public class ExitCommand extends GenericCommand {
    volatile Boolean exitCondition = Boolean.FALSE;

    public Boolean getExitCondition() {
        return this.exitCondition;
    }

    public ExitCommand(PrintStream printStream) {
        super(printStream);
    }

    public void Execute() throws Exception {
        this.exitCondition = Boolean.TRUE;
    }

    public String Description() {
        return "close the application.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
