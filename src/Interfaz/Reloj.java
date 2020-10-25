package Interfaz;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Reloj extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected Timer timer;
	protected int segundos, minutos, horas;
	protected ImageIcon[] imagenes;
	protected JLabel horasDecena, horasUnidad, minutosDecena, minutosUnidad, segundosDecena, segundosUnidad;
	
	public Reloj() {
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
		JLabel dp1 = new JLabel();
		JLabel dp2 = new JLabel();
		horasDecena = new JLabel();
		horasUnidad = new JLabel();
		minutosDecena = new JLabel();
		minutosUnidad = new JLabel();
		segundosDecena = new JLabel();
		segundosUnidad = new JLabel();
		imagenes = setImagenes();
		crearLabels(dp1, dp2);
		segundos = minutos = horas = 0;		
		timer = new Timer (1000, (ActionListener) new ActionListener ()
		{
			public void actionPerformed(ActionEvent e) {
				if(segundos<59) {
					segundos++;
				}	
				else if(minutos<59){
					minutos++;
					segundos=0;
					
				} else {
					horas++;
					minutos = 0;
					segundos = 0;
				}
				segundosUnidad.setIcon(imagenes[segundos%10]);
				reDimensionar(segundosUnidad, imagenes[segundos%10]);
				segundosDecena.setIcon(imagenes[segundos/10]);
				reDimensionar(segundosDecena, imagenes[segundos/10]);
				minutosUnidad.setIcon(imagenes[minutos%10]);
				reDimensionar(minutosUnidad, imagenes[minutos%10]);
				minutosDecena.setIcon(imagenes[minutos/10]);
				reDimensionar(minutosDecena, imagenes[minutos/10]);
				horasUnidad.setIcon(imagenes[horas%10]);
				reDimensionar(horasUnidad, imagenes[horas%10]);
				horasDecena.setIcon(imagenes[horas/10]);
				reDimensionar(horasDecena, imagenes[horas/10]);
			}
		});
	}
	
	/**
	 * Agrega los diferentes labels al panel y se definen sus vistas gráficas.
	 * @param dp1 - Label al que se le setea una imagen.
	 * @param dp2 - Label al que se le setea una imagen.
	 */
	public void crearLabels(JLabel dp1, JLabel dp2) {	
		this.setLayout(new GridLayout(0, 8, 0, 0));
		
		horasDecena.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		this.add(horasDecena);
		
		horasUnidad.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(horasUnidad);
		
		dp1.setIcon(imagenes[10]);
		dp1.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(dp1);
		
		minutosDecena.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(minutosDecena);
		
		minutosUnidad.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(minutosUnidad);
		
		dp2.setIcon(imagenes[10]);
		dp2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(dp2);
		
		segundosDecena.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(segundosDecena);
		
		segundosUnidad.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(segundosUnidad);
	}
	
	/**
	 * Inicia el timer.
	 */
	public void start() {
		timer.start();
	}
	
	/**
	 * Frena el timer.
	 */
	public void stop() {
		timer.stop();
	}
	
	/**
	 * Reinicia el timer.
	 */
	public void restart() {
		segundos = minutos = horas = 0;
		timer.restart();
	}
	
	public int getSegundos() {
		return segundos;
	}
	
	public int getMinutos() {
		return minutos;
	}
	
	public int getHoras() {
		return horas;
	}
	
	public ImageIcon[] setImagenes() {		
		ImageIcon[] toReturn = new ImageIcon[11];
		for(int i=0; i<11; i++)
			toReturn[i] = new ImageIcon("/Imagenes/r"+i+".png");
		return toReturn;
	}
	
	public ImageIcon[] getImagenes() {
		return imagenes;
	}
	
	protected void reDimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
}
