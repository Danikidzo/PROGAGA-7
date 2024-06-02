package Commands;

import DataBase.DataBase;
import Managers.UserStatusManager;
import java.io.PrintStream;

public class RemoveByIdCommand extends GenericCommand {
    private final DataBase db;
    private final UserStatusManager userStatusManager;
    private Long id;

    public RemoveByIdCommand(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            this.db.RemoveById(this.id, this.userStatusManager.getUser_name());
        }
    }

    public String Description() {
        return "(id), remove the element with entered id.";
    }

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
