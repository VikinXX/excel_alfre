package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excel.ExcelReader;
import excel.ExcelWriter;
import json.Reader;
import json.Writer;
import modelo.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Generador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int id;
	private JLabel lblActuales;
	private JButton btnGenerar;
	private JLabel lblEj1;
	private JLabel lblEj2;
	private JLabel lblEj3;
	private JLabel lblEj4;
	private String [] ejs;
	private Actividad ac;
	private JButton btnPuntuaciones;
	private JButton btnAtras;
	private JButton btnEj1;
	private JButton btnEj2;
	private JButton btnEj3;
	private JButton btnEj4;
	private JButton btnDone;
	private String generados[];

	/**
	 * Create the frame.
	 */
	public Generador(int id) {
		setTitle("Generador");
		this.id = id;
		ejs = Reader.getActuales(this.id);
		ac = new Actividad(
				ExcelReader.getMiembroSuperior(id), 
				ExcelReader.getMuneca(id), 
				ExcelReader.getMano(id), 
				ExcelReader.getCoordinacionVelocidad(id)
				);
		crearGUI();
		setEvents();
	}
	
	public void setEvents() {
		btnGenerar.addActionListener((e)->{
			generados = ac.returnActuales(id, ExcelReader.getEjerciciosRealizadosJSON(id), ExcelReader.getBetados(id));
			lblEj1.setText(generados[0]);
			lblEj2.setText(generados[1]);
			lblEj3.setText(generados[2]);
			lblEj4.setText(generados[3]);
			btnEj1.setVisible(true);
			btnEj2.setVisible(true);
			btnEj3.setVisible(true);
			btnEj4.setVisible(true);
			btnEj1.addActionListener((e1)->{
				ConfirmDialog cd = new ConfirmDialog(this, generados[0], id, this, 1);
				cd.setVisible(true);
			});
			btnEj2.addActionListener((e1)->{
				ConfirmDialog cd = new ConfirmDialog(this, generados[1], id, this, 2);
				cd.setVisible(true);
			});
			btnEj3.addActionListener((e1)->{
				ConfirmDialog cd = new ConfirmDialog(this, generados[2], id, this, 3);
				cd.setVisible(true);
			});
			btnEj4.addActionListener((e1)->{
				ConfirmDialog cd = new ConfirmDialog(this, generados[3], id, this, 4);
				cd.setVisible(true);
			});
			btnDone.setVisible(true);
			btnGenerar.setVisible(false);
			btnDone.addActionListener((e1)->{
				if (ExcelReader.getActuales(id).length != 0) {
					ExcelWriter.nuevosActuales(id);
					ExcelWriter.escribirActuales(id, generados);
					ExcelWriter.escribirEjercicios(id, generados);
					ExcelWriter.escribirActualesExcel(id, generados);
				}else {
					ExcelWriter.nuevosActuales(id);
					ExcelWriter.escribirActuales(id, generados);
					ExcelWriter.escribirEjercicios(id, generados);
					ExcelWriter.nuevosEjerciciosExcel(id);
					ExcelWriter.escribirActualesExcel(id, generados);
				}
				
				lblActuales.setText("¡GENERADOS!");
				btnPuntuaciones.setVisible(true);
				btnEj1.setVisible(false);
				btnEj2.setVisible(false);
				btnEj3.setVisible(false);
				btnEj4.setVisible(false);
				btnDone.setVisible(false);
			});
		});
		btnPuntuaciones.addActionListener((e)->{
			Actuales punt = new Actuales(id);
			punt.setVisible(true);
			this.dispose();
		});
		btnAtras.addActionListener((e)->{
			Opciones op = new Opciones(id);
			op.setVisible(true);
			this.dispose();
		});
	}
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 800);
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGenerar = new JButton("¡Generar!");
		btnGenerar.setBounds(190, 727, 89, 23);
		contentPane.add(btnGenerar);
		
		lblActuales = new JLabel("Actuales");
		lblActuales.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblActuales.setBounds(175, 37, 137, 72);
		contentPane.add(lblActuales);
		lblActuales.setVerticalAlignment(SwingConstants.CENTER);
		lblActuales.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEj1 = new JLabel(ejs[0]);
		lblEj1.setBounds(10, 141, 464, 14);
		contentPane.add(lblEj1);
		lblEj1.setVerticalAlignment(SwingConstants.CENTER);
		lblEj1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEj2 = new JLabel(ejs[1]);
		lblEj2.setBounds(10, 288, 464, 14);
		contentPane.add(lblEj2);
		lblEj2.setVerticalAlignment(SwingConstants.CENTER);
		lblEj2.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEj3 = new JLabel(ejs[2]);
		lblEj3.setBounds(10, 445, 464, 14);
		contentPane.add(lblEj3);
		lblEj3.setVerticalAlignment(SwingConstants.CENTER);
		lblEj3.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEj4 = new JLabel(ejs[3]);
		lblEj4.setBounds(10, 583, 464, 14);
		contentPane.add(lblEj4);
		lblEj4.setVerticalAlignment(SwingConstants.CENTER);
		lblEj4.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnPuntuaciones = new JButton("Ir a puntuaciones");
		btnPuntuaciones.setBounds(10, 693, 464, 23);
		contentPane.add(btnPuntuaciones);
		btnPuntuaciones.setVisible(false);
		
		ImageIcon im = new ImageIcon("images/atras.png");
		
		btnAtras = new JButton(im);
		btnAtras.setBounds(10, 11, 40, 40);
		contentPane.add(btnAtras);
		
		btnEj1 = new JButton("Banear");
		btnEj1.setBounds(190, 204, 89, 23);
		contentPane.add(btnEj1);
		btnEj1.setVisible(false);
		
		btnEj2 = new JButton("Banear");
		btnEj2.setBounds(190, 359, 89, 23);
		contentPane.add(btnEj2);
		btnEj2.setVisible(false);
		
		btnEj3 = new JButton("Banear");
		btnEj3.setBounds(190, 509, 89, 23);
		contentPane.add(btnEj3);
		btnEj3.setVisible(false);
		
		btnEj4 = new JButton("Banear");
		btnEj4.setBounds(190, 630, 89, 23);
		contentPane.add(btnEj4);
		btnEj4.setVisible(false);
		
		btnDone = new JButton("Done");
		btnDone.setBounds(190, 727, 89, 23);
		contentPane.add(btnDone);
		btnDone.setVisible(false);
	}
	
	public void setNuevoEj1(String ac) {
		lblEj1.setText(ac);
		generados[0] = ac;
	}
	
	public void setNuevoEj2(String ac) {
		lblEj2.setText(ac);
		generados[1] = ac;
	}
	
	public void setNuevoEj3(String ac) {
		lblEj3.setText(ac);
		generados[2] = ac;
	}
	
	public void setNuevoEj4(String ac) {
		lblEj4.setText(ac);
		generados[3] = ac;
	}
}
