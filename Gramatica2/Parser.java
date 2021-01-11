/*
Autores:      Cabrera Gaytán Jazmín Andrea
              Camacho Morales Gerardo Iván
              Nicolas Marin Brian Geovanny
              Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.0
Fecha:        10 de enero de 2021
Nombre:       Parser.java
*/

import java.util.ArrayList;
import java.io.IOException;

public class Parser{
  /*Utilizamos el orden definido en el analizador léxico para reutilizar
  el analizador léxico*/
  //TIPOS DE DATOS
  private static final int INT=1;
  private static final int FLOAT=2;
  private static final int CHAR=3;
  private static final int DOUBLE=4;
  private static final int VOID=5;
  //PALABRAS RESERVADAS
  private static final int FUNC=6;
  private static final int IF=7;
  private static final int ELSE=8;
  private static final int WHILE=9;
  private static final int DO=10;
  private static final int BREAK=11;
  private static final int SWITCH=12;
  private static final int CASE=13;
  private static final int DEFAULT=14;
  //OPERADORES
  private static final int ASIGNACION=15;
  private static final int OR=16;
  private static final int AND=17;
  private static final int IGUAL=18;
  private static final int DESIGUAL=19;
  private static final int LT=20;
  private static final int LET=21;
  private static final int MT=22;
  private static final int MET=23;
  private static final int SUMA=24;
  private static final int RESTA=25;
  private static final int MULTI=26;
  private static final int DIVISION=27;
  private static final int MODULO=28;
  private static final int NEGACION=29;
  //PUNTUACION
  private static final int L1=30;
  private static final int L2=31;
  private static final int C1=32;
  private static final int C2=33;
  private static final int P1=34;
  private static final int P2=35;
  private static final int DOSPUNTOS=36;
  private static final int PUNTOYCOMA=37;
  private static final int COMA=38;
  //LITERALES
  private static final int IDENTIFIER=39;
  private static final int STRING_LIT=40;
  private static final int TRUE=41;
  private static final int FALSE=42;
  private static final int INT_LIT=43;
  private static final int FLOAT_LIT=44;
  // otros atributos necesarios
  private Lexer analizadorLexico;
  private int tokenActual;
  private int dir;
  private ArrayList<Symbol> tablaSimbolos;
  private ArrayList<Type> tablaTipos;

  //CONSTRUCTOR 
  public Parser(Lexer lexer) throws IOException,Exception{
    // asignamos el analizador léxico
    analizadorLexico = lexer;
    dir = 0;
    tokenActual = analizadorLexico.yylex();
    // llenamos la tabla de tipos
    tablaSimbolos = new ArrayList<Symbol>();
    tablaTipos = new ArrayList<Type>();

    tablaTipos.add(new Type(0, "int", 2, -1, -1));
    tablaTipos.add(new Type(1, "float", 4, -1, -1));
    tablaTipos.add(new Type(2, "char", 1, -1, -1));
    tablaTipos.add(new Type(3, "double", 8, -1, -1));
    //tablaTipos.add(new Type(1, "void", 4, -1, -1));
  }

  // método que inicia
  public void parse() throws IOException,Exception{
    printTT();
    printTS();
  }


  /*

  Aquí va el parser recursivo

  */

  // MÉTODOS DE AYUDA

  // Método para avanzar de token
  public void eat(int i) throws IOException,Exception{
    if(tokenActual==i){
      tokenActual = analizadorLexico.yylex();
      // se revisa que el nuevo token sea un número correcto
      if(tokenActual==-1){
        throw new Exception("Error léxico, línea "+analizadorLexico.getYyline());
      }
    }else{
      error("Error de sintaxis, se esperaba "+i+" se encontró "+tokenActual);
    }
  }

  // Método que muestra la existencia de un error
  private void error(String mensaje) throws Exception{
    throw new Exception(mensaje+", línea "+analizadorLexico.getYyline()+"\n"+analizadorLexico.linea);
  }

  // Método que nos regresa el tamaño de un tipo de dato
  private int getTam(int id){
    for(Type t: tablaTipos){
      if(id == t.id){
        return t.tam;
      }
    }
    return -1;
  }

  // Método que nos regresa el tipo de dato de un identificador
  private int getTipo(String id){
    for(Symbol s: tablaSimbolos){
      if(s.id.equals(id)){
        return s.type;
      }
    }
    return -1;
  }

  // Método que revisa si una variable ya se encuentra declarada
  private boolean buscar(String id){
    for(Symbol s: tablaSimbolos){
      if(s.id.equals(id)){
        return true;
      }
    }
    return false;
  }

  // MÉTODOS PARA DEBUGGEAR
  
  // imprime la tabla de tipos
  private void printTT(){
    System.out.println("Tabla de tipos");
    System.out.println("id\ttipo\ttam\t#e\tTipoBase");
    for(Type t: tablaTipos){
      System.out.println(t.id+"\t"+t.type+"\t"+t.tam+"\t"+t.elem+"\t"+t.tipoBase);
    }
  }

  // imprime la tabla de simbolos
  private void printTS(){
    int i=0;
    System.out.println("Tabla de simbolos");
    System.out.println("pos\tid\ttipo\tdir\tvar");
    for(Symbol s: tablaSimbolos){
      System.out.println(i+"\t"+s.id+"\t"+s.type+"\t"+s.dir+"\t"+s.var);
      i++;
    }
  }

}