//*****************************************************************
// Tratamiento de funciones semánticas
//
// Fichero:    SemanticFunctions.java
// Fecha:      13/05/2024
// Versión:    v1.0
// Asignatura: Procesadores de Lenguajes, curso 2021-2022
//*****************************************************************

package lib.tools;

import java.util.*;
// import traductor.Token;
// import lib.attributes.*;
import lib.symbolTable.*;
import lib.symbolTable.Symbol.ParameterClass;
import lib.symbolTable.exceptions.*;
import traductor.Token;
import lib.errors.*;

import java.util.ArrayList;

import lib.symbolTable.Symbol.Types;
import lib.attributes.Attributes;
import lib.attributes.Return;
import lib.attributes.Return.ReturnPosibilities;

public class SemanticFunctions {

	public SemanticFunctions() {
	}

	public static boolean couldBeAsigned(Attributes a1, Attributes a2) {
		if (a1 == null || a2 == null || a1.type == Types.UNDEFINED || a2.type == Types.UNDEFINED)
			return false;
		if (a1.symbol.type == Types.PROCEDURE || a1.symbol.type == Types.FUNCTION)
			return false;
		if (a2.symbol != null && a2.symbol.type == Types.PROCEDURE)
			return false;
		if (a1.type == Types.ARRAY) {
			return a1.symbol.type == a2.type;
		}
		if (a2.type == Types.ARRAY) {
			return a2.symbol.type == a1.type;
		}
		if (a2.symbol.type == Types.FUNCTION)
			return ((SymbolFunction) a2.symbol).returnType == a1.type;

		if (a1.type != a2.type)
			return false;

		return true;
	}

	public static boolean areSameTypes(Symbol s1, Symbol s2) {
		if (s1 == null || s2 == null)
			return false;
		if (s1.type == Symbol.Types.UNDEFINED || s2.type == Symbol.Types.UNDEFINED)
			return false;

		if (s1.type == Symbol.Types.FUNCTION && s2.type == ((SymbolFunction) s1).returnType)
			return true;
		if (s2.type == Symbol.Types.FUNCTION && s1.type == ((SymbolFunction) s2).returnType)
			return true;

		if (s1.type != s2.type)
			return false;

		if (s1.type == Symbol.Types.ARRAY) {
			SymbolArray sa1 = (SymbolArray) s1;
			SymbolArray sa2 = (SymbolArray) s2;

			if (sa1.minInd != sa2.minInd)
				return false;
			if (sa1.maxInd != sa2.maxInd)
				return false;
			if (sa1.baseType != sa2.baseType)
				return false;

			return true;
		}
		return true;
	}

	public static String paramsTypes2String(ArrayList<Symbol> params) {
		if (params == null)
			return "()";

		String s = "(";
		for (Symbol p : params) {
			if (p == null) {
				s += "UNDEFINED, ";
				continue;
			}

			if (p.type == Types.ARRAY) {
				SymbolArray sa = (SymbolArray) p;
				s += "ARRAY(" + sa.minInd + ".." + sa.maxInd + " OF " + sa.baseType + "), ";
			} else {
				s += p.type + ", ";
			}
		}
		// quitar el último espacio
		if (s.length() > 0)
			s = s.substring(0, s.length() - 2) + ")";
		return s;
	}

	public static void variable_declaration(ArrayList<String> ids, Symbol symbol, SymbolTable st) {
		for (String id : ids) {
			if (symbol instanceof SymbolInt) {
				st.insertSymbol(new SymbolInt(id));
			} else if (symbol instanceof SymbolBool) {
				st.insertSymbol(new SymbolBool(id));
			} else if (symbol instanceof SymbolChar) {
				st.insertSymbol(new SymbolChar(id));
			} else if (symbol instanceof SymbolArray) {
				SymbolArray symbolArray = (SymbolArray) symbol;
				if (symbolArray.minInd > symbolArray.maxInd) {
					System.err.println("ERR-07");
					throw new ArrayDefinitionException("El índice mínimo es mayor que el máximo");
				}
				st.insertSymbol(new SymbolArray(id, symbolArray.minInd, symbolArray.maxInd, symbolArray.baseType));
			}
		}
	}

	public static Symbol type(boolean is_array, int minInd, int maxInd, Types type) {
		if (is_array) {
			return new SymbolArray("", minInd, maxInd, type);
		} else {
			switch (type) {
				case INT:
					return new SymbolInt("");
				case BOOL:
					return new SymbolBool("");
				case CHAR:
					return new SymbolChar("");
				default:
					return null;
			}
		}
	}

	public static ArrayList<Symbol> parameters(ArrayList<ArrayList<Symbol>> parList) {
		ArrayList<Symbol> parListResult = new ArrayList<Symbol>();
		for (ArrayList<Symbol> parListSameTypeItem : parList) {
			parListResult.addAll(parListSameTypeItem);
		}
		return parListResult;
	}

	public static ArrayList<Symbol> parameters_same_type(ArrayList<String> ids, Symbol symbol, boolean is_ref) {
		ArrayList<Symbol> parList = new ArrayList<Symbol>();
		ParameterClass parClass = is_ref ? ParameterClass.REF : ParameterClass.VAL;
		for (String id : ids) {
			if (symbol instanceof SymbolInt) {
				parList.add(new SymbolInt(id, parClass));
			} else if (symbol instanceof SymbolBool) {
				parList.add(new SymbolBool(id, parClass));
			} else if (symbol instanceof SymbolChar) {
				parList.add(new SymbolChar(id, parClass));
			} else if (symbol instanceof SymbolArray) {
				SymbolArray symbolArray = (SymbolArray) symbol;
				parList.add(
						new SymbolArray(id, symbolArray.minInd, symbolArray.maxInd, symbolArray.baseType, parClass));
			}
		}
		return parList;
	}

	public static Symbol proc_func_array_call(SymbolTable st, Token t, ArrayList<Attributes> parList) {

		boolean is_escalar_variable = true;
		if (st.containsSymbol(t.image.toLowerCase())) {
			Symbol s = st.getSymbol(t.image.toLowerCase());
			if (s instanceof SymbolFunction || s instanceof SymbolProcedure || s instanceof SymbolArray) {
				is_escalar_variable = false;
			}
		}

		if (is_escalar_variable) {
			Symbol symbol = st.getSymbol(t.image.toLowerCase());
			if (symbol == null) {
				System.err.println("ERR-09");
				throw new SymbolNotFoundException(t.image.toLowerCase());
			}
			// symbol.parClass = ParameterClass.REF;
			return symbol;
		}

		ArrayList<Symbol> parListSymbols = new ArrayList<Symbol>();
		for (Attributes a : parList) {
			parListSymbols.add(a.symbol);
		}

		Symbol symbol = st.getSymbol(t.image.toLowerCase());
		if (symbol == null) {
			System.err.println("ERR-10");
			throw new SymbolNotFoundException(t.image.toLowerCase());
		}

		if (symbol instanceof SymbolFunction) {
			SymbolFunction symbolFunction = (SymbolFunction) symbol;
			if (parListSymbols.size() != symbolFunction.parList.size()) {
				System.err.println("ERR-11");
				throw new ParametersMismatchException(symbolFunction.parList.size(), parListSymbols.size());
			}
			for (int i = 0; i < parListSymbols.size(); i++) {
				if (!SemanticFunctions.areSameTypes(symbolFunction.parList.get(i), parListSymbols.get(i))) {
					System.err.println("ERR-12");
					throw new ParametersMismatchException(SemanticFunctions.paramsTypes2String(symbolFunction.parList),
							SemanticFunctions.paramsTypes2String(parListSymbols));
				}
				if (symbolFunction.parList.get(i).parClass == Symbol.ParameterClass.REF
						&& !st.existsSymbol(parListSymbols.get(i).name)) {
					System.err.println("ERR-13");
					throw new ParametersMismatchException("REF " + symbolFunction.parList.get(i).type.toString(),
							"LITERAL " + symbolFunction.parList.get(i).type.toString());
				}
			}
			return st.getSymbol(t.image.toLowerCase());
		} else if (symbol instanceof SymbolProcedure) {
			SymbolProcedure symbolProcedure = (SymbolProcedure) symbol;

			if (parListSymbols.size() != symbolProcedure.parList.size()) {
				System.err.println("ERR-14");
				throw new ParametersMismatchException(symbolProcedure.parList.size(), parListSymbols.size());
			}
			for (int i = 0; i < parListSymbols.size(); i++) {
				if (!SemanticFunctions.areSameTypes(symbolProcedure.parList.get(i), parListSymbols.get(i))) {
					System.err.println("ERR-15");
					throw new ParametersMismatchException(SemanticFunctions.paramsTypes2String(symbolProcedure.parList),
							SemanticFunctions.paramsTypes2String(parListSymbols));
				}
				if (symbolProcedure.parList.get(i).parClass == Symbol.ParameterClass.REF
						&& !st.existsSymbol(parListSymbols.get(i).name)) {
					System.err.println("ERR-16");
					throw new ParametersMismatchException("REF " + symbolProcedure.parList.get(i).type.toString(),
							"LITERAL " + symbolProcedure.parList.get(i).type.toString());
				}
			}
			return st.getSymbol(t.image.toLowerCase());

		} else if (symbol instanceof SymbolArray) {
			SymbolArray symbolArray = (SymbolArray) symbol;
			if (parListSymbols.size() == 0) {
				// symbolArray.parClass = ParameterClass.REF;
				return symbolArray;

			} else if (parListSymbols.size() == 1) {
				if (parList.get(0).type != Types.INT) {
					System.err.println("ERR-17");
					throw new ParametersMismatchException("INT", parListSymbols.get(0).type.toString());
				}
				if (parListSymbols.get(0).type == Types.INT) {
					SymbolInt symbolInt = (SymbolInt) parListSymbols.get(0);
					if (symbolInt.constant) {
						if (symbolInt.value < symbolArray.minInd || symbolInt.value > symbolArray.maxInd) {
							System.err.println("ERR-18");
							throw new ArrayOutOfBoundsException(symbolArray.minInd, symbolArray.maxInd,
									symbolInt.value);
						}
					}
				}
			} else {
				System.err.println("ERR-19");
				throw new ParametersMismatchException(1, parListSymbols.size());
			}
			Symbol sst = st.getSymbol(t.image.toLowerCase());
			// sst.parClass = ParameterClass.REF;
			return sst;
		} else {
			System.err.println("ERR-20");
			throw new SymbolNotAFunctionException(t.image.toLowerCase());
		}
	}

	public static Return instructions_check_returned(Return willReturn, boolean has_returned_at_least_once,
			Types type_returned) {

		if (willReturn.posibility == ReturnPosibilities.RETURN && !has_returned_at_least_once) {
			System.err.println("ERR-21");
			throw new ReturnMissingInFunctionException();
		}

		ReturnPosibilities posibility = has_returned_at_least_once ? ReturnPosibilities.RETURN
				: ReturnPosibilities.NO_RETURN;

		return new Return(type_returned, posibility);
	}

	public static Return one_instruction_return(Return mayReturn, Symbol s) {
		Types returned = s == null ? Types.UNDEFINED : s.type;
		if (s != null && s.type == Types.FUNCTION) {
			returned = ((SymbolFunction) s).returnType;
		}
		if (s != null && mayReturn.typeReturned != returned) {
			if (mayReturn.typeReturned == Types.UNDEFINED) {
				System.err.println("ERR-22");
				throw new ReturnInNonReturnBlockException(returned);
			}
			System.err.println("ERR-23");
			throw new ReturnTypeMismatchException(mayReturn.typeReturned, returned);
		}
		if (s == null)
			return new Return(Types.UNDEFINED, ReturnPosibilities.RETURN);
		else
			return new Return(s.type, ReturnPosibilities.RETURN);

	}

	public static void instruction_check_boolean(Attributes e) {
		if (e.type != Types.BOOL) {
			System.err.println("ERR-24");
			throw new NonBooleanExpressionException(e.type);
		}
	}

	public static Return instruction_if_check_returned(Return mayReturn, ArrayList<Attributes> has_returned_list) {
		if (mayReturn.posibility == ReturnPosibilities.MAY_RETURN) {
			for (Attributes a : has_returned_list) {
				Return has_returned_item = a.returnAttr;
				if (has_returned_item.posibility == ReturnPosibilities.RETURN) {
					return new Return(has_returned_item.typeReturned, ReturnPosibilities.RETURN);
				}
			}
		}
		return new Return(Types.UNDEFINED, ReturnPosibilities.NO_RETURN);

	}

	public static void instruction_asign_check_array_assign(Attributes s) {
		if (s.symbol instanceof SymbolArray) {
			System.err.println("ERR-25");
			throw new ArrayAssignmentException(s.symbol.name);
		}
	}

	public static void instruction_asign_check_expression(Attributes expression, Attributes asigned) {
		if (expression != null) {
			// ver que asigned no sea PROCEDIMIENTO/FUNCION
			if (!SemanticFunctions.couldBeAsigned(asigned, expression)) {
				if (asigned.symbol.type == Types.FUNCTION || asigned.symbol.type == Types.PROCEDURE) {
					System.err.println("ERR-26");
					throw new FunctionAssignmentException(asigned.symbol.name, asigned.symbol.type);
				}
				System.err.println("ERR-27");
				throw new MismatchTypesExpressionException(asigned.type, expression.type);
			}
		} else {
			// expreesion == null : debe ser una llamada a procedimiento
			// si asigned no es procedimiento -> exception
			if (asigned.symbol.type != Types.PROCEDURE) {
				System.err.println("ERR-28");
				throw new FunctionAssignmentException(asigned.symbol.name, asigned.symbol.type);
			}
		}
	}

	public static void readable(SymbolTable st, Symbol s) {

		// Mirar si es por REF
		if (st.containsSymbol(s.name)) {
			Symbol _s = st.getSymbol(s.name);
			if (_s instanceof SymbolArray && !(s instanceof SymbolArray)) {
				SymbolArray _sa = (SymbolArray) _s;
				switch (_sa.baseType) {
					case INT:
						_s = new SymbolInt("", ParameterClass.NONE);
						break;
					case CHAR:
						_s = new SymbolChar("", ParameterClass.NONE);
						break;
					case BOOL:
						_s = new SymbolBool("", ParameterClass.NONE);
						break;
					default:
						break;
				}
			}
			if (!(_s instanceof SymbolInt || _s instanceof SymbolChar)) {
				System.err.println("ERR-29");
				throw new ReadableException(s);
			}
		} else {
			System.err.println("ERR-30");
			throw new ReadableException();
		}
	}

	public static Attributes i2c(Attributes s) {
		if (s.type == Types.INT) {
			s.type = Types.CHAR;
			s.symbol = new SymbolChar("");
			return s;
		} else {
			System.err.println("ERR-31");
			throw new MismatchTypesExpressionException(s.type, Types.INT);
		}
	}

	public static Attributes c2i(Attributes s) {
		if (s.type == Types.CHAR) {
			s.type = Types.INT;
			s.symbol = new SymbolInt("");
			return s;
		} else {
			System.err.println("ERR-32");
			throw new MismatchTypesExpressionException(s.type, Types.CHAR);
		}
	}
}
