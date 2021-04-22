import java.io.FileWriter;
import java.io.PrintWriter;

public class Log implements Runnable {
    //PRIVATES VARIABLES
    //------------------------------------------------------------------------------------------------------------------
    private final long SAMPLE_TIME = 1000;
    private final String REPORT_FILE_NAME = "log.txt";

    private BookCase bookCase;
    private int reportNumber = 1;

    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public Log(BookCase bookCase){
        this.bookCase = bookCase;
    }

    //PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {

        //System.out.printf("i am alive");

        String tittle = "##########################################\n" +
                "REPORT OF PROGRAM \n" +
                "Five Threads\n" +
                "##########################################";

        //write a tittle in a file
        this.writeFile(tittle);

        do {
            //wait 2 seconds
            try {
                Thread.sleep(SAMPLE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //take a report sample and print
            String report = this.takeInformation();

            //write a sample in a file
            this.writeFile(report);
        }while (!bookCase.checkAllBooksReady());
    }

    //PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------

    /**
     * @return [String] report
     * @apiNote Print a information log
     */
    private String takeInformation() {
        String report = "";

        //Save report
        report += ("----------------------------------\n");
        report += ("Report " + this.reportNumber +" |\n"+ "----------" +
                "\nTotal number of books:   " + bookCase.getNumberOfBooks() +
                "\nTotal number of books in final version: " + bookCase.getAmountOfBooksInFinalVersion() +
                "\nTotal number of books ready: " + bookCase.getAmountOfBooksReady() +
                "\nBooks Stats\n:" + bookCase.getBooksStats() +
                "\n");
        report += ("----------------------------------\n");
        /*for (int i = 0; i < this.consumers.length; i++) {
            report += ("-----> CONSUMER N: " + this.consumers[i].getID() + "\n");
            report += ("            state: " + this.consumers[i].getState() + "\n");
        }
        report += ("----------------------------------\n");*/

        //increase a report number counter
        this.reportNumber++;

        //print report
        System.out.printf(report);

        return report;
    }

    /**
     * @param report [String]
     * @apiNote write report in a file
     */
    private void writeFile(String report) {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(REPORT_FILE_NAME, true);
            pw = new PrintWriter(file);

            pw.println(report);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != file)
                    file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
