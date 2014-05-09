package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.math.Vector2;

public abstract class SerVivo extends Entidad {
	
	public final int MAXVIDA;
	protected Vector2 velocidad;
	protected int vidaActual;	
	

	public SerVivo(){
		this.alto=0;
		this.ancho=0;
		this.posicion =new Vector2();
		this.cuerpo =null;
		
		this.velocidad =new Vector2();
		this.vidaActual=10;
		this.MAXVIDA=10;
	}
	
	public SerVivo(float ancho, float alto, int vidaMax, Vector2 velMax,Vector2 pos){
		MAXVIDA =vidaMax;
		this.alto=alto;
		this.ancho=ancho;
		this.velocidad=velMax;
		this.posicion =pos;
		this.vidaActual=vidaMax;
		this.cuerpo =null;
	}
	
	public Vector2 getVelocidad() {
		return velocidad;
	}
	
	public void setVelocidad(Vector2 velocidad) {
		this.velocidad = velocidad;
	}

	public int getVidaActual() {
		return vidaActual;
	}
	
	public void setVidaActual(int vida) {
		this.vidaActual = vida;
	}

	//@brief Metodo para aumentar o reducir la vida en @value unidades
	int cambiarVidaActual(int value){
		
		vidaActual+=value;
		if(vidaActual>MAXVIDA)
			vidaActual=MAXVIDA;
		else if(vidaActual<0)
			vidaActual=0;				
		return vidaActual;
	}
	
	boolean estaMuerto(){
		return(vidaActual<=0);
	}
}
