package com.example.proyectofinal.util;

import java.text.Normalizer;

public class StringUtil {
    /**
     * This method is used to normalize strings with accents.
     * @param s not normalized string.
     * @return normalized string.
     */
    public static String normalizeString(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
