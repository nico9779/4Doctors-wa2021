package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.resource.Doctor;
import it.unipd.dei.webapp.resource.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ProfileDAO {

    /**
     * The SQL statement to be executed to retrieve the user personal information
     */

    public static final String GET_PATIENT_PERSONAL_INFO_STATEMENT = "SELECT cf, nome, cognome, email, password, sesso, datanascita, luogonascita, indirizzoresidenza FROM doctors.Paziente " +
            "WHERE cf = ?";

    public static final String GET_DOCTOR_PERSONAL_INFO_STATEMENT = "SELECT cf, nome, cognome, email, password, sesso, datanascita, luogonascita, codiceasl, indirizzoresidenza FROM doctors.Medico " +
            "WHERE cf = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The patient to which the personal information (profile) are displayed
     */
    private final String cf;

    /**
     * Creates a new object to retrieve the personal information of the user identified by cf.
     *
     * @param con
     *            the connection to the database.
     * @param cf
     *            the patient to which the personal information are displayed.
     */
    public ProfileDAO(final Connection con, final String cf) {
        this.con = con;
        this.cf = cf;
    }

    /**
     * Get the patient to which we want to display the personal information
     *
     * @return the retrieved {@code Patient}
     * @throws SQLException
     *             if any error occurs while getting the patient
     */
    public Patient getPatient() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Patient patient = null;

        try {

            // Prepare the query statement with the correct values
            pstmt = con.prepareStatement(GET_PATIENT_PERSONAL_INFO_STATEMENT);
            pstmt.setString(1, this.cf);

            // Execute the query and get the first result in the result set
            rs = pstmt.executeQuery();
            rs.next();

            // Create the patient with the information retrieved from the db
            patient = new Patient(
                    rs.getString("cf"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDate("datanascita"),
                    rs.getString("luogonascita"),
                    rs.getString("indirizzoresidenza"),
                    Gender.valueOf(rs.getString("sesso"))
            );

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return patient;
    }

    /**
     * Get the doctor to which we want to display the personal information
     *
     * @return the retrieved {@code Doctor}.
     * @throws SQLException
     *             if any error occurs while getting the doctor
     */
    public Doctor getDoctor() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Doctor doctor = null;

        try {

            // Prepare the query statement with the correct values
            pstmt = con.prepareStatement(GET_DOCTOR_PERSONAL_INFO_STATEMENT);
            pstmt.setString(1, this.cf);

            // Execute the query and get the first result in the result set
            rs = pstmt.executeQuery();
            rs.next();

            // Create the patient with the information retrieved from the db
            doctor = new Doctor(
                    rs.getString("cf"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getDate("datanascita"),
                    rs.getString("luogonascita"),
                    rs.getString("indirizzoresidenza"),
                    Gender.valueOf(rs.getString("sesso")),
                    rs.getString("codiceasl")
            );

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return doctor;
    }
}
