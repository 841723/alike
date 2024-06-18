
package lib.attributes;

import lib.symbolTable.*;

import java.util.ArrayList;
import lib.tools.codeGeneration.CodeBlock;

import lib.tools.codeGeneration.PCodeInstruction;

public class Attributes implements Cloneable {

    public Return returnAttr;
    public Symbol symbol;

    public Symbol.Types type;
    public Symbol.Types baseType;
    public Symbol.ParameterClass parClass;

    public int valInt;
    public boolean valBool;
    public char valChar;
    public String valString;

    public CodeBlock code;
    public String label;
    public ArrayList<Symbol> parList;
    public ArrayList<Attributes> lExps;
    public boolean isVar;
    public boolean isCompVector;
    public boolean isConst;
    public int arraySize;
    public int nivel;
    public int dir;

    public int beginLine;
    public int beginColumn;

    public Attributes() {
        this.code = new CodeBlock();
        this.lExps = new ArrayList<>();
        this.parList = new ArrayList<>();
        this.isConst = false;
    }

    public Attributes(Symbol s) {
        this();
        this.symbol = s;
        this.type = s.type;
    }

    public Attributes(Object value) {
        this();
        this.isConst = true;
        int value2add = 0;

        if (value instanceof Integer) {
            this.valInt = (int) value;
            value2add = this.valInt;
            this.type = Symbol.Types.INT;
            this.symbol = new SymbolInt("", (int) value);
            this.symbol.constant = true;
        } else if (value instanceof Boolean) {
            this.valBool = (boolean) value;
            value2add = this.valBool ? 1 : 0;
            this.type = Symbol.Types.BOOL;
            this.symbol = new SymbolBool("", (boolean) value);
            this.symbol.constant = true;
        } else if (value instanceof Character) {
            this.valChar = (char) value;
            value2add = (int) this.valChar;
            this.type = Symbol.Types.CHAR;
            this.symbol = new SymbolChar("", (char) value);
            this.symbol.constant = true;
        } /*
           * else if (value instanceof String) {
           * this.valString = (String) value;
           * this.type = Symbol.Types.STRING;
           * }
           */

        this.code.addInst(PCodeInstruction.OpCode.STC, value2add);
    }

    public Object getValue() {
        if (this.type == Symbol.Types.INT) {
            return this.valInt;
        } else if (this.type == Symbol.Types.BOOL) {
            return this.valBool;
        } else if (this.type == Symbol.Types.CHAR) {
            return this.valChar;
        } else if (this.type == Symbol.Types.STRING) {
            return this.valString;
        }
        return null;
    }

    public Attributes clone() {
        try {
            return (Attributes) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return "code: \"" + code + "\"\n" +
        // "Type: " + type + "\n" +
        // "BaseType: " + baseType + "\n" +
        // "ParClass: " + parClass + "\n" +
        // "ValInt: " + valInt + "\n" +
        // "ValBool: " + valBool + "\n" +
        // "ValChar: " + valChar + "\n" +
        // "ValString: " + valString + "\n" +
        // "Label: " + label + "\n" +
        // "IsVar: " + isVar + "\n" +
        // "IsCompVector: " + isCompVector + "\n" +
        // "IsConst: " + isConst + "\n" +
        // "ArraySize: " + arraySize + "\n" +
        // "Nivel: " + nivel + "\n" +
        // "Dir: " + dir + "\n" +
        // "BeginLine: " + beginLine + "\n" +
        // "BeginColumn: " + beginColumn + "\n";
                "";
    }
}
