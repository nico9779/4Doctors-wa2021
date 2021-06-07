package it.unipd.dei.webapp.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter used to redirect the user into unauthorized page if he is not logged in as patient
 */
public class PatientFilter extends HttpFilter {

    /**
     * Redirect the user into login page if he is not logged in
     * and try to access protected resources
     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/jsp/login.jsp";
        String unauthorizedPage = req.getContextPath() + "/jsp/unauthorized.jsp";

        boolean isLoggedIn = (session != null && (session.getAttribute("cf") != null || session.getAttribute("username") != null));

        if(isLoggedIn && session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("patient")) {
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                chain.doFilter(req, res); // Patient is logged in, just continue request.
            }
            else {
                res.sendRedirect(unauthorizedPage); // Not authorized, show the proper page
            }
        } else {
            res.sendRedirect(loginURI); // Not logged in, show login page.
        }
    }
}
