package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.physics.box2d.Body;

public class Entidad {
	//Atributos comunes de todas las entidades
	protected float posX;
	protected float posY;
	protected int alto;
	protected int ancho;
	Body cuerpo;
	
	
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public int getAncho() {
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
