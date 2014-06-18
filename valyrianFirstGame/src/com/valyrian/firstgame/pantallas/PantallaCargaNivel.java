package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.utilidades.BarraCarga;

public class PantallaCargaNivel implements Screen {

	private Stage escena;
	private Skin skin;
	private SpriteBatch batch;
	private BarraCarga barra;
	private Quetzal juego;
	private Screen pantallaActual;
	private Texture textureFondo;
	private Image fondo;
	private Table tabla;
	private String nivel;
	
	
	public PantallaCargaNivel(Quetzal game) {
		this.juego = game;
		this.pantallaActual = null;
		this.nivel = "nivel1";
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		escena.act(delta);
		escena.draw();
		batch.begin();
		
		barra.draw(batch, 1);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		escena.setViewport(width, height);
		
		fondo.setSize(width, height);
		
		tabla.setBounds(0, height*0.5f, width, height);
		tabla.setSize(width, height*0.5f);
	}

	@Override
	public void show() {
		this.batch = Quetzal.getSpriteBatch();
		barra = new BarraCarga();
		escena = new Stage();
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		
		textureFondo = Quetzal.getManejaRecursos().get("images/menus/carga_BG.jpg",Texture.class);
		fondo = new Image(textureFondo);
		
		
		tabla = new Table(skin);
		tabla.add(Gdx.files.internal("ui/texto/"+nivel+".txt").readString()).expand();
		tabla.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderbg.png"))));
		escena.addActor(fondo);
		escena.addActor(tabla);
		
		Gdx.input.setInputProcessor(escena);
		
		escena.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(Quetzal.getManejaRecursos().getProgress()==1){
					if(keycode == Keys.SPACE){
						juego.setScreen(juego.pantallaNivel);
						return true;
					}
					else if(keycode == Keys.I){
						juego.setScreen(juego.pantallaSecreto);
						return true;
					}
					return true;
				}
				return false;
			}
		});
		
		
		//Cargar los recursos necesarios
		Quetzal.getManejaRecursos().load("personajes/nativo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/rana.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/avispa.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/dardo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/moneda.png", Texture.class);
		
		Quetzal.getManejaRecursos().load("images/corazon.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/hud.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya1.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya2.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya3.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/pausa.png", Texture.class);
//		Quetzal.getManejaRecursos().load("images/moneda.png", Texture.class);
		
		Quetzal.getManejaRecursos().load("audio/"+nivel+".mp3", Music.class);
		Quetzal.getManejaRecursos().load("audio/salto.wav", Sound.class);
		Quetzal.getManejaRecursos().load("audio/moneda.ogg",Sound.class);
		Quetzal.getManejaRecursos().load("audio/disparo.mp3",Sound.class);
		Quetzal.getManejaRecursos().load("audio/dolor.wav",Sound.class);
		Quetzal.getManejaRecursos().load("audio/muerte.wav",Sound.class);
		
		Quetzal.getManejaRecursos().load("images/platnivel2.png",Texture.class);
		Quetzal.getManejaRecursos().load("images/platnivel3.png",Texture.class);
		Quetzal.getManejaRecursos().load("secreto/balde.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/gota.png", Texture.class);
		
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
		barra.dispose();
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA CARGA NIVEL");
//		Quetzal.getManejaRecursos().unload("images/menus/carga_BG.jpg");
	}
	
	public void setPantallaActual(Screen pa){ this.pantallaActual =  pa; }
	
	public Screen getPantallaActual(){ return this.pantallaActual; }
	
	public void setNivel(String nivel){
		this.nivel = nivel;
	}
}