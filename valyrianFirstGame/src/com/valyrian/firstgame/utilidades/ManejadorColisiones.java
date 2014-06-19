package com.valyrian.firstgame.utilidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.entidades.Coleccionable;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.entidades.Plataforma;
import com.valyrian.firstgame.entidades.Proyectil;

public class ManejadorColisiones implements ContactListener {

	private Array<Body> cuerposABorrar;
	private Jugador player;
	private Sound moneda,dolor,muerte;

	public ManejadorColisiones(Jugador personaje) {
		super();
		cuerposABorrar= new Array<Body>();
		player=personaje;
		moneda = Quetzal.getManejaRecursos().get("audio/moneda.ogg",Sound.class);
		dolor=Quetzal.getManejaRecursos().get("audio/dolor.wav",Sound.class);
		muerte=Quetzal.getManejaRecursos().get("audio/muerte.wav",Sound.class);
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
		
		//Colisiones del Proyectil
		if(fA.getUserData() !=null && fA.getUserData().equals("Proyectil")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
				
				this.jugadorProyectil(fB, fA);
				if(!cuerposABorrar.contains(fA.getBody(),true))
					cuerposABorrar.add(fA.getBody());
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Entorno"))
				if(!cuerposABorrar.contains(fA.getBody(),true))
					cuerposABorrar.add(fA.getBody());
		}
		
		
		if(fB.getUserData() !=null && fB.getUserData().equals("Proyectil")){
			if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
				this.jugadorProyectil(fA, fB);
				if(!cuerposABorrar.contains(fB.getBody(),true))
					cuerposABorrar.add(fB.getBody());
			}
			else if(fA.getUserData() !=null && fA.getUserData().equals("Entorno"))
				if(!cuerposABorrar.contains(fB.getBody(),true))
					cuerposABorrar.add(fB.getBody());
		}
		
		//Colisiones del enemigo
		if(fA.getUserData() !=null && fA.getUserData().equals("Enemigo")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Jugador")){
				
				this.jugadorEnemigo(fB, fA);
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Proyectil")){
				
				this.enemigoProyectil(fA, fB);
				if(!cuerposABorrar.contains(fB.getBody(),true))
					cuerposABorrar.add(fB.getBody());
			}
			if(((Enemigo)fA.getBody().getUserData()).estaMuerto()){
				if(!cuerposABorrar.contains(fA.getBody(),true))
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
				if(!cuerposABorrar.contains(fA.getBody(),true))
					cuerposABorrar.add(fA.getBody());
			}
			if(((Enemigo)fB.getBody().getUserData()).estaMuerto()){
				if(!cuerposABorrar.contains(fB.getBody(),true))
					cuerposABorrar.add(fB.getBody());
				player.setPuntaje(((Enemigo)fB.getBody().getUserData()).getDanio());
			}
		}
		
		//Colisiones del jugador
		if(fA.getUserData() !=null && fA.getUserData().equals("Jugador")){
			if(fB.getUserData() !=null && fB.getUserData().equals("Muerte")){
				this.jugadorMuerte();
			}
			else if(fB.getUserData() !=null && fB.getUserData().equals("Coleccionable")){
				this.jugadorColectable(fB);
				if(!cuerposABorrar.contains(fB.getBody(),true))
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
				if(!cuerposABorrar.contains(fA.getBody(),true))
					this.cuerposABorrar.add(fA.getBody());
			}
			else if(fA.getUserData() !=null && fA.getUserData().equals("Meta")){
				this.jugadorMeta();
			}
		}

		if(fA.getUserData()!=null && fA.getUserData().equals("Plataforma")){
			if(fB.getUserData()!=null && fB.getUserData().equals("Sensor")){
				this.plataformaSensor(fA);
			}else if(fB.getUserData()!=null && fB.getUserData().equals("Proyectil")){
				if(!cuerposABorrar.contains(fB.getBody(),true))
					this.cuerposABorrar.add(fB.getBody());
			}
		}
		if(fB.getUserData()!=null && fB.getUserData().equals("Plataforma")){
			if(fA.getUserData()!=null && fA.getUserData().equals("Sensor")){
				this.plataformaSensor(fB);
			}else if(fA.getUserData()!=null && fA.getUserData().equals("Proyectil")){
				if(!cuerposABorrar.contains(fA.getBody(),true))
					this.cuerposABorrar.add(fA.getBody());
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
		dolor.play(VOLUMEN*0.3f);
		if(player.estaMuerto()){
			muerte.play(VOLUMEN*0.8f);
			player.finJuego = true;
		}
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
		dolor.play(VOLUMEN*0.3f);
		if(player.estaMuerto()){
			muerte.play(VOLUMEN*0.8f);
			player.finJuego = true;
		}
	}
	
	//fA = Jugador
	private void jugadorMuerte() {
	player.setVidaActual(-player.getMaxVida());
	muerte.play(VOLUMEN*0.8f);
	player.finJuego = true;
	}
	
	//fB = Colectable
	private void jugadorColectable(Fixture fB) {
		int p = ((Coleccionable)fB.getBody().getUserData()).getPuntos();
		moneda.play(VOLUMEN*0.5f);
		player.setPuntaje(p);
	}

	private void jugadorMeta(){
		player.finJuego = true;
		System.out.println("LLEGO AL FINAL DEL NIVEL");
	}
	
	private void plataformaSensor(Fixture fA){
		float dirX = ((Plataforma)fA.getBody().getUserData()).getDireccion().x*=-1;
		float dirY = ((Plataforma)fA.getBody().getUserData()).getDireccion().y*=-1;
		float velX = ((Plataforma)fA.getBody().getUserData()).getVelocidad().x;
		float velY = ((Plataforma)fA.getBody().getUserData()).getVelocidad().y;
		fA.getBody().setLinearVelocity(velX*dirX,velY*dirY);
	}
}