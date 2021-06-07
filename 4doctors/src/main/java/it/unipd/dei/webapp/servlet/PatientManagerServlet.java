package it.unipd.dei.webapp.servlet;


import it.unipd.dei.webapp.dao.ListMyPatientsDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;


public final class PatientManagerServlet extends AbstractDatabaseServlet {

    /**
     * Show all patients followed by the logged doctor
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
        String cf = (String) session.getAttribute("cf");
        String type = req.getParameter("typeReq");

        // model
        List<Patient> patients  = null;
        Message message = null;

        try{
            // Check if some parameters are null
            if(cf == null) {
                throw new InputFormatException("One or more input parameters are null");
            }

            // Check if some parameters are empty
            if(cf.isEmpty()) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            // Removing leading and trailing white space and converting to upper case
            cf = cf.trim().toUpperCase();

            // Check if cf has a bad format
            if(cf.length() != 16 || cf.contains(" ")){
                throw new InputFormatException("The format of the parameter Codice Fiscale is wrong");
            }
            // creates a new object for accessing the database and stores the patient
            ListMyPatientsDAO listPatients = new ListMyPatientsDAO(getDataSource().getConnection(), cf);
            patients = listPatients.listMyPatients();
//            message = new Message("Patients succesfully searched into the database!");

        } catch (SQLException | InputFormatException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        }

        req.setAttribute("message", message);
        req.setAttribute("cf", cf);
        req.setAttribute("patientsList", patients);


        // forwards the control
        req.getRequestDispatcher("/protected/jsp/doctor/my-patients-list.jsp").forward(req, res);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}