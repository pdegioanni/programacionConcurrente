import java.util.ArrayList;

public class Writer implements Runnable{
    private BookCase bookCase;
    private ArrayList<Book> booksReviewed;

    public Writer(BookCase bookCase){
        this.bookCase = bookCase;
        booksReviewed = new ArrayList<>();
    }

    @Override
    public void run() {
        do {
            reviewBook();
        } while(booksReviewed.size() < bookCase.getNumberOfBooks());
    }

    private void reviewBook(){
        Book bookToReview = bookCase.getBook(booksReviewed);
        System.out.printf("%s: going to write a review on book %d \n",Thread.currentThread().getName(), bookToReview.getIdNumber());
        bookToReview.writeReview();
        System.out.printf("%s: a review has been written on book %d \n",Thread.currentThread().getName(), bookToReview.getIdNumber());
        markBookAsReviewed(bookToReview);
    }

    private void markBookAsReviewed(Book book){
        booksReviewed.add(book);
    }
}
