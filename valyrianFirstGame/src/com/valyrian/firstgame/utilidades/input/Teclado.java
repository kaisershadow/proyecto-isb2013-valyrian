package com.valyrian.firstgame.utilidades.input;


import com.badlogic.gdx.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;

public class Teclado implements InputProcessor {

	private Jugador personaje;
	private PrimerJuego juego;
	private OrthographicCamera camera;
	
	public Teclado(Jugador player, OrthographicCamera cam, PrimerJuego game) {
		personaje = player;
		camera = cam;
		juego =game;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(personaje.isPaused() && keycode !=Input.Keys.P)
			return true;
		
		switch(keycode){
			case(Input.Keys.P):
				personaje.Pausar();
				break;
			
			case(Input.Keys.D):
				personaje.MoverDerecha();
				break;
			
			case(Input.Keys.A):
				personaje.MoverIzquierda();
				break;
			
			case(Input.Keys.SPACE):
				personaje.Saltar();
				break;
			
			case(Input.Keys.J):
				personaje.Disparar(20);
				break;
		}
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		if(personaje.isPaused())
			return true;
		
		switch(keycode){
		
		case Input.Keys.A:
		case Input.Keys.D:
			personaje.Detener();
			break;
		
		case Input.Keys.SPACE:		
			personaje.Aterrizar();
			break;
			
		case Input.Keys.J:
			personaje.Enfundar();			
			break;
			
		case Input.Keys.ESCAPE:
			juego.setScreen(juego.pantallaNiveles);
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		camera.zoom+=amount/20f;
		return true;
	}
}