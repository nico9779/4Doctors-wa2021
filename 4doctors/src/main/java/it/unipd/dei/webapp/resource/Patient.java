package it.unipd.dei.webapp.resource;

import java.sql.Date;
import org.json.JSONObject;

/**
 * Represents the data about a patient.
 */
public class Patient {

    /**
     * The codice fiscale of the patient
     */
    private final String cf;

    /**
     * The name of the patient
     */
    private final String name;

    /**
     * The surname of the patient
     */
    private final String surname;

    /**
     * The email of the patient
     */
    private final String email;

    /**
     * The password of the patient
     */
    private final String password;

    /**
     * The birthday of the patient
     */
    private final Date birthday;

    /**
     * The birthplace of the patient
     */
    private final String birthplace;

    /**
     * The address of the patient
     */
    private final String address;

    /**
     * The gender of the patient
     */
    private final Gender gender;

    /**
     * Creates a new patient
     *
     * @param cf    the codice fiscale of the patient
     * @param name  the name of the patient
     * @param surname   the surname of the patient
     * @param email the email of the patient
     * @param password  the password of the patient
     * @param birthday  the birthday of the patient
     * @param birthplace    the birthplace of the patient
     * @param address   the address of the patient
     * @param gender    the gender of the patient
     */
    public Patient(final String cf, final String name, final String surname, final String email, final String password,
                   final Date birthday, final String birthplace, final String address, final Gender gender) {
        this.cf = cf;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.address = address;
        this.gender = gender;
    }


    /**
     * Returns the codice fiscale of the patient.
     *
     * @return the codice fiscale of the patient.
     */
    public final String getCf() {
        return cf;
    }

    /**
     * Returns the name of the patient.
     *
     * @return the name of the patient.
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the surname of the patient.
     *
     * @return the surname of the patient.
     */
    public final String getSurname() {
        return surname;
    }

    /**
     * Returns the email of the patient.
     *
     * @return the email of the patient.
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Returns the password of the patient.
     *
     * @return the password of the patient.
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Returns the birthday of the patient.
     *
     * @return the birthday of the patient.
     */
    public final Date getBirthday() {
        return birthday;
    }

    /**
     * Returns the birthplace of the patient.
     *
     * @return the birthplace of the patient.
     */
    public final String getBirthplace() {
        return birthplace;
    }

    /**
     * Returns the address of the patient.
     *
     * @return the address of the patient.
     */
    public final String getAddress() {
        return address;
    }

    /**
     * Returns the gender of the patient.
     *
     * @return the gender of the patient.
     */
    public final Gender getGender() {
        return gender;
    }

    /**
     * Convert a patient object into a JSONObject
     *
     * @return a JSONObject containing the patient
     */
    public final JSONObject toJson(){

        JSONObject patientJson = new JSONObject();
        patientJson.put("cf", cf);
        patientJson.put("name", name);
        patientJson.put("surname", surname);
        patientJson.put("email", email);
        patientJson.put("birthday", birthday);
        patientJson.put("birthplace", birthplace);
        patientJson.put("address", address);
        patientJson.put("gender", gender);

        return patientJson;
    }
}
