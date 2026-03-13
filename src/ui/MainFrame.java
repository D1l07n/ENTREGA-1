package ui;

import model.*;
import service.CalculadoraService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JTextField txtMonto      = new JTextField(12);
    private final JComboBox<Ajuste> cboAjuste = new JComboBox<>();
    private final JLabel lblResultado      = new JLabel("—");
    private final JTextArea txtHistorial   = new JTextArea(6, 30);

    private final CalculadoraService service = new CalculadoraService();

    public MainFrame() {
        super("EIF204 · Semana 3 · Swing + POO");
        construirUI();
        cargarAjustes();
        configurarEventos();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void construirUI() {

        // ── Formulario (NORTH) ──────────────────────────────────────
        JPanel pnlForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 8, 8, 8);
        gbc.anchor  = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(new JLabel("Monto (CRC):"), gbc);
        gbc.gridx = 1;
        pnlForm.add(txtMonto, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Ajuste:"), gbc);
        gbc.gridx = 1;
        pnlForm.add(cboAjuste, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Resultado:"), gbc);
        gbc.gridx = 1;
        pnlForm.add(lblResultado, gbc);

        // ── Historial (CENTER) ──────────────────────────────────────
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de cálculos"));
        scroll.setPreferredSize(new Dimension(460, 130));

        // ── Botones (SOUTH) ─────────────────────────────────────────
        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpiar  = new JButton("Limpiar");

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(btnLimpiar);
        pnlBotones.add(btnCalcular);

        btnCalcular.addActionListener(e -> onCalcular());
        btnLimpiar.addActionListener(e -> onLimpiar());

        // ── Ensamblar ventana ───────────────────────────────────────
        setLayout(new BorderLayout(0, 4));
        add(pnlForm,    BorderLayout.NORTH);
        add(scroll,     BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    private void cargarAjustes() {
        cboAjuste.addItem(new AjusteNinguno());
        cboAjuste.addItem(new AjusteIVA13());
        cboAjuste.addItem(new AjusteDescuento10());
        cboAjuste.addItem(new AjusteRecargo5());
    }

    private void configurarEventos() {
        txtMonto.addActionListener(e -> onCalcular());
    }

    private void onCalcular() {
        try {
            String txt = txtMonto.getText().trim();
            if (txt.isEmpty())
                throw new IllegalArgumentException("Debe ingresar un monto.");

            double monto = Double.parseDouble(txt);
            if (monto <= 0)
                throw new IllegalArgumentException("El monto debe ser mayor que cero.");

            Ajuste ajuste    = (Ajuste) cboAjuste.getSelectedItem();
            double resultado = service.calcular(monto, ajuste);

            lblResultado.setText(String.format("%,.2f", resultado));

            String linea = String.format("%-15s | %,10.2f  ->  %,10.2f",
                    ajuste.nombre(), monto, resultado);
            txtHistorial.append(linea + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Monto inválido. Use solo números.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Validación",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLimpiar() {
        txtMonto.setText("");
        cboAjuste.setSelectedIndex(0);
        lblResultado.setText("—");
        txtHistorial.setText("");
        txtMonto.requestFocusInWindow();
    }
}