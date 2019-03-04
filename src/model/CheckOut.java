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
    private Calendar checkedDate;


    public CheckOut(Book book, Visitor visitor, Calendar dueDate) {
        this.book = book;
        this.visitor = visitor;
        this.dueDate = dueDate;
        dueDate.add(Calendar.DATE, 7);
        this.checkedDate = dueDate;
    }

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
            }
            else {
                return ONE_DAY_LATE + (weeks * EACH_WEEK);
            }
        }
        else {
            return 0.0;
        }
    }

    public String getBookISBN() {
        return book.getISBN();
    }

    public Book getBook() {
        return book;
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

    public String getCheckedDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(dueDate.getTime());
    }
}
