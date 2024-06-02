package Commands;

import DataBase.DataBaseManager;
import Managers.PasswordManager;
import Managers.UserStatusManager;
import java.io.PrintStream;
import java.util.Scanner;

public class LoginCommand extends GenericCommand {
    DataBaseManager db;

    private final UserStatusManager userStatusManager;

    public LoginCommand(UserStatusManager userStatusManager, PrintStream printStream, DataBaseManager db) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter name to login: ");
            if (scanner.hasNextLine()) {
                String user_name = scanner.nextLine();
                if (this.db.checkUser(user_name)) {
                    String str;
                    do {
                        System.out.print("Enter password: ");
                        String pswd1 = PasswordManager.enterPassword();
                        if (this.db.checkPassword(user_name, pswd1)) {
                            this.userStatusManager.setStatus(true);
                            this.userStatusManager.setUser_name(user_name);
                            System.out.println("You have successfully logged in to your account");
                            break;
                        }
                        System.out.println("Enter 'Y' if u want to try again");
                        str = scanner.nextLine();
                    } while (str.equals("Y") || str.equals("y"));
                    break;
                }
                System.out.println("Error: Cant find such user");
                System.out.println("Enter 'Y' if u want to try again, u can try to register!!!");
                String inputCheck = scanner.nextLine();
                if (!inputCheck.equals("Y") && !inputCheck.equals("y"))
                    break;
            }
        }
    }

    public String Description() {
        return "login user.";
    }

    public boolean VerifyInputParameters(String[] tokens) {
        return (tokens.length == 1);
    }
}
