package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.resource.*;
import it.unipd.dei.webapp.rest.*;
import it.unipd.dei.webapp.utils.ErrorCode;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Manages the REST API for the different REST resources.
 */
public final class RestManagerServlet extends AbstractDatabaseServlet {

    /**
     * The JSON MIME media type
     */
    private static final String JSON_MEDIA_TYPE = "application/json";

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    /**
     * The any MIME media type
     */
    private static final String ALL_MEDIA_TYPE = "*/*";

    @Override
    protected final void service(final HttpServletRequest req, final HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType(JSON_UTF_8_MEDIA_TYPE);

        // if the request method and/or the MIME media type are not allowed, return.
        // Appropriate error message sent by {@code checkMethodMediaType}
        if (!checkMethodMediaType(req, res)) {
            return;
        }

        // if the requested resource was a Medicine, delegate its processing and return
        if (processMedicine(req, res)) {
            return;
        }
        // if the requested resource was a Medical Examination, delegate its processing and return
        if (processMedicalExamination(req, res)) {
            return;
        }
        // if the requested resource was a Patient, delegate its processing and return
        if(processPatient(req, res)){
            return;
        }
        // if the requested resource was a Doctor, delegate its processing and return
        if(processDoctor(req, res)){
            return;
        }
        // if the requested resource was a Exams, delegate its processing and return
        if(processExams(req, res)){
            return;
        }

        // if none of the above process methods succeeds, it means an unknow resource has been requested
        writeError(res, ErrorCode.UNKNOWN_OPERATION);
    }

    /**
     * Checks that the request method and MIME media type are allowed.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res)
            throws IOException {

        final String method = req.getMethod();
        final String contentType = req.getHeader("Content-Type");
        final String accept = req.getHeader("Accept");

        Message m = null;

        if (accept == null) {
            writeError(res, ErrorCode.UNSPECIFIED_MEDIA_TYPE);
            return false;
        }

        if (!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            writeError(res, ErrorCode.UNSUPPORTED_MEDIA_TYPE);
            return false;
        }

        switch (method) {
            case "GET":
            case "DELETE":
                // nothing to do
                break;

            case "POST":
            case "PUT":
                if (contentType == null) {
                    writeError(res, ErrorCode.UNSPECIFIED_MEDIA_TYPE);
                    return false;
                }

                if (!contentType.contains(JSON_MEDIA_TYPE)) {
                    writeError(res, ErrorCode.UNSUPPORTED_MEDIA_TYPE);
                    return false;
                }

                break;
            default:
                writeError(res, ErrorCode.METHOD_NOT_ALLOWED);
                return false;
        }

        return true;
    }
    /**
     * Checks whether the request is for an {@link Medicine} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for an {@code Medicine; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processExams(HttpServletRequest req, HttpServletResponse res) throws IOException {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;


        if(path.lastIndexOf("rest/exams") <= 0) {
            return false;
        }

        try {
            // strip everyhing until after the /exams
            path = path.substring(path.lastIndexOf("exams") + 5);

            if (path.length() == 0 || path.equals("/")) {

                switch (method) {
                    case "GET":
                        new ExamsRestResource(req, res, getDataSource().getConnection()).listExams();
                        break;
                    default:
                        writeError(res, ErrorCode.EXAMS_UNSUPPORTED_OPERATION);
                }
            } else {
                ErrorCode ec = ErrorCode.EXAMS_INVALID_PARAMETERS;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }

        } catch(Throwable t) {
            writeError(res, ErrorCode.SERVER_ERROR);
        }

        return true;

    }

    /**
     * Checks whether the request is for an {@link Medicine} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for an {@code Medicine; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processMedicine(HttpServletRequest req, HttpServletResponse res) throws IOException {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;


        if(path.lastIndexOf("rest/medicine") <= 0) {
            return false;
        }

        try {
            // strip everyhing until after the /medicine
            path = path.substring(path.lastIndexOf("medicine") + 8);

            if (path.length() == 0 || path.equals("/")) {

                switch (method) {
                    case "GET":
                        new MedicineRestResource(req, res, getDataSource().getConnection()).listMedicines();
                        break;
                    case "POST":
                        new MedicineRestResource(req, res, getDataSource().getConnection()).addMedicine();
                        break;
                    default:
                        writeError(res, ErrorCode.MEDICINE_UNSUPPORTED_OPERATION);
                }
            }else if(path.length() == 17){ // case: path == /cf

                String cf = path.substring(1,17); //remove the '/'

                switch (method){
                    case "GET":
                        new MedicineRestResource(req, res, getDataSource().getConnection()).userMedicines(cf);
                        break;
                    default:
                        ErrorCode ec = ErrorCode.MEDICINE_UNSUPPORTED_OPERATION;
                        res.setStatus(ec.getHTTPCode());
                        res.getWriter().write(ec.toJSON().toString());
                }
            } else {
                ErrorCode ec = ErrorCode.MEDICINE_INVALID_PARAMETERS;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }

        } catch(Throwable t) {
            writeError(res, ErrorCode.SERVER_ERROR);
        }

        return true;

    }

     
    /**
     * Checks whether the request is for an {@link MedicalExamination} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for an {@code MedicalExamination}; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processMedicalExamination(HttpServletRequest req, HttpServletResponse res) throws IOException {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        // the requested resource was not a medical examination
        if(path.lastIndexOf("rest/medicalExamination") <= 0) {
            return false;
        }

        try {
            // strip everything until after the /medicalExamination
            path = path.substring(path.lastIndexOf("medicalExamination") + 18);

            // the request URI is: /medicalExamination
            // if method POST, create medical examination
            if (path.length() == 0 || path.equals("/")) {

                switch (method) {
                    case "POST":
                        new MedicalExaminationRestResource(req, res, getDataSource().getConnection()).createMedicalExamination();
                        break;
                    default:
                        writeError(res, ErrorCode.MEDICAL_EXAMINATION_UNSUPPORTED_OPERATION);
                }
            } else {
                // the request URI is: /medicalExamination/patient/{patient_cf}
                if (path.contains("patient")) {
                    path = path.substring(path.lastIndexOf("patient") + 7);

                    if (path.length() == 0 || path.equals("/")) {
                        writeError(res, ErrorCode.MEDICAL_EXAMINATION_BAD_URI);
                    } else {
                        switch (method) {
                            case "GET":
                                new MedicalExaminationRestResource(req, res, getDataSource().getConnection()).searchMedicalExaminationByPatient();
                                break;
                            default:
                                writeError(res, ErrorCode.MEDICAL_EXAMINATION_UNSUPPORTED_OPERATION);
                        }
                    }
                } else {
                    // the request URI is: /medicalExamination/{doctor_cf}/{date}/{time}
                    switch (method) {
                        case "GET":
                            new MedicalExaminationRestResource(req, res, getDataSource().getConnection()).readMedicalExamination();
                            break;
                        case "PUT":
                            new MedicalExaminationRestResource(req, res, getDataSource().getConnection()).updateMedicalExaminationOutcome();
                            break;
                        case "DELETE":
                            new MedicalExaminationRestResource(req, res, getDataSource().getConnection()).deleteMedicalExamination();
                            break;
                        default:
                            writeError(res, ErrorCode.MEDICAL_EXAMINATION_UNSUPPORTED_OPERATION);
                    }
                }
            }
        } catch(Throwable t) {
            writeError(res, ErrorCode.SERVER_ERROR);
        }

        return true;

    }


    /**
     * Checks whether the request is     for a {@link Patient} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for a {@code Patient}; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processPatient(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();
        String[] tokens = op.split("/");

        if (tokens.length<4 || !(tokens[3].equals("patient") || tokens[3].equals("list"))){
            return false;
        }

        // rest/patient/list
        if(tokens.length == 5 && tokens[3].equals("patient") && tokens[4].equals("list")){
            switch (req.getMethod()) {
                // Get a list of all patients
                case "GET":
                    new PatientRestResource(req, res).getAllPatients();
                    break;
                default:
                    writeError(res, ErrorCode.PATIENT_UNSUPPORTED_OPERATION);
            }
        }
        // GET or DELETE patient/{patient_cf}
        else if(tokens.length == 5 && tokens[3].equals("patient")){
            switch (req.getMethod()) {
                case "GET":
                    // Get a single patient by CF
                    new PatientRestResource(req, res).getPatient();
                    break;
                case "DELETE":
                    // Delete a single patient by CF
                    new PatientRestResource(req, res).deletePatient();
                    break;
                default:
                    writeError(res, ErrorCode.PATIENT_UNSUPPORTED_OPERATION);
            }
        }
        else {
            return false;
        }

        return true;
    }


    /**
     * Checks whether the request if for a {@link Doctor} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for a {@code Doctor}; {@code false} otherwise.
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processDoctor(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String op = req.getRequestURI();
        String[] tokens = op.split("/");

        if (tokens.length<4 || !(tokens[3].equals("doctor") || tokens[3].equals("list"))){
            return false;
        }

        // doctor/list
        if(tokens.length == 5 && tokens[3].equals("doctor") && tokens[4].equals("list")){
            switch (req.getMethod()) {
                // Get a list of all active doctors
                case "GET":
                    new DoctorRestResource(req, res).getAllActiveDoctors();
                    break;
                default:
                    writeError(res, ErrorCode.DOCTOR_UNSUPPORTED_OPERATION);
            }
        }
        // GET or PUT or DELETE doctor/{doctor_cf}
        else if(tokens.length == 5 && tokens[3].equals("doctor")){
            switch (req.getMethod()) {
                case "GET":
                    // Get a single doctor by CF
                    new DoctorRestResource(req, res).getDoctor();
                    break;
                case "PUT":
                    // Update the status of a doctor
                    new DoctorRestResource(req, res).updateDoctorStatus();
                    break;
                case "DELETE":
                    // Delete a single doctor by CF
                    new DoctorRestResource(req, res).deleteDoctor();
                    break;
                default:
                    writeError(res, ErrorCode.DOCTOR_UNSUPPORTED_OPERATION);
            }
        }
        // doctor/
        else if(tokens.length == 4 && tokens[3].equals("doctor")){
            switch (req.getMethod()) {
                case "POST":
                    // Insert a new doctor in the database
                    new DoctorRestResource(req, res).createDoctor();
                    break;
                default:
                    writeError(res, ErrorCode.DOCTOR_UNSUPPORTED_OPERATION);
            }
        }
        else {
            return false;
        }

        return true;
    }
}
