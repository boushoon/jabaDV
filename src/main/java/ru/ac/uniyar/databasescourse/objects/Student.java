package ru.ac.uniyar.databasescourse.objects;

public class Student {
    private final int id;
    private final String name;
    private final String surname;

    public Student (int id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
