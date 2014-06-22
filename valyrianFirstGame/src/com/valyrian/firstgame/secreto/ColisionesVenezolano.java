package com.valyrian.firstgame.secreto;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.secreto.Venezolano;


public class ColisionesVenezolano implements ContactListener {
	
	Array<Body> toRemove;
	Array<Body> collected;
	Venezolano bucket;
	
	public ColisionesVenezolano(Venezolano d){
		super();
		bucket = d;
		toRemove = new Array<Body>();
		collected = new Array<Body>();
	}
	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a.getUserData() == null || b.getUserData() == null)
			return;
		
		if(a.getUserData().equals("Bucket") && b.getUserData().equals("Product")){
			if(!toRemove.contains(b.getBody(), true)){
				toRemove.add(b.getBody());
				collected.add(b.getBody());
			}
		}
		
		if(a.getUserData().equals("Ground") && b.getUserData().equals("Product")){
			if(!toRemove.contains(b.getBody(), true))
				toRemove.add(b.getBody());
		}
		
		if(b.getUserData().equals("Bucket") && a.getUserData().equals("Product")){
			if(!toRemove.contains(b.getBody(), true)){
				toRemove.add(a.getBody());
				collected.add(a.getBody());
			}
		}
		
		if(b.getUserData().equals("Ground") && a.getUserData().equals("Product")){
			if(!toRemove.contains(b.getBody(), true))
				toRemove.add(b.getBody());
		}
		
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a == null || b == null)
			return;
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public Array<Body> getListToRemove(){
		return toRemove;
	}
	
	public Array<Body> getCollected(){
		return collected;
	}

}