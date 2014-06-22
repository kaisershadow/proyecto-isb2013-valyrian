package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public abstract class EntidadDibujable {

	//Atributos comunes de todas las entidades
	protected Vector2 velocidad;
	protected float alto;
	protected float ancho;
	protected Body cuerpo;
	protected ManejadorAnimacion manAnim;
	//Sentido de la velocidad
	protected Vector2 direccion;
	
	protected abstract void crearCuerpo(World mundo,float ancho,float alto,float posX,float posY);

	public abstract void render(float deltaTime, SpriteBatch batch);
	
	//Constructor
	protected EntidadDibujable(float ancho,float alto,Vector2 vel, float posX,float posY, World m,ManejadorAnimacion ma){
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = vel;
		this.manAnim = ma;
		direccion = new Vector2(1,0);
		crearCuerpo(m, ancho, alto,posX,posY);
	}
	
	public void setAnimacion(ManejadorAnimacion ma){ this.manAnim = ma; }
	
	public float getAlto() { return alto; }
	
	public void setAlto(int alto) { this.alto = alto; }
	
	public float getAncho() {return ancho; }
	
	public void setAncho(int ancho) { this.ancho = ancho; }
	
	public Body getCuerpo() { return cuerpo; }
	
	public void setCuerpo(Body cuerpo) { this.cuerpo = cuerpo; }
	
	public Vector2 getVelocidad() { return velocidad; }
	
	public void setVelocidad(Vector2 vel) { this.velocidad = vel; }
	
	public Vector2 getDireccion() { return direccion; }
	
//	public void cambiarDireccion(boolean derechaX,boolean derechaY){
//		if(derechaX)
//			this.direccion.x = 1;
//		else
//			this.direccion.x = -1;
//		
//		if(derechaY)
//			this.direccion.y = 1;
//		else
//			this.direccion.y = -1;
//	}	
}