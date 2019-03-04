package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckOut {

    //Fine amount constant
    private static final double ONE_DAY_LATE = 10.00;
    private static final double EACH_WEEK = 2.00;
    private static final double MAX_FINE = 30.00;

    private Book book;
    private String visitorId;
    private Calendar dueDate;
    private Calendar checkedDate;


    /**
     * Constructor
     * @param book book being checked out
     * @param visitorId ID of who checked out the book
     * @param dueDate date book is due
     */
    public CheckOut(Book book, String visitorId, Calendar dueDate) {
        this.book = book;
        this.visitorId = visitorId;
        this.dueDate = dueDate;
        dueDate.add(Calendar.DATE, 7);
        this.checkedDate = dueDate;
    }

    /**
     * Calculate the fine of how late this book is
     * @return Amount in dollars, between 10 and 30, inclusive
     */
    public double getFine() {
        if (isOverDue()) {
            Calendar now = Calendar.getInstance();

            int days = (int)((((now.getTimeInMillis() - dueDate.getTimeInMillis()) / 1000) / 3600) / 24);
            int weeks = days / 7;

            if (weeks == 0) {
                return ONE_DAY_LATE;
            }
            if (weeks >= 10) {
                return MAX_FINE;
            } else {
                return ONE_DAY_LATE + (weeks * EACH_WEEK);
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Get the book that has been checked out
     * @return Book that has been checked out
     */
    public Book getBook() {
        return book;
    }

    /**
     * Visitor who has checked out this book
     * @return Unique ID of visitor
     */
    public String getVisitorId() {
        return visitorId;
    }

    /**
     * Checks if this checkout is overdue
     * @return True or False depending on overdue
     */
    public boolean isOverDue() {
        return Calendar.getInstance().after(dueDate);
    }

    /**
     * yyyy-MM-dd representation of when the book is due
     * @return due date of book
     */
    public String getDueDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(dueDate.getTime());
    }

    /**
     * yyyy-MM-dd representation of when the book was checked out
     * @return when the book was checked out
     */
    public String getCheckedDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(checkedDate.getTime());
    }
}