package it.unipd.dei.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordDAO {

    /**
     * The SQL statement to be executed to set the user password
     */

    public static final String SET_PATIENT_PASSWORD_STATEMENT = "UPDATE doctors.paziente " +
            "SET password=md5(?) " +
            "WHERE cf=?";

    public static final String SET_DOCTOR_PASSWORD_STATEMENT = "UPDATE doctors.medico " +
            "SET password=md5(?) " +
            "WHERE cf=?";

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
    public PasswordDAO(final Connection con, final String cf) {
        this.con = con;
        this.cf = cf;
    }

    /**
     * Set the new password to the logged user
     *
     * @throws SQLException
     *             if any error occurs while setting the password
     */
    public void setPassword (String new_psw, String role) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String psw = null;

        try {
            if (role.equals("patient")) {
                pstmt = con.prepareStatement(SET_PATIENT_PASSWORD_STATEMENT);

            } else if (role.equals("doctor")) {
                pstmt = con.prepareStatement(SET_DOCTOR_PASSWORD_STATEMENT);

            }

            pstmt.setString(1, new_psw);
            pstmt.setString(2, this.cf);

            pstmt.executeUpdate();

        } finally {

            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
    }

}
