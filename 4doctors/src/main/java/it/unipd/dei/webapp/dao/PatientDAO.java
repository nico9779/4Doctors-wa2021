package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Gender;
import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing all the useful operation for the resource patient in the database
 */
public class PatientDAO {

    /**
     * Stores a new patient into the database
     *
     * @param patient the patient to store in the database
     * @throws SQLException
     *             if any error occurs while storing the patient.
     */
    public static void createPatient(Patient patient) throws SQLException, NamingException {

        final String STATEMENT = "INSERT INTO doctors.Paziente (cf, nome, cognome, email, password, sesso, datanascita, luogonascita, " +
                "indirizzoresidenza) VALUES (?, ?, ?, ?, MD5(?), ?, ?, ?, ?)";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement pstmt = con.prepareStatement(STATEMENT)) {

            pstmt.setString(1, patient.getCf());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getSurname());
            pstmt.setString(4, patient.getEmail());
            pstmt.setString(5, patient.getPassword());
            pstmt.setObject(6, patient.getGender(), Types.OTHER);
            pstmt.setDate(7, patient.getBirthday());
            pstmt.setString(8, patient.getBirthplace());
            pstmt.setString(9, patient.getAddress());

            pstmt.execute();
        }
    }

    /**
     * Search a patient by codice fiscale
     *
     * @param cf the codice fiscale of a patient
     * @return the patient
     * @throws SQLException if any error occurs while finding the patient.
     */
    public static Patient searchPatientByCf(String cf) throws SQLException, NamingException {

        final String STATEMENT = "SELECT * FROM doctors.Paziente WHERE cf=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        Patient patient = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, cf);

            result = pstmt.executeQuery();

            if(result.next()){
                patient = new Patient(
                        result.getString("cf"),
                        result.getString("nome"),
                        result.getString("cognome"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getDate("datanascita"),
                        result.getString("luogonascita"),
                        result.getString("indirizzoresidenza"),
                        Gender.valueOf(result.getString("sesso")));
            }

            return patient;

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
     * Search a patient by email
     *
     * @param email the email of a patient
     * @return the patient
     * @throws SQLException if any error occurs while finding the patient.
     */
    public static Patient searchPatientByEmail(String email) throws SQLException, NamingException {

        final String STATEMENT = "SELECT * FROM doctors.Paziente WHERE email=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        Patient patient = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, email);

            result = pstmt.executeQuery();

            if(result.next()){
                patient = new Patient(
                        result.getString("cf"),
                        result.getString("nome"),
                        result.getString("cognome"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getDate("datanascita"),
                        result.getString("luogonascita"),
                        result.getString("indirizzoresidenza"),
                        Gender.valueOf(result.getString("sesso")));
            }

            return patient;

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
     * Returns the list of all patients in the database
     *
     * @return list of all patients
     */
    public static List<Patient> getAllPatients() throws SQLException, NamingException {

        final String STATEMENT = "SELECT * FROM doctors.Paziente";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Patient> patients = new ArrayList<>();

        try {
            con = DataSourceProvider.getDataSource().getConnection();
            pstmt = con.prepareStatement(STATEMENT);

            result = pstmt.executeQuery();

            while (result.next()){
                Patient patient = new Patient(
                        result.getString("cf"),
                        result.getString("nome"),
                        result.getString("cognome"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getDate("datanascita"),
                        result.getString("luogonascita"),
                        result.getString("indirizzoresidenza"),
                        Gender.valueOf(result.getString("sesso")));
                patients.add(patient);
            }
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
        return patients;
    }

    /**
     * Delete a patient from the database
     *
     * @param cf of the patient
     * @return the result of the operation
     */
    public static int deletePatient(String cf) throws SQLException, NamingException {

        final String STATEMENT = "DELETE FROM doctors.Paziente WHERE cf=?";

        try (Connection con = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement pstmt = con.prepareStatement(STATEMENT)) {

            pstmt.setString(1, cf);

            int resultDeletion = pstmt.executeUpdate();

            if (resultDeletion == 1){
                return 0;
            } else {
                return -1;
            }
        }
    }
}
