package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;

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
        var id        = rs.getInt(1);
        var name      = rs.getString(2);
        var kind      = rs.getString(3);
        var desc      = rs.getString(4);
        var loc       = rs.getString(5);
        var startDate = rs.getDate(6).toLocalDate();
        var endDate   = rs.getDate(7).toLocalDate();
        var minAge    = rs.getInt(8);
        var maxAge    = rs.getInt(9);

        return new Camp(id, name, kind, desc, loc, startDate, endDate, minAge, maxAge);
    }

    private String mapCampKind(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }

    private PreparedStatement prepareCampInsert(PreparedStatement statement, Camp camp) throws SQLException {
        statement.setString(1, camp.name());
        statement.setString(2, camp.kind());
        statement.setString(3, camp.description());
        statement.setString(4, camp.location());
        statement.setDate(5, Date.valueOf(camp.startDate()));
        statement.setDate(6, Date.valueOf(camp.endDate()));
        statement.setInt(7, camp.minAge());
        statement.setInt(8, camp.maxAge());

        return statement;
    }

    private PreparedStatement prepareCampUpdate(PreparedStatement statement, Camp camp) throws SQLException {
        prepareCampInsert(statement, camp);
        statement.setInt(9, camp.id());

        return statement;
    }

    private PreparedStatement prepareCampDelete(PreparedStatement statement, Camp camp) throws SQLException {
        statement.setInt(1, camp.id());
        return statement;
    }

    public Result<List<Camp>> fetchCamps() {
        var query = "select * from camp"; 
        return Result.of(() -> fetch(query, this::mapCamp));
    }

    public Result<List<String>> fetchCampKinds() {
        var query = "select kind from camp_kind";
        return Result.of(() -> fetch(query, this::mapCampKind));
    }

    public Result<Integer> addCamp(Camp camp) {
        var query = """
            insert into camp (camp_name, kind, description, location, start_date, end_date, min_age, max_age)
            values (?, ?, ?, ?, ?, ?, ?, ?)""";
        return Result.of(() -> update(query, this::prepareCampInsert, camp));
    }

    public Result<Integer> updateCamp(Camp camp) {
        var query = """
            update camp set
                camp_name   = ?,
                kind        = ?,
                description = ?,
                location    = ?,
                start_date  = ?,
                end_date    = ?,
                min_age     = ?,
                max_age     = ?
            where id = ?;""";
        return Result.of(() -> update(query, this::prepareCampUpdate, camp));
    }

    public Result<Integer> deleteCamp(Camp camp) {
        var query = "delete from camp where id = ?";
        return Result.of(() -> update(query, this::prepareCampDelete, camp));
    }
}
