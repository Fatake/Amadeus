/*Clase Triangulo:
  Clase final que representa a un Triángulo a través de tres puntos
  en el espacio, que son los vértices del Triángulo.*/
import java.awt.*;
import java.io.Serializable;

public class Triangulo extends Figura implements Serializable{
  private final Punto[] vertices; //Conjunto de Puntos (3)

  //Constructor
  public Triangulo(Punto[] vertices) {
    super();
    this.vertices = vertices;
  }

  //Retorno de Información
  public String toString() {
    return ("\nFigura No.: " + ID + ", Triangulo."
            + "\nPunto 1: " + vertices[0]
            + "\nPunto 2: " + vertices[1]
            + "\nPunto 3: " + vertices[2]);
  }

  //Retorno de ID y Tipo
  public String getInfo() {
    return ("Punto 1: " + vertices[0]
          + "\nPunto 2: " + vertices[1]
          + "\nPunto 3: " + vertices[2] + "\n");
  }

  //Pintar figura
  public void pintarFigura(Graphics g) {
    g.setColor(relleno);
    g.fillPolygon(getXCoordinates(), getYCoordinates(), vertices.length);
    g.setColor(contorno);
    ((Graphics2D)g).setStroke(new BasicStroke(2));
    g.drawPolygon(getXCoordinates(), getYCoordinates(), vertices.length);
  }

  //Obtención de Arreglo de Coordenadas X (Privada)
  private int[] getXCoordinates() {
    int[] xCoord = new int[vertices.length];

    for (int i = 0; i < vertices.length; i++) xCoord[i] = vertices[i].getX();

    return xCoord;
  }

  //Obtención de Arreglo de Coordenadas Y (Privada)
  private int[] getYCoordinates() {
    int[] yCoord = new int[vertices.length];

    for (int i = 0; i < vertices.length; i++) yCoord[i] = vertices[i].getY();

    return yCoord;
  }
}
