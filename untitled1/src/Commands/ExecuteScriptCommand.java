package Commands;

import Managers.CommandManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScriptCommand extends GenericCommand {
    private final CommandManager commandManager;

    private static final Set<String> scriptPaths = new HashSet<>();

    public ExecuteScriptCommand(PrintStream printStream, CommandManager commandManager) {
        super(printStream);
        this.commandManager = commandManager;
    }

    public void Execute() throws Exception {
        String scriptPath = this.tokens[1];
        File scriptFile = new File(scriptPath);
        if (scriptPaths.contains(scriptPath)) {
            this.printStream.println("Error: Recursion detected. Script won't be executed again to prevent infinite recursion.");
            return;
        }
        try {
            Scanner scanner = new Scanner(scriptFile);
            try {
                scriptPaths.add(scriptPath);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    String[] tokens = line.split("\\s+");
                    if (tokens.length == 0 || tokens[0].isEmpty())
                        continue;
                    GenericCommand command = this.commandManager.getCommand(tokens[0]);
                    if (command != null) {
                        command.setTokens(tokens);
                        if (command.VerifyInputParameters(tokens)) {
                            command.Execute();
                            continue;
                        }
                        this.printStream.println("Error in script file at command: " + line);
                        continue;
                    }
                    this.printStream.println("Unknown command in script: " + tokens[0]);
                }
                scanner.close();
            } catch (Throwable throwable) {
                try {
                    scanner.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (FileNotFoundException e) {
            this.printStream.println("Script file not found: " + scriptPath);
        } finally {
            scriptPaths.remove(scriptPath);
        }
    }

    public boolean VerifyInputParameters(String[] tokens) {
        if (tokens.length < 2) {
            this.printStream.println("Error: wrong command arguments!");
            return false;
        }
        return true;
    }

    public String Description() {
        return "(file_name), execute commands from an entered file.";
    }
}
