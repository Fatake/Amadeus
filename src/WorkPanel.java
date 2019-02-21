import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WorkPanel extends JPanel{
  //Variables del Panel
  private ArrayList<Figura> figuras = new ArrayList<>();

  //Métodos
  //Constructor
  public WorkPanel() {
    super();
  }

  //Pintar en el Panel
  @Override
  public void paint(Graphics g) {
    super.paintComponent(g);
    for (Figura figura : figuras){
      figura.pintarFigura(g);
    }
  }

  //Obtención del Arreglo de Figuras
  public ArrayList<Figura> getFiguras() {
    return figuras;
  }
  //Set del Arreglo de Figuras
  public void setFiguras(ArrayList<Figura> figuras) {
    this.figuras = figuras;
    repaint();
  }

  //Agregar figuras al Panel
  public void addFigura(Figura figura) {
    figuras.add(figura);
    repaint();
  }

  //Eliminar Figuras del panel
  public void delFigura(int ID) {
    for (Figura figura : figuras)
      if (figura.getID() == ID) {
        figuras.remove(figura);
        repaint();
        break;
      }
  }
}
