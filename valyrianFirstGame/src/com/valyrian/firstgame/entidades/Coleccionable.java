package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class Coleccionable extends EntidadDibujable {

	private int puntos;
	
	
	public Coleccionable(float ancho, float alto,float posIniX,float posIniY,Vector2 vel,int puntos, World mundo,ManejadorAnimacion ma) {
		super(ancho, alto,vel,posIniX,posIniY, mundo,ma);
		this.puntos = puntos;
	}

	public void setPuntos(int p){this.puntos = p;}
	
	public int getPuntos(){return this.puntos;}
	

	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX, float posY) {
		//Definicicion del cuerpo
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(posX/PIXELSTOMETERS,posY/PIXELSTOMETERS);
				
				//Definicion de la forma del fixture
				PolygonShape boxShape = new PolygonShape();
				boxShape.setAsBox(((ancho/2-6)/PIXELSTOMETERS),((alto/2-3)/PIXELSTOMETERS));
				
				//Definicion del fixture
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = boxShape;
				fixtureDef.friction = 0;
				fixtureDef.restitution = 0;
				fixtureDef.isSensor =true;
				fixtureDef.filter.categoryBits = BITS_COLECTABLE;
				fixtureDef.filter.maskBits = BITS_JUGADOR;
				
				this.cuerpo= mundo.createBody(bodyDef);
				this.cuerpo.createFixture(fixtureDef);
				this.cuerpo.setFixedRotation(true);
				this.cuerpo.setUserData(this);
				
				this.cuerpo.getFixtureList().first().setUserData("Coleccionable");
			     boxShape.dispose();
	}


	@Override
	public void render(float deltaTime, SpriteBatch batch) {
		batch.draw(manAnim.getAnimacion(deltaTime), this.cuerpo.getPosition().x-this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y-this.alto/2/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}
}
