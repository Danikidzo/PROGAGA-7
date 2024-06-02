package Commands;

import DataBase.DataBase;
import Factory.HumanBeingFactory;
import Managers.UserStatusManager;
import Objects.HumanBeing;
import java.io.PrintStream;

public class UpdateCommand extends GenericCommand {
    private Long id;
    private final DataBase db;
    private final HumanBeingFactory humanBeingFactory;
    private final UserStatusManager userStatusManager;

    public UpdateCommand(PrintStream printStream, DataBase db, HumanBeingFactory humanBeingFactory, UserStatusManager userStatusManager) {
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
            this.db.Update(this.id, human, this.userStatusManager.getUser_name());
            System.out.println("Human successfully updated");
        }
    }

    public String Description() {return "(id), update a human with entered id";}

    public boolean VerifyInputParameters(String[] tokens) {
        if (tokens.length != 2)
            return false;
        try {
            this.id = Long.parseLong(tokens[1]);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

