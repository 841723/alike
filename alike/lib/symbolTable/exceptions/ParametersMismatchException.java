/*********************************************************************************
 * Excepción utilizada al emplear un número incorrecto de parámetros 
 * en una función o procedimiento, o llamar con tipos de parámetros incorrectos 
 *
 * Fichero:    ParametersMismatchException.java
 * Fecha:      31/03/2024
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2023-2024
 **********************************************************************************/

package lib.symbolTable.exceptions;

import java.util.ArrayList;

import lib.symbolTable.Symbol;

public class ParametersMismatchException extends Error {

	public ParametersMismatchException(int expected, int received) {
		super("Expected " + expected + " parameters, but received " + received);
	}

	public ParametersMismatchException(String expected, String received) {
		super("Expected " + expected + " parameters, but received " + received);
	}

}
