/*********************************************************************************
 * Excepción utilizada al intentar leer un tipo incorrecto.
 * 
 *
 * Fichero:    ReadableException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol;

public class ReadableException extends Error {

	public ReadableException(Symbol s) {
		super("Cannot read " + s.name + " because its " + s.type + " type is not readable. INT or CHAR expected.");
	}
	
	public ReadableException() {
		super("Cannot read literal, its not readable. INT or CHAR variable expected.");
	}
}
