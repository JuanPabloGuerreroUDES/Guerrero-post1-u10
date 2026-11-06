package com.universidad.enviosservice.estrategia;

/**
 * Recargo por peso en tres tramos (hasta 5 kg, de 5 a 20 kg y más de 20 kg).
 * Todas las estrategias comparten la estructura de tramos y solo cambian las tarifas.
 * Por eso se extrajo aquí: así se eliminan los bloques duplicados que SonarQube
 * detectó entre las estrategias.
 */
record TarifaPorPeso(double tarifaTramoMedio, double tarifaTramoAlto) {

    private static final double LIMITE_TRAMO_BAJO = 5;
    private static final double LIMITE_TRAMO_MEDIO = 20;

    double recargo(double pesoKg) {
        if (pesoKg <= LIMITE_TRAMO_BAJO) {
            return 0;
        }
        if (pesoKg <= LIMITE_TRAMO_MEDIO) {
            return (pesoKg - LIMITE_TRAMO_BAJO) * tarifaTramoMedio;
        }
        return (LIMITE_TRAMO_MEDIO - LIMITE_TRAMO_BAJO) * tarifaTramoMedio
            + (pesoKg - LIMITE_TRAMO_MEDIO) * tarifaTramoAlto;
    }
}
