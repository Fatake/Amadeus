import java.io.Serializable;

/*Clase Punto:
  Clase que representa las coordenadas de un punto en el plano.*/

public final class Punto implements Serializable{
  private final int x,y;

  //Constructor
  public Punto(int x, int y) {
    this.x = x;
    this.y = y;
  }

  //Obtener x
  public int getX() {
    return x;
  }

  //Obtener y
  public int getY() {
    return y;
  }

  //Cadena de información de la coordenada (x,y)
  public String toString() {
    return ("(" + x + "," + y + ")");
  }
}
