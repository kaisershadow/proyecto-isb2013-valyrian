package com.valyrian.firstgame;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.valyrian.firstgame.pantallas.PantallaCargaNivel;
import com.valyrian.firstgame.pantallas.PantallaMenuInicio;
import com.valyrian.firstgame.pantallas.PantallaCreditos;
import com.valyrian.firstgame.pantallas.PantallaNivel;
import com.valyrian.firstgame.pantallas.PantallaPuntuaciones;
import com.valyrian.firstgame.pantallas.PantallaSeleccionNivel;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.secreto.PantallaNivelSecreto;


public class Quetzal extends Game {
	private static AssetManager manejadorRecursos;
	private static SpriteBatch sb;
	
	public Screen pantallaSplash;
	public Screen pantallaMenu;
	public Screen pantallaSeleccionNivel;
//	Screen pantallaOpciones;
	public Screen pantallaPuntuaciones;
	public Screen pantallaCreditos;
	public Screen pantallaNivel;
	public Screen pantallaCargaNivel;
	public Screen pantallaSecreto;
	//public Screen pantallaPrueba;	
	
	public Quetzal() {
		manejadorRecursos = new AssetManager();
		
		
		manejadorRecursos.load("images/splash.png",Texture.class);
		manejadorRecursos.load("audio/splash_sound.mp3",Sound.class);
		
		pantallaSplash = new PantallaSplash(this);
		pantallaMenu = new PantallaMenuInicio(this);
		pantallaSeleccionNivel = new PantallaSeleccionNivel(this);
//		pantallaOpciones = new PantallaOpciones();
		pantallaPuntuaciones = new PantallaPuntuaciones(this);
		pantallaCreditos = new PantallaCreditos(this);
		//pantallaNivel = new PantallaNivel(this);
		pantallaCargaNivel = new PantallaCargaNivel(this);
		pantallaNivel = new PantallaNivel(this);
		pantallaSecreto = new PantallaNivelSecreto(this);
	}
	
	@Override
	public void create() {
		sb = new SpriteBatch();
		manejadorRecursos.finishLoading();
		setScreen(pantallaSeleccionNivel);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		manejadorRecursos.dispose();
		sb.dispose();
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE QUETZAL (GAME)");
	}
	
	public static SpriteBatch getSpriteBatch(){ return sb; }
	
	public static AssetManager getManejaRecursos(){ return manejadorRecursos; }
}