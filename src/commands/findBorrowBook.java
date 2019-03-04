package commands;

import model.Book;
import model.CheckOut;

import java.util.ArrayList;

public class findBorrowBook extends command{
    @Override
    public String runCommand(String[] args) {
        if(library.getCheckedOutBooks().containsKey(args[1])){
            int id = 0;
            StringBuilder sb = new StringBuilder();
            sb.append(library.getCheckedOutBooks().get(args[1]).size());
            sb.append(",\n");

            for(CheckOut c : library.getCheckedOutBooks().get(args[1])){
                sb.append(id);
                id++;
                sb.append(",");
                sb.append(c.getBookISBN());
                sb.append(",");
                sb.append(c.getBook().getTitle());
                sb.append(",");
                sb.append(c.getCheckedDate());
            }
            sb.append(";");
        }
        return "borrow,invalid-visitor-id;";
    }
}
