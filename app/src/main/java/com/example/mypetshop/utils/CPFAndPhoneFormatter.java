package com.example.mypetshop.utils;

public class CPFAndPhoneFormatter {
    public static String formatCPF(String s) {
        String cleanText = s.replaceAll("[^\\d]", "");

        if (cleanText.length() <= 3) {
            return cleanText;
        } else if (cleanText.length() <= 6) {
            return cleanText.replaceFirst("(\\d{3})(\\d+)", "$1.$2");
        } else if (cleanText.length() <= 9) {
            return cleanText.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3");
        } else {
            return cleanText.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3-$4");
        }
    }

    public static String formatPhoneNumber(String s) {
        String cleanText = s.replaceAll("[^\\d]", "");

        int length = cleanText.length();
        if (length <= 2) {
            return "(" + cleanText;
        } else if (length <= 6) {
            return cleanText.replaceFirst("(\\d{2})(\\d+)", "($1) $2");
        } else if (length <= 10) {
            return cleanText.replaceFirst("(\\d{2})(\\d{4})(\\d+)", "($1) $2-$3");
        } else {
            return cleanText.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
    }
}
