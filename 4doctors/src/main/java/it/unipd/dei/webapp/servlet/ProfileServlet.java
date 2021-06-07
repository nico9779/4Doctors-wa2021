package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.ProfileDAO;
import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.resource.Doctor;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Collect personal information of the logged patient
 */
public final class ProfileServlet extends AbstractDatabaseServlet {

    /**
     *
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
            throws ServletException, IOException{

        HttpSession session = req.getSession(false);
        String user_cf = (String) session.getAttribute("cf");
        req.setAttribute("cf", user_cf);

        String role = (String) session.getAttribute("role");

        Message m = null;
        String query = null;

        // retrieves the request parameter
        String requestType = req.getParameter("type");


        // If the request is not null...
        if (requestType != null){

            if (requestType.equals("Profile")){

                if (role.equals("patient")){

                    Patient patient = null;
                    try{
                        ProfileDAO profile = new ProfileDAO(getDataSource().getConnection(), user_cf);
                        patient = profile.getPatient();

                        //m = new Message("Profile successfully entered.\n Request type: " + requestType);
                    } catch (SQLException ex) {
                        ErrorCode ec = ErrorCode.SERVER_ERROR;
                        res.setStatus(ec.getHTTPCode());
                        m = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot search for personal information: unexpected error while accessing the database.\n The query which will be performed is "+query+ ", Request type: " + requestType);
                    }

                    req.setAttribute("personalInfo", patient);
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/protected/jsp/patient/patient-profile.jsp").forward(req, res);
                }
                else if (role.equals("doctor")){

                    Doctor doctor = null;

                    try{
                        ProfileDAO profile = new ProfileDAO(getDataSource().getConnection(), user_cf);
                        doctor = profile.getDoctor();

                        //m = new Message("Profile successfully entered.\n Request type: " + requestType);
                    } catch (SQLException ex) {
                        ErrorCode ec = ErrorCode.SERVER_ERROR;
                        res.setStatus(ec.getHTTPCode());
                        m = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot search for personal information: unexpected error while accessing the database.\n The query which will be performed is "+query+ ", Request type: " + requestType);

                    }

                    req.setAttribute("personalInfo", doctor);
                    req.setAttribute("message", m);
                    req.getRequestDispatcher("/protected/jsp/doctor/doctor-profile.jsp").forward(req, res);
                }
            }

        } else{ // If the request type is null
            throw new IOException();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doGet(req,res);
    }
}
