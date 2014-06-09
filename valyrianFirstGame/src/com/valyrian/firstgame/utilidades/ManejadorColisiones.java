package com.valyrian.firstgame.utilidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.entidades.Coleccionable;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.entidades.Proyectil;

public class ManejadorColisiones implements ContactListener {

	private Array<Body> cuerposABorrar;
	private Jugador player;


	public ManejadorColisiones(Jugador personaje) {
		super();
		cuerposABorrar= new Array<Body>();
		player=personaje;
	}


	@Override
	public void beginContact(Contact contact) {
	
		Fixture fA=contact.getFixtureA();
		Fixture fB=contact.getFixtureB();
		//Verificacion del salto del personaje, para evitar que pueda saltar varias veces
		if(fA.getUserData() !=null && fA.getUserData().equals("Salto")){
			this.saltar();
		}
		if(fB.getUserData() !=null && fB.getUserData().equals("Salto")){
			this.saltar();
		}
		
		
		//Colisiones del enemigo
		if(fA.getUserData() !=null && fA.getUserData().equals("Enemigo")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
				
				this.jugadorEnemigo(fB, fA);
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Proyectil")){
				
				this.enemigoProyectil(fA, fB);
				cuerposABorrar.add(fB.getBody());
			}
			if(((Enemigo)fA.getBody().getUserData()).estaMuerto()){
				cuerposABorrar.add(fA.getBody());
				player.setPuntaje(((Enemigo)fA.getBody().getUserData()).getDanio());
			}
		}
		
		
		if(fB.getUserData() !=null && fB.getUserData().equals("Enemigo")){
			if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
				
				this.jugadorEnemigo(fA, fB);
		
			}
			else if(fA.getUserData() !=null && fA.getUserData().equals("Proyectil")){
				
				this.enemigoProyectil(fB, fA);
				cuerposABorrar.add(fA.getBody());
			}
			if(((Enemigo)fB.getBody().getUserData()).estaMuerto()){
				cuerposABorrar.add(fB.getBody());
				player.setPuntaje(((Enemigo)fA.getBody().getUserData()).getDanio());
			}
		}
		
		//Colisiones del Proyectil
		if(fA.getUserData() !=null && fA.getUserData().equals("Proyectil")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
				
				this.jugadorProyectil(fB, fA);
				cuerposABorrar.add(fA.getBody());
			}
//			return;
		}
		
		
		if(fB.getUserData() !=null && fB.getUserData().equals("Proyectil")){
			if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
				this.jugadorProyectil(fA, fB);
				cuerposABorrar.add(fB.getBody());
			}
//			return;
		}

		
		System.out.println("Despues Proyectil");
		
		
		//Colisiones del jugador
		if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Muerte")){
				this.jugadorMuerte();
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Coleccionable")){
				this.jugadorColectable(fB);
				this.cuerposABorrar.add(fB.getBody());
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Meta")){
				this.jugadorMeta();
			}
		}
		
		if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
			if(fA.getUserData() !=null && fA.getUserData().equals("Muerte")){
				this.jugadorMuerte();
			}
			else if(fA.getUserData() !=null && fA.getUserData().equals("Coleccionable")){
				this.jugadorColectable(fA);
				this.cuerposABorrar.add(fA.getBody());
			}
			else if(fA.getUserData() !=null && fA.getUserData().equals("Meta")){
				this.jugadorMeta();
			}
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
			if(player.numContactos==0)
				player.estado = ESTADO_ACTUAL.Saltando;
		}
		if(fB.getUserData() !=null && fB.getUserData().equals("Salto")){
			player.numContactos--;
			if(player.numContactos==0)
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

	public Array<Body> getCuerposABorrar() {
		return cuerposABorrar;
	}


	public void setCuerposABorrar(Array<Body> listaABorrar) {
		cuerposABorrar= listaABorrar;
	}
	
//	METODOS DE MANEJO DE COLISIONES
	
	private void saltar(){
		player.numContactos++;
		if(player.getCuerpo().getLinearVelocity().x!= 0)
			player.estado = ESTADO_ACTUAL.Moviendose;
		else
			player.estado = ESTADO_ACTUAL.Quieto;
	}

	//	fA = Jugador, fB = Enemigo
	private void jugadorEnemigo(Fixture fA,Fixture fB){
		player.setVidaActual(((Enemigo)fB.getBody().getUserData()).getDanio()*-1);
		fA.getBody().applyLinearImpulse(new Vector2(player.getDireccion().x*-7f,0), fA.getBody().getWorldCenter(), true);
		if(player.estaMuerto())
			PAUSE = true;
	}
	
//	fA = Enemigo, fB = Proyectil
	private void enemigoProyectil(Fixture fA,Fixture fB){
		int danio = ((Proyectil)fB.getBody().getUserData()).getDamage();
		((Enemigo)fA.getBody().getUserData()).setVidaActual(-danio);
	}
	
//	fA = Jugador, fB = Proyectil
	private void jugadorProyectil(Fixture fA,Fixture fB){
		int danio = ((Proyectil)fB.getBody().getUserData()).getDamage();
		((Jugador)fA.getBody().getUserData()).setVidaActual(-danio);
		if(player.estaMuerto())
			PAUSE = true;
	}
	
	//fA = Jugador
	private void jugadorMuerte() {
	player.setVidaActual(-player.getMaxVida());
	PAUSE = true;
		
	}
	
	//fB = Colectable
	private void jugadorColectable(Fixture fB) {
		int p = ((Coleccionable)fB.getBody().getUserData()).getPuntos();
		player.setPuntaje(p);
	}

	private void jugadorMeta(){
		// TO DO
	}
}