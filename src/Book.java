import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Book {
    private int reviews;
    private int reads;
    private ReadWriteLock lock;

    public Book(){
        reviews = 0;
        reads = 0;
        lock = new ReentrantReadWriteLock();
    }

    public int readBook() {
        lock.readLock().lock();
        int value=reads;
        System.out.printf("Tiempo Lectura 2: %s, Hilo %s\n", System.currentTimeMillis(),Thread.currentThread().getName());
        lock.readLock().unlock();
        return value;
    }
}
