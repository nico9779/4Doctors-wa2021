package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.DoctorDAO;
import it.unipd.dei.webapp.dao.MedExDAO;
import it.unipd.dei.webapp.dao.MedicalExaminationDAO;
import it.unipd.dei.webapp.resource.Doctor;
import it.unipd.dei.webapp.resource.MedicalExamination;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.BookingTime;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Allow the Patient to view a list of his/her own reserved
 * and past examinations, and to reserve new ones.
 *
 * @author Pietro Balzan
 */
public class PatientMedicalExaminationsServlet extends AbstractDatabaseServlet {

    /**
     * Loads page and list of examinations
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String patient_cf = (String) session.getAttribute("cf");

        Cookie patientCF = new Cookie("patientCF", patient_cf);
        res.addCookie(patientCF);

        //get current date as sql.date
        Date date = new java.sql.Date(new java.util.Date().getTime());

        // model
        ArrayList<List<MedicalExamination>> examinations = null;
        List<MedicalExamination> futureExaminations = null;
        List<MedicalExamination> pastExaminations = null;
        List<Doctor> patientDoctors = null;
        Message m = null;

        //get times (empty arraylist as parameter to get standard list with all times not booked)
        ArrayList<BookingTime> bookingTimeList = BookingTime.TimesList(new ArrayList<>());

        try{
            //get doctors of the patient to set list items
            patientDoctors = DoctorDAO.searchActiveDoctorsByPatientCF(patient_cf);

            //get lists of future and past examination of the logged patient
            MedicalExaminationDAO medicalExaminationDAO =
                    new MedicalExaminationDAO(getDataSource().getConnection(), patient_cf);

            //retrieve examinations from the db
            examinations = medicalExaminationDAO.getMedicalExaminations();

            pastExaminations = examinations.get(0);
            Collections.reverse(pastExaminations);
            futureExaminations = examinations.get(1);

            //m = new Message("Examinations successfully retrieved.");
        }
        catch (SQLException | NamingException ex) {
            ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_FOUND;
            res.setStatus(ec.getHTTPCode());
            m = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        }

        // return JSP page as result
        req.setAttribute("futureExaminationsList", futureExaminations);
        req.setAttribute("pastExaminationsList", pastExaminations);
        req.setAttribute("patientDoctors", patientDoctors);
        req.setAttribute("timeSelection", bookingTimeList);
        req.setAttribute("message", m);

        // forwards the control back to the patient-medical-examinations JSP
        req.getRequestDispatcher("/protected/jsp/patient/patient-medical-examinations.jsp").forward(req, res);

    }



    /**
     * Create a new examination (visita) into the database and reload the page.
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //request parameters

        HttpSession session = req.getSession(false);
        String patient_cf = (String) session.getAttribute("cf");

        //al parameters selected in the form
        String doctor_cf = null;
        Date date = null;
        Time time = null;
        String outcome = "--"; // it will be modified later by the doctor after the examination

        //get current date as sql.date
        Date currentDate = new java.sql.Date(new java.util.Date().getTime());

        // model
        MedicalExamination newExamination  = null;
        Message m = null;

        try{
            //convert date and time strings to java.sql format for storage in the database
            java.util.Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateselect"));
            java.util.Date selectedTime = new SimpleDateFormat("HH:mm").parse(req.getParameter("timeToSelect"));

            date =  new java.sql.Date(selectedDate.getTime());
            time =  new java.sql.Time(selectedTime.getTime());

            //get selected doctor's cf
            doctor_cf = req.getParameter("patientDoctor");

            //check date is in the future (can only reserve future examinations
            if(date.compareTo(currentDate) < 0 ) {
                throw new InputFormatException("Can only reserve examinations at future dates.");
            }

            // creates a new medical examination from the request parameters
            newExamination = new MedicalExamination(doctor_cf, patient_cf, date, time, outcome);
            // stores the new examination
            MedExDAO.createMedicalExamination(newExamination);


            //m = new Message("Examination successfully added to the database.");

        }
        catch (ParseException | InputFormatException ex){
            ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_INVALID_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            m = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        }
        catch (SQLException ex) {
            ErrorCode ec = ErrorCode.MEDICAL_EXAMINATION_NOT_CREATED;
            res.setStatus(ec.getHTTPCode());
            m = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        } catch (NamingException ex) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            m = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        }

        req.setAttribute("message", m);
        //return JSP page with all examinations lists
        doGet(req, res);
    }
}
