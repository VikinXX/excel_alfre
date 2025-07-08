package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excel.ExcelReader;
import excel.ExcelWriter;
import modelo.Actividad;

import javax.swing.JLabel;

public class ConfirmDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblMensaje;
	private String actividad;
	private int id;
	private Generador g;
	private Actividad ac;
	private JButton okButton;
	private JButton cancelButton;
	private int ej;

	/**
	 * Create the dialog.
	 */
	public ConfirmDialog(JFrame parent, String mensaje, int id, Generador g, int ej) {
		super(parent, "Confimación", true);
		this.actividad = mensaje;
		crearGUI();
		this.id = id;
		this.g = g;
		this.ej = ej;
		ac = new Actividad(ExcelReader.getMiembroSuperior(id), ExcelReader.getMuneca(id), ExcelReader.getMano(id),
				ExcelReader.getCoordinacionVelocidad(id));
		setEvents();
	}

	public void setEvents() {
		okButton.addActionListener((e) -> {
			ExcelWriter.banear(id, actividad);
			if (ej == 1) {
				g.setNuevoEj1(ac.nuevoEjercicio(id, ExcelReader.getEjerciciosRealizadosJSON(id), ExcelReader.getBetados(id)));
				this.dispose();
			} else if (ej == 2) {
				g.setNuevoEj2(ac.nuevoEjercicio(id, ExcelReader.getEjerciciosRealizadosJSON(id), ExcelReader.getBetados(id)));
				this.dispose();
			} else if (ej == 3) {
				g.setNuevoEj3(ac.nuevoEjercicio(id, ExcelReader.getEjerciciosRealizadosJSON(id), ExcelReader.getBetados(id)));
				this.dispose();
			} else {
				g.setNuevoEj4(ac.nuevoEjercicio(id, ExcelReader.getEjerciciosRealizadosJSON(id), ExcelReader.getBetados(id)));
				this.dispose();
			}
		});
		cancelButton.addActionListener((e) -> {
			this.dispose();
		});
	}

	public void crearGUI() {
		setLocationRelativeTo(null);
		setSize(300, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lblMensaje = new JLabel(
				"<html>¿Estás seguro de que quieres betar el ejercicio<br> " + this.actividad + "?</html>");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setVerticalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(10, 11, 264, 156);
		contentPanel.add(lblMensaje);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}

}
