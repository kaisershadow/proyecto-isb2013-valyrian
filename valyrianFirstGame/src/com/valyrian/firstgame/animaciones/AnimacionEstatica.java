package com.valyrian.firstgame.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class AnimacionEstatica implements ManejadorAnimacion {

	private TextureRegion textura;
	
	public AnimacionEstatica(Texture t){
		cargarAnimacion(t);
	}
	@Override
	public void cargarAnimacion(Texture t) {
//		TextureRegion[] texture = TextureRegion.split(t, ancho, alto)[0];
		textura = new TextureRegion(t);
	}

	@Override
	public TextureRegion getAnimacion(float deltaTime, int opc) {
		return textura;
	}

}
