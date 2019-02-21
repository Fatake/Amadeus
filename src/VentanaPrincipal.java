/*Benemérita Universidad Autónoma de Puebla
	Facultad de Ciencias de la Computación

	Ingeniería en Ciencias de la Computación
	Programacion II

	Sistema de Graficación de Figuras Básicas:
	"Amadeus"

	Versión 1.0
	27/nov/2018

	Arizmendi Ramírez Esiel Kevin
	Coria Rios Marco Antonio
	Ruiz Lozano Paulo Cesar

	Otoño 2018
*/

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//
/*		Class Ventana principal		*/
//
public class VentanaPrincipal extends JFrame{
	//Constantes
	private final int WINDOW_LENGHT = 1280; //Tamaño de la Vetana (frame)
	private final int WINDOW_HEIGTH = 720;
	private final Color PANEL_BACKGROUND = new Color(161,175,224);
	private final Color SUBPANEL_BACKGROUND = new Color(140,154,206);

	//Variables
	private ArrayList<DetalleFigura> detallesFiguras = new ArrayList<>();
	private String ruta = "Sin Titulo.acr"; //Ruta del archivo abierto
	private boolean cambios = false; //Variable de cambios en el archivo actual

	//Variables de Creación de Figuras
	public boolean creadorTrianguloHabilitado = false;
	public boolean creadorRectanguloHabilitado = false;
	public boolean creadorCirculoHabilitado = false;

	//Componentes Graficos
	//Contenedor Principal
	private Container contenedorPrincipal;

	//Paneles
	//Paneles principales
	private WorkPanel workPanel;
	private JLabel coordenadasInfo;
	private JPanel creadorFiguras;
	private JPanel panelInfo;
	//Subpanees de informacion figuras
	private JScrollPane scrollInfoFigura;
	private JPanel panelInfoListaFiguras;
	private JPanel panelInfoFigura;
	//Subpaneles de creadorFiguras
	private JPanel coordenadasWorkPanel;
	private JPanel panelCrearDefault;
	private CreadorFigura panelCrearTriangulo;
	private CreadorFigura panelCrearRectangulo;
	private CreadorFigura panelCrearCirculo;

	//Menus
	private JMenuBar menuPrincipal;
	private JMenu menuArchivo;
	private JMenuItem menuArchivoNuevo;
	private JMenuItem menuArchivoAbrir;
	private JMenuItem menuArchivoGuardar;
	private JMenuItem menuArchivoGuardarComo;
	private JMenu menuFigurasGeometricas;
	private JMenuItem menuFigurasDefault;
	private JMenuItem menuFigurasTriangulo;
	private JMenuItem menuFigurasRectangulo;
	private JMenuItem menuFigurasCirculo;

	//
	/*		Constructor		*/
	//
	public VentanaPrincipal() {
		//Coloca el icono de la Aplicación
		Image icon = new ImageIcon(System.getProperty("user.dir") + "\\lib\\icon.png").getImage(); //Obtiene la imagen de la ruta actual + /lib
		setIconImage(icon);

		setTitle("Amadeus - " + ruta);
		setSize(WINDOW_LENGHT, WINDOW_HEIGTH);
		setResizable(false);
		setLocationRelativeTo(null);

		initComponents();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Evita el cierre automático de la ventana
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if (cambios) { //Ya se realizaron cambios
		      int jop = mostrarSeleccion("El archivo tiene cambios sin guardar, ¿Deseas guardar los cambios?");
		      if( jop == JOptionPane.YES_OPTION) {
						guardarArchivo();
						salir();
					}
					else if (jop == JOptionPane.NO_OPTION) salir();
		    }
				else salir();
			}
		});

		setVisible(true);
	}

	//
	/*		Inicialización de Componentes		*/
	//
	private void initComponents() {
		//Fuentes
		Font MENU_FONT = new Font("Arial", Font.PLAIN, 14);
		Font MENUITEM_FONT = new Font("Arial", Font.PLAIN, 12);

		//Contenedor
		Container contenedorPrincipal = getContentPane();
		contenedorPrincipal.setLayout(new BorderLayout());

		//Paneles
		workPanel = new WorkPanel();
		creadorFiguras = new JPanel();
		creadorFiguras.setBackground(PANEL_BACKGROUND);
		panelInfo = new JPanel();
		panelInfo.setBackground(PANEL_BACKGROUND);
		//subpaneles de informacion de figuras
		panelInfoListaFiguras = new JPanel();
		panelInfoListaFiguras.setBackground(SUBPANEL_BACKGROUND);
		panelInfoFigura = new JPanel();
		panelInfoFigura.setLayout(new BoxLayout(panelInfoFigura, BoxLayout.Y_AXIS));
		panelInfoFigura.setBackground(SUBPANEL_BACKGROUND);
		scrollInfoFigura = new JScrollPane(panelInfoFigura);
		//Subpaneles de creador de figuras
		coordenadasWorkPanel = new JPanel();
		coordenadasWorkPanel.setBackground(PANEL_BACKGROUND);

		//
		/*Menu*/
		//
		initMenus(MENU_FONT, MENUITEM_FONT);

		//
		/*WorkPanel*/
		//
		workPanel.setLayout(null);
		workPanel.setBackground(new Color(225,225,225));
		workPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));
		workPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				workPanelMouseMoved(evt);
			}
		});
		contenedorPrincipal.add(workPanel,BorderLayout.CENTER);

		//
		/*Panel de informacion de Figuras*/
		//
		//Label de figuras
		JLabel labelFiguras = new JLabel("		Lista de Figuras:		");
		labelFiguras.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		//Subpanel Superior
		panelInfoListaFiguras.add(labelFiguras);
		panelInfoListaFiguras.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));

		//Subpanel Inferior
		panelInfoFigura.add(new JLabel("No hay figuras"));
		scrollInfoFigura.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//Configuracion de panelInfo
		panelInfo.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));
		panelInfo.setLayout(new BorderLayout());
		panelInfo.add(panelInfoListaFiguras, BorderLayout.NORTH);
		panelInfo.add(scrollInfoFigura, BorderLayout.CENTER);
		contenedorPrincipal.add(panelInfo, BorderLayout.EAST);

		//
		/*Panel de Creador de Figuras*/
		//
		//Subpanel de coordenadas
		coordenadasInfo = new JLabel();
		coordenadasInfo.setFont(MENU_FONT);
		coordenadasInfo.setText("(x,y)");
		coordenadasWorkPanel.add(coordenadasInfo);

		//Subpanel de ninguna figura seleeccionada
		panelCrearDefault = new JPanel();
		panelCrearDefault.add(new JLabel("Seleccione una figura a crear en el menu"));
		panelCrearDefault.setBackground(PANEL_BACKGROUND);

		//Subpanel de triangulo
		panelCrearTriangulo = new CreadorFigura("Triangulo");
		panelCrearTriangulo.setBackground(SUBPANEL_BACKGROUND);
		panelCrearTriangulo.getBotonCrear().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				botonCrearMouseClicked(evt, panelCrearTriangulo);
			}
		});

		//Subpanel de rectangulo
		panelCrearRectangulo = new CreadorFigura("Rectangulo");
		panelCrearRectangulo.setBackground(SUBPANEL_BACKGROUND);
		panelCrearRectangulo.getBotonCrear().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				botonCrearMouseClicked(evt, panelCrearRectangulo);
			}
		});

		//subpanel de circulo
		panelCrearCirculo = new CreadorFigura("Circulo");
		panelCrearCirculo.setBackground(SUBPANEL_BACKGROUND);
		panelCrearCirculo.getBotonCrear().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				botonCrearMouseClicked(evt, panelCrearCirculo);
			}
		});

		//Configuracion Panel Creador de Figuras
		creadorFiguras.setLayout(new BorderLayout());
		creadorFiguras.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new Color(51, 51, 51), null, null));
		creadorFiguras.add(coordenadasWorkPanel, BorderLayout.EAST);
		creadorFiguras.add(panelCrearDefault, BorderLayout.WEST);

		contenedorPrincipal.add(creadorFiguras,BorderLayout.SOUTH);
	}

	//Inicialización de los Menús
	private void initMenus(Font MENU_FONT, Font MENUITEM_FONT) {
		menuPrincipal = new JMenuBar();

		//Menu Archivos
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setFont(MENU_FONT);

		// componentes sub menu Archivo
		menuArchivoNuevo = new JMenuItem("Nuevo");
		menuArchivoNuevo.setFont(MENUITEM_FONT);
		menuArchivoNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuArchivoNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuArchivoNuevoMouseClicked(evt);
			}
		});
		menuArchivo.add(menuArchivoNuevo);

		menuArchivoAbrir = new JMenuItem ("Abrir");
		menuArchivoAbrir.setFont(MENUITEM_FONT);
		menuArchivoAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuArchivoAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuArchivoAbrirMouseClicked(evt);
			}
		});
		menuArchivo.add(menuArchivoAbrir);
		menuArchivo.add(new JPopupMenu.Separator());

		menuArchivoGuardar = new JMenuItem ("Guardar");
		menuArchivoGuardar.setFont(MENUITEM_FONT);
		menuArchivoGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuArchivoGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuArchivoGuardarMouseClicked(evt);
			}
		});
		menuArchivo.add (menuArchivoGuardar);

		menuArchivoGuardarComo = new JMenuItem ("Guardar como");
		menuArchivoGuardarComo.setFont(MENUITEM_FONT);
		menuArchivoGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		menuArchivoGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuArchivoGuardarComoMouseClicked(evt);
			}
		});
		menuArchivo.add (menuArchivoGuardarComo);

		// Menu Figuras Figuras Geometricas
		menuFigurasGeometricas= new JMenu ("Figuras Geometricas");
		menuFigurasGeometricas.setFont(MENU_FONT);

		// Submenu de Figuras Geometricas
		menuFigurasDefault = new JMenuItem ("Ninguna");
		menuFigurasDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFigurasDefaultMouseClicked(evt);
			}
		});
		menuFigurasGeometricas.add(menuFigurasDefault);
		menuFigurasGeometricas.add(new JPopupMenu.Separator());

		menuFigurasTriangulo = new JMenuItem ("Triangulo") ;
		menuFigurasTriangulo.setFont(MENUITEM_FONT);
		menuFigurasTriangulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFigurasTrianguloMouseClicked(evt);
			}
		});
		menuFigurasGeometricas.add(menuFigurasTriangulo ) ;

		menuFigurasRectangulo = new JMenuItem ("Rectangulo");
		menuFigurasRectangulo.setFont(MENUITEM_FONT);
		menuFigurasRectangulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFigurasRectanguloMouseClicked(evt);
			}
		});
		menuFigurasGeometricas.add(menuFigurasRectangulo) ;

		menuFigurasCirculo= new JMenuItem ("Circulo");
		menuFigurasCirculo.setFont(MENUITEM_FONT);
		menuFigurasCirculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFigurasCirculoMouseClicked(evt);
			}
		});
		menuFigurasGeometricas.add(menuFigurasCirculo);
		//Se añaden todos los menus
		menuPrincipal.add(menuArchivo);
		menuPrincipal.add(new JPopupMenu.Separator());
		menuPrincipal.add(menuFigurasGeometricas);
		this.setJMenuBar(menuPrincipal);
	}

	//Cambiar el Creador de Figuras
	public void cambiarCreadorFiguras(JPanel creador) {
		if (creadorTrianguloHabilitado) creadorFiguras.remove(panelCrearTriangulo);
		else if (creadorRectanguloHabilitado) creadorFiguras.remove(panelCrearRectangulo);
		else if (creadorCirculoHabilitado) creadorFiguras.remove(panelCrearCirculo);
		else creadorFiguras.remove(panelCrearDefault);

		creadorFiguras.add(creador, BorderLayout.WEST);
		creadorFiguras.updateUI();
	}

	//Actualizacion del panel de información
	public void actualizarPanelInfo() {
		panelInfoFigura.removeAll();
		for (DetalleFigura panelDetalle : detallesFiguras)
			panelInfoFigura.add(panelDetalle);

		panelInfoFigura.updateUI();
		scrollInfoFigura.updateUI();
		panelInfo.updateUI();
	}

	//Mensaje de Error
	public void mostrarAdvertencia(String control) {
		JOptionPane.showMessageDialog(this, control, "Error", JOptionPane.ERROR_MESSAGE);
	}

	//Mensaje de Advertencia
	public int mostrarSeleccion(String control) {
		return JOptionPane.showConfirmDialog(null, control, ruta, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	}

	//Establecer cambios
	public void cambios(boolean valor) {
		if (valor) setTitle("* Amadeus - " + ruta);
		else setTitle("Amadeus - " + ruta);

		cambios = valor;
	}

  //Guardar archivo Como
  public void guardarComoArchivo(){
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    jfc.setDialogTitle("Guardar como: ");
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ACR", "acr");
    jfc.addChoosableFileFilter(filter);

    do {
      int returnValue = jfc.showSaveDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION){
        ruta = jfc.getSelectedFile().getAbsolutePath().concat(".acr");
        File file = new File(ruta);

        if(file.exists()) { //El archivo existe?
          int jop = this.mostrarSeleccion("Desea sobreescribir el archivo existente");

          //Verificar si desea sobre escribir
          if (jop == JOptionPane.YES_OPTION) {
						guardarArchivo();
						break;
					}
        }
				else { //No existe el archivo se crea y se guarda
					guardarArchivo();
					break;
				}
      }
			else break;
    } while (true);
  }

  //Guardar Archivo
  public void guardarArchivo(){
    //Abrir Archivo
    if(Archivo.fopen(ruta, 'w')){
      //Escribir Archivo
      if(!Archivo.fwrite((Object) workPanel.getFiguras())) mostrarAdvertencia("ERROR: No se puedo guardar el archivo");

      //Cerrar Archivo
      Archivo.fclose('w');
      cambios(false);
    }
		else mostrarAdvertencia("ERROR: No se puedo salvar el archivo ");
  }

  //Abrir Archivo Nuevo
  public void reiniciarFrame(){
    workPanel.setFiguras(new ArrayList<>()); //repaint
    detallesFiguras = new ArrayList<>();
    actualizarPanelInfo(); //repaint

		ruta = "Sin Titulo.acr";
    cambios(false);
  }


	//Abrir Archivo
  public void abrirArchivo(){
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    jfc.setDialogTitle("Seleecione un archivo .acr");
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ACR", "acr");
    jfc.addChoosableFileFilter(filter);

    int returnValue = jfc.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      reiniciarFrame(); //Reiniciar componentes
      ruta = jfc.getSelectedFile().getPath();

      //Abrir Archivo
      if(Archivo.fopen(ruta, 'r')){
        //ArrayList Auxiliar
        ArrayList<Figura> aux;
        aux = (ArrayList<Figura>)Archivo.fread();

        if(aux != null){
          workPanel.setFiguras(aux);

          //Cerrar archivo
          Archivo.fclose('r');

          //Inicializar Figuras
          for(Figura figura : workPanel.getFiguras()){
            DetalleFigura nuevoPanelFigura = new DetalleFigura(figura);
            detallesFiguras.add(nuevoPanelFigura);

            //Implementacion del boton eliminar
            nuevoPanelFigura.getBotonEliminar().addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent evt) {
                botonEliminarMouseClicked(evt, nuevoPanelFigura);
	            }
            });

            actualizarPanelInfo();
          }

          Figura.setContID(workPanel.getFiguras().get(workPanel.getFiguras().size()-1).getID()); //ID SE INICIALIZA EN LA ULTIMA FIGURA
          cambios(false);
        }
				else mostrarAdvertencia("Archivo Dañado");
      }
    }
  }

	//Salida del Programa
	private void salir() {
		setVisible(false); //Esconde la ventana
		dispose(); //Elimina los componentes gráficos y retorna la memoria al SO

		System.exit(0); //Termina el Proceso
	}

	//
	/*		EVENTOS		*/
	//
	//Menús Eventos
	//
	//Click sobre menuArchivoGuardar
	public void menuArchivoNuevoMouseClicked(ActionEvent evt){
    if (cambios) { //Ya se realizaron cambios
      int jop = mostrarSeleccion("El archivo tiene cambios sin guardar, ¿Deseas guardar los cambios?");
      if (jop == JOptionPane.YES_OPTION) {
				guardarArchivo();
				reiniciarFrame();
			}
			else if (jop == JOptionPane.NO_OPTION) reiniciarFrame();
    }
		else reiniciarFrame();
	}

	//Click sobre menuArchivoAbrir
	public void menuArchivoAbrirMouseClicked(ActionEvent evt){
    if (cambios) { //Ya se realizaron cambios
      int jop = mostrarSeleccion("El archivo tiene cambios sin guardar, ¿Deseas guardar los cambios?");
      if( jop == JOptionPane.YES_OPTION) {
				guardarArchivo();
				abrirArchivo();
			}
			else if (jop == JOptionPane.NO_OPTION) abrirArchivo();
    }
		else abrirArchivo();
	}
	//Click sobre menuArchivoGuardar
	public void menuArchivoGuardarMouseClicked(ActionEvent evt){
    if (ruta.equals("Sin Titulo.acr")) guardarComoArchivo();
    else guardarArchivo();
	}

	//Click sobre menuArchivoGuardarComo
	public void menuArchivoGuardarComoMouseClicked(ActionEvent evt){
    guardarComoArchivo();
  }

	//Click sobre menuFigurasDefault
	public void menuFigurasDefaultMouseClicked(ActionEvent evt){
		cambiarCreadorFiguras(panelCrearDefault);
		creadorCirculoHabilitado = false;
		creadorTrianguloHabilitado = false;
		creadorRectanguloHabilitado = false;
	}

	//Click sobre menuFigurasCirculo
	public void menuFigurasCirculoMouseClicked(ActionEvent evt){
		cambiarCreadorFiguras(panelCrearCirculo);
		creadorCirculoHabilitado = true;
		creadorTrianguloHabilitado = false;
		creadorRectanguloHabilitado = false;
	}
	//Click sobre menuFigurasTriangulo
	public void menuFigurasTrianguloMouseClicked(ActionEvent evt){
		cambiarCreadorFiguras(panelCrearTriangulo);
		creadorCirculoHabilitado = false;
		creadorTrianguloHabilitado = true;
		creadorRectanguloHabilitado = false;
	}
	//Click sobre menuFigurasRecangulo
	public void menuFigurasRectanguloMouseClicked(ActionEvent evt){
		cambiarCreadorFiguras(panelCrearRectangulo);
		creadorCirculoHabilitado = false;
		creadorTrianguloHabilitado = false;
		creadorRectanguloHabilitado = true;
	}

	//
	//WorkPanel Eventos
	//
	//Mouse movido sobre WorkPanel
	public void workPanelMouseMoved(MouseEvent evt){
		coordenadasInfo.setText("(" + evt.getX() + "," + evt.getY() + ")");
	}

	//
	//Paneles de Creación de Figuras
	//
	//tipo == 1 (Circulo), tipo == 2 (Rectangulo), tipo == 3 (Triangulo)
	public void botonCrearMouseClicked(MouseEvent evt, CreadorFigura panelCreador) {
		Figura nuevaFigura;

		//Se crea la figura dependiendo del panel
		if (panelCreador.getTipoFigura().equals("Circulo")) nuevaFigura = crearCirculo(panelCreador);
		else if (panelCreador.getTipoFigura().equals("Rectangulo"))  nuevaFigura = crearRectangulo(panelCreador);
		else nuevaFigura = crearTriangulo(panelCreador);

		if (nuevaFigura != null) {
			workPanel.addFigura(nuevaFigura);
      cambios(true);

			//Se crea el panel de detalles de la figura y se añade al sistema
			DetalleFigura nuevoPanelFigura = new DetalleFigura(nuevaFigura);
			detallesFiguras.add(nuevoPanelFigura);

			actualizarPanelInfo();

			//implementación del botón de eliminar figura
			nuevoPanelFigura.getBotonEliminar().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					botonEliminarMouseClicked(evt, nuevoPanelFigura);
				}
			});
		}
	}
	//Creación de un Círculo
	public Circulo crearCirculo(CreadorFigura panelCreador) {
		Punto centro;
		int radio, x, y;
		int[] limitesPant = new int[4];
				//Distancias a los bordes de la pantalla siendo:
				//[0] = Límite de longitud hacia la derecha
				//[1] = límite de longitud hacia la Izquierda
				//[2] = Límite de altura hacia arriba
				//[3] = límite de altura hacia abajo


		x = panelCreador.getCoordXi(0);
		if (x > workPanel.getWidth() - 1) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth() - 1));
			return null;
		}
		y = panelCreador.getCoordYi(0);
		if (y > workPanel.getHeight() - 1) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight() - 1));
			return null;
		}
		centro = new Punto(x, y);

		//Obtención de los límites
		limitesPant[0] = x;
		limitesPant[1] = workPanel.getWidth() - x;
		limitesPant[2] = y;
		limitesPant[3] = workPanel.getHeight() - y;

		//Se busca el valor más pequeño (el cual se quedará en limitesPant[3])
		for (int i = 0; i < 3; i++)
			if (limitesPant[i] < limitesPant[i + 1]) limitesPant[i + 1] = limitesPant[i];

		radio = panelCreador.getRadio();

		if(radio > limitesPant[3]) {
			mostrarAdvertencia("El Radio debe ser menor o igual a: " + limitesPant[3]);
			return null;
		}

		//Se construye la figura y se retorna
		Circulo circle = new Circulo(centro, radio);
		return circle;
	}
	//Creación de Rectangulo
	public Rectangulo crearRectangulo(CreadorFigura panelCreador) {
		Punto[] vertices = new Punto[2];
	   int x, y;

		 //Esquina Superior Izquierda
		x = panelCreador.getCoordXi(0);
		if (x > workPanel.getWidth() - 1) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth() - 1));
			return null;
		}
		y = panelCreador.getCoordYi(0);
		if (y > workPanel.getHeight() - 1) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight() - 1));
			return null;
		}
	  vertices[0] = new Punto(x, y);

		//Esquina Inferior Derecha
		x = panelCreador.getCoordXi(1);
		if (x <= vertices[0].getX()) {
			mostrarAdvertencia("Minima Coordenada en X de la Esq. Inf. Der.: " + (vertices[0].getX() + 1));
			return null;
		}
		if (x > workPanel.getWidth()) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth()));
			return null;
		}
		y = panelCreador.getCoordYi(1);
		if (y <= vertices[0].getY()) {
			mostrarAdvertencia("Minima Coordenada en Y de la Esq. Inf. Der.: " + (vertices[0].getY() + 1));
			return null;
		}
		if (y > workPanel.getHeight()) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight()));
			return null;
		}
    vertices[1] = new Punto(x, y);

	  //Se construye la figura y se retorna
	  Rectangulo rectangle = new Rectangulo(vertices);
	  return rectangle;
	}
	//Creación de Triangulo
	public Triangulo crearTriangulo(CreadorFigura panelCreador) {
		Punto[] vertices = new Punto[3];
    int x, y;

    //Punto 1
		x = panelCreador.getCoordXi(0);
		if (x > workPanel.getWidth()) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth()));
			return null;
		}
		y = panelCreador.getCoordYi(0);
		if (y > workPanel.getHeight()) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight()));
			return null;
		}
	  vertices[0] = new Punto(x, y);

    //Punto 2
		x = panelCreador.getCoordXi(1);
		if (x > workPanel.getWidth()) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth()));
			return null;
		}
		y = panelCreador.getCoordYi(1);
		if (y == vertices[0].getY() && x == vertices[0].getX()) {
			mostrarAdvertencia("Punto 2 no puede ser igual a Punto 1");
			return null;
		}
		if (y > workPanel.getHeight()) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight()));
			return null;
		}
	  vertices[1] = new Punto(x, y);

    //Punto 3
		x = panelCreador.getCoordXi(2);
		if (x == vertices[0].getX() && x == vertices[1].getX()) {
			mostrarAdvertencia("La Coordenada X del Punto 3 no puede ser igual a : " + vertices[0].getX());
			return null;
		}
		if (x > workPanel.getWidth()) {
			mostrarAdvertencia("Maxima Coordenada X: " + (workPanel.getWidth()));
			return null;
		}
		y = panelCreador.getCoordYi(2);
		if (y == vertices[0].getY() && y == vertices[1].getY()) {
			mostrarAdvertencia("La Coordenada Y del Punto 3 no puede ser igual a : " + vertices[0].getY());
			return null;
		}
		if (x == vertices[0].getX() && y == vertices[0].getY()) {
			mostrarAdvertencia("Punto 3 no puede ser igual al Punto 1");
			return null;
		}
		if (x == vertices[1].getX() && y == vertices[1].getY()) {
			mostrarAdvertencia("Punto 3 no puede ser igual al Punto 2");
			return null;
		}
		if (y > workPanel.getHeight()) {
			mostrarAdvertencia("Maxima Coordenada Y: " + (workPanel.getHeight()));
			return null;
		}
    vertices[2] = new Punto(x, y);

    //Se construye la figura y se retorna
    Triangulo triangle = new Triangulo(vertices);
    return triangle;
	}

	//
	//Paneles de Detalles de Figura
	//
	//Click sobre el botón de elimianr una figura
	public void botonEliminarMouseClicked(MouseEvent evt, DetalleFigura detalleFigura) {
		workPanel.delFigura(detalleFigura.getFiguraID());

		//Se remueve el panel de detalles
		detallesFiguras.remove(detalleFigura);
		actualizarPanelInfo();
    cambios(true);
	}



	//
	/*		Programa Principal (Creacion de la ventana)		*/
	//
	public static void main (String[] args) {
		VentanaPrincipal mainFrame = new VentanaPrincipal();
	}
}
