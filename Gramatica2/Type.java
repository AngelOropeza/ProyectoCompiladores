/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.0
Fecha:        10 de enero de 2021
Nombre:       lexer.jflex
*/
public class Type{
  int id;
  String type;
  int tam;
  int elem;
  int tipoBase;

  public Type(int id, String type, int tam, int elem, int tipoBase){
    this.id = id;
    this.type = type;
    this.tam = tam;
    this.elem = elem;
    this.tipoBase = tipoBase;
  }
}