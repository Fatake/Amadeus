/*Clase Figura:
	Abstracta.
	Engloba todas las figuras que maneja el programa.
	Hereda el campo del ID único para cada figura creada en el programa.
	Contiene el contador que genera los ID de cada figura.
 */
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public abstract class Figura implements Serializable {
	//Atributos
	private static int contID = 0;  //Generador de ID (Contador)
	protected final int ID; //ID de la Figura

	//Color de la Figura
	protected Color contorno;
	protected Color relleno;

	//Metodos
	//Constructor
	public Figura() {
		ID = ++contID;

		genColor(); //Genera el Color de la Figura
	}
	public static int getContID() {
		return contID;
	}

        public static void setContID(int id){
            contID=id;
        }

	//Obtener ID
	public int getID() {
		return ID;
	}

	//Generar los Colores del Contorno y Relleno
	private void genColor() {
		Random rand = new Random(); //Generador de Numeros aleatorios

		contorno = new Color(rand.nextInt(220), rand.nextInt(220), rand.nextInt(220));
		relleno = new Color(rand.nextInt(220), rand.nextInt(220), rand.nextInt(220));
	}

	//Retorno de cadenas con información de la Figura (Abstractos)
	public abstract String toString();
	public abstract String getInfo();

	//Pintar la figura en el ponel
	public abstract void pintarFigura(Graphics g);
}
