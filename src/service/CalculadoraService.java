
package service;


import model.Ajuste;

public class CalculadoraService {
    public double calcular(double monto, Ajuste ajuste) {
        if (ajuste == null) throw new IllegalArgumentException("Debe seleccionar un ajuste.");
        return ajuste.aplicar(monto);
    }
}
