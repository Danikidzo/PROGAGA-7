package Commands;

import DataBase.CommandLogger;
import java.io.PrintStream;

public class HistoryCommand extends GenericCommand {
    CommandLogger commandLogger;

    public HistoryCommand(PrintStream printStream, CommandLogger commandLogger) {
        super(printStream);
        this.commandLogger = commandLogger;
    }

    public void Execute() throws Exception {
        if (this.printStream != null)
            this.printStream.println(this.commandLogger);
    }

    public String Description() {
        return "show last 6 commands.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
