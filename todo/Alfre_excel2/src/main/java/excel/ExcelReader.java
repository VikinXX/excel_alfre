package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExcelReader {
	
	/**
	 * Método que comprueba si es la primera vez que se inicia el programa
	 * @return 
	 */

	public static boolean firstTime() {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		if (jsonEj.getBoolean("first-time")) {
			jsonEj.put("first-time", false);
			try (FileWriter file = new FileWriter("data/ejercicios.json")) {
				file.write(jsonEj.toString(4));
				file.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;

	}
	
	/**
	 * Método que comprueba que el paciente existe
	 * 
	 * @param id
	 * @return
	 */

	// Son 7 saltos de fila para cada id
	public static boolean isReal(int id) {
		String rutaArchivo = "data/data_ejercicios.xlsx";

		try (FileInputStream fis = new FileInputStream(rutaArchivo); Workbook workbook = new XSSFWorkbook(fis)) {
			int row = 1;
			Sheet hoja = workbook.getSheetAt(0); // Primera hoja
			Row fila = hoja.getRow(row); // Primera fila (índice 0)

			Cell celda = fila.getCell(0); // Primera celda (A1)
			
//			System.out.println(getCellValueAsString(celda));
			
			while (celda.getCellType() != CellType.BLANK || celda.getCellType() != null) {
				int id2 = 0;
				if (celda.getCellType() == CellType.NUMERIC) {
					id2 = (int) celda.getNumericCellValue();

				}else if(celda.getCellType() == CellType.STRING) {
					id2 = Integer.parseInt(celda.getStringCellValue());
				}
				
				if (id2 == id) {
					return true;
				} else {
					row += 7;
					
					fila = ExcelWriter.getOrCreateRow(hoja, row);
					
					if (celda.getCellType() == CellType.BLANK || celda == null) {
						return false;
					}else {
						
						celda = ExcelWriter.getOrCreateCell(hoja, row, 0);
					}
				}
			}
			return false;

		} catch (IOException e) {
			
			return false;
		}
	}

	/**
	 * Método que devuelve un array con los ejercicios actuales del paciente
	 * introducido
	 * 
	 * @help Para poder recoger las actividades de cada paciente hay que moverse de
	 *       la celda inicial 3 h y 2 v
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
	 * Método que devuelve el array de los ejercicios realizados para posteriormente
	 * generar otro ejercicio que no esté en los ejercicios realizados
	 * 
	 * @param id
	 * @return null, si no hay ejercicios escitos en el archivo
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
	 * Método que devuelve los betados de cada jugador
	 * @param id
	 * @return
	 */
	
	public static String[] getBetados(int id) {
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

		for(int i = 0 ; i < pacientesEj.length() && !encontrado ; i++) {
			JSONObject pacienteEj = pacientesEj.getJSONObject(i);
			
			int pacid = pacienteEj.getInt("paciente");
			
			if (pacid == id) {
				
				JSONArray betados = pacienteEj.getJSONArray("betados");
				
				ejActuales = new String[betados.length()];
				
				for (int j = 0; j < betados.length(); j++) {
					JSONObject ejercicio = betados.getJSONObject(j);
					ejActuales[j] = ejercicio.getString("ejercicio");
				}
				encontrado = true;
			}
			
		}
		
		return ejActuales;
	}
	
	/**
	 * Método que devuelve los ejercicios realizados de cada jugador
	 * @param id
	 * @return
	 */

	public static String[] getEjerciciosRealizadosJSON(int id) {
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
	 * 
	 * @param id
	 * @return
	 */
	
	public static boolean getMiembroSuperior(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
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
	 * 
	 * @param id
	 * @return
	 */
	
	public static boolean getMuneca(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
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
	 * 
	 * @param id
	 * @return
	 */
	
	public static boolean getMano(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
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
	 * 
	 * @param id
	 * @return
	 */
	
	public static boolean getCoordinacionVelocidad(int id) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
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
	
	/**
	 * Método que devuelve el dia por el que va de las puntuaciones
	 * @param id
	 * @return
	 */
	
	public static int getDias(int id) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray pacientes = json.getJSONArray("pacientes");
		boolean encontrado = false;

		for (int i = 0; i < pacientes.length() && !encontrado ; i++) {
			JSONObject paciente = pacientes.getJSONObject(i);
			
			if (paciente.getInt("paciente") == id) {
				int dia = paciente.getInt("dia");
				
				return dia;
			}
		}

		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Método que devuelve el dia por el que va el excel de el paciente
	 * @param id
	 * @return
	 */
	
	public static int getDiasExcel(int id) {
		String rutaArchivo = "data/data_ejercicios.xlsx";

        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0);
            int row = 1;
            int colum = 0;

            Row filaLoop = hoja.getRow(row);
            Cell celdaLoop = filaLoop.getCell(colum);
            int id2 = (int) celdaLoop.getNumericCellValue();
            // Buscar el ID
            while (id2 != id) {
                row += 7;
                id2 = (int) ExcelWriter.getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            row++;
            colum++;
            String nextEj = "";
            boolean finales = false;
            while (!finales) {
            	if(ExcelWriter.getOrCreateCell(hoja, row, colum).getCellType() == CellType.BLANK) {
    				finales = true;
    			}else if (ExcelWriter.getOrCreateCell(hoja, row, colum + 6).getCellType() != CellType.BLANK) {
            		colum += 6;
                    nextEj = ExcelWriter.getOrCreateCell(hoja, row, colum + 6).getStringCellValue();
    			}else if(ExcelWriter.getOrCreateCell(hoja, row, colum + 6).getCellType() == CellType.BLANK) {
    				finales = true;
    			} 
			}
            int dias = 0;
            colum++;
            for (int i = colum, n = 0; n < 5; i++, n++) {
				if (ExcelWriter.getOrCreateCell(hoja, row, i).getCellType() == CellType.BLANK) {
					break;
				}
				dias++;
			}
            return dias;
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;

	}
}
