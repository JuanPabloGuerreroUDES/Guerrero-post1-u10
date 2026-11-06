package com.universidad.enviosservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioCalculoEnvioTest {

    private final ServicioCalculoEnvio servicio = new ServicioCalculoEnvio();

    // --- Suite de partida (Parte 1) ---

    @Test
    void calculaEnvioEstandarPesoBajo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.01);
    }

    // --- Pruebas agregadas en la Parte 2 (ramas R7, R8 y R9 del reporte JaCoCo inicial) ---

    @Test
    void aplicaRecargoDel15PorCientoAPaqueteFragil() {
        // R8: esFragil == true nunca se había ejercitado. 50 * 1.15
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", true);
        assertEquals(57.5, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClienteCorporativo() {
        // R9: el descuento CORPORATIVO solo se había evaluado como falso. 50 * 0.85
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", false);
        assertEquals(42.5, costo, 0.01);
    }

    @Test
    void aplicaRecargoPorFragilidadAntesDelDescuento() {
        // Orden de las reglas transversales: (50 * 1.15) * 0.9
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", true);
        assertEquals(51.75, costo, 0.01);
    }

    @Test
    void rechazaMetodoDeEnvioNoSoportado() {
        // R7: la rama "else -> throw" del método original
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("DRON", 3.0, 100.0, "REGULAR", false));
        assertEquals("Metodo de envio no soportado: DRON", ex.getMessage());
    }

    @Test
    void delegaEnLaEstrategiaDelMetodoSolicitado() {
        // El orquestador elige la estrategia EXPRESS y le aplica el descuento CORPORATIVO: 156 * 0.85
        double costo = servicio.calcularCosto("EXPRESS", 3.0, 100.0, "CORPORATIVO", false);
        assertEquals(132.6, costo, 0.01);
    }

    @Test
    void propagaLaRestriccionDeDistanciaDeOvernight() {
        assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("OVERNIGHT", 3.0, 350.0, "REGULAR", false));
    }
}
