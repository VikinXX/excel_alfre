package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import excel.ExcelReader;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lblID;
	private JButton btnID;
	private JLabel lblExcp;
	private JButton btnAtras;

	/**
	 * Create the frame.
	 */
	public Inicio() {
		setTitle("Paciente existente");
		crearGUI();
		setEvents();
		
	}
	
	public void setEvents() {
		btnID.addActionListener((e)->{
			int id = 0;
			try {
				id = Integer.parseInt(lblID.getText());
			} catch (NumberFormatException e2) {
				lblExcp.setText("Tienes que introducir un número");
			}
			if (ExcelReader.isReal(id)) {
				Opciones o = new Opciones(id);
				o.setVisible(true);
				this.dispose();
	
			}else {
				lblExcp.setText("No existe el paciente");
			}
			
		});
		btnAtras.addActionListener((e)->{
			Paciente p = new Paciente();
			p.setVisible(true);
			this.dispose();
		});
	}
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("¿Qué ID de paciente es?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 36, 264, 48);
		contentPane.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblID = new JTextField();
		lblID.setBounds(159, 116, 86, 20);
		contentPane.add(lblID);
		lblID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(32, 119, 95, 14);
		contentPane.add(lblNewLabel_1);
		
		btnID = new JButton("Submit");
		btnID.setBounds(91, 206, 89, 23);
		contentPane.add(btnID);
		
		lblExcp = new JLabel("");
		lblExcp.setBounds(10, 181, 264, 14);
		contentPane.add(lblExcp);
		lblExcp.setHorizontalAlignment(SwingConstants.CENTER);
		
		ImageIcon atras = new ImageIcon("images/atras.png");
		
		btnAtras = new JButton(atras);
		btnAtras.setBounds(10, 11, 40, 40);
		contentPane.add(btnAtras);
	}
}
