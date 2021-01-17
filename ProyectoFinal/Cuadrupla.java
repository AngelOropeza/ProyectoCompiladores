public class Cuadrupla{

  public String op;
  public String arg1;
  public String arg2;
  public String res;

  // Constructor
  public Cuadrupla(String op, String arg1, String arg2, String res){
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.res = res;
  }
  
  public toString(){
    return op+" "+arg1+" "+arg2+" "+res;
  }
}