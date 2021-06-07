package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.MedicineDAO;
import it.unipd.dei.webapp.dao.PrescriptionDAO;
import it.unipd.dei.webapp.resource.Medicine;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Prescription;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;


/**
 * Creates a new prescription into the database.
 */
public final class PrescriptionRequestServlet extends AbstractDatabaseServlet {

    /**
     * Creates a new patient into the database.
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

        HttpSession session = req.getSession(false);
        String patient_cf = (String) session.getAttribute("cf");

        // request parameters
        String doctor_email;

        String description = null;

        String type = null;

        int duration = 0;

        /*
            identifier of exam or medicine, depends on the value of type
         */
        String code = null;

        int quantity = 0;


        //other prescription info
        String id = null;

        String doctor_cf = null;

        Date date = null;

        Prescription.Status status = Prescription.Status.PENDING;

        // model
        Prescription prescription = null;
        Message message = null;

        try{
            // retrieves the request parameters
            doctor_email = req.getParameter("doctor_email");
            description = req.getParameter("description");
            duration = Integer.parseInt(req.getParameter("numeroprestazioni"));
            date = new Date(System.currentTimeMillis());
            //code = req.getParameter("code");

            type = req.getParameter("type");

            //check if duration end quantity overcome their range
            if(duration<1 || duration>10){
                throw new InputFormatException("Validity times must be greater than 0 and lower than 11!");
            }

            // Check if some parameters are null
            if(doctor_email == null || patient_cf == null || description == null || type == null ) {
                throw new InputFormatException("One or more input parameters are null");
            }

            // Check if some parameters are empty
            if(doctor_email.isEmpty() || patient_cf.isEmpty() || description.isEmpty() || type.isEmpty() ) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            Prescription.TypeOfPrescription typeEnum = Prescription.TypeOfPrescription.valueOf(type);

            if(typeEnum == Prescription.TypeOfPrescription.FARMACO){
                quantity = Integer.parseInt(req.getParameter("qnt"));
                code = req.getParameter("codeM");
            }
            else{
                quantity = 1;
                code = req.getParameter("codeE");
            }
            if(quantity<1 || quantity>100){
                throw  new InputFormatException("The quantity must be in the range of 1-100");
            }

            // Removing leading and trailing white space
            doctor_email = doctor_email.trim();
            patient_cf = patient_cf.trim().toUpperCase();
            description = description.trim();
            code = code.trim().toUpperCase();

            // Check if cf has a bad format
            if(doctor_email.contains(" ")){
                throw new InputFormatException("The format of the parameter Codice Fiscale is wrong");
            }
            if(patient_cf.length() != 16 || patient_cf.contains(" ")){
                throw new InputFormatException("The format of the parameter Codice Fiscale is wrong");
            }

            prescription = new Prescription(id, doctor_cf, patient_cf, date, description, duration, typeEnum, status);

            // creates a new object for accessing the database and stores the patient
            new PrescriptionDAO(getDataSource().getConnection()).prescriptionRequest(prescription, doctor_email, code, quantity);

            message = new Message("Prescription successfully forwarded to the doctor.");

        } catch (InputFormatException | IllegalArgumentException ex) {
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        } catch (SQLException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        }

        req.setAttribute("message", message);
        req.setAttribute("cf", patient_cf);

        // forwards the control
        req.getRequestDispatcher("/protected/jsp/patient/prescription-home.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String patient_cf = (String) session.getAttribute("cf");

        List<Medicine> medicineList = null;
        Message message = null;

        try{
            // creates a new object for accessing the database and stores the patient
            MedicineDAO show = new MedicineDAO(getDataSource().getConnection());
            medicineList = show.getListMedicines();
            //message = new Message("Medicine succesfully searched!");

        } catch (SQLException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            //message = new Message(err.getErrorMessage(), err.getErrorCode(), "Cannot search for Medicine: unexpected error while accessing the database.");
        }

        req.setAttribute("medicineList", medicineList);
        //req.setAttribute("message", message);
        req.setAttribute("cf", patient_cf);

        // forwards the control
        req.getRequestDispatcher("/protected/jsp/patient/prescription-home.jsp").forward(req, res);
    }
}