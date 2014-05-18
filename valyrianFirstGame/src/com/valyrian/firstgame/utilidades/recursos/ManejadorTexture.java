package com.valyrian.firstgame.utilidades.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ManejadorTexture extends ResourceManager<Texture> {

	
	public ManejadorTexture(){
		super();
	}
	@Override
	public void cargarRecurso(String path, String key) {
		Texture t = new Texture(Gdx.files.internal(path));
		this.recursos.put(key, t);
	}

	@Override
	public void disposeRecurso(String key) {
		Texture t = recursos.remove(key);
		if(t !=null)
			t.dispose();
	}

}