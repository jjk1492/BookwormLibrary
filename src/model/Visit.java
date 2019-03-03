package model;

import model.Visitor;

/**
 * Represents a visit, one is created each time a visitor enters the library. Tracks the time of the visit, the date, and who visited.
 *
 * @author John Knecht V(jjk1492@rit.edu)
 */
public class Visit {

    private long startTime;
    private long endTime;
    private String date;
    private Visitor visitor;

    /**
     * Constructor for a visit, endTime is initially set to -1 indicating it hasn't ended yet.
     * @param startTime - Starting time of the visit
     * @param date - Date of the visit
     * @param visitor - Who visited the library
     */
    public Visit(long startTime, String date, Visitor visitor) {
        this.endTime = -1;

        this.startTime = startTime;
        this.date = date;
        this.visitor = visitor;
    }

    public void endVisit(long endTime){
        this.endTime = endTime;
    }
}
