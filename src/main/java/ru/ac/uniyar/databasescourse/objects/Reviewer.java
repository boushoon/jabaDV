package ru.ac.uniyar.databasescourse.objects;

public class Reviewer {
    private final int id;
    private final String surname;
    private final int departmentID;

    public Reviewer(int id, String surname, int departmentID) {
        this.id = id;
        this.surname = surname;
        this.departmentID = departmentID;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
