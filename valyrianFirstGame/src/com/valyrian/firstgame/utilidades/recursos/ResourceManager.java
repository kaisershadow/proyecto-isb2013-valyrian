package com.valyrian.firstgame.utilidades.recursos;

import java.util.HashMap;


public abstract class ResourceManager<T> {

	//private static ManejadorRecursos instancia;
	protected HashMap<String, T> recursos;
	
	protected ResourceManager(){
	 recursos = new HashMap<String, T>();
	}
	
	public abstract void cargarRecurso(String path,String key);
	public abstract void disposeRecurso(String key);
	
	public T getRecurso(String key){
		return recursos.get(key);
	}
	public void disposeAll(){
		for(String key : recursos.keySet()){
			disposeRecurso(key);
		}
		recursos.clear();
	}
}