package Commands;

import DataBase.DataBase;
import Managers.UserStatusManager;
import Objects.WeaponType;

import java.io.PrintStream;

public class FilterByWeaponType extends GenericCommand {
    DataBase db;
    String inpEnum;
    UserStatusManager userStatusManager;

    public FilterByWeaponType(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }


    public void Execute() {
        if (!this.userStatusManager.getStatus()) {
            System.out.println("You are not authorized, try to login or register");
        } else {
            inpEnum = String.valueOf(WeaponType.valueOf(String.valueOf(inpEnum).toUpperCase()));
            this.db.filterByWeaponType(WeaponType.valueOf(inpEnum)).forEach(humanBeing -> this.printStream.println(humanBeing));
        }
    }


    @Override
    public String Description() {
        return "(weapon_type), show all humans with chosen weapon type.";
    }

    public boolean VerifyInputParameters(String[] tokens) {
        if (tokens.length != 2) {
            if (this.printStream != null)
                this.printStream.println("Usage: filter_by_weapon_type <inpEnum>");
            return false;
        }
        this.inpEnum = tokens[1];
        return true;
    }
}
