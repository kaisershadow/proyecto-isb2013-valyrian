package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionAvispa implements ManejadorAnimacion {
	
	private Animation animacion;
	private float stateTime;
	
	public AnimacionAvispa(Texture textura){
		cargarAnimacion(textura);
		stateTime = 0;
	}
	
	
	@Override
	public void cargarAnimacion(Texture t) {
		TextureRegion[] avispa = TextureRegion.split(t, 32, 32)[0];
		animacion = new Animation(1/11f,avispa[0],avispa[1],avispa[2],avispa[3],avispa[4],avispa[5],
									avispa[6],avispa[7],avispa[8],avispa[9],avispa[10]);
		animacion.setPlayMode(Animation.LOOP);		
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime, int opc) {
		stateTime+=deltaTime;
		return animacion.getKeyFrame(stateTime);
	}

}
