package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.LoginDAO;
import it.unipd.dei.webapp.dao.PasswordDAO;
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
 * Change the user password
 */
public class ChangePasswordServlet extends AbstractDatabaseServlet {
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
        String current_pws = null;
        String new_pws = null;
        String confirm_pws = null;

        Message message = null;

        try{
            current_pws = req.getParameter("current");
            new_pws = req.getParameter("new");
            confirm_pws = req.getParameter("confirm");

            // Check if some parameters are null
            if(current_pws == null || new_pws == null || confirm_pws == null) {
                throw new InputFormatException("One or more input parameters are null");
            }
            // Check if some parameters are empty
            if(current_pws.isEmpty() || new_pws.isEmpty() || confirm_pws.isEmpty()) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            boolean authenticated = LoginDAO.authenticateUser(user_cf, current_pws, role);

            if (authenticated){
                if (new_pws.equals(confirm_pws)) {
                    PasswordDAO pwsDao = new PasswordDAO(getDataSource().getConnection(), user_cf);
                    pwsDao.setPassword(new_pws, role);
                    message = new Message("Password successfully changed.");
                    req.setAttribute("message", message);
                    if (role.equals("patient")){
                        req.getRequestDispatcher("/protected/jsp/patient/patient-homepage.jsp").forward(req, res);
                    } else if (role.equals("doctor")){
                        req.getRequestDispatcher("/protected/jsp/doctor/doctor-homepage.jsp").forward(req, res);
                    }

                    return;

                } else{
                    throw new InputFormatException("Please make sure your passwords match");
                }
            } else{
                throw new InputFormatException("Password incorrect!");
            }

        } catch (InputFormatException ex){
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        } catch (SQLException | NamingException ex){
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            message = new Message(err.getErrorMessage(), err.getErrorCode(), "Cannot change password: unexpected error while accessing the database.");
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("/protected/jsp/patient/patient-change-password.jsp").forward(req, res);
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
