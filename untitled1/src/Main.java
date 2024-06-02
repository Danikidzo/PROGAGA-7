import DataBase.DataBase;
import DataBase.DataBaseManager;
import Managers.CommandManager;
import Managers.UserStatusManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5252/postgres";
            String user = "postgres";
            String password = "UmkH&6102";
            DataBaseManager dataBaseManager = new DataBaseManager(url, user, password);
            dataBaseManager.initDataBase();
            DataBase db = new DataBase(dataBaseManager);
            UserStatusManager userStatusManager = new UserStatusManager(false, "");
            CommandManager commandManager = new CommandManager(userStatusManager, dataBaseManager, db, new Scanner(System.in), false);
            commandManager.Run();
        } catch (Exception e) {
            System.out.println("Abnormal termination:");
            System.out.println(e.getMessage());
        } finally {
            System.out.println("The application is closed.");
        }
    }
}