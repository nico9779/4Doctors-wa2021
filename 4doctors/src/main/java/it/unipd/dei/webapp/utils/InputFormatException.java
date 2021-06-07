package it.unipd.dei.webapp.utils;

/**
 * Exception called when the input inserted in the form
 * by the user has a bad format.
 */
public class InputFormatException extends Exception {
    public InputFormatException(String errorMessage) {
        super(errorMessage);
    }
}
