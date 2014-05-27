package com.valyrian.firstgame.refactor.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Proyectil extends Entidad{
	
	private int damage;
	public Proyectil(float ancho,float alto,Vector2 pos,Vector2 vel,World mundo,Texture textura,int danio) {
		super(ancho, alto, pos, vel, mundo, textura);
		setDamage(danio);
	}
	
	public int getDamage() { return damage; }
	
	public void setDamage(int damage) { this.damage = damage; }
	
	@Override
	protected void crearCuerpo(World mundo, Vector2 pos, float ancho, float alto) {
		///Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.x,pos.y);
		bodyDef.angle = 0;
	
		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(ancho/2/PIXELSTOMETERS,alto/2/PIXELSTOMETERS);
		
		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_PROYECTIL;
		fixtureDef.filter.maskBits = BITS_ENTORNO|BITS_PLATAFORMA;		
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setBullet(true);
		this.cuerpo.setUserData(this);
		
		this.cuerpo.getFixtureList().first().setUserData("Proyectil");
		cuerpo.setGravityScale(0.25f);
	    boxShape.dispose();
	    cuerpo.setLinearVelocity(this.velocidad.x, 0);
	}
	
	public void render(float deltaTime,SpriteBatch batch){
		batch.draw(textura, this.posicion.x-this.ancho/2/PIXELSTOMETERS, this.posicion.y-this.alto/2/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}
	
	public void dispose(){
		if(debug)
		System.out.println("SE LLAMO AL DISPOSE DE PROYECTIL");
	}


}
