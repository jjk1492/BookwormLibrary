package commands;

import model.Book;

public class bookPurchase extends command {
    @Override
    public String runCommand(String[] args) {
        if(args.length >= 3) {
            int quantity = Integer.parseInt(args[1]);
            int n = 0;
            String beginning = "buy,success,";
            String books = "";

            for (int i = 2; i <= args.length - 1; i++) {
                books += library.buyBook(args[i], quantity) + "\n";
                n += 1;

            }

            beginning += n;
            return beginning + books;
        }

        return "buy,missing-parameters,{quantity,id[,ids]};";
    }
}
