package com.valyrian.firstgame.utilidades.input;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Proyectil;
import com.valyrian.firstgame.pantallas.PantallaNivel;

public class Teclado implements InputProcessor {

	PantallaNivel pantallaNivel;
	public Teclado(PantallaNivel p) {
		this.pantallaNivel = p;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(PAUSE && keycode !=Input.Keys.P)
			return true;
		
		switch(keycode){
			case(Input.Keys.P):
				if(!pantallaNivel.getJugador().estaMuerto())
					pantallaNivel.getJugador().Pausar();
				break;
			
			case(Input.Keys.D):
				pantallaNivel.getJugador().MoverDerecha();
				break;
			
			case(Input.Keys.A):
				pantallaNivel.getJugador().MoverIzquierda();
				break;
			
			case(Input.Keys.SPACE):
				pantallaNivel.getJugador().Saltar();
				break;
			
			case(Input.Keys.J):
				Proyectil bala= pantallaNivel.getJugador().Disparar(pantallaNivel.getWorld(),20);
				if(bala!=null)
					PantallaNivel.getEntidades().add(bala);
				break;
			case(Input.Keys.L):
				Proyectil bala2= pantallaNivel.getJugador().Disparar(pantallaNivel.getWorld(),10);
				if(bala2!=null)
					PantallaNivel.getEntidades().add(bala2);
				break;
			case(Input.Keys.O):
				pantallaNivel.getJugador().setmaxVida(10000);
				break;
		}
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		if(PAUSE && !pantallaNivel.getJugador().estaMuerto())
//			if(){
				return true;
//			}
		
		switch(keycode){
		
		case Input.Keys.A:
		case Input.Keys.D:
			pantallaNivel.getJugador().Detener();
			break;
		
		case Input.Keys.SPACE:		
			pantallaNivel.getJugador().Aterrizar();
			break;
			
		case Input.Keys.J:
			pantallaNivel.getJugador().Enfundar();			
			break;
			
		case Input.Keys.ESCAPE:
			pantallaNivel.getJuego().setScreen(pantallaNivel.getJuego().pantallaSeleccionNivel);
			break;
		case Input.Keys.R:
			pantallaNivel.getJuego().setScreen(pantallaNivel.getJuego().pantallaCargaNivel);
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
		if(!PAUSE)
			pantallaNivel.getCamera().zoom+=amount/20f;
		return true;
	}
}