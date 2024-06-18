/*********************************************************************************
 * Excepción utilizada al intentar declarar poner un return dentro de un bloque
 * que no es una función
 *
 * Fichero:    ReturnInNonReturnBlockException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import lib.symbolTable.Symbol.Types;

public class ReturnInNonReturnBlockException extends Error {

	public ReturnInNonReturnBlockException(Types returned) {
		super("Return statement found in a non-return block. Found: " + returned);

	}
}
