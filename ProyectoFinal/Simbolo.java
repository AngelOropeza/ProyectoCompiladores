/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.1
Fecha:        16 de enero de 2021
Nombre:       Simbolo.java
*/
import java.util.ArrayList;

public class Simbolo{
  String id;
  int tipo;
  int dir;
  String var;
  ArrayList<Integer> args;

  public Simbolo(String id, int dir, int tipo, String var, ArrayList<Integer> args){
    this.id=id;
    this.tipo=tipo;
    this.dir=dir;
    this.var=var;
    this.args=args;
  }
  
}