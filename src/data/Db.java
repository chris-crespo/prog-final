package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Optional;

public class Db {
    private static Db instance;
    private static String url = "jdbc:oracle:thin:@192.168.100.160:49164";
    private static String user = "system";
    private static String password = "5596";

    private Connection connection;
    private Statement statement;

    private Db() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        this.statement = this.connection.createStatement();
    }

    public static Optional<Db> instance() {
        if (instance == null) {
            try {
                return Optional.of(new Db());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }

        return Optional.of(instance);
    }
}
