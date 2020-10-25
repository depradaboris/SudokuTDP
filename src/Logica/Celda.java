package Logica;

import Interfaz.EntidadGrafica;

public class Celda {
	protected Integer valor;
	protected EntidadGrafica entidadGrafica;
	
	public Celda() {
		valor = null; //Si está null es que "no hay nada"
		entidadGrafica = new EntidadGrafica();
	}
	
	/**
	 * Actualiza el valor de la celda haciendo que oscile en [0..9].
	 */
	public void actualizar() {
		if (valor != null && valor + 1 < getCantElementos()) 
			valor++;
		else 
			valor = 0;
		entidadGrafica.actualizar(valor);
	}
	
	public int getCantElementos() {
		return entidadGrafica.getImagenes().length;
	}
	
	public Integer getValor() {
		return valor; 
	}
	
	public void setValor(Integer valor) {
		if (valor!=null && valor < getCantElementos()) {
			this.valor = valor;
			entidadGrafica.actualizar(this.valor);
		}
		else 
			this.valor = null;
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return entidadGrafica;
	}
	
	public void setEntidadGrafica(EntidadGrafica entidadGrafica) {
		this.entidadGrafica = entidadGrafica;
	}
}