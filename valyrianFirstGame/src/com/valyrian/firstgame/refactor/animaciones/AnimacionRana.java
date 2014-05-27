package com.valyrian.firstgame.refactor.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.refactor.interfaces.ManejadorAnimacion;

public class AnimacionRana implements ManejadorAnimacion{

	Animation ataca,quieto,salto, moviendo;
	float stateTime = 0;
	
	public AnimacionRana(Texture textura){
		cargarAnimacion(textura);
	}
	
	@Override
	public void cargarAnimacion(Texture textura) {
		TextureRegion[] rana = TextureRegion.split(textura, 32, 32)[0];
		
		quieto= new Animation(0, rana[0]);
		
		moviendo = new Animation(1/5f,rana[0],rana[1],rana[2],rana[3],rana[4]);
		moviendo.setPlayMode(Animation.LOOP);

		ataca =new Animation(1/3f,rana[2],rana[1]);
		ataca.setPlayMode(Animation.NORMAL);
	
		salto = new Animation(0,rana[0]);
		
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
