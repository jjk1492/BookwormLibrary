package controller;

import model.BookwormLibrary;

import java.util.Scanner;

public class Launcher {

    private static QueryHandler queryHandler;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        queryHandler = new QueryHandler();
        boolean running = true;
        BookwormLibrary bookwormLibrary = new BookwormLibrary();

        while (running) {
            System.out.print("Enter command: ");
            String line = scanner.nextLine();
            running = queryHandler.handleQuery(line);
        }
    }

}
