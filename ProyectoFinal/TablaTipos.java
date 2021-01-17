/*
Autores:      Lázaro Martínez Abraham Josué
              Oropeza Castañeda Ángel Eduardo

Versión:      1.0
Fecha:        16 de enero de 2021
Nombre:       TablaTipos.java
*/

import java.util.ArrayList;

public class TablaTipos{

  private ArrayList<Tipo> tabla;

  // Constructor
  public TablaTipos(){
    tabla = new ArrayList<Tipo>();

    tabla.add(new Tipo(0, "int", 4, -1, -1));
    tabla.add(new Tipo(1, "float", 4, -1, -1));
    tabla.add(new Tipo(2, "char", 1, -1, -1));
    tabla.add(new Tipo(3, "double", 8, -1, -1));
  }

  // Método para obtener el tamaño de un tipo de dato
  public int getTam(int id){
    for (Tipo t : tabla ) {
      if(t.id==id){
        return t.tam;
      }
    }
    return -1;
  }

  // Método para obtener el tipo de dato base de un tipo de dato
  public int getTipoBase(int id){
    for (Tipo t : tabla ) {
      if(t.id==id){
        return t.tipoBase;
      }
    }
    return -1;
  }

  // Método para agregar un nuevo tipo de dato
  public void insertar(Tipo t){
    tabla.add(t);
  }

  // Obtiene el nombre del tipo
  public String getNombre(int id){
    for (Tipo t : tabla ) {
      if(t.id==id){
        return t.tipo;
      }
    }
    return "";
  }

  
}