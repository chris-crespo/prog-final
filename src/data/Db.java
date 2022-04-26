package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.*;

import utils.Result;
import models.*;

interface RowMapper<T> {
    T apply(ResultSet rs) throws SQLException;
}

public class Db {
    private static Db instance;
    private static String url = "jdbc:postgresql://" + System.getenv().get("DATABASE_URL");
    private static String user = System.getenv().get("USER");
    private static String password = System.getenv().get("PASSWORD");

    private Connection connection;

    private Db() throws SQLException {
        System.out.println(url);
        this.connection = DriverManager.getConnection(url, user, password);

        var closeHook = new Thread(this::close);
        Runtime.getRuntime().addShutdownHook(closeHook);
    }

    private void close() {
        try {
            connection.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Optional<Db> instance() {
        if (instance == null) {
            try {
                return Optional.of(instance = new Db());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }

        return Optional.of(instance);
    }

    private <T> List<T> mapRs(ResultSet rs, RowMapper<T> fn) throws SQLException {
        return new ArrayList<>() {{
            while (rs.next()) add(fn.apply(rs));
        }};
    }

    private <T> List<T> fetch(String query, RowMapper<T> fn) throws SQLException {
        try (var rs = connection.createStatement().executeQuery(query)) {
            return mapRs(rs, fn);
        }
    }

    private Camp mapCamp(ResultSet rs) throws SQLException {
        var name = rs.getString(1);
        var kind = rs.getString(2);
        var desc = rs.getString(3);
        var loc  = rs.getString(4);

        return new Camp(name, kind, desc, loc);
    }

    public Result<List<Camp>> fetchCamps() {
        var query = "select camp_name, kind, description, location from camp"; 
        return Result.of(() -> fetch(query, this::mapCamp));
    }
}
