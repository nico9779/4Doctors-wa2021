package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.utils.InputFormatException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.unipd.dei.webapp.utils.InputFormatException;



public class EmailDAO {

    /**
     * The SQL statement to be executed to set the user password
     */

    public static final String SET_PATIENT_EMAIL_STATEMENT = "UPDATE doctors.paziente " +
            "SET email=? " +
            "WHERE cf=?";

    public static final String CHECK_PATIENT_EMAIL = "SELECT * FROM doctors.paziente " +
            "WHERE cf=? AND " +
            "email=?";

    public static final String SET_DOCTOR_EMAIL_STATEMENT = "UPDATE doctors.medico " +
            "SET email=? " +
            "WHERE cf=?";

    public static final String CHECK_DOCTOR_EMAIL = "SELECT * FROM doctors.medico " +
            "WHERE cf=? AND " +
            "email=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The patient to which the the password is canged
     */
    private final String cf;

    /**
     * Creates a new object for changing the password of the logged patient.
     *
     * @param con
     *            the connection to the database.
     * @param cf
     *            the patient which wants to change password.
     */
    public EmailDAO(final Connection con, final String cf) {
        this.con = con;
        this.cf = cf;
    }

    /**
     * Set the new password to the logged user
     *
     * @throws SQLException
     *             if any error occurs while setting the password
     */
    public void setEmail (String new_email, String current_email, String role) throws SQLException, InputFormatException {
        PreparedStatement pstmt = null;
        ResultSet result = null;

        try {

            if (role.equals("patient")) {
                pstmt = con.prepareStatement(CHECK_PATIENT_EMAIL);

            } else if (role.equals("doctor")) {
                pstmt = con.prepareStatement(CHECK_DOCTOR_EMAIL);
            }

            pstmt.setString(1, this.cf);
            pstmt.setString(2, current_email);

            result = pstmt.executeQuery();

            // If there exists a user whose mail is current_email
            if(result.next()){

                pstmt = null;
                if (role.equals("patient")) {
                    pstmt = con.prepareStatement(SET_PATIENT_EMAIL_STATEMENT);

                } else if (role.equals("doctor")) {
                    pstmt = con.prepareStatement(SET_DOCTOR_EMAIL_STATEMENT);
                }

                pstmt.setString(1, new_email);
                pstmt.setString(2, this.cf);

                pstmt.executeUpdate();

            } else{
                throw new InputFormatException("Current email incorrect!");
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
    }

}

