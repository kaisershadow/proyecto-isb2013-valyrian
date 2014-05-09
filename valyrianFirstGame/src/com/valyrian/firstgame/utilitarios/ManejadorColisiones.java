package com.valyrian.firstgame.utilitarios;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;

import java.lang.String;
import java.util.ArrayList;
import java.util.Stack;

import sun.misc.Queue;
public class ManejadorColisiones implements ContactListener {

	private ArrayList<Fixture> ListaABorrar;
	private ArrayList<Fixture> ListaEnemigo;


//private int numcontacts=0;

private boolean gameover;
private Jugador player;


	public ManejadorColisiones(Jugador personaje) {
		ListaABorrar= new ArrayList<Fixture>();
		ListaEnemigo = new ArrayList<Fixture>();
		gameover= false;
		player=personaje;
	}


	@Override
	public void beginContact(Contact contact) {
//		// TODO Auto-generated method stub
//		Body A=contact.getFixtureA().getBody();
//		Body B=contact.getFixtureB().getBody();
//		if((A.getUserData()!=null) &&(B.getUserData()!=null)){
//			if(A.getUserData().equals("Enemigo"))
//				lista.add(A);
//			else 
//				if(B.getUserData().equals("Enemigo"))
//					lista.add(B);
//	
//			if(A.getUserData().equals("bala"))
//				bala.add(A);
//			else
//				if(B.getUserData().equals("bala"))
//					bala.add(B);
//		}
//		
//		if((A.getUserData()=="muerte") || (B.getUserData()=="muerte"))
//			gameover=true;
//			
//		if((A.getUserData()=="bala") || (B.getUserData()=="bala")){
//			if(A.getUserData()!=null)
//				bala.add(A);
//			else
//				if(B.getUserData()!=null)
//						bala.add(B);

	
		Fixture A=contact.getFixtureA();
		Fixture B=contact.getFixtureB();
		if((A.getUserData()=="Personaje" || B.getUserData()=="Personaje") && (A.getUserData()!=null && B.getUserData()!=null)){
			if(A.getUserData().equals("Enemigo"))
				ListaABorrar.add(A);
			else
				if(B.getUserData().equals("Enemigo"))
					ListaABorrar.add(B);
						
			if(A.getUserData().equals("muerte") || B.getUserData().equals("muerte"))
				gameover=true;
		}
		
		if(A.getUserData()=="bala" || B.getUserData()=="bala"){
			if(A.getUserData()==null)
					ListaABorrar.add(B);
			else
				if(A.getUserData().equals("Enemigo")){
					ListaABorrar.add(A);					
					ListaABorrar.add(B);
				}

			if(B.getUserData()==null)
				ListaABorrar.add(A);	
			else
				if(B.getUserData().equals("Enemigo")){
					ListaABorrar.add(A);
					ListaABorrar.add(B);
				}
		}
	
	
	
	
	
	
	}
//		if(A.getFixtureList().size > 1){
//			if((String)A.getFixtureList().get(1).getUserData() == "Salto"){
//				numcontacts++;
//				player.estado=ESTADO_ACTUAL.Quieto;
//			}
//		} else if(B.getFixtureList().size > 1){
//			if((String)B.getFixtureList().get(1).getUserData() == "Salto"){
//				numcontacts++;
//				player.estado=ESTADO_ACTUAL.Quieto;
//			}
//		}


		
	
	
	public boolean getGameOver(){
		return gameover;
	}

	@Override
	public void endContact(Contact contact) {
//		Body A=contact.getFixtureA().getBody();
//		Body B=contact.getFixtureB().getBody();
//		if(A.getFixtureList().size > 1){
//			if((String)A.getFixtureList().get(1).getUserData() == "Salto"){
//				numcontacts--;
//				if(numcontacts<0)
//					player.estado=ESTADO_ACTUAL.Saltando;
//			}
//		}else if(B.getFixtureList().size > 1){
//			if((String)B.getFixtureList().get(1).getUserData() == "Salto"){
//				numcontacts--;
//				if(numcontacts<0)
//					player.estado=ESTADO_ACTUAL.Saltando;
//			}
//		}
	}
	
	

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}


	public ArrayList<Fixture> getListaEnemigo() {
		return ListaEnemigo;
	}


	public void setListaEnemigo(ArrayList<Fixture> listaEnemigo) {
		ListaEnemigo = listaEnemigo;
	}


	public ArrayList<Fixture> getListaABorrar() {
		return ListaABorrar;
	}


	public void setListaABorrar(ArrayList<Fixture> listaABorrar) {
		ListaABorrar = listaABorrar;
	}

}
