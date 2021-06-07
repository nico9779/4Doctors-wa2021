package it.unipd.dei.webapp.resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the data about a doctor.
 */
public class Doctor {

    /**
     * The codice fiscale of the doctor
     */
    private final String cf;

    /**
     * The name of the doctor
     */
    private final String name;

    /**
     * The surname of the doctor
     */
    private final String surname;

    /**
     * The email of the doctor
     */
    private final String email;

    /**
     * The password of the doctor
     */
    private final String password;

    /**
     * The birthday of the doctor
     */
    private final Date birthday;

    /**
     * The birthplace of the doctor
     */
    private final String birthplace;

    /**
     * The address of the doctor
     */
    private final String address;

    /**
     * The gender of the doctor
     */
    private final Gender gender;

    /**
     * The ASL code
     */
    private final String aslcode;

    /**
     * Creates a new doctor
     *
     * @param cf    the codice fiscale of the doctor
     * @param name  the name of the doctor
     * @param surname   the surname of the doctor
     * @param email the email of the doctor
     * @param password  the password of the doctor
     * @param birthday  the birthday of the doctor
     * @param birthplace    the birthplace of the doctor
     * @param address   the address of the doctor
     * @param gender    the gender of the doctor
     * @param aslcode    the ASL code of the doctor
     */

    public Doctor(final String cf, final String name, final String surname, final String email, final String password,
                   final Date birthday, final String birthplace, final String address, final Gender gender, final String aslcode) {
        this.cf = cf;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.address = address;
        this.gender = gender;
        this.aslcode = aslcode;
    }

    /**
     * Returns the codice fiscale of the doctor.
     *
     * @return the codice fiscale of the doctor.
     */
    public String getCf() {
        return cf;
    }

    /**
     * Returns the name of the doctor.
     *
     * @return the name of the doctor.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the surname of the doctor.
     *
     * @return the surname of the doctor.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns the email of the doctor.
     *
     * @return the email of the doctor.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Returns the password of the doctor.
     *
     * @return the password of the doctor.
     */
    public String getPassword() {
        return password;
    }


    /**
     * Returns the birthday of the doctor.
     *
     * @return the birthday of the doctor.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Returns the birthplace of the doctor.
     *
     * @return the birthplace of the doctor.
     */
    public String getBirthplace() {
        return birthplace;
    }

    /**
     * Returns the address of the doctor.
     *
     * @return the address of the doctor.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the gender of the doctor.
     *
     * @return the gender of the doctor.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns the ASL code of the doctor.
     *
     * @return the ASL code of the doctor.
     */
    public String getAslcode() {
        return aslcode;
    }

    /**
     * Convert a doctor object into a JSONObject
     *
     * @return a JSONObject containing the doctor
     */
    public final JSONObject toJson(){

        JSONObject doctorJson = new JSONObject();
        doctorJson.put("cf", cf);
        doctorJson.put("name", name);
        doctorJson.put("surname", surname);
        doctorJson.put("email", email);
        doctorJson.put("birthday", birthday);
        doctorJson.put("birthplace", birthplace);
        doctorJson.put("address", address);
        doctorJson.put("gender", gender);
        doctorJson.put("aslcode", aslcode);

        return doctorJson;
    }

    /**
     * Convert a JSON into a doctor object
     *
     * @param inputStream JSON sent from client
     * @return doctor object converted from a JSON
     */
    public static Doctor fromJSON(InputStream inputStream) throws IOException, ParseException, JSONException {

        String jsonString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(jsonString);

        JSONObject doctorJson = json.getJSONObject("doctor");

        String cf = doctorJson.getString("cf");
        String name = doctorJson.getString("name");
        String surname = doctorJson.getString("surname");
        String email = doctorJson.getString("email");
        String password = doctorJson.getString("password");

        String birthdayString = doctorJson.getString("birthday");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date(sdf.parse(birthdayString).getTime());

        String birthplace = doctorJson.getString("birthplace");
        String address = doctorJson.getString("address");
        Gender gender = Gender.valueOf(doctorJson.getString("gender"));
        String aslcode = doctorJson.getString("aslcode");

        return new Doctor(cf, name, surname, email, password, birthday, birthplace, address, gender, aslcode);
    }
}
