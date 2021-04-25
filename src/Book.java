import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Book {

    //PRIVATE FIELDS
    //------------------------------------------------------------------------------------------------------------------
    private final int idNumber;
    private final int numberOfWriters;
    private final int numberOfReaders;
    private int reviews;
    private int reads;
    private boolean finalVersion;
    private boolean ready;
    private final Semaphore writerEntry;
    private final ReentrantReadWriteLock bookLock;
    private final ReentrantReadWriteLock finalVersionLock;
    private final ReentrantReadWriteLock readyLock;
    private final Object controlReads;


    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public Book(int idNumber, int numberOfWriters, int numberOfReaders ){
        this.idNumber = idNumber;
        this.numberOfWriters = numberOfWriters;
        this.numberOfReaders =numberOfReaders;
        reviews = 0;
        reads = 0;
        finalVersion =false;
        ready= false;
        writerEntry = new Semaphore(1, true);
        bookLock = new ReentrantReadWriteLock(true);
        finalVersionLock = new ReentrantReadWriteLock(true);
        readyLock  = new ReentrantReadWriteLock(true);
        controlReads = new Object();
    }

    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------

    public void writeReview(String writerId){
        try {
            writerEntry.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.printf("%s %s: waiting to write a review on book %d, writers waiting: %d\n", new Date().getTime(), writerId, this.getIdNumber(), writerEntry.getQueueLength());
            bookLock.writeLock().lock();
            writerEntry.release();

        System.out.printf("%s %s: writing a review on book %d\n", new Date().getTime(),writerId, this.getIdNumber());
        reviews ++;

        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*150));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.printf("%s %s: a review has been written on book %d \n",new Date().getTime(), writerId, this.getIdNumber());
            bookLock.writeLock().unlock();
        }
        checkFinalVersion();
    }

    public void registerRead(String readerId) {
            bookLock.readLock().lock();
            System.out.printf("%s %s: going to read final version of book %d \n",new Date().getTime(), readerId, this.getIdNumber());
            incrementReads();
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*150));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                System.out.printf("%s %s: a read has been registered on book %d \n",new Date().getTime(),readerId, this.getIdNumber());
                bookLock.readLock().unlock();
            }
            checkReady();
    }

    public void onlyReadBook(String readerId){
        bookLock.readLock().lock();
        if(!writerEntry.hasQueuedThreads()){
            System.out.printf("%s %s: reading book %d, writers waiting: %d\n",new Date().getTime(), readerId, this.getIdNumber(), writerEntry.getQueueLength());
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*150));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                System.out.printf("%s %s: read book %d, writers waiting: %d\n", new Date().getTime(),readerId, this.getIdNumber(), writerEntry.getQueueLength());
                bookLock.readLock().unlock();
            }
        }
        else{
            bookLock.readLock().unlock();
            System.out.printf("%s %s: couldn't read book %d, writers waiting: %d\n",new Date().getTime(),readerId, this.getIdNumber(), writerEntry.getQueueLength());
        }
    }

    public void incrementReads(){
        synchronized (controlReads) {
            reads ++;
        }
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

    public int getIdNumber() {
        return idNumber;
    }

    public int getReviews() {
        bookLock.readLock().lock();
        int r = reviews;
        bookLock.readLock().unlock();
        return r;
    }

    public int getReads() {
        bookLock.readLock().lock();
        int r = reads;
        bookLock.readLock().unlock();
        return r;
    }


    // PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------

    private void checkFinalVersion(){
        bookLock.readLock().lock();
        int numberOfReviews = reviews;
        bookLock.readLock().unlock();
        if(numberOfReviews == numberOfWriters) {
            finalVersionLock.writeLock().lock();
            finalVersion = true;
            finalVersionLock.writeLock().unlock();
        }
    }
    private void checkReady(){
        bookLock.readLock().lock();
        int numberOfReads = reads;
        bookLock.readLock().unlock();
        if(numberOfReads == numberOfReaders) {
            readyLock.writeLock().lock();
            ready = true;
            readyLock.writeLock().unlock();
        }
    }
}