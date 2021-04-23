import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Book {
    private final int idNumber;
    private final int numberOfWriters;
    private final int numberOfReaders;
    private int reviews;
    private int reads;
    private  boolean finalVersion;
    private boolean ready; // indica que el libro
    private ReadWriteLock reviewsLock, readsLock, finalVersionLock, readyLock;

    public Book(int idNumber, int numberOfWriters, int numberOfReaders ){
        this.idNumber = idNumber;
        this.numberOfWriters = numberOfWriters;
        this.numberOfReaders =numberOfReaders;
        reviews = 0;
        reads = 0;
        ready= false;
        finalVersion =false;
        reviewsLock= new ReentrantReadWriteLock(true);
        readsLock = new ReentrantReadWriteLock(true);
        finalVersionLock = new ReentrantReadWriteLock(true);
        readyLock  = new ReentrantReadWriteLock(true);
    }

    public boolean isReady(){
        readyLock.readLock().lock();
        boolean r = ready;
        readyLock.readLock().unlock();
        return r;
    }

    public boolean isFinalVersion(){
        finalVersionLock.readLock().lock();
        boolean r = finalVersion;
        finalVersionLock.readLock().unlock();
        return r;
    }

    public void writeReview(){
        reviewsLock.writeLock().lock();
        reviews ++;
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            reviewsLock.writeLock().unlock();
        }
        checkFinalVersion();
    }

    public void registerRead() {
        readsLock.writeLock().lock();
        reads ++;
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            readsLock.writeLock().unlock();
        }
        checkReady();
    }

    // public void readBook(){
    // }

    public int getIdNumber() {
        return idNumber;
    }

    public int getReviews() {
        reviewsLock.readLock().lock();
        int r = reviews;
        reviewsLock.readLock().unlock();
        return r;
    }

    public int getReads() {
        readsLock.readLock().lock();
        int r = reads;
        readsLock.readLock().unlock();
        return r;
    }


    private void checkFinalVersion(){
        reviewsLock.readLock().lock();
        int numberOfReviews = reviews;
        reviewsLock.readLock().unlock();
        if(numberOfReviews == numberOfWriters) {
            finalVersionLock.writeLock().lock();
            finalVersion = true;
            finalVersionLock.writeLock().unlock();
        }
    }
    private void checkReady(){
        readsLock.readLock().lock();
        int numberOfReads = reads;
        readsLock.readLock().unlock();
        if(numberOfReads == numberOfReaders) {
            readyLock.writeLock().lock();
            ready = true;
            readyLock.writeLock().unlock();
        }
}
