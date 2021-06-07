package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.EmailDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Change the user e-mail
 */
public class ChangeEmailServlet extends AbstractDatabaseServlet {
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
        String role = (String) session.getAttribute("role");
        String user_cf = (String) session.getAttribute("cf");


        // request parameters
        String current_email = null;
        String new_email = null;

        Message message = null;

        try{
            current_email = req.getParameter("current");
            new_email = req.getParameter("new");

            // Check if some parameters are null
            if(current_email == null || new_email == null) {
                throw new InputFormatException("One or more input parameters are null");
            }
            // Check if some parameters are empty
            if(current_email.isEmpty() || new_email.isEmpty()) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            EmailDAO emailDao = new EmailDAO(getDataSource().getConnection(), user_cf);
            emailDao.setEmail(new_email, current_email, role);
            message = new Message("E-mail successfully updated.");
            req.setAttribute("message", message);
            if (role.equals("patient")){
                req.getRequestDispatcher("/protected/jsp/patient/patient-homepage.jsp").forward(req, res);
            } else if (role.equals("doctor")){
                req.getRequestDispatcher("/protected/jsp/doctor/doctor-homepage.jsp").forward(req, res);
            }
            return;

        } catch (InputFormatException ex){
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        } catch (SQLException ex){
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), "Cannot update e-mail: unexpected error while accessing the database.");
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("/protected/jsp/patient/patient-change-mail.jsp").forward(req, res);
    }

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
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException{
        doGet(req,res);
    }

}
