package it.unipd.dei.webapp.resource;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the data about a medical examination.
 */
public class MedicalExamination extends Resource {

    /**
     * The codice fiscale of the doctor
     */
    private final String doctor_cf;

    /**
     * The codice fiscale of the patient
     */
    private final String patient_cf;

    /**
     * The date of the medical examination
     */
    private final Date date;

    /**
     * The time of the medical examination
     */
    private final Time time;

    /**
     * The outcome of the medical examination
     */
    private final String outcome;

    /**
     * Creates a new medical examination
     *
     * @param doctor_cf    the codice fiscale of the doctor
     * @param patient_cf   the codice fiscale of the patient
     * @param date   the date of a medical examination
     * @param time   the time of a medical examination
     */
    public MedicalExamination(final String doctor_cf, final String patient_cf, final Date date, final Time time, final String outcome){
        this.doctor_cf = doctor_cf;
        this.patient_cf = patient_cf;
        this.date = date;
        this.time = time;
        this.outcome = outcome;
    }

    /**
     * Returns the codice fiscale of the doctor.
     *
     * @return the codice fiscale of the doctor.
     */
    public String getDoctor_cf() {
        return doctor_cf;
    }

    /**
     * Returns the codice fiscale of the patient.
     *
     * @return the codice fiscale of the patient.
     */
    public String getPatient_cf() {
        return patient_cf;
    }

    /**
     * Returns the date of the medical examination.
     *
     * @return the date of the medical examination.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the time of the medical examination.
     *
     * @return the time of the medical examination.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the time of the medical examination.
     *
     * @return the time of the medical examination.
     */
    public String getOutcome() {
        return outcome;
    }



    @Override
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("medicalExamination");

        jg.writeStartObject();

        jg.writeStringField("doctor_cf", doctor_cf);

        jg.writeStringField("patient_cf", patient_cf);

        jg.writeStringField("date", date.toString());

        jg.writeStringField("time", time.toString());

        jg.writeStringField("outcome", outcome);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }



    /**
     * Creates a {@code MedicalExamination} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Medical Examination} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static MedicalExamination fromJSON(final InputStream in) throws IOException, ParseException {

        // the fields read from JSON
        String jDoctor_cf = null;
        String jPatient_cf = null;
        Date jDate = null;
        Time jTime = null;
        String jOutcome = null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"medicalExamination".equals(jp.getCurrentName())) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no medical examination object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "doctor_cf":
                        jp.nextToken();
                        jDoctor_cf = jp.getText();
                        break;
                    case "patient_cf":
                        jp.nextToken();
                        jPatient_cf = jp.getText();
                        break;
                    case "date":
                        jp.nextToken();
                        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(jp.getText());
                        jDate = new java.sql.Date(utilDate.getTime());
                        break;
                    case "time":
                        jp.nextToken();
                        java.util.Date utilTime = new SimpleDateFormat("HH:mm").parse(jp.getText());
                        jTime = new java.sql.Time(utilTime.getTime());
                        break;
                    case "outcome":
                        jp.nextToken();
                        jOutcome = jp.getText();
                        break;
                }
            }
        }

        return new MedicalExamination(jDoctor_cf, jPatient_cf, jDate, jTime, jOutcome);
    }
}
