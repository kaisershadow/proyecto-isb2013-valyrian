package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class SerVivo extends Entidad {
	
	public final int MAXVIDA;
	public float MAXVELOCIDAD;
	protected int vidaActual;
	protected int densidad;
	
	
	public SerVivo(){
		this.alto=0;
		this.ancho=0;
		this.MAXVELOCIDAD=0;
		this.MAXVIDA=100;
		this.posicion =new Vector2();
		this.vidaActual=0;
		this.cuerpo =null;
	}
	
	public SerVivo(int ancho, int alto, int vidaMax, float velMax,Vector2 pos,Body cuerpo){
		MAXVIDA =vidaMax;
		this.alto=alto;
		this.ancho=ancho;
		this.MAXVELOCIDAD=velMax;
		this.posicion =pos;
		this.vidaActual=vidaMax;
		this.cuerpo =cuerpo;
		cuerpo.setUserData(this);
	}
	
	public int getAncho() {
		return ancho;
	}

	public void setAncho(int aNCHO) {
		this.ancho = aNCHO;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int aLTO) {
		this.alto = aLTO;
	}

	public Vector2 getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector2 posicion) {
		this.posicion = posicion;
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
