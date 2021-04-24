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
        Book bookToRead = bookCase.getBook();
        if (bookToRead.isReady()){
            bookToRead = bookCase.getBook();
        }
        else if (bookToRead.isFinalVersion() && !booksRead.contains(bookToRead)){
            //System.out.printf("%s: going to read the final version of book %d \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            if(bookToRead.registerRead(Thread.currentThread().getName())){
                System.out.printf("%s: a read has been registered on book %d \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
                markBookAsRead(bookToRead);
            }
            else{
                System.out.printf("%s: couldn't read on book %d. Writers were waiting \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            }
        }
        else {
            //System.out.printf("%s: going to read book %d (not final version) \n",Thread.currentThread().getName(), bookToRead.getIdNumber());
            if(bookToRead.onlyReadBook(Thread.currentThread().getName())) {
                System.out.printf("%s: read book %d (not final version)\n", Thread.currentThread().getName(), bookToRead.getIdNumber());
            }
        };
    }

    private void markBookAsRead(Book book){
        booksRead.add(book);
    }

    private void waitForLog(){
        try {
            wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}