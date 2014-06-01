package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		barra.draw(batch, 1);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
//		barra.setPosition(width/2-200,height/2);
	}

	@Override
	public void show() {
		this.batch = Quetzal.getSpriteBatch();
		barra = new BarraCarga(juego);
		Quetzal.getManejaRecursos().load("personajes/nativo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/rana.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/avispa.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/dardo.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/corazon.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/hud.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/pausa.png", Texture.class);
		
		Quetzal.getManejaRecursos().load("secreto/balde.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/gota.png", Texture.class);
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
