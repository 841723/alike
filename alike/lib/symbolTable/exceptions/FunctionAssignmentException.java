/*********************************************************************************
 * Excepción utilizada al intentar asignar un valor a una funcion.
 * 
 *
 * Fichero:    FunctionAssignmentException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol;

public class FunctionAssignmentException extends Error {

	public FunctionAssignmentException(String name, Symbol.Types type) {
		super("Cannot assign " + name + " because its a " + type);

	}
}
