package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioEstandarStrategyTest {

    private final EnvioEstandarStrategy estrategia = new EnvioEstandarStrategy();

    // R1 y R2 de la Parte 1: tramos de peso que la suite inicial nunca alcanzó
    @ParameterizedTest(name = "{0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 50.0",   // pesoKg <= 5 (ya cubierto en la Parte 1, sirve de referencia)
        "5.0,  100.0, 50.0",   // límite superior del primer tramo
        "8.0,  100.0, 52.4",   // 5 < pesoKg <= 20  (R1)
        "20.0, 100.0, 62.0",   // límite superior del segundo tramo (R1)
        "25.0, 100.0, 68.0"    // pesoKg > 20 (R2)
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    // R3 de la Parte 1: recargo de 10 por distancia > 500 km (solo se había probado el caso falso)
    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "500.0, 250.0",   // en el límite: sin recargo
        "600.0, 310.0"    // por encima del límite: 300 + 10
    })
    void aplicaRecargoSoloPorEncimaDe500Km(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
