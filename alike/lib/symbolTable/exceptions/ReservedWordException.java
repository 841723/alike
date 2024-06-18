/*********************************************************************************
 * Excepción utilizada al intentar declarar una palabra reservada como identificador
 * 
 *
 * Fichero:    ReservedWordException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class ReservedWordException extends Error {

	public ReservedWordException() {
		super("Cannot use reserved word");
	}
}
