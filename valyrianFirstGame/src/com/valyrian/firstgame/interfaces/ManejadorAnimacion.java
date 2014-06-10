package com.valyrian.firstgame.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ManejadorAnimacion {

	void cargarAnimacion(Texture t,int ancho,int alto, float time);
	
//	TextureRegion getAnimacion(float deltaTime,int opc);
	TextureRegion getAnimacion(float deltaTime);
}
