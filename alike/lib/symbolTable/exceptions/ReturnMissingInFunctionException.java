/*********************************************************************************
 * Excepción utilizada al no poner un return en una función
 * 
 *
 * Fichero:    ReturnMissingInFunctionException.java
 * Fecha:      23/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class ReturnMissingInFunctionException extends Error {

	public ReturnMissingInFunctionException() {
		super("Missing return in function");	
	}
}
