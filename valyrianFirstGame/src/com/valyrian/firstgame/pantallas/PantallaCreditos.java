package com.valyrian.firstgame.pantallas;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.utilidades.recursos.ManejadorRecursos;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

public class PantallaCreditos implements Screen{
	
	private Stage escena;
	private Table tabla,container;
	private ScrollPane scroller;
	private TextButton botonSalir,t1,t2;
	private Skin skin;
	private SpriteBatch batch;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Image fondo;
	private Image tituloQuetzal;
	private PrimerJuego juego;
	private Color color;
	
	public PantallaCreditos(PrimerJuego primerJuego) {
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
		
		tituloQuetzal.setBounds(width*0.25f, height*0.75f, width, height);
		tituloQuetzal.setSize(width*0.5f, height*0.2f);
			
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
		batch.dispose();
		ManejadorRecursos.getInstancia().disposeTexture("titulo_creditos"); 
		skin.dispose();
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE CREDITOS");
	}

	void inicializar_variables(){
	    skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
	    color = new Color(99, 145, 0, 0.4f);
	    batch = new SpriteBatch();
	    
	    textureFondo = ManejadorRecursos.getInstancia().getTexture("mainmenu_BG");
	    fondo = new Image(textureFondo);
	    
	    
	    ManejadorRecursos.getInstancia().cargarTexture("images/menus/titulo_creditos.png", "titulo_creditos");
	    textureTitulo = ManejadorRecursos.getInstancia().getTexture("titulo_creditos"); 
	    tituloQuetzal = new Image(textureTitulo);
		
	    escena = new Stage();
		tabla = new Table(skin);
		container = new Table(skin);

		botonSalir = new TextButton("Atras", skin);
		botonSalir.setColor(color);
		
		t1 = new TextButton("Hola", skin);
		t1.setColor(color);
		t2 = new TextButton("Hola 22222", skin);
		t2.setColor(color);
		scroller = new ScrollPane(tabla, skin);

	}
	
	void mouse_listeners(){
		
		botonSalir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(juego.pantallaMenu);
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
		tabla.debug();
		
		tabla.add(t1).fill().expand();
		tabla.row();
		tabla.add(t2).fill().expand();
		tabla.row();
		//tabla.setFillParent(true);
		
		//scroller.setFillParent(true);
		container.add(scroller).fill().expand();
		
		escena.addActor(container);
	//	escena.addActor(scroller);
		escena.addActor(fondo);
		escena.addActor(botonSalir);
		escena.addActor(tituloQuetzal);
	}

}
