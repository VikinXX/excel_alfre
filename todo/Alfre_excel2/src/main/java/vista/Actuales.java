package vista;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Comprobador;
import excel.ExcelReader;
import excel.ExcelWriter;
import json.Reader;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Actuales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblEj1;
	private JLabel lblEj2;
	private JLabel lblEj3;
	private JLabel lblEj4;
	private int id;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JButton btnSubmit;
	private String[] ejs;
	private JLabel lblResult;
	private JButton btnAtras;
	private JRadioButton rdbtnNormal;
	private JRadioButton rdbtnDificultad;
	private JRadioButton rdbtnDificultad2;
	private JRadioButton rdbtnNormal_1;
	private JRadioButton rdbtnDificultad_1;
	private JRadioButton rdbtnDificultad2_1;
	private JRadioButton rdbtnNormal_2;
	private JRadioButton rdbtnDificultad_2;
	private JRadioButton rdbtnDificultad2_2;
	private JRadioButton rdbtnNormal_3;
	private JRadioButton rdbtnDificultad_3;
	private JRadioButton rdbtnDificultad2_3;
	private ButtonGroup g1;
	private ButtonGroup g2;
	private ButtonGroup g3;
	private ButtonGroup g4;

	/**
	 * Create the frame.
	 */
	public Actuales(int id) {
		setTitle("Puntuaciones");
		this.id = id;
		ejs = Reader.getActuales(id);
		crearGUI();
		setEvents();
		if (ExcelReader.getDias(id) == 5) {
			btnSubmit.setEnabled(false);
		}
	}

	public void setEvents() {
		btnSubmit.addActionListener((e) -> {
			if (Comprobador.checkPuntuaciones(tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText())) {
				// Escribir puntuaciones de los ejercicios
				if (rdbtnNormal.isSelected()) {
					ExcelWriter.escribirPuntuacionExcel(id, ejs[0], Integer.parseInt(tf1.getText()));
				} else if (rdbtnDificultad.isSelected()) {
					ExcelWriter.escribirPuntuacion1DificultadExcel(id, ejs[0], Integer.parseInt(tf1.getText()));
				} else {
					ExcelWriter.escribirPuntuacion2DificultadExcel(id, ejs[0], Integer.parseInt(tf1.getText()));
				}
				
				if (rdbtnNormal_1.isSelected()) {
					ExcelWriter.escribirPuntuacionExcel(id, ejs[1], Integer.parseInt(tf2.getText()));
				} else if (rdbtnDificultad_1.isSelected()) {
					ExcelWriter.escribirPuntuacion1DificultadExcel(id, ejs[1], Integer.parseInt(tf2.getText()));
				} else {
					ExcelWriter.escribirPuntuacion2DificultadExcel(id, ejs[1], Integer.parseInt(tf2.getText()));
				}
				
				if (rdbtnNormal_2.isSelected()) {
					ExcelWriter.escribirPuntuacionExcel(id, ejs[2], Integer.parseInt(tf3.getText()));
				} else if (rdbtnDificultad_2.isSelected()) {
					ExcelWriter.escribirPuntuacion1DificultadExcel(id, ejs[2], Integer.parseInt(tf3.getText()));
				} else {
					ExcelWriter.escribirPuntuacion2DificultadExcel(id, ejs[2], Integer.parseInt(tf3.getText()));
				}
				
				if (rdbtnNormal_3.isSelected()) {
					ExcelWriter.escribirPuntuacionExcel(id, ejs[3], Integer.parseInt(tf4.getText()));
				} else if (rdbtnDificultad_3.isSelected()) {
					ExcelWriter.escribirPuntuacion1DificultadExcel(id, ejs[3], Integer.parseInt(tf4.getText()));
				} else {
					ExcelWriter.escribirPuntuacion2DificultadExcel(id, ejs[3], Integer.parseInt(tf4.getText()));
				}
				
				// Blanquear los espacios para las puntuaciones
				
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				rdbtnNormal.setSelected(true);
				rdbtnNormal_1.setSelected(true);
				rdbtnNormal_2.setSelected(true);
				rdbtnNormal_3.setSelected(true);
				lblResult.setText("Done");
				lblResult.setForeground(Color.green);
				ExcelWriter.dia(id);
				if (ExcelReader.getDias(id) == 5) {
					btnSubmit.setEnabled(false);
				}
			} else {
				lblResult.setText("Tienes que introducir solo numeros");
				lblResult.setForeground(Color.red);
			}
		});
		btnAtras.addActionListener((e) -> {
			Opciones o = new Opciones(id);
			o.setVisible(true);
			this.dispose();
		});
	}

	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ejercicios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(120, 21, 118, 45);
		contentPane.add(lblNewLabel);
		lblNewLabel.setVerticalAlignment(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblEj1 = new JLabel(ejs[0]);
		lblEj1.setBounds(33, 77, 286, 39);
		contentPane.add(lblEj1);
		lblEj1.setVerticalAlignment(SwingConstants.CENTER);
		lblEj1.setHorizontalAlignment(SwingConstants.CENTER);

		lblEj2 = new JLabel(ejs[1]);
		lblEj2.setBounds(33, 235, 286, 43);
		contentPane.add(lblEj2);
		lblEj2.setVerticalAlignment(SwingConstants.CENTER);
		lblEj2.setHorizontalAlignment(SwingConstants.CENTER);

		lblEj3 = new JLabel(ejs[2]);
		lblEj3.setBounds(33, 387, 286, 43);
		contentPane.add(lblEj3);
		lblEj3.setVerticalAlignment(SwingConstants.CENTER);
		lblEj3.setHorizontalAlignment(SwingConstants.CENTER);

		lblEj4 = new JLabel(ejs[3]);
		lblEj4.setBounds(33, 541, 286, 39);
		contentPane.add(lblEj4);
		lblEj4.setVerticalAlignment(SwingConstants.CENTER);
		lblEj4.setHorizontalAlignment(SwingConstants.CENTER);

		tf1 = new JTextField();
		tf1.setBounds(134, 202, 86, 20);
		contentPane.add(tf1);
		tf1.setColumns(10);

		tf2 = new JTextField();
		tf2.setBounds(134, 356, 86, 20);
		contentPane.add(tf2);
		tf2.setColumns(10);

		tf3 = new JTextField();
		tf3.setBounds(134, 510, 86, 20);
		contentPane.add(tf3);
		tf3.setColumns(10);

		tf4 = new JTextField();
		tf4.setBounds(134, 648, 86, 20);
		contentPane.add(tf4);
		tf4.setColumns(10);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(120, 727, 89, 23);
		contentPane.add(btnSubmit);

		lblResult = new JLabel("");
		lblResult.setBounds(73, 702, 202, 14);
		contentPane.add(lblResult);
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);

		ImageIcon atras = new ImageIcon("images/atras.png");

		btnAtras = new JButton(atras);
		btnAtras.setBounds(10, 21, 40, 40);
		contentPane.add(btnAtras);

		rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setBounds(10, 140, 109, 23);
		contentPane.add(rdbtnNormal);
		rdbtnNormal.setSelected(true);

		rdbtnDificultad = new JRadioButton("1 Dificultad");
		rdbtnDificultad.setBounds(141, 140, 109, 23);
		contentPane.add(rdbtnDificultad);

		rdbtnDificultad2 = new JRadioButton("2 Dificultad");
		rdbtnDificultad2.setBounds(269, 140, 109, 23);
		contentPane.add(rdbtnDificultad2);

		g1 = new ButtonGroup();
		g1.add(rdbtnNormal);
		g1.add(rdbtnDificultad);
		g1.add(rdbtnDificultad2);

		rdbtnNormal_1 = new JRadioButton("Normal");
		rdbtnNormal_1.setBounds(6, 299, 109, 23);
		contentPane.add(rdbtnNormal_1);
		rdbtnNormal_1.setSelected(true);

		rdbtnDificultad_1 = new JRadioButton("1 Dificultad");
		rdbtnDificultad_1.setBounds(137, 299, 109, 23);
		contentPane.add(rdbtnDificultad_1);

		rdbtnDificultad2_1 = new JRadioButton("2 Dificultad");
		rdbtnDificultad2_1.setBounds(265, 299, 109, 23);
		contentPane.add(rdbtnDificultad2_1);

		g2 = new ButtonGroup();
		g2.add(rdbtnNormal_1);
		g2.add(rdbtnDificultad_1);
		g2.add(rdbtnDificultad2_1);

		rdbtnNormal_2 = new JRadioButton("Normal");
		rdbtnNormal_2.setBounds(6, 456, 109, 23);
		contentPane.add(rdbtnNormal_2);
		rdbtnNormal_2.setSelected(true);

		rdbtnDificultad_2 = new JRadioButton("1 Dificultad");
		rdbtnDificultad_2.setBounds(137, 456, 109, 23);
		contentPane.add(rdbtnDificultad_2);

		rdbtnDificultad2_2 = new JRadioButton("2 Dificultad");
		rdbtnDificultad2_2.setBounds(265, 456, 109, 23);
		contentPane.add(rdbtnDificultad2_2);

		g3 = new ButtonGroup();
		g3.add(rdbtnNormal_2);
		g3.add(rdbtnDificultad_2);
		g3.add(rdbtnDificultad2_2);

		rdbtnNormal_3 = new JRadioButton("Normal");
		rdbtnNormal_3.setBounds(6, 599, 109, 23);
		contentPane.add(rdbtnNormal_3);
		rdbtnNormal_3.setSelected(true);

		rdbtnDificultad_3 = new JRadioButton("1 Dificultad");
		rdbtnDificultad_3.setBounds(137, 599, 109, 23);
		contentPane.add(rdbtnDificultad_3);

		rdbtnDificultad2_3 = new JRadioButton("2 Dificultad");
		rdbtnDificultad2_3.setBounds(265, 599, 109, 23);
		contentPane.add(rdbtnDificultad2_3);

		g4 = new ButtonGroup();
		g4.add(rdbtnNormal_3);
		g4.add(rdbtnDificultad_3);
		g4.add(rdbtnDificultad2_3);

	}
}
