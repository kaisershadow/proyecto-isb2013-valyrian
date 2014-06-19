package com.valyrian.firstgame.inteligencia;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionUnica;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.Proyectil;
import com.valyrian.firstgame.pantallas.PantallaNivel;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;
import com.valyrian.firstgame.interfaces.ManejadorInteligencia;



public class InteligenciaRana implements ManejadorInteligencia {
	private Enemigo enemigo;
	private float limite;
	private float stateTime;
	private boolean dispIzq;
	private boolean dispDer;
	private ManejadorAnimacion mab;
	

	public InteligenciaRana(float lim,Enemigo e) {
		enemigo = e;
		this.limite = lim;
		dispIzq=false;
		dispDer=false;
		Texture tx = Quetzal.getManejaRecursos().get("personajes/dardo.png", Texture.class);
		mab = new AnimacionUnica(tx,7,3,0);
		stateTime =0;
	}
	
	@Override
	public void Actualizar(float deltaTime) {
		this.stateTime+=deltaTime;
		if(this.stateTime>this.limite)
			this.stateTime=0;
		if(this.stateTime>this.limite/2 && !this.dispDer){
			this.enemigo.mirandoDerecha = false;
			this.enemigo.getDireccion().x=-1;
			this.Disparar();
			this.dispDer=true;
			this.dispIzq=false;
		}
		if(this.stateTime<this.limite/2 && !this.dispIzq){
			this.enemigo.mirandoDerecha = true;
			this.enemigo.getDireccion().x=1;
			this.Disparar();
			this.dispDer=false;
			this.dispIzq=true;
		}
	}
	
	
	public void Disparar(){
		
		World mundo = this.enemigo.getCuerpo().getWorld();
		Proyectil bala;
		Vector2 posAux = new Vector2();
		Vector2 velAux = new Vector2();
		posAux.x =this.enemigo.getCuerpo().getPosition().x+ this.enemigo.getDireccion().x*((this.enemigo.getAncho()/2+4)/PIXELSTOMETERS);
		posAux.y =this.enemigo.getCuerpo().getPosition().y;
		velAux.x = this.enemigo.getDireccion().x*5;
		velAux.y = 0;
		bala = new Proyectil(7, 3, posAux.x, posAux.y, velAux, mundo, mab, this.enemigo.getDanio());
		bala.getCuerpo().setGravityScale(0.1f);
		Filter filter = bala.getCuerpo().getFixtureList().first().getFilterData();
		filter.maskBits |=BITS_JUGADOR;
		bala.getCuerpo().getFixtureList().first().setFilterData(filter);
		PantallaNivel.getEntidades().add(bala);
	}
}