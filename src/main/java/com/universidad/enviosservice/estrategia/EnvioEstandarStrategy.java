package com.universidad.enviosservice.estrategia;

public class EnvioEstandarStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA_PESO = new TarifaPorPeso(0.8, 1.2);

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = distanciaKm * 0.5 + TARIFA_PESO.recargo(pesoKg);
        if (distanciaKm > 500) {
            costo += 10;
        }
        return costo;
    }
}
