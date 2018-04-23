package aplicacion;

import java.io.Serializable;

public class FlappyException extends Exception implements Serializable {
	public static final String FIN_DE_JUEGO = "El jugador %d ha muerto";
	public static final String ERROR_GUARDAR_ARCHIVO = "Ha surgido un inconveniente al guardar el archivo.";
	public static final String ARCHIVO_NO_ENCONTRADO = "No se ha encontrado el archivo seleccionado.";
	public static final String ERROR_ABRIR_ARCHIVO = "Ha surgido un inconveniente al abrir el archivo.";
	public static final String ERROR_IMPORTAR = "Ha surgido un inconveniente al importar el archivo.";
	public static final String ARGUMENTO_INVALIDO = "En la linea %d algun argumento no es valido.";
	public static final String CLASE_INVALIDA = "En la linea %d el valor (%s) no es una clase valida.";
	public static final String LINEA_INVALIDA = "En la linea %d el numero de valores recibidos (%d) no es valido ó la clase (%s) no existe.";
	public static final String LINEA_EN_BLANCO = "La linea %d está vacia.";
	public static final String OBTACULOS_VACIO = "No se encontro nungun obstaculo, agregue almenos uno.";
	public static final String EXTENSION_INVALIDA = "La extencion del archivo ingresada no es valida.";
	public static final String FORMATO_INVALIDO = "En la linea %d el formato no es el adecuado.";
	public static final String ERROR_FATAL = "Error fatal, el archivo no sigue la estructura minima necesaria, el error debe estar antes de la linea %d";
	
	
	public FlappyException(String message) {
		super(message);
	}
}
