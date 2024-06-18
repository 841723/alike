package lib.tools;

import java.util.ArrayList;
import lib.attributes.Attributes;
import lib.symbolTable.Symbol.ParameterClass;
import lib.symbolTable.Symbol.Types;

import traductor.Token;

import lib.symbolTable.Symbol;
import lib.symbolTable.SymbolArray;
import lib.symbolTable.SymbolBool;
import lib.symbolTable.SymbolChar;
import lib.symbolTable.SymbolFunction;
import lib.symbolTable.SymbolInt;
import lib.symbolTable.SymbolProcedure;
import lib.symbolTable.SymbolTable;
import lib.symbolTable.exceptions.MismatchTypesExpressionException;
import lib.tools.codeGeneration.CGUtils;
import lib.tools.codeGeneration.CodeBlock;
import lib.tools.codeGeneration.PCodeInstruction;

public class CodeGenerationFunctions {

    public static final boolean COMMENTS = false;
    private static SymbolTable st;

    public CodeGenerationFunctions(SymbolTable _st) {
        st = _st;
    }

    public static CodeBlock variable_declaration(ArrayList<String> ids) {
        CodeBlock cb = new CodeBlock();
        if (COMMENTS) {
            for (String s : ids) {
                Symbol sst = st.getSymbol(s);

                if (sst instanceof SymbolArray) {
                    SymbolArray sstArray = (SymbolArray) sst;
                    cb.addComment("Array variable \"" + s + "\", type " + sstArray.baseType + ", size: "
                            + (sstArray.maxInd - sstArray.minInd + 1) + ", level: " + sstArray.nivel + ", dir: ["
                            + sstArray.dir + ".." + (sstArray.dir + sstArray.maxInd - sstArray.minInd) + "]");
                } else {
                    cb.addComment("Simple variable \"" + s + "\", type: " + sst.type + ", level: " + sst.nivel
                            + ", dir: [" + sst.dir + "]");
                }
            }
        }
        return cb;
    }

    public static CodeBlock getVariableAddress(Symbol s, boolean doDRF) {
        CodeBlock cb = new CodeBlock();
        if (COMMENTS) {
            cb.addComment("Recuperando variable " + s.name);
        }
        Symbol sst = st.getSymbol(s.name);
        cb.addInst(PCodeInstruction.OpCode.SRF, st.level - sst.nivel, (int) sst.dir);
        if (doDRF) {
            cb.addInst(PCodeInstruction.OpCode.DRF);
        }
        return cb;
    }

    public static CodeBlock getVariableValue(Symbol s) {
        CodeBlock cb = new CodeBlock();
        if (COMMENTS) {
            cb.addComment("Recuperando variable " + s.name);
        }
        cb.addBlock(getVariableAddress(s, s.parClass == ParameterClass.REF));
        cb.addInst(PCodeInstruction.OpCode.DRF);
        return cb;
    }

    public static CodeBlock asignValue(Attributes s, Attributes e) {
        CodeBlock cb = new CodeBlock();
        Symbol ssymbol = s.symbol;
        if (e != null) {
            if (COMMENTS) {
                cb.addComment("Asignación de " + ssymbol.name + " con tipo " + s.type);
            }
            if (s.type != Types.ARRAY) { // recupera diferencia de nivel
                // recupera offset en nivel
                cb.addBlock(getVariableAddress(ssymbol, ssymbol.parClass == ParameterClass.REF));
                // recupera valora
            } else {
                cb.addBlock(s.code);
                cb.removeLastInst();
                s.type = ssymbol.type;
            }
            cb.addBlock(e.code);
            cb.addInst(PCodeInstruction.OpCode.ASG);
        } else {
            // llamada a funcion
            cb.addBlock(s.code);

            if (ssymbol instanceof SymbolProcedure) {
                SymbolProcedure sfst = (SymbolProcedure) ssymbol;
                cb.addOSFInst(st.getNumVarsToSave(sfst.nivel) + 3, st.level - sfst.nivel, sfst.label);
            }
        }
        return cb;
    }

    public static CodeBlock parameters(ArrayList<Symbol> parList) {
        CodeBlock cb = new CodeBlock();
        for (int i = parList.size() - 1; i >= 0; i--) {
            Symbol s = parList.get(i);
            if (COMMENTS) {
                cb.addComment("Parametro " + s.name);
            }
            // al reves para que si es por REF guarde la direccion
            // si es por VAL guarde el valor
            if (s.type == Types.ARRAY) {
                SymbolArray sstArray = (SymbolArray) s;
                if (s.parClass != ParameterClass.REF) {
                    // array por valor
                    for (int j = (sstArray.maxInd - sstArray.minInd); j >= 0; j--) {
                        if (COMMENTS) {
                            cb.addComment("Recuperando variable " + s.name);
                        }
                        cb.addInst(PCodeInstruction.OpCode.SRF, st.level - sstArray.nivel, ((int) sstArray.dir) + j);
                        cb.addInst(PCodeInstruction.OpCode.ASGI);
                    }
                } else {
                    // array por referencia
                    cb.addBlock(getVariableAddress(s, false));
                    cb.addInst(PCodeInstruction.OpCode.ASGI);
                }
            } else {
                cb.addBlock(getVariableAddress(s, false));
                cb.addInst(PCodeInstruction.OpCode.ASGI);
            }
        }
        return cb;
    }

    public static Attributes proc_func_array_call(Token t, ArrayList<Attributes> parList, Attributes attributes) {
        Symbol s = st.getSymbol(t.image.toLowerCase());
        boolean is_proc_func = s.type == Types.FUNCTION || s.type == Types.PROCEDURE;
        if (is_proc_func) {
            attributes.type = st.getSymbol(t.image.toLowerCase()).type;

            if (s instanceof SymbolFunction) {
                SymbolFunction sf = (SymbolFunction) s;
                for (int i = 0; i < sf.parList.size(); i++) {
                    Attributes attParRec = parList.get(i);
                    Symbol symParList = sf.parList.get(i);

                    if (symParList.parClass == ParameterClass.REF) {
                        if (symParList.type == Types.ARRAY) {
                            attributes.code.addBlock(attParRec.code.getFirstInst());
                            if (attParRec.symbol.parClass == ParameterClass.REF) {
                                attributes.code.addInst(PCodeInstruction.OpCode.DRF);
                            }
                        } else {
                            attributes.code.addBlock(attParRec.code);
                            attributes.code.removeLastInst();
                        }
                    } else {
                        attributes.code.addBlock(attParRec.code);
                    }
                }
                // int l = (st.level - s.nivel > 1 ? st.level - s.nivel - 1 : 0);
                attributes.code.addOSFInst(st.getNumVarsToSave(sf.nivel) + 3, st.level - s.nivel, sf.label);
            } else if (s instanceof SymbolProcedure) {
                SymbolProcedure sf = (SymbolProcedure) s;
                for (int i = 0; i < sf.parList.size(); i++) {
                    Attributes attParRec = parList.get(i);
                    Symbol symParList = sf.parList.get(i);

                    if (symParList.parClass == ParameterClass.REF) {
                        if (symParList.type == Types.ARRAY) {
                            attributes.code.addBlock(attParRec.code.getFirstInst());
                            if (attParRec.symbol.parClass == ParameterClass.REF) {
                                attributes.code.addInst(PCodeInstruction.OpCode.DRF);
                            }
                        } else {
                            attributes.code.addBlock(attParRec.code);
                            attributes.code.removeLastInst();
                        }
                    } else {
                        attributes.code.addBlock(attParRec.code);
                    }
                }
            }
            Symbol sst = st.getSymbol(t.image.toLowerCase());
            attributes.symbol = sst;
        } else if (s.type == Types.ARRAY) {
            SymbolArray sa = (SymbolArray) s;

            if (parList.size() == 0) {
                attributes.symbol = sa;
                for (int i = sa.minInd; i <= sa.maxInd; i++) {
                    if (sa.parClass == ParameterClass.REF) {
                        attributes.code.addBlock(getVariableAddress(sa, true));
                        attributes.code.addInst(PCodeInstruction.OpCode.STC, i - sa.minInd);
                        attributes.code.addInst(PCodeInstruction.OpCode.PLUS);
                        attributes.code.addInst(PCodeInstruction.OpCode.DRF);
                    } else {
                        attributes.code.addInst(PCodeInstruction.OpCode.SRF, st.level - sa.nivel,
                                (int) sa.dir + i - sa.minInd);
                        attributes.code.addInst(PCodeInstruction.OpCode.DRF);
                    }
                }
            } else {
                Attributes idx = parList.get(0);

                switch (sa.baseType) {
                    case INT:
                        attributes.symbol = new SymbolInt(s.name);
                        break;
                    case BOOL:
                        attributes.symbol = new SymbolBool(s.name);
                        break;
                    case CHAR:
                        attributes.symbol = new SymbolChar(s.name);
                        break;
                    default:
                        attributes.symbol = null;
                        break;
                }
                attributes.symbol.name = s.name;
                attributes.type = Types.ARRAY;
                attributes.code.addBlock(idx.code);
                attributes.code.addInst(PCodeInstruction.OpCode.STC, sa.minInd);
                attributes.code.addInst(PCodeInstruction.OpCode.SBT);

                attributes.code.addBlock(getVariableAddress(s, false));
                if (sa.parClass == ParameterClass.REF) {
                    attributes.code.addInst(PCodeInstruction.OpCode.DRF);
                }
                attributes.code.addInst(PCodeInstruction.OpCode.PLUS);
                attributes.code.addInst(PCodeInstruction.OpCode.DRF);
            }
        } else {
            // si es una variable -> escalar/array
            attributes.code = getVariableValue(st.getSymbol(t.image.toLowerCase()));
            attributes.type = st.getSymbol(t.image.toLowerCase()).type;
        }
        return attributes;
    }

    public static CodeBlock instruction_if(ArrayList<Attributes> expression_att_list,
            ArrayList<Attributes> has_returned_list) {
        Attributes expression_att_list_item, has_returned;
        CodeBlock cb = new CodeBlock();
        String labelfinal = "";
        boolean are_elsif = expression_att_list.size() > 2;
        if (are_elsif) {
            labelfinal = CGUtils.newLabel();
            String label = "";

            for (int i = 0; i < expression_att_list.size(); i++) {
                expression_att_list_item = expression_att_list.get(i);
                has_returned = has_returned_list.get(i);
                label = CGUtils.newLabel();
                if (expression_att_list_item != null) {
                    cb.addBlock(expression_att_list_item.code);
                    cb.addInst(PCodeInstruction.OpCode.JMF, label);
                }
                cb.addBlock(has_returned.code);
                if (are_elsif && i != expression_att_list.size() - 1) {
                    cb.addInst(PCodeInstruction.OpCode.JMP, labelfinal);
                }
                cb.addLabel(label);
            }
            cb.addLabel(labelfinal);
        } else {
            String label = CGUtils.newLabel(), label2 = CGUtils.newLabel();
            cb.addBlock(expression_att_list.get(0).code);
            cb.addInst(PCodeInstruction.OpCode.JMF, label);
            cb.addBlock(has_returned_list.get(0).code);
            cb.addInst(PCodeInstruction.OpCode.JMP, label2);

            cb.addLabel(label);
            if (has_returned_list.size() == 2) {
                cb.addBlock(has_returned_list.get(1).code);
            }
            cb.addLabel(label2);
        }
        return cb;
    }

    public static CodeBlock write(ArrayList<Attributes> parList) {
        CodeBlock cb = new CodeBlock();

        for (Attributes a : parList) {
            Symbol s = a.symbol;
            if (s != null && s.name == "") {
                s = null;
            }
            if (s == null || (s != null && (st.getSymbol(s.name).type == Types.ARRAY || st
                    .getSymbol(s.name).type == Types.FUNCTION))) {
                if (a.valString != null) {
                    for (int i = 0; i < a.valString.length(); i++) {
                        char caracter = a.valString.charAt(i);
                        if (caracter == '\n') {
                            if (COMMENTS) {
                                cb.addComment("Se escribe \'\\n\'");
                            }
                            cb.addInst(PCodeInstruction.OpCode.STC, 13);
                            cb.addInst(PCodeInstruction.OpCode.WRT, 0); // 1 ya que escribimos un char
                            cb.addInst(PCodeInstruction.OpCode.STC, 10);
                            cb.addInst(PCodeInstruction.OpCode.WRT, 0); // 1 ya que escribimos un char
                            continue;
                        }

                        int ascii = (int) caracter;
                        if (ascii > 127) {
                            try {
                                byte[] utf8 = new String(new char[] { caracter }).getBytes("UTF-8");
                                if (COMMENTS) {
                                    cb.addComment("se escribe \'" + caracter + "\'");
                                }
                                cb.addInst(PCodeInstruction.OpCode.STC, utf8[0]);
                                cb.addInst(PCodeInstruction.OpCode.WRT, 0); // 1 ya que escribimos un char
                                cb.addInst(PCodeInstruction.OpCode.STC, utf8[1]);
                                cb.addInst(PCodeInstruction.OpCode.WRT, 0); // 1 ya que escribimos un char

                            } catch (Exception e) {
                            }
                        } else {
                            if (COMMENTS) {
                                cb.addComment("se escribe \'" + caracter + "\'");
                            }
                            cb.addInst(PCodeInstruction.OpCode.STC, ascii);
                            cb.addInst(PCodeInstruction.OpCode.WRT, 0); // 1 ya que escribimos un char
                        }

                    }
                } else {
                    // si es expresion
                    if (COMMENTS) {
                        cb.addComment("Escribiendo expresion");
                    }
                    cb.addBlock(a.code);
                    int is_int_bool;
                    if (s == null)
                        is_int_bool = (a.type != Types.CHAR) ? 1 : 0;
                    else
                        is_int_bool = (s.type != Types.CHAR) ? 1 : 0;

                    cb.addInst(PCodeInstruction.OpCode.WRT, is_int_bool); // 1 ya que escribimos un entero
                }
            } else {
                // si es variable
                if (COMMENTS) {
                    cb.addComment("Escribiendo " + s.name);
                }
                cb.addBlock(getVariableValue(s));
                int is_int_bool = (s.type != Types.CHAR) ? 1 : 0;
                cb.addInst(PCodeInstruction.OpCode.WRT, is_int_bool); // 1 ya que escribimos un entero
            }
        }

        return cb;
    }

    public static CodeBlock readable(Attributes att_returned) {
        CodeBlock cb = new CodeBlock();
        cb.addBlock(getVariableAddress(att_returned.symbol, att_returned.symbol.parClass == ParameterClass.REF));
        return cb;
    }

    public static Attributes addOperand(Attributes att, PCodeInstruction.OpCode op) {
        Attributes attributes = new Attributes();
        attributes.code.addBlock(att.code);
        attributes.code.addInst(op);

        attributes.symbol = att.symbol;
        attributes.type = att.type;
        return attributes;
    }

    public static Attributes joinAttributes(Attributes att1, Attributes att2, Types requestedType, Types returnedType) {
        if (att2 == null) {
            if (att1.type == Types.FUNCTION) {
                att1.type = ((SymbolFunction) att1.symbol).returnType;
            }
            if (att1.type == Types.ARRAY) {
                att1.type = att1.symbol.type;
            }
            return att1;
        }

        if (att1.type == Types.FUNCTION) {
            att1.type = ((SymbolFunction) att1.symbol).returnType;
        }
        if (att2.type == Types.FUNCTION) {
            att2.type = ((SymbolFunction) att2.symbol).returnType;
        }

        if (att1.type == Types.ARRAY) {
            att1.type = att1.symbol.type;
        }
        if (att2.type == Types.ARRAY) {
            att2.type = att2.symbol.type;
        }

        if (att1.type != requestedType) {
            System.err.println("ERR-04");
            throw new MismatchTypesExpressionException(requestedType, att1.type);
        }
        if (att2.type != requestedType) {
            System.err.println("ERR-05");
            throw new MismatchTypesExpressionException(requestedType, att2.type);
        }
        if (att1.type != att2.type) {
            System.err.println("ERR-06");
            throw new MismatchTypesExpressionException(att1.type, att2.type);
        }

        Attributes att = new Attributes();

        att.code.addBlock(att1.code);
        att.code.addBlock(att2.code);

        switch (returnedType) {
            case INT:
                att.symbol = new SymbolInt("");
                break;
            case BOOL:
                att.symbol = new SymbolBool("");
                break;
            case CHAR:
                att.symbol = new SymbolChar("");
                break;
            default:
                att.symbol = null;
                break;
        }
        att.type = returnedType;

        return att;
    }
}
