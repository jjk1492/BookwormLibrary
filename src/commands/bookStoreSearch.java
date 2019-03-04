package commands;

import model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class bookStoreSearch extends command {
    @Override
    public String runCommand(String[] args) {
        String title = args[1];
        ArrayList < String > authors = new ArrayList < > ();
        int offset = 0;
        for (int i = 2; i < args.length - 1; i++) {
            if (args[i].contains("{") && args[i].contains("}")) {
                String s = args[i].replace("{", "").replace("}", "");
                authors.add(s);
                i = Integer.MAX_VALUE;
            } else if (args[i].contains("{")) {
                String s = args[i].replace("{", "");
                authors.add(s);
            } else if (args[i].contains("}")) {
                String s = args[i].replace("}", "");
                authors.add(s);
                i = Integer.MAX_VALUE;
            } else {
                authors.add(args[i]);
            }
            offset++;
        }
        String isbn = args[2 + offset];
        String publisher = args[3 + offset];
        String sortOrder = args[4 + offset];

        ArrayList < Book > books = new ArrayList < > ();

        for (Book b: library.getCatalogue()) {
            if (b.getTitle().equals(title) ||
                    b.getAuthors().equals(authors) ||
                    b.getISBN().equals(isbn) ||
                    b.getPublisher().equals(publisher)) {
                books.add(b);
            }
        }

        if (sortOrder.equals("title")) {
            Collections.sort(books, new Comparator < Book > () {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        } else if (sortOrder.equals("publish-date")) {
            Collections.sort(books, new Comparator < Book > () {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getPublishDate().compareTo(o2.getPublishDate());
                }
            });
        } else {
            return "info,invalid-sort-order;";
        }

        return "";
    }
}