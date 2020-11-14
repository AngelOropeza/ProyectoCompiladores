/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo
Versión:      1
Fecha:        13 de noviembre de 2020
Nombre:       Implementación para llamar al analizador léxico
*/
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

      //Creamos una variable entera que servirá para obtener el número del
      //token generado
      int aux;
      //Un ciclo infinito que llamará continuamente al método yylex() del
      //analizador léxico
      //Se detiene cuando encuentra un cero (solo se retorna o al EOF)
      while(true){
        aux = analizadorJFlex.yylex();
        if(aux==0){
          break;
        }
        System.out.println(aux);
      }
    //Si ocurre una excepción, se muestra la información relacionada a esta
    }catch (Exception e){
      System.out.println(e.toString());
    }
  }
}