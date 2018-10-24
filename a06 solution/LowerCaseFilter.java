public class LowerCaseFilter extends Filter {
    protected String doFilter(String s) {
        return s != null ? s.toLowerCase() : null;
    }
}
