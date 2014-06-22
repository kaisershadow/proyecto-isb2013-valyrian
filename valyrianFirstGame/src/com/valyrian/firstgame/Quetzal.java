package com.valyrian.firstgame;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.valyrian.firstgame.pantallas.PantallaCargaNivel;
import com.valyrian.firstgame.pantallas.PantallaFinNivel;
import com.valyrian.firstgame.pantallas.PantallaMenuInicio;
import com.valyrian.firstgame.pantallas.PantallaAcercaDe;
import com.valyrian.firstgame.pantallas.PantallaNivel;
import com.valyrian.firstgame.pantallas.PantallaOpciones;
import com.valyrian.firstgame.pantallas.PantallaPuntuaciones;
import com.valyrian.firstgame.pantallas.PantallaSeleccionNivel;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.secreto.PantallaFinSecreto;
import com.valyrian.firstgame.secreto.PantallaNivelSecreto;


public class Quetzal extends Game {
	private static AssetManager manejadorRecursos;
	private static SpriteBatch sb;
	
	public Screen pantallaSplash;
	public Screen pantallaMenu;
	public Screen pantallaSeleccionNivel;
	public Screen pantallaOpciones;
	public Screen pantallaPuntuaciones;
	public Screen pantallaCreditos;
	public Screen pantallaNivel;
	public Screen pantallaCargaNivel;
	public Screen pantallaSecreto;
	public Screen pantallaFinNivel;
	public Screen pantallaFinSecreto;
	public Quetzal() {
		
		manejadorRecursos = new AssetManager();
		manejadorRecursos.load("images/splash.png",Texture.class);
		manejadorRecursos.load("audio/splash_sound.mp3",Sound.class);
		manejadorRecursos.load("images/menus/carga_BG.jpg",Texture.class);
		
		pantallaSplash = new PantallaSplash(this);
		pantallaMenu = new PantallaMenuInicio(this);
		pantallaSeleccionNivel = new PantallaSeleccionNivel(this);
		pantallaOpciones = new PantallaOpciones(this);
		pantallaPuntuaciones = new PantallaPuntuaciones(this);
		pantallaCreditos = new PantallaAcercaDe(this);
		pantallaCargaNivel = new PantallaCargaNivel(this);
		pantallaNivel = new PantallaNivel(this);
		pantallaFinNivel = new PantallaFinNivel(this);
		
		pantallaSecreto = new PantallaNivelSecreto(this);
		pantallaFinSecreto = new PantallaFinSecreto(this);
	}
	
	@Override
	public void create() {
		Quetzal.sb = new SpriteBatch();
		Quetzal.manejadorRecursos.finishLoading();
		this.setScreen(pantallaSplash);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		Quetzal.manejadorRecursos.dispose();
		Quetzal.sb.dispose();
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE QUETZAL (GAME)");
	}
	
	public static SpriteBatch getSpriteBatch(){ return sb; }
	
	public static AssetManager getManejaRecursos(){ return manejadorRecursos; }
}