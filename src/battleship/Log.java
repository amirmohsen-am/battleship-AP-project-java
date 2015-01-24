package battleship;

import java.io.*;

/** Writes output
 *
 * @author Ahanchi
 */
public class Log{
    private boolean printToFile = false;
    private boolean printToConsole = false;
    private PrintWriter writer = null;

    public Log(boolean printToConsole) {
        this.printToConsole = printToConsole;
        this.printToFile = false;
    }

    /** Constructs a stance of class and trues to write to the file
     *
     * @param printToConsole weather to print to console or to file
     * @param fileName name of the file which we want to write to
     * @throws java.io.FileNotFoundException if the file could not be found
     * @throws java.io.UnsupportedEncodingException if the encoding is not supported
     */
    public Log(boolean printToConsole, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        this.printToConsole = printToConsole;
        printToFile = true;
        writer = new PrintWriter(fileName, "UTF-8");
    }

    /** Prints a string to the output
     *
     * @param string the string to write to output
     */
    public void print(String string) {
        if (printToConsole)
            System.out.print(string);
        if (printToFile)
            writer.print(string);
    }

    /** Prints a string to output and goes to a newline
     *
     * @param string the string to write to output
     */
    public void println(String string) {
        print(string + "\n");
    }

    /** Closes the file */
    public void close() {
        if (printToFile)
            writer.close();
    }

}
