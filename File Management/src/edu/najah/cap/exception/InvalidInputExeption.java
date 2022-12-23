package edu.najah.cap.exception;

public class InvalidInputExeption extends Exception{
    private String message;

    public InvalidInputExeption(String message) {
        super(message);
        this.message = message;
    }
    public InvalidInputExeption() {
    }
}
