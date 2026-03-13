package model;

public class AjusteRecargo5 extends Ajuste {
    @Override
    public String nombre() { return "Recargo 5%"; }

    @Override
    public double aplicar(double monto) { return monto * 1.05; }
}