
public class Main {
    public static void main (String args[]){
        // Creates the print queue
        Thread[] threadW=new Thread[10];
        Thread[] threadR=new Thread[20];
        BookCase bookCase = new BookCase(24, threadW.length, threadR.length);

        // Creates the first ten Threads for writers
        for (int i=0; i< threadW.length; i++){
            threadW[i]=new Thread(new Writer(bookCase),"Writer "+i);
            threadW[i].setPriority(Thread.MAX_PRIORITY);
        }
        // Creates another twenty Threads for readers
        for (int i=0; i< threadR.length; i++){
            threadR[i]=new Thread(new Reader(bookCase),"Reader "+i);
            threadR[i].setPriority(Thread.MIN_PRIORITY);
        }

        // Creates thread for log
        Log log = new Log(bookCase);
        Thread threadL = new Thread(log);

        //Checks initial time
        long startTime = System.currentTimeMillis();

        // Starts the Threads
        for (int i=0; i< threadW.length; i++){
            threadW[i].start();
        }

        threadL.start();

        /*try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        for (int i=0; i< threadR.length; i++){
            threadR[i].start();
        }

        //Checks final time when log ends reporting
        try {
            threadL.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long finishTime = System.currentTimeMillis();

        //time execution report
        System.out.printf("\n");
        System.out.printf("------------------------\n");
        System.out.printf("EXECUTION TIME: " + (finishTime - startTime)/1000 + " seconds\n");
        System.out.printf("------------------------\n");
    }

    }
