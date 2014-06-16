package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class PantallaMenuInicio implements Screen{
	
	private Stage escena;
	private Table tabla;
	private TextButton botonJugar;
	private TextButton botonConfiguraciones;
	private TextButton botonPuntuaciones;
	private TextButton botonSalir;
	private TextButton botonCreditos;
	private Skin skin;
	private SpriteBatch batch;
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

	public PantallaMenuInicio(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	
	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        //Se actualiza la escena (escene)
        escena.act(delta);
        batch.begin();       
        	fondo.draw(batch, 1);
        	escena.draw();
        batch.end();
        
        //Para ver las lineas de depuracion
        if(debug)
        	Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		
		escena.setViewport(width , height, true);
		
		tabla.setBounds(width*0.05f, 30, width, height);
		tabla.setSize(width*0.2f,height*0.50f);
		tabla.invalidateHierarchy();
		
		botonSalir.setBounds(width*0.75f , 30, width , height*0.30f);
		botonSalir.setSize(width*0.2f, (int)(height*0.5/4));
		
		fondo.setSize(width, height);
		
		tituloQuetzal.setBounds(width*0.25f, height*0.75f, width, height);
		tituloQuetzal.setSize(width*0.5f, height*0.2f);
		
		subQuetzal.setBounds(width*0.40f, height*0.70f, width, height);
		subQuetzal.setSize(width*0.3f, height*0.10f);		
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
	//	escena.dispose();
		
		Quetzal.getManejaRecursos().unload("images/menus/titulo_labusqueda.png");
		Quetzal.getManejaRecursos().unload("images/menus/titulo_quetzal.png");
		
		Controllers.removeListener(mjs);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE MENU INICIO");
	}

	void inicializar_variables(){
		if(!Quetzal.getManejaRecursos().isLoaded("ui/skin/uiskin.json", Skin.class))
			Quetzal.getManejaRecursos().load("ui/skin/uiskin.json", Skin.class);
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
			Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/titulo_quetzal.png"))
			Quetzal.getManejaRecursos().load("images/menus/titulo_quetzal.png", Texture.class);
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/titulo_labusqueda.png"))
			Quetzal.getManejaRecursos().load("images/menus/titulo_labusqueda.png", Texture.class);
		Quetzal.getManejaRecursos().finishLoading();
		
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		colorExit = new Color(99, 145, 0, 0.4f);
		colorEnter = new Color(1f, 1f, 1f, 0.3f);
		batch = Quetzal.getSpriteBatch();
		
		textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg");
	    fondo = new Image(textureFondo);
	    
	    textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_quetzal.png");
	    tituloQuetzal = new Image(textureTitulo);
	    
	    textureSubtitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_labusqueda.png");
	    subQuetzal = new Image(textureSubtitulo);
		
	    escena = new Stage();
		tabla = new Table(skin);
		
		botonJugar = new TextButton("Iniciar", skin);
		botonConfiguraciones = new TextButton("Configuraciones", skin);
		botonPuntuaciones = new TextButton("Puntuaciones", skin);
		botonSalir = new TextButton("Salir del juego", skin);
		botonCreditos = new TextButton("Creditos", skin);
		
		botonJugar.setColor(colorEnter);
		botonConfiguraciones.setColor(colorExit);
		botonPuntuaciones.setColor(colorExit);
		botonSalir.setColor(colorExit);
		botonCreditos.setColor(colorExit);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
	}
	
	private void button_listeners(){
		
		botonJugar.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SE LLAMO AL TOUCH DE BOTON JUGAR");
				juego.setScreen(juego.pantallaSeleccionNivel);
				return false;
			}});
		
		botonConfiguraciones.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SE LLAMO AL TOUCH DE BOTON CONFIGURACIONES");
				juego.setScreen(juego.pantallaOpciones);
				return true;
			}});
		
		botonPuntuaciones.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SE LLAMO AL TOUCH DE BOTON PUNTUACIONES");
				juego.setScreen(juego.pantallaPuntuaciones);
				return true;
			}});
//		
		botonCreditos.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SE LLAMO AL TOUCH DE BOTON CREDITOS");
				juego.setScreen(juego.pantallaCreditos);
				return true;
			}});
		
		botonSalir.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SE LLAMO AL TOUCH DE BOTON SALIR");
				Gdx.app.exit();
				return true;
			}});
	}

	private void cargar_actores_escenario(){
		if(debug)
			tabla.debug();

		tabla.add(botonJugar).space( 10f ).fill().expand();
		tabla.row();
		tabla.add(botonConfiguraciones).space(10f).fill().expand();
		tabla.row();
		tabla.add(botonPuntuaciones).space(10f).fill().expand();
		tabla.row();
		tabla.add(botonCreditos).space(10f).fill().expand();

		escena.addActor(fondo);
		escena.addActor(botonSalir);
		escena.addActor(tabla);
		escena.addActor(tituloQuetzal);
		escena.addActor(subQuetzal);
		
		escena.setKeyboardFocus(botonJugar);
		
		ArrayList<TextButton> lista = new ArrayList<TextButton>();
		lista.add(botonJugar);
		lista.add(botonConfiguraciones);
		lista.add(botonPuntuaciones);
		lista.add(botonCreditos);
		lista.add(botonSalir);
		escena.addListener(new MenuListener(escena, lista));
		
	}
}