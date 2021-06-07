package it.unipd.dei.webapp.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * Enum that contains all the possible error in the webapp
 */
public enum ErrorCode {

    // List of all the errors

    DOCTOR_NOT_FOUND("E4D1", HttpServletResponse.SC_NOT_FOUND, "Doctor not found"),
    DOCTOR_NOT_CREATED("E5D2", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create doctor"),
    DOCTOR_CONFLICT("E5D3", HttpServletResponse.SC_CONFLICT,"Cannot create the doctor: it already exists."),
    DOCTOR_ALREADY_NON_ACTIVE("E5D4", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot update doctor status because is already inactive"),
    DOCTOR_UNSUPPORTED_OPERATION("E4D5", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for URI /doctor."),

    PATIENT_NOT_FOUND("E4P1", HttpServletResponse.SC_NOT_FOUND, "Patient not found"),
    PATIENT_NOT_CREATED("E5P2", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create patient"),
    PATIENT_CONFLICT("E5P3", HttpServletResponse.SC_CONFLICT, "Cannot create the patient: it already exists."),
    PATIENT_UNSUPPORTED_OPERATION("E4P4", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for URI /patient."),

    MEDICINE_NOT_CREATED("E5M1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create medicine"),
    MEDICINE_NOT_FOUND("E5M2", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to search medicine"),
    MEDICINE_CONFLICT("E5M3", HttpServletResponse.SC_CONFLICT,"Cannot create the medicine: it already exists."),
    MEDICINE_UNSUPPORTED_OPERATION("E4M4", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for URI /medicine."),
    MEDICINE_INVALID_PARAMETERS("E4M5", HttpServletResponse.SC_BAD_REQUEST, "Cannot serve the request: invalid cf"),

    EXAMS_INVALID_PARAMETERS("E4E1", HttpServletResponse.SC_BAD_REQUEST, "Cannot serve the request"),
    EXAMS_UNSUPPORTED_OPERATION("E4E2", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for URI /exams."),
    EXAMS_NOT_FOUND("E5E3", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to search exams"),

    MEDICAL_EXAMINATION_NOT_CREATED("E5V1", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot create the medical examination"),
    MEDICAL_EXAMINATION_INVALID_PARAMETERS("E4V2", HttpServletResponse.SC_BAD_REQUEST, "Cannot create the examination: date or time are not valid."),
    MEDICAL_EXAMINATION_NOT_FOUND("E5V3", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to search medical examination"),
    MEDICAL_EXAMINATION_BAD_URI("E5V4", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to search medical examination: error while parsing URI"),
    MEDICAL_EXAMINATION_CONFLICT("E5V5", HttpServletResponse.SC_CONFLICT,"Cannot create the medical examination: it already exists."),
    MEDICAL_EXAMINATION_UNSUPPORTED_OPERATION("E4V6", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for URI /medicalExamination."),

    WRONG_DATA_FORMAT("E4G1", HttpServletResponse.SC_BAD_REQUEST, "The data is in the wrong format"),
    WRONG_JSON_FORMAT("E4G2", HttpServletResponse.SC_BAD_REQUEST, "The input json is in the wrong format"),
    UNKNOWN_OPERATION("E4G3", HttpServletResponse.SC_BAD_REQUEST, "Requested an unknown operation"),
    METHOD_NOT_ALLOWED("E4G4", HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method requested not implemented"),
    UNSPECIFIED_MEDIA_TYPE("E4G5", HttpServletResponse.SC_BAD_REQUEST, "Media type not specified."),
    UNSUPPORTED_MEDIA_TYPE("E4G6", HttpServletResponse.SC_NOT_ACCEPTABLE, "Unsupported media type. Resources are represented only in application/json."),
    WRONG_CREDENTIAL("E4G7", HttpServletResponse.SC_BAD_REQUEST, "Submitted credentials are wrong"),
    INVALID_INPUT_PARAMETERS("E4G8", HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters"),
    EMAIL_NOT_SENT("E5G9", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while sending an email"),
    WRONG_VERIFICATION_CODE("E4G10", HttpServletResponse.SC_BAD_REQUEST, "Wrong verification code"),
    VALIDATION_ERROR("E5G11", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while validating the user"),

    SERVER_ERROR("E5G12", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");

    /**
     * The error code in the webapp
     */
    private final String errorCode;

    /**
     * The http error code
     */
    private final int httpCode;

    /**
     * The message of the error
     */
    private final String errorMessage;

    /**
     * Create a new ErrorCode Object
     */
    ErrorCode(String errorCode, int httpCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Get the error code
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Get the http error
     *
     * @return the http code
     */
    public int getHTTPCode() {
        return httpCode;
    }

    /**
     * Get the message of the error
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Convert an ErrorCode into a JSONObject
     *
     * @return a JSONObject containing an ErrorCode
     */
    public JSONObject toJSON() {
        JSONObject error = new JSONObject();
        error.put("code", errorCode);
        error.put("message", errorMessage);
        JSONObject info = new JSONObject();
        info.put("error", error);
        return info;
    }
}
