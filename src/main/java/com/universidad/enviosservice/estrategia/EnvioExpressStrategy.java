package com.universidad.enviosservice.estrategia;

public class EnvioExpressStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA_PESO = new TarifaPorPeso(1.5, 2.0);

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = distanciaKm * 1.2 + TARIFA_PESO.recargo(pesoKg);
        if (distanciaKm > 500) {
            costo += 25;
        }
        return costo * 1.3;
    }
}
