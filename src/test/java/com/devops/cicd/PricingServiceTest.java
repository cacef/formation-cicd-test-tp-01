package com.devops.cicd;

// Imports des classes à tester
import com.devops.cicd.PricingConfig;
import com.devops.cicd.PricingService;

// Imports JUnit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    // Configuration factice pour tests
    // TVA = 20% (0.20) ; seuil livraison gratuite = 50.0
    private final PricingConfig fakeConfig = new PricingConfig(0.20, 50.0);
    private final PricingService service = new PricingService(fakeConfig);

    @Test
    void testApplyVat() {
        double amountExclVat = 100.0;
        double expectedTtc = 120.0; // 100 + 20%
        assertEquals(expectedTtc, service.applyVat(amountExclVat), 0.001,
                "La TVA devrait être appliquée correctement");
    }

    @Test
    void testApplyVipDiscountForVip() {
        double amount = 120.0;
        boolean vip = true;
        double expected = 120.0 * 0.9; // 10% de réduction
        assertEquals(expected, service.applyVipDiscount(amount, vip), 0.001,
                "La remise VIP doit être appliquée pour un client VIP");
    }

    @Test
    void testApplyVipDiscountForNonVip() {
        double amount = 120.0;
        boolean vip = false;
        double expected = 120.0; // Pas de réduction
        assertEquals(expected, service.applyVipDiscount(amount, vip), 0.001,
                "Aucune remise ne doit être appliquée pour un client non VIP");
    }

    @Test
    void testShippingCostFree() {
        double amount = 60.0; // supérieur au seuil
        double expected = 0.0;
        assertEquals(expected, service.shippingCost(amount), 0.001,
                "Les frais de livraison doivent être gratuits si le montant dépasse le seuil");
    }

    @Test
    void testShippingCostPaid() {
        double amount = 40.0; // inférieur au seuil
        double expected = 5.0; // frais fixes
        assertEquals(expected, service.shippingCost(amount), 0.001,
                "Les frais de livraison doivent être facturés si le montant est inférieur au seuil");
    }

    @Test
    void testFinalTotalForVipAboveThreshold() {
        double ht = 100.0;
        boolean vip = true;
        // Calcul attendu :
        // TTC = 100 * 1.2 = 120
        // VIP = 120 * 0.9 = 108
        // Livraison = 0 (108 > 50)
        double expected = 108.0;
        assertEquals(expected, service.finalTotal(ht, vip), 0.001,
                "Total final avec VIP et montant supérieur au seuil de livraison gratuite");
    }

    @Test
    void testFinalTotalForNonVipBelowThreshold() {
        double ht = 30.0;
        boolean vip = false;
        // Calcul attendu :
        // TTC = 30 * 1.2 = 36
        // Pas de VIP
        // Livraison = 5
        double expected = 36 + 5;
        assertEquals(expected, service.finalTotal(ht, vip), 0.001,
                "Total final pour un client non VIP avec montant inférieur au seuil");
    }
}
