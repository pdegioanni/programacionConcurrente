

public class Reader implements Runnable{
    private Book book;

    public Reader(Book book){
        this.book = book;
    }

    @Override
    public void run() {
        System.out.printf("%s: \n",Thread.currentThread().getName());
        System.out.printf("%s: \n",Thread.currentThread().getName());
    }
}
