package com.valyrian.firstgame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.valyrian.firstgame.PrimerJuego;

public class MenuInicio implements Screen{
	
	private Stage escena;
	private Table tabla;
	private TextButton botonJugar;
	private TextButton botonConfiguraciones;
	private TextButton botonPuntuaciones;
	private TextButton botonSalir;
	private Skin skin;
	private SpriteBatch batch;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Texture textureSubtitulo;	
	private Image fondo;
	private Image tituloQuetzal;
	private Image subQuetzal;
	private PrimerJuego juego;
	
	public MenuInicio(PrimerJuego primerJuego) {
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
		Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

		escena.setViewport(width , height, true);
		
		tabla.setBounds(width*0.05f, 30, width*0.15f, height*0.3f);
		tabla.setSize(width*0.20f,height*0.30f);
		tabla.invalidateHierarchy();
		
		botonSalir.setBounds(width*0.75f , 30, width , height*0.30f);
		botonSalir.setSize(width*0.2f, (int)(height*0.3/3));
		
		fondo.setSize(width, height);
		
		tituloQuetzal.setBounds(width*0.25f, height*0.75f, width, height);
		tituloQuetzal.setSize(width*0.5f, height*0.2f);
		
		subQuetzal.setBounds(width*0.40f, height*0.70f, width, height);
		subQuetzal.setSize(width*0.3f, height*0.10f);		
	}

	@Override
	public void show() {
		
		FileHandle skinFile = Gdx.files.internal("ui/skin/uiskin.json"); 
        skin = new Skin(skinFile);
        batch = new SpriteBatch();
        
        textureFondo = new Texture(Gdx.files.internal("images/menus/mainmenu_BG.jpg"));
        fondo = new Image(textureFondo);
        textureTitulo = new Texture(Gdx.files.internal("images/menus/titulo_quetzal.png"));
        tituloQuetzal = new Image(textureTitulo);
        textureSubtitulo = new Texture(Gdx.files.internal("images/menus/titulo_labusqueda.png"));
        subQuetzal = new Image(textureSubtitulo);
		escena = new Stage();
		tabla = new Table(skin);
		
		Gdx.input.setInputProcessor(escena);	
		Color color;
		color = new Color(99, 145, 0, 0.4f);
	
		botonJugar = new TextButton("Jugar", skin);
		botonConfiguraciones = new TextButton("Configuraciones", skin);
		botonPuntuaciones = new TextButton("Puntuaciones", skin);
		botonSalir = new TextButton("Salir del juego", skin);
		
		
		//Agregar o crear otro listener para eventos del teclado
		botonSalir.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});
 
		botonJugar.setColor(color);
		botonConfiguraciones.setColor(color);
		botonPuntuaciones.setColor(color);
		botonSalir.setColor(color);
		
		
		//tabla.debug();
		tabla.add(botonJugar).space( 10f ).fill().expand();
		tabla.row();
		tabla.add(botonConfiguraciones).space(10f).fill().expand();
		tabla.row();
		tabla.add(botonPuntuaciones).space(10f).fill().expand();
		
		escena.addActor(fondo);
		escena.addActor(botonSalir);
		escena.addActor(tabla);
		escena.addActor(tituloQuetzal);
		escena.addActor(subQuetzal);
		
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		//dispose();
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
		textureFondo.dispose();
		textureSubtitulo.dispose();
		textureTitulo.dispose();
		skin.dispose();
	}
}
