package Commands;

import DataBase.DataBase;
import Managers.UserStatusManager;
import java.io.PrintStream;

public class CountLessThanImpactSpeed extends GenericCommand {
    private final DataBase db;

    private float impactSpeed;

    private final UserStatusManager userStatusManager;

    public CountLessThanImpactSpeed(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            long count = this.db.countLessThanImpactSpeed(this.impactSpeed);
            if (this.printStream != null)
                this.printStream.println("Number of humans with impact speed less than " + this.impactSpeed + ": " + count);
        }
    }

    public String Description() {
        return "(impact_speed), display the number of elements with impact speed less than the entered one.";
    }

    public boolean VerifyInputParameters(String[] tokens) {
        if (tokens.length != 2)
            return false;
        try {
            this.impactSpeed = Float.parseFloat(tokens[1]);
            return true;
        } catch (NumberFormatException e) {
            if (this.printStream != null)
                this.printStream.println("Invalid impact speed value: " + tokens[1]);
            return false;
        }
    }
}
