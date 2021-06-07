package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.PrescriptionDAO;
import it.unipd.dei.webapp.resource.Prescription;
import it.unipd.dei.webapp.resource.Prescription.Status;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet invoked to retrieve all prescriptions data needed for the doctor-prescription-manager page
 */
public class DoctorPrescriptionManagerServlet extends AbstractDatabaseServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Prescription> pending = null, past = new ArrayList<Prescription>();
        Message m = null;

        try {

            HttpSession session = req.getSession(false);
            String doctor = (String) session.getAttribute("cf");

            if(doctor == null) throw new InputFormatException("Session doctor cf is null");

            // retrieve all pending prescriptions related to the current doctor
            pending = new PrescriptionDAO(getDataSource().getConnection()).searchDoctorPrescriptionByStatus(doctor, Status.PENDING);

            // retrieve all nonpending prescriptions related to the current doctor (approved or rejected)
            past.addAll(new PrescriptionDAO(getDataSource().getConnection()).searchDoctorPrescriptionByStatus(doctor, Status.APPROVED));
            past.addAll(new PrescriptionDAO(getDataSource().getConnection()).searchDoctorPrescriptionByStatus(doctor, Status.REJECTED));

            // order all nonpending prescriptions descending w.r.t. the prescription date
            Collections.sort(past, new Comparator<Prescription>() {
                @Override
                public int compare(Prescription p1, Prescription p2) {
                    return p2.getDate().compareTo(p1.getDate());
                }
            });

        } catch (SQLException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            m = new Message(err.getErrorMessage(), err.getErrorCode(), "Cannot search for prescriptions: unexpected error while accessing the database.");
        } catch (InputFormatException ex) {
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(err.getHTTPCode());
            m = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        }

        // stores the pending, approved and rejected prescriptions list and the message as a request attribute
        req.setAttribute("pending", pending);
        req.setAttribute("past", past);
        req.setAttribute("message", m);

        // forwards the control to the prescription-manager JSP
        req.getRequestDispatcher("/protected/jsp/doctor/doctor-prescription-manager.jsp").forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
 
}