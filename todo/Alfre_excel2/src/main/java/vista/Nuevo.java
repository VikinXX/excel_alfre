package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import json.Reader;
import json.Writer;
import modelo.Actividad;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import controlador.*;
import excel.ExcelReader;
import excel.ExcelWriter;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Nuevo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfID;
	private JTextField tfMS;
	private JTextField tfMnc;
	private JTextField tfM;
	private JTextField tfCV;
	private JButton btnSubmit;
	private Actividad ac;
	private JLabel lblResult;
	private JButton btnAtras;

	/**
	 * Create the frame.
	 */
	public Nuevo() {
		setTitle("Nuevo Paciente");
		crearGUI();
		setEvents();
	}
	
	public void setEvents() {
		btnSubmit.addActionListener((e)->{
			if (Comprobador.check(tfID.getText(), tfMS.getText(), tfM.getText(), tfMnc.getText(), tfCV.getText())) {
				if (!ExcelReader.isReal(Integer.parseInt(tfID.getText()))) {
					
					int id = Integer.parseInt(tfID.getText());
					int ms = Integer.parseInt(tfMS.getText());
					int mnc = Integer.parseInt(tfMnc.getText());
					int m = Integer.parseInt(tfM.getText());
					int cv = Integer.parseInt(tfCV.getText());
					
					boolean discapacidades[] = new boolean[4];
					
					if (ms >= 15) {
						discapacidades[0] = true;
					}
					if (mnc >= 5) {
						discapacidades[1] = true;
					}
					if (m >= 7) {
						discapacidades[2] = true;
					}
					if (cv >= 3) {
						discapacidades[3] = true;
					}
					
					ac = new Actividad(discapacidades[0], discapacidades[1], discapacidades[2], discapacidades[3]);
					
					ExcelWriter.nuevoPacienteJSON(id, discapacidades[0], discapacidades[1], discapacidades[2], discapacidades[3], ac.todos());
					ExcelWriter.nuevoPacienteExcel(id, discapacidades[0], discapacidades[1], discapacidades[2], discapacidades[3]);
					
					Opciones o = new Opciones(id);
					o.setVisible(true);
					this.dispose();
				}else {
					lblResult.setText("El ID del paciente ya existe");
					lblResult.setForeground(Color.RED);
					lblResult.setHorizontalAlignment(SwingConstants.CENTER);
				}
				
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
		setSize(400, 600);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nuevo Paciente");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(126, 36, 145, 48);
		contentPane.add(lblNewLabel);
		
		tfID = new JTextField();
		tfID.setBounds(246, 125, 86, 20);
		contentPane.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setBounds(44, 128, 192, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Puntos muñeca:");
		lblNewLabel_2.setBounds(44, 245, 192, 14);
		contentPane.add(lblNewLabel_2);
		
		tfMS = new JTextField();
		tfMS.setBounds(246, 191, 86, 20);
		contentPane.add(tfMS);
		tfMS.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Puntos miembro superior:");
		lblNewLabel_2_1.setBounds(44, 194, 192, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Puntos mano:");
		lblNewLabel_2_1_1.setBounds(44, 304, 192, 14);
		contentPane.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("Puntos coordinacion/velocidad:");
		lblNewLabel_2_1_2.setBounds(44, 361, 192, 14);
		contentPane.add(lblNewLabel_2_1_2);
		
		tfMnc = new JTextField();
		tfMnc.setBounds(246, 242, 86, 20);
		contentPane.add(tfMnc);
		tfMnc.setColumns(10);
		
		tfM = new JTextField();
		tfM.setBounds(246, 301, 86, 20);
		contentPane.add(tfM);
		tfM.setColumns(10);
		
		tfCV = new JTextField();
		tfCV.setBounds(246, 358, 86, 20);
		contentPane.add(tfCV);
		tfCV.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(147, 455, 89, 23);
		contentPane.add(btnSubmit);
		
		lblResult = new JLabel("");
		lblResult.setBounds(10, 416, 364, 14);
		contentPane.add(lblResult);
		
		ImageIcon atras = new ImageIcon("images/atras.png");
		
		btnAtras = new JButton(atras);
		btnAtras.setBounds(10, 11, 40, 40);
		contentPane.add(btnAtras);
	}
}
