package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.dao.MedExDAO;
import it.unipd.dei.webapp.resource.BookingTime;
import it.unipd.dei.webapp.resource.MedicalExamination;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.ResourceList;
import it.unipd.dei.webapp.utils.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MedicalExaminationRestResource extends RestResource{

    /**
     * Creates a new REST resource for Medical Examinations resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public MedicalExaminationRestResource(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(req, res, con);
    }






    /**
     * creates a new Medical Examination into the database
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void createMedicalExamination() throws IOException{
        MedicalExamination mE = null;
        Message m = null;

        try{
            final MedicalExamination medicalExamination = MedicalExamination.fromJSON(req.getInputStream());

            //check if date is valid (has to be in the future)
            Date currentDate = new java.sql.Date(new java.util.Date().getTime());
            //check time is valid
            BookingTime bookingTime = new BookingTime(medicalExamination.getTime(), true);

            //invalid parameters
            if(medicalExamination.getDate().compareTo(currentDate) < 0 ||  bookingTime.getMin() == null
                    ||  bookingTime.getHour() == null) {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_INVALID_PARAMETERS;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
                return;
            }

            // stores the examination
            mE = MedExDAO.createMedicalExamination(medicalExamination);

            if(mE != null) {
                res.setStatus(HttpServletResponse.SC_CREATED);
                mE.toJSON(res.getOutputStream());
            } else {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_CREATED;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_CONFLICT;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            } else {
                ErrorCode ec = ErrorCode.SERVER_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        }
    }






    /**
     * Reads a medical examination from the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void readMedicalExamination() throws IOException {

        MedicalExamination mE = null;
        Message m = null;

        try{
            // parse the URI path to extract the parameters
            String path = req.getRequestURI();
            String examParam = path.substring(path.lastIndexOf("medicalExamination") + 18+1); //+1 to skip the first "/"

            String delim = "[/]"; //split the string at the slashes
            String[] params = examParam.split(delim);

            if(params.length != 3){
                //failed to split
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_BAD_URI;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
                return;
            }

            final String doctor_cf = params[0];
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[1]);
            final Date date = new java.sql.Date(utilDate.getTime());
            java.util.Date utilTime = new SimpleDateFormat("HH:mm").parse(params[2]);
            final Time time = new java.sql.Time(utilTime.getTime());

            // creates a new object for accessing the database and reads the examination
            mE = MedExDAO.readMedicalExamination(doctor_cf, date, time);

            if(mE != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                mE.toJSON(res.getOutputStream());
            } else {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }






    /**
     * Updates a medical examination in the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void updateMedicalExaminationOutcome() throws IOException {

        MedicalExamination mE = null;
        Message m = null;

        try{

            // parse the URI path to extract the parameters
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("medicalExamination") + 18+1); //+1 to skip the first "/"

            String delim = "[/]"; //split the string at the slashes
            String[] params = path.split(delim);

            if(params.length != 3){
                //failed to split
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_BAD_URI;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
                return;
            }

            //get request parameters
            final String doctor_cf = params[0];
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[1]);
            final Date date = new java.sql.Date(utilDate.getTime());
            java.util.Date utilTime = new SimpleDateFormat("HH:mm").parse(params[2]);
            final Time time = new java.sql.Time(utilTime.getTime());

            final MedicalExamination updatedExamination = MedicalExamination.fromJSON(req.getInputStream());

            // creates a new object for accessing the database and updates the medical examination
            mE = MedExDAO.updateMedicalExaminationOutcome(updatedExamination);

            if(mE != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                mE.toJSON(res.getOutputStream());
            } else {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }






    /**
     * Searches medical examinations depending on the patient.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void searchMedicalExaminationByPatient()  throws IOException {

        ArrayList<MedicalExamination> medicalExaminationList = null;
        Message m = null;
        String patientCf = "epic";

        try{
            // parse the URI path to extract the patient's CF
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("patient") + 7);

            //get cf
            //final String patientCf = path.substring(1);
            patientCf = path.substring(1);

            // creates a new object for accessing the database and search the medical examination
            medicalExaminationList = MedExDAO.getMedicalExaminations(patientCf);

            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(medicalExaminationList).toJSON(res.getOutputStream());

        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }






    /**
     * Deletes a medical examination from the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void deleteMedicalExamination() throws IOException {

        MedicalExamination mE = null;
        Message m = null;

        try{

            // parse the URI path to extract the examination
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("medicalExamination") + 18+1); //+1 to skip the first "/"

            String delim = "[/]"; //split the string at the slashes
            String[] params = path.split(delim);

            if(params.length != 3){
                //failed to split
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_BAD_URI;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
                return;
            }

            //get request parameters
            final String doctor_cf = params[0];
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(params[1]);
            final Date date = new java.sql.Date(utilDate.getTime());
            java.util.Date utilTime = new SimpleDateFormat("HH:mm").parse(params[2]);
            final Time time = new java.sql.Time(utilTime.getTime());

            // delete the medical examination
            mE = MedExDAO.deleteMedicalExamination(doctor_cf, date, time);

            if(mE != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                mE.toJSON(res.getOutputStream());
            } else {
                ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_FOUND;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }

}
