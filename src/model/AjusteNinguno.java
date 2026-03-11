
package model;

public class AjusteNinguno extends Ajuste {
    @Override
    public String nombre() { return "Sin ajuste"; }

    @Override
    public double aplicar(double monto) { return monto; }
}
