public class Main {
    public static void main (String args[]){
        // Creates the print queue
        Book book =new Book();

        // Creates the first ten Threads for writers
        Thread thread[]=new Thread[30];
        for (int i=0; i<10; i++){
            thread[i]=new Thread(new Writer(book),"Writer thread"+i);
        }

        // Creates the last 20 threads for readers Threads for Readers
        for (int i=10; i<30; i++){
            thread[i]=new Thread(new Reader(book),"Reader thread"+i);
        }

        // Starts the Threads
        for (int i=0; i<30; i++){
            thread[i].start();
        }
    }

}
