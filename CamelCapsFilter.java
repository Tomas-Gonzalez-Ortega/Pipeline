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
* CamelCapsFilter alternates the capitalization of each position in the string.
*/
public class CamelCapsFilter extends Filter {

    /**
    * CamelCapsFilter alternates the capitalization of each position in the string so that every even position is in upper case, and every odd position is in lower case.  String indexes start at 0..
    * @param s the string to apply the filter's transformation to
    * @return a string whose value is the given string,
    *   with any capitalization alternated. AKN: Javadoc
    */
    protected String doFilter(String s) {
        if (s == null) {
            return null;
        } else {
            String temp = "";
            for(int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                temp += ( i % 2 == 0) ? Character.toUpperCase(c) : Character.toLowerCase(c);
            }
            s = temp;
            return s;
        }
    }
}
