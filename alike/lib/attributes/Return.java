package lib.attributes;

import lib.symbolTable.Symbol.Types;

public class Return {

    static public enum ReturnPosibilities {
        RETURN, NO_RETURN, MAY_RETURN
    }

    public Types typeReturned;
    public ReturnPosibilities posibility;

    public Return(Types _typeReturned, ReturnPosibilities _posibility) {
        typeReturned = _typeReturned;
        posibility = _posibility;
    }

    // public Return(Types _typeReturned) {
    //     this(_typeReturned, ReturnPosibilities.RETURN);
    // }

    // public Return(ReturnPosibilities _posibility) {
    //     this(Types.UNDEFINED, _posibility);
    // }

    // public Return() {
    //     this(Types.UNDEFINED, ReturnPosibilities.NO_RETURN);
    // }

    public String toString() {
        return "(" + typeReturned + "," + posibility + ")";
    }

    public Return clone() {
        try {
            return (Return) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
