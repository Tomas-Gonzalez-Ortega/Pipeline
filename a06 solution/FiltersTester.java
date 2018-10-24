import java.io.*;

public class FiltersTester {

	public static void main(String[] args) {
		testTrimFilter();
		testTrimFilter_special();
		testUpperCaseFilter();
		testUpperCaseFilter_special();
		testLowerCaseFilter();
		testLowerCaseFilter_special();
		testCamelCapsFilter();
		testCamelCapsFilter_special();
		testCamelCapsFilter_provided();
		testReverseCapsFilter();
		testReverseCapsFilter_special();
		testReverseCapsFilter_provided();
		testPrintStreamSink();
		testPrintStreamSink_special();
		testPrintStreamSink_provided();
		testPipeline();
		testPipeline_special();
	}

	private static void testPipeline_special() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it

		Filter f = new TrimFilter()
			.add(new CamelCapsFilter())
			.add(new ReverseCapsFilter())
			.add(new PrintStreamSink(ps));

		baos.reset();
		f.filter(null);
		assert "null".equals(baos.toString());
		baos.reset();
		f.filter("");
		assert "".equals(baos.toString());
	}

	private static void testPipeline() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it

		Filter f = new TrimFilter()
			.add(new CamelCapsFilter())
			.add(new ReverseCapsFilter())
			.add(new PrintStreamSink(ps));

		// bound lower
		baos.reset();
		f.filter(" az ");
		assert "aZ".equals(baos.toString());
		// bounds Upper
		baos.reset();
		f.filter(" AZ ");
		assert "aZ".equals(baos.toString());
		// typical mixed
		baos.reset();
		f.filter("I am a String.   ");
		assert "i aM A StRiNg.".equals(baos.toString());
	}

	private static void testPrintStreamSink_provided() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it
		Filter sink = new PrintStreamSink(ps); // create the sink to print to the buffer
		sink.doFilter("A secret message.");    // runs the filter -> on the sink, prints to the buffer
		assert baos.toString().equals("A secret message.");  // verifies that contents of buffer match expected
	}

	private static void testPrintStreamSink_special() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it
		Filter f = new PrintStreamSink(ps); // the sink

		baos.reset();
		f.doFilter(null);
		assert "null".equals(baos.toString());
		baos.reset();
		f.doFilter("");
		assert "".equals(baos.toString());
	}

	private static void testPrintStreamSink() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // a buffer to hold the filter's output
		PrintStream ps = new PrintStream(baos); // a printstream wrapping the buffer so that the sink can write to it
		Filter f = new PrintStreamSink(ps); // the sink

		// bound lower
		baos.reset();
		f.doFilter("az");
		assert "az".equals(baos.toString());
		// bounds Upper
		baos.reset();
		f.doFilter("AZ");
		assert "AZ".equals(baos.toString());
		// typical mixed
		baos.reset();
		f.doFilter("I am a String.");
		assert "I am a String.".equals(baos.toString());
	}

	private static void testReverseCapsFilter_provided() {
		Filter f = new ReverseCapsFilter();

		assert "hELLO wORLD!".equals(f.doFilter("Hello World!"));
	}

	private static void testReverseCapsFilter_special() {
		Filter f = new ReverseCapsFilter();

		assert null == f.doFilter(null);
		assert "".equals(f.doFilter(""));
	}

	private static void testReverseCapsFilter() {
		Filter f = new ReverseCapsFilter();

		// bound lower
		assert "AZ".equals(f.doFilter("az"));
		// bounds Upper
		assert "az".equals(f.doFilter("AZ"));
		// typical mixed
		assert "i AM A sTRING.".equals(f.doFilter("I am a String."));
	}

	private static void testCamelCapsFilter_provided() {
		Filter f = new CamelCapsFilter();

		assert "HeLlO WoRlD!".equals(f.doFilter("hello WORLD!"));
	}

	private static void testCamelCapsFilter_special() {
		Filter f = new CamelCapsFilter();

		assert null == f.doFilter(null);
		assert "".equals(f.doFilter(""));
	}

	private static void testCamelCapsFilter() {
		Filter f = new CamelCapsFilter();

		// bound lower
		assert "Az".equals(f.doFilter("az"));
		// bounds Upper
		assert "Az".equals(f.doFilter("AZ"));
		// typical mixed
		assert "I Am a sTrInG.".equals(f.doFilter("I am a String."));
	}

	private static void testLowerCaseFilter_special() {
		Filter f = new LowerCaseFilter();

		assert null == f.doFilter(null);
		assert "".equals(f.doFilter(""));
	}

	private static void testLowerCaseFilter() {
		Filter f = new LowerCaseFilter();

		// bound lower
		assert "az".equals(f.doFilter("az"));
		// bounds Upper
		assert "az".equals(f.doFilter("AZ"));
		// typical mixed
		assert "i am a string.".equals(f.doFilter("I am a String."));
	}

	private static void testUpperCaseFilter_special() {
		Filter f = new UpperCaseFilter();

		assert null == f.doFilter(null);
		assert "".equals(f.doFilter(""));
	}

	private static void testUpperCaseFilter() {
		Filter f = new UpperCaseFilter();

		// bound lower
		assert "AZ".equals(f.doFilter("az"));
		// bounds Upper
		assert "AZ".equals(f.doFilter("AZ"));
		// typical mixed
		assert "I AM A STRING.".equals(f.doFilter("I am a String."));
	}

	private static void testTrimFilter_special() {
		Filter f = new TrimFilter();
		String s;

		s = "";			// empty
		assert "".equals(f.doFilter(s));
		s = null;		// null
		assert null == f.doFilter(s);
	}

	private static void testTrimFilter() {
		Filter f = new TrimFilter();
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
	}
}
