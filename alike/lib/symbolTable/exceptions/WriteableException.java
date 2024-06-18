/*********************************************************************************
 * Excepción utilizada al intentar escribir algo en pantalla que no se puede.
 * 
 *
 * Fichero:    WriteableException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol;

public class WriteableException extends Error {

	public WriteableException(Symbol s) {
		super("Cannot write " + s.name + " because its "+ s.type);
	}
}
