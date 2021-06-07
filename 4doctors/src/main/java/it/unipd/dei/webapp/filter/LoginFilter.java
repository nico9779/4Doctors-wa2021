package it.unipd.dei.webapp.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter used to redirect the user into login page if he is not logged in
 */
public class LoginFilter extends HttpFilter {


    /**
     * Redirect the user into login page if he is not logged in
     * and try to access protected resources
     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/jsp/login.jsp";
        String adminLoginURI = req.getContextPath() + "/jsp/admin-login.jsp";
        String patient_homepageURI = req.getContextPath() + "/protected/jsp/patient/patient-homepage.jsp";
        String doctor_homepageURI = req.getContextPath() + "/protected/jsp/doctor/doctor-homepage.jsp";
        String admin_homepageURI = req.getContextPath() + "/protected/jsp/admin/admin-homepage.jsp";

        boolean isLoggedIn = (session != null && (session.getAttribute("cf") != null || session.getAttribute("username") != null));

        boolean isLoginRequest = (req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(adminLoginURI));

        if(isLoggedIn && isLoginRequest && session.getAttribute("role") != null){
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.

            String role = (String) session.getAttribute("role");
            if(role.equals("patient")){
                res.sendRedirect(patient_homepageURI); // Patient is logged in, go to the homepage.
            }
            else if(role.equals("doctor")){
                res.sendRedirect(doctor_homepageURI); // Doctor is logged in, go to the homepage.
            }
            else if(role.equals("admin")){
                res.sendRedirect(admin_homepageURI); // Admin is logged in, go to the homepage.
            }
            else{
                session.invalidate();
                res.sendRedirect(loginURI);
            }
        }
        else if(isLoggedIn || isLoginRequest){
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            chain.doFilter(req, res); // User is logged in, just continue request.
        }
        else
            res.sendRedirect(loginURI); // Not logged in, show login page.
    }
}
