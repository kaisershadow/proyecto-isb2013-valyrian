package com.valyrian.firstgame.utilitarios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.entidades.Proyectil;

public class Teclado implements InputProcessor {

	private Jugador personaje;
	
	public Teclado(Jugador player) {
		personaje = player;
	}


	@Override
	public boolean keyDown(int keycode) {
		//float cuerpoVelX = cuerpo.getLinearVelocity().x;
	//	float desiredVel = 0;
	 		
		switch(keycode){
		case(Input.Keys.D):
//			if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
//				personaje.getDesiredVel().x = 0;
//			else
			//	personaje.getDesiredVel().x = personaje.getVelocidad().x;
			//personaje.getDesiredVel().y = -personaje.getVelocidad().y;
			//personaje.getCuerpo().applyLinearImpulse(personaje.getVelocidad().x,0,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
	//	if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
		//	personaje.getCuerpo().applyLinearImpulse(0,personaje.getVelocidad().y,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
		
		

		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			personaje.getCuerpo().setLinearVelocity(personaje.getVelocidad().x,personaje.getVelocidad().y);
		else
			personaje.getCuerpo().setLinearVelocity(personaje.getVelocidad().x, 0);
			
		personaje.mirandoDerecha = true;
		personaje.estado=ESTADO_ACTUAL.Caminando;
			
			break;
		case(Input.Keys.A):
			
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			personaje.getCuerpo().setLinearVelocity(-personaje.getVelocidad().x,personaje.getVelocidad().y);
		else
			personaje.getCuerpo().setLinearVelocity(-personaje.getVelocidad().x,0);
			
			
		//personaje.getCuerpo().applyLinearImpulse(-personaje.getVelocidad().x,0,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
//		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
//			personaje.getCuerpo().applyLinearImpulse(0,personaje.getVelocidad().y,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
			personaje.mirandoDerecha = false;
			personaje.estado=ESTADO_ACTUAL.Caminando;
			break;
		case(Input.Keys.SPACE):
//			if(estado== ESTADO_ACTUAL.Caminando){
//				if(mirandoDerecha)
//					desiredVel.x = velocidad.x;
//				else
//					desiredVel.x = -velocidad.x;
//			}
			personaje.getCuerpo().setGravityScale(0);
			
		
		
			//personaje.getDesiredVel().y = personaje.getVelocidad().y;
			//personaje.getCuerpo().applyLinearImpulse(0,personaje.getVelocidad().y,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
			if(Gdx.input.isKeyPressed(Input.Keys.A))
				personaje.getCuerpo().setLinearVelocity(-personaje.getVelocidad().x,personaje.getVelocidad().y);
				//personaje.getCuerpo().applyLinearImpulse(-personaje.getVelocidad().x,0,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
			else if(Gdx.input.isKeyPressed(Keys.D))
				//personaje.getCuerpo().applyLinearImpulse(personaje.getVelocidad().x,0,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
				personaje.getCuerpo().setLinearVelocity(personaje.getVelocidad().x,personaje.getVelocidad().y);
			else
			personaje.getCuerpo().setLinearVelocity(0,personaje.getVelocidad().y);
			
			
			//personaje.getCuerpo().applyLinearImpulse(personaje.getVelocidad().x,personaje.getVelocidad().y,personaje.getCuerpo().getWorldCenter().x,personaje.getCuerpo().getWorldCenter().y,true);
			personaje.estado = ESTADO_ACTUAL.Saltando;
				//IMPLEMENTAR SALTO	
			
			
			//cuerpo.getPosition().y+=32f;
				break;
		case(Input.Keys.J):
			personaje.estado=ESTADO_ACTUAL.Atacando;
			Proyectil bala;
			bala = new Proyectil();
			bala.crearCuerpo(personaje.getCuerpo().getWorld(), personaje.getCuerpo().getWorldCenter(), 7, 3);
	//		cuerpo.setLinearVelocity(100, 0);
		break;
		}

		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.A:
		case Input.Keys.D:
		//	personaje.getDesiredVel().x=0;
			//personaje.getDesiredVel().y = -personaje.getVelocidad().y;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
				personaje.getCuerpo().setLinearVelocity(0, personaje.getVelocidad().y);
			else
				personaje.getCuerpo().setLinearVelocity(0, 0);
				
			personaje.estado=ESTADO_ACTUAL.Quieto;
		//	cuerpo.setLinearVelocity(0,cuerpo.getLinearVelocity().y);
			break;
		case Input.Keys.SPACE:
		//	if(personaje.getCuerpo().getLinearVelocity().y<personaje.getVelocidad().y)
			
			//personaje.getDesiredVel().y=personaje.getVelocidad().y;
			personaje.getCuerpo().setGravityScale(1);
	//		personaje.getDesiredVel().y=(personaje.getCuerpo().getWorld().getGravity().y)*60;
	//	cuerpo.setLinearVelocity(cuerpo.getLinearVelocity().x, 0);
			if(personaje.getCuerpo().getLinearVelocity().x!=0)
				personaje.estado=ESTADO_ACTUAL.Caminando;
			else
				personaje.estado=ESTADO_ACTUAL.Quieto;
			break;
			
		case Input.Keys.J:
			//if(estado!=ESTADO_ACTUAL.Quieto)
			personaje.estado=ESTADO_ACTUAL.Quieto;
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
		// TODO Auto-generated method stub
		return false;
	}
}
