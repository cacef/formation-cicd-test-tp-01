package com.devops.cicd;

// Import des classes à tester
import com.devops.cicd.PricingConfig;
import com.devops.cicd.PricingConfigLoader;
import com.devops.cicd.PricingService;

// Imports JUnit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        // Charger la configuration réelle depuis app.properties
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        // Instancier le service métier avec cette configuration
        PricingService service = new PricingService(config);

        // Scénario : montant HT = 100, client VIP
        double amountExclVat = 100.0;
        boolean vip = true;

        // Calcul attendu :
        // TTC = HT * (1 + vatRate)
        double expectedTtc = amountExclVat * (1 + config.getVatRate());
        // VIP discount
        double afterVip = expectedTtc * 0.9; // 10% de réduction pour VIP
        // Livraison
        double shipping = expectedTtc >= config.getFreeShippingThreshold() ? 0.0 : 5.0;
        double expectedTotal = afterVip + shipping;

        // Vérifier le total final
        double actualTotal = service.finalTotal(amountExclVat, vip);
        assertEquals(expectedTotal, actualTotal, 0.001,
                "Le total final avec configuration réelle doit correspondre au calcul complet");
    }
}
