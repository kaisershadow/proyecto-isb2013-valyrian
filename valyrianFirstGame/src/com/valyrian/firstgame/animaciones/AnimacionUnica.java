package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionUnica implements ManejadorAnimacion{

	private Animation animacion;
	float stateTime;
	
	
	public AnimacionUnica(Texture t, int ancho, int alto, float time){
		this.cargarAnimacion(t, ancho, alto, time);
		stateTime =0;
	}
	
	@Override
	public void cargarAnimacion(Texture t, int ancho, int alto, float time) {
		TextureRegion[] spriteSheet = TextureRegion.split(t, ancho, alto)[0];
		animacion = new Animation(time,spriteSheet);
		animacion.setPlayMode(Animation.LOOP);
		
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime) {
		stateTime+=deltaTime;
		return animacion.getKeyFrame(stateTime);
	}


}
