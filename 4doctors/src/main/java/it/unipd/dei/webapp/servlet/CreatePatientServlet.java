package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.resource.Gender;

import it.unipd.dei.webapp.dao.PatientDAO;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Creates a new patient into the database.
 */
public final class CreatePatientServlet extends AbstractDatabaseServlet {

    /**
     * Creates a new patient into the database.
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
        String name = null;
        String surname = null;
        String email = null;
        String password = null;
        String retype_password = null;
        String birthday = null;
        String birthplace = null;
        String address = null;
        String gender = null;

        // model
        Patient patient  = null;
        Message message = null;

        try{
            // retrieves the request parameters
            cf = req.getParameter("cf");
            name = req.getParameter("name");
            surname = req.getParameter("surname");
            email = req.getParameter("email");
            password = req.getParameter("password");
            retype_password = req.getParameter("retype_password");
            birthday = req.getParameter("birthday");
            birthplace = req.getParameter("birthplace");
            address = req.getParameter("address");
            gender = req.getParameter("gender");

            // Check if some parameters are null
            if(cf == null || name == null || surname == null || email == null || password == null ||
                   retype_password == null || birthday == null || birthplace == null || address == null || gender == null) {
                throw new InputFormatException("One or more input parameters are null");
            }

            // Removing leading and trailing white space
            cf = cf.trim().toUpperCase();
            name = name.trim();
            surname = surname.trim();
            email = email.trim();
            birthplace = birthplace.trim();
            address = address.trim();

            // Check if some parameters are empty
            if(cf.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    retype_password.isEmpty() ||birthday.isEmpty() || birthplace.isEmpty() || address.isEmpty() || gender.isEmpty()) {
                throw new InputFormatException("One or more input parameters are empty");
            }

            // Check if cf has a bad format
            if(cf.length() != 16 || cf.contains(" ")){
                throw new InputFormatException("The format of the parameter Codice Fiscale is wrong");
            }

            // Check if the password is equal to the retyped password
            if(!password.equals(retype_password)){
                throw new InputFormatException("The passwords are not corresponding");
            }

            // Parse the data retrieved from the form and create a new Date object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdayDate = new Date(sdf.parse(birthday).getTime());

            // Create a new Gender enum with the gender type retrieved from the form
            Gender genderEnum = Gender.valueOf(gender);

            // Check if a patient with cf is already stored in database before sending email
            if(PatientDAO.searchPatientByCf(cf) != null){
                ErrorCode ec = ErrorCode.PATIENT_CONFLICT;
                res.setStatus(ec.getHTTPCode());
                message = new Message(ec.getErrorMessage(), ec.getErrorCode(), String.format("Duplicate patient with cf=%s", cf));
                req.setAttribute("message", message);
                req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);
                return;
            }

            // Check if a patient with email is already stored in database before sending email
            if(PatientDAO.searchPatientByEmail(email) != null){
                ErrorCode ec = ErrorCode.PATIENT_CONFLICT;
                res.setStatus(ec.getHTTPCode());
                message = new Message(ec.getErrorMessage(), ec.getErrorCode(), String.format("Duplicate patient with email=%s", email));
                req.setAttribute("message", message);
                req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);
                return;
            }

            // creates a new patient from the request parameters
            patient = new Patient(cf, name, surname, email, password, birthdayDate, birthplace, address, genderEnum);

            // Generate a random verification code
            Random random = new Random();
            int verificationCode = random.nextInt(999999);

            // Send email to verify account
            sendEmail(name, email, verificationCode);

            // Put in the session object the verification code and the patient object
            HttpSession session = req.getSession();
            session.setAttribute("verification_code", Integer.toString(verificationCode));
            session.setAttribute("patient", patient);

            // forwards the control to the verification page
            req.getRequestDispatcher("/jsp/verify.jsp").forward(req, res);

            return;

        } catch (InputFormatException ex) {
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot create the patient: "+ex.getMessage());
        } catch (ParseException ex) {
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot create the patient: error while parsing the data of the birthday.");
        } catch (IllegalArgumentException ex){
            ErrorCode ec = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Cannot create the patient: gender type is wrong.");
        } catch (EmailException ex) {
            ErrorCode ec = ErrorCode.EMAIL_NOT_SENT;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), String.format("Error while sending verification code to %s.", email));
        } catch (SQLException | NamingException ex) {
            ErrorCode ec = ErrorCode.SERVER_ERROR;
            res.setStatus(ec.getHTTPCode());
            message = new Message(ec.getErrorMessage(), ec.getErrorCode(), "Unexpected error while accessing the database.");
        }

        // stores the message as a request attribute
        req.setAttribute("message", message);

        // forwards the control to the patient_registration JSP
        req.getRequestDispatcher("/jsp/patient_registration.jsp").forward(req, res);
    }

    /**
     * Send an email containing the verification code to the new registered user
     *
     * @param userName  name of the user
     * @param userEmail email of the user
     * @param verificationCode  verification code used to register
     * @throws EmailException   if any errors while sending email to user
     */
    private void sendEmail(String userName, String userEmail, int verificationCode) throws EmailException {

        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("4doctorswebapp@gmail.com", "4mwlihz5"));
        email.setSSLOnConnect(true);
        email.setFrom("4doctorswebapp@gmail.com");
        email.setSubject("4Doctors - Verification code");
        email.setMsg("Welcome "+userName+" to 4Doctors, your verification code is: "+verificationCode);
        email.addTo(userEmail);
        email.send();
    }

}
