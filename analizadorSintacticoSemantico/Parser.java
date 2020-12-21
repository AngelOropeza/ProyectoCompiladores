import java.util.ArrayList;
import java.io.IOException;

public class Parser{
  /*Utilizamos el orden definido en el analizador léxico para reutilizar
  el analizador léxico*/
  // constantes
  private static final int PACKAGE=1;
  private static final int VAR=2;
  private static final int CONST=3;
  private static final int UINT8=4;
  private static final int UINT16=5;
  private static final int INT8=6;
  private static final int INT16=7;
  private static final int INT32=8;
  private static final int FLOAT32=9;
  private static final int FLOAT64=10;
  private static final int COMPLEX64=11;
  private static final int BYTE=12;
  private static final int STRING=13;
  private static final int BOOL=14;
  private static final int STRUCT=15;
  private static final int FUNC=16;
  private static final int DEFER=17;
  private static final int IF=18;
  private static final int ELSE=19;
  private static final int SWITCH=20;
  private static final int CASE=21;
  private static final int DEFAULT=22;
  private static final int FOR=23;
  private static final int RETURN=24;
  private static final int BREAK=25;
  private static final int CONTINUE=26;
  private static final int OR=27;
  private static final int AND=28;
  private static final int IGUAL=29;
  private static final int DESIGUAL=30;
  private static final int MT=31;
  private static final int MET=32;
  private static final int LT=33;
  private static final int LET=34;
  private static final int SUMA=35;
  private static final int RESTA=36;
  private static final int MULTI=37;
  private static final int DIVISION=38;
  private static final int MODULO=39;
  private static final int NEGACION=40;
  private static final int DIR=41;
  private static final int INCREMENTO=42;
  private static final int DECREMENTO=43;
  private static final int ASIGNACION=44;
  private static final int P1=45;
  private static final int P2=46;
  private static final int C1=47;
  private static final int C2=48;
  private static final int L1=49;
  private static final int L2=50;
  private static final int PUNTO=51;
  private static final int DOSPUNTOS=52;
  private static final int PUNTOYCOMA=53;
  private static final int COMA=54;
  private static final int IDENTIFIER=55;
  private static final int INT_LIT=56;
  private static final int FLOAT_LIT=57;
  private static final int IMAGINARY_LIT=58;
  private static final int STRING_LIT=59;
  private static final int BOOL_LIT=60;
  private static final int INT=61;
  private static final int FLOAT=62;
  // otros atributos necesarios
  private Lexer analizadorLexico;
  private int tokenActual;
  private int dir;
  private ArrayList<Symbol> tablaSimbolos;
  private ArrayList<Type> tablaTipos;

  public Parser(Lexer lexer) throws IOException,Exception{
    // asignamos el analizador léxico
    analizadorLexico = lexer;
    dir = 0;
    tokenActual = analizadorLexico.yylex();
    // llenamos la tabla de tipos
    tablaSimbolos = new ArrayList<Symbol>();
    tablaTipos = new ArrayList<Type>();

    tablaTipos.add(new Type(0, "int", 4, -1, -1));
    tablaTipos.add(new Type(1, "float", 4, -1, -1));
  }

  // método que inicia todo
  public void parse() throws IOException,Exception{
    P();
  }

  /*
  Para esta parte comenzaremos con las reglas gramaticales
  dadas por el profesor

  P → D S
  D → TL ; D | ε
  T → BC
  C → [num]C | ε
  B → int | float
  L → id L’
  L’ → , id L’ | ε
  S → id = E; S’
  S’ → S | ε
  E → U E’
  E’ → + U E’ | - U E’ | ε
  U  → F U’
  U’  → * F U’ | / F U’ | % F U’
  F → (E) | id | num

  */
  // P → D S
  private void P() throws IOException,Exception{
    D();
    SP();
  }
  // D → TL ; D | ε
  private void D() throws IOException,Exception{
    if(tokenActual==INT || tokenActual==FLOAT){
      // L.tipo = T.tipo
      int tipoT = T();
      L(tipoT);
      eat(PUNTOYCOMA);
      D();
    }
  }
  // T → BC
  private int T() throws IOException,Exception{
    // C.base = B.tipo
    int tipoB = B();
    int tipoC = C(tipoB);
    // T.tipo = C.tipo
    return tipoC;
  }
  // C → [num]C | ε
  private int C(int base) throws IOException,Exception{
    // primera producción
    if(tokenActual==C1){
      eat(C1);
      // eat(NUM);
      int valor = Integer.parseInt(analizadorLexico.yytext());
      eat(INT_LIT);
      eat(C2);
      // C1.base = C.base
      int tipoc = C(base);
      // C.tipo=TT.insertar(“array”,num.val,C1.tipo)
      int tam = valor * getTam(tipoc);
      //   si getTam regresa un -1, tam es un número negativo por lo tanto
      if(tam < 0){
        printTT();
        error("Fallo en la generación de arreglos, id "+tipoc);
      }
      int id = tablaTipos.size();
      tablaTipos.add(new Type(id, "array", tam, valor, tipoc));
      return id;
    // segunda producción, vacía
    }else{
      // C.tipo = C.base
      return base;
    }
  }
  // B → int | float
  private int B() throws IOException,Exception{
    if(tokenActual==INT){
      eat(INT);
      // B.tipo = TT.get_id(int)
      return 0;
    }else if(tokenActual==FLOAT){
      eat(FLOAT);
      // B.tipo = TT.get_id(float)
      return 1;
    }else{
      error("Error sintáctico, se esperaba \"float\" o \"int\"");
      return -1;
    }
  }
  // L → id L’
  private void L(int tipo) throws IOException,Exception{
    if(tokenActual==IDENTIFIER){
      /*
      si TS.buscar(id)==falso entonces:
        id.tipo = L.tipo
        TS.insertar(id,L.tipo,dir,”array”)
        dir += TT.get_Tam(id.tipo)
      else:
        error(“Variable declarada anteriormente”)
      */
      if(!buscar(analizadorLexico.yytext())){
        // id.tipo = L.tipo
        tablaSimbolos.add(new Symbol(analizadorLexico.yytext(),dir,tipo,0,null));
        int tam = getTam(tipo);
        if(tam == -1){
          printTT();
          error("Error al generar una variable con un tipo arreglo, id "+tipo);
        }else{
          dir += tam;
        }
      }else{
        error("Variable ya definida ");
      }
      eat(IDENTIFIER);
      // L’.tipo = L.tipo
      LP(tipo);
    }else{
      error("Error sintáctico, se esperaba un id");
    }
  }
  // L’ → , id L’ | ε
  private void LP(int tipo) throws IOException,Exception{
    if(tokenActual==COMA){
      eat(COMA);
      /*
      si TS.buscar(id)==falso entonces:
        id.tipo = L.tipo
        TS.insertar(id,L.tipo,dir,”array”)
        dir += TT.get_Tam(id.tipo)
      else:
        error(“Variable declarada anteriormente”)
      */
      if (!buscar(analizadorLexico.yytext())){
        // id.tipo = L.tipo
        tablaSimbolos.add(new Symbol(analizadorLexico.yytext(),dir,tipo,0,null));
        int tam = getTam(tipo);
        if(tam == -1){
          printTT();
          error("Error al generar una variable con un tipo arreglo, id "+tipo);
        }else{
          dir += tam;
        }
      }else{
        error("Variable ya definida ");
      }
      eat(IDENTIFIER);
      LP(tipo);
    }
  }
  // S → id = E; S’
  private void S() throws IOException,Exception{
    if(tokenActual==IDENTIFIER){
      int tipoId = getTipo(analizadorLexico.yytext());
      /*
      si id no declarado:
        error(variable no declarada)
      */
      if(tipoId==-1){
        error("Variable no declarada");
      }
      eat(IDENTIFIER);
      eat(ASIGNACION);
      int tipoE = E();
      /*
      si id.tipo != E.tipo:
        error(tipos distintos)
      */
      if(tipoId != tipoE){
        error("Tipos de datos incompatibles");
      }
      eat(PUNTOYCOMA);
      SP();
    }else{
      error("Error sintactico, se esperaba un id");
    }
  }
  // S’ → S | ε
  private void SP() throws IOException,Exception{
    if(tokenActual==IDENTIFIER){
      S();
    }
  }
  // E → U E’
  private int E() throws IOException,Exception{
    int tipoU = U();
    int tipoEP = EP();
    /*
    si E’.tipo == -1:
      E’.tipo = U.tipo
    */
    if(tipoEP==-1){
      tipoEP = tipoU;
    }
    /*
    si E’.tipo == U.tipo:
      E.tipo = U.tipo
    else
     error(tipos diferentes)
    */
    if(tipoEP == tipoU){
      return tipoU;
    }else{
      error("Tipos de datos incompatibles");
    }
    return -1;
  }
  // E’ → + U E’ | - U E’ | ε
  private int EP() throws IOException,Exception{
    if(tokenActual==SUMA){
      eat(SUMA);
      int tipoU = U();
      int tipoEP = EP();
      /*
      si E’1.tipo == -1:
        E’1.tipo = U.tipo
      */
      if(tipoEP==-1){
        tipoEP = tipoU;
      }
      /*
      si E’1.tipo == U.tipo:
        E’.tipo = U.tipo
      else
       error(tipos diferentes)
      */
      if(tipoEP == tipoU){
        return tipoU;
      }else{
        error("Tipos de datos incompatibles");
      }
    }else if(tokenActual==RESTA){
      eat(RESTA);
      int tipoU = U();
      int tipoEP = EP();
      /*
      si E’1.tipo == -1:
        E’1.tipo = U.tipo
      */
      if(tipoEP==-1){
        tipoEP = tipoU;
      }
      /*
      si E’1.tipo == U.tipo:
        E’.tipo = U.tipo
      else
       error(tipos diferentes)
      */
      if(tipoEP == tipoU){
        return tipoU;
      }else{
        error("Tipos de datos incompatibles");
      }
    }else{
      return -1;
    }
    return -1;
  }
  // U  → F U’
  private int U() throws IOException,Exception{
    int tipoF = F();
    int tipoUP = UP();
    /*
    si U'.tipo == -1:
      U'.tipo = F.tipo
    */
    if(tipoUP==-1){
      tipoUP = tipoF;
    }
    /*
    si U’1.tipo == F.tipo:
      U.tipo = F.tipo
    else
     error(tipos diferentes)
    */
    if(tipoUP == tipoF){
      return tipoF;
    }else{
      error("Tipos de datos no compatibles");
      return -1;
    }
  }
  // U’  → * F U’ | / F U’ | % F U’ | ε
  private int UP() throws IOException,Exception{
    if(tokenActual==MULTI){
      eat(MULTI);
      int tipoF = F();
      int tipoUP = UP();
      /*
      si U’1.tipo == -1:
        U’1.tipo = F.tipo
      */
      if(tipoUP==-1){
        tipoUP = tipoF;
      }
      /*
      si U’1.tipo == F.tipo:
        U’.tipo = F.tipo
      sino
        error(tipos diferentes)
      */
      if(tipoUP == tipoF){
        return tipoF;
      }else{
        error("Tipos de datos no compatibles");
      }
      F();
      UP();
    }else if(tokenActual==DIVISION){
      eat(DIVISION);
      int tipoF = F();
      int tipoUP = UP();
      /*
      si U’1.tipo == -1:
        U’1.tipo = F.tipo
      */
      if(tipoUP==-1){
        tipoUP = tipoF;
      }
      /*
      si U’1.tipo == F.tipo:
        U’.tipo = F.tipo
      sino
        error(tipos diferentes)
      */
      if(tipoUP == tipoF){
        return tipoF;
      }else{
        error("Tipos de datos no compatibles");
      }
      F();
      UP();
    }else if(tokenActual==MODULO){
      eat(MODULO);
      int tipoF = F();
      int tipoUP = UP();
      /*
      si U’1.tipo == -1:
        U’1.tipo = F.tipo
      */
      if(tipoUP==-1){
        tipoUP = tipoF;
      }
      /*
      si U’1.tipo == F.tipo:
        U’.tipo = F.tipo
      sino
        error(tipos diferentes)
      */
      if(tipoUP == tipoF){
        return tipoF;
      }else{
        error("Tipos de datos no compatibles");
      }
    }else{
      // U’.tipo = -1
      return -1;
    }
    return -1;
  }
  // F → (E) | id | num
  private int F() throws IOException,Exception{
    switch(tokenActual){
      // F.tipo = E.tipo
      case P1:
        eat(P1);
        int tipoE = E();
        eat(P2);
        return tipoE;
      // F.tipo = id.tipo
      case IDENTIFIER:
        int tipoId = getTipo(analizadorLexico.yytext());
        if(tipoId==-1){
          error("Variable no declarada");
        }
        eat(IDENTIFIER);
        return tipoId;
      // F.tipo = num.tipo
      case INT_LIT:
        eat(INT_LIT);
        return 0;
      case FLOAT_LIT:
        eat(FLOAT_LIT);
        return 1;

      // error
      default:
        error("Tipo de operando no casteado");
        return -1;
    }
  }

  // Método para avanzar de token
  public void eat(int i) throws IOException,Exception{
    if(tokenActual==i){
      tokenActual = analizadorLexico.yylex();
      //System.out.println(tokenActual);
      //System.out.println(analizadorLexico.yytext());
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
    printTT();
    printTS();
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

  // Métodos para debuggear
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