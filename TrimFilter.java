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
* TrimFilter will remove the leading and trailing whitespace from a string.
*/
public class TrimFilter extends Filter {

    /**
    * TrimFilter will remove the leading and trailing whitespace from a string.
    * @param s the string to apply the filter's transformation to
    * @return a string whose value is the given string,
    *   with any leading and trailing whitespace removed. AKN: Javadoc
    */
    protected String doFilter(String s) {
        return s != null ? s.trim() : null;
    }
}
