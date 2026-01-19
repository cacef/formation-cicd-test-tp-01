package com.devops.cicd;

import com.devops.cicd.PasswordPolicy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordPolicyTest {

    @Test
    void passwordTooShort() {
        assertFalse(PasswordPolicy.isStrong("Ab1!"), "Doit échouer si le mot de passe est trop court (<8)");
    }

    @Test
    void passwordMissingUppercase() {
        assertFalse(PasswordPolicy.isStrong("abcdef1!"), "Doit échouer si pas de majuscule");
    }

    @Test
    void passwordMissingLowercase() {
        assertFalse(PasswordPolicy.isStrong("ABCDEF1!"), "Doit échouer si pas de minuscule");
    }

    @Test
    void passwordMissingDigit() {
        assertFalse(PasswordPolicy.isStrong("Abcdefg!"), "Doit échouer si pas de chiffre");
    }

    @Test
    void passwordMissingSpecialChar() {
        assertFalse(PasswordPolicy.isStrong("Abcdefg1"), "Doit échouer si pas de caractère spécial");
    }

    @Test
    void passwordValid() {
        assertTrue(PasswordPolicy.isStrong("Abcdef1!"), "Doit réussir si toutes les règles sont respectées");
    }

    @Test
    void passwordNull() {
        assertFalse(PasswordPolicy.isStrong(null), "Doit échouer si le mot de passe est null");
    }
}
