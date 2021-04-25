import java.util.ArrayList;
import java.util.Date;

public class Reader implements Runnable{

    //PRIVATE FIELDS
    //------------------------------------------------------------------------------------------------------------------
    private BookCase bookCase;
    private ArrayList<Book> booksRead;

    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public Reader(BookCase bookCase){
        this.bookCase = bookCase;
        booksRead = new ArrayList<>();
    }

    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        do {
            readBook();
        } while(booksRead.size() < bookCase.getNumberOfBooks());
    }

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
    private void readBook(){
        Book bookToRead = bookCase.getBook();

        if (bookToRead.isFinalVersion()){
            if(!booksRead.contains(bookToRead)){
                bookToRead.registerRead(Thread.currentThread().getName());
                markBookAsRead(bookToRead);
            }
            /*else{
                bookToRead.onlyReadBook(Thread.currentThread().getName());
            }*/
        }


        else {
            //System.out.printf("%s: going to read book %d (not final version) \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            //if(!bookToRead.onlyReadBook(Thread.currentThread().getName())) {
                //System.out.printf("%s %s: read book %d (not final version)\n", new Date().getTime(),Thread.currentThread().getName(), bookToRead.getIdNumber());
            //}
            //else{
                //System.out.printf("%s %s: couldn't read book %d. Writers go first \n",new Date().getTime(),Thread.currentThread().getName(), bookToRead.getIdNumber());
            bookToRead.onlyReadBook(Thread.currentThread().getName());
           //bookToRead.lockPriority(false, Thread.currentThread().getName());
            //}
        }
    }

    private void markBookAsRead(Book book){
        booksRead.add(book);
    }

    /*private void waitForLog(){
        try {
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
}