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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
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
	private SpriteBatch batch;
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
	private Boolean fullScreen = false;
	private TextField nombre;
	
	
	public PantallaOpciones(Quetzal primerJuego) {
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
        //Para ver las lineas de decupuracion
		if(debug)
			Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

		escena.setViewport(width , height, true);
		
		container.setBounds(width*0.05f, 30, width, height);
		container.setSize(width*0.6f,height*0.60f);
		container.invalidateHierarchy();
		
		botonSalir.setBounds(width*0.75f , 30, width , height*0.30f);
		botonSalir.setSize(width*0.2f, (int)(height*0.5/4));
		
		fondo.setSize(width, height);
		
		tituloOpciones.setBounds(width*0.15f, height*0.75f, width, height);
		tituloOpciones.setSize(width*0.7f, height*0.2f);
				
		sliderVolume.setSize(width*0.5f, height*0.05f);
		
		botonFullScreen.setSize(width*0.2f, (int)(height*0.5/4));
		
		nombre.setSize(width*0.2f, height*0.05f);
		
		tabla.setBounds(width*0.15f, height*0.15f, width, height);
		tabla.setSize(width*0.5f, height*0.5f);

			
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
	    
		batch = Quetzal.getSpriteBatch();
	    
	    if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
	    	Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);	    
		 
		    Quetzal.getManejaRecursos().load("images/menus/titulo_configuraciones.png", Texture.class);
	    
		    Quetzal.getManejaRecursos().finishLoading();
		    
	    textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg");
	    fondo = new Image(textureFondo);
	    
	    textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_configuraciones.png"); 
	    tituloOpciones = new Image(textureTitulo);
	    
		
	    escena = new Stage();
		tabla = new Table(skin);
		container = new Table(skin);

		botonSalir = new TextButton("Atras", skin);
		botonSalir.setColor(colorEnter);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
		botonFullScreen = new TextButton("Pantalla Completa", skin);
		botonFullScreen.setColor(colorExit);
		
		botonNombre = new TextButton("Guardar Nombre", skin);
		botonNombre.setColor(colorExit);
		
		nombre = new TextField(GameVariables.PLAYER_NAME, skin);
		
		
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
		tabla.add("Nombre del Jugador:").space(10f).left();
		tabla.row();
		tabla.add(nombre).fill().expand().space(10f);
		tabla.row();
		tabla.add(botonNombre).space(10f).left();
		tabla.row();
		tabla.add("Volúmen de juego:").fill().expand().space(10f);
		tabla.row();
		tabla.add(sliderVolume).fill().expand().space(10f);;
		tabla.row();
		tabla.add("Opciones de Pantalla:").fill().expand().space(10f);
		tabla.row();
		tabla.add(botonFullScreen).space(10f).left();
		
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
	}
}
