package controller;

import commands.*;
import model.Book;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private BookwormLibrary library = BookwormLibrary.getInstance();

    public String runCommand(String s) {
        String s1 = s.replaceAll("\\s", ""); //remove spaces from input
        String[] args = s1.split(",");          //split the string on commas

        if (!args[args.length - 1].endsWith(";")) {
            return "partial-request;";
        }

        //Remove ";" from end of the last string
        args[args.length - 1] = args[args.length - 1].substring(0, args[args.length - 1].length() - 1);


        switch (args[0]) {
            case "register":
                return new registerVisitor().runCommand(args);
            case "arrive":
                return new beginVisit().runCommand(args);
            case "depart":
                return new endVisit().runCommand(args);
            case "info":
                return new libraryBookSearch().runCommand(args);
            case "borrow":
                return new borrowBook().runCommand(args);
            case "borrowed":
                return new findBorrowBook().runCommand(args);
            case "return":
                return new returnBook().runCommand(args);
            case "pay":
                return new payFine().runCommand(args);
            case "search":
                return new bookStoreSearch().runCommand(args);
            case "buy":
                return new bookPurchase().runCommand(args);
            case "advance":
                return new advanceTime().runCommand(args);
            case "datetime":
                return new currentTime().runCommand(args);
            case "report":
                return new report().runCommand(args);
            default:
                return "Error:Unknown Command";
        }
    }

    private void readInBooks() {
        try {
            Scanner scanner = new Scanner(new File("./src/books.txt"));

            while(scanner.hasNextLine()) {
                ArrayList<String> vals = new ArrayList<>();
                StringBuilder builder = new StringBuilder();

                boolean inSpecial = false;
                for (char c : scanner.nextLine().toCharArray()) {
                    if (c == '"' || c == '{' || c == '}') {
                        inSpecial = !inSpecial;
                    }
                    else if (c == ',' && !inSpecial) {
                        vals.add(builder.toString());
                        builder = new StringBuilder();
                    }
                    else {
                        builder.append(c);
                    }
                }

                vals.add(builder.toString());

                if (vals.get(4).length() == 4) {
                    vals.set(4, vals.get(4) + "-01-01");
                }
                else if (vals.get(4).length() == 7) {
                    vals.set(4, vals.get(4) + "-01");
                }

                this.library.addBookToCatalogue(new Book(vals.get(0), vals.get(1), new ArrayList<>(Arrays.asList(vals.get(2).split(","))), vals.get(3), new SimpleDateFormat("yyyy-MM-dd").parse(vals.get(4)), Integer.parseInt(vals.get(5))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.readInBooks();
    }
}
