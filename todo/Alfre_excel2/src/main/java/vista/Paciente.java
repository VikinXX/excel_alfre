package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import excel.ExcelReader;
import excel.ExcelWriter;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Paciente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnInicio;
	private JButton btnNew;
	private JButton btnSync;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Paciente frame = new Paciente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Paciente() {
		setTitle("Inicio");
		crearGUI();
		setEvents();
		if (ExcelReader.firstTime()) {
			ExcelWriter.setEjerciciosRealizadosJSON();
		}
		this.setVisible(true);
	}
	
	public void setEvents() {
		btnInicio.addActionListener((e)->{
			Inicio i = new Inicio();
			i.setVisible(true);
			this.dispose();
		});
		btnNew.addActionListener((e)->{
			Nuevo n = new Nuevo();
			n.setVisible(true);
			this.dispose();
		});
		btnSync.addActionListener((e)->{
			ExcelWriter.borrarPacientes();
			ExcelWriter.setEjerciciosRealizadosJSON();
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
		
		btnInicio = new JButton("Paciente existente");
		btnInicio.setBounds(54, 102, 171, 23);
		contentPane.add(btnInicio);
		
		btnNew = new JButton("Nuevo paciente");
		
		btnNew.setBounds(54, 147, 171, 23);
		contentPane.add(btnNew);
		
		JLabel lblNewLabel = new JLabel("Inicio de paciente");
		lblNewLabel.setBounds(10, 55, 264, 14);
		contentPane.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnSync = new JButton("Sincronizar");
		btnSync.setBounds(54, 210, 171, 23);
		contentPane.add(btnSync);
		
		JLabel lblNewLabel_1 = new JLabel("Intenta sincronizar todo lo menos posible");
		lblNewLabel_1.setBounds(10, 185, 264, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
