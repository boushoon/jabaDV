package ru.ac.uniyar.databasescourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskClass {
    public static void createTable(Statement smt){
        String query =
                "CREATE TABLE IF NOT EXISTS students2" +
                "(" +
                "card INT PRIMARY KEY NOT NULL," +
                "name CHAR(127) NOT NULL," +
                "surname CHAR(127) NOT NULL," +
                "answer TEXT NOT NULL," +
                "score FLOAT," +
                "review TEXT," +
                "has_pass BOOL" +
                ")";
        try (ResultSet rs = smt.executeQuery(query)) {
            ResultSet new_rs = smt.executeQuery("SELECT * FROM students2");
            System.out.println("\nвсе хорошо!!!1");
                for(int i = 1; i <= 7; i++)
                    System.out.print(new_rs.getMetaData().getColumnName(i) + " | ");
        }
        catch (SQLException ex) {
            System.out.printf("Statement execution error: %s\n", ex);
        }
    }
}
