package com.valyrian.firstgame.refactor.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entidad {

	//Atributos comunes de todas las entidades
	protected Vector2 posicion;
	protected Vector2 velocidad;
	protected float alto;
	protected float ancho;
	protected Body cuerpo;
	protected Texture textura; 

	//Sentido de la velocidad
	protected Vector2 direccion;

	protected abstract void crearCuerpo(World mundo,Vector2 pos,float ancho,float alto);

	public void actualizarPosicion(){
		this.posicion = cuerpo.getPosition();
	}
	public abstract void render(float deltaTime, SpriteBatch batch);
	//Constructor
	Entidad(float ancho,float alto,Vector2 pos,Vector2 vel, World m,Texture t){
		this.ancho = ancho;
		this.alto = alto;
		this.posicion = pos;
		this.velocidad = vel;
		this.textura = t;
		direccion = new Vector2(1,0);
		crearCuerpo(m, pos, ancho, alto);
	}
	
	
	public Vector2 getPosicion() { return posicion; }
	
	public void setPosicion(Vector2 pos) { this.posicion = pos; }
	
	public float getAlto() { return alto; }
	
	public void setAlto(int alto) { this.alto = alto; }
	
	public float getAncho() {return ancho; }
	
	public void setAncho(int ancho) { this.ancho = ancho; }
	
	public Body getCuerpo() { return cuerpo; }
	
	public void setCuerpo(Body cuerpo) { this.cuerpo = cuerpo; }
	
	public Texture getTextura() { return textura; }
	
	public void setTextura(Texture textura) {this.textura = textura; }
	
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

