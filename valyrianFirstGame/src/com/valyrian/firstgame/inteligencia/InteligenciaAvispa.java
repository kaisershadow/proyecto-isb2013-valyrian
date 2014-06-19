package com.valyrian.firstgame.inteligencia;

import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.interfaces.ManejadorInteligencia;

public class InteligenciaAvispa implements ManejadorInteligencia{
	private Enemigo enemigo;
	private float stateTime;
	private float limite;
	
	public InteligenciaAvispa(float lim, Enemigo e){
		this.enemigo = e;
		stateTime=0;
		limite = lim;
	}
	@Override
	public void Actualizar(float deltaTime) {
		stateTime+=deltaTime;
		if(stateTime>limite/2){
			stateTime =0;
			enemigo.getDireccion().x*=-1;
			if(enemigo.mirandoDerecha)
				enemigo.mirandoDerecha = false;
			else
				enemigo.mirandoDerecha = true;
//			if(enemigo.mirandoDerecha)
//				enemigo.getDireccion().x=1;
//			else
//				enemigo.getDireccion().x=-1;
			enemigo.getCuerpo().setLinearVelocity(enemigo.getDireccion().x*enemigo.getVelocidad().x,0);
		}
	}

}
