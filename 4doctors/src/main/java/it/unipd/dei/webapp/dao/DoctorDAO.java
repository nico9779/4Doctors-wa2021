package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Doctor;
import it.unipd.dei.webapp.resource.Gender;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Class containing all the useful operations to handle doctor resource in the database
 *
 * @author 4Doctors
 */
public class DoctorDAO {

    /**
     * Stores a new doctor into the database
     *
     * @param doctor the doctor to store in the database
     * @throws SQLException
     *             if any error occurs while storing the doctor.
     */
    public static int createDoctor(Doctor doctor) throws SQLException, NamingException {

        final String STATEMENT = "INSERT INTO doctors.Medico (cf, nome, cognome, email, password, sesso, datanascita, luogonascita, " +
                "CodiceASL, indirizzoresidenza) VALUES (?, ?, ?, ?, MD5(?), ?, ?, ?, ?, ?)";

        try (Connection con  = DataSourceProvider.getDataSource().getConnection()) {

            PreparedStatement pstmt = con.prepareStatement(STATEMENT);

            pstmt.setString(1, doctor.getCf());
            pstmt.setString(2, doctor.getName());
            pstmt.setString(3, doctor.getSurname());
            pstmt.setString(4, doctor.getEmail());
            pstmt.setString(5, doctor.getPassword());
            pstmt.setObject(6, doctor.getGender(), Types.OTHER);
            pstmt.setDate(7, doctor.getBirthday());
            pstmt.setString(8, doctor.getBirthplace());
            pstmt.setString(9, doctor.getAslcode());
            pstmt.setString(10, doctor.getAddress());

            int result = pstmt.executeUpdate();

            if(result == 1){
                return 0;
            }
            else {
                return -1;
            }
        }
    }


    /**
     * Get the Doctor specified by the cf from the database
     *
     * @param cf of the doctor
     * @return doctor object requested
     * @throws SQLException if any error occurs while storing the doctor.
     * @throws NamingException if any error occurs while storing the doctor.
     */
    public static Doctor searchDoctorByCF(String cf) throws SQLException, NamingException {
        final String SINGLE_DOCTOR_STATEMENT = "SELECT * FROM doctors.Medico Where cf = ?";

        PreparedStatement pstmt_doctor = null;
        Connection con = null;
        ResultSet rs_doctor = null;

        Doctor doc = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();

            pstmt_doctor = con.prepareStatement(SINGLE_DOCTOR_STATEMENT);
            pstmt_doctor.setString(1, cf);

            rs_doctor = pstmt_doctor.executeQuery();

            //select the only doctor if any
            if(rs_doctor.next()){
                doc = new Doctor(
                        rs_doctor.getString("cf"),
                        rs_doctor.getString("nome"),
                        rs_doctor.getString("cognome"),
                        rs_doctor.getString("email"),
                        rs_doctor.getString("password"),
                        rs_doctor.getDate("datanascita"),
                        rs_doctor.getString("luogonascita"),
                        rs_doctor.getString("indirizzoresidenza"),
                        Gender.valueOf(rs_doctor.getString("sesso")),
                        rs_doctor.getString("CodiceASL")
                );
            }
        } finally {
            if (pstmt_doctor != null) {
                pstmt_doctor.close();
            }
            if(rs_doctor != null){
                rs_doctor.close();
            }

            con.close();
        }

        return doc;
    }



    /**
     * get a list of the currently active doctors of a given patient from the database
     *
     * @param patient_cf
     *              the cf of the patient of whom we want to get the doctors' list
     */
    public static List<Doctor> searchActiveDoctorsByPatientCF(String patient_cf) throws SQLException, NamingException{
        final String DOCTOR_LIST_STATEMENT = "SELECT medico FROM doctors.Segue WHERE paziente = ? AND attivo = '1'";

        PreparedStatement pstmt_list = null;
        Connection con = null;
        ResultSet rs_list = null;

        final List<Doctor> doctors = new ArrayList<Doctor>();

        try {

            con = DataSourceProvider.getDataSource().getConnection();
            pstmt_list = con.prepareStatement(DOCTOR_LIST_STATEMENT);

            pstmt_list.setString(1, patient_cf);

            rs_list = pstmt_list.executeQuery();

            while (rs_list.next()){
                //get the doctor from the server
                Doctor doc = DoctorDAO.searchDoctorByCF(rs_list.getString("medico"));

                doctors.add(doc);
            }

        } finally {
            if (pstmt_list != null) {
                pstmt_list.close();
            }
            if(rs_list != null){
                rs_list.close();
            }

            con.close();
        }

        return doctors;
    }

    /**
     * Get the list of all doctors stored in the database
     *
     * @return list of doctors
     */
    public static List<Doctor> getAllDoctors() throws SQLException, NamingException {

        final String STATEMENT = "SELECT * FROM doctors.Medico";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            con = DataSourceProvider.getDataSource().getConnection();
            pstmt = con.prepareStatement(STATEMENT);

            result = pstmt.executeQuery();

            while (result.next()){
                Doctor doctor = new Doctor(
                        result.getString("cf"),
                        result.getString("nome"),
                        result.getString("cognome"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getDate("datanascita"),
                        result.getString("luogonascita"),
                        result.getString("indirizzoresidenza"),
                        Gender.valueOf(result.getString("sesso")),
                        result.getString("CodiceASL"));
                doctors.add(doctor);
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
        return doctors;
    }

    /**
     * Get the list of all active doctors in the database
     *
     * @return list of all active doctors
     */
    public static List<Doctor> getAllActiveDoctors() throws SQLException, NamingException {

        final String STATEMENT = "SELECT medico FROM doctors.Segue WHERE attivo = '1'";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Doctor> doctors = new ArrayList<>();
        HashSet<String> doctors_cf_set = new HashSet<>();

        try {
            con = DataSourceProvider.getDataSource().getConnection();
            pstmt = con.prepareStatement(STATEMENT);
            result = pstmt.executeQuery();

            while(result.next()){
                String doctor_cf = result.getString("medico");
                // Avoid to retrieve the same doctor
                if(!doctors_cf_set.contains(doctor_cf)){
                    doctors_cf_set.add(doctor_cf);
                    Doctor doctor = searchDoctorByCF(doctor_cf);
                    doctors.add(doctor);
                }

            }

            return doctors;

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
     * Delete a doctor from the database
     *
     * @param cf of the doctor
     * @return the result of the operation
     */
    public static int deleteDoctor(String cf) throws SQLException, NamingException {

        final String STATEMENT = "DELETE FROM doctors.Medico WHERE cf=?";

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

    /**
     * Update the status of the doctor making it no more active
     *
     * @param doctor_cf of the doctor
     * @return the result of the operation
     */
    public static int updateDoctorStatus(String doctor_cf) throws SQLException, NamingException {

        final String STATEMENT = "UPDATE doctors.Segue SET attivo = '0' WHERE medico = ?";

        try(Connection con = DataSourceProvider.getDataSource().getConnection();
            PreparedStatement pstmt = con.prepareStatement(STATEMENT)) {

            // If doctor to update is not found return
            if(searchDoctorByCF(doctor_cf) == null){
                return -2;
            }

            pstmt.setString(1, doctor_cf);

            int resultUpdate = pstmt.executeUpdate();

            if (resultUpdate >= 1){
                return 0;
            } else {
                return -1;
            }
        }
    }
}
