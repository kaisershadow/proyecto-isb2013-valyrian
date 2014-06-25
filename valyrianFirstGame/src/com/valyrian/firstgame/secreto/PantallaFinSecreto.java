package com.valyrian.firstgame.secreto;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
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
import com.valyrian.firstgame.utilidades.input.MenuJoystick;

public class PantallaFinSecreto implements Screen{
	
	
	private Stage escena;
	private Skin skin;
	private Quetzal juego;
	private Texture textureFondo;
	private Image fondo;
	private Table tabla1, tabla2;
	private boolean aprobado;
	private MenuJoystick mjs;
	
	public PantallaFinSecreto(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		escena.act();
		escena.draw();
		if(debug){
			Table.drawDebug(escena);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		escena.setViewport(width, height);
		
		fondo.setSize(width, height);
		
		tabla1.setBounds(0, height*0.05f, width, height);
		tabla1.setSize(width, height*0.3f);
		
		tabla2.setBounds(0, height*0.6f, width, height);
		tabla2.setSize(width, height*0.3f);
	}

	@Override
	public void show() {
		
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		escena = new Stage();
		textureFondo = Quetzal.getManejaRecursos().get("secreto/fondo.jpg", Texture.class);
		fondo = new Image(textureFondo);
				
		tabla1 = new Table(skin);
		tabla2 = new Table(skin);
		tabla1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderbg.png"))));
		tabla2.setBackground(tabla1.getBackground());
		
		escena.addActor(fondo);
		escena.addActor(tabla1);
		escena.addActor(tabla2);
		
		inputs();
		if(aprobado)
			tabla2.add("LOGRASTE CONSEGUIR TODO LO QUE NECESITABAS... DEBES SER TODO UN BURGUESITO");
		else
			tabla2.add("SI NECESITAS MAS DINERO, HABLA CON ALGUN POLITICO...ELLOS TIENEN MUCHO");
		
		tabla1.add("PRESIONE R (BACK) PARA REINICIAR EL NIVEL"); 
		tabla1.row();
		tabla1.add("PRESIONE ENTER (START) PARA SELECCIONAR OTRO NIVEL");
		
		if(debug){
			tabla1.debug();
			tabla2.debug();
		}
	}

	
	private void inputs(){
		escena.addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
					if(keycode == Keys.R){
						juego.setScreen(juego.pantallaSecreto);
						return true;
					}
					else if(keycode == Keys.ENTER){
						juego.setScreen(juego.pantallaSeleccionNivel);
						return true;
					}
					return true;
			}
		});
		
		Gdx.input.setInputProcessor(escena);
		mjs = new MenuJoystick(escena);
		Controllers.addListener(mjs);
		
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
		Controllers.removeListener(mjs);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA FIN NIVEL SECRETO");
	}
	
	public void setAprobado(Boolean aprobado){
		this.aprobado = aprobado;
	}
}