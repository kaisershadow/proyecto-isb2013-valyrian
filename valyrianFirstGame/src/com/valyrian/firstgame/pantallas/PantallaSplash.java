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
		
		Quetzal.getManejaRecursos().update();
		splashImage.act(delta);
		spriteBatch.begin();
		splashImage.draw(spriteBatch, 1);	
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		splashImage.setBounds(0, 0, width, height);
		splashImage.setSize(V_WIDTH, V_HEIGHT);
	}

	@Override
	public void show() {
		spriteBatch = Quetzal.getSpriteBatch();
		
		//Cargar recursos del manejador
		sound = Quetzal.getManejaRecursos().get("audio/splash_sound.mp3");
		splashTexture = Quetzal.getManejaRecursos().get("images/splash.png");
		
		//Cargar imagen de fondo de las pantallas de menu
		Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);
		
		//Cargar imagenes de la siguiente pantalla
		Quetzal.getManejaRecursos().load("images/menus/titulo_quetzal.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/menus/titulo_labusqueda.png", Texture.class);
		
		//Cargar SKIN para las pantallas
		Quetzal.getManejaRecursos().load("ui/skin/uiskin.json", Skin.class);
		
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
			    		Quetzal.getManejaRecursos().finishLoading();
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
		Quetzal.getManejaRecursos().unload("images/splash.png");
		Quetzal.getManejaRecursos().unload("audio/splash_sound.mp3");
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