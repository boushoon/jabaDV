package ru.ac.uniyar.databasescourse.queries;

import ru.ac.uniyar.databasescourse.objects.Department;
import ru.ac.uniyar.databasescourse.objects.Reviewer;
import ru.ac.uniyar.databasescourse.objects.Solution;
import ru.ac.uniyar.databasescourse.objects.Student;

import java.sql.*;
import java.util.ArrayList;

public class Task {
    public static void createTables(Connection conn){
        String query =
                "CREATE TABLE IF NOT EXISTS students(" +
                "id INT PRIMARY KEY NOT NULL," +
                "name CHAR(127) NOT NULL," +
                "surname CHAR(127) NOT NULL);" +

                "CREATE TABLE IF NOT EXISTS departments(" +
                "id INT PRIMARY KEY NOT NULL," +
                "name CHAR(127) NOT NULL);" +

                "CREATE TABLE IF NOT EXISTS reviewers(" +
                "id INT PRIMARY KEY NOT NULL," +
                "surname CHAR(127) NOT NULL," +
                "departmentID INT," +
                "FOREIGN KEY(departmentID) REFERENCES departments(id));" +

                "CREATE TABLE IF NOT EXISTS solutions(" +
                "id INT PRIMARY KEY NOT NULL," +
                "score FLOAT," +
                "hasPassed BOOLEAN," +
                "studentID INT," +
                "reviewerID INT," +
                "FOREIGN KEY(studentID) REFERENCES students(id)," +
                "FOREIGN KEY(reviewerID) REFERENCES reviewers(id));";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeQuery();
            System.out.println("все хорошо!!!!11");
        }
        catch (SQLException ex) {
            System.out.printf("Statement execution error: %s\n", ex);
        }
    }

    public static void insertStudents(Connection conn, ArrayList<Student> students){
        String query = "INSERT INTO students (id, name, surname) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Student student : students) {
                stmt.setInt(1, student.getId());
                stmt.setString(2, student.getName());
                stmt.setString(3, student.getSurname());

                stmt.executeUpdate();
            }
            System.out.println("Students successful added in table 'students'!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void insertDepartments(Connection conn, ArrayList<Department> departments) {
        String query = "INSERT INTO departments (id, name) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Department department : departments) {
                stmt.setInt(1, department.getId());
                stmt.setString(2, department.getName());
                stmt.executeUpdate();
            }
            System.out.println("Departments successfully added to table 'departments'!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void insertReviewers(Connection conn, ArrayList<Reviewer> reviewers) {
        String query = "INSERT INTO reviewers (id, surname, departmentID) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Reviewer reviewer : reviewers) {
                stmt.setInt(1, reviewer.getId());
                stmt.setString(2, reviewer.getSurname());
                stmt.setInt(3, reviewer.getDepartmentID());
                stmt.executeUpdate();
            }
            System.out.println("Reviewers successfully added to table 'reviewers'!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void insertSolutions(Connection conn, ArrayList<Solution> solutions) {
        String query = "INSERT INTO solutions (id, score, hasPassed, reviewerID, studentID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Solution solution : solutions) {
                stmt.setInt(1, solution.getId());
                stmt.setDouble(2, solution.getScore());
                stmt.setBoolean(3, solution.isHasPassed());
                stmt.setInt(4, solution.getReviewerID());
                stmt.setInt(5, solution.getStudentID());
                stmt.executeUpdate();
            }
            System.out.println("Solutions successfully added to table 'solutions'!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void dropStudentsTable(Connection conn) {
        String query = "DROP TABLE IF EXISTS students";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeQuery();
            System.out.println("Table 'students' dropped successfully!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void dropDepartmentTable(Connection conn) {
        String query = "DROP TABLE IF EXISTS departments";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeQuery();
            System.out.println("Table 'department' dropped successfully!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void dropReviewersTable(Connection conn) {
        String query = "DROP TABLE IF EXISTS reviewers";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeQuery();
            System.out.println("Table 'reviewers' dropped successfully!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void dropSolutionsTable(Connection conn) {
        String query = "DROP TABLE IF EXISTS solutions";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.executeQuery();
            System.out.println("Table 'solutions' dropped successfully!");
        } catch (SQLException ex) {
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void dropAllTables(Connection conn) {
        dropSolutionsTable(conn);
        dropReviewersTable(conn);
        dropStudentsTable(conn);
        dropDepartmentTable(conn);
    }
}
