package controller.command.commands;

import controller.command.Command;
import model.Book;
import model.BookwormLibrary;
import model.CheckOut;

import java.util.ArrayList;

/**
 * Find borrowed books from a user Command
 */
public class FindBorrowBook implements Command {

    private String[] commandArgs;

    public FindBorrowBook(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }

    @Override
    public void execute() {

        Long visitorID = Long.parseLong(this.commandArgs[2]);

        BookwormLibrary library = BookwormLibrary.getInstance();

        if( library.hasVisitor(visitorID)){
            ArrayList<CheckOut> visitorsCheckouts = library.getVisitorCheckOuts(visitorID);
            if( visitorsCheckouts.isEmpty() ){
                System.out.println("borrowed,0;");
            }else{
                System.out.println("borrowed," + visitorsCheckouts.size() + ",\n");
                for( int i=0; i<visitorsCheckouts.size(); i++){
                    Book b = visitorsCheckouts.get(i).getBook();
                    String result = (i+1) + "," + b.getISBN() + "," + b.getTitle() + "," + visitorsCheckouts.get(i).getCheckedDate() + "/n";
                    if( i == visitorsCheckouts.size()-1){
                        result += ";";
                    }
                    System.out.println(result);
                }
            }
        }else{
            System.out.println("borrowed,invalid-visitor-id;");
        }

    }

}