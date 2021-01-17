/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1
Fecha:        20 de diciembre de 2020
Nombre:       lexico.jflex
*/
import java.io.*;

public class Main{
  public static void main(String[] args) {
    try{
      File f = new File(args[0]);
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      Lexer lexer = new Lexer(br);
      Parser parser = new Parser(lexer);
      parser.parse();
      br.close();
    }catch(IOException e){
      System.out.println("Error al abrir el archivo");
    }catch(Exception e){
      System.out.println(e.toString());
    }
  }
}