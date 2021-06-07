package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.dao.*;
import it.unipd.dei.webapp.resource.*;
import it.unipd.dei.webapp.utils.ErrorCode;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages the REST API for the {@link Medicine} resource.
 *
 * @author 4Doctors
 * @version 1.00
 * @since 1.00
 */
public final class MedicineRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Medicine} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public MedicineRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }



    /**
     * Lists all the medicines.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listMedicines() throws IOException {

        List<Medicine> medicineList  = null;
        Message m = null;

        try{
            // creates a new object for accessing the database and lists all the medicines
            medicineList = new MedicineDAO(con).getListMedicines();

            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(medicineList).toJSON(res.getOutputStream());

        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.MEDICINE_NOT_FOUND;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }

    /**
     *  Add a new medicine
     * @throws IOException
     */
    public void addMedicine() throws IOException{

        Message m = null;

        try{

            final Medicine medicine =  Medicine.fromJSON(req.getInputStream());

            // creates a new object for accessing the database and stores the medicine
            if(new MedicineDAO(con).addMedicine(medicine)){
                JSONObject resJSON = new JSONObject();
                resJSON.put("result", "successful");
                res.setStatus(HttpServletResponse.SC_CREATED);
                res.getWriter().write(resJSON.toString());

            } else {
                ErrorCode ec = ErrorCode.MEDICINE_NOT_CREATED;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
                ErrorCode ec = ErrorCode.MEDICINE_CONFLICT;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
            else {
                ErrorCode ec = ErrorCode.SERVER_ERROR;
                res.setStatus(ec.getHTTPCode());
                res.getWriter().write(ec.toJSON().toString());
            }
        }
    }

    /**
     * Get the list of all medicines required by a patients (specified by the parameter) and accepted by any doctors
     * @param patient_cf
     * @throws IOException
     */
    public void userMedicines(String patient_cf) throws IOException {

        List<Medicine> medicineList  = null;
        Message m = null;

        try{

            // creates a new object for accessing the database and lists all the medicines
            medicineList = new MedicineDAO(con).userMedicines(patient_cf);


            res.setStatus(HttpServletResponse.SC_OK);
            new ResourceList(medicineList).toJSON(res.getOutputStream());

        } catch (Throwable t) {
            ErrorCode ec = ErrorCode.MEDICINE_NOT_FOUND;
            res.setStatus(ec.getHTTPCode());
            res.getWriter().write(ec.toJSON().toString());
        }
    }
}
