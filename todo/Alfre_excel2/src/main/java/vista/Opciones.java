package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excel.ExcelReader;
import json.Reader;

import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Opciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int id;
	private JButton btn4Ej;
	private JButton btnNew;
	private JButton btnChange;
	private JButton btnActuales;

	/**
	 * Create the frame.
	 */
	public Opciones(int id) {
		setTitle("Menú");
		this.id = id;
		crearGUI();
		String ejs[] = ExcelReader.getActuales(id);
		if (ejs[0] != null) {
			btnActuales.setEnabled(true);
		}
		setEvents();
		if (ExcelReader.getDias(id) == 5) {
			btnActuales.setEnabled(false);
		}
	}
	
	public void setEvents() {
		btn4Ej.addActionListener((e)->{
			Generador g = new Generador(id);
			g.setVisible(true);
			this.dispose();
		});
		btnNew.addActionListener((e)->{
			Nuevo n = new Nuevo();
			n.setVisible(true);
			this.dispose();
		});
		btnChange.addActionListener((e)->{
			Inicio i = new Inicio();
			i.setVisible(true);
			this.dispose();
		});
		btnActuales.addActionListener((e)->{
			Actuales a = new Actuales(id);
			a.setVisible(true);
			this.dispose();
		});
	}
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Elige que opción quieres hacer");
		lblNewLabel.setBounds(10, 29, 364, 14);
		contentPane.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		btn4Ej = new JButton("Generar 4 ejercicios");
		btn4Ej.setBounds(84, 96, 195, 23);
		contentPane.add(btn4Ej);
		
		btnNew = new JButton("Ingresar nuevo paciente");
		btnNew.setBounds(84, 224, 195, 23);
		contentPane.add(btnNew);
		
		btnChange = new JButton("Cambiar de paciente");
		btnChange.setBounds(84, 284, 195, 23);
		contentPane.add(btnChange);
		
		JLabel lblNewLabel_1 = new JLabel("Paciente actual: " + id);
		lblNewLabel_1.setBounds(10, 54, 364, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnActuales = new JButton("Continuar con los ejercicios");
		btnActuales.setBounds(84, 164, 195, 23);
		contentPane.add(btnActuales);
		btnActuales.setEnabled(false);
	}
}
