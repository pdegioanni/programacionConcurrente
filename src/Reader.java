import java.util.ArrayList;

public class Reader implements Runnable{
    private BookCase bookCase;
    private ArrayList<Book> booksRead;

    public Reader(BookCase bookCase){
        this.bookCase = bookCase;
        booksRead = new ArrayList<>();
    }

    @Override
    public void run() {
        do {
            readBook();
        } while(booksRead.size() < bookCase.getNumberOfBooks());
    }

    private void readBook(){
        Book bookToRead = bookCase.getBook(booksRead);
        if(bookToRead.isFinalVersion()){
            System.out.printf("%s: going to read the final version of book %d \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            bookToRead.registerRead();
            System.out.printf("%s: a read has been registered on book %d \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            markBookAsRead(bookToRead);
        }
        //else bookToRead.readBook()
    }

    private void markBookAsRead(Book book){
        booksRead.add(book);
    }
}