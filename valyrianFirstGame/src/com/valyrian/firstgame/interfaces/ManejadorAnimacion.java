package com.valyrian.firstgame.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ManejadorAnimacion {

	void cargarAnimacion(Texture t);
	
	TextureRegion getAnimacion(float deltaTime,int opc);
}
