package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.LoginDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Log the user in the webapp
 */
public final class LoginServlet extends AbstractDatabaseServlet {

    /**
     * Log a user into the webapp.
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
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String cf = null;
        String password = null;
        String role = null;

        // model
        Message message = null;

        try{
            // retrieves the request parameters
            cf = req.getParameter("cf");
            password = req.getParameter("password");
            role = req.getParameter("role");

            // Check if some parameters are null
            if(cf == null || password == null || role == null) {
                throw new InputFormatException("One or more input parameters are null");
            }

            // Removing leading and trailing white space and converting to upper case
            cf = cf.trim().toUpperCase();

            // Check if some parameters are empty
            if(cf.isEmpty() || password.isEmpty() || role.isEmpty()) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            // Check if cf has a bad format
            if(cf.length() != 16 || cf.contains(" ")){
                throw new InputFormatException("The format of the parameter Codice Fiscale is wrong");
            }

            // Log the user
            boolean authenticated = LoginDAO.authenticateUser(cf, password, role);

            if(authenticated){
                HttpSession session = req.getSession();
                session.setAttribute("cf", cf);
                session.setAttribute("role", role);
                //message = new Message(String.format("User %s successfully logged in.", cf));
                // stores the message as a request attribute
                req.setAttribute("message", message);

                // forwards the control to the homepage
                if (role.equals("patient")){
                    req.getRequestDispatcher("/protected/jsp/patient/patient-homepage.jsp").forward(req, res);
                }
                else if (role.equals("doctor")){
                    req.getRequestDispatcher("/protected/jsp/doctor/doctor-homepage.jsp").forward(req, res);
                }
            }
            else {
                ErrorCode ec = ErrorCode.WRONG_CREDENTIAL;
                res.setStatus(ec.getHTTPCode());
                message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Error while authenticating the user");
                req.setAttribute("message", message);

                // forwards the control to the login
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }

            return;

        } catch (InputFormatException ex) {
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), ex.getMessage());
        } catch (SQLException | NamingException ex) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Unexpected error while accessing the database.");
        }

        req.setAttribute("message", message);

        // forwards the control to the login
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
    }
}
