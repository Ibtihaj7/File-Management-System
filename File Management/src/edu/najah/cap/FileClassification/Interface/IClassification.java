package edu.najah.cap.FileClassification.Interface;


import java.sql.SQLException;

public interface IClassification {
    public void addClassification(String fileName) throws SQLException;
}