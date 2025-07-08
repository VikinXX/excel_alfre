package json;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Álvaro Ortiz
 * @version 1.0
 */

public class Writer {
	
	/**
	 * Método que crea un nuevo paciente
	 * @param id
	 * @param miembroS
	 * @param mano
	 * @param muneca
	 * @param coordinacion
	 */

	public static void nuevoPaciente(int id, boolean miembroS, boolean muneca, boolean mano, boolean coordinacion, String[] todos) {
		String contenidoEj = null;
		String contenidoPunt = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
			contenidoPunt = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONObject jsonPunt = new JSONObject(contenidoPunt);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");
		JSONArray pacientesPunt = jsonPunt.getJSONArray("pacientes");

		JSONObject newPacientePunt = new JSONObject();
		JSONObject newPacienteEj = new JSONObject();

		newPacientePunt.put("paciente", id);
		newPacienteEj.put("paciente", id);
		newPacientePunt.put("miembro_superior", miembroS);
		newPacienteEj.put("miembro_superior", miembroS);
		newPacientePunt.put("mano", mano);
		newPacienteEj.put("mano", mano);
		newPacientePunt.put("muñeca", muneca);
		newPacienteEj.put("muñeca", muneca);
		newPacientePunt.put("coordinacion_velocidad", coordinacion);
		newPacienteEj.put("coordinacion_velocidad", coordinacion);

		JSONArray ejercicios = new JSONArray();
		
		for (int i = 0; i < todos.length; i++) {
			JSONObject ejercicio = new JSONObject();
			
			ejercicio.put("ejercicio", todos[i]);
			
			JSONArray puntuaciones = new JSONArray();
			
			ejercicio.put("puntuaciones", puntuaciones);
			
			ejercicios.put(ejercicio);
		}
		
		newPacientePunt.put("ejercicios", ejercicios);

		pacientesPunt.put(newPacientePunt);
		
		JSONArray ejs = new JSONArray();
		
		newPacienteEj.put("ejercicios", ejs);
		
		JSONArray actuales = new JSONArray();
		
		newPacienteEj.put("actuales", actuales);
		pacientesEj.put(newPacienteEj);


		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(jsonEj.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileWriter file = new FileWriter("data/puntuaciones.json")) {
			file.write(jsonPunt.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que escribe nuevas puntuaciones
	 * @param id
	 * @param actividad
	 * @param punt
	 */

	public static void escribirPuntuacion(int id, String actividad, int punt) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/puntuaciones.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;

		for (int i = 0; i < pacientes.length() || !encontrado; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				JSONArray ejercicios = paciente.getJSONArray("ejercicios");

				for (int j = 0; j < ejercicios.length(); j++) {
					JSONObject ejercicio = ejercicios.getJSONObject(j);

					if (ejercicio.getString("ejercicio").equals(actividad)) {
						JSONArray puntuaciones = ejercicio.getJSONArray("puntuaciones");
						
						JSONObject nuevaPuntuacion = new JSONObject();
						nuevaPuntuacion.put("puntuacion", punt);
						nuevaPuntuacion.put("serie", Reader.getSeries(id, actividad) + 1);
						
						puntuaciones.put(nuevaPuntuacion);
					}
				}
				encontrado = true;
			}
		}

		try (FileWriter file = new FileWriter("data/puntuaciones.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que blanquea los ejercicios para que empiece una nueva serie
	 */
	
	public static void nuevaSerie(int id) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;

		for (int i = 0; i < pacientes.length() || !encontrado ; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				paciente.put("ejercicios", new JSONArray());
				
				encontrado = false;
			}
		}

		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que blanquea los ejercicios actuales
	 * @param id
	 */
	
	public static void nuevosActuales(int id) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;

		for (int i = 0; i < pacientes.length() || !encontrado; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				paciente.put("actuales", new JSONArray());

				encontrado = true;
			}
		}

		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que sobreescribe los ejercicios actuales
	 * @param id
	 * @param actividad
	 */
	
	public static void escribirActuales(int id, String[] actividad) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;
		
//		Writer.nuevosActuales(id);

		for (int i = 0; i < pacientes.length() || !encontrado ; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				JSONArray actuales = paciente.getJSONArray("actuales");

				for (int j = 0; j < actividad.length; j++) {
					JSONObject actual = new JSONObject();
					
					actual.put("ejercicio", actividad[j]);
					
					actuales.put(actual);
				}
				encontrado = true;
			}
		}

		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que escribe los nuevos ejericios generados
	 * @param id
	 * @param actividad
	 */
	
	public static void escribirEjercicios(int id, String[] actividad) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;

		for (int i = 0; i < pacientes.length() || !encontrado; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				JSONArray ejercicios = paciente.getJSONArray("ejercicios");

				for (int j = 0; j < actividad.length; j++) {
					JSONObject ejercicio = new JSONObject();
					
					ejercicio.put("ejercicio", actividad[j]);
					
					ejercicios.put(ejercicio);
				}
				encontrado = true;
			}
		}

		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}