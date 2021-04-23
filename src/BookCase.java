import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class BookCase {
    //PRIVATES VARIABLES
    //------------------------------------------------------------------------------------------------------------------
    private ArrayList<Book> allBooks;
    private final int  numberOfBooks;
    private final int numberOfWriters;
    private final int numberOfReaders;

    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public BookCase(int numberOfBooks, int numberOfWriters, int numberOfReaders){
        allBooks = new ArrayList<>();
        this.numberOfBooks = numberOfBooks;
        this.numberOfWriters = numberOfWriters;
        this.numberOfReaders = numberOfReaders;
        for (int i=0; i<numberOfBooks; i++){
            allBooks.add(new Book(i, numberOfWriters, numberOfReaders));
        }
    }
    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    public Book getBook(ArrayList<Book> notValidBooks){
        Random rand = new Random();
        Book bookSelected;
        List<Book> validBooks = allBooks.stream().filter(book -> !notValidBooks.contains(book)).collect(Collectors.toList());

        bookSelected = validBooks.get(rand.nextInt(validBooks.size()));
        return bookSelected;
    }

    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public boolean checkAllBooksReady(){
        boolean allBooksReady = false;
        long booksReady = allBooks.stream().filter(book -> book.isReady()).count();
        if(booksReady == numberOfBooks){
            allBooksReady = true;
        }
        return allBooksReady;
    }

    public String getBooksStats(){
        StringBuilder result = new StringBuilder();
        for(Book book : allBooks){
            result.append("Book ").append(book.getIdNumber()).append(": has ").append(book.getReviews()).append(" reviews and ").append(book.getReads()).append(" reads\n");
        }
        return result.toString();
    }

    public int getAmountOfBooksInFinalVersion(){
        int booksInFinalVersion = 0;
        for (Book book: allBooks){
            if(book.isFinalVersion()){
                booksInFinalVersion++;
            }
        }
        return booksInFinalVersion;
    }

    public int getAmountOfBooksReady(){
        int booksReady = 0;
        for(Book book: allBooks){
            if(book.isReady()) {
                booksReady++;
            }
        }
        return booksReady;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

}
