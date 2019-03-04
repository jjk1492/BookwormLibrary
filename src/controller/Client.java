package controller;

import commands.*;
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();

        while (true) {
            String line = scanner.nextLine();
            System.out.println(client.runCommand(line));
        }
    }
}
