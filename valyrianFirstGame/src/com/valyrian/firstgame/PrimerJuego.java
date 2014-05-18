package com.valyrian.firstgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.valyrian.firstgame.pantallas.PantallaMenuInicio;
import com.valyrian.firstgame.pantallas.PantallaCreditos;
import com.valyrian.firstgame.pantallas.PantallaPruebaPersonaje;
import com.valyrian.firstgame.pantallas.PantallaPuntuaciones;
import com.valyrian.firstgame.pantallas.PantallaSeleccionNivel;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.utilidades.recursos.ManejadorRecursos;

public class PrimerJuego extends Game {
	public Screen pantallaSplash;
	public Screen pantallaMenu;
	public Screen pantallaNiveles;
//	Screen pantallaOpciones;
	public Screen pantallaPuntuaciones;
	public Screen pantallaCreditos;
	public Screen pantallaNivel1;
	public Screen pantallaNivel2;
	public Screen pantallaNivel3;
	
	public Screen pantallaPrueba;	
	
	public PrimerJuego() {
		pantallaSplash = new PantallaSplash(this);
		pantallaMenu = new PantallaMenuInicio(this);
		pantallaNiveles = new PantallaSeleccionNivel(this);
//		pantallaOpciones = new PantallaOpciones();
		pantallaPuntuaciones = new PantallaPuntuaciones(this);
		pantallaCreditos = new PantallaCreditos(this);
//		pantallaNivel1 = new PantallaNivel1();
//		pantallaNivel2 = new PantallaNivel2();
//		pantallaNivel3 = new PantallaNivel3();	
		pantallaPrueba = new PantallaPruebaPersonaje(this);
		
	}
	
	@Override
	public void create() {
		//Cargar la imagen de fondo , que es compartida entre pantallas
	    ManejadorRecursos.getInstancia().cargarTexture("images/menus/mainmenu_BG.jpg", "mainmenu_BG");
		//setScreen(pantallaSplash);
		//setScreen(pantallaNiveles);
		setScreen(pantallaNiveles);
	}
}