public class ReverseCapsFilter extends Filter {
    protected String doFilter(String s) {
        if(s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if(Character.isLowerCase(c)) {
                c = Character.toUpperCase(c);
            } else if(Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
