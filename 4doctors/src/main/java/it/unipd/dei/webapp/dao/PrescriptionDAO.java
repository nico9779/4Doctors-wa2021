package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class containing all the useful operation for the resource Prescription in the database
 */
public class PrescriptionDAO {

    /**
     * The connection to the database
     */
    Connection con = null;

    /**
     * Create an object containing all the useful operation for the resource Prescription in the database
     *
     * @param con The connection to the database
     */
    public PrescriptionDAO(final Connection con) {
        this.con = con;
    }

    /**
     * Make a new request of a prescription to a doctor
     * @param prescription The prescription to be forwarded
     * @param email The doctor's email
     * @param code The code of the medicine required
     * @param qnt The quantity required
     * @throws SQLException : if any error occurs while executing the query through JDBC
     */
    public void prescriptionRequest(final Prescription prescription, final String email, final String code, final int qnt) throws SQLException {
        /**
         * The SQL statement to be executed
         */
        final String STATEMENT = "INSERT INTO doctors.ricetta (id, medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ( ?, (SELECT cf FROM doctors.medico WHERE email=?), ?, ?, ?, ?, ?, ?)";

        final String STATEMENT_RICETTAESAME = "INSERT INTO doctors.ricettaesame (esame, ricetta) VALUES (?, ?)";

        final String STATEMENT_RICETTAFARMACO = "INSERT INTO doctors.farmaciricetta (farmaco, ricetta, qta) VALUES (?, ?, ?)";

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        UUID id = null;
        int index;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            id = UUID.randomUUID();
            index = 1;
            pstmt.setObject(index++, id);
            pstmt.setString(index++, email);
            pstmt.setString(index++, prescription.getPatient());
            pstmt.setDate(index++, prescription.getDate());
            pstmt.setString(index++, prescription.getDescription());
            pstmt.setInt(index++, prescription.getDuration());
            pstmt.setObject(index++, prescription.getType(), Types.OTHER);
            pstmt.setObject(index, prescription.getStatus(), Types.OTHER);

            pstmt.execute();

            if(prescription.getType()==Prescription.TypeOfPrescription.ESAME){
                pstmt1 = con.prepareStatement(STATEMENT_RICETTAESAME);
                index = 1;
                pstmt1.setString(index++, code);
                pstmt1.setObject(index, id);

                pstmt1.execute();

            }
            else if(prescription.getType()==Prescription.TypeOfPrescription.FARMACO){
                pstmt1 = con.prepareStatement(STATEMENT_RICETTAFARMACO);
                index = 1;
                pstmt1.setString(index++, code);
                pstmt1.setObject(index++, id);
                pstmt1.setInt(index, qnt);

                pstmt1.execute();

            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
                pstmt1.close();
            }

            con.close();
        }

    }

    /**
     * Get the list of all the prescriptions referred to an user
     * @param cf The user identifier
     * @param statusReq It can
     * @return
     * @throws SQLException if any error occurs while executing the query through JDBC
     */
    public List<Prescription> listUserPrescriptions(final String cf, final String statusReq) throws SQLException {
        /**
         * The SQL statement to be executed for retrieve the list of all prescriptions related to an user
         * Results are ordered in a way to show before the approved ones, the the pending and at the end
         * the rejected;
         */
        final String ListPrescriptions_STATEMENT = "SELECT * FROM doctors.ricetta WHERE paziente = ? ORDER BY status DESC  ";

        /**
         * The SQL statement for retrieve only the prescriptions which have a particular status given by the user
         */
        final String ListPrescriptions_STATEMENT_filtered = "SELECT * FROM doctors.ricetta WHERE paziente = ? AND status = ? ORDER BY data DESC  ";

        /**
         * Get the list of the prescriptions of a patient
         *
         * @throws SQLException
         *             if any error occurs while accessing the database.
         */
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Prescription> prescriptions = new ArrayList<>();

        try {
            String id;
            String doctor;
            String patient;
            Date date;
            String description;
            int duration;
            Prescription.TypeOfPrescription type;
            Prescription.Status status;

            if(statusReq.equals("all")) {
                pstmt = con.prepareStatement(ListPrescriptions_STATEMENT);
                pstmt.setString(1, cf);
                result = pstmt.executeQuery();
            }
            else if(statusReq.equals("pending")){
                Prescription.Status pending = Prescription.Status.PENDING;
                pstmt = con.prepareStatement(ListPrescriptions_STATEMENT_filtered);
                pstmt.setString(1, cf);
                pstmt.setObject(2, pending, Types.OTHER);
                result = pstmt.executeQuery();
            }
            else if(statusReq.equals("rejected")){
                Prescription.Status rejected = Prescription.Status.REJECTED;
                pstmt = con.prepareStatement(ListPrescriptions_STATEMENT_filtered);
                pstmt.setString(1, cf);
                pstmt.setObject(2, rejected, Types.OTHER);
                result = pstmt.executeQuery();
            }
            else if(statusReq.equals("approved")){
                Prescription.Status approved = Prescription.Status.APPROVED;
                pstmt = con.prepareStatement(ListPrescriptions_STATEMENT_filtered);
                pstmt.setString(1, cf);
                pstmt.setObject(2, approved, Types.OTHER);
                result = pstmt.executeQuery();
            }

            while (result.next()){
                id=result.getString("id");
                doctor=result.getString("medico");
                patient=result.getString("paziente");
                date=result.getDate("data");
                description=result.getString("descrizione");
                duration = result.getInt("numeroprestazioni");
                type= Prescription.TypeOfPrescription.valueOf(result.getString("tipo"));
                status= Prescription.Status.valueOf(result.getString("status"));

                prescriptions.add(new Prescription(id, doctor, patient, date, description, duration, type, status));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if(result != null){
                result.close();
            }

            con.close();
        }
        return prescriptions;
    }

    /**
     * Update the status of the prescription corresponding to the specified id
     *
     * @param id The prescription id (UUID format)
     * @param status The new status which will be assigned to the specified prescription
     * @throws SQLException SQLException when an error occurs while executing the query through JDBC
     */
    public void updatePrescriptionStatus(final String id, final Prescription.Status status) throws SQLException {

        final String STATEMENT = "UPDATE doctors.Ricetta SET status = ? WHERE id::text = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setObject(1, status, Types.OTHER);
            pstmt.setString(2, id);

            pstmt.executeUpdate();
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }

    /**
     * Returns a list containing all doctor prescription retrieved given a specified status
     *
     * @param doctor The doctor cf
     * @param status The status of the prescriptions which will be returned
     * @return A list containing all doctor prescription retrieved given a specified status
     * @throws SQLException when an error occurs while executing the query through JDBC
     */
    public List<Prescription> searchDoctorPrescriptionByStatus(final String doctor, final Prescription.Status status) throws SQLException {

        final String STATEMENT = "SELECT * FROM doctors.Ricetta WHERE medico = ? AND status = ? ORDER BY data DESC";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Prescription> prescriptions = new ArrayList<Prescription>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, doctor);
            pstmt.setObject(2, status, Types.OTHER);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                prescriptions.add(new Prescription(rs.getString("id"), rs.getString("medico"), rs.getString("paziente"), rs.getDate("data"), rs.getString("descrizione"), rs.getInt("numeroprestazioni"), Prescription.TypeOfPrescription.valueOf(rs.getString("tipo")), Prescription.Status.valueOf(rs.getString("status"))));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return prescriptions;
    }

}