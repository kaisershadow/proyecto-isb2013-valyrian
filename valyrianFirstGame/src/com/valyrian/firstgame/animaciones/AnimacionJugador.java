package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionJugador implements ManejadorAnimacion{

	Animation ataca,quieto,salto, moviendo;
	private float stateTime;
	private static final int Quieto =1;
	private static final int Caminando = 2;
	private static final int Saltando = 3;
	private static final int Atacando = 4;
	
	public AnimacionJugador(Texture textura){
		cargarAnimacion(textura);
		stateTime =0;
	}
	
	
	@Override
	public void cargarAnimacion(Texture textura) {
		TextureRegion[] jugador = TextureRegion.split(textura, 32, 64)[0];
		
		quieto = new Animation(0,jugador[2]);
		
		moviendo= new Animation(1/12f, jugador[2],jugador[3],jugador[4],jugador[5],jugador[6],jugador[7]);
		moviendo.setPlayMode(Animation.LOOP);
		
		ataca =new Animation(1/12f,jugador[1],jugador[0]);
		ataca.setPlayMode(Animation.NORMAL);
		
		salto = new Animation(0,jugador[10]);
		salto.setPlayMode(Animation.LOOP);
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime,int opc) {
		stateTime+=deltaTime;
		TextureRegion frame = null;
		switch (opc) {
			case Quieto:
				frame = quieto.getKeyFrame(stateTime);
				break;
			case Caminando:
				frame = moviendo.getKeyFrame(stateTime);
				break;
			case Saltando:
				frame = salto.getKeyFrame(stateTime);
				break;
			case Atacando:
				frame = ataca.getKeyFrame(stateTime);
				break;
		}	
		return frame;
	}
}
