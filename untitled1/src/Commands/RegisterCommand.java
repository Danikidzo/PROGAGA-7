package Commands;

import DataBase.DataBaseManager;
import Managers.PasswordManager;
import Managers.UserStatusManager;
import java.io.PrintStream;
import java.util.Scanner;

public class RegisterCommand extends GenericCommand {
    private final DataBaseManager db;

    public UserStatusManager userStatusManager;

    public RegisterCommand(UserStatusManager userStatusManager, PrintStream printStream, DataBaseManager db) {
        super(printStream);
        this.userStatusManager = userStatusManager;
        this.db = db;
    }

    public void Execute() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.printStream.print("Enter name: ");
            if (scanner.hasNextLine()) {
                String inputCheck, user_name = scanner.nextLine();
                if (this.db.checkUser(user_name)) {
                    System.out.println("This name already exists!");
                    System.out.println("Enter 'Y' to try to login with another name or use command 'login'");
                    String str = scanner.nextLine();
                    if (!str.equals("Y") && !str.equals("y"))
                        break;
                    continue;
                }
                do {
                    System.out.print("Enter password: ");
                    String pass1 = PasswordManager.enterPassword();
                    System.out.print("Accept password: ");
                    String pass2 = PasswordManager.enterPassword();
                    if (!pass1.isEmpty() && pass1.equals(pass2)) {
                        this.db.registerUser(user_name, PasswordManager.hashPassword(pass1));
                        this.userStatusManager.setUser_name(user_name);
                        this.userStatusManager.setStatus(true);
                        System.out.println("You have successfully registered");
                        break;
                    }
                    System.out.println("Password mismatch");
                    System.out.println("Enter 'Y' to try to again'");
                    inputCheck = scanner.nextLine();
                } while (inputCheck.equals("Y") || inputCheck.equals("y"));
                break;
            }
        }
    }

    public String Description() {
        return "add user.";
    }

    public boolean VerifyInputParameters(String[] args) {
        return (args.length == 1);
    }
}
