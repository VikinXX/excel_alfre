package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import json.Reader;

public class ExcelWriter {
	
	/**
	 * Método que setea lso ejercicios realizados de cada jugador
	 */
	
	public static void setEjerciciosRealizadosJSON() {
		String rutaArchivo = "data/data_ejercicios.xlsx";

		try (FileInputStream fis = new FileInputStream(rutaArchivo); Workbook workbook = new XSSFWorkbook(fis)) {
			int row = 1;
			int colum = 0;
			Sheet hoja = workbook.getSheetAt(0); // Primera hoja
			Row fila = hoja.getRow(row); // Primera fila (índice 0)

			Cell celda = fila.getCell(colum); // Primera celda (A1)
			
			int id = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
			
			while (getOrCreateCell(hoja, row, colum).getCellType() != CellType.BLANK) {
				
				int row2 = row;
				int colum2 = colum;
				ArrayList<String> ejs = new ArrayList<>();
				row2++;
				colum2++;
				if (getOrCreateCell(hoja, row2, colum2).getCellType() != CellType.BLANK) {
					for (int i = row2, n = 0; n < 4; i++, n++) {
						ejs.add(getOrCreateCell(hoja, i, colum2).getStringCellValue());
					}
				}
				while (getOrCreateCell(hoja, row2, colum2 + 6).getCellType() != CellType.BLANK) {
					colum2 += 6;
					for (int i = row2, n = 0; n < 4; i++, n++) {
						ejs.add(getOrCreateCell(hoja, i, colum2).getStringCellValue());
					}
				}
				
				setRealizados(id, ejs, ExcelReader.getDiasExcel(id));
				row += 7;
				id = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
			}
			

		} catch (IOException e) {
			e.toString();
		}
		
	}
	
	/**
	 * Método que setea los ejercicios realizados de un jugador
	 * @param id
	 * @param lista
	 * @param dias
	 */

	public static void setRealizados(int id, ArrayList<String> lista, int dias) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");

		JSONObject newPacienteEj = new JSONObject();

		newPacienteEj.put("paciente", id);
		newPacienteEj.put("miembro_superior", true);
		newPacienteEj.put("mano", true);
		newPacienteEj.put("muñeca", true);
		newPacienteEj.put("coordinacion_velocidad", true);
		newPacienteEj.put("dia", dias);

		JSONArray ejercicios = new JSONArray();
		JSONArray betados = new JSONArray();
		
		newPacienteEj.put("ejercicios", ejercicios);
		newPacienteEj.put("betados", betados);

		
		JSONArray ejs = new JSONArray();
		
		for (int i = 0; i < lista.size(); i++) {
			JSONObject ej = new JSONObject();
			ej.put("ejercicio", lista.get(i));
			ejs.put(ej);
		}
		
		newPacienteEj.put("ejercicios", ejs);
		
		JSONArray actuales = new JSONArray(); 
		
		for (int i = lista.size() - 1, n = 0; n < 4; i--, n++) {
			JSONObject actual = new JSONObject();
			actual.put("ejercicio", lista.get(i));
			actuales.put(actual);
		}
		
		newPacienteEj.put("actuales", actuales);
		pacientesEj.put(newPacienteEj);


		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(jsonEj.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que crea un nuevo paciente
	 * @param id
	 * @param miembroS
	 * @param mano
	 * @param muneca
	 * @param coordinacion
	 */

	public static void nuevoPacienteJSON(int id, boolean miembroS, boolean muneca, boolean mano, boolean coordinacion, String[] todos) {
		String contenidoEj = null;
		try {
			contenidoEj = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonEj = new JSONObject(contenidoEj);
		JSONArray pacientesEj = jsonEj.getJSONArray("pacientes");

		JSONObject newPacienteEj = new JSONObject();

		newPacienteEj.put("paciente", id);
		newPacienteEj.put("miembro_superior", miembroS);
		newPacienteEj.put("mano", mano);
		newPacienteEj.put("muñeca", muneca);
		newPacienteEj.put("coordinacion_velocidad", coordinacion);
		newPacienteEj.put("dia", 0);

		JSONArray ejercicios = new JSONArray();
		JSONArray betados = new JSONArray();
		
		newPacienteEj.put("ejercicios", ejercicios);
		newPacienteEj.put("betados", betados);

		
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
	}
	
	/**
	 * Método que crea un nuevo paciente en el excel
	 * @param id
	 * @param miembroS
	 * @param mano
	 * @param muneca
	 * @param coordinacion
	 */
	
	public static void nuevoPacienteExcel(int id, boolean miembroS, boolean muneca, boolean mano, boolean coordinacion) {
        String rutaArchivo = "data/data_ejercicios.xlsx";

        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0);
            int row = 1;
            int colum = 0;

            Row filaLoop = hoja.getRow(row);
            Cell celdaLoop = filaLoop.getCell(colum);
            // Buscar la primera fila libre
            while (celdaLoop.getCellType() != CellType.BLANK) {
                filaLoop = hoja.getRow(row);
                if (filaLoop == null) break;
                celdaLoop = filaLoop.getCell(colum);
                if (celdaLoop == null || celdaLoop.getCellType() == CellType.BLANK) break;
                row += 7;
            }

            // Unir celdas para ID
            CellRangeAddress rango = new CellRangeAddress(row, row + 4, colum, colum);
            hoja.setColumnWidth(colum, (int) (2.34 * 256 / 0.143));
            hoja.addMergedRegion(rango);
            getOrCreateCell(hoja, row, colum).setCellValue(id);
            
            // Crear el estilo con bordes negros
            CellStyle estiloBorde = workbook.createCellStyle();
            estiloBorde.setBorderTop(BorderStyle.THICK);
            estiloBorde.setBorderBottom(BorderStyle.THICK);
            estiloBorde.setBorderLeft(BorderStyle.THICK);
            estiloBorde.setBorderRight(BorderStyle.THICK);

            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 18);
            
            estiloBorde.setFont(fuenteID);

            // Establecer el color del borde
            estiloBorde.setTopBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setRightBorderColor(IndexedColors.BLACK.getIndex());
            
            estiloBorde.setAlignment(HorizontalAlignment.CENTER);
            estiloBorde.setVerticalAlignment(VerticalAlignment.CENTER);

            for (int i = rango.getFirstRow(); i < rango.getLastRow() ; i++) {
				Cell cel = getOrCreateCell(hoja, i, colum);
				cel.setCellStyle(estiloBorde);
			}

            // Crear encabezado lateral
            CellStyle estiloResto = workbook.createCellStyle();
            estiloResto.cloneStyleFrom(estiloBorde);
            Font fuente = workbook.createFont();
            fuente.setFontName("Aptos Narrow");
            fuente.setFontHeightInPoints((short) 11);
            estiloResto.setFont(fuente);
            
            colum += 1;
            hoja.setColumnWidth(colum, 15114); // 6 cm
            getOrCreateCell(hoja, row, colum).setCellValue("Actividad");
            getOrCreateCell(hoja, row, colum).setCellStyle(estiloResto);

            // Actividades semanales
            getOrCreateCell(hoja, row + 1, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 2, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 3, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 4, colum).setCellStyle(estiloResto);
            
            
            colum += 1;
            hoja.addMergedRegion(new CellRangeAddress(row, row, colum, colum + 4));
            for (int i = colum, n = 0; n < 5; i++, n++) {
                hoja.setColumnWidth(i, 4190);
                getOrCreateCell(hoja, row, i).setCellStyle(estiloResto);
			}
            Date fecha = new Date();
            int dia = fecha.getDate();
            int mes = fecha.getMonth();
            int dia2;
			int mes2;

            YearMonth dias = YearMonth.of(fecha.getYear(), mes);
            if (dia + 4 > dias.lengthOfMonth()) {
				dia2 = dias.lengthOfMonth() - dia;
				mes2 = mes + 1;
			}else {
				dia2 = dia + 4;
				mes2 = mes;
			}
            getOrCreateCell(hoja, row, colum).setCellValue("Semana " + dia + "/" + mes + "-" + dia2 + "/" + mes2);
            
            CellStyle puntuaciones = workbook.createCellStyle();
            
            puntuaciones.setAlignment(HorizontalAlignment.CENTER);
            puntuaciones.setBorderTop(BorderStyle.THICK);
            puntuaciones.setBorderBottom(BorderStyle.THICK);
            puntuaciones.setTopBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setRightBorderColor(IndexedColors.BLACK.getIndex());
            
            for (int i = row, n = 0; n < 4; i++, n++) {
				for (int j = colum, n2 = 0; n2 < 5; j++, n2++) {
					getOrCreateCell(hoja, i, j).setCellStyle(puntuaciones);
				}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * Método para crear el formato de unos nuevos ejercicios
	 */
	
	public static void nuevosEjerciciosExcel(int id) {
        String rutaArchivo = "data/data_ejercicios.xlsx";

        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0);
            int row = 1;
            int colum = 0;

            Row filaLoop = hoja.getRow(row);
            Cell celdaLoop = filaLoop.getCell(colum);
            // Buscar la primera fila libre
            while (celdaLoop.getNumericCellValue() != id) {
                filaLoop = hoja.getRow(row);
                if (filaLoop == null) break;
                celdaLoop = filaLoop.getCell(colum);
                if (celdaLoop == null || celdaLoop.getCellType() == CellType.BLANK) break;
                row += 7;
            }
            while (getOrCreateCell(hoja, row, colum + 6).getCellType() != CellType.BLANK) {
            	colum += 6;
			}
            
            hoja.setColumnWidth(colum, (int) (2.34 * 256 / 0.143));
            
            // Crear el estilo con bordes negros
            CellStyle estiloBorde = workbook.createCellStyle();
            estiloBorde.setBorderTop(BorderStyle.THICK);
            estiloBorde.setBorderBottom(BorderStyle.THICK);
            estiloBorde.setBorderLeft(BorderStyle.THICK);
            estiloBorde.setBorderRight(BorderStyle.THICK);

            // Establecer el color del borde
            estiloBorde.setTopBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            estiloBorde.setRightBorderColor(IndexedColors.BLACK.getIndex());
            
            estiloBorde.setAlignment(HorizontalAlignment.CENTER);
            estiloBorde.setVerticalAlignment(VerticalAlignment.CENTER);

            // Crear encabezado lateral
            CellStyle estiloResto = workbook.createCellStyle();
            estiloResto.cloneStyleFrom(estiloBorde);
            Font fuente = workbook.createFont();
            fuente.setFontName("Aptos Narrow");
            fuente.setFontHeightInPoints((short) 11);
            estiloResto.setFont(fuente);
            
            colum += 1;
            hoja.setColumnWidth(colum, 15114); // 6 cm
            getOrCreateCell(hoja, row, colum).setCellValue("Actividad");
            getOrCreateCell(hoja, row, colum).setCellStyle(estiloResto);

            // Actividades semanales
            getOrCreateCell(hoja, row + 1, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 2, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 3, colum).setCellStyle(estiloResto);
            getOrCreateCell(hoja, row + 4, colum).setCellStyle(estiloResto);
            
            
            colum += 1;
            hoja.addMergedRegion(new CellRangeAddress(row, row, colum, colum + 4));
            for (int i = colum, n = 0; n < 5; i++, n++) {
                hoja.setColumnWidth(i, 4190);
                getOrCreateCell(hoja, row, i).setCellStyle(estiloResto);
			}
            Date fecha = new Date();
            int dia = fecha.getDate();
            int mes = fecha.getMonth();
            int dia2;
			int mes2;

            YearMonth dias = YearMonth.of(fecha.getYear(), mes);
            if (dia + 4 > dias.lengthOfMonth()) {
				dia2 = dias.lengthOfMonth() - dia;
				mes2 = mes + 1;
			}else {
				dia2 = dia + 4;
				mes2 = mes;
			}
            getOrCreateCell(hoja, row, colum).setCellValue("Semana " + dia + "/" + mes + "-" + dia2 + "/" + mes2);
            
            CellStyle puntuaciones = workbook.createCellStyle();
            
            puntuaciones.setAlignment(HorizontalAlignment.CENTER);
            puntuaciones.setBorderTop(BorderStyle.THICK);
            puntuaciones.setBorderBottom(BorderStyle.THICK);
            puntuaciones.setTopBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            puntuaciones.setRightBorderColor(IndexedColors.BLACK.getIndex());
            
            for (int i = row, n = 0; n < 4; i++, n++) {
				for (int j = colum, n2 = 0; n2 < 5; j++, n2++) {
					getOrCreateCell(hoja, i, j).setCellStyle(puntuaciones);
				}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	/**
	 * Método para coger o crear una fila
	 * @param hoja
	 * @param rowIndex
	 * @return
	 */
	
    // Auxiliar: asegura que la fila exista
    public static Row getOrCreateRow(Sheet hoja, int rowIndex) {
        Row row = hoja.getRow(rowIndex);
        return (row != null) ? row : hoja.createRow(rowIndex);
    }
    
    /**
     * Método para coger o crear una celda
     * @param hoja
     * @param rowIndex
     * @param colIndex
     * @return
     */

    // Auxiliar: asegura que la celda exista
    public static Cell getOrCreateCell(Sheet hoja, int rowIndex, int colIndex) {
        Row row = getOrCreateRow(hoja, rowIndex);
        Cell cell = row.getCell(colIndex);
        return (cell != null) ? cell : row.createCell(colIndex);
    }
    
    /**
     * Método que escribe los 4 ejercicios
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

		for (int i = 0; i < pacientes.length() && !encontrado; i++) {
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

	
	/**
	 * Método que escribe nuevas puntuaciones
	 * @param id
	 * @param actividad
	 * @param punt
	 */

	public static void escribirPuntuacion(int id, String actividad, int punt) {
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
                id2 = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            row++;
            colum++;
            String nextEj = "";
            boolean finales = false;
            while (!finales) {
            	if (getOrCreateCell(hoja, row, colum + 6).getCellType() != CellType.BLANK) {
            		colum += 6;
                    nextEj = getOrCreateCell(hoja, row, colum + 6).getStringCellValue();
    			}else {
    				finales = true;
    			}
			}
            
            CellStyle estilo = workbook.createCellStyle();
            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 11);
            
            estilo.setFont(fuenteID);
            
            for (int i = row, n = 0; n < 4; i++, n++) {
            	if (getOrCreateCell(hoja, i, colum).getStringCellValue().equals(actividad)) {
            		colum++;
            		getOrCreateCell(hoja, i, colum).setCellStyle(estilo);
					getOrCreateCell(hoja, i, colum).setCellValue(punt);
					break;
				}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Método que blanquea los ejercicios para que empiece una nueva serie
	 * @deprecated 
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

		for (int i = 0; i < pacientes.length() && !encontrado; i++) {
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

		for (int i = 0; i < pacientes.length() && !encontrado ; i++) {
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
	 * Método que banea un ejericio
	 * @param id
	 * @param actividad
	 */
	
	public static void banear(int id, String actividad) {
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
				JSONArray betados = paciente.getJSONArray("betados");
				
				JSONObject ej = new JSONObject();
				ej.put("ejercicio", actividad);
				
				betados.put(ej);
				
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
	 * Método que escribe los ejercicios actuales en el excel
	 * @param id
	 * @param actividad
	 */
	
	public static void escribirActualesExcel(int id, String[] actividad) {
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
                id2 = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            
            CellStyle estilo = workbook.createCellStyle();
            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 11);
            
            estilo.setFont(fuenteID);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            
            colum++;
            row++;
            String nextEj = "";
            boolean finales = false;
            while (!finales) {
            	if(getOrCreateCell(hoja, row, colum).getCellType() == CellType.BLANK) {
    				finales = true;
    			}else if (getOrCreateCell(hoja, row, colum + 6).getCellType() != CellType.BLANK) {
            		colum += 6;
                    nextEj = getOrCreateCell(hoja, row, colum + 6).getStringCellValue();
    			}else if(getOrCreateCell(hoja, row, colum + 6).getCellType() == CellType.BLANK) {
    				colum += 6;
    				finales = true;
    			} 
			}
            
            hoja.setColumnWidth(colum, 15114);
            getOrCreateCell(hoja, row - 1, colum).setCellValue("Actividad");
            getOrCreateCell(hoja, row - 1, colum).setCellStyle(estilo);
            
            for (int i = colum + 2, n = 0; n < 5; i++, n++) {
                getOrCreateCell(hoja, row, i).setCellStyle(estilo);
			}
            
            Date fecha = new Date();
            int dia = fecha.getDate();
            int mes = fecha.getMonth();
            int dia2;
			int mes2;

            YearMonth dias = YearMonth.of(fecha.getYear(), mes);
            if (dia + 4 > dias.lengthOfMonth()) {
				dia2 = dias.lengthOfMonth() - dia;
				mes2 = mes + 1;
			}else {
				dia2 = dia + 4;
				mes2 = mes;
			}
            getOrCreateCell(hoja, row - 1, colum + 1).setCellValue("Semana " + dia + "/" + mes + "-" + dia2 + "/" + mes2);
            
            for (int i = row, n = 0; n < 4; i++, n++) {
            	getOrCreateCell(hoja, i, colum).setCellValue(actividad[n]);
            	getOrCreateCell(hoja, i, colum).setCellStyle(estilo);
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Método que escribe la puntuacion de un ejercicio
	 * @param id
	 * @param actividad
	 * @param punt
	 */
	
	public static void escribirPuntuacionExcel(int id, String actividad, int punt) {
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
                id2 = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            CellStyle estilo = workbook.createCellStyle();
            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 11);
            
            estilo.setFont(fuenteID);
            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            
            row++;
            colum++;
            String nextEj = "";
            boolean encontrado = false;
            while (!encontrado) {
            	if (getOrCreateCell(hoja, row, colum).getCellType() == CellType.STRING) {
            		for (int i = row, n = 0; n < 4 && !encontrado; i++, n++) {
						if (getOrCreateCell(hoja, i, colum).getStringCellValue().equals(actividad)) {
							colum++;
							for (int j = colum, n2 = 0; n2 < 5 && !encontrado; j++, n2++) {
								if (getOrCreateCell(hoja, i, j).getCellType() == CellType.BLANK) {
									getOrCreateCell(hoja, i, j).setCellValue(punt);
									getOrCreateCell(hoja, row, colum).setCellStyle(estilo);
									encontrado = true;
								}
							}
						}
					}
            		colum += 6;
    			}else {
    				encontrado = true;
    			}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Método que escribe la puntuacion con 1 de dificultad
	 * @param id
	 * @param actividad
	 * @param punt
	 */
	
	public static void escribirPuntuacion1DificultadExcel(int id, String actividad, int punt) {
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
                id2 = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            byte[] rgb = new byte[] {
            	    (byte) 0xED, // Rojo
            	    (byte) 0xA5, // Verde
            	    (byte) 0xC8  // Azul
            };
            XSSFColor colorPersonalizado = new XSSFColor(rgb, null);
            CellStyle estilo = workbook.createCellStyle();
            ((XSSFCellStyle)estilo).setFillForegroundColor(colorPersonalizado);
            estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            estilo.setBorderTop(BorderStyle.THICK);
            estilo.setBorderBottom(BorderStyle.THICK);
            estilo.setTopBorderColor(IndexedColors.BLACK.getIndex());
            estilo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 11);
            
            estilo.setFont(fuenteID);
            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            
            row++;
            colum++;
            String nextEj = "";
            boolean encontrado = false;
            while (!encontrado) {
            	if (getOrCreateCell(hoja, row, colum).getCellType() == CellType.STRING) {
            		for (int i = row, n = 0; n < 4 && !encontrado; i++, n++) {
						if (getOrCreateCell(hoja, i, colum).getStringCellValue().equals(actividad)) {
							colum++;
							for (int j = colum, n2 = 0; n2 < 5 && !encontrado; j++, n2++) {
								if (getOrCreateCell(hoja, i, j).getCellType() == CellType.BLANK) {
									getOrCreateCell(hoja, i, j).setCellValue(punt);;
									getOrCreateCell(hoja, i, j).setCellStyle(estilo);
									encontrado = true;
								}
							}
						}
					}
            		colum += 6;
    			}else {
    				encontrado = true;
    			}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Método que escribe la puntuacion con 2 de dificultad
	 * @param id
	 * @param actividad
	 * @param punt
	 */
	
	public static void escribirPuntuacion2DificultadExcel(int id, String actividad, int punt) {
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
                id2 = (int) getOrCreateCell(hoja, row, colum).getNumericCellValue();
            }
            byte[] rgb = new byte[] {
            	    (byte) 0xF8, // Rojo
            	    (byte) 0xCB, // Verde
            	    (byte) 0xAD  // Azul
            };
            XSSFColor colorPersonalizado = new XSSFColor(rgb, null);
            CellStyle estilo = workbook.createCellStyle();
            ((XSSFCellStyle)estilo).setFillForegroundColor(colorPersonalizado);
            estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            estilo.setBorderTop(BorderStyle.THICK);
            estilo.setBorderBottom(BorderStyle.THICK);
            estilo.setTopBorderColor(IndexedColors.BLACK.getIndex());
            estilo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            
            Font fuenteID = workbook.createFont();
            fuenteID.setFontName("Aptos Narrow");
            fuenteID.setFontHeightInPoints((short) 11);
            
            estilo.setFont(fuenteID);
            estilo.setVerticalAlignment(VerticalAlignment.CENTER);
            estilo.setAlignment(HorizontalAlignment.CENTER);
            
            row++;
            colum++;
            String nextEj = "";
            boolean encontrado = false;
            while (!encontrado) {
            	if (getOrCreateCell(hoja, row, colum).getCellType() == CellType.STRING) {
            		for (int i = row, n = 0; n < 4 && !encontrado; i++, n++) {
						if (getOrCreateCell(hoja, i, colum).getStringCellValue().equals(actividad)) {
							colum++;
							for (int j = colum, n2 = 0; n2 < 5 && !encontrado; j++, n2++) {
								if (getOrCreateCell(hoja, i, j).getCellType() == CellType.BLANK) {
									getOrCreateCell(hoja, i, j).setCellValue(punt);
									getOrCreateCell(hoja, i, j).setCellStyle(estilo);
									encontrado = true;
								}
							}
						}
					}
            		colum += 6;
    			}else {
    				encontrado = true;
    			}
			}

            // Guardar el archivo
            try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Método que limpia los betados de el JSON porque no hay mas ejercicios
	 * @param id
	 */
	
	public static void cleanBanned(int id) {
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
				paciente.put("betados", new JSONArray());
				
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
	 * Método para ver las puntuaciones que hemos escrito
	 */
	
	public static void dia(int id) {
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
				
				dia++;
				
				if(dia == 6) {
					dia = 1;
				}
				
				paciente.put("dia", dia);
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
	 * Método que borra todos los pacientes para escribirlos de nuevo
	 */
	
	public static void borrarPacientes() {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/ejercicios.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		
		json.put("pacientes", new JSONArray());
		
		try (FileWriter file = new FileWriter("data/ejercicios.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
