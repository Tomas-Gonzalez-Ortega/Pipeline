public class CamelCapsFilter extends Filter {
    public String doFilter(String s) {
        if(s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if(i % 2 == 0) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
