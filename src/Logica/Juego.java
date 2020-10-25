package Logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Random;

public class Juego {
	protected static int cantFilas=9,  cantColumnas=9;
	protected Celda[][] mapa; 
	protected int [][] estadoInicial;
	protected int Verificador; 
	protected LinkedHashMap<String, Integer> errores; 
	
	public Juego() {	
		boolean esCorrecto = true;
		errores = new LinkedHashMap<String, Integer>();
		esCorrecto = cargarArchivo();
		if(esCorrecto)
			esCorrecto = estadoInicial();
		if(esCorrecto) 
			crearJuego();
		else
			mapa = null;
	}
	
	/**
	 * Carga la solución del juego que esta en un archivo de texto y evalúa el formato de la solución.
	 * @return TRUE Si el archivo fue cargado de forma exitosa y el formato era el correcto. FALSE caso contrario.
	 */
	protected boolean cargarArchivo() {
		boolean toReturn = true;
		String linea = "";
		String[] auxiliar;
		int fila = 0;
		int numero;
		try {
			String rutaArchivo = "Sudoku.txt";
			InputStream in = Juego.class.getClassLoader().getResourceAsStream(rutaArchivo);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader bf = new BufferedReader(isr);
			estadoInicial = new int [cantFilas][cantColumnas];
			while((linea = bf.readLine())!=null && toReturn) {
				auxiliar = linea.split(" ");
				if(auxiliar.length==cantColumnas)
					for(int i=0; i<auxiliar.length && toReturn; i++) {
						numero = Integer.parseInt(auxiliar[i]);
						if(numero>0 && numero<=9)
							estadoInicial[fila][i] = numero;
						else
							toReturn = false;
					}
				else
					toReturn = false;
				fila++;
			}
			bf.close();
			toReturn = toReturn==true ? fila==cantFilas : toReturn;
		}
		catch(IOException e) {
			e.printStackTrace();
			toReturn = false;
		}
		return toReturn;
	}
	
	public Celda getCelda(int f, int c) {
		return mapa[f][c];
	}
	
	public int getCantFilas() {
		return cantFilas;
	}
	
	public int getCantColumnas() {
		return cantColumnas;
	}
	
	public Celda[][] getMapa(){
		return mapa;
	}
	
	public int getVerificador() {
		return Verificador;
	}
	
	/**
	 * Actualiza el valor de control.
	 * @param numero - Valor actual de la celda que se activa. 
	 */
	public void setVerificador(int numero) {
		if(numero == 1) //Estaba en 0, por lo que es la primera vez que se activa.
			Verificador++;
		else if(numero == 0) //Estaba en 9, por lo que al adoptar 0, se desactiva la celda.
			Verificador--; //Caso contrario (el else faltante), la celda ya había sido activada con anterioridad.
	}
	
	/**
	 * Evalúa la correctitud de la solución del archivo de texto. Evalúa si las filas, columnas y paneles son correctas.
	 * @return TRUE si es correcto, FALSE caso contrario.
	 */
	public boolean estadoInicial() {
		boolean toReturn = true;
		for(int i=0, j=0; i<cantFilas && j<cantColumnas && toReturn; i++, j++) 
			toReturn = chequearFila(i, estadoInicial) && chequearColumna(j, estadoInicial) && chequearPanel(i, estadoInicial);
		return toReturn;
	}
	
	/**
	 * Evalúa la correctitud de los datos cargados en un panel determinado.
	 * @param nroPanel - Panel donde se desea evaluar la correctitud.
	 * @param m - Matriz de valor enteros correspondiente a los valores que se desean chequear.
	 * @return TRUE si es correcto, FALSE caso contrario.
	 */
	protected boolean chequearPanel(int nroPanel, int m[][]) {
		boolean toReturn;
		String[] arregloControl = new String[(cantFilas+cantColumnas)/2];
		int f, c;
		if(nroPanel%3==0) {
			f = nroPanel;
			c = 0;
		}
		else if(nroPanel%3==1) {
			f = nroPanel-1;
			c = 3;
		}
		else {
			f = nroPanel-2;
			c = 6;
		}
		int valor;		
		for(int i=f; i<f+3; i++)
			for(int j=c; j<c+3; j++) {
				valor = m[i][j];
				if(valor!=0) {
					toReturn = arregloControl[valor-1] == null;
					if(!toReturn) {
						errores.put(i+","+j, valor);
						errores.put(arregloControl[valor-1], valor);
					}
					arregloControl[valor-1] = i+","+j;
				}
			}		
		return errores.isEmpty();
	}
	
	/**
	 * Evalúa la correctitud de los datos cargados en una fila determinada.
	 * @param fila - Fila donde se desea evaluar la correctitud.
	 * @param m - Matriz de valor enteros correspondiente a los valores que se desean chequear.
	 * @return TRUE si es correcto, FALSE caso contrario.
	 */
	protected boolean chequearFila(int fila, int m[][]) {
		boolean toReturn;
		String[] arregloControl = new String[cantColumnas];
		int valor;
		for(int c=0; c<cantColumnas; c++) {
			valor = m[fila][c];
				if(valor!=0) {
				toReturn = arregloControl[valor-1] == null;
				if(!toReturn) {
					errores.put(fila+","+c, valor);
					errores.put(arregloControl[valor-1], valor);
				}
				arregloControl[valor-1] = fila+","+c;
			}
		}	
		return errores.isEmpty();
	}
	
	/**
	 * Evalúa la correctitud de los datos cargados en una columna determinada.
	 * @param columna - Columna donde se desea evaluar la correctitud.
	 * @param m - Matriz de valor enteros correspondiente a los valores que se desean chequear.
	 * @return TRUE si es correcto, FALSE caso contrario.
	 */
	protected boolean chequearColumna(int columna, int m[][]) {
		boolean toReturn;
		String[] arregloControl = new String[cantFilas];
		int valor;
		for(int f=0; f<cantFilas; f++) {
			valor = m[f][columna];
			if(valor!=0) {
				toReturn = arregloControl[valor-1] == null;
				if(!toReturn) {
					errores.put(f+","+columna, valor);
					errores.put(arregloControl[valor-1], valor);
				}	
				arregloControl[valor-1] = f+","+columna;
			}
		}	
		return errores.isEmpty();
	}

	/**
	 * Crea el juego asignando celdas a cada componente del tablero. Inicializa solo algunas con los valores correspondientes
	 * y los otros los deja vacíos para que sean completados en la ejecución del juego.
	 */
	public void crearJuego() {
		Random rand = new Random();
		int value;
		int valor;
		Verificador = 0;
		mapa = new Celda[cantFilas][cantColumnas]; //Crea un tablero de cantFilas x cantColumnas Celdas
		for(int f=0; f<cantFilas; f++) {
			for(int c=0; c<cantColumnas; c++) {
				mapa[f][c] = new Celda(); //Por cada componente del tablero le creo una Celda
				valor = 0;
				value = rand.nextInt(5); //Se toman mas de dos valores para disminuir la probabilidad de que aparezcan muchos numeros.
				if(value==0 || value == 1) { //Si el valor es 0 o 1, le asigno el valor que debería tener en el juego
					valor = estadoInicial[f][c];
					Verificador++;
				}	
				mapa[f][c].setValor(valor);	
			}
		}
	}
	
	/**
	 * Evalúa la correctitud de la solución propuesta del usuario.
	 * @return TRUE si es correcto, FALSE caso contrario.
	 */
	public boolean comprobar() {
		return errores.isEmpty();
	}
	 	
	/**
	 * Se actualizan los errores presentes en el tablero agregando los nuevos y eliminando los que ya no son contemplados como error.
	 * Los errores se almacenan de forma i,j con i numero de fila y j numero de columna.
	 * @param indice - Índice a partir del cual se buscan los nuevos errores.
	 */
 	public void setErrores(String indice) {
 		int[][] matrizAux = new int[cantFilas][cantColumnas];
 		String[] erroresBorrar;
 		int pos = 0;
 		for(int f=0; f<cantFilas; f++)
 			for(int c=0; c<cantColumnas; c++)
 				matrizAux[f][c] = mapa[f][c].getValor();
 		for(int i=0, j=0; i<cantFilas && j<cantColumnas; i++, j++){
			chequearFila(i, matrizAux);
			chequearColumna(j, matrizAux);
			chequearPanel(i, matrizAux);
 		}	
 		erroresBorrar = new String[errores.size()]; //Almacena los errores guardados que ya no son errores por la actualización del índice.
 		for(String clave : errores.keySet())
	 		if(!repetido(clave, matrizAux))
	 			erroresBorrar[pos++] = clave;
 		for(int i=0; i<erroresBorrar.length && erroresBorrar[i]!=null; i++)
	 			errores.remove(erroresBorrar[i]);
 	}
 	
 	/**
 	 * Getter de los errores.
 	 * @return arreglo de cadenas de forma i,j que modela las coordenadas en el tablero donde hay un error.
 	 */
 	public String[] getErrores() {
		String[] toReturn;
		toReturn = errores.size()==0 ? null : new String[errores.size()];
		int pos = 0;
		for(String indice : errores.keySet())
			toReturn[pos++] = indice;
		return toReturn;
	}
	
 	/**
 	 * Evalúa si una celda en una posición determinada del mapa esta incumpliendo alguna regla del juego.
 	 * @param indice - Índice del error a partir del cual se busca evaluar la repetición.
 	 * @param m - Matriz de valor enteros correspondiente a los valores de las celdas.
 	 * @return TRUE si hay alguna repetición sobre su fila, columna o panel. FALSE caso contrario.
 	 */
 	protected boolean repetido(String indice, int[][] m) {
 		String[] coordenadas = indice.split(",");
 		int fila = Integer.parseInt(coordenadas[0]);
 		int columna = Integer.parseInt(coordenadas[1]);
 		boolean toReturn = false;
 		int valor = m[fila][columna];
 		for(int f=0; f<cantFilas && !toReturn && valor!=0; f++) //Busco repeticiones del valor en la fila.
			if(f!=fila) //Descarto el indice que deseo chequear.
				toReturn = mapa[f][columna].getValor() == valor ? true : false;
		for(int c=0; c<cantColumnas && !toReturn && valor!=0; c++) //Busco repeticiones del valor en la columna.
			if(c!=columna) //Descarto el indice que deseo chequear.
				toReturn = mapa[fila][c].getValor() == valor ? true : false;
		toReturn = !toReturn ? repetidoPanel(fila, columna, m) : toReturn;
		return toReturn;
 	}
 	
 	/**
	 * Evalúa si una celda en una posición determinada del tablero se encuentra incumpliendo la regla de no repetición sobre un subpanel.
	 * @param fila - Fila donde pertenece el valor a chequear.
	 * @param columna - Columna donde pertenece el valor a chequear.
	 * @param m - Matriz de valor enteros correspondiente a los valores de las celdas.
	 * @return TRUE si hay alguna repetición sobre su panel. FALSE caso contrario.
	 */
 	protected boolean repetidoPanel(int fila, int columna, int[][] m) {
 		boolean toReturn = false;
 		int f = fila - fila%3; //Con este indice recorro las filas del panel.
		int c = columna - columna%3; //Con este indice recorro las columnas del panel.
		int valor = m[fila][columna];
		for(int i=f; i<f+3 && !toReturn && valor!=0; i++)
			for(int j=c; j<c+3 && !toReturn; j++)
				if(i!=fila && j!=columna) //Descarto el indice que deseo chequear.
					toReturn = m[i][j]==valor ? true : toReturn;
		return toReturn;
 	}
 	
 	public void rendirse() {
 		for(int i=0; i<cantFilas; i++)
 			for(int j=0; j<cantColumnas; j++)
 				mapa[i][j].setValor(estadoInicial[i][j]);
 	}
}
