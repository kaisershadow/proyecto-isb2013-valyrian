package com.valyrian.firstgame.secreto;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.entidades.EntidadDibujable;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class Venezolano extends EntidadDibujable{

	private int sueldo = 0;
	public Boolean mirandoDerecha = true;

	public Venezolano(float ancho, float alto, Vector2 vel, float posX,float posY, World m, ManejadorAnimacion ma) {
		super(ancho, alto, vel, posX, posY, m, ma);
	}

	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX, float posY) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX/PIXELSTOMETERS, posY/PIXELSTOMETERS);
		cuerpo = mundo.createBody(bodyDef);
		cuerpo.setUserData(this);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((ancho/2)/PIXELSTOMETERS, (alto/2)/PIXELSTOMETERS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor = false;
		
		cuerpo.createFixture(fixtureDef).setUserData("Bucket");
		shape.dispose();
		
	}

	@Override
	public void render(float deltaTime, SpriteBatch batch) {
		TextureRegion frame = manAnim.getAnimacion(deltaTime);
		if(!mirandoDerecha)
			batch.draw(frame, this.cuerpo.getPosition().x*PIXELSTOMETERS - this.ancho/2, this.cuerpo.getPosition().y*PIXELSTOMETERS- (this.alto/2-8), this.ancho, this.alto);
		else
			batch.draw(frame, this.cuerpo.getPosition().x*PIXELSTOMETERS+this.ancho/2, this.cuerpo.getPosition().y*PIXELSTOMETERS-(this.alto/2-8), -this.ancho, this.alto);
	}
	
	public void dispose(){
	}
	
	public void setSueldo(int p){
		sueldo = p;
	}
	
	public int getSueldo(){
		return sueldo;
	}
}