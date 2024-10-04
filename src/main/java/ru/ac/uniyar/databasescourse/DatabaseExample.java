package ru.ac.uniyar.databasescourse;

import java.sql.*;

public class DatabaseExample {
    private static final String URL = String.format("jdbc:mariadb://%s", System.getenv("MARIADB_HOST"));
    private static final String user = System.getenv("MARIADB_USER");
    private static final String password = System.getenv("MARIADB_PASSWORD");

    public static void main(String[] args) {
        System.out.println("The work has started");
        System.out.println(System.getenv("MARIADB_HOST"));
        String query = "SELECT card, name, surname FROM students";
        try (Connection conn = createConnection()) {
            try (Statement smt = conn.createStatement()) {
                TaskClass.createTable(smt);
            }
            catch (SQLException ex) {
                System.out.printf("Can't create statement: %s\n", ex);
            }
        }
        catch (SQLException ex) {
            System.out.printf("Can't create connection: %s\n", ex);
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }
}

