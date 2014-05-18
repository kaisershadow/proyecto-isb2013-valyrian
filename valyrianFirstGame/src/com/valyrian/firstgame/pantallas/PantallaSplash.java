package com.valyrian.firstgame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.utilidades.recursos.ManejadorRecursos;

import 	static com.valyrian.firstgame.utilidades.GameVariables.*;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class PantallaSplash implements Screen {

	private PrimerJuego juego;
	private Sound sound;
	private Texture splashTexture;
	private SpriteBatch spriteBatch;
	private Image splashImage;
	
	public PantallaSplash(PrimerJuego primerJuego) {
		juego = primerJuego;
	}
	
	@Override
	public void render(float delta) {	
		//Limpiar la pantalla y asignar un color de fondo a la ventana
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		splashImage.act(delta);
		spriteBatch.begin();
		splashImage.draw(spriteBatch, 1);	
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		splashImage.setBounds(0, 0, width, height);
		splashImage.setSize(width, height);
	}

	@Override
	public void show() {
		//Agregar recursos al manejador
		ManejadorRecursos.getInstancia().cargarSound("audio/splash_sound.mp3", "roar_sound");
		ManejadorRecursos.getInstancia().cargarTexture("images/splash.png", "splash_texture");
		
		spriteBatch = new SpriteBatch();
	
		//Cargar recursos del manejador
		sound = ManejadorRecursos.getInstancia().getSound("roar_sound");
		splashTexture = ManejadorRecursos.getInstancia().getTexture("splash_texture");
	
		// seleccionar Linear para mejorar el estiramiento 
		splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear ); 
		
		splashImage = new Image(splashTexture); 
		splashImage.setFillParent(true);
		splashImage.getColor().a=0;
		sound.play();
		
		 //Se agrega la accion para que haga el efecto de desvanecimiento
		splashImage.addAction( sequence(fadeIn(1.5f), delay(1.5f), fadeOut(1.5f),
				new Action() {       
			    	@Override       
			    	public boolean act(float delta){
			    	// La última acción nos direcciona hacia la siguiente pantalla (menu) 
			    		juego.setScreen(juego.pantallaMenu);
			    		return true; 
			        }  
			     }));
	}

	@Override
	public void hide() {
		dispose();
	}


	@Override
	public void dispose() {
		spriteBatch.dispose();
		ManejadorRecursos.getInstancia().disposeTexture("splash_texture");
		ManejadorRecursos.getInstancia().disposeSound("roar_sound");
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE SPLASH");
		
	}
	
//Metodos no definidos (Especificos de ANDROID)
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
}