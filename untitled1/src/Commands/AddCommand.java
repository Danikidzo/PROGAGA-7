package Commands;

import DataBase.DataBase;
import Factory.HumanBeingFactory;
import Managers.UserStatusManager;
import Objects.HumanBeing;

import java.io.PrintStream;

public class AddCommand extends GenericCommand {
    DataBase db;

    HumanBeingFactory humanBeingFactory;

    UserStatusManager userStatusManager;

    public AddCommand(PrintStream printStream, DataBase db, HumanBeingFactory humanBeingFactory, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.humanBeingFactory = humanBeingFactory;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            HumanBeing human = this.humanBeingFactory.Produce("Make a human:\n");
            human.setUser_name(this.userStatusManager.getUser_name());
            this.db.Add(human);
            System.out.println("Human successfully added");
        }
    }

    public String Description() {
        return "add a new human into the database.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}