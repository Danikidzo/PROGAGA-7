package Commands;

import DataBase.DataBase;
import java.io.PrintStream;

public class Users extends GenericCommand {
    DataBase db;

    public Users(PrintStream printStream, DataBase db) {
        super(printStream);
        this.db = db;
    }

    public void Execute() throws Exception {
        if (this.printStream != null) {
            System.out.println("USERS: ");
            for (String users : this.db.getUsers())
                System.out.println(users);
        }
    }

    public String Description() {
        return "show users.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}

