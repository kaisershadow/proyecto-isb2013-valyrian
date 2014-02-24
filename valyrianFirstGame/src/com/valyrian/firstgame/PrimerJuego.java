package com.valyrian.firstgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.valyrian.firstgame.pantallas.MenuInicio;
import com.valyrian.firstgame.pantallas.PantallaSeleccionNivel;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.pantallas.pruebas.PantallaPruebaPersonaje;

public class PrimerJuego extends Game {
	public Screen pantallaSplash;
	public Screen pantallaMenu;
	public Screen pantallaNiveles;
//	Screen pantallaOpciones;
//	Screen pantallaPuntuaciones;
//	Screen pantallaNivel1;
//	Screen pantallaNivel2;
//	Screen pantallaNivel3;
	
	public Screen pantallaPrueba;
	
	
	public PrimerJuego() {
		// TODO Auto-generated constructor stub
		pantallaSplash = new PantallaSplash(this);
		pantallaMenu = new MenuInicio(this);
		pantallaNiveles = new PantallaSeleccionNivel(this);
//		pantallaOpciones = new PantallaOpciones();
//		pantallaPuntuaciones = new PantallaPuntuaciones();
//		pantallaNivel1 = new PantallaNivel1();
//		pantallaNivel2 = new PantallaNivel2();
//		pantallaNivel3 = new PantallaNivel3();	
		pantallaPrueba = new PantallaPruebaPersonaje(this);
		
	}
	
	@Override
	public void create() {
		//setScreen(pantallaSplash);
		//setScreen(pantallaNiveles);
		setScreen(pantallaPrueba);
	}
}
