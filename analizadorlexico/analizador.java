import java.io.BufferedReader;
import java.io.FileReader;
 
public class analizador{
 
  public static void main(String[] args) {
    try{
   
      // Asignación del nombre de archivo por defecto que usará la aplicación
      String archivo = "prueba1.txt";
     
      // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
      BufferedReader buffer = new BufferedReader(new FileReader(archivo));
      Lexer analizadorJFlex = new Lexer(buffer);
      int aux;
      while(true){
        aux = analizadorJFlex.yylex();
        if(aux==0){
          break;
        }
        System.out.println(aux);
      }

    }catch (Exception e){
      System.out.println(e.toString());
    }
  }
}