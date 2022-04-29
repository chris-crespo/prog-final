package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.*;

import utils.Result;
import models.*;

interface RowMapper<T> {
    T apply(ResultSet rs) throws SQLException;
}

interface StmntPrep<T> {
    PreparedStatement prepare(PreparedStatement statement, T obj) throws SQLException;
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

    private <T> int update(String query, StmntPrep<T> fn, T camp) throws SQLException {
        try (var statement = connection.prepareStatement(query)) {
            return fn.prepare(statement, camp).executeUpdate();
        }
    }

    private Camp mapCamp(ResultSet rs) throws SQLException {
        var id   = rs.getInt(1);
        var name = rs.getString(2);
        var kind = rs.getString(3);
        var desc = rs.getString(4);
        var loc  = rs.getString(5);
        var min_age = rs.getInt(6);
        var max_age = rs.getInt(7);

        return new Camp(id, name, kind, desc, loc, min_age, max_age);
    }

    private PreparedStatement prepareCamp(PreparedStatement statement, Camp camp) throws SQLException {
        statement.setString(1, camp.name());
        statement.setString(2, camp.kind());
        statement.setString(3, camp.description());
        statement.setString(4, camp.location());
        statement.setInt(5, camp.min_age());
        statement.setInt(6, camp.max_age());
        statement.setInt(7, camp.id());

        return statement;
    }

    public Result<List<Camp>> fetchCamps() {
        var query = "select id, camp_name, kind, description, location, min_age, max_age from camp"; 
        return Result.of(() -> fetch(query, this::mapCamp));
    }

    public Result<Integer> updateCamp(Camp camp) {
        var query = """
            update camp set
                camp_name = ?,
                kind = ?,
                description = ?,
                location = ?,
                min_age = ?,
                max_age = ?
            where id = ?;""";
        return Result.of(() -> update(query, this::prepareCamp, camp));
    }
}
