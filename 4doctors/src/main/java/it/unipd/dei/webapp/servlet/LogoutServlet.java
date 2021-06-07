package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.resource.Message;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Log out the user from the webapp
 */
public final class LogoutServlet extends AbstractDatabaseServlet {

    /**
     * Log out a user
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException if any error occurs in the client/server communication.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Fetch session object

        if(session!=null) {
            String role = (String) session.getAttribute("role");
            session.invalidate(); // Removes all session attributes bound to the session
            Message message = new Message("You have logged out successfully");
            req.setAttribute("message", message);
            // forwards the control back to the login
            if(role != null && role.equals("admin")){
                req.getRequestDispatcher("/jsp/admin-login.jsp").forward(req, res);
            }
            else {
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }
        }
    }
}
