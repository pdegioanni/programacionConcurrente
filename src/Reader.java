import java.util.ArrayList;

public class Reader implements Runnable{

    //PRIVATE FIELDS
    //------------------------------------------------------------------------------------------------------------------
    private final BookCase bookCase;
    private ArrayList<Book> booksReadInFinalVersion;

    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public Reader(BookCase bookCase){
        this.bookCase = bookCase;
        booksReadInFinalVersion = new ArrayList<>();
    }

    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        do {
            readBook();
        } while(booksReadInFinalVersion.size() < bookCase.getNumberOfBooks());
    }

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
    private void readBook(){
        Book bookToRead = bookCase.getBook();

        if (bookToRead.isFinalVersion()){
            if(!booksReadInFinalVersion.contains(bookToRead)){
                bookToRead.registerRead(Thread.currentThread().getName());
                markBookAsRead(bookToRead);
            }
        }

        else {
            bookToRead.onlyReadBook(Thread.currentThread().getName());
        }
    }

    private void markBookAsRead(Book book){
        booksReadInFinalVersion.add(book);
    }

}