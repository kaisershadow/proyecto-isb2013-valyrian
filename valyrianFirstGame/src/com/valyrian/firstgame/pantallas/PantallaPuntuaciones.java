package com.valyrian.firstgame.pantallas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.input.MenuJoystick;
import com.valyrian.firstgame.utilidades.input.MenuListener;
import com.valyrian.firstgame.utilidades.input.TextButtonListener;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

public class PantallaPuntuaciones implements Screen{
	
	private Stage escena;
	private Table tabla1;
	private TextButton botonSalir;
	private Skin skin;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Texture textureSubtitulo;
	private Image fondo;
	private Image tituloQuetzal;
	private Image subQuetzal;
	private Quetzal juego;
	private Color colorExit;
	private Color colorEnter;
	private MenuJoystick mjs;
	
//	private Image primero;
//	private Image segundo;
//	private Image tercero;
//	private Texture texturePrimero;
//	private Texture textureSegundo;
//	private Texture textureTercero;
	
	public PantallaPuntuaciones(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	
	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        //Se actualiza la escena (escene)
        escena.act(delta);
        escena.draw();
        //Para ver las lineas de decupuracion
		if(debug)
			Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		escena.setViewport(width , height, true);
		
		tabla1.setBounds(width*0.05f, height*0.2f, width, height);
		tabla1.setSize(width*0.85f,height*0.5f);
		tabla1.invalidateHierarchy();
		
		botonSalir.setBounds(width*0.75f , 30, width , height*0.30f);
		botonSalir.setSize(width*0.2f, (int)(height*0.5/4));
		
		fondo.setSize(width, height);
		
		tituloQuetzal.setBounds(width*0.15f, height*0.75f, width, height);
		tituloQuetzal.setSize(width*0.65f, height*0.2f);
		
		subQuetzal.setBounds(width*0.20f, height*0.65f, width, height);
		subQuetzal.setSize(width*0.30f, height*0.15f);
		
//		primero.setSize(width*0.002f, height*0.03f);
//		segundo.setSize(width*0.05f, height*0.03f);
//		tercero.setSize(width*0.05f, height*0.03f);
	}

	@Override
	public void show() {
		
		inicializar_variables();
		
		Gdx.input.setInputProcessor(escena);
	
		button_listeners();
	
		cargar_actores_escenario();
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
//		escena.dispose();
		Controllers.removeListener(mjs);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PUNTUACIONES");
	}

	void inicializar_variables(){
	    skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
	    
	    colorExit = new Color(99, 145, 0, 0.5f);
		colorEnter = new Color(1f, 1f, 1f, 0.3f);
	    	    
	    //Cargar las imagenes de la pantalla
	    if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
			Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);
	 
	    Quetzal.getManejaRecursos().load("images/menus/titulo_puntuaciones.png",Texture.class);
	    Quetzal.getManejaRecursos().load("images/menus/titulo_mas_altas.png",Texture.class);
	    Quetzal.getManejaRecursos().load("images/menus/primero.png",Texture.class);
	    Quetzal.getManejaRecursos().load("images/menus/segundo.png",Texture.class);
	    Quetzal.getManejaRecursos().load("images/menus/tercero.png",Texture.class);
	    Quetzal.getManejaRecursos().finishLoading();

	    
	    textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg");
	    fondo = new Image(textureFondo);
	    
	    textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_puntuaciones.png");
	    tituloQuetzal = new Image(textureTitulo);
	    
	    textureSubtitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_mas_altas.png");
	    subQuetzal = new Image(textureSubtitulo);
		
//	    texturePrimero = Quetzal.getManejaRecursos().get("images/menus/primero.png"); 
//	    primero = new Image(texturePrimero);
//
//	    textureSegundo = Quetzal.getManejaRecursos().get("images/menus/segundo.png"); 
//	    segundo = new Image(textureSegundo);
//
//	    textureTercero = Quetzal.getManejaRecursos().get("images/menus/tercero.png"); 
//	    tercero = new Image(textureTercero);
	    
	    escena = new Stage();
		tabla1 = new Table(skin);

		botonSalir = new TextButton("Regresar", skin);
		botonSalir.setColor(colorEnter);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);

	}
	
	void cargar_actores_escenario(){
		if(debug)
			tabla1.debug();

		escena.addActor(fondo);
		escena.addActor(botonSalir);
		escena.addActor(tabla1);
		escena.addActor(tituloQuetzal);
		escena.addActor(subQuetzal);		
		
		tabla1.add().fill().expand();
		tabla1.add("NIVEL 1").fill().expand();
		tabla1.add("NIVEL 2").fill().expand();
		tabla1.add("NIVEL 3").fill().expand();
		tabla1.row();
		//tabla1.add(primero).space( 10f ).expand();
		tabla1.add("PRIMER LUGAR").fill().expand().right();
		tabla1.add(Gdx.files.internal("data/nivel1/puntuacion1.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel2/puntuacion1.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel3/puntuacion1.txt").readString()).fill().expand().center();
		tabla1.row();
//		tabla1.add(segundo).space(10f).expand();
		tabla1.add("SEGUNDO LUGAR").fill().expand().right();
		tabla1.add(Gdx.files.internal("data/nivel1/puntuacion2.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel2/puntuacion2.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel3/puntuacion2.txt").readString()).fill().expand().center();
		tabla1.row();
//		tabla1.add(tercero).space(10f).expand();
		tabla1.add("TERCER LUGAR").fill().expand().right();
		tabla1.add(Gdx.files.internal("data/nivel1/puntuacion3.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel2/puntuacion3.txt").readString()).fill().expand().center();
		tabla1.add(Gdx.files.internal("data/nivel3/puntuacion3.txt").readString()).fill().expand().center();
		tabla1.row();
		
		escena.setKeyboardFocus(botonSalir);
		
		ArrayList<TextButton> lista = new ArrayList<TextButton>();
		lista.add(botonSalir);
		escena.addListener(new MenuListener(escena, lista));
	}
	
	
	private void button_listeners(){
		
		botonSalir.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				juego.setScreen(juego.pantallaMenu);
				return true;
			}	
		});
	}
}