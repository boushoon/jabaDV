package ru.ac.uniyar.databasescourse.objects;

import java.util.ArrayList;

public class DataResult {
    private final ArrayList<Student> students;
    private final ArrayList<Solution> solutions;
    private final ArrayList<Reviewer> reviewers;
    private final ArrayList<Department> departments;

    public DataResult(ArrayList<Student> students,
                      ArrayList<Solution> solutions,
                      ArrayList<Reviewer> reviewers,
                      ArrayList<Department> departments) {
        this.students = students;
        this.solutions = solutions;
        this.reviewers = reviewers;
        this.departments = departments;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public ArrayList<Reviewer> getReviewers() {
        return reviewers;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }
}
