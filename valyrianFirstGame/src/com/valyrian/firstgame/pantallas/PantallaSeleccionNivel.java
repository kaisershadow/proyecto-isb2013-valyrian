package com.valyrian.firstgame.pantallas;

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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.input.MenuJoystick;
import com.valyrian.firstgame.utilidades.input.MenuListener;
import com.valyrian.firstgame.utilidades.input.TextButtonListener;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

public class PantallaSeleccionNivel implements Screen{

	private Stage escena;
	private Table tablaNiveles;
	private Table tablaControles;
	private Table captura;
	private TextButton botonJugar;
	private TextButton botonRegresar;
	private TextButton niveles[];
	private Skin skin;
	private Color colorEnter;
	private Color colorExit;
	private SpriteBatch batch;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Texture textureSeleccionar;
	private Image fondo;
	private Image tituloNiveles;
	private Image subSeleccionar;
	private Texture capturaNivel[];
	private Window zonaTexto;
	private Quetzal juego;
	private MenuJoystick mjs;
	
	private int numNiveles; //numero de niveles del juego
	private int nivelActual;

	public PantallaSeleccionNivel(Quetzal primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    	
        batch.begin();
        
        fondo.draw(batch, 1f);
        escena.act(delta);
		escena.draw();
		
        batch.end();
        if(debug)
        	Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		
		escena.setViewport(width , height, true);
		
		tablaNiveles.setBounds(width*0.05f, 85, width*0.30f, height*0.3f);
		tablaNiveles.setSize(width*0.30f,height*0.45f);
		tablaNiveles.invalidateHierarchy();
		
		tablaControles.setBounds(width*0.55f , 15, width , height*0.30f);
		tablaControles.setSize(width*0.4f, (int)(height*0.3/3));
		tablaControles.invalidateHierarchy();
		
		fondo.setSize(width, height);
		
		tituloNiveles.setBounds(width*0.05f, height*0.75f, width, height);
		tituloNiveles.setSize(width*0.5f, height*0.2f);
		
		subSeleccionar.setBounds(width*0.18f, height*0.70f, width, height);
		subSeleccionar.setSize(width*0.4f, height*0.10f);
		
		captura.setBounds(width*0.65f, height*0.65f, width, height);
		captura.setSize(width*0.3f, height*0.3f);
		
		zonaTexto.setBounds(width*0.4f, height*0.15f, width, height);
		zonaTexto.setSize(width*0.55f, height*0.45f);
		

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
		Quetzal.getManejaRecursos().unload("images/menus/titulo_niveles.png");
		Quetzal.getManejaRecursos().unload("images/menus/titulo_seleccionarnivel.png");
		for(int i=1;i<=numNiveles;i++)
			Quetzal.getManejaRecursos().unload("images/menus/nivel"+i+".png");
		Controllers.removeListener(mjs);

		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE SELECCION NIVEL");
	}
	
	void inicializar_variables(){
		numNiveles = 3;
		
		if(!Quetzal.getManejaRecursos().isLoaded("ui/skin/uiskin.json", Skin.class))
			Quetzal.getManejaRecursos().load("ui/skin/uiskin.json", Skin.class);
		Quetzal.getManejaRecursos().finishLoading();
		
        skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
        colorExit = new Color(99, 145, 0, 0.4f);
		colorEnter = new Color(1f, 1f, 1f, 0.3f);        

        batch = Quetzal.getSpriteBatch();
        
        if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
			Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);
	 
	    Quetzal.getManejaRecursos().load("images/menus/titulo_niveles.png",Texture.class);
	    Quetzal.getManejaRecursos().load("images/menus/titulo_seleccionarnivel.png",Texture.class);
	    for(int i=1;i<=numNiveles;i++)
	    	Quetzal.getManejaRecursos().load("images/menus/nivel"+i+".png",Texture.class);
	    Quetzal.getManejaRecursos().finishLoading();        

        textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg"); 
        fondo = new Image(textureFondo);
        
        
        textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_niveles.png");
        tituloNiveles = new Image(textureTitulo);
        
        
        textureSeleccionar = textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_seleccionarnivel.png");
        subSeleccionar = new Image(textureSeleccionar);
        
        captura = new Table(skin);
		escena = new Stage();
		tablaNiveles = new Table(skin);
		tablaControles = new Table(skin);
		zonaTexto = new Window("Resumen: ", skin);
		botonJugar = new TextButton("Jugar", skin);
		botonRegresar = new TextButton("Regresar", skin);
		
		niveles = new TextButton[numNiveles];
		for (int i = 0; i < numNiveles ; i++) {
			niveles[i] = new TextButton("nivel " + Integer.toString(i+1) , skin);
			niveles[i].setColor(colorExit);
			niveles[i].setName("nivel" + Integer.toString(i+1) );
		}
		
		zonaTexto.add("Por favor seleccione un nivel de \n\r y presione el boton Jugar");
		capturaNivel = new Texture[numNiveles];
		for (int i = 0; i < numNiveles; i++) 
			capturaNivel[i] = Quetzal.getManejaRecursos().get("images/menus/nivel"+(i+1)+".png");
		
		botonJugar.setColor(colorEnter);
		botonRegresar.setColor(colorExit);
		zonaTexto.setColor(colorExit);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
	}
	
	private void button_listeners(){
		
		botonRegresar.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				juego.setScreen(juego.pantallaMenu);
				return true;
			}});
		
		botonJugar.addListener(new TextButtonListener(colorEnter, colorExit){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				switch (nivelActual){
				case 1:
					((PantallaCargaNivel)juego.pantallaCargaNivel).setPantallaActual(juego.pantallaNivel);
					break;
					
				case 2:
					((PantallaCargaNivel)juego.pantallaCargaNivel).setPantallaActual(juego.pantallaNivel);
					break;
					
				case 3:
					((PantallaCargaNivel)juego.pantallaCargaNivel).setPantallaActual(juego.pantallaNivel);
					break;
				
				default:
					//Si no se selecciona ningun nivel, se carga el nivel secreto
					((PantallaCargaNivel)juego.pantallaCargaNivel).setPantallaActual(juego.pantallaSecreto);
					break;
				}
				juego.setScreen(juego.pantallaCargaNivel);
				return true;
			}});
		
		
		for(int i = 0; i < numNiveles; i++){
			niveles[i].addListener(new TextButtonListener(colorEnter, colorExit){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					zonaTexto.clear();
					zonaTexto.add(Gdx.files.internal("ui/texto/" + event.getListenerActor().getName()+ ".txt").readString());
					captura.clear();
					captura.add(new Image(capturaNivel[Integer.parseInt(event.getListenerActor().getName().substring(5,6))-1]));
					nivelActual = Integer.parseInt(event.getListenerActor().getName().substring(5, 6));
					event.getListenerActor().setColor(colorExit);
					return true;
				}	
			});		
		}
	}
	
	void cargar_actores_escenario(){
		if(debug){
			tablaNiveles.debug();
			tablaControles.debug();			
		}
		for (int i = 0; i < numNiveles ; i++) {
			tablaNiveles.add(niveles[i]).fill().expand().space(10f);
			tablaNiveles.row();
		}
		
		tablaControles.add(botonRegresar).fill().expand().space(10f);
		tablaControles.add(botonJugar).fill().expand().space(10f);		
		escena.addActor(fondo);
		escena.addActor(tablaNiveles);
		escena.addActor(tablaControles);
		escena.addActor(tituloNiveles);
		escena.addActor(subSeleccionar);
		escena.addActor(captura);
		escena.addActor(zonaTexto);		
		
		escena.setKeyboardFocus(botonJugar);
		
		ArrayList<TextButton> lista = new ArrayList<TextButton>();
		lista.add(botonJugar);
		for (int i = 0; i < numNiveles ; i++)
			lista.add(niveles[i]);
		lista.add(botonRegresar);
		escena.addListener(new MenuListener(escena, lista));
	}
}