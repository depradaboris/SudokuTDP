package Interfaz;

import javax.swing.ImageIcon;

public class EntidadGrafica {
	protected ImageIcon imagen;
	protected String[] imagenes;
	
	public EntidadGrafica() {
		imagen = new ImageIcon();
		imagenes = setImagenes();
	}
	
	public void actualizar(int indice) { //Indice oscila en [0,9]
		if (indice < imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			imagen.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon getImagen() {
		return imagen;
	}
	
	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}
	
	public String[] getImagenes() {
		return imagenes;
	}
	
	public String[] setImagenes() {
		String[] toReturn = new String[10];
		for(int i=0; i<=9; i++)
			toReturn[i] = "/Imagenes/"+i+".png";
		return toReturn;
	}
}