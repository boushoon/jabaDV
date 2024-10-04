package ru.ac.uniyar.databasescourse.objects;

public class Solution {
    private final int id;
    private final int studentID;
    private final int reviewerID;
    private final double score;
    private final boolean hasPassed;

    public Solution(int id, boolean hasPassed, double score, int reviewerID, int studentID) {
        this.id = id;
        this.hasPassed = hasPassed;
        this.score = score;
        this.reviewerID = reviewerID;
        this.studentID = studentID;
    }

    public int getId() {
        return id;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public double getScore() {
        return score;
    }

    public boolean isHasPassed() {
        return hasPassed;
    }
}
