public class Semantico{

  public static int numTemporal=-1;

  // método para generar una nueva temporal
  public static STring nuevaTemporal(){
    return "t"+numTemporal++;
  }

  // método que nos indica si dos tipos son equivalentes
  public static boolean equivalentes(int tipo1, int tipo2){
    if(tipo1 == tipo2) return true;
    if(tipo1 == 0 && tipo2 == 1) return true;
    if(tipo1 == 1 && tipo2 == 0) return true;
    return false;
  }

  // Método para ampliar un tipo de dato 
  public static String ampliar(String d, int menor, int mayor, CodigoIntermedio cod){
    if(menor == mayor) return d;
    String temp;
    if(menor==0 && mayor==1){
      temp = nuevaTemporal();
      cod.generarCodigo(new Cuadrupla("=","(float)"+d,"",temp)); // temp = (float)d
      return temp;
    }
    return null;
  }

  // Método para reducir un tipo de dato
  public static String reducir(String d, int mayor, int menor, CodigoIntermedio cod){
    if(menor == mayor) return d;
    String temp;
    if(menor==0 && mayor==1){
      temp = nuevaTemporal();
      cod.generarCodigo(new Cuadrupla("=","(int)"+d,"",temp)); // temp = (int)d
      return temp;
    }
    return null;
  }
}