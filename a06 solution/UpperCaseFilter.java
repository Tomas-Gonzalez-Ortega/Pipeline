public class UpperCaseFilter extends Filter {
    protected String doFilter(String s) {
        return s != null ? s.toUpperCase() : null;
    }
}
