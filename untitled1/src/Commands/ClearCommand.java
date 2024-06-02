package Commands;

import DataBase.DataBase;
import Managers.UserStatusManager;
import java.io.PrintStream;

public class ClearCommand extends GenericCommand {
    private final DataBase db;

    private final UserStatusManager userStatusManager;

    public ClearCommand(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            this.db.Clear(this.userStatusManager.getUser_name());
            System.out.println("Successfully cleared");
        }
    }

    public String Description() {
        return "eliminate all entities in the database.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
