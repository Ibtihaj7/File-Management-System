package edu.najah.cap.exception;

public class TypeNotSupportExeption extends Exception{
    private String message;

    public TypeNotSupportExeption(String message) {
        super(message);
        this.message = message;
    }
    public TypeNotSupportExeption() {
    }
}
