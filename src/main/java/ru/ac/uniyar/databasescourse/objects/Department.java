package ru.ac.uniyar.databasescourse.objects;

public class Department {
    private static int count = 0;
    private final String name;
    private final int id;

    public Department(String name) {
        this.name = name;
        id = ++count;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
