package com.devops.cicd;

public class PasswordPolicy {

    public static boolean isStrong(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true; // tout caractère non alphanumérique
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
