package it.unipd.dei.webapp.resource;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to handle and show booked and available times during the reservation of a Medical Examination
 */
public class BookingTime {
    /**
     * list of possible hours and minutes that the user will be able to select
     */
    private static final ArrayList<String> hours =  new ArrayList<String>(Arrays.asList("09", "10", "11", "12", "14", "15", "16", "17", "18"));
    private static final ArrayList<String> minutes = new ArrayList<String>(Arrays.asList("00", "30"));

    /**
     *  hour of a given time
     */
    private final String hour;

    /**
     * minutes of a given time
     */
    private final String min;

    /**
     * availability of a given time, true if the time was booked already, false otherwise
     */
    private final boolean booked;


    /**
     * public constructor, only specific values will provide a correct booking time
     * @param hour
     *          the hour indicated in the time, needs to be among the possible hours, otherwise null
     * @param min
     *          the minutes indicated in the time, needs to be among the possible minutes, otherwise null
     * @param booked
     */
    public BookingTime(String hour, String min, boolean booked) {
        if(hours.indexOf(hour) != -1) {
            this.hour = hour;
        }
        else{
            this.hour = null;
        }

        if(minutes.indexOf(min) != -1) {
            this.min = min;
        }
        else{
            this.min = null;
        }

        this.booked = booked;
    }


    /**
     * public constructor from sql Time
     * @param time
     *          the sql Time to be converted
     * @param booked
     *          the
     */
    public BookingTime(Time time, boolean booked){
        //get hour and minutes
        String stringTime = time.toString();
        String[] splitTime = stringTime.split(":");
        String h = splitTime[0];
        String m = splitTime[1];

        //if one of the available correct hours/minutes, proceed with successful initiation
        if(hours.indexOf(h) != -1) {
            this.hour = h;
        }
        else{
            this.hour = null;
        }

        if(minutes.indexOf(m) != -1) {
            this.min = m;
        }
        else{
            this.min = null;
        }

        this.booked = booked;
    }



    public boolean isBooked() {
        return booked;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public Time getSqlTime() throws ParseException {
        String time = this.hour+":"+this.min;

        //parse string and convert into dql time
        java.util.Date utilTime = new SimpleDateFormat("HH:mm").parse(time);
        return  new java.sql.Time(utilTime.getTime());
    }



    /**
     * generate list of possible times for the user to select in the form
     *
     * @param bookedTimes
     *              a list of examinations times already booked for a specific doctor.
     *              If it is empty the returned list will be the default one with all times not booked.
     *
     * @return the list of possible times
     */
    public static ArrayList<BookingTime> TimesList(ArrayList<BookingTime> bookedTimes) {
        ArrayList<BookingTime> bookingTimeArrayList = new ArrayList<>();

        if (!bookedTimes.isEmpty()) {
            //fill in the list depending on the already booked times
            for (String hour : hours) {
                for (String minute : minutes) {
                    boolean booked = false;
                    if (bookedTimes.indexOf(new BookingTime(hour, minute, true)) != -1) {
                        //if we can find a match in the (the time is booked), set booked to true
                        booked = true;
                    }
                    bookingTimeArrayList.add(new BookingTime(hour, minute, booked));
                }
            }
        }

        else{
            //prepare default list
            for (String hour : hours) {
                for (String minute : minutes) {
                    bookingTimeArrayList.add(new BookingTime(hour, minute, false));
                }
            }
        }

        return bookingTimeArrayList;
    }

}
