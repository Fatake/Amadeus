/*Clase Rectangulo:
  Clase final que representa a un Rectángulo a través de dos puntos
  en el espacio, que son los puntos de dos esquinas contrarias.*/
import java.awt.*;
import java.io.Serializable;

public class Rectangulo extends Figura implements Serializable{
  private final Punto[] vertices; //Conjunto de puntos (2)

  //Constructor
  public Rectangulo(Punto[] vertices) {
    super();
    this.vertices = vertices;
  }

  //Retorno de Información
  public String toString() {
    return ("\nFigura No.: " + ID + ", Rectangulo."
          + "\nPunto 1: " + vertices[0]
          + "\nPunto 2: (" + vertices[1].getX() + "," + vertices[0].getY()
          + ")\nPunto 3: (" + vertices[0].getX() + "," + vertices[1].getY()
          + ")\nPunto 4: " + vertices[1]);
  }

  //Retorno de ID y Tipo
  public String getInfo() {
    return ("Punto 1: " + vertices[0]
          + "\nPunto 2: (" + vertices[1].getX() + "," + vertices[0].getY()
          + ")\nPunto 3: (" + vertices[0].getX() + "," + vertices[1].getY()
          + ")\nPunto 4: " + vertices[1] + "\n");
  }

  //Pintar figura
  public void pintarFigura(Graphics g) {
    g.setColor(relleno);
    g.fillRect(vertices[0].getX(), vertices[0].getY(), vertices[1].getX() - vertices[0].getX(), vertices[1].getY() - vertices[0].getY());
    g.setColor(contorno);
    ((Graphics2D)g).setStroke(new BasicStroke(2));
    g.drawRect(vertices[0].getX(), vertices[0].getY(), vertices[1].getX() - vertices[0].getX(), vertices[1].getY() - vertices[0].getY());
  }
}
