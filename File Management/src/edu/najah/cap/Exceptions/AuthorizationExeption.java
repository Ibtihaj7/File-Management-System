package edu.najah.cap.Exceptions;

public class AuthorizationExeption extends Exception{
    private String message;

    public AuthorizationExeption(String message) {
        super(message);
        this.message = message;
    }
    public AuthorizationExeption() {
    }
}
