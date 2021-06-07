package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Check if a user is in the database
 */
public class LoginDAO {

    /**
     * The SQL statement to be executed for the patient
     */
    private static final String PATIENT_STATEMENT = "SELECT * FROM doctors.Paziente WHERE cf=? AND password=md5(?)";

    /**
     * The SQL statement to be executed for the medic
     */
    private static final String MEDIC_STATEMENT = "SELECT * FROM doctors.Medico WHERE cf=? AND password=md5(?)";

    /**
     * The SQL statement to be executed for the admin
     */
    private static final String ADMIN_STATEMENT = "SELECT * FROM doctors.Admin WHERE username=? AND password=md5(?)";

    /**
     * Check if a patient or a medic is in the database
     *
     * @throws SQLException
     *             if any error occurs while accessing the database.
     */
    public static boolean authenticateUser(String cf, String password, String role) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        boolean authenticated = false;

        try {
            con = DataSourceProvider.getDataSource().getConnection();

            // Select the statement based on the role
            if(role.equals("patient")){
                pstmt = con.prepareStatement(PATIENT_STATEMENT);
            }
            else if(role.equals("doctor")){
                pstmt = con.prepareStatement(MEDIC_STATEMENT);
            }
            else {
                if(con!=null){
                    con.close();
                }
                return false;
            }

            pstmt.setString(1, cf);
            pstmt.setString(2, password);
            result = pstmt.executeQuery();

            // Check if a user has been retrieved from database
            if(result.next()){
                authenticated = true;
            }

            return authenticated;

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if(result != null){
                result.close();
            }
            if(con!=null){
                con.close();
            }
        }
    }

    /**
     * Check if an admin is in the database
     *
     * @param username of the admin
     * @param password of the admin
     * @return true if admin is found in the database
     */
    public static boolean authenticateAdmin(String username, String password) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        boolean authenticated = false;

        try {
            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(ADMIN_STATEMENT);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            result = pstmt.executeQuery();

            // Check if an admin has been retrieved from database
            if(result.next()){
                authenticated = true;
            }

            return authenticated;

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if(result != null){
                result.close();
            }
            if(con!=null){
                con.close();
            }
        }
    }
}
