/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1
Fecha:        20 de diciembre de 2020
Nombre:       lexico.jflex
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