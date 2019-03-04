package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckOut {

    //Fine amount constant
    private static final double ONE_DAY_LATE = 10.00;
    private static final double EACH_WEEK = 2.00;
    private static final double MAX_FINE = 30.00;

    private Book book;
    private Visitor visitor;
    private Calendar dueDate;


    public CheckOut(Book book, Visitor visitor, Calendar dueDate) {
        this.book = book;
        this.visitor = visitor;
        this.dueDate = dueDate;
        dueDate.add(Calendar.DATE, 7);
    }

    public double checkIn(){

        double fine = 0.00;
        this.book.checkIn();
        this.book = null;
        this.visitor = null;
        return fine;
    }

    public String getBook() {
        return book.getISBN();
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public boolean isOverDue() {
        return Calendar.getInstance().after(dueDate);
    }

    public String getDueDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(dueDate.getTime());
    }
}
