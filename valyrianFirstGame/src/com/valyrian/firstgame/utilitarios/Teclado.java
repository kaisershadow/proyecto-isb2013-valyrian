package com.valyrian.firstgame.utilitarios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.entidades.Proyectil;

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
		switch(keycode){
		
			case(Input.Keys.D):
				personaje.getCuerpo().setLinearVelocity(personaje.getVelocidad().x, personaje.getCuerpo().getLinearVelocity().y);
				personaje.mirandoDerecha = true;
				
				if(personaje.estado != ESTADO_ACTUAL.Saltando)
					personaje.estado=ESTADO_ACTUAL.Caminando;
				break;
			
			case(Input.Keys.A):
				personaje.getCuerpo().setLinearVelocity(-personaje.getVelocidad().x, personaje.getCuerpo().getLinearVelocity().y);
				personaje.mirandoDerecha = false;
			
				if(personaje.estado != ESTADO_ACTUAL.Saltando)
					personaje.estado=ESTADO_ACTUAL.Caminando;
				break;
			
			case(Input.Keys.SPACE):
				if(personaje.numContactos>0){
					if(personaje.estado !=ESTADO_ACTUAL.Saltando){
						//personaje.getCuerpo().applyForceToCenter(0, personaje.getVelocidad().y*50, true);
						personaje.getCuerpo().setLinearVelocity(personaje.getCuerpo().getLinearVelocity().x, personaje.getVelocidad().y);
						personaje.estado = ESTADO_ACTUAL.Saltando;
					}
				}
				break;
			
			case(Input.Keys.J):
				personaje.estado=ESTADO_ACTUAL.Atacando;
				Proyectil bala;
				bala = new Proyectil(20);

				if (personaje.mirandoDerecha)
					bala.crearCuerpo(personaje.getCuerpo().getWorld(), personaje.getCuerpo().getWorldCenter().x+personaje.getAncho()+10/ManejadorVariables.PIXELSTOMETERS,personaje.getCuerpo().getWorldCenter().y+8/ManejadorVariables.PIXELSTOMETERS, 3);
				else 
					bala.crearCuerpo(personaje.getCuerpo().getWorld(), personaje.getCuerpo().getWorldCenter().x-personaje.getAncho()-10/ManejadorVariables.PIXELSTOMETERS,personaje.getCuerpo().getWorldCenter().y+8/ManejadorVariables.PIXELSTOMETERS, -3);				
			
				personaje.disparos.add(bala);
			
				break;
		}
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.A:
		case Input.Keys.D:
			personaje.getCuerpo().setLinearVelocity(0, personaje.getCuerpo().getLinearVelocity().y);
			personaje.estado=ESTADO_ACTUAL.Quieto;
			break;
		
		case Input.Keys.SPACE:		
			if(personaje.getCuerpo().getLinearVelocity().x!=0)
				personaje.estado=ESTADO_ACTUAL.Caminando;
			else
				personaje.estado=ESTADO_ACTUAL.Quieto;
			break;
			
		case Input.Keys.J:
			if(Gdx.input.isButtonPressed(Keys.A) || Gdx.input.isButtonPressed(Keys.D))
				personaje.estado=ESTADO_ACTUAL.Caminando;
			else
				personaje.estado=ESTADO_ACTUAL.Quieto;
			
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
