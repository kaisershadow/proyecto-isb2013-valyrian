package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entidad {
	
	//Atributos comunes de todas las entidades
	protected Vector2 posicion;
	protected float alto;
	protected float ancho;
	protected Body cuerpo;
	
	public Vector2 getPosicion() {
		return posicion;
	}
	public void setPosicion(Vector2 pos) {
		this.posicion = pos;
	}
	public float getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public float getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public Body getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(Body cuerpo) {
		this.cuerpo = cuerpo;
	}
}