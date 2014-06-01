package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;
import com.valyrian.firstgame.interfaces.ManejadorInteligencia;

public class Enemigo extends EntidadDibujable {

	private int maxVida;
	private int vidaActual;
	private int danio;
	public boolean mirandoDerecha;
	private ManejadorInteligencia manIntel;
	
	public Enemigo(Quetzal game,float ancho, float alto, float posIniX, float posIniY,Vector2 vel,int danioAux,int vidaMax,World m,ManejadorAnimacion ma) {
		super(game,ancho, alto, vel,posIniX,posIniY, m,ma);
		this.vidaActual = this.maxVida = vidaMax;
		this.danio = danioAux;
		this.mirandoDerecha=true;
	}
	
	public void setInteligencia(ManejadorInteligencia mi){ this.manIntel= mi; }
	
	public void setmaxVida(int mv){ this.maxVida = mv; }
	
	public int getMaxVida(){ return this.maxVida; }
	
	public int getVidaActual(){ return vidaActual; }
	
	public int getDanio(){ return danio; }
	
	//@brief Metodo para aumentar o reducir la vida en @value unidades
	public int setVidaActual(int value){
		
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
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX,float posY) {
		//Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX/PIXELSTOMETERS,posY/PIXELSTOMETERS);
		
		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(((ancho/2-3)/PIXELSTOMETERS),((alto/2-8)/PIXELSTOMETERS));

		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 10000;
		fixtureDef.density = 500;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_ENEMIGO;
		fixtureDef.filter.maskBits = BITS_JUGADOR |BITS_ENTORNO | BITS_PLATAFORMA |BITS_PROYECTIL;

		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setUserData(this);
		this.cuerpo.getFixtureList().first().setUserData("Enemigo");
		boxShape.dispose();
	}

	@Override
	public void render(float deltaTime, SpriteBatch batch) {
		//Actualizar enemigo
		if(!PAUSE)
			this.manIntel.Actualizar(deltaTime);
		
		TextureRegion frame = manAnim.getAnimacion(deltaTime, 0);
		if(mirandoDerecha)
			batch.draw(frame, this.cuerpo.getPosition().x - this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y- (this.alto/2-8)/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
		else
			batch.draw(frame, this.cuerpo.getPosition().x+this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y-(this.alto/2-8)/PIXELSTOMETERS, -this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
	}	
}