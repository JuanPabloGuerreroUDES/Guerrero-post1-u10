package com.universidad.enviosservice.estrategia;

/**
 * Strategy: cada método de envío calcula su costo base con una fórmula propia.
 * Los ajustes transversales (fragilidad y tipo de cliente) no forman parte de la
 * estrategia y se aplican en ServicioCalculoEnvio.
 */
public interface EstrategiaEnvio {
    double calcular(double pesoKg, double distanciaKm);
}
