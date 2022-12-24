package edu.najah.cap.Exceptions;

public class MaxSizeExeption extends Exception{
    private String message;

    public MaxSizeExeption(String message) {
        super(message);
        this.message = message;
    }
    public MaxSizeExeption() {
    }
}
