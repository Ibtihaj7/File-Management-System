package edu.najah.cap.Exceptions;

public class CategoryNotFoundExeption extends Exception{
    private String message;

    public CategoryNotFoundExeption(String message) {
        super(message);
        this.message = message;
    }
    public CategoryNotFoundExeption() {
    }
}
