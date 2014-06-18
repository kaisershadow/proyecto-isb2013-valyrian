package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.valyrian.firstgame.Quetzal;

public class PantallaFinNivel implements Screen{
	
	private Quetzal juego;
	private SpriteBatch batch;
	private  BitmapFont font;
	
	public PantallaFinNivel(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(Quetzal.getManejaRecursos().get("images/menus/carga_BG.jpg",Texture.class),0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		font.draw(batch, "PRESIONE R PARA REINICIAR EL NIVEL \n o C PARA SELECCIONAR OTRO",V_WIDTH/2,V_HEIGHT/2);
		batch.end();	
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch=Quetzal.getSpriteBatch();
		font = new BitmapFont();
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {
					if(keycode == Keys.R){
						juego.setScreen(juego.pantallaSeleccionNivel);
						return true;
					}
					else if(keycode == Keys.C){
						juego.setScreen(juego.pantallaSeleccionNivel);
						return true;
					}
					return true;
			}
		});
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
font.dispose();
		
	}

}
