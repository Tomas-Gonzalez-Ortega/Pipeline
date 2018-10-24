import java.io.PrintStream;

public class PrintStreamSink extends Filter {
    private final PrintStream sink;

    public PrintStreamSink(PrintStream aSink) {
        sink = aSink;
    }

    protected String doFilter(String s) {
        sink.print(s);
        return s;
    }
}
