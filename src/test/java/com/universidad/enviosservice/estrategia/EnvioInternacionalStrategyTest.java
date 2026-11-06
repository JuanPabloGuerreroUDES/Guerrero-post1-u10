package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

// R6 de la Parte 1: la rama INTERNACIONAL completa no tenía ninguna prueba
class EnvioInternacionalStrategyTest {

    private final EnvioInternacionalStrategy estrategia = new EnvioInternacionalStrategy();

    @ParameterizedTest(name = "{0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 230.0",   // pesoKg <= 5: 180 + 50
        "8.0,  100.0, 239.0",   // 5 < pesoKg <= 20: 230 + 9
        "25.0, 100.0, 297.5"    // pesoKg > 20: 230 + 45 + 22.5
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "5000.0, 9050.0",    // en el límite: sin recargo
        "6000.0, 10950.0"    // por encima de 5000 km: 10800 + 50 + 100
    })
    void aplicaRecargoSoloPorEncimaDe5000Km(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
