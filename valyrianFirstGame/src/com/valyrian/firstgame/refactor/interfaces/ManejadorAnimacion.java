package com.valyrian.firstgame.refactor.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ManejadorAnimacion {

	void cargarAnimacion(Texture t);
	
	TextureRegion getAtacar(float delta);
	TextureRegion getMover(float delta);
	TextureRegion getSaltar(float delta);
	TextureRegion getQuieto(float delta);
}
