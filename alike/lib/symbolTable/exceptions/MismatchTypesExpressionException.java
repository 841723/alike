/*********************************************************************************
 * Excepción utilizada al intentar realizar una operación con tipos no compatibles
 * 
 *
 * Fichero:    MismatchTypesExpressionException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol.Types;

public class MismatchTypesExpressionException extends Error {

	public MismatchTypesExpressionException(Types type1, Types type2) {
		super("Mismatch types in expression. Found: " + type1 + " and " + type2);
	}

	
}
