import java.util.ArrayList;
import java.util.Date;

public class Writer implements Runnable{

    //PRIVATE FIELDS
    //------------------------------------------------------------------------------------------------------------------
    private BookCase bookCase;
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

    public ArrayList<Book> getBooksReviewed() {
        return booksReviewed;
    }

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
    private void reviewBook(){
        Book bookToReview = bookCase.getBook(booksReviewed);
        //System.out.printf("%s: going to write a review on book %d \n",Thread.currentThread().getName(), bookToReview.getIdNumber());
        bookToReview.writeReview(Thread.currentThread().getName());
        //bookToReview.lockPriority(true, Thread.currentThread().getName());
        //System.out.printf("%s %s: a review has been written on book %d \n",new Date().getTime(), Thread.currentThread().getName(), bookToReview.getIdNumber());
        markBookAsReviewed(bookToReview);
    }

    private void markBookAsReviewed(Book book){
        booksReviewed.add(book);
    }
   /* private void waitForLog(){
        try {
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
}
