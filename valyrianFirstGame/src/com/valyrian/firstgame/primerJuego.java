package com.valyrian.firstgame;

import com.badlogic.gdx.Game;
import com.valyrian.firstgame.pantallas.pantallaSplash;
import com.valyrian.firstgame.pantallas.pruebas.pantallaPruebaPersonaje;

public class PrimerJuego extends Game {

	@Override
	public void create() {
		setScreen(new pantallaPruebaPersonaje());
	}
}
