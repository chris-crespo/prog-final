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

import models.*;

interface RowMapper<T> {
    T apply(ResultSet rs) throws SQLException;
}

public class Db {
    private static Db instance;
    private static String url = "jdbc:postgresql://ec2-34-246-227-219.eu-west-1.compute.amazonaws.com:5432/dbas1vp8gkllu9";
    private static String user = "zuqecytnilorph";
    private static String password = "6f81f26818ecf95518cec24cfc4fda9e1d3a1b81e86cafaeb08836128c624196";

    private Connection connection;

    private Db() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
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

    private Camp mapCamp(ResultSet rs) throws SQLException {
        var name = rs.getString(1);
        var kind = rs.getString(2);
        var desc = rs.getString(3);
        var loc  = rs.getString(4);

        return new Camp(name, kind, desc, loc);
    }

    private <T> List<T> mapRs(ResultSet rs, RowMapper<T> fn) throws SQLException {
        return new ArrayList<>() {{
            while (rs.next()) add(fn.apply(rs));
        }};
    }

    public List<Camp> fetchCamps() {
        var query = "select camp_name, kind, description, location from camp"; 
        try (var rs = connection.createStatement().executeQuery(query)) {
            return mapRs(rs, this::mapCamp);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
