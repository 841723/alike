/*********************************************************************************
 * Excepción utilizada al intentar asignar un valor a un array.
 * 
 *
 * Fichero:    ArrayAssignmentException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class ArrayAssignmentException extends Error {

	public ArrayAssignmentException(String name) {
		super("Cannot assign " + name + " because its an array");

	}
}
