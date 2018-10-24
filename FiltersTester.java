import java.io.*;
import java.util.*;

/** A tester to check the pipeline in various ways.
    @author Tomas Gonzalez Ortega @ Langara
    @version 2017-06-17 12h35
    @see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=50949&grpid=0&isprv=0&bp=0&ou=88736">a 06: pipes and filters</a>
*/

public class FiltersTester {
	public static void main(String[] args) {
		testTrimFilter();
		testReverseCapsFilter();
		testCamelCapsFilter();
		testLowerCapsFilter();
		testUpperCapsFilter();
		testPrintStreamSink();
		System.err.println("*** PASSED ***");
	}

	private static void testTrimFilter() {
		TrimFilter f = new TrimFilter();
		String s;

		// typical
		s = "i am a string";	// no spaces
		assert s.equals(f.doFilter(s));
		s = "    leading";		// leading
		assert "leading".equals(f.doFilter(s));
		s = "trailing    ";		// trailing
		assert "trailing".equals(f.doFilter(s));
		s = "  leading & trailing  ";	// both
		assert "leading & trailing".equals(f.doFilter(s));
		s = "  a     b  ";				// both with gap
		assert "a     b".equals(f.doFilter(s));
		s = "\tx\t";		// tabs
		assert "x".equals(f.doFilter(s));

		// boundary
		s = "    ";		// all spaces
		assert "".equals(f.doFilter(s));

		// special
		s = "";			// empty
		assert "".equals(f.doFilter(s));

		// special
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testReverseCapsFilter() {
		ReverseCapsFilter f = new ReverseCapsFilter();
		String s;

		// typical
		s = "    leading";		// leading
		assert "    LEADING".equals(f.doFilter(s));
		s = "tRAILING   ";		// trailing
		assert "Trailing   ".equals(f.doFilter(s));
		s = "  leadING & trailING  ";	// both
		assert "  LEADing & TRAILing  ".equals(f.doFilter(s));
		s = "  a     B  ";				// both with gap
		assert "  A     b  ".equals(f.doFilter(s));

		// special
		s = "a23B";			// numeric characters
		assert "A23b".equals(f.doFilter(s));

		// special
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testCamelCapsFilter() {
		CamelCapsFilter f = new CamelCapsFilter();
		String s;

		// typical
		s = "leading";		// leading
		assert "LeAdInG".equals(f.doFilter(s));
		s = "a     B";				// both with gap
		assert "A     B".equals(f.doFilter(s));
		s = "\tX\t";		// tabs
		assert "\tx\t".equals(f.doFilter(s));

		// special
		s = "a23B";			// numeric characters
		assert "A23b".equals(f.doFilter(s));

		// special
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testLowerCapsFilter() {
		LowerCapsFilter f = new LowerCapsFilter();
		String s;

		// typical
		s = "LEADING";		// typical
		assert "leading".equals(f.doFilter(s));
		s = "a     B";				// both with gap
		assert "a     b".equals(f.doFilter(s));
		s = "\tX\t";		// tabs
		assert "\tx\t".equals(f.doFilter(s));

		// special
		s = "a23B";			// numeric characters
		assert "a23b".equals(f.doFilter(s));

		// special
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testUpperCapsFilter() {
		UpperCapsFilter f = new UpperCapsFilter();
		String s;

		// typical
		s = "leading";	// typical
		assert "LEADING".equals(f.doFilter(s));
		s = "a     B";				// both with gap
		assert "A     B".equals(f.doFilter(s));
		s = "\tx\t";		// tabs
		assert "\tX\t".equals(f.doFilter(s));

		// special
		s = "a23B";			// numeric characters
		assert "A23B".equals(f.doFilter(s));

		// special
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testPrintStreamSink() {
		// prints "Hello World!" to console.
		 Filter hey = new PrintStreamSink(System.out);
		 hey.filter("Hello World!");

		// a reproducible, automated test:
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it
		Filter sink = new PrintStreamSink(ps); // create the sink to print to the buffer
		sink.doFilter("A secret message.");    // runs the filter -> on the sink, prints to the buffer
		assert baos.toString().equals("A secret message.");  // verifies that contents of buffer match expected
	}

	static {
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Intentional side effect!!!
		if (!assertsEnabled) {
			throw new RuntimeException("Asserts must be enabled!!! java -ea");
		}
    }
}
