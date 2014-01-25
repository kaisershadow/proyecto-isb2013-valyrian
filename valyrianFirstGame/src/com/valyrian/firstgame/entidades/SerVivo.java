package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.math.Vector2;

public abstract class SerVivo {
	
	protected final int MAX_VIDA;
	protected float MAX_VELOCIDAD;
	protected int vida;
	protected float velocidad;
	protected Vector2 posicion = new Vector2();
	protected int ANCHO, ALTO;
	
	
	public SerVivo(int maxvida){
		MAX_VIDA=maxvida;
	}
	public int getANCHO() {
		return ANCHO;
	}

	public void setANCHO(int aNCHO) {
		ANCHO = aNCHO;
	}

	public int getALTO() {
		return ALTO;
	}

	public void setALTO(int aLTO) {
		ALTO = aLTO;
	}

	public Vector2 getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector2 posicion) {
		this.posicion = posicion;
	}

	public int getVida() {
		return vida;
	}
	
	public void setVida(int vida) {
		this.vida = vida;
	}
	public float getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}
	//@brief Metodo para reducir la vida 
	int cambiarVida(int value){
		
		vida=vida+value;
		if(vida>MAX_VIDA)
			vida=MAX_VIDA;
		if(vida<0)
			vida=0;				
		return vida;
	}
	
	boolean estaMuerto(){
		return(vida<=0);
	}
	
	
	
	
	
	
	
	

}
