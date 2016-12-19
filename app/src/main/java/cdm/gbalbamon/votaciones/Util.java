package cdm.gbalbamon.votaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gerardo on 19/12/2016.
 */

public class Util {
    static public boolean dniOK(String dni) {
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern pattern = Pattern.compile("(\\d{8})([" + letters + "])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            int number = Integer.parseInt(matcher.group(1));
            String letter = matcher.group(2);
            char correctLetter = letters.charAt(number % 23);
            if (letter.toUpperCase().charAt(0) == correctLetter) return true;
        }
        return false;
    }
}
