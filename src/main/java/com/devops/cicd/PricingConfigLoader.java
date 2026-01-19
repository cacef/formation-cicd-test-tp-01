package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    /**
     * Charge le fichier app.properties depuis le classpath
     * et crée un objet PricingConfig avec les valeurs lues
     */
    public PricingConfig load() {
        Properties props = new Properties();

        // Charger le fichier depuis src/main/resources ou src/test/resources
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("Impossible de trouver app.properties dans le classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de app.properties", e);
        }

        // Lire les valeurs requises
        double vatRate = Double.parseDouble(required(props, "vatRate"));
        double freeShippingThreshold = Double.parseDouble(required(props, "freeShippingThreshold"));

        return new PricingConfig(vatRate, freeShippingThreshold);
    }

    /**
     * Récupère une propriété obligatoire
     * Lève une exception si la clé est absente
     */
    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("La clé obligatoire '" + key + "' est manquante dans app.properties");
        }
        return value;
    }
}
