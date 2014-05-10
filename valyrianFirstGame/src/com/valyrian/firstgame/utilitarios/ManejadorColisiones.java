package com.valyrian.firstgame.utilitarios;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Proyectil;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.entidades.Rana;
public class ManejadorColisiones implements ContactListener {

	private Array<Body> cuerposABorrar;
	private boolean gameover;
	private Jugador player;


	public ManejadorColisiones(Jugador personaje) {
		super();
		cuerposABorrar= new Array<Body>();
		gameover= false;
		player=personaje;
	}


	@Override
	public void beginContact(Contact contact) {
	
		Fixture fA=contact.getFixtureA();
		Fixture fB=contact.getFixtureB();
		//Verificacion del salto del personaje, para evitar que pueda saltar varias veces
		if(fA.getUserData() !=null && fA.getUserData().equals("Salto")){
			player.numContactos++;
		}
		if(fB.getUserData() !=null && fB.getUserData().equals("Salto")){
			player.numContactos++;
		}
		
		if(fA.getUserData() !=null && fA.getUserData().equals("Enemigo")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
				System.out.println("VIDA ANTES: "+player.getVidaActual());
				player.cambiarVidaActual(((Rana)fA.getBody().getUserData()).getDamage()*-1);
				System.out.println("VIDA DESPUEs: "+player.getVidaActual());
			}else if(fB.getUserData() !=null && fB.getUserData().equals("Proyectil")){
				System.out.println("VIDA RANA ANTES FA: "+((Rana)fA.getBody().getUserData()).getVidaActual());
				//((Rana)fA.getBody().getUserData()).cambiarVidaActual(((Proyectil)fB.getUserData()).getDamage()*-1);
				((Rana)fA.getBody().getUserData()).cambiarVidaActual(-10);
				System.out.println("VIDA RANA DESPUEs FA: "+((Rana)fA.getBody().getUserData()).getVidaActual());
			}
			if(((Rana)fA.getBody().getUserData()).estaMuerto())
				cuerposABorrar.add(fA.getBody());
		}
		if(fB.getUserData() !=null && fB.getUserData().equals("Enemigo")){
			if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
				System.out.println("VIDA ANTES FB: "+player.getVidaActual());
				player.cambiarVidaActual(((Rana)fB.getBody().getUserData()).getDamage()*-1);
				fA.getBody().applyLinearImpulse(new Vector2(-10,0), fA.getBody().getWorldCenter(), true);
				System.out.println("VIDA DESPUEs FB: "+player.getVidaActual());
			}else if(fA.getUserData() !=null && fA.getUserData().equals("Proyectil")){
				System.out.println("VIDA RANA ANTES FB: "+((Rana)fB.getBody().getUserData()).getVidaActual());
//				((Rana)fB.getBody().getUserData()).cambiarVidaActual(((Proyectil)fA.getUserData()).getDamage()*-1);
				((Rana)fB.getBody().getUserData()).cambiarVidaActual(-10);

				System.out.println("VIDA RANA DESPUEs FB: "+((Rana)fB.getBody().getUserData()).getVidaActual());
			}else if(((Rana)fB.getBody().getUserData()).estaMuerto())
				cuerposABorrar.add(fB.getBody());
		}
	}	
	
	

	@Override
	public void endContact(Contact contact) {
		Fixture fA=contact.getFixtureA();
		Fixture fB=contact.getFixtureB();
		
		if(fA ==null || fB==null)
			return;
		
		if(fA.getUserData() !=null && fA.getUserData().equals("Salto")){
			player.numContactos--;
			player.estado = ESTADO_ACTUAL.Saltando;
		}
		if(fB.getUserData() !=null && fB.getUserData().equals("Salto")){
			player.numContactos--;
			player.estado = ESTADO_ACTUAL.Saltando;
		}
	}
	
	

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	public boolean getGameOver(){
		return gameover;
	}

	public Array<Body> getCuerposABorrar() {
		return cuerposABorrar;
	}


	public void setCuerposABorrar(Array<Body> listaABorrar) {
		cuerposABorrar= listaABorrar;
	}
}