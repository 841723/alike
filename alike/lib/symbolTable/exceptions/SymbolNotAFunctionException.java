/*********************************************************************************
 * Excepción utilizada al intentar utilizar un símbolo que no es
 *  función/procedimiento como si lo fuera 
 * 
 *
 * Fichero:    SymbolNotAFunctionException.java
 * Fecha:      31/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class SymbolNotAFunctionException extends Error {

	public SymbolNotAFunctionException(String symbolName) {
		super("Symbol " + symbolName + " is not a function or procedure");
	}

}
