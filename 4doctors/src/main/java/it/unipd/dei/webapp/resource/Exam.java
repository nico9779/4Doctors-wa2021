package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class for representing the information about a medicine
 */
public class Exam extends Resource {

    private final String code;
    private final String name;

    /**
     *  @param code
     * @param name
     *
     */
    public Exam(final String code, final String name){

        this.code = code;
        this.name = name;

    }


    /**
     *
     * @return The coded which uniquely identify the medicine in the dataset
     */
    public final String getCode() {
        return code;
    }

    /**
     *
     * @return The name of the medicine
     */
    public final String getName() {
        return name;
    }

    @Override
    public final void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();

        jg.writeFieldName("exam");

        jg.writeStartObject();

        jg.writeStringField("code", code);

        jg.writeStringField("name", name);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates a {@code Medicine} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Medicine} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    public static Exam fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON
        String jCode = null;
        String jName = null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "exam".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no medicine object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {

            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

                switch (jp.getCurrentName()) {
                    case "code":
                        jp.nextToken();
                        jCode = jp.getText();
                        break;
                    case "name":
                        jp.nextToken();
                        jName = jp.getText();
                        break;
                }
            }
        }

        return new Exam(jCode, jName);
    }

}