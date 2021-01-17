import java.util.ArrayList;

public class CodigoIntermedio{

  private ArrayList<Cuadrupla> codigo;

  // Constructor
  public CodigoIntermedio(){
    codigo = new ArrayList<Cuadrupla>();
  }

  // método que agrega código
  public void generarCodigo(Cuadrupla cuadrupla){
    codigo.add(cuadrupla);
  }

  // get para mostrar el código generado
  public String getCodigo(){
    String texto="";
    for (Cuadrupla cuad : codigo) {
      texto+=cuad.toString();
    }
  }
}