package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.GameVariables;
import com.valyrian.firstgame.utilidades.input.MenuJoystick;
import com.valyrian.firstgame.utilidades.input.MenuListener;
import com.valyrian.firstgame.utilidades.input.TextButtonListener;

public class PantallaOpciones implements Screen {

	private Stage escena;
	private Table tabla,container;
	private TextButton botonSalir;
	private Skin skin;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Image fondo;
	private Image tituloOpciones;
	private Slider sliderVolume;
	private Quetzal juego;
	private Color colorExit;
	private Color colorEnter;
	private MenuJoystick mjs;
	private TextButton botonFullScreen;
	private TextButton botonNombre;
	private Boolean fullScreen;
	private TextField nombre;
	private CheckBox facil, medio, dificil;
	
	public PantallaOpciones(Quetzal primerJuego) {
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
		
		botonSalir.setBounds(width*0.75f , 30, width , height*0.30f);
		botonSalir.setSize(width*0.2f, (int)(height*0.5/4));
		
		fondo.setSize(width, height);
		
		tituloOpciones.setBounds(width*0.08f, height*0.83f, width, height);
		tituloOpciones.setSize(width*0.4f, height*0.13f);
				
		sliderVolume.setSize(width*0.5f, height*0.05f);
		
		botonFullScreen.setSize(width*0.2f, (int)(height*0.5/4));
		
		nombre.setSize(width*0.2f, height*0.05f);
		
		tabla.setBounds(width*0.1f, height*0.07f, width, height);
		tabla.setSize(width*0.7f, height*0.7f);

			
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
		Quetzal.getManejaRecursos().unload("images/menus/titulo_configuraciones.png");
//		Quetzal.getManejaRecursos().unload("images/menus/mainmenu_BG.jpg");
		Controllers.removeListener(mjs);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE OPCIONEs");
	}

	void inicializar_variables(){
	    skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
	    
	    colorExit = new Color(99, 145, 0, 0.4f);
		colorEnter = new Color(1f, 1f, 1f, 0.3f);
	    	    
	    if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
	    	Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);	    
		 
		    Quetzal.getManejaRecursos().load("images/menus/titulo_configuraciones.png", Texture.class);
	    
		    Quetzal.getManejaRecursos().finishLoading();
		    
	    textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg");
	    fondo = new Image(textureFondo);
	    
	    textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_configuraciones.png"); 
	    tituloOpciones = new Image(textureTitulo);
	    
	    fullScreen = Gdx.graphics.isFullscreen();
	    
	    escena = new Stage();
		tabla = new Table(skin);
		container = new Table(skin);

		botonSalir = new TextButton("Regresar", skin);
		botonSalir.setColor(colorEnter);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
		botonFullScreen = new TextButton("Pantalla Completa", skin);
		if(fullScreen)
			botonFullScreen.setText("Ventana");
		
		botonFullScreen.setColor(colorExit);
		
		botonNombre = new TextButton("Guardar Nombre", skin);
		botonNombre.setColor(colorExit);
		
		nombre = new TextField(GameVariables.PLAYER_NAME, skin);
		nombre.setColor(colorExit);
		
		dificil = new CheckBox("Dificil", skin);
		medio = new CheckBox("Medio", skin);
		facil = new CheckBox("Facil", skin);
		
		switch (DIFICULTAD) {
		case 1:
			facil.setChecked(true);	
			break;
		case 2:
			medio.setChecked(true);	
			break;
		case 3:
			dificil.setChecked(true);	
			break;
		default:
			break;
		}
		
		
		SliderStyle style = new SliderStyle(
									new TextureRegionDrawable(
											new TextureRegion(
													new Texture("images/sliderbg.png")
									)),
									new TextureRegionDrawable(
											new TextureRegion(
													new Texture("images/knob.png"))));
		sliderVolume = new Slider(0, 1, 0.1f, false, style);
		sliderVolume.setValue(VOLUMEN);
		

	}
		
	void cargar_actores_escenario(){
		if(debug)
			tabla.debug();
		
		escena.addActor(fondo);
		escena.addActor(botonSalir);
		escena.addActor(tituloOpciones);
		
		container.add(facil).expand().left();
		container.row();
		container.add(medio).expand().left();
		container.row();
		container.add(dificil).expand().left();
		
		tabla.add("Nombre del Jugador:").expand().left();
		tabla.add(nombre).expand().left();
		tabla.add(botonNombre).expand().left();
		tabla.row();
		tabla.add("Volúmen de juego:").expand().left();
		tabla.add(sliderVolume).fill().expand().left();
		tabla.row();
		tabla.add("Opciones de Pantalla:").expand().left();
		tabla.add(botonFullScreen).space(10f).expand().left();
		tabla.row();
		tabla.add("Dificultad:").expand().left();
		tabla.add(container).expand().left();
		
		escena.addActor(tabla);		
		escena.setKeyboardFocus(sliderVolume);
		
		ArrayList<TextButton> lista = new ArrayList<TextButton>();
		lista.add(botonNombre);
		lista.add(botonFullScreen);
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
			
		sliderVolume.addListener(new EventListener() {		
				@Override
				public boolean handle(Event event) {
						GameVariables.VOLUMEN = sliderVolume.getValue();
					return false;
				}
			});
		
		botonFullScreen.addListener(new TextButtonListener(colorEnter,colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				fullScreen = !fullScreen;
				if(fullScreen){
					botonFullScreen.setText("Ventana");
					Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
				}else{
					botonFullScreen.setText("Pantalla Completa");
					Gdx.graphics.setDisplayMode(GameVariables.V_WIDTH, GameVariables.V_HEIGHT, false);
				}
				return true;
			}	
		});
		
		botonNombre.addListener(new TextButtonListener(colorEnter,colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				GameVariables.PLAYER_NAME = nombre.getText();
				return true;
			}	
		});
		
		facil.addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				if(facil.isChecked()){
					DIFICULTAD = 1;
					medio.setChecked(false);
					dificil.setChecked(false);
				}
				return false;
			}
		});
		
		medio.addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				if(medio.isChecked()){
					DIFICULTAD = 2;
					facil.setChecked(false);
					dificil.setChecked(false);
				}
				return false;
			}
		});
		
		dificil.addListener(new EventListener() {
			
			@Override
			public boolean handle(Event event) {
				if(dificil.isChecked()){
					DIFICULTAD = 3;
					facil.setChecked(false);
					medio.setChecked(false);
				}
				return false;
			}
		});
	
	
	}
}
