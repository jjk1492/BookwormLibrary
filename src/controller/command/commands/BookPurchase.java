package controller.command.commands;

import controller.command.Command;

/**
 * Purchase a book from catalogue Command
 */
public class BookPurchase implements Command {

    public BookPurchase() {
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    /*@Override
    public String runCommand(String[] args) {
        if (args.length >= 3) {
            int quantity = Integer.parseInt(args[1]);
            int n = 0;
            String beginning = "buy,success,";
            String books = "";

            for (int i = 2; i <= args.length - 1; i++) {
                books += library.buyBook(args[i], quantity) + "\n";
                n += 1;
            }

            beginning += (n + "\n");
            return beginning + books.substring(0, books.length() - 1);
        }

        return "buy,missing-parameters,{quantity,id[,ids]};";
    }*/
}