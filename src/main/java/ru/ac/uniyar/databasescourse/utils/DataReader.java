package ru.ac.uniyar.databasescourse.utils;

import de.siegmar.fastcsv.reader.CsvRow;
import ru.ac.uniyar.databasescourse.objects.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader.load;

public class DataReader {
    private static Department findDepartment(ArrayList<Department> departments, String name){
        for(Department department: departments){
            if (department.getName().equals(name))
                return department;
        }
        return null;
    }

    private static Student findStudent(ArrayList<Student> students, int id){
        for(Student student: students){
            if (student.getId() == id)
                return student;
        }
        return null;
    }

    private static Reviewer findReviewer(ArrayList<Reviewer> reviewers, int id){
        for(Reviewer reviewer: reviewers){
            if (reviewer.getId() == id)
                return reviewer;
        }
        return null;
    }

    public static DataResult csvRead(Path path) throws IOException {
        ArrayList<CsvRow> arrayOfRow = load(path);
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Solution> solutions = new ArrayList<>();
        ArrayList<Reviewer> reviewers = new ArrayList<>();
        ArrayList<Department> departments = new ArrayList<>();

        for(CsvRow row: arrayOfRow){
            List<String> fields = row.getFields();
            int studentID = -1, solutionID = -1, reviewerID = -1;
            String studentName = "", studentSurname = "";
            String reviewerSurname = "", reviewerDepartment = "";
            double score = -1;
            boolean hasPassed = false;

            try {
                studentID = Integer.parseInt(fields.get(0));
                studentName = fields.get(1);
                studentSurname = fields.get(2);
                solutionID = Integer.parseInt(fields.get(3));
                reviewerID = Integer.parseInt(fields.get(4));
                reviewerSurname = fields.get(5);
                reviewerDepartment = fields.get(6);
                score = Double.parseDouble(fields.get(7));
                hasPassed = Objects.equals(fields.get(8), "Yes") ?
                        true : (Objects.equals(fields.get(8), "No") ? false : null);
            }
            catch (NumberFormatException ex){
                System.out.printf("Can`t read data in csv: %s\n", ex);
            }
            Department dpt = findDepartment(departments, reviewerDepartment);
            if (dpt == null) {
                dpt = new Department(reviewerDepartment);
                departments.add(dpt);
            }

            Student student = findStudent(students, studentID);
            if (student == null){
                student = new Student(studentID, studentName, studentSurname);
                students.add(student);
            }

            Reviewer reviewer = findReviewer(reviewers, reviewerID);
            if (reviewer == null){
                reviewer = new Reviewer(reviewerID, reviewerSurname, dpt.getId());
                reviewers.add(reviewer);
            }

            solutions.add(new Solution(solutionID, hasPassed, score, reviewer.getId(), student.getId()));
        }

        return new DataResult(students, solutions, reviewers, departments);
    }
}
