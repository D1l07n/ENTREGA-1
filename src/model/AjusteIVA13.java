
package model;


public class AjusteIVA13 extends Ajuste {
    @Override
    public String nombre() { return "IVA 13%"; }

    @Override
    public double aplicar(double monto) { return monto * 1.13; }
}