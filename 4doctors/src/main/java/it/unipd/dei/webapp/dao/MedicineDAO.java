package it.unipd.dei.webapp.dao;

import it.unipd.dei.webapp.resource.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class containing all the useful operation for the resource Medicine in the database
 */
public class MedicineDAO {

    /**
     * The connection to the database
     */
    Connection con = null;

    /**
     * Create an object containing all the useful operation for the resource Medicine in the database
     *
     * @param con The connection to the database
     */
    public MedicineDAO(final Connection con) {
        this.con = con;
    }

    /**
     * Get the list of all medicines available in the database
     * @return The list of all medicines
     * @throws SQLException : if any error occurs while accessing the database.
     */
    public List<Medicine> getListMedicines() throws SQLException {

        final String MEDICINE_STATEMENT = "SELECT * FROM doctors.Farmaco";

        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Medicine> medicines = new ArrayList<>();


        try {
            pstmt = con.prepareStatement(MEDICINE_STATEMENT);
            result = pstmt.executeQuery();

            while (result.next()){
                medicines.add(new Medicine(result.getString("codice"), result.getString("nome"), result.getString("classe"), result.getString("azienda"), result.getString("descrizione")));
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
        return medicines;
    }

    /**
     * Insert new medicine in the database
     * @param medicine : represent the medicine to be insert
     * @return true if the insertion successfully end, false otherwise.
     * @throws SQLException : if any error occurs while accessing the database.
     */
    public Boolean addMedicine(Medicine medicine) throws SQLException {

        final String MEDICINE_STATEMENT = "INSERT INTO doctors.farmaco VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = null;
        String code = medicine.getCode();
        String name = medicine.getName();
        String m_class = medicine.getMedicine_class();
        String producer = medicine.getProducer();
        String description = medicine.getDescription();
        int index = 1;

        boolean result = false;

        try {
            pstmt = con.prepareStatement(MEDICINE_STATEMENT);
            pstmt.setString(index++, code);
            pstmt.setString(index++, name);
            pstmt.setObject(index++, m_class, Types.OTHER);
            pstmt.setString(index++, producer);
            pstmt.setString(index, description);
            if(pstmt.executeUpdate() == 1) result = true;

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
        return result;  //if no exeptions has been thrown
    }

    /**
     * Get the list of all medicines requested by a patient identified by the fiscal code
     * @param patien_cf : the fiscal code which uniquely identify a patient
     * @return A list of all medicines required by the patient and accepted by doctors
     * @throws SQLException : if any error occurs while accessing the database.
     */
    public List<Medicine> userMedicines(String patien_cf) throws SQLException {

        final String MEDICINE_STATEMENT = "SELECT DISTINCT medicine.codice, medicine.nome, medicine.classe, medicine.azienda, medicine.descrizione FROM doctors.ricetta AS r\n" +
                "                INNER JOIN ( SELECT * FROM doctors.farmaciricetta AS fr\n" +
                "                INNER JOIN doctors.farmaco AS f\n" +
                "                ON fr.farmaco = f.codice) AS medicine\n" +
                "                ON r.id = medicine.ricetta AND r.paziente = ?\n" +
                "\t\t\t\tWHERE r.status = 'APPROVED'";
        PreparedStatement pstmt = null;
        ResultSet result = null;
        List<Medicine> medicines = new ArrayList<>();


        try {
            pstmt = con.prepareStatement(MEDICINE_STATEMENT);
            pstmt.setString(1, patien_cf);
            result = pstmt.executeQuery();

            while (result.next()){
                medicines.add(new Medicine(result.getString("codice"), result.getString("nome"), result.getString("classe"), result.getString("azienda"), result.getString("descrizione")));
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
        return medicines;
    }

}
