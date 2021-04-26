import java.util.ArrayList;

public class Writer implements Runnable{

    //PRIVATE FIELDS
    //------------------------------------------------------------------------------------------------------------------
    private final BookCase bookCase;
    private ArrayList<Book> booksReviewed;

    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public Writer(BookCase bookCase){
        this.bookCase = bookCase;
        booksReviewed = new ArrayList<>();
    }

    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        do {
            reviewBook();
        } while(booksReviewed.size() < bookCase.getNumberOfBooks());
    }

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
    private void reviewBook(){
        Book bookToReview = bookCase.getBook(booksReviewed);
        bookToReview.writeReview(Thread.currentThread().getName());
        markBookAsReviewed(bookToReview);
    }

    private void markBookAsReviewed(Book book){
        booksReviewed.add(book);
    }
}
