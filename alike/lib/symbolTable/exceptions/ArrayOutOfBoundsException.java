/*********************************************************************************
 * Excepción utilizada al intentar acceder a un índice de un array fuera de rango.
 * 
 *
 * Fichero:    ArrayOutOfBoundsException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class ArrayOutOfBoundsException extends Error {

	public ArrayOutOfBoundsException(int min, int max, int index) {
		super("Array index out of bounds: " + index + " not in [" + min + ", " + max + "]");

	}
}
