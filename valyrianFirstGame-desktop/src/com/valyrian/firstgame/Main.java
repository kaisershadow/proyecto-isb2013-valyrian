package com.valyrian.firstgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import static com.valyrian.firstgame.utilidades.GameVariables.*;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Quetzal: La Busqueda";
		cfg.useGL20 = true;
		cfg.width = V_WIDTH;
		cfg.height = V_HEIGHT;
//		cfg.fullscreen=true;
		cfg.resizable = false;
		new LwjglApplication(new Quetzal(), cfg);
	}
}