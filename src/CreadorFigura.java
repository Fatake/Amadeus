import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

//Clase: Panel Creador de Figura
//Rectangulo: Lectura de 2 Puntos
//Triangulo: LEctura de 3 Puntos
//Circulo: LEctura de 1 punto y un radio
public class CreadorFigura extends JPanel {
  //Componentes
  private ArrayList<JLabel> etiquetasPuntos = new ArrayList<>(); //Etiquetas para Detonar los puntos a introducir
  private ArrayList<JTextField> camposDatos = new ArrayList<>(); //Areas de Texto para las coordenadas en X, en Y y el radio
  private JButton botonCrearFigura;
  private final JLabel labelCoordenadas = new JLabel("Coordenadas:");
  private TextFieldListener manejador; //Escuchador de eventos para todos los JTextField

  //Variables
  private boolean[] camposEscritos; //Campos que ya tienen información escrita
  private String tipoFigura;

  //Constantes
  private static final int columns = 4;

  //Constructor
  //tipo: "Circulo", "Rectangulo", "Triangulo"
  public CreadorFigura(String tipo) {
    super();
    labelCoordenadas.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));
    manejador = new TextFieldListener();

    if (tipo.equals("Circulo") || tipo.equals("circulo") || tipo.equals("CIRCULO") || tipo.equals("c") || tipo.equals("C")) initCreadorCirculo();
    else if (tipo.equals("Rectangulo") || tipo.equals("rectangulo") || tipo.equals("RECTANGULO") || tipo.equals("r") || tipo.equals("R")) initCreadorPoligono(2);
    else if (tipo.equals("Triangulo") || tipo.equals("triangulo") || tipo.equals("TRIANGULO") || tipo.equals("t") || tipo.equals("T")) initCreadorPoligono(3);

    botonCrearFigura = new JButton();
		botonCrearFigura.setBounds(new Rectangle(107, 50, 102, 41));
		botonCrearFigura.setFont(new Font("Arial",Font.PLAIN,14));
		botonCrearFigura.setText("Crear");
    botonCrearFigura.setEnabled(false); //El botón se encuentra desactivado
    add(botonCrearFigura);
  }

  //Panel para Crear Circulo
  private void initCreadorCirculo() {
    tipoFigura = "Circulo";

    etiquetasPuntos.add(new JLabel("Centro"));
    etiquetasPuntos.add(new JLabel("Radio"));

    add(labelCoordenadas);

    //Añade los TextArea al arreglo para después poder ser invocados
    //Centro: X
    add(etiquetasPuntos.get(0));
    camposDatos.add(new JTextField(columns));
    camposDatos.get(0).addKeyListener(manejador);
    add(new JLabel("("));
    add(camposDatos.get(0));
    //Centro: Y
    camposDatos.add(new JTextField(columns));
    camposDatos.get(1).addKeyListener(manejador);
    add(new JLabel(","));
    add(camposDatos.get(1));
    add(new JLabel(")"));
    //Radio
    add(etiquetasPuntos.get(1));
    camposDatos.add(new JTextField(columns));
    camposDatos.get(2).addKeyListener(manejador);
    add(camposDatos.get(2));

    camposEscritos = new boolean[] {false, false, false}; //Ningún elemento tiene texto
  }
  //Panel para Crear Rectangulo o Triangulo
  //type == 2 (Rectangulo), type == 3 (Triangulo)
  private void initCreadorPoligono(int type) {
    if (type == 2) tipoFigura = "Rectangulo";
    else tipoFigura = "Triangulo";

    add(labelCoordenadas);
    camposEscritos = new boolean[type * 2]; //Dos campos por cada Punto

    for (int i = 0; i < type; i++) {
      if (type == 3)
        etiquetasPuntos.add(new JLabel("Punto " + (i + 1)));
      else if (i == 0)
        etiquetasPuntos.add(new JLabel("Esq. Sup. Izq."));
      else
        etiquetasPuntos.add(new JLabel("Esq. Inf. Der."));

      add(etiquetasPuntos.get(i));

      //Añade los TextArea al arreglo para después poder ser invocados
      //Punto: X
      camposDatos.add(new JTextField(columns));
      camposDatos.get(2*i).addKeyListener(manejador); // Pares para X
      add(new JLabel("("));
      add(camposDatos.get(2*i));
      //Punto: Y
      camposDatos.add(new JTextField(columns));
      camposDatos.get(2*i+1).addKeyListener(manejador); // Impares para Y
      add(new JLabel(","));
      add(camposDatos.get(2*i+1));
      add(new JLabel(")"));

      camposEscritos[2*i] = false; //Ningún elemento tiene texto
      camposEscritos[2*i+1] = false;
    }
  }

  //Retorno del Tipo de Figura a Crear
  public String getTipoFigura() {
    return tipoFigura;
  }

  //Retorno de una Coordenada X
  //Recibe: Número de posicion en el arreglo del punto del que se desea la coordenada X
  public int getCoordXi(int i) throws ArrayIndexOutOfBoundsException {
    return Integer.parseInt(camposDatos.get(2*i).getText());
  }
  //Retorno de Coordenada Y
  //Recibe: Número de posicion en el arreglo del punto del que se desea la coordenada Y
  public int getCoordYi(int i) throws ArrayIndexOutOfBoundsException {
    return Integer.parseInt(camposDatos.get(2*i+1).getText());
  }
  //Retorno del Radio
  public int getRadio() {
    if (camposDatos.size() > 3) return -1;
    return Integer.parseInt(camposDatos.get(2).getText());
  }
  //Retorno del Boton
  public JButton getBotonCrear() {
    return botonCrearFigura;
  }

  //Escuchador de Eventos del Teclado
  private class TextFieldListener extends KeyAdapter {
    public void keyTyped(KeyEvent e) {
      JTextField campoFuente = (JTextField) e.getSource(); //Fuente del evento

      // Verificar si la tecla pulsada no es un digito
      if(((e.getKeyChar() < '0') || (e.getKeyChar() > '9')) && (e.getKeyChar() != '\b'))
         e.consume();  // ignorar el evento de teclado

      //Verificar si el número máximo de caracteres ha sido alcanzado
      if (campoFuente.getText().length() == columns) e.consume();

      //Verificar si hay entrada escrita en el Campo Fuente
      if (campoFuente.getText().length() == 0) {
        if (e.getKeyChar() == '\b') camposEscritos[camposDatos.indexOf(campoFuente)] = false;
        else {
          if (camposDatos.size() == 3) { //Si la figura es el circulo, no se permiten medidas o coordenadas en 0
            if((e.getKeyChar() > '0') && (e.getKeyChar() <= '9')) camposEscritos[camposDatos.indexOf(campoFuente)] = true;
          }
          else //Rectangulo y Triangulo admiten coordenadsa en 0
            if((e.getKeyChar() >= '0') && (e.getKeyChar() <= '9')) camposEscritos[camposDatos.indexOf(campoFuente)] = true;
        }
      }

      //Verifica si todos los campos tienen entrada
      botonCrearFigura.setEnabled(true);
      for (int i = 0; i < camposDatos.size(); i++)
        //Si al menos un campo no tiene entrada, el boton permanece desactivado
        if (!camposEscritos[i]) botonCrearFigura.setEnabled(false);
    }
  } // fin clase TextFieldListener
}// fin clase CreadorFigura
