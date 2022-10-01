import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class PostFix {

    private static boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] args) {
        String in = "4 20 + 3 5 1 * * +";
        Stack<Integer> stack = new Stack<>();
        ArrayList<String> exp = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(in);
        while (tokenizer.hasMoreTokens()) {
            exp.add(tokenizer.nextToken());
        }
        for (String s : exp) {
            if (isInt(s))
                stack.add(Integer.parseInt(s));
            else {
                Integer one = stack.pop();
                Integer two = stack.pop();
                switch (s) {
                    case "+":
                        stack.push(two + one);
                        break;
                    case "-":
                        stack.push(two - two);
                        break;
                    case "*":
                        stack.push(two * one);
                        break;
                    case "/":
                        stack.push(two / one);
                        break;
                }
            }
        }
        System.out.println(stack.pop());
    }
}
