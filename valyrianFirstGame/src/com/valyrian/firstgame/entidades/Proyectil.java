package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class Proyectil extends EntidadDibujable{
	
	private int damage;
	public Proyectil(Quetzal game,float ancho,float alto,float posIniX, float posIniY, Vector2 vel,World mundo,ManejadorAnimacion ma,int danio) {
		super(game,ancho, alto, vel,posIniX,posIniY,mundo,ma);
		setDamage(danio);
	}
	
	public int getDamage() { return damage; }
	
	public void setDamage(int damage) { this.damage = damage; }
	
	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX,float posY) {
		//Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.angle = 0;
		bodyDef.position.set(posX,posY);

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
		batch.draw(manAnim.getAnimacion(deltaTime, 0), this.cuerpo.getPosition().x-this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y-this.alto/2/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}
	
	public void dispose(){
		if(debug)
		System.out.println("SE LLAMO AL DISPOSE DE PROYECTIL");
	}
}
