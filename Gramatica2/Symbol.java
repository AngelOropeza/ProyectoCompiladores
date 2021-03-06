/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.0
Fecha:        10 de enero de 2021
Nombre:       lexer.jflex
*/
import java.util.ArrayList;

public class Symbol{
  String id;
  int type;
  int dir;
  int var;
  ArrayList<Integer> args;

  public Symbol(String id, int dir, int type, int var, ArrayList<Integer> args){
    this.id=id;
    this.type=type;
    this.dir=dir;
    this.var=var;
    this.args=args;
  }
}