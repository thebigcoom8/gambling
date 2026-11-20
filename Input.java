package gambling.util;

public class Input {
    // true if the answer starts with "y" (yes, y, yeah, ...)
    public static boolean isYes(String answer) {
        return answer.trim().toLowerCase().startsWith("y");
    }
}
