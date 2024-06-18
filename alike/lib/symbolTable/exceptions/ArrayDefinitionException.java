/*********************************************************************************
 * Excepción utilizada al intentar realizar una operación con tipos no compatibles
 * 
 *
 * Fichero:    ArrayDefinitionException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class ArrayDefinitionException extends Error {

	public ArrayDefinitionException(String message) {
		super(message);
	}
}
