package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the data about a presecription
 */
public class Prescription extends Resource {



    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public enum TypeOfPrescription {
        ESAME, FARMACO
    }

    // The id of the prescription in postgre uuid format
    private final String id;

    // The cf of the doctor who gets the prescription request
    private final String doctor;

    // The cf of the patient who requests the prescription
    private final String patient;

    // The date when the prescription request has been done
    private final Date date;

    // A short description about the motivation of the presciption request
    private final String description;

    // The duration of the prescription
    private final int duration;

    // The type of the prescription. It can be either EXAM or MEDICINE according to what the patient has requested
    private final TypeOfPrescription type;

    /* The status of the presecription. It can be either
        - PENDING when the request has been sent
        - APPORVED when the doctor accepts it
        - REJECETED when the doctor refuses it
    */
    private final Status status;

    /**
     * Creates a new Prescription
     * @param id The id of the prescription in postgre uuid format
     * @param doctor The cf of the doctor who gets the prescription request
     * @param patient The cf of the patient who requests the prescription
     * @param date The date when the prescription request has been done
     * @param description A short description about the motivation of the presciption request
     * @param duration The duration of the prescription
     * @param type The type of the prescription
     * @param status The status of the presecription
     */
    public Prescription(final String id, final String doctor, final String patient, final Date date, final String description, final int duration, final TypeOfPrescription type, final Status status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.description = description;
        this.duration = duration;
        this.type = type;
        this.status = status;
    }

    /**
     * Creates a new presciption without knowing which uuid will be assigned
     * @param doctor The cf of the doctor who gets the prescription request
     * @param patient The cf of the patient who requests the prescription
     * @param date The date when the prescription request has been done.
     * @param description A short description about the motivation of the presciption request
     * @param duration The duration of the prescription
     * @param type The type of the prescription
     * @param status The status of the presecription
     */
    public Prescription(final String doctor, final String patient, final Date date, final String description, final int duration, final TypeOfPrescription type, final Status status) {
        this(null, doctor, patient, date, description, duration, type, status);
    }

    /**
     * Returns the id of the prescription in uuid formad
     * @return the id of the prescription in uuid formad
     */
    public final String getId() {
        return id;
    }

    /**
     * Returns the type of the prescription
     * @return the prescription's doctor cf
     */
    public final String getDoctor() {
        return doctor;
    }

    /**
     * Returns the type of the prescription
     * @return the prescription's patient cf
     */
    public final String getPatient() {
        return patient;
    }

    /**
     * Returns the date of the prescription
     * @return the date of the prescription
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Returns the description of the prescription
     * @return the description of the prescription
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Returns the duration of the prescription
     * @return the duration of the prescription
     */
    public final int getDuration() {
        return duration;
    }

    /**
     * Returns the type of the prescription
     * @return the type of the prescription
     */
    public final TypeOfPrescription getType() {
        return type;
    }

    /**
     * Returns the status of the user
     * @return the status of the user
     */
    public final Status getStatus() {
        return status;
    }

    @Override
    public final void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();

        jg.writeFieldName("prescription");

        jg.writeStartObject();

        jg.writeStringField("id", id);

        jg.writeStringField("doctor", doctor);

        jg.writeStringField("patient", patient);

        jg.writeStringField("date", date.toString()); //date?? can treat it as a date?

        jg.writeStringField("description", description);

        jg.writeNumberField("duration", duration);

        jg.writeStringField("type", type.name());

        jg.writeStringField("status", status.name());

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates a {@code Medicine} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Medicne} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Prescription fromJSON(final InputStream in) throws IOException, ParseException {

        // the fields read from JSON
        String jId = null;
        String jDoctor = null;
        String jPatient = null;
        Date jDate = null;
        String jDescription = null;
        int jDuration = -1;
        TypeOfPrescription jType = null;
        Status jStatus = null;

        String jDateS = null; //for getting the date from JSON object

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "prescription".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no prescription object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "id":
                        jp.nextToken();
                        jId = jp.getText();
                        break;
                    case "doctor":
                        jp.nextToken();
                        jDoctor = jp.getText();
                        break;
                    case "patient":
                        jp.nextToken();
                        jPatient = jp.getText();
                        break;
                    case "date":
                        jp.nextToken();
                        jDateS = jp.getText();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
                        jDate = (Date) sdf.parse(jDateS);
                        break;
                    case "description":
                        jp.nextToken();
                        jDescription = jp.getText();
                        break;
                    case "duration":
                        jp.nextToken();
                        jDuration = jp.getIntValue();
                        break;
                    case "type":
                        jp.nextToken();
                        jType = Prescription.TypeOfPrescription.valueOf(jp.getText());
                        break;
                    case "status":
                        jp.nextToken();
                        jStatus = Prescription.Status.valueOf(jp.getText());
                        break;
                }
            }
        }

        return new Prescription(jId, jDoctor, jPatient, jDate, jDescription, jDuration, jType, jStatus);
    }

}
