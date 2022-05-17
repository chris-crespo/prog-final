package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.Booking;
import models.Camp;
import models.Instructor;
import models.User;
import utils.Result;

interface RowMapper<T> {
    T apply(ResultSet rs) throws SQLException;
}

interface StmntPrep<T> {
    PreparedStatement prepare(PreparedStatement statement, T obj) throws SQLException;
}

public class Db {
    private static Db instance;
    private static String url = "jdbc:postgresql://ec2-54-164-40-66.compute-1.amazonaws.com:5432/d8f5g95nfav1ct";
    private static String user = "qcvhtyeqwvepqc";
    private static String password = "659503667819440713159bc9a9b6fe23930a5dda971c0efbdf0e823d191c8c8a";

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
        } catch (Exception e) {
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
        return new ArrayList<>() {
            {
                while (rs.next())
                    add(fn.apply(rs));
            }
        };
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
        var id = rs.getInt(1);
        var name = rs.getString(2);
        var kind = rs.getString(3);
        var desc = rs.getString(4);
        var loc = rs.getString(5);
        var startDate = rs.getDate(6).toLocalDate();
        var endDate = rs.getDate(7).toLocalDate();
        var minAge = rs.getInt(8);
        var maxAge = rs.getInt(9);

        return new Camp(id, name, kind, desc, loc, startDate, endDate, minAge, maxAge);
    }

    private String mapCampKind(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        var username = rs.getString(1);
        var email = rs.getString(2);
        var firstName = rs.getString(4);
        var lastName = rs.getString(5);
        var phone = rs.getString(6);

        return new User(username, email, firstName + " " + lastName, phone);
    }

    private Booking mapBooking(ResultSet rs) throws SQLException {
        var kidName = rs.getString(1);
        var kidDni = rs.getString(2);
        var email = rs.getString(3);
        var campName = rs.getString(4);

        return new Booking(kidName, kidDni, email, campName);
    }

    private Instructor mapInstructor(ResultSet rs) throws SQLException {
        var dni = rs.getString(1);
        var firstName = rs.getString(2);
        var lastName = rs.getString(3);
        var phone = rs.getString(4);
        var campName = rs.getString(5);
        var activity = rs.getString(6);

        return new Instructor(dni, firstName + " " + lastName, phone, campName, activity);
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

    public Result<List<User>> fetchUsers() {
        var query = "select * from app_user";
        return Result.of(() -> fetch(query, this::mapUser));
    }

    public Result<List<Booking>> fetchBookings() {
        var query = """
                select concat(k.first_name, ' ', k.last_name), dni, u.email, camp_name
                from booking b join kid k
                on b.kid = k.dni
                join app_user u
                on u.email = b.user_email
                join camp c
                on c.id = b.camp""";

        return Result.of(() -> fetch(query, this::mapBooking));
    }

    public Result<List<Instructor>> fetchInstructors() {
        var query = """
                select dni, first_name, last_name, phone, camp_name, activity
                from instructor i join camp c
                on i.camp = c.id
                join activity a
                on i.activity = a.act_name""";

        return Result.of(() -> fetch(query, this::mapInstructor));
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
