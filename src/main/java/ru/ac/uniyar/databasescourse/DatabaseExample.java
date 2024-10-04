package ru.ac.uniyar.databasescourse;

import java.io.IOException;
import java.sql.*;
import java.nio.file.Path;

import static ru.ac.uniyar.databasescourse.utils.DataReader.csvRead;

public class DatabaseExample {
    private static final String URL = String.format("jdbc:mariadb://%s", System.getenv("MARIADB_HOST"));
    private static final String user = System.getenv("MARIADB_USER");
    private static final String password = System.getenv("MARIADB_PASSWORD");

    public static void main(String[] args) throws IOException {
        System.out.println("The work has started");
        try{
            csvRead(Path.of("data.csv"));
        }
        catch(IOException ex){
            System.out.printf("Error: %s\n", ex);
        }
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

