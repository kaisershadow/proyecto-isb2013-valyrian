package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.valyrian.firstgame.utilidades.input.MenuJoystick;

public class PantallaCargaNivel implements Screen {

	private Stage escena;
	private Skin skin;
	private BarraCarga barra;
	private Quetzal juego;
	private Screen pantallaActual;
	private Texture textureFondo;
	private Image fondo;
	private Table tabla,tabla2;
	private String nivel;
	private MenuJoystick mjs;

	
	
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
		if(debug)
			Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		escena.setViewport(width, height);
		fondo.setSize(width, height);
		
		tabla.setBounds(width*0.1f, height*0.2f, width*0.8f, height);
		
		tabla2.setBounds(width*0.02f, -height*0.08f, width*0.96f, height*0.3f);
		
		barra.setBounds(width*0.5f-barra.barra.getWidth()/2,0.2f*height, barra.barra.getWidth(), barra.barra.getHeight());
	}

	@Override
	public void show() {
		barra = new BarraCarga();
		escena = new Stage();
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		
		textureFondo = Quetzal.getManejaRecursos().get("images/menus/carga_BG.jpg",Texture.class);
		fondo = new Image(textureFondo);
		
		
		tabla = new Table(skin);
		tabla2 = new Table(skin);
		tabla.add(Gdx.files.internal("ui/texto/"+nivel+".txt").readString()).expand();
		tabla.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderbg.png"))));
		tabla2.setBackground(tabla.getBackground());
		escena.addActor(fondo);
		escena.addActor(tabla);
		escena.addActor(tabla2);
		escena.addActor(barra);
		
		
		
		if(debug)
			tabla.debug();
		Gdx.input.setInputProcessor(escena);
		
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
		escena.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(Quetzal.getManejaRecursos().getProgress()==1){
					if(keycode == Keys.ENTER){
						juego.setScreen(juego.pantallaNivel);
						return true;
					}
					else if(keycode == Keys.CONTROL_LEFT || keycode ==Keys.CONTROL_RIGHT){
						juego.setScreen(juego.pantallaSecreto);
						return true;
					}
					return true;
				}
				return false;
			}
		});
		
		
		//Cargar texturas
			//Personajes
		Quetzal.getManejaRecursos().load("personajes/nativo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/rana.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/avispa.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/dardo.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/moneda.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/murcielago.png", Texture.class);
		Quetzal.getManejaRecursos().load("personajes/raton.png", Texture.class);
		//Varias
		Quetzal.getManejaRecursos().load("images/corazon.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/hud.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya1.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya2.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/calendario_maya3.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/pausa.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/juego_acabado.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/juego_completo.png", Texture.class);
		Quetzal.getManejaRecursos().load("images/platnivel2.png",Texture.class);
		Quetzal.getManejaRecursos().load("images/platnivel3.png",Texture.class);
		//Cargar audio
		Quetzal.getManejaRecursos().load("audio/"+nivel+".mp3", Music.class);
		Quetzal.getManejaRecursos().load("audio/salto.wav", Sound.class);
		Quetzal.getManejaRecursos().load("audio/calendario.wav", Sound.class);
		Quetzal.getManejaRecursos().load("audio/moneda.ogg",Sound.class);
		Quetzal.getManejaRecursos().load("audio/disparo.mp3",Sound.class);
		Quetzal.getManejaRecursos().load("audio/dolor.wav",Sound.class);
		Quetzal.getManejaRecursos().load("audio/muerte.wav",Sound.class);
		Quetzal.getManejaRecursos().load("audio/patria.mp3",Music.class);
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
		Controllers.removeListener(mjs);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA CARGA NIVEL");
	}
	
	public void setPantallaActual(Screen pa){ this.pantallaActual =  pa; }
	
	public Screen getPantallaActual(){ return this.pantallaActual; }
	
	public void setNivel(String nivel){ this.nivel = nivel; }
}