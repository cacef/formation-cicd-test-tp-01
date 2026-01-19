package com.devops.cicd;

public final class PricingService {

    private final PricingConfig config;

    // Constructeur
    public PricingService(PricingConfig config) {
        this.config = config;
    }

    /**
     * Applique la TVA sur un montant hors taxes
     */
    public double applyVat(double amountExclVat) {
        return amountExclVat * (1 + config.getVatRate());
    }

    /**
     * Applique une remise VIP si le client est VIP
     * Exemple : 10% de remise pour les VIP
     */
    public double applyVipDiscount(double amount, boolean vip) {
        if (vip) {
            return amount * 0.9; // 10% de réduction
        }
        return amount;
    }

    /**
     * Calcule les frais de livraison
     * Livraison gratuite si montant >= freeShippingThreshold
     * Sinon, frais fixes (exemple 5€)
     */
    public double shippingCost(double amount) {
        if (amount >= config.getFreeShippingThreshold()) {
            return 0.0;
        }
        return 5.0; // frais de livraison fixe
    }

    /**
     * Calcule le total final :
     * - TVA appliquée sur montant HT
     * - remise VIP appliquée ensuite
     * - frais de livraison ajoutés enfin
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double withVat = applyVat(amountExclVat);         // HT -> TTC
        double withDiscount = applyVipDiscount(withVat, vip); // appliquer remise VIP
        double shipping = shippingCost(withVat);         // frais livraison calculés sur TTC
        return withDiscount + shipping;
    }
}
