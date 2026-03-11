
package model;

public abstract class Ajuste {
    public abstract String nombre();
    public abstract double aplicar(double monto);

    @Override
    public String toString() {
        // Esto hace que el JComboBox muestre el nombre bonito
        return nombre();
    }
}
