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

    public static void selectMaxOrMinScoreStudents(Connection conn, boolean minOrMax){
        // minOrMax = 1 -> Max
        // minOrMax = 0 -> Min
        String function = minOrMax ? "MAX" : "MIN";

        String query =
                "WITH query AS (\n" +
                "\tSELECT s.id, s.name, s.surname, sol.score\n" +
                " \tFROM solutions sol\n" +
                " \tJOIN students s ON sol.studentID = s.id\n" +
                ")\n" +
                "SELECT id, name, surname, score from query\n" +
                "WHERE score = (SELECT " + function + "(score) FROM query);";

        try(PreparedStatement statement = conn.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();

            if (minOrMax)
                System.out.println("Students with the highest average score:");
            else
                System.out.println("Students with the lowest average score:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                double score = rs.getDouble("score");
                System.out.printf("\tID: %d\n\tName: %s %s\n\tScore: %.2f\n\n", id, name, surname, score);
            }
        } catch (SQLException ex){
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void selectMaxOrMinScoreReviewers(Connection conn, boolean minOrMax){
        /*
        minOrMax = 1 -> Max
        minOrMax = 0 -> Min
         */
        String function = minOrMax ? "MAX" : "MIN";

        String query =
                "WITH query AS (\n" +
                "\tSELECT r.id, r.surname, sol.score\n" +
                " \tFROM solutions sol\n" +
                " \tJOIN reviewers r ON sol.reviewerID = r.id\n" +
                ")\n" +
                "SELECT id, surname, score from query\n" +
                "WHERE score = (SELECT " + function + "(score) FROM query);";

        try(PreparedStatement statement = conn.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();

            if (minOrMax)
                System.out.println("Reviewers with the highest average score:");
            else
                System.out.println("Reviewers with the lowest average score:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                double score = rs.getDouble("score");
                System.out.printf("\tID: %d\n\tSurname: %s\n\tScore: %.2f\n\n", id, surname, score);
            }
        } catch (SQLException ex){
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void distributionOfScore(Connection conn){
        String query =
                "SELECT reviewers.id as rev_id, reviewers.surname as rev_surname, " +
                        "students.id as stud_id, students.name as stud_name, " +
                        "students.surname as stud_surname, AVG(solutions.score) as avg\n" +
                "FROM solutions \n" +
                "JOIN students ON solutions.studentID = students.id \n" +
                "JOIN reviewers ON solutions.reviewerID = reviewers.id\n" +
                "GROUP BY reviewers.id, students.id;";

        System.out.println("Distribution of score among students:");

        try(PreparedStatement statement = conn.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int rev_id = rs.getInt("rev_id");
                int stud_id = rs.getInt("stud_id");

                String rev_surname = rs.getString("rev_surname");
                String stud_surname = rs.getString("stud_surname");
                String stud_name = rs.getString("stud_name");
                double score = rs.getDouble("avg");
                System.out.printf("\tREV_ID: %d\n\tSurname: %s" +
                        "\n\t\tSTUD_ID: %d\n\t\tName: %s %s\n\t\tAverage score: %.2f\n\n", rev_id, rev_surname, stud_id,
                        stud_name, stud_surname, score);
            }
        } catch (SQLException ex){
            System.out.printf("Error: %s\n", ex);
        }
    }

    public static void countScore(Connection conn){
        String query =
                "SELECT s.id as stud_id, s.name as stud_name, s.surname as stud_surname,\n" +
                "r.id as rev_id, r.surname as rev_surname, COUNT(sol.id) as rev_count from solutions sol\n" +
                "JOIN students s ON sol.studentID = s.id\n" +
                "JOIN reviewers r ON sol.reviewerID = r.id\n" +
                "GROUP BY s.id, r.id\n" +
                "ORDER BY COUNT(sol.id) DESC;";

        System.out.println("Count score among reviewers:");

        try(PreparedStatement statement = conn.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int rev_id = rs.getInt("rev_id");
                int stud_id = rs.getInt("stud_id");

                String rev_surname = rs.getString("rev_surname");
                String stud_surname = rs.getString("stud_surname");
                String stud_name = rs.getString("stud_name");
                int score = rs.getInt("rev_count");
                System.out.printf("\tREV_ID: %d\n\tSurname: %s" +
                                "\n\t\tSTUD_ID: %d\n\t\tName: %s %s\n\t\tCount score: %d\n\n", rev_id, rev_surname, stud_id,
                        stud_name, stud_surname, score);
            }
        } catch (SQLException ex){
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
