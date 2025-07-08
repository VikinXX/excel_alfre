package json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Reader {
	
	/**
	 * Método que comprueba que el paciente existe
	 * @param id
	 * @return
	 */
	
	public static boolean existe(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");

		for(int i = 0;i<pacientesEj.length();i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (id == pacid) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método que devuelve un array con los ejercicios actuales del paciente introducido
	 * @param id
	 * @return
	 */
	
	public static String[] getActuales(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		String [] ejActuales = new String[4];
		boolean encontrado = false;

		for(int i = 0 ; i < pacientesEj.length() || !encontrado ; i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				JSONArray actuales = pacienteEj.getJSONArray("actuales");
				
				for (int j = 0; j < actuales.length(); j++) {
					JSONObject actual = actuales.getJSONObject(j);
					ejActuales[j] = actual.getString("ejercicio");
				}
				encontrado = true;
				
			}
			
		}
		
		return ejActuales;
	}
	
	/**
	 * Método que devuelve la serie por la que va el ejercicio
	 * @return 
	 */
	
	public static int getSeries(int id, String actividad) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		int serie = 0;
		
		boolean encontrado = false;

		for(int i = 0 ; i < pacientesEj.length() || !encontrado ; i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				JSONArray ejercicios = pacienteEj.getJSONArray("ejercicios");
				
				for (int j = 0; j < ejercicios.length(); j++) {
					JSONObject ejercicio = ejercicios.getJSONObject(j);
					
					if (ejercicio.getString("ejercicio").equals(actividad)) {
						JSONArray puntuaciones = ejercicio.getJSONArray("puntuaciones");
						for (int k = 0; k < puntuaciones.length(); k++) {
							JSONObject puntuacion = puntuaciones.getJSONObject(k);
							if (puntuacion.getInt("serie") > serie) {
								serie = puntuacion.getInt("serie");
							}
						}
						
					}
				}
				encontrado = true;
			}
			
		}
		
		return serie;
	}
	
	/**
	 * Método que devuelve el array de los ejercicios realizados para posteriormente generar otro ejercicio que no esté en los ejercicios realizados
	 * @param id
	 * @return
	 */
	
	public static String[] getEjerciciosRealizados(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		String [] ejActuales = null;
		boolean encontrado = false;

		for(int i = 0 ; i < pacientesEj.length() || !encontrado ; i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				JSONArray ejercicios = pacienteEj.getJSONArray("ejercicios");
				
				ejActuales = new String[ejercicios.length()];
				
				for (int j = 0; j < ejercicios.length(); j++) {
					JSONObject ejercicio = ejercicios.getJSONObject(j);
					ejActuales[j] = ejercicio.getString("ejercicio");
				}
				encontrado = true;
			}
			
		}
		
		return ejActuales;
	}
	
	/**
	 * Método que devuelve el Miembro Superior
	 * @param id
	 * @return
	 */
	
	public static boolean getMiembroSuperior(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		boolean b1 = false;
		
		for(int i = 0 ; i < pacientesEj.length() ; i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				b1 = pacienteEj.getBoolean("miembro_superior");
				
				return b1;
				
			}
			
		}
		return b1;
	}
	
	/**
	 * Método que devuelve el Muñeca
	 * @param id
	 * @return
	 */
	
	public static boolean getMuneca(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		boolean b1 = false;
		
		for(int i = 0;i<pacientesEj.length();i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				b1 = pacienteEj.getBoolean("muñeca");
				
				return b1;
				
			}
			
		}
		return b1;
	}
	
	/**
	 * Método que devuelve el Mano
	 * @param id
	 * @return
	 */
	
	public static boolean getMano(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		boolean b1 = false;
		
		for(int i = 0;i<pacientesEj.length();i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				b1 = pacienteEj.getBoolean("mano");
				
				return b1;
				
			}
			
		}
		return b1;
	}
	
	/**
	 * Método que devuelve el Coordinación/Velocidad
	 * @param id
	 * @return
	 */
	
	public static boolean getCoordinacionVelocidad(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		boolean b1 = false;
		
		for(int i = 0;i<pacientesEj.length();i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				b1 = pacienteEj.getBoolean("coordinacion_velocidad");
				
				return b1;
				
			}
			
		}
		return b1;
	}
	
}