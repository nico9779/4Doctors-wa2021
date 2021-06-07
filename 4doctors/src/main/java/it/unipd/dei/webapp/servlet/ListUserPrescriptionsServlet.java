package it.unipd.dei.webapp.servlet;


import it.unipd.dei.webapp.dao.PrescriptionDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Prescription;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;


public final class ListUserPrescriptionsServlet extends AbstractDatabaseServlet {

    /**
     * Show all medicines available
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
        List<Prescription> pres  = null;
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
            PrescriptionDAO listPresc = new PrescriptionDAO(getDataSource().getConnection());
            pres = listPresc.listUserPrescriptions(cf, type);
            //message = new Message("Prescriptions succesfully searched into the database!");

        } catch (InputFormatException ex) {
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        }
        catch (SQLException ex) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Unexpected error while accessing the database.");
        }

        req.setAttribute("message", message);
        req.setAttribute("cf", cf);
        req.setAttribute("prescriptionsList", pres);
        //req.setAttribute("typeReq", type);

        // forwards the control
        req.getRequestDispatcher("/protected/jsp/patient/prescriptions-list.jsp").forward(req, res);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

