package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.PatientDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.utils.ErrorCode;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Verify if the code inserted by the user is the same code sent in the email
 */
public final class VerificationServlet extends AbstractDatabaseServlet {

    /**
     * Verify if the code inserted by the user is the same code sent in the email
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
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Message message = null;

        // If session is null the control is back to the registration page
        if(session == null){
            ErrorCode ec = ErrorCode.VALIDATION_ERROR;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Error while validating the new user.");
            req.setAttribute("message", message);
            req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);

            return;
        }

        // Retrieve from the session the verification code sent and the patient object created
        String code = (String) session.getAttribute("verification_code");
        Patient patient = (Patient) session.getAttribute("patient");

        // If code or patient are null the control is back to the registration page
        if(code == null || code.isEmpty() || patient == null){
            ErrorCode ec = ErrorCode.VALIDATION_ERROR;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Error while validating the new user.");
            req.setAttribute("message", message);
            req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);

            session.invalidate();

            return;
        }

        // Check if the code inserted by the user is the same of the one sent in the email
        if(code.equals(req.getParameter("code"))){

            try {
                // creates a new object for accessing the database and stores the patient
                PatientDAO.createPatient(patient);
                message = new Message("Verification Done!");
                req.setAttribute("message", message);
                req.setAttribute("patient", patient);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

                session.invalidate();

                return;
            } catch (SQLException ex) {
                if (ex.getSQLState().equals("23505")) {
                    ErrorCode ec = ErrorCode.PATIENT_CONFLICT;
                    res.setStatus(ec.getHTTPCode());
                    message = new Message(ec.getErrorMessage(), ec.getErrorCode(), String.format("Cannot create the patient: patient with cf=%s or email=%s already exists.",
                            patient.getCf(), patient.getEmail()));
                } else {
                    ErrorCode ec = ErrorCode.PATIENT_NOT_CREATED;
                    res.setStatus(ec.getHTTPCode());
                    message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot create the patient: unexpected error while accessing the database.");
                }
            } catch (NamingException ex){
                ErrorCode ec = ErrorCode.SERVER_ERROR;
                res.setStatus(ec.getHTTPCode());
                message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Unexpected error while accessing the database.");
            }

            req.setAttribute("message", message);
            req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);
            session.invalidate();
        }
        else {
            // If the code is incorrect return to verify page
            ErrorCode ec = ErrorCode.WRONG_VERIFICATION_CODE;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Incorrect verification code. Please rewrite the code.");
            req.setAttribute("message", message);
            req.getRequestDispatcher("/jsp/verify.jsp").forward(req, res);
        }
    }
}
