package com.valyrian.firstgame.entidades;

public abstract class serVivo {
	
	private int MAX_VIDA;
	private int vida;
	private float velocidad;
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
