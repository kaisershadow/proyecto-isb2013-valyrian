package com.valyrian.firstgame;

import com.badlogic.gdx.Game;
import com.valyrian.firstgame.pantallas.PantallaSplash;
import com.valyrian.firstgame.pantallas.pruebas.PantallaPruebaPersonaje;

public class PrimerJuego extends Game {

	@Override
	public void create() {
		setScreen(new PantallaSplash());
	}
}
