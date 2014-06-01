package com.valyrian.firstgame.secreto;



import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.secreto.Bucket;


public class DContactListener implements ContactListener {
	
	Array<Body> toRemove;
	Bucket bucket;
	
	public DContactListener(Bucket d){
		super();
		bucket = d;
		toRemove = new Array<Body>();
	}
	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a.getUserData() == null || b.getUserData() == null)
			return;
		
		if(a.getUserData().equals("Bucket") && b.getUserData().equals("Product")){
			toRemove.add(b.getBody());
			bucket.setPuntuacion(bucket.getPuntuacion()+1);
		}
		
		if(a.getUserData().equals("Ground") && b.getUserData().equals("Product")){
			toRemove.add(b.getBody());
		}
		
		if(b.getUserData().equals("Bucket") && a.getUserData().equals("Product")){
			toRemove.add(a.getBody());
			bucket.setPuntuacion(bucket.getPuntuacion()+1);
		}
		
		if(b.getUserData().equals("Ground") && a.getUserData().equals("Product")){
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

}