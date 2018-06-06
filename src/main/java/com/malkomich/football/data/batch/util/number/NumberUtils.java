package com.malkomich.football.data.batch.util.number;

public class NumberUtils {

    public static Integer parseInt(final String string) {
        if (string == null) {
            return null;
        }
        return Integer.parseInt(string);
    }

    public static Double parseDouble(final String string) {
        if (string == null) {
            return null;
        }
        return Double.parseDouble(string);
    }
}
