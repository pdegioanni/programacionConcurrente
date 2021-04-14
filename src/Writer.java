public class Writer implements Runnable{
    private Book book;

    public Writer(Book book){
        this.book = book;
    }

    @Override
    public void run() {
        System.out.printf("%s: \n",Thread.currentThread().getName());
        System.out.printf("%s: \n",Thread.currentThread().getName());
    }
}
