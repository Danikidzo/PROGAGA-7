package Managers;

import Commands.*;
import DataBase.*;
import Factory.HumanBeingFactory;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {
    private final DataBase db;

    private final DataBaseManager dataBaseManager;

    private final PrintStream printStream;

    private final Map<String, GenericCommand> commands = new HashMap<>();

    private final Scanner scanner;

    private final ExitCommand exitCommand;

    private final HumanBeingFactory humanBeingFactory;

    private final CommandLogger commandLogger;

    private final UserStatusManager userStatusManager;

    public CommandManager(UserStatusManager userStatusManager, DataBaseManager dataBaseManager, DataBase db, Scanner scanner, boolean stopIfError) {
        this.userStatusManager = userStatusManager;
        this.db = db;
        this.scanner = scanner;
        this.printStream = System.out;
        this.humanBeingFactory = new HumanBeingFactory(this.printStream, scanner, stopIfError);
        this.commandLogger = new CommandLogger();
        this.exitCommand = new ExitCommand(this.printStream);
        this.dataBaseManager = dataBaseManager;
        initializeCommands();
    }

    private void initializeCommands() {
        this.commands.put("help", new HelpCommand(this.printStream, this));
        this.commands.put("login", new LoginCommand(this.userStatusManager, this.printStream, this.dataBaseManager));
        this.commands.put("register", new RegisterCommand(this.userStatusManager, this.printStream, this.dataBaseManager));
        this.commands.put("logout", new LogoutCommand(this.userStatusManager, this.printStream));
        this.commands.put("users", new Users(this.printStream, this.db));
        this.commands.put("add", new AddCommand(this.printStream, this.db, this.humanBeingFactory, this.userStatusManager));
        this.commands.put("add_if_min", new AddIfMinCommand(this.printStream, this.db, this.humanBeingFactory, this.userStatusManager));
        this.commands.put("add_if_max", new AddIfMaxCommand(this.printStream, this.db, this.humanBeingFactory, this.userStatusManager));
        this.commands.put("clear", new ClearCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("history", new HistoryCommand(this.printStream, this.commandLogger));
        this.commands.put("show", new ShowCommand(this.printStream, this.db));
        this.commands.put("info", new InfoCommand(this.printStream, this.db));
        this.commands.put("remove_by_id", new RemoveByIdCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("update", new UpdateCommand(this.printStream, this.db, this.humanBeingFactory, this.userStatusManager));
        this.commands.put("average_of_impact_speed", new AverageOfImpactSpeed(this.printStream, this.db, this.userStatusManager));
        this.commands.put("count_less_than_impact_speed", new CountLessThanImpactSpeed(this.printStream, this.db, this.userStatusManager));
        this.commands.put("filter_contains_name", new FilterContainsNameCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("filter_by_weapon_type", new FilterByWeaponType(this.printStream, this.db, this.userStatusManager));
        this.commands.put("execute_script", new ExecuteScriptCommand(this.printStream, this));
        this.commands.put("save", new SaveCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("exit", this.exitCommand);
    }

    public GenericCommand getCommand(String commandName) {
        return this.commands.get(commandName);
    }

    public void Run() {
        System.out.println("Login or register to access full functionality! Enter 'help' to see all of the commands!");
        while (!this.exitCommand.getExitCondition()) {
            this.printStream.print(">>> ");
            if (this.scanner.hasNextLine()) {
                String commandLine = this.scanner.nextLine();
                processCommand(commandLine);
            }
        }
    }

    public void processCommand(String commandLine) {
        if (commandLine.isEmpty())
            return;
        String[] tokens = commandLine.trim().split("\\s+");
        if (tokens.length == 0)
            return;
        String commandName = tokens[0];
        GenericCommand command = getCommand(commandName);
        if (command != null) {
            try {
                command.setTokens(tokens);
                if (command.VerifyInputParameters(tokens)) {
                    command.Execute();
                    this.commandLogger.Add(commandName);
                }
            } catch (Exception e) {
                this.printStream.println("Error executing command: " + e.getMessage());
            }
        } else {
            this.printStream.println("Error: Unknown command!");
        }
    }

    public String Help() {
        StringBuilder help = new StringBuilder("Available commands:\n");
        for (Map.Entry<String, GenericCommand> entry : this.commands.entrySet())
            help.append(entry.getKey()).append(": ").append(entry.getValue().Description()).append("\n");
        return help.toString();
    }
}
