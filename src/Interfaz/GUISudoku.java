package Interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.Celda;
import Logica.Juego;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISudoku extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel contentPane;
	protected Juego logica;
	protected Reloj panelCronometro;
	protected JButton[][] tablero;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISudoku frame = new GUISudoku();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUISudoku() {
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTablero = new JPanel();
		panelTablero.setBounds(10, 11, 441, 441);
		contentPane.add(panelTablero);
		panelTablero.setLayout(new GridLayout(9, 9, 0, 0));
				
		panelCronometro = new Reloj();
		panelCronometro.setBounds(461, 11, 290, 100);
		contentPane.add(panelCronometro);
		
		JLabel labelAviso = new JLabel("");
		labelAviso.setHorizontalAlignment(SwingConstants.CENTER);
		labelAviso.setEnabled(false);
		labelAviso.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelAviso.setBounds(461, 122, 290, 159);
		contentPane.add(labelAviso);
		
		crear(panelTablero, labelAviso);
				
		

	
		
		JButton botonFrenar = new JButton("FRENAR");
		botonFrenar.setActionCommand("f");
		botonFrenar.setEnabled(false);
		botonFrenar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component boton = e.getComponent();
				String codigo = ((JButton) boton).getActionCommand();
				if(codigo=="f") {
					botonFrenar.setText("REANUDAR");
					panelCronometro.stop();
					panelTablero.setVisible(false);
					labelAviso.setText("JUEGO EN PAUSA. PULSE REANUDAR");
					botonFrenar.setActionCommand("r");
			
				}
				if(codigo=="r") {
					botonFrenar.setText("FRENAR");
					panelCronometro.start();
					panelTablero.setVisible(true);
					labelAviso.setText("JUEGO EN CURSO");
					botonFrenar.setActionCommand("f");
				
				}
				
			}
		});
		botonFrenar.setBackground(Color.BLUE);
		botonFrenar.setBounds(461, 406, 290, 46);
		botonFrenar.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.black));
		contentPane.add(botonFrenar);
	
		JButton botonIniciar = new JButton("INICIAR");
		botonIniciar.setActionCommand("inicio");
		botonIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(botonIniciar.isEnabled()) {
					Component boton = e.getComponent();
					String codigo = ((JButton) boton).getActionCommand();
					if(codigo=="inicio") {
						iniciar();
						panelCronometro.setEnabled(true);
						panelCronometro.start();
						labelAviso.setText("JUEGO EN CURSO");
						botonIniciar.setActionCommand("r");
						botonIniciar.setText("RENDIRSE");
						botonFrenar.setEnabled(true);

					}
					if(codigo=="r") {
						panelTablero.setVisible(true);
						rendirse(labelAviso);
					}
				}
			}
		});
		botonIniciar.setBounds(461, 292, 290, 46);
		botonIniciar.setBackground(new Color(255,255,255));
		botonIniciar.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.black));
		contentPane.add(botonIniciar);
	}
	
	/**
	 * Crea la vista gráfica de la ventana del juego.
	 * @param panelTablero - Panel contenedor de grilla de juego.
	 * @param labelAviso - Label que avisa el estado del juego.
	 */
	public void crear(JPanel panelTablero, JLabel labelAviso) {
		logica = new Juego();
		if(logica.getMapa()!=null) {
			String s1 = "2) Una fila no puede contener numeros repetidos";
			String s2 = "2) Una columna no puede contener numeros repetidos";
			String s3 = "4) Un cuadrado no puede contener objetos repetidos";
			String s4 = "5) Gana cuando completa sin errores todo el panel";
			String s5 = "Para jugar interactúe con los botones";
			String s ="\n"+s1+"\n"+s2+"\n"+s3+"\n"+s4+"\n"+s5+"\n";
			JOptionPane.showConfirmDialog(new JFrame(),"Reglas del juego: \n"+s+"\n SUERTE","Inicia el juego",JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			tablero(panelTablero, labelAviso);
		}	
		else {
			labelAviso.setEnabled(true);
			labelAviso.setText("OCURRIÓ UN ERROR");
			JOptionPane.showConfirmDialog(new JFrame(),"SOLUCION INCORRECTA","ERROR",JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Crea y organiza la vista gráfica de la grilla del juego.
	 * @param panelTablero - Panel contenedor de grilla de juego.
	 * @param labelAviso - Label que avisa el estado del juego.
	 */
	protected void tablero(JPanel panelTablero, JLabel labelAviso) {
		tablero = new JButton[logica.getCantFilas()][logica.getCantColumnas()];
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				crearBoton(f, c);
				tablero[f][c].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Component boton = e.getComponent();
						String codigo = ((JButton) boton).getActionCommand();
						logicaBoton(codigo, labelAviso);
					}
				});
				panelTablero.add(tablero[f][c]);
			}
		labelAviso.setEnabled(true);
		labelAviso.setText("PULSE INICIO PARA COMENZAR");
	}
	
	/**
	 * Organiza gráficamente cada uno de los botones de la grilla de juego.
	 * @param f - Coordenada i de la matriz donde está el botón. f por fila.
	 * @param c - Coordenada j de la matriz donde está el botón. c por columna.
	 */
	protected void crearBoton(int f, int c) {
		tablero[f][c] = new JButton();
		tablero[f][c].setActionCommand(f+","+c);
		tablero[f][c].setEnabled(false);
		tablero[f][c].setBackground(Color.white);
		if(f==0 || f==3 || f==6) {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
		}
		else if(f==8) {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
		}
		else {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		}
	}
	
	/**
	 * Crea y asigna la parte lógica de los botones de la grilla de juego.
	 * @param codigo - Índice asociado a la ubicación del botón en el tablero.
	 * @param labelAviso - Label que avisa el estado del juego.
	 */
	protected void logicaBoton(String codigo, JLabel labelAviso) {
		String[] fyc= codigo.split(",");
        int f=Integer.parseInt(fyc[0]);
        int c=Integer.parseInt(fyc[1]);
        Celda celda = logica.getCelda(f,c);
        String[] repetidos;
        boolean ganado;
        if(tablero[f][c].isEnabled()) //Permite actualizar los valores clickeados únicamente de los botones habilitados.
        	celda.actualizar();
        logica.setVerificador(celda.getValor());
        tablero[f][c].setIcon(celda.getEntidadGrafica().getImagen());
        reDimensionar(tablero[f][c], celda.getEntidadGrafica().getImagen());
        logica.setErrores(codigo);
        repetidos = logica.getErrores();
        pintar(repetidos);
        if(repetidos!=null) 
        	labelAviso.setText("Hay uno o varios errores en el tablero");
        else {
        	labelAviso.setText("Juego Correcto sin errores");
        	if(logica.getVerificador()==(logica.getCantFilas()*logica.getCantColumnas())) {
        		ganado = logica.comprobar();
        		if(ganado) {
        			ganar(labelAviso);
        		}	
        	}	
        }	
	}

	/**
	 * Crea e inicia el juego mostrando la grilla de juego.
	 */
	public void iniciar() {
		Celda celda;
		logica.crearJuego();
		panelCronometro=new Reloj();
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				celda = logica.getCelda(f, c);
				if(celda.getValor()==0)  
					tablero[f][c].setEnabled(true); 
				tablero[f][c].setBackground(new Color(255,255,255));
				tablero[f][c].setIcon(celda.getEntidadGrafica().getImagen());
				reDimensionar(tablero[f][c], celda.getEntidadGrafica().getImagen());
			}
	}

		
	/**
	 * Colorea los botones cuyos índices son parametrizados.
	 * @param repetidas
	 */
	public void pintar(String[] repetidas) {
		pintar(Color.WHITE);
		if(repetidas!=null) 
			advertencia(repetidas);
	}
	
	/**
	 * Colorea toda la grilla de juego.
	 * @param color - Color con el que se colorea la grilla de juego.
	 */
	protected void pintar(Color color) {
		for(int f=0; f<logica.getCantFilas(); f++)
			for(int c=0; c<logica.getCantColumnas(); c++)
				tablero[f][c].setBackground(color);	
	}
	
	/**
	 * Colorea de color rojo aquellos botones de la grilla de juego que poseen los índices parametrizados.
	 * @param repetidas - Índices donde se desea colorear de rojo.
	 */
	protected void advertencia(String[] repetidas) {
		String[] fyc;
		int fila, columna;
		for(int i=0; i<repetidas.length && repetidas[i]!=null; i++) {
			fyc = repetidas[i].split(",");
			fila = Integer.parseInt(fyc[0]);
			columna = Integer.parseInt(fyc[1]);
			tablero[fila][columna].setBackground(Color.RED);	
		}
	}
	
	/**
	 * Muestra gráficamente que el usuario ganó y bloquea todas las componentes de la ventana.
	 * @param labelAviso - Label que avisa el estado del juego.
	 */
	protected void ganar(JLabel labelAviso) {
		int h = panelCronometro.getHoras();
		int m = panelCronometro.getMinutos();
		int s = panelCronometro.getSegundos();
		String tiempo = h+":"+m+":"+s;
		labelAviso.setText("JUEGO GANADO. FELICIDADES");
		panelCronometro.stop();
		pintar(Color.GREEN);
		Component[] componentes = contentPane.getComponents();
		for(int i=0; i<componentes.length; i++)
			componentes[i].setEnabled(false);
		JOptionPane.showMessageDialog(new JFrame(),"SOLUCION CORRECTA. FELICIDADES. \n TIEMPO DE JUEGO: "+tiempo,"Dialog",JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	/**
	 * Finaliza la ejecución del juego.
	 * @param labelAviso - Label que avisa el estado del juego.
	 */
	protected void rendirse(JLabel labelAviso) {
		int h = panelCronometro.getHoras();
		int m = panelCronometro.getMinutos();
		int s = panelCronometro.getSegundos();
		Celda celda;
		String tiempo = h+":"+m+":"+s;
		labelAviso.setText("SE RINDIÓ. MEJOR SUERTE LA PRÓXIMA");
		panelCronometro.stop();
		logica.rendirse();
		pintar(Color.BLUE);
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				celda = logica.getCelda(f, c);
				tablero[f][c].setEnabled(false);
				tablero[f][c].setIcon(celda.getEntidadGrafica().getImagen());
				reDimensionar(tablero[f][c], celda.getEntidadGrafica().getImagen());
			}
		Component[] componentes = contentPane.getComponents();
		for(int i=0; i<componentes.length; i++)
			componentes[i].setEnabled(false);
		JOptionPane.showMessageDialog(new JFrame(),"TIEMPO DE JUEGO: "+tiempo,"Dialog",JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}	
	
	protected void reDimensionar(JButton boton, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(boton.getWidth(), boton.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			boton.repaint();
		}
	}
}