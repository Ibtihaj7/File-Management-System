package edu.najah.cap.Exceptions;

public class InvalidInputExeption extends Exception{
    private String message;

    public InvalidInputExeption(String message) {
        super(message);
        this.message = message;
    }
    public InvalidInputExeption() {
    }
}
