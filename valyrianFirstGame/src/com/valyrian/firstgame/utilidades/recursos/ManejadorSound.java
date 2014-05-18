package com.valyrian.firstgame.utilidades.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ManejadorSound extends ResourceManager<Sound> {

	
	public ManejadorSound(){
		super();
	}
	@Override
	public void cargarRecurso(String path, String key) {
		Sound s = Gdx.audio.newSound(Gdx.files.internal(path));
		this.recursos.put(key, s);
	}

	@Override
	public void disposeRecurso(String key) {
		Sound s = recursos.remove(key);
		if(s !=null)
			s.dispose();
	}

}