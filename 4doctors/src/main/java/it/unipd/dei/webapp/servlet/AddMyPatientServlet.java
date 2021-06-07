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

public final class AddMyPatientServlet extends AbstractDatabaseServlet {

    /**
     * Add a patient to the followed list by the logged doctor
     *
     * @param req
     *            the HTTP request from the client.
     * @param resp
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String doctor_cf = (String) session.getAttribute("cf");

        String patient_cf = null;

        Message message = null;

        try {

            patient_cf = req.getParameter("patient_cf");

            if (patient_cf == null) {
                throw new InputFormatException("One or more input parameters are null");
            }

            new ListMyPatientsDAO(getDataSource().getConnection(), doctor_cf).addMyPatient(doctor_cf, patient_cf);

        } catch (InputFormatException ex) {
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            resp.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        } catch (IllegalArgumentException ex) {
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            resp.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot forward the request: gender type is wrong.");
        } catch (SQLException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            resp.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("/list-my-patients").forward(req, resp);

    }
}