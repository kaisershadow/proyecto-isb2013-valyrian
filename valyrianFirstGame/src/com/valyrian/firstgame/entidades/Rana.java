package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.utilidades.GameVariables;

public class Rana extends SerVivo {
	private Texture enemigo;
	private int damage;
	public Rana() {
		// TODO Auto-generated constructor stub
	}

	public Rana(float ancho, float alto, int vidaMax,int danio, Vector2 velMax, Vector2 pos,World mundo) {
		super(ancho, alto, vidaMax, velMax, pos);
		// TODO Auto-generated constructor stub
		enemigo = new Texture("mapas/tiles/frog_idle.gif");
		setDamage(danio);
		
		crearCuerpo(mundo,pos,ancho,alto);
		posicion.x=(cuerpo.getPosition().x);
		posicion.y=(cuerpo.getPosition().y);
	}

	private void crearCuerpo(World mundo, Vector2 pos, float ancho, float alto) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(pos.x,pos.y);
		bodyDef.angle = 0;
	
		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(ancho/2-5/PIXELSTOMETERS,alto/2-5/PIXELSTOMETERS);
		
		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		//fixtureDef.density = 1/(ancho*alto);
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_ENEMIGO;
		fixtureDef.filter.maskBits = BITS_JUGADOR |BITS_ENTORNO|BITS_PROYECTIL;
		
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.getFixtureList().first().setUserData("Enemigo");
		//cuerpo.setGravityScale(0);
		this.cuerpo.setUserData(this);
		
		boxShape.dispose();

	}
	
	public void renderRana(SpriteBatch batch){
		//if(enemigo!=null)
		batch.draw(this.enemigo, this.posicion.x-ancho-0/PIXELSTOMETERS,this.posicion.y-alto/2+5/PIXELSTOMETERS,this.ancho*2,this.alto*2);
//		batch.draw(enemigo, this.cuerpo.getPosition().x,this.cuerpo.getPosition().y,this.ancho,this.alto);
	}
	public void dispose(){
		enemigo.dispose();
		//enemigo=null;
		System.out.println("SE LLAMO EL DISPOSE DE RANA");
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
