package Commands;

import DataBase.DataBase;
import Factory.HumanBeingFactory;
import Managers.UserStatusManager;
import Objects.HumanBeing;

import java.io.PrintStream;

public class AddIfMaxCommand extends GenericCommand {
    DataBase db;

    HumanBeingFactory humanBeingFactory;

    UserStatusManager userStatusManager;

    public AddIfMaxCommand(PrintStream printStream, DataBase db, HumanBeingFactory  humanBeingFactory, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.humanBeingFactory = humanBeingFactory;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            try {
                HumanBeing human = this.humanBeingFactory.Produce("Make a human:\n");
                human.setUser_name(this.userStatusManager.getUser_name());
                this.db.AddIfMax(human);
                System.out.println("Human successfully added");
            } catch (IllegalArgumentException e) {
                if (this.printStream != null)
                    this.printStream.println(e.getMessage());
            } catch (Exception e) {
                if (this.printStream != null)
                    this.printStream.println("An error occurred while adding the human: " + e.getMessage());
            }
        }
    }

    public String Description() {
        return "add a human in case it is maximum.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
