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
    private boolean ready;
    private Object controlReads;
    private ReadWriteLock finalVersionLock, readyLock;
    private ReentrantReadWriteLock bookLock;
    private int writersWaiting;

    public Book(int idNumber, int numberOfWriters, int numberOfReaders ){
        this.idNumber = idNumber;
        this.numberOfWriters = numberOfWriters;
        this.numberOfReaders =numberOfReaders;
        reviews = 0;
        reads = 0;
        ready= false;
        finalVersion =false;
        bookLock = new ReentrantReadWriteLock(true);
        finalVersionLock = new ReentrantReadWriteLock(true);
        readyLock  = new ReentrantReadWriteLock(true);
        controlReads = new Object();
        writersWaiting = 0;
    }

    public boolean isReady(){
        bookLock.readLock().lock();
        boolean r = ready;
        bookLock.readLock().unlock();
        return r;
    }

    public boolean isFinalVersion(){
        //bookLock.readLock().lock();
        boolean r = finalVersion;
        //bookLock.readLock().unlock();
        return r;
    }

    public void writeReview(String writerId){
        if(!bookLock.writeLock().tryLock()){
            writersWaiting ++;
            bookLock.writeLock().lock();
            writersWaiting --;
        }
        System.out.printf("%s: going to write a review on book %d \n",writerId, this.getIdNumber());
        reviews ++;
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            bookLock.writeLock().unlock();
        }
        checkFinalVersion();
    }

    public boolean registerRead(String readerId) {
        if(writersWaiting == 0){
            bookLock.readLock().lock();
            System.out.printf("%s: going to read final version of book %d \n",readerId, this.getIdNumber());
            incrementReads();
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                bookLock.readLock().unlock();
            }
            checkReady();
            return  true;
        }
        return false;
    }

    public void incrementReads(){
        synchronized (controlReads) {
            reads ++;
        }
    }

    public boolean onlyReadBook(String readerId){
        if(writersWaiting == 0){
            bookLock.readLock().lock();
            System.out.printf("%s: going to read book %d (not final version) \n",readerId, this.getIdNumber());
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                bookLock.readLock().unlock();
            }
            return true;
        }
        return false;
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
