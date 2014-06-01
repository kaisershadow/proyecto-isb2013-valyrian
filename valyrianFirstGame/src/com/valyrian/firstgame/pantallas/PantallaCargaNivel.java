package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.BarraCarga;

public class PantallaCargaNivel implements Screen {

	private SpriteBatch batch;
	private BarraCarga barra;
	private Quetzal juego;
	
	
	public PantallaCargaNivel(Quetzal game) {
		this.juego = game;
	}
	
	@Override
	public void render(float delta) {
	batch.begin();
		barra.draw(batch, 1);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		this.batch = juego.getSpriteBatch();
		barra = new BarraCarga(juego);
		juego.manejadorRecursos.load("personajes/nativo.png", Texture.class);
		juego.manejadorRecursos.load("personajes/rana.png", Texture.class);
		juego.manejadorRecursos.load("personajes/dardo.png", Texture.class);
		juego.manejadorRecursos.load("images/corazon.png", Texture.class);
		juego.manejadorRecursos.load("images/hud.png", Texture.class);
		juego.manejadorRecursos.load("images/calendario_maya.png", Texture.class);
		juego.manejadorRecursos.load("images/pausa.png", Texture.class);
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		barra.dispose();
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA CARGA NIVEL");
		
	}

}
