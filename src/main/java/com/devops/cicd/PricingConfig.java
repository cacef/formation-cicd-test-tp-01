package com.devops.cicd;

public class PricingConfig {
    private final double vatRate;
    private final double freeShippingThreshold;

    // Constructeur
    public PricingConfig(double vatRate, double freeShippingThreshold) {
        this.vatRate = vatRate;
        this.freeShippingThreshold = freeShippingThreshold;
    }

    // Getter pour le taux de TVA
    public double getVatRate() {
        return vatRate;
    }

    // Getter pour le seuil de livraison gratuite
    public double getFreeShippingThreshold() {
        return freeShippingThreshold;
    }
}
