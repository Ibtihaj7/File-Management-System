package edu.najah.cap.exception;

public class AuthorizationExeption extends Exception{
    private String message;

    public AuthorizationExeption(String message) {
        super(message);
        this.message = message;
    }
    public AuthorizationExeption() {
    }
}
