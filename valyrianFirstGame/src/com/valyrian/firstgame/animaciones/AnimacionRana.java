package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionRana implements ManejadorAnimacion{

	Animation animacion;
	float stateTime;
	
	public AnimacionRana(Texture textura){
		cargarAnimacion(textura);
		stateTime = 0;
	}
	
	@Override
	public void cargarAnimacion(Texture t) {
		TextureRegion[] rana = TextureRegion.split(t, 32, 32)[0];
		animacion = new Animation(1/5f,rana[0],rana[1],rana[2],rana[3],rana[4]);
		animacion.setPlayMode(Animation.LOOP);		
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime,int opc){
		stateTime+=deltaTime;
		return animacion.getKeyFrame(stateTime);	
	}
}
