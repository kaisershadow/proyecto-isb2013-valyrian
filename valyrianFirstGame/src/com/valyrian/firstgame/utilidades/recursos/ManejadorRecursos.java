package com.valyrian.firstgame.utilidades.recursos;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ManejadorRecursos {

	private static ManejadorRecursos instancia;
	
	private ManejadorSound soundManager;
	private ManejadorMusic musicManager;
	private ManejadorTexture textureManager;
	
	private ManejadorRecursos() {
		soundManager = new ManejadorSound();
		musicManager = new ManejadorMusic();
		textureManager = new ManejadorTexture();
	}
	
	public static synchronized ManejadorRecursos getInstancia(){
		if(instancia==null)
			instancia = new ManejadorRecursos();
		return instancia;		
	}
	
//	Manejador de Texturas
	public void cargarTexture(String path,String key){ textureManager.cargarRecurso(path, key); }
	
	public void disposeTexture(String key){	textureManager.disposeRecurso(key);	}
	
	public  Texture getTexture(String key){	return textureManager.getRecurso(key);	}

	public void disposeAllTextures(){ textureManager.disposeAll();}
//Manejador de Music
	public void cargarMusic(String path,String key){ musicManager.cargarRecurso(path, key); }
	
	public void disposeMusic(String key){	musicManager.disposeRecurso(key);	}
	
	public  Music getMusic(String key){	return musicManager.getRecurso(key);	}

	public void disposeAllMusic(){ musicManager.disposeAll();}
//Manejador de Sound
	
	public void cargarSound(String path,String key){ soundManager.cargarRecurso(path, key); }
	
	public void disposeSound(String key){	soundManager.disposeRecurso(key);	}
	
	public  Sound getSound(String key){	return soundManager.getRecurso(key);	}
	
	public void disposeAllSounds(){ soundManager.disposeAll();}


//Metodos generales
	public void disposeAllResources(){ 
		textureManager.disposeAll();
		soundManager.disposeAll();
		musicManager.disposeAll();
	}
}
