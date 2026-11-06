package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

// R4 de la Parte 1: la rama EXPRESS completa no tenía ninguna prueba
class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest(name = "{0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 156.0",    // pesoKg <= 5: 120 * 1.3
        "8.0,  100.0, 161.85",   // 5 < pesoKg <= 20: (120 + 4.5) * 1.3
        "25.0, 100.0, 198.25"    // pesoKg > 20: (120 + 22.5 + 10) * 1.3
    })
    void calculaCostoPorTramoDePesoConFactorExpress(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "500.0, 780.0",   // en el límite: 600 * 1.3, sin recargo
        "600.0, 968.5"    // el recargo de 25 se suma ANTES del factor 1.3: (720 + 25) * 1.3
    })
    void aplicaRecargoPorDistanciaAntesDelFactorExpress(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
