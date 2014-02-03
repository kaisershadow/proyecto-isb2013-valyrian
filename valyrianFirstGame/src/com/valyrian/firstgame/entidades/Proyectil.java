package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Proyectil extends Entidad{

public Proyectil() {
	
}	

public void crearCuerpo(World mundo,Vector2 pos,int ancho,int alto) {
	///Definicicion del cuerpo
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.DynamicBody;
	bodyDef.position.set(pos.x+ancho+10,pos.y+8);
	bodyDef.angle = 0;

	//Definicion de la forma del fixture
	PolygonShape boxShape = new PolygonShape();
	boxShape.setAsBox(ancho/2,alto/2);
	
	//Definicion del fixture
	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = boxShape;
	fixtureDef.friction = 0;
	fixtureDef.restitution = 0;
	fixtureDef.density = 1/(ancho*alto);
	fixtureDef.isSensor =false;
	
	this.cuerpo= mundo.createBody(bodyDef);
	this.cuerpo.createFixture(fixtureDef);		
	this.cuerpo.setFixedRotation(true);
	this.cuerpo.setBullet(true);
	cuerpo.setGravityScale(0.005f);

     boxShape.dispose();
     cuerpo.setLinearVelocity(300, 0);
     System.out.println("VEL BALA :"+cuerpo.getLinearVelocity().x);
}


}