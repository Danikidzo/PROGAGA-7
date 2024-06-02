package DataBase;

import Managers.PasswordManager;
import Objects.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

public class DataBaseManager {
    private Connection connection;

    public DataBaseManager(String url, String user, String password) {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            System.out.println("Error with connection to DataBase");
        }
    }

    public void initDataBase() {
        createUserTable();
        createHumanTable();
    }

    public void createUserTable() {
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "CREATE TABLE IF NOT EXISTS USERS (user_name TEXT PRIMARY KEY, password TEXT);";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }

    public void createHumanTable() {
        //createTypeWeaponType();
        //createTypeMood();
        //createIdSeq();
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "CREATE TABLE IF NOT EXISTS humans (id bigint PRIMARY KEY DEFAULT nextval('ID_SEQ'), name text NOT NULL, coordinates_x bigint NOT NULL,coordinates_y float NOT NULL, creationDate timestamp NOT NULL, realHero boolean NOT NULL,impactSpeed float NOT NULL,hasToothpick boolean,weaponType weapontype, mood mood,isCarCool boolean NOT NULL, user_name text NOT NULL, CONSTRAINT user_name FOREIGN KEY (user_name) REFERENCES users(user_name));";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }
    public void createTypeMood() {
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "CREATE TYPE mood AS ENUM ('FRENZY', 'APATHY', 'GLOOM');";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }
    public void createTypeWeaponType() {
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "CREATE TYPE weapontype AS ENUM ('HAMMER', 'PISTOL', 'RIFLE');";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }


    public void createIdSeq() {
        try {
            Statement statement = this.connection.createStatement(1005, ResultSet.CONCUR_UPDATABLE);
            String sql = "CREATE SEQUENCE IF NOT EXISTS ID_SEQ START WITH 1 INCREMENT BY 1;";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }

    public void truncateTable() {
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "TRUNCATE TABLE Humans;";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }
    public void restartSeq() {
        try {
            Statement statement = this.connection.createStatement(1005, 1008);
            String sql = "ALTER SEQUENCE ID_SEQ RESTART WITH 1;";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error sending request");
        }
    }

    public boolean checkUser(String user_name) {
        boolean exists = false;
        try {
            String sql = "SELECT COUNT(*) AS count FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0)
                    exists = true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error with sql");
        }
        return exists;
    }

    public void registerUser(String user_name, String pswd) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO users (user_name, password) VALUES (?, ?)";
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, pswd);
            preparedStatement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user registration");
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("Error with closing statement");
            }
        }
    }

    public boolean checkPassword(String user_name, String pswd) {
        String sql = "SELECT password FROM USERS WHERE user_name = ?";
        try {
            PreparedStatement prepareStatement = this.connection.prepareStatement(sql);
            prepareStatement.setString(1, user_name);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                String hashedInputPassword = PasswordManager.hashPassword(pswd);
                prepareStatement.close();
                resultSet.close();
                return hashedInputPassword.equals(hashedPassword);
            }
            prepareStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public ArrayList<String> getUsers() {
        String sql = "SELECT user_name FROM USERS;";
        ArrayList<String> users = new ArrayList<>();
        try {
            PreparedStatement prepareStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("user_name");
                users.add(user);
            }
            if (!users.isEmpty()) {
                prepareStatement.close();
                resultSet.close();
            } else {
                users.add("There are no users yet...");
                return users;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }

    public TreeSet<HumanBeing> readFromDataBase() {
        TreeSet<HumanBeing> humans = new TreeSet<>();
        try {
            String sql = "SELECT id, name, coordinates_x, coordinates_y, creationDate, hasToothpick, realHero, impactSpeed, weaponType, mood, isCarCool, user_name FROM humans";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HumanBeing human = new HumanBeing();
                Coordinates coordinates = new Coordinates();
                human.setId(rs.getLong("id"));
                IdManager.AddId(rs.getLong("id"));
                human.setName(rs.getString("name"));
                coordinates.setX(rs.getLong("coordinates_x"));
                coordinates.setY(rs.getFloat("coordinates_y"));
                human.setCoordinates(coordinates);
                human.setCreationDate(rs.getDate("creationDate"));
                human.setRealHero(rs.getBoolean("realHero"));
                human.setImpactSpeed(rs.getFloat("impactSpeed"));
                human.setCar(new Car(rs.getBoolean("isCarCool")));
                human.setUser_name(rs.getString("user_name"));
                Boolean hasToothpick = rs.getBoolean("hasToothpick");
                if (rs.wasNull()) {
                    human.setHasToothpick(null);
                } else {
                    human.setHasToothpick(hasToothpick);
                }
                String weaponTypeString = rs.getString("weaponType");
                if (weaponTypeString != null) {
                    human.setWeaponType(WeaponType.valueOf(weaponTypeString));
                } else {
                    human.setWeaponType(null);
                }
                String moodString = rs.getString("mood");
                if (moodString != null) {
                    human.setMood(Mood.valueOf(moodString));
                } else {
                    human.setMood(null);
                }
                humans.add(human);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return humans;
    }

    public void saveToDB(TreeSet<HumanBeing > humans) {
        truncateTable();
        restartSeq();
        PreparedStatement preparedStatement;
            try{
            String INSERT_USERS_SQL = "INSERT INTO humans" + "  (name, coordinates_x, coordinates_y, creationDate, realHero, impactSpeed, hasToothpick, weaponType, mood, isCarCool, user_name) VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                preparedStatement = this.connection.prepareStatement(INSERT_USERS_SQL);
            for (HumanBeing human: humans) {
                preparedStatement.setString(1, human.getName());
                preparedStatement.setLong(2, human.getCoordinates().getX());
                preparedStatement.setFloat(3, human.getCoordinates().getY());
                preparedStatement.setDate(4, human.getCreationDate());
                preparedStatement.setBoolean(5, human.isRealHero());
                preparedStatement.setFloat(6, human.getImpactSpeed());
                setNullableBoolean(preparedStatement,7, human.getHasToothpick());
                setWeaponType(preparedStatement,8, human.getWeaponType());
                setMood(preparedStatement,9, human.getMood());
                preparedStatement.setBoolean(10, human.getCar().isCool());
                preparedStatement.setString(11, human.getUser_name());
                preparedStatement.executeUpdate();
            }
                System.out.println("Database saved successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setNullableBoolean(PreparedStatement ps, int parameterIndex, Boolean value) throws SQLException {
        if (value != null) {
            ps.setBoolean(parameterIndex, value);
        }
        else {
            ps.setNull(parameterIndex, java.sql.Types.BOOLEAN);
        }
    }

    private void setMood(PreparedStatement ps, int parameterIndex, Mood mood) throws SQLException {
        if (mood != null) {
            ps.setObject(parameterIndex, mood.name(), java.sql.Types.OTHER);
        }
        else {
            ps.setNull(parameterIndex, java.sql.Types.NULL);
        }
    }

    private void setWeaponType(PreparedStatement ps, int parameterIndex, WeaponType weaponType) throws SQLException {
        if (weaponType != null) {
            ps.setObject(parameterIndex, weaponType.name(), java.sql.Types.OTHER);
        }
        else {
            ps.setNull(parameterIndex, java.sql.Types.NULL);
        }
    }

}