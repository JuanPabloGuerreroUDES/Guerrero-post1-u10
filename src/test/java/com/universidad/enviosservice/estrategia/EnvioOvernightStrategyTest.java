package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// R5 de la Parte 1: la rama OVERNIGHT completa, incluida su excepción, no tenía pruebas
class EnvioOvernightStrategyTest {

    private final EnvioOvernightStrategy estrategia = new EnvioOvernightStrategy();

    @ParameterizedTest(name = "{0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 220.0",   // pesoKg <= 5
        "8.0,  100.0, 227.5",   // 5 < pesoKg <= 20
        "25.0, 100.0, 272.5",   // pesoKg > 20: 200 + 20 + 37.5 + 15 (la guía indicaba 250 por error)
        "3.0,  300.0, 620.0"    // 300 km exactos todavía se permiten
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    void rechazaDistanciasMayoresA300Km() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> estrategia.calcular(3.0, 301.0));
        assertEquals("OVERNIGHT no disponible para mas de 300 km", ex.getMessage());
    }
}
