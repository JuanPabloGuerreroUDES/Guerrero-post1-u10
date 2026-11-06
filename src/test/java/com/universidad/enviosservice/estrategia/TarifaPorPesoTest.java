package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifaPorPesoTest {

    private final TarifaPorPeso tarifa = new TarifaPorPeso(0.8, 1.2);

    @ParameterizedTest(name = "{0} kg -> recargo {1}")
    @CsvSource({
        "0.0,  0.0",    // sin peso, sin recargo
        "5.0,  0.0",    // límite del tramo bajo: aún sin recargo
        "5.5,  0.4",    // apenas entra al tramo medio: 0.5 * 0.8
        "20.0, 12.0",   // límite del tramo medio: 15 * 0.8
        "20.5, 12.6"    // apenas entra al tramo alto: 12 + 0.5 * 1.2
    })
    void calculaRecargoSegunElTramoDePeso(double pesoKg, double esperado) {
        assertEquals(esperado, tarifa.recargo(pesoKg), 0.0001);
    }
}
