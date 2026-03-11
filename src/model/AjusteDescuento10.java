
package model;

public class AjusteDescuento10 extends Ajuste {
    @Override
    public String nombre() { return "Descuento 10%"; }

    @Override
    public double aplicar(double monto) { return monto * 0.90; }
}
