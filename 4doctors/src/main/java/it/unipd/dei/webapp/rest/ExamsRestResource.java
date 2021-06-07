package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.dao.ExamDAO;
import it.unipd.dei.webapp.dao.MedicineDAO;
import it.unipd.dei.webapp.resource.Exam;
import it.unipd.dei.webapp.resource.Medicine;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.ResourceList;
import it.unipd.dei.webapp.utils.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Manages the REST API for the {@link Exam} resource.
 *
 * @author 4Doctors
 * @version 1.00
 * @since 1.00
 */
public final class ExamsRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Medicine} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public ExamsRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }



    /**
     * Lists all the medicines.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listExams() throws IOException {

        List<Exam> examsList  = null;
        Message m = null;

        try{
            // creates a new object for accessing the database and lists all the medicines
            examsList = new ExamDAO(con).getListExams();

            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(examsList).toJSON(res.getOutputStream());

        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.EXAMS_NOT_FOUND;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }

}
