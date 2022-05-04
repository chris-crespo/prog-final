package utils;

import java.util.function.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Parse {
    public static Predicate<String> withParsedString(Predicate<Integer> pred) {
        return str -> {
            try {
                var x = Integer.parseInt(str);
                return pred.test(x);
            }
            catch (Exception exn) {
                return false;
            }
        };
    }

    public static int parseIntOrDefault(String str, int def) {
        try {
            return Integer.parseInt(str);
        }
        catch (Exception exn) {
            return def;
        }
    }

    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Predicate<String> withParsedDate(Predicate<LocalDate> pred) {
        return str -> {
            try {
                return pred.test(parseDate(str));
            }
            catch (Exception exn) {
                return false;
            }
        };
    }
}
