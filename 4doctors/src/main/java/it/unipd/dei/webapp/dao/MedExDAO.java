package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.MedicalExamination;
import it.unipd.dei.webapp.utils.DataSourceProvider;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing all the useful operations to handle Medical Examination resources in the database
 *
 * @author 4Doctors
 */
public class MedExDAO {

    /**
     * Stores a new medical examination into the database
     *
     * @throws SQLException
     *             if any error occurs while storing the examination.
     */
    public static MedicalExamination createMedicalExamination(MedicalExamination med_ex) throws SQLException, NamingException {

        final String STATEMENT = "INSERT INTO doctors.Visita (medico, paziente, data, ora, esito) VALUES (?, ?, ?, ?, ?) RETURNING *";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        //the created examination
        MedicalExamination medEx = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, med_ex.getDoctor_cf());
            pstmt.setString(2, med_ex.getPatient_cf());
            pstmt.setDate(3, med_ex.getDate());
            pstmt.setTime(4, med_ex.getTime());
            pstmt.setString(5, med_ex.getOutcome());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                medEx = new MedicalExamination( rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito"));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return medEx;
    }



    /**
     * Reads a medical examination from the database
     *
     * @throws SQLException
     *             if any error occurs while storing the examination.
     */
    public static MedicalExamination readMedicalExamination(String doctor_cf, Date date, Time time) throws NamingException, SQLException {

        final String STATEMENT = "SELECT * FROM doctors.Visita WHERE medico = ? AND data = ? AND ora = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        //the created examination
        MedicalExamination medEx = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, doctor_cf);
            pstmt.setDate(2, date);
            pstmt.setTime(3, time);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                medEx = new MedicalExamination( rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito"));
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return medEx;
    }



    /**
     * Updates a medical examination from the database
     *
     * @throws SQLException
     *             if any error occurs while updating the outcome of the medical examination.
     */
    public static MedicalExamination updateMedicalExaminationOutcome(MedicalExamination examination) throws NamingException, SQLException {

        final String STATEMENT = "UPDATE doctors.Visita SET esito = ? WHERE medico = ? AND data = ? AND ora = ? RETURNING *";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        //the created examination
        MedicalExamination medEx = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, examination.getOutcome());
            pstmt.setString(2, examination.getDoctor_cf());
            pstmt.setDate(3, examination.getDate());
            pstmt.setTime(4, examination.getTime());

            rs = pstmt.executeQuery();

            if(rs.next()){
                medEx = new MedicalExamination( rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito"));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return medEx;
    }



    /**
     * Get ALL the medical examinations of the patient
     *
     * @throws SQLException
     *             if any error occurs while getting the medical examination
     */
    public static ArrayList<MedicalExamination> getMedicalExaminations(String patientCf) throws SQLException, NamingException {

        final String GET_MEDICAL_EXAMINATIONS_STATEMENT = "SELECT medico, paziente, data, ora, esito FROM doctors.Visita " +
                "WHERE paziente = ? " +
                "ORDER BY data";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;


        // the results of the search
        ArrayList<MedicalExamination> examinationList = new ArrayList<>();

        try {
            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(GET_MEDICAL_EXAMINATIONS_STATEMENT);
            pstmt.setString(1, patientCf);

            rs = pstmt.executeQuery();

            while (rs.next()){

                MedicalExamination medExamination = new MedicalExamination(
                        rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito"));

                examinationList.add(medExamination);
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

        return examinationList;
    }



    /**
     * Deletes a medical examination from the database
     *
     * @throws SQLException
     *             if any error occurs while deleting the medical examination.
     */
    public static MedicalExamination deleteMedicalExamination(String doctor_cf, Date date, Time time) throws NamingException, SQLException {

        final String STATEMENT = "DELETE FROM doctors.Visita WHERE medico = ? AND data = ? AND ora = ? RETURNING *";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        //the deleted examination
        MedicalExamination medEx = null;

        try {

            con = DataSourceProvider.getDataSource().getConnection();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, doctor_cf);
            pstmt.setDate(2, date);
            pstmt.setTime(3, time);

            rs = pstmt.executeQuery();

            //get the deleted examination and return it
            if(rs.next()){
                medEx = new MedicalExamination(rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito"));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return medEx;
    }

}
