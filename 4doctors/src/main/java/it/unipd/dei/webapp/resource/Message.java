package it.unipd.dei.webapp.resource;


import org.json.JSONObject;

/**
 * Represents a message or an error message.
 */
public class Message {

    /**
     * The message
     */
    private final String message;

    /**
     * The code of the error, if any
     */
    private final String errorCode;

    /**
     * Additional details about the error, if any
     */
    private final String errorDetails;

    /**
     * Indicates whether the message is about an error or not.
     */
    private final boolean isError;


    /**
     * Creates an error message.
     *
     * @param message
     *            the message.
     * @param errorCode
     *            the code of the error.
     * @param errorDetails
     *            additional details about the error.
     */
    public Message(final String message, final String errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }


    /**
     * Creates a generic message.
     *
     * @param message
     *            the message.
     */
    public Message(final String message) {
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }


    /**
     * Returns the message.
     *
     * @return the message.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Returns the code of the error, if any.
     *
     * @return the code of the error, if any, {@code null} otherwise.
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns additional details about the error, if any.
     *
     * @return additional details about the error, if any, {@code null} otherwise.
     */
    public final String getErrorDetails() {
        return errorDetails;
    }

    /**
     * Indicates whether the message is about an error or not.
     *
     * @return {@code true} is the message is about an error, {@code false} otherwise.
     */
    public final boolean isError() {
        return isError;
    }

    /**
     * Convert a message object into a JSONObject
     *
     * @return a JSONObject containing a message
     */
    public final JSONObject toJSON() {

        JSONObject messageJson = new JSONObject();
        messageJson.put("message", message);

        if(errorCode != null){
            messageJson.put("error-code", errorCode);
        }

        if(errorDetails != null){
            messageJson.put("error-details", errorDetails);
        }

        return messageJson;

    }
}
