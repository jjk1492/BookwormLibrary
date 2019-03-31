package model;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * Represents a visit, one is created each time a visitor enters the library. Tracks the time of the visit, the date, and who visited.
 *
 * @author John Knecht V(jjk1492@rit.edu) & Lucas Golden
 */
public class Visit {

    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;

    private Long visitorId;

    /**
     * Constructor for a visit, endTime is initially set to -1 indicating it hasn't ended yet.
     * @param visitor - Who visited the library
     */
    public Visit(Visitor visitor, LocalDateTime time) {
        this.visitorId = visitor.getUserID();
        this.date = time;
        startTime = date.toLocalTime();
    }

    /**
     * yyyy-MM-dd representation of date the visit started
     * @return Date visit started
     */
    public String getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    /**
     * milliseconds representation of when the visit began
     * @return
     */
    public String getStartTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return startTime.format(formatter);
    }

    /**
     * milliseconds representation of when the visit ended
     * @return
     */
    public String getEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return endTime.format(formatter);
    }

    /**
     * Get the visitor ID
     * @return visitorID
     */
    public Long getVisitorId() {
        return visitorId;
    }

    /**
     * End the visit
     */
    public void endVisit(LocalDateTime time) {
        this.endTime = time.toLocalTime();
    }

    /**
     * Find how long someone was in the library
     * @return Int representation of minutes spent in library
     */
    public String getVisitTimeMinutes() {
        Duration duration = Duration.between(startTime, endTime);
        LocalTime t = LocalTime.MIDNIGHT.plus(duration);
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(t);
    }
}
