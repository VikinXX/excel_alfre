package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import excel.ExcelReader;
import excel.ExcelWriter;
import json.Writer;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Actividad {

	private static final String [][] actividades= {
			{   "Colocar objetos en la estantería",
	            "Sacar las cajoneras",
	            "Abrir/cerrar cajones",
	            "Sacar papeles del dispensador",
	            "Colgar ropa en el burro",
	            "Descolgar ropa del burro",
	            "Abrir/cerrar armario",
	            "Limpieza de mesa",
	            "Lavarse las manos",
	            "Limpiar el cristal (+1 espejo)",
	            "Subir bajar tapa del wc",
	            "Trasladar el bote de un lado a otro de la mesa",
	            "Lavar los platos",
	            "Empujar/tirar de una silla",
	            "Encender/apagar la luz",
	            "Abrir/cerrar puerta baño",
	            "Beber agua",
	            "Quitar ventosas del cristal"},
			{		"Echar agua de un recipiente a otro",
	                "Hacer la cama",
	                "Meter la almohada en la funda",
	                "Doblar la ropa y llevarla desde la cama a la mesa",
	                "Organizar el armario",
	                "Organizar la estantería",
	                "Preparar la mesa para comer",
	                "Cortar masilla",
	                "Abrir/cerrar tendedero",
	                "Lavarse los dientes",
	                "Colocar la estantería del baño",
	                "Sacar/guardar cubiertos",
	                "Sacar/guardar platos del armario",
	                "Comer con cuchara (sólidos)",
	                "Cambiar objetos de recipiente cogiéndolos con una pinza"},
			{			"Llevar canicas de un recipiente a otro con dedos I y II, III, IV, V",
	                    "Abrir/cerrar tarros",
	                    "Darle la vuelta a las cartas",
	                    "Doblar y colocar servilletas",
	                    "Poner pinzas de la ropa",
	                    "Quitar pinzas de la ropa",
	                    "Recolocar la ropa en el tendedero",
	                    "Echar con una cuchara café en un recipiente",
	                    "Puzzle destreza (cabezones)",
	                    "Pasar páginas de un libro I y II, III, IV, V",
	                    "Girar fichas del dominó",
	                    "Coger garbanzos en un cesto con arroz",
	                    "Abrir/cerrar con la llave"},
			{				"Botar una pelota",
	                        "Coger una canica en movimiento",
	                        "Devolver pelota con raqueta pin pon",
	                        "Dar toques a un globo"}
			
	};
	private boolean[] nums;
	private int total;
	
	/**
	 * Constructor para identificar las discapacidades del paciente que hace la comprobacion con las puntuaciones, para pasarlos a booleano
	 * @param miembroS
	 * @param mune
	 * @param mano
	 * @param coordi
	 */
	
	public Actividad(int miembroS, int mune, int mano, int coordi) {
		if(miembroS>=15) {
			nums[0]=true;
			total += actividades[0].length;
		}
		if(mune>=5) {
			nums[1]=true;
			total += actividades[1].length;
		}
		if(mano>=7){
			nums[2]=true;
			total += actividades[2].length;
		}
		if(coordi>=3) {
			nums[3]=true;
			total += actividades[3].length;
		}
	}
	
	/**
	 * Método que guarda las discapacidades del paciente a través de booleans
	 * @param ms
	 * @param mnc
	 * @param m
	 * @param cv
	 */
	
	public Actividad(boolean ms, boolean mnc, boolean m, boolean cv){
		nums = new boolean[4];
		if(ms) {
			nums[0] = true;
			total += actividades[0].length;
		}
		if(mnc) {
			nums[1] = true;
			total += actividades[1].length;
		}
		if(m){
			nums[2] = true;
			total += actividades[2].length;
		}
		if(cv) {
			nums[3] = true;
			total += actividades[3].length;
		}
	}
	
	/**
	 * Método que devuelve el total de ejercicios de un paciente, a partir de sus discapacidades
	 * @return
	 */
	
	public int getTotal() {
		return total;
	}
	
	/**
	 * Método que genera un ejercicio y lo devuelve para que en otro método llame a este para generar ejercicios hasta que devuelva uno válido
	 * @deprecated
	 * @return
	 */
	
	public String setActividad() {
		int opcion1=(int)(Math.random()*4);
		while(!nums[opcion1]) {
			opcion1=(int)(Math.random()*4);
		}
		int opcion2=(int)(Math.random()*actividades[opcion1].length);
		while(actividades[opcion1][opcion2].equals("_")) {
			opcion2=(int)(Math.random()*actividades[opcion1].length);
		}
		return actividades[opcion1][opcion2];
	}
	
	/**
	 * Método que dado un array con los ejercicios realizados del paciente genera ejercicios que no haya hecho
	 * @param id
	 * @param realizados
	 * @return
	 */
	
	public String[] refreshActuales(int id, ArrayList<String> realizados) {
        Set<String> set = new HashSet<>();
        String [] ejs = new String[4];
        for (int i = 0; i < realizados.size(); i++) {
			set.add(realizados.get(i));
		}
        for (int i = 0; i < ejs.length; ) {
        	/**Comprobación que hace el método para que cuando se hayan cogido todos los ejercicios posibles de las 
        	 * discapacidades del paciente, se vuelvan a empezar a coger ejercicios. Para una nueva serie.
        	*/
        	if (set.size() == total) {
        		set.clear();
				Writer.nuevaSerie(id);
				for (int j = 0; j < ejs.length; j++) {
					if (ejs[j] == null) {
						j += 5;
					}else {
						set.add(ejs[j]);
					}
				}
			}
        	int opcion1=(int)(Math.random()*4);
        	
    		while(!nums[opcion1]) {
    			opcion1=(int)(Math.random()*4);
    		}
    		int opcion2 = (int)(Math.random()*actividades[opcion1].length);
    		
    		if (set.add(actividades[opcion1][opcion2])) {
				ejs[i] = actividades[opcion1][opcion2];
				i++;
			}
		}
        return ejs;
	}
	
	public String[] returnActuales(int id, String[] realizados, String[] betados) {
        Set<String> set = new HashSet<>();
        String [] ejs = new String[4];
        for (int i = 0; i < betados.length; i++) {
			set.add(betados[i]);
		}
        for (int i = 0; i < realizados.length; i++) {
			set.add(realizados[i]);
		}
        for (int i = 0; i < ejs.length; ) {
        	/**Comprobación que hace el método para que cuando se hayan cogido todos los ejercicios posibles de las 
        	 * discapacidades del paciente, se vuelvan a empezar a coger ejercicios. Para una nueva serie.
        	*/
        	if (set.size() == total) {
        		set.clear();
				for (int j = 0; j < ejs.length; j++) {
					if (ejs[j] == null) {
						j += 5;
					}else {
						set.add(ejs[j]);
					}
				}
			}
        	int opcion1=(int)(Math.random()*4);
        	
    		while(!nums[opcion1]) {
    			opcion1=(int)(Math.random()*4);
    		}
    		int opcion2 = (int)(Math.random()*actividades[opcion1].length);
    		
    		if (set.add(actividades[opcion1][opcion2])) {
				ejs[i] = actividades[opcion1][opcion2];
				i++;
			}
		}
        return ejs;
	}
	
	public String nuevoEjercicio(int id, String[] realizados, String[] betados) {
		Set<String> set = new HashSet<>();
		String ej = "";
		String lastBan = "";
        for (int i = 0; i < betados.length; i++) {
			set.add(betados[i]);
			if (i == betados.length - 1) {
				lastBan = betados[i];
			}
		}
        for (int i = 0; i < realizados.length; i++) {
			set.add(realizados[i]);
		}
        if (set.size() == total) {
			ExcelWriter.cleanBanned(id);
			set.clear();
			ExcelWriter.banear(id, lastBan);
			set.add(lastBan);
	        for (int i = 0; i < realizados.length; i++) {
				set.add(realizados[i]);
			}
		}
        
        while(true) {
        	int opcion1=(int)(Math.random()*4);
        	while(!nums[opcion1]) {
    			opcion1=(int)(Math.random()*4);
    		}
    		int opcion2 = (int)(Math.random()*actividades[opcion1].length);
    		if (set.add(actividades[opcion1][opcion2])) {
				ej = actividades[opcion1][opcion2];
				break;
			}
        }
        return ej;
	}
	
	/**
	 * Método que devuelve un array de todos los ejercicios posibles con las discapacidades del paciente
	 * @return
	 */
	
	public String[] todos() {
		String[] todos = new String[total];
		
		int h = 0, v = 0;
		
		for (int i = 0; v < todos.length; i++) {
			while(!nums[h]) {
				h++;
			}
			for (int j = 0; h < 4 && j < actividades[h].length ; j++) {
				todos[v] = actividades[h][j];
				v++;
				if (j == actividades[h].length - 1) {
					h++;
				}
				if (h > 3) {
					
				}
			}
		}
		return todos;
	}
	
	/**
	 * Método que junto con el @method setActividad() comprobaba que esos ejercicios no se hubiesen hecho
	 * @deprecated
	 * @param array
	 * @return
	 */
	
	public boolean comprobarRepetidos(String[] array) {
        // Crear un conjunto para almacenar elementos sin duplicados
        Set<String> set = new HashSet<>();
        boolean b1=false;
        // Iterar sobre el array para verificar duplicados
        for (String elemento : array) {
            // Si el set ya contiene el elemento, es un duplicado
            if (!set.add(elemento)) {
                b1=true;
            }
        }
		return b1;
    }
}
