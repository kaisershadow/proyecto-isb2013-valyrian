package com.valyrian.firstgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.valyrian.firstgame.pantallas.PantallaMenuInicio;
import com.valyrian.firstgame.pantallas.PantallaCreditos;
import com.valyrian.firstgame.pantallas.PantallaPruebaPersonaje;
import com.valyrian.firstgame.pantallas.PantallaPuntuaciones;
import com.valyrian.firstgame.pantallas.PantallaSeleccionNivel;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.utilidades.recursos.ManejadorRecursos;

public class PrimerJuego extends Game {
	public AssetManager manejadorRecursos;
	
	public Screen pantallaSplash;
	public Screen pantallaMenu;
	public Screen pantallaSeleccionNivel;
//	Screen pantallaOpciones;
	public Screen pantallaPuntuaciones;
	public Screen pantallaCreditos;
	public Screen pantallaNivel;
	
	public Screen pantallaPrueba;	
	
	public PrimerJuego() {
		manejadorRecursos = new AssetManager();
		pantallaSplash = new PantallaSplash(this);
		pantallaMenu = new PantallaMenuInicio(this);
		pantallaSeleccionNivel = new PantallaSeleccionNivel(this);
//		pantallaOpciones = new PantallaOpciones();
		pantallaPuntuaciones = new PantallaPuntuaciones(this);
		pantallaCreditos = new PantallaCreditos(this);
		//pantallaNivel = new PantallaNivel(this);	
		pantallaPrueba = new PantallaPruebaPersonaje(this);
	}
	
	@Override
	public void create() {
		//Cargar la imagen de fondo , que es compartida entre pantallas
//	    ManejadorRecursos.getInstancia().cargarTexture("images/menus/mainmenu_BG.jpg", "mainmenu_BG");
		manejadorRecursos.load("images/menus/mainmenu_BG.jpg", Texture.class);
		manejadorRecursos.finishLoading();
//		manejadorRecursos.get("images/menus/mainmenu_BG.jpg");
		//setScreen(pantallaSplash);
		//setScreen(pantallaNiveles);
		setScreen(pantallaSeleccionNivel);
	}
}