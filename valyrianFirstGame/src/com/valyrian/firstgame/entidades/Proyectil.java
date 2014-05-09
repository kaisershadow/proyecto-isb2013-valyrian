package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.utilitarios.ManejadorUnidades;

public class Proyectil extends Entidad{
	
	private Texture textureProyectil;
	
	public Proyectil() {
		textureProyectil = new Texture(Gdx.files.internal("personajes/dardo.png"));
		ancho =7/ManejadorUnidades.PIXELSTOMETERS;
		alto =3/ManejadorUnidades.PIXELSTOMETERS;
		this.posicion = new Vector2();
	}

	public void crearCuerpo(World mundo,float posX,float posY,float velX) {
	
		///Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX,posY);
		bodyDef.angle = 0;
	
		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(ancho/2/ManejadorUnidades.PIXELSTOMETERS,alto/2/ManejadorUnidades.PIXELSTOMETERS);
		
		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setBullet(true);
		this.cuerpo.getFixtureList().first().setUserData("bala");
		cuerpo.setGravityScale(0.25f);
	    boxShape.dispose();
	    cuerpo.setLinearVelocity(velX, 0);
	}
	
	public void actualizarPosicionBala(){
			posicion.x=cuerpo.getPosition().x-ancho/2;
			posicion.y=cuerpo.getPosition().y-alto/2;
	}
	
	public void renderBala(SpriteBatch batch){
		batch.draw(textureProyectil, this.posicion.x, this.posicion.y, this.ancho, this.alto);
	}
	
	public void dispose(){
		textureProyectil.dispose();
		System.out.println("SE LLAMO AL DISPOSE DE PROYECTIL");
	}
}