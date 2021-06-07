package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Exam;
import it.unipd.dei.webapp.resource.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class containing all the useful operation for the resource Medicine in the database
 */
public class ExamDAO {

    /**
     * The connection to the database
     */
    Connection con = null;

    /**
     * Create an object containing all the useful operation for the resource Medicine in the database
     *
     * @param con The connection to the database
     */
    public ExamDAO(final Connection con) {
        this.con = con;
    }

    /**
     * Get the list of all medicines available in the database
     * @return The list of all medicines
     * @throws SQLException : if any error occurs while accessing the database.
     */
    public List<Exam> getListExams() throws SQLException {

        final String EXAM_STATEMENT = "SELECT * FROM doctors.esame";

        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Exam> exams = new ArrayList<>();


        try {
            pstmt = con.prepareStatement(EXAM_STATEMENT);
            result = pstmt.executeQuery();

            while (result.next()){
                exams.add(new Exam(result.getString("codice"), result.getString("nome")));
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
        return exams;
    }

}
