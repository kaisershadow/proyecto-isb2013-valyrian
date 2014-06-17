package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.BarraCarga;

public class PantallaCargaNivel implements Screen {

	private SpriteBatch batch;
	private BarraCarga barra;
	private Quetzal juego;
	private Screen pantallaActual;
	
	
	public PantallaCargaNivel(Quetzal game) {
		this.juego = game;
		this.pantallaActual = null;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(Quetzal.getManejaRecursos().get("images/menus/carga_BG.jpg",Texture.class),0, 0,V_WIDTH, V_HEIGHT);
		barra.draw(batch, 1);
		batch.end();
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
    		juego.setScreen(this.pantallaActual);
    		return;
		}
		else if(Gdx.input.isKeyPressed(Keys.I)){
    		juego.setScreen(juego.pantallaSecreto);
    		return;
    		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		this.batch = Quetzal.getSpriteBatch();
		barra = new BarraCarga();

		//Cargar los recursos necesarios
		Quetzal.getManejaRecursos().load("personajes/nativo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/rana.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/avispa.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/dardo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/moneda.png", Texture.class);
		
		Quetzal.getManejaRecursos().load("images/corazon.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/hud.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya1.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya2.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya3.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/pausa.png", Texture.class);
//		Quetzal.getManejaRecursos().load("images/moneda.png", Texture.class);
		
		Quetzal.getManejaRecursos().load("audio/nivel1.mp3", Music.class);
		Quetzal.getManejaRecursos().load("audio/salto.wav", Sound.class);
		Quetzal.getManejaRecursos().load("audio/moneda.ogg",Sound.class);
		Quetzal.getManejaRecursos().load("audio/disparo.mp3",Sound.class);
		Quetzal.getManejaRecursos().load("audio/dolor.wav",Sound.class);
		Quetzal.getManejaRecursos().load("audio/muerte.wav",Sound.class);
		
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
	
	public void setPantallaActual(Screen pa){ this.pantallaActual =  pa; }
}