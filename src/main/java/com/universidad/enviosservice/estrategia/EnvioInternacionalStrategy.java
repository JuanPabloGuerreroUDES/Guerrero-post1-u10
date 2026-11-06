package com.universidad.enviosservice.estrategia;

public class EnvioInternacionalStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA_PESO = new TarifaPorPeso(3.0, 4.5);

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = distanciaKm * 1.8 + 50 + TARIFA_PESO.recargo(pesoKg);
        if (distanciaKm > 5000) {
            costo += 100;
        }
        return costo;
    }
}
