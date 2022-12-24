package edu.najah.cap.Exceptions;

public class FileNotFoundExeption extends Exception{
    private String message;

    public FileNotFoundExeption(String message) {
        super(message);
        this.message = message;
    }
    public FileNotFoundExeption() {
    }
}