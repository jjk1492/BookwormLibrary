package model;

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
    }

    public double checkIn(){

        double fine = 0.00;
        Calendar currentTime = Calendar.getInstance();

        if( currentTime.after(this.dueDate)){

            fine += ONE_DAY_LATE;
            dueDate.add(Calendar.WEEK_OF_MONTH, 1);

            while (currentTime.after(dueDate)){

                fine += EACH_WEEK;
                dueDate.add(Calendar.WEEK_OF_MONTH, 1);

                if( fine > MAX_FINE){
                    fine = MAX_FINE;
                    break;
                }
            }
        }
        return fine;
    }

    public Book getBook() {
        return book;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Calendar getDueDate() {
        return dueDate;
    }
}
