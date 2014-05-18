package com.valyrian.firstgame.utilidades.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ManejadorMusic extends ResourceManager<Music> {

	
	public ManejadorMusic(){
		super();
	}
	@Override
	public void cargarRecurso(String path, String key) {
		Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
		this.recursos.put(key, m);
	}

	@Override
	public void disposeRecurso(String key) {
		Music m = recursos.remove(key);
		if(m !=null)
			m.dispose();
	}
}