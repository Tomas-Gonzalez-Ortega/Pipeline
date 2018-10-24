import java.io.*;
import java.util.*;

/** A pipeline to filter arguments. In a pipeline, some data source (called a "pump") produces input for the pipeline.
Each filter in the pipeline performs some transformation on the data.
The output of the pump is the input to the first filter, the output of the first filter is the input to the second filter, the output of the second filter is the input to the third filter, and so on.
Eventually, the final filter's output is sent to the pipeline's final destination, called a "sink."
    @author Jeremy Hilliker @ Langara
    @author Tomas Gonzalez Ortega
    @version 2017-06-17 12h37
    @see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=50949&grpid=0&isprv=0&bp=0&ou=88736">a 06: pipes and filters</a>
*/

/**
* PrintStreamSink will print a given String into a provided PrintStream.
*/
public class PrintStreamSink extends Filter {

    private final PrintStream sink;

    /** This method will create a constructor of PrintStreamSink with a given PrintStreamSink.
         @param ps the given PrintStream that contains ByteArrayOutputStream
    */
    public PrintStreamSink(PrintStream aPrintStream) {
        sink = aPrintStream;
    }

    /**  Prints a given string into the provided PrintStream.
         @param s the string to print
         @return null since the sink wont pass any value to other filters
    */
    protected String doFilter(String s) {
        sink.print(s);
        return null;
    }
}
