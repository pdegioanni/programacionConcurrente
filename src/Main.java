import java.util.concurrent.TimeUnit;

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
        for (int i=0; i< threadR.length; i++){
            threadR[i]=new Thread(new Reader(bookCase),"Reader "+i);
            threadR[i].setPriority(Thread.MIN_PRIORITY);
        }
        Log log = new Log(bookCase);
        Thread threadL = new Thread(log);


        //check initial time
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


        //check finish time
        try {
            threadL.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    /*
        log2 log = new log2(bookCase);
        log.startReport();

        do {
            //wait 2 seconds
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //take a report sample and print
            for (int i=0; i< threadW.length; i++){
                try {
                    threadW[i].waitForLog();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0; i< threadR.length; i++){
                try {
                    threadR[i].waitForLog();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.takeInformation();*/
           /* for (int i=0; i< threadW.length; i++){
            threadW.notifyAll();
            }
            for (int i=0; i< threadR.length; i++){
                threadR.notifyAll();
                } while (!bookCase.checkAllBooksReady());
            }*/



        long finishTime = System.currentTimeMillis();

        //time execution report
        System.out.printf("\n");
        System.out.printf("------------------------\n");
        System.out.printf("EXECUTION TIME: " + (finishTime - startTime)/1000 + " seconds\n");
        System.out.printf("------------------------\n");
    }

    }
