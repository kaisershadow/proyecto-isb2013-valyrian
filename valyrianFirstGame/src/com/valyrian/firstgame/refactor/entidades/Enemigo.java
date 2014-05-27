package com.valyrian.firstgame.refactor.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.refactor.animaciones.AnimacionRana;
import com.valyrian.firstgame.refactor.interfaces.ManejadorAnimacion;

public class Enemigo extends Entidad {

	private int maxVida;
	private int vidaActual;
	private int danio;
	private boolean mirandoDerecha;
	private ManejadorAnimacion manAnim;
	public ESTADO_ACTUAL estado;
	
	public Enemigo(float ancho, float alto, Vector2 pos,Vector2 vel,int damage,int vidaMax,World m, Texture t) {
		super(ancho, alto, pos, vel, m, t);
		this.vidaActual = this.maxVida = vidaMax;
		this.danio = damage;
		this.mirandoDerecha=true;
		estado=ESTADO_ACTUAL.Moviendose;
		
//		Hacer un factory de enemigos, segun un parametro que se le pase
		manAnim = new AnimacionRana(t);
	}
	
	public void setmaxVida(int mv){ this.maxVida = mv; }
	
	public int getMaxVida(){ return this.maxVida; }
	
	public int getVidaActual(){ return vidaActual; }
	
	public int getDamage(){ return danio; }
	
	//@brief Metodo para aumentar o reducir la vida en @value unidades
	public int cambiarVidaActual(int value){
		
		vidaActual+=value;
		if(vidaActual>maxVida)
			vidaActual=maxVida;
		else if(vidaActual<0)
			vidaActual=0;				
		return vidaActual;
	}
	
	public boolean estaMuerto(){
		return(vidaActual<=0);
	}

	@Override
	protected void crearCuerpo(World mundo, Vector2 pos, float ancho, float alto) {
		//Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(pos.x/PIXELSTOMETERS,pos.y/PIXELSTOMETERS);

		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(((ancho/2-3)/PIXELSTOMETERS),((alto/2-3)/PIXELSTOMETERS));

		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_ENEMIGO;
		fixtureDef.filter.maskBits = BITS_JUGADOR |BITS_ENTORNO | BITS_PLATAFORMA |BITS_PROYECTIL;

		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setUserData(this);
		//				this.cuerpo.setBullet(true);
		this.cuerpo.getFixtureList().first().setUserData("Enemigo");
		boxShape.dispose();

	}

	@Override
	public void render(float deltaTime, SpriteBatch batch) {
		TextureRegion frame = null;
		switch (this.estado)
		{
			case Quieto:
				frame = manAnim.getQuieto(deltaTime);
				break;
			case Saltando:
				frame = manAnim.getSaltar(deltaTime);
				break;
			case Moviendose:
				frame = manAnim.getMover(deltaTime);
				break;
			case Atacando:
				frame = manAnim.getAtacar(deltaTime);
				break;
		}
		if(mirandoDerecha)
			batch.draw(frame, this.posicion.x - this.ancho/2/PIXELSTOMETERS, this.posicion.y- this.alto/2/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
		else
			batch.draw(frame, this.posicion.x+this.ancho/2/PIXELSTOMETERS, this.posicion.y-this.alto/2/PIXELSTOMETERS, -this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}
}