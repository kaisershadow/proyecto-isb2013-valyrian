package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.valyrian.firstgame.Quetzal;

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
	private Color color;
	
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
        
        //Para ver las lineas de decupuracion
        if(debug)
        	Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		mouse_listeners();
		
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
		escena.dispose();
//		batch.dispose();
//		skin.dispose();
		Quetzal.getManejaRecursos().unload("images/menus/titulo_labusqueda.png");
		Quetzal.getManejaRecursos().unload("images/menus/titulo_quetzal.png");
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE MENU INICIO");
	}

	void inicializar_variables(){
		if(!Quetzal.getManejaRecursos().isLoaded("ui/skin/uiskin.json", Skin.class))
			Quetzal.getManejaRecursos().load("ui/skin/uiskin.json", Skin.class);
		Quetzal.getManejaRecursos().finishLoading();
		
	    skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
	    color = new Color(99, 145, 0, 0.4f);
	    batch = Quetzal.getSpriteBatch();
	    
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/mainmenu_BG.jpg"))
			Quetzal.getManejaRecursos().load("images/menus/mainmenu_BG.jpg", Texture.class);
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/titulo_quetzal.png"))
			Quetzal.getManejaRecursos().load("images/menus/titulo_quetzal.png", Texture.class);
		if(!Quetzal.getManejaRecursos().isLoaded("images/menus/titulo_labusqueda.png"))
			Quetzal.getManejaRecursos().load("images/menus/titulo_labusqueda.png", Texture.class);
		Quetzal.getManejaRecursos().finishLoading();
		
		textureFondo = Quetzal.getManejaRecursos().get("images/menus/mainmenu_BG.jpg");
	    fondo = new Image(textureFondo);
	    
	    textureTitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_quetzal.png");
	    tituloQuetzal = new Image(textureTitulo);
	    
	    textureSubtitulo = Quetzal.getManejaRecursos().get("images/menus/titulo_labusqueda.png");
	    subQuetzal = new Image(textureSubtitulo);
		
	    escena = new Stage();
		tabla = new Table(skin);
		
		botonJugar = new TextButton("Jugar", skin);
		botonConfiguraciones = new TextButton("Configuraciones", skin);
		botonPuntuaciones = new TextButton("Puntuaciones", skin);
		botonSalir = new TextButton("Salir del juego", skin);
		botonCreditos = new TextButton("Creditos", skin);
		
		botonJugar.setColor(color);
		botonConfiguraciones.setColor(color);
		botonPuntuaciones.setColor(color);
		botonSalir.setColor(color);
		botonCreditos.setColor(color);
	}
	
	void mouse_listeners(){
		
		botonSalir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(1f, 1f, 1f, 0.3f);
            }

			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(color);
            }	
		});
		
		botonJugar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(juego.pantallaSeleccionNivel);
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(1f, 1f, 1f, 0.3f);
            }

			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(color);
            }	
		});
		
		botonConfiguraciones.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(1f, 1f, 1f, 0.3f);
            }

			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(color);
            }	
		});
 
		botonPuntuaciones.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(juego.pantallaPuntuaciones);
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(1f, 1f, 1f, 0.3f);
            }

			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(color);
            }	
		});

		botonCreditos.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(juego.pantallaCreditos);
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(1f, 1f, 1f, 0.3f);
            }

			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				event.getListenerActor().setColor(color);
            }	
		});
				
	}
	
	/*private void keyboard_listeners(){
		// TODO implementar listeners para el teclado
	}*/
	
	void cargar_actores_escenario(){
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
	}

}