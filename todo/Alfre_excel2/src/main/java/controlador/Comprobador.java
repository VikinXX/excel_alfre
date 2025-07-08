package controlador;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Comprobador {

	/**
	 * Método que dados varios paramétros comprueba si son números para introducirlos en una variable int
	 * @param id
	 * @param ms
	 * @param mnc
	 * @param m
	 * @param cv
	 * @throws NumberFormatException
	 * @return
	 */
	
	public static boolean check(String id, String ms, String mnc, String m, String cv) {
		String [] cads = new String[5];
		cads[0] = id;
		cads[1] = ms;
		cads[2] = mnc;
		cads[3] = m;
		cads[4] = cv;
		
		for (int i = 0; i < cads.length; i++) {
			int p;
			try {
				p = Integer.parseInt(cads[i]);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Método que comprueba una serie de Strings para poder pasarlos a Integer
	 * @param ej1
	 * @param ej2
	 * @param ej3
	 * @param ej4
	 * @throws NumberFormatException
	 * @return
	 */
	
	public static boolean checkPuntuaciones(String ej1, String ej2, String ej3, String ej4) {
		String [] cads = new String[4];
		cads[0] = ej1;
		cads[1] = ej2;
		cads[2] = ej3;
		cads[3] = ej4;
		
		for (int i = 0; i < cads.length; i++) {
			int p;
			try {
				p = Integer.parseInt(cads[i]);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
}
