package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class Plataforma extends EntidadDibujable {

	public Plataforma(float ancho, float alto, Vector2 vel, float posX, float posY, World m, ManejadorAnimacion ma) {
		super(ancho, alto, vel, posX, posY, m, ma);
		this.getCuerpo().setLinearVelocity(vel);
		this.direccion.y=1;
	}

	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto, float posX, float posY) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(posX/PIXELSTOMETERS,posY/PIXELSTOMETERS);
		bodyDef.gravityScale =0;
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(((ancho/2)/PIXELSTOMETERS),((alto/2)/PIXELSTOMETERS));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.density = 100;
		fixtureDef.friction = 0;
		fixtureDef.filter.categoryBits = BITS_PLATAFORMA;
		fixtureDef.filter.maskBits = BITS_JUGADOR  |BITS_PROYECTIL | BITS_SENSOR;
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.getMassData().mass = 0;
		this.cuerpo.setUserData(this);
		this.cuerpo.getFixtureList().first().setUserData("Plataforma");
		boxShape.setAsBox(0.5f/PIXELSTOMETERS, 1.5f/PIXELSTOMETERS,new Vector2((-ancho/2)/PIXELSTOMETERS, ((alto/2)/PIXELSTOMETERS)) , 0);
		cuerpo.createFixture(fixtureDef);
		boxShape.setAsBox(0.5f/PIXELSTOMETERS, 1.5f/PIXELSTOMETERS,new Vector2((ancho/2)/PIXELSTOMETERS, ((alto/2)/PIXELSTOMETERS)) , 0);
		cuerpo.createFixture(fixtureDef);
		boxShape.dispose();
	}

	@Override
	public void render(float deltaTime, SpriteBatch batch) {		
		TextureRegion frame = manAnim.getAnimacion(deltaTime);
		batch.draw(frame, this.cuerpo.getPosition().x - this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y- (this.alto/2)/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}
}