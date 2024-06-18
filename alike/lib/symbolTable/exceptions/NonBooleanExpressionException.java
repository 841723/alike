/*********************************************************************************
 * Excepción utilizada al no poner una expresión booleana donde se requiere 
 * condiciones de if y while 
 *
 * Fichero:    NonBooleanExpressionException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol.Types;

public class NonBooleanExpressionException extends Error {

	public NonBooleanExpressionException(Types type) {
		super("Non boolean expression found in if/while. Found: " + type);

	}
}
