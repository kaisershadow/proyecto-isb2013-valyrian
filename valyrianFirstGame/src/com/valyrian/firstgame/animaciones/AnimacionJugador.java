package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionJugador implements ManejadorAnimacion{

	private Animation ataca,quieto,salto, moviendo;
	private float stateTime;
	private Jugador jugador;
	
	public AnimacionJugador(Texture textura, int ancho, int alto, float time){
		cargarAnimacion(textura,ancho,alto,time);
		stateTime =0;
	}
	
	@Override
	public void cargarAnimacion(Texture t, int ancho, int alto, float time){
		TextureRegion[] jugador = TextureRegion.split(t, ancho, alto)[0];
		
		quieto = new Animation(0,jugador[2]);
		
		moviendo= new Animation(time, jugador[2],jugador[3],jugador[4],jugador[5],jugador[6],jugador[7]);
		moviendo.setPlayMode(Animation.LOOP);
		
		ataca =new Animation(time,jugador[1],jugador[0]);
		ataca.setPlayMode(Animation.NORMAL);
		
		salto = new Animation(0,jugador[10]);
		salto.setPlayMode(Animation.LOOP);
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime) {
		stateTime+=deltaTime;
		TextureRegion frame = null;
		if(jugador.estado == ESTADO_ACTUAL.Quieto)
				frame = quieto.getKeyFrame(stateTime);
		else if(jugador.estado == ESTADO_ACTUAL.Moviendose)
				frame = moviendo.getKeyFrame(stateTime);
		else if(jugador.estado == ESTADO_ACTUAL.Saltando)
				frame = salto.getKeyFrame(stateTime);
		else
				frame = ataca.getKeyFrame(stateTime);
		
		return frame;
	}
	
	public void setJugador(Jugador j){this.jugador = j;}
}
