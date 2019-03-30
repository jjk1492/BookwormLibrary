package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a visit, one is created each time a visitor enters the library. Tracks the time of the visit, the date, and who visited.
 *
 * @author John Knecht V(jjk1492@rit.edu) & Lucas Golden
 */
public class Visit {

    private Date date = new Date();
    private long startTime = System.currentTimeMillis();
    private long endTime;

    private Long visitorId;

    /**
     * Constructor for a visit, endTime is initially set to -1 indicating it hasn't ended yet.
     * @param visitor - Who visited the library
     */
    public Visit(Visitor visitor) {
        this.visitorId = visitor.getUserID();
    }

    /**
     * yyyy-MM-dd representation of date the visit started
     * @return Date visit started
     */
    public String getStartDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * milliseconds representation of when the visit began
     * @return
     */
    public long getStartTime(){
        return this.startTime;
    }

    /**
     * milliseconds representation of when the visit ended
     * @return
     */
    public long getEndTime() {
        return this.endTime;
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
    public void endVisit() {
        this.endTime = System.currentTimeMillis();
    }

    /**
     * Find how long someone was in the library
     * @return Int representation of minutes spent in library
     */
    public int getVisitTimeMinutes() {
        if (endTime == 0L) return (int)((System.currentTimeMillis() - startTime) / 1000) / 60;

        return (int)((endTime - startTime) / 1000) / 60;
    }

    /**
     * Provides the necessary endtime to end a visit
     * @param endTime the time the visit ends
     */
    public void endVisit(long endTime){
        this.endTime = endTime;
    }
}
