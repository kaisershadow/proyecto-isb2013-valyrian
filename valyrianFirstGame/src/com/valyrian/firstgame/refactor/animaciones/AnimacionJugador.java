package com.valyrian.firstgame.refactor.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.refactor.interfaces.ManejadorAnimacion;

public class AnimacionJugador implements ManejadorAnimacion{

	Animation ataca,quieto,salto, moviendo;
	private float stateTime = 0;
	
	public AnimacionJugador(Texture textura){
		cargarAnimacion(textura);
	}
	
	
	@Override
	public void cargarAnimacion(Texture textura) {
		TextureRegion[] jugador = TextureRegion.split(textura, 32, 64)[0];
		
		quieto = new Animation(0,jugador[2]);
		
		moviendo= new Animation(1/7f, jugador[2],jugador[3],jugador[4],jugador[5],jugador[6],jugador[7]);
		moviendo.setPlayMode(Animation.LOOP);
		
		ataca =new Animation(1/12f,jugador[1],jugador[0]);
		ataca.setPlayMode(Animation.NORMAL);
		
		salto = new Animation(0,jugador[10]);
		salto.setPlayMode(Animation.LOOP);
		
	}

	@Override
	public TextureRegion getAtacar(float delta) {
		stateTime+=delta;
		return ataca.getKeyFrame(stateTime);
	}

	@Override
	public TextureRegion getMover(float delta) {
		stateTime+=delta;
		return moviendo.getKeyFrame(stateTime);
	}

	@Override
	public TextureRegion getSaltar(float delta) {
		stateTime+=delta;
		return salto.getKeyFrame(stateTime);
	}

	@Override
	public TextureRegion getQuieto(float delta) {
		stateTime+=delta;
		return quieto.getKeyFrame(stateTime);
	}

}
