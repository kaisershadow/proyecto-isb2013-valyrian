package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Rana extends SerVivo {
	private Texture enemigo;
	public Rana() {
		// TODO Auto-generated constructor stub
	}

	public Rana(int ancho, int alto, int vidaMax, Vector2 velMax, Vector2 pos,World mundo) {
		super(ancho, alto, vidaMax, velMax, pos);
		// TODO Auto-generated constructor stub
		enemigo = new Texture("mapas/tiles/frog_idle.gif");
		crearCuerpo(mundo,pos,ancho,alto);

	}

	private void crearCuerpo(World mundo, Vector2 pos, int ancho, int alto) {
		// TODO Auto-generated method stub
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(pos.x,pos.y);
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
		this.cuerpo.setUserData("Enemigo");
		//cuerpo.setGravityScale(0);
		
	
		 boxShape.setAsBox(ancho/2, 1f,new Vector2(0, -alto/2) , 0);
	     fixtureDef.isSensor = true;
	     cuerpo.createFixture(fixtureDef);
	    // cuerpo.setGravityScale(0);
	     //cuerpo.setLinearDamping(0);
//	     System.out.println(cuerpo.getLinearDamping());
	     boxShape.dispose();

	}
	
	public void renderRana(SpriteBatch batch)
	{
		
		batch.draw(enemigo, this.posicion.x,this.posicion.y);
		
	}
	public void dispose(){
		enemigo.dispose();
	}
}
