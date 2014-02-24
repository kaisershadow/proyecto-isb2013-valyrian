package com.valyrian.firstgame.pantallas.pruebas;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.lang.String;
import java.util.ArrayList;
import java.util.Stack;

import sun.misc.Queue;
public class ChequeaColision implements ContactListener {
	
private ArrayList<Body> lista;
private ArrayList<Body> bala;
private boolean gameover;
	public ChequeaColision() {
		lista= new ArrayList<Body>();
		bala= new ArrayList<Body>();
		gameover= false;


		// TODO Auto-generated constructor stub
	}


	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Body A=contact.getFixtureA().getBody();
		Body B=contact.getFixtureB().getBody();
		if((A.getUserData()!=null) &&(B.getUserData()!=null)){
			if(A.getUserData().equals("Enemigo"))
				lista.add(A);
			else 
				if(B.getUserData().equals("Enemigo"))
					lista.add(B);
	
			if(A.getUserData().equals("bala"))
				bala.add(A);
			else
				if(B.getUserData().equals("bala"))
					bala.add(B);
		}
		
		if((A.getUserData()=="muerte") || (B.getUserData()=="muerte"))
			gameover=true;
			
		if((A.getUserData()=="bala") || (B.getUserData()=="bala")){
			if(A.getUserData()!=null)
				bala.add(A);
			else
				if(B.getUserData()!=null)
						bala.add(B);
		}
		


		
	}
	
	public boolean getGameOver(){
		return gameover;
	}
	
	public ArrayList<Body> getlistbala(){
		return bala;
	}
	
	public ArrayList<Body> getlista(){
		return lista;
	}
	

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		lista.clear();
		bala.clear();
		}
	
	

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
