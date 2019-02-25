/**
 * Represents the Bookworm Library. Holds all the books, visitors, and visits to the library.
 * Utilizes the Singleton design pattern
 */
public class BookwormLibrary {

    /**
     * Lazy constructor
     */
    private BookwormLibrary(){}

    /**
     * Creates one instance of the BookwormLibrary
     */
    private static class LibraryInstance{
        private static final BookwormLibrary INSTANCE = new BookwormLibrary();
    }

    /**
     * Retrieve the one and only instance of the BookwormLibrary.
     * @return The instance of the BookwormLibrary
     */
    public static BookwormLibrary getInstance(){
        return LibraryInstance.INSTANCE;
    }
}
