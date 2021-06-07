package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.PrescriptionDAO;
import it.unipd.dei.webapp.resource.Prescription.Status;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import it.unipd.dei.webapp.utils.InputFormatException;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class to update a prescription status
 */
public class UpdatePrescriptionStatusServlet extends AbstractDatabaseServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Message m = null;

        try {

            String id = req.getParameter("id");
            Status status = Status.valueOf(req.getParameter("status"));

            //check parameters
            if(id == null) throw new InputFormatException("All parameters must be not NULL");

            if((status != Status.APPROVED) && (status != Status.PENDING) && (status != Status.REJECTED)) throw new InputFormatException("Status is "+ status +". Status parameter must be 'APPROVED', 'PENDING', or 'REJECTED'");

            new PrescriptionDAO(getDataSource().getConnection()).updatePrescriptionStatus(id, status);

            m = new Message("Prescription " + id + " successfully updated.");

        } catch (InputFormatException | IllegalArgumentException ex) {
            ErrorCode err = ErrorCode.INVALID_INPUT_PARAMETERS;
            res.setStatus(err.getHTTPCode());
            m = new Message(err.getErrorMessage(), err.getErrorCode(), ex.getMessage());
        } catch (SQLException ex) {
            ErrorCode err = ErrorCode.SERVER_ERROR;
            res.setStatus(err.getHTTPCode());
            m = new Message(err.getErrorMessage(), err.getErrorCode(), "Cannot update the prescription: unexpected error while accessing the database.");
        }

        // stores the message as a request attribute
        req.setAttribute("message", m);
        // forwards the control to the DoctorPrescriptionManagerServlet
        req.getRequestDispatcher("/prescription-manager").forward(req, res);

    }

}