/*Clase Circulo:
  Clase final que representa la figura geométrica Círculo a partir de
  su centro (un Punto) y su radio.*/
import java.awt.*;
import java.io.Serializable;

public final class Circulo extends Figura  implements Serializable{
  private final Punto centro;
  private final int radio;

  //Constructor
  public Circulo(Punto centro, int radio) {
    super();
    this.centro = centro;
    this.radio = radio;
  }

  //Retorno de Información
  public String toString() {
    return ("\nFigura No.: " + ID + ", Circulo."
          + "\nCentro:" + centro + "\nRadio: "+ radio);
  }

  //Retorno de ID y Tipo
  public String getInfo() {
    return ("Centro:" + centro + "\nRadio: "+ radio + "\n");
  }

  //Pintar figura
  public void pintarFigura(Graphics g) {
    g.setColor(relleno);
    g.fillOval(centro.getX() - radio, centro.getY() - radio, radio*2, radio*2);
    g.setColor(contorno);
    ((Graphics2D)g).setStroke(new BasicStroke(2));
    g.drawOval(centro.getX() - radio, centro.getY() - radio, radio*2, radio*2);
  }
}
