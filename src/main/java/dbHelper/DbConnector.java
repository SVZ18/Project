package dbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public DbConnector() {
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "pate9gaV!");
            System.out.println("Database connected successfully.");
        } catch (SQLException var2) {
            System.out.println("Unable to connect to database.");
            var2.printStackTrace();
        }

        return connection;

    }
}


