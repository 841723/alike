/*********************************************************************************
 * Excepción utilizada al pasar como parámetro un procedure.
 * 
 *
 * Fichero:    UseProcedureValueException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class UseProcedureValueException extends Error {

	public UseProcedureValueException(String name) {
		super("Procedure " + name + " cannot be used as a value.");

	}
}
