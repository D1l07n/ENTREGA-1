package ui;

import model.*;
import service.CalculadoraService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JTextField txtMonto = new JTextField(12);
    private final JComboBox<Ajuste> cboAjuste = new JComboBox<>();
    private final JLabel lblResultado = new JLabel("Resultado: —");

    private final CalculadoraService service = new CalculadoraService();

    public MainFrame() {
        super("EIF204 · Semana 3 · Swing + POO");
        construirUI();
        cargarAjustes();
        configurarEventos();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 220);
        setLocationRelativeTo(null);
    }

    private void construirUI() {
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(new JLabel("Monto (CRC):"), gbc);

        gbc.gridx = 1;
        pnlForm.add(txtMonto, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Ajuste:"), gbc);

        gbc.gridx = 1;
        pnlForm.add(cboAjuste, gbc);

        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpiar  = new JButton("Limpiar");

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(btnLimpiar);
        pnlBotones.add(btnCalcular);

        JPanel pnlResultado = new JPanel(new BorderLayout());
        pnlResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlResultado.add(lblResultado, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(pnlForm, BorderLayout.NORTH);
        add(pnlResultado, BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);

        btnCalcular.addActionListener(e -> onCalcular());
        btnLimpiar.addActionListener(e -> onLimpiar());
    }

    private void cargarAjustes() {
        cboAjuste.addItem(new AjusteNinguno());
        cboAjuste.addItem(new AjusteIVA13());
        cboAjuste.addItem(new AjusteDescuento10());
    }

    private void configurarEventos() {
        // Enter en el campo monto también calcula
        txtMonto.addActionListener(e -> onCalcular());
    }

    private void onCalcular() {
        try {
            String txt = txtMonto.getText().trim();
            if (txt.isEmpty()) throw new IllegalArgumentException("Debe ingresar un monto.");

            double monto = Double.parseDouble(txt);
            if (monto <= 0) throw new IllegalArgumentException("El monto debe ser mayor que cero.");

            Ajuste ajuste = (Ajuste) cboAjuste.getSelectedItem();
            double resultado = service.calcular(monto, ajuste);

            lblResultado.setText("Resultado: " + String.format("%,.2f", resultado));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Monto inválido. Use números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLimpiar() {
        txtMonto.setText("");
        cboAjuste.setSelectedIndex(0);
        lblResultado.setText("Resultado: —");
        txtMonto.requestFocusInWindow();
    }
}
