package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionMoneda implements ManejadorAnimacion {

	private Animation animacion;
	private float stateTime;
	
	public AnimacionMoneda(Texture textura){
		cargarAnimacion(textura);
		stateTime = 0;
	}
	
	
	@Override
	public void cargarAnimacion(Texture t) {
		TextureRegion[] moneda = TextureRegion.split(t, 168, 168)[0];
		animacion = new Animation(1/10f,moneda[0],moneda[1],moneda[2]);
		animacion.setPlayMode(Animation.LOOP);		
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime, int opc) {
		stateTime+=deltaTime;
		return animacion.getKeyFrame(stateTime);
	}

}
