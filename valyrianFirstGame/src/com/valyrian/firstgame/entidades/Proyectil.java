package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Proyectil extends Entidad{
	Texture textureProyectil;
public Proyectil() {
	textureProyectil = new Texture(Gdx.files.internal("personajes/dardo.png"));
	ancho =7;
	alto =3;
	this.posicion = new Vector2();
}

public void crearCuerpo(World mundo,float posX,float posY,int ancho,int alto,float velX) {
	///Definicicion del cuerpo
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.DynamicBody;
	bodyDef.position.set(posX,posY);
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
	cuerpo.setGravityScale(0);

     boxShape.dispose();
     cuerpo.setLinearVelocity(velX, 0);
     System.out.println("VEL BALA :"+cuerpo.getLinearVelocity().x);
	}

public void actualizarPosicionBala(){
		posicion.x=cuerpo.getPosition().x-ancho/2;
		posicion.y=cuerpo.getPosition().y-alto/2;
		System.out.println("ACTUALIZE LA BALA");
	}
public void renderBala(SpriteBatch batch){
	System.out.println("VOY A RENDERIZAR LA BALA");
	batch.draw(textureProyectil, this.posicion.x, this.posicion.y, this.ancho, this.alto);
	System.out.println("YA RENDERIZE LA BALA");
}


}