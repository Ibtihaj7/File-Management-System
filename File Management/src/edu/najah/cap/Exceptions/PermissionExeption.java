package edu.najah.cap.Exceptions;

public class PermissionExeption extends Exception{
    private String message;

    public PermissionExeption(String message) {
        super(message);
        this.message = message;
    }
    public PermissionExeption() {
    }
}
