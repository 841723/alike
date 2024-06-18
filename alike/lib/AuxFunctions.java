//*****************************************************************
// File:   AuxFunctions.java
// Author: Diego Roldán (841723) && Abel Romeo (846088)
// Date:   abril 2024
// Coms:   Clase AuxFunctions para funciones auxiliares
//*****************************************************************
package lib;

import lib.symbolTable.SymbolTable;

public class AuxFunctions {

    static boolean verbose = false;
    static boolean verboseSymbolTable = false;
    public static String line = "=================================================================";

    public static void printIfVerbose(String s) {
        if (verbose) {
            System.out.println(s);
        }
    }

    public static void printExpression(String s) {
        if (verbose) {
            System.out.println(s);
        }
    }

    public static void printInicioTraduccion(String filename) {

        int total_width = 50;
        int pre_spaces = (total_width - filename.length()) / 2;
        int post_spaces = total_width - filename.length() - pre_spaces;

        String line2 = String.format("%" + pre_spaces + "s%s%" + post_spaces + "s", "", filename, "");

        System.out.println(line);
        System.out.println("               Starting translation             ");
        System.out.println(line2);
        System.out.println();
    }

    public static void printExitoTraduccion(String filename) {
        String basename = filename.substring(0, filename.length() - 3);
        System.out.println("OK: file " + basename + ".pcode has been created successfully");
        System.out.println(line);
        System.out.println();
        System.out.println();
        System.out.println("to run the program:");
        System.out.println("   $ <path>/ensamblador " + basename);
        System.out.println("   $ <path>/maquinap " + basename);
    }

    public static void printSymbolTable(SymbolTable st) {
        if (verboseSymbolTable) {
            System.out.println(st);
        }
    }

    public static void printErrorHeader() {
    }
}
