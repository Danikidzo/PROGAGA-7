package Commands;

import DataBase.DataBase;
import Factory.HumanBeingFactory;
import Managers.UserStatusManager;

import java.io.PrintStream;

public class AverageOfImpactSpeed extends GenericCommand{
    DataBase db;
    UserStatusManager userStatusManager;

    public AverageOfImpactSpeed(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    @Override
    public void Execute() throws Exception {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            System.out.println("The average impact speed of humans: " + db.averageImpactSpeed());
        }


    }

    @Override
    public String Description() {
        return "show the average impact speed of all humans.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
