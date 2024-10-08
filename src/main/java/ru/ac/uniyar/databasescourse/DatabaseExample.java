package ru.ac.uniyar.databasescourse;

import ru.ac.uniyar.databasescourse.objects.*;
import ru.ac.uniyar.databasescourse.queries.Task;

import java.io.IOException;
import java.sql.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static ru.ac.uniyar.databasescourse.utils.DataReader.csvRead;

public class DatabaseExample {
    private static final String URL = String.format("jdbc:mariadb://%s?allowMultiQueries=true", System.getenv("MARIADB_HOST"));
    private static final String user = System.getenv("MARIADB_USER");
    private static final String password = System.getenv("MARIADB_PASSWORD");

    public static void main(String[] args){
        DataResult data = null;
        try{
            data = csvRead(Path.of("data.csv"));
        }
        catch(IOException ex){
            System.out.printf("Error: %s\n", ex);
        }

        ArrayList<Student> students = data.getStudents();
        ArrayList<Solution> solutions = data.getSolutions();
        ArrayList<Reviewer> reviewers = data.getReviewers();
        ArrayList<Department> departments = data.getDepartments();

        try (Connection conn = createConnection()) {
            try (Statement smt = conn.createStatement()) {
                /*Practice 2
                Task.dropAllTables(conn);
                Task.createTables(conn);
                Task.insertStudents(conn, students);
                Task.insertDepartments(conn, departments);
                Task.insertReviewers(conn, reviewers);
                Task.insertSolutions(conn, solutions);
                 */
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

