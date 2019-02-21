import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

//Panel de Detalles de una Figura
public class DetalleFigura extends JPanel {
  //Atributos
  private final JPanel detalleEtiquetas;
  private final JLabel figuraID;
  private final JLabel tipoFigura;
  private final JTextArea infoFigura;
  private final JButton botonEliminar;

  //Métodos
  //Constructor
  public DetalleFigura(Figura figura) {
    super();
    setLayout(new BorderLayout());
    setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));

    figuraID = new JLabel("ID: " + figura.getID());

    if (figura instanceof Circulo) {
      tipoFigura = new JLabel("Circulo");
      setMaximumSize(new Dimension(210,100));
      setMinimumSize(new Dimension(210,100));
    }
    else if (figura instanceof Rectangulo) {
      tipoFigura = new JLabel("Rectangulo");
      setMaximumSize(new Dimension(210,125));
      setMinimumSize(new Dimension(210,125));
    }
    else {
      tipoFigura = new JLabel("Triangulo");
      setMaximumSize(new Dimension(210,110));
      setMinimumSize(new Dimension(210,110));
    }

    //Adición de las etiquetas al panel superior
    detalleEtiquetas = new JPanel();
    detalleEtiquetas.setLayout(new FlowLayout());
    detalleEtiquetas.add(figuraID);
    detalleEtiquetas.add(tipoFigura);
    add(detalleEtiquetas, BorderLayout.NORTH); //Parte superior

    //Adicion de la información de la Figura
    infoFigura = new JTextArea(2,15);
    infoFigura.setText(figura.getInfo());
    infoFigura.setEditable(false);
    add(infoFigura, BorderLayout.CENTER);

    //Adición del Boton de Eliminar
    botonEliminar = new JButton();
    botonEliminar.setFont(new Font("Arial",Font.BOLD,14));
    botonEliminar.setText("Eliminar");
    botonEliminar.setBackground(new Color(237,26,26)); //Botón Rojo
    botonEliminar.setForeground(Color.WHITE); //Texto blanco
    add(botonEliminar, BorderLayout.SOUTH);
  }

  //Retorno del Boton de Eliminar Figura
  public JButton getBotonEliminar() {
    return botonEliminar;
  }

  //Retornar ID
  public int getFiguraID() {
    return Integer.parseInt(figuraID.getText().substring(4));
  }
}
