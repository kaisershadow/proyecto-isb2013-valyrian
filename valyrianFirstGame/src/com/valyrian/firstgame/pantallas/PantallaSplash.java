package com.valyrian.firstgame.pantallas;

import 	static com.valyrian.firstgame.utilidades.GameVariables.*;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import  static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.valyrian.firstgame.Quetzal;


public class PantallaSplash implements Screen {

	private Quetzal juego;
	private Sound sound;
	private Texture splashTexture;
	private SpriteBatch spriteBatch;
	private Image splashImage;
	
	public PantallaSplash(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	@Override
	public void render(float delta) {	
		//Limpiar la pantalla y asignar un color de fondo a la ventana
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		juego.manejadorRecursos.update();
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
		spriteBatch = juego.getSpriteBatch();
		//Cargar recursos del manejador
		sound = juego.manejadorRecursos.get("audio/splash_sound.mp3");
		splashTexture = juego.manejadorRecursos.get("images/splash.png");
	
		//Cargar imagen de fondo de las pantallas de menu
		juego.manejadorRecursos.load("images/menus/mainmenu_BG.jpg", Texture.class);
		
		//Cargar imagenes de la siguiente pantalla
		juego.manejadorRecursos.load("images/menus/titulo_quetzal.png", Texture.class);
		juego.manejadorRecursos.load("images/menus/titulo_labusqueda.png", Texture.class);
		
		//Cargar SKIN para las pantallas
		juego.manejadorRecursos.load("ui/skin/uiskin.json", Skin.class);
		
		//Seleccionar Linear para mejorar el estiramiento 
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
			    		juego.manejadorRecursos.finishLoading();
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
//		spriteBatch.dispose();
//		ManejadorRecursos.getInstancia().disposeTexture("splash_texture");
//		ManejadorRecursos.getInstancia().disposeSound("roar_sound");
		juego.manejadorRecursos.unload("images/splash.png");
		juego.manejadorRecursos.unload("audio/splash_sound.mp3");
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