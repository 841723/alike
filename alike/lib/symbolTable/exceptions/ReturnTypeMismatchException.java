/*********************************************************************************
 * Excepción utilizada al intentar devolver un tipo distinto que devuelve la 
 * función en la que se encuentra el return
 *
 * Fichero:    ReturnTypeMismatchException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol.Types;

public class ReturnTypeMismatchException extends Error {

	public ReturnTypeMismatchException(Types may, Types returned) {
		super("Return type mismatch. Expected: " + may + " but found: " + returned);

	}
}
