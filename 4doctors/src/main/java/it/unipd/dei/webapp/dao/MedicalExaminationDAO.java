package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.MedicalExamination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class MedicalExaminationDAO {

    /**
     * The SQL statement to be executed to retrieve the medical examinations of the patient
     */
//    private static final String GET_MEDICAL_EXAMINATIONS_STATEMENT = "SELECT nomem.nome, nomem.cognome, data, esito " +
//            "FROM doctors.Paziente JOIN (" +
//            "    SELECT *" +
//            "    FROM doctors.Medico JOIN doctors.Visita ON (Medico.cf = Visita.medico)" +
//            "    ) AS nomem ON Paziente.cf=nomem.paziente" +
//            "    WHERE Paziente.nome = ? AND Paziente.cognome = ?" +
//            "    ORDER BY data";
    public static final String GET_MEDICAL_EXAMINATIONS_STATEMENT = "SELECT medico, paziente, data, ora, esito FROM doctors.Visita " +
            "WHERE paziente = ? " +
            "ORDER BY data";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The patient to which the medical examinations are displayed
     */
    private final String cf;

    /**
     * Creates a new object for storing a patient into the database.
     *
     * @param con
     *            the connection to the database.
     * @param cf
     *            the patient to which the medical examinations are displayed.
     */
    public MedicalExaminationDAO(final Connection con, final String cf) {
        this.con = con;
        this.cf = cf;
    }

    /**
     * Get the medical examinations of the patient
     *
     * @throws SQLException
     *             if any error occurs while storing the medical examination.
     */
    public ArrayList<List<MedicalExamination>> getMedicalExaminations() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd;

        // the results of the search
        final List<MedicalExamination> pastMedicalExaminationList = new ArrayList<MedicalExamination>();
        final List<MedicalExamination> futureMedicalExaminationList = new ArrayList<MedicalExamination>();
        ArrayList<List<MedicalExamination>> result = new ArrayList<List<MedicalExamination>>(2);

        try {
            pstmt = con.prepareStatement(GET_MEDICAL_EXAMINATIONS_STATEMENT);
            pstmt.setString(1, this.cf);

            rs = pstmt.executeQuery();

            while (rs.next()){

                MedicalExamination medExamination = new MedicalExamination(
                        rs.getString("medico"),
                        rs.getString("paziente"),
                        rs.getDate("data"),
                        rs.getTime("ora"),
                        rs.getString("esito")
                );

                if (medExamination.getDate().getTime() < System.currentTimeMillis()){
                    pastMedicalExaminationList.add(medExamination);
                }else{
                    futureMedicalExaminationList.add(medExamination);
                }

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

        result.add(pastMedicalExaminationList);
        result.add(futureMedicalExaminationList);
        return result;
    }
}
