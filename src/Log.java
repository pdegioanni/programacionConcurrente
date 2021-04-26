import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class Log implements Runnable {
    //PRIVATES VARIABLES
    //------------------------------------------------------------------------------------------------------------------
    private final long SAMPLE_TIME = 2000;
    private final String REPORT_FILE_NAME = "log.txt";

    private final BookCase bookCase;
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

        String tittle = "##########################################\n" +
                new Date() + "\n" +
                "REPORT OF PROGRAM \n" +
                "Thirty Threads\n" +
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

            //write sample in a file
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
                bookCase.getBooksStats() +
                "\n");
        report += ("----------------------------------\n");

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
