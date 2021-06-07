package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Patient;
import it.unipd.dei.webapp.resource.Gender;


import java.sql.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListMyPatientsDAO{

    private static final String ListMyPatients_STATEMENT = "SELECT * FROM doctors.Segue INNER JOIN doctors.Paziente ON (paziente = cf) WHERE medico = ? AND attivo = '1';";

    private final Connection con;

    private final String cf;


    /**
     * Creates a new object for searching followed patients in the db
     *
     * @param con the connection to the database.
     * @param cf the cf of the olgged doctor
     */
    public ListMyPatientsDAO(final Connection con, final String cf) {
        this.con = con;
        this.cf = cf;
    }

    /**
     * Get the list of the followed patients
     *
     * @throws SQLException
     *             if any error occurs while accessing the database.
     */
    public List<Patient> listMyPatients() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Patient> patients = new ArrayList<>();


        try {

            String name;
            String surname;
            String email;
            String password;
            Gender gender;
            Date birthday;
            String birthplace;
            String city;
            String doctor;
            String patient;



            pstmt = con.prepareStatement(ListMyPatients_STATEMENT);
            pstmt.setString(1, cf);
            result = pstmt.executeQuery();




            while (result.next()){
                patient = result.getString("paziente");
                doctor = result.getString("medico");
                name = result.getString("nome");
                surname = result.getString("cognome");
                email= result.getString("email");
                password = result.getString("password");
                gender = Gender.valueOf(result.getString("sesso"));
                birthday = result.getDate("datanascita");
                birthplace = result.getString("luogonascita");
                city = result.getString("indirizzoresidenza");


                patients.add(new Patient(patient, name, surname, email, password, (java.sql.Date) birthday, birthplace, city, gender));
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
        return patients;
    }

    /**
     * Creates a new object to add followed patients in the db
     *
     * @param doctor_cf  the cf of the logged doctor
     * @param patient_cf the cf of the patient to add
     */

    public void addMyPatient(final String doctor_cf, final String patient_cf) throws SQLException {
        /**
         * The SQL statement to be executed
         */
        final String STATEMENT_1 = "SELECT * FROM doctors.Segue WHERE medico=? AND paziente=?";
        final String STATEMENT_2 = "UPDATE doctors.Segue SET attivo='1' WHERE medico=? AND paziente=?";
        final String STATEMENT_3 = "INSERT INTO doctors.Segue (medico, paziente, attivo) VALUES (?,?, True)";

        PreparedStatement pstmt_1 = null;
        PreparedStatement pstmt_2 = null;
        PreparedStatement pstmt_3 = null;
        ResultSet result = null;



        try {
            pstmt_1 = con.prepareStatement(STATEMENT_1);
            pstmt_1.setString(1, doctor_cf);
            pstmt_1.setString(2, patient_cf);
            result = pstmt_1.executeQuery();

            if(result.next()){
                pstmt_2 = con.prepareStatement(STATEMENT_2);
                pstmt_2.setString(1, doctor_cf);
                pstmt_2.setString(2, patient_cf);
                pstmt_2.executeUpdate();
            } else {
                pstmt_3 = con.prepareStatement(STATEMENT_3);

                pstmt_3.setString(1, doctor_cf);
                pstmt_3.setString(2, patient_cf);

                pstmt_3.execute();
            }

        } finally {
            if (pstmt_3 != null) {
                pstmt_3.close();
            }

            if (pstmt_1 != null) {
                pstmt_1.close();
            }

            if (pstmt_2 != null) {
                pstmt_2.close();
            }

            con.close();
        }

    }
}