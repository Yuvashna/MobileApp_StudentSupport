package codeconverters.loginpage;

public class Validate {

    public static boolean isText(String text) {
        if (text.isEmpty()) {
            return false;
        }
        if ((text == "(.*[A-Z].*)") || (text == "(.*[a-z].*)")) {
            return false;
        }
        return true;
    }
    public static boolean isValidPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }
        return true;
    }
}