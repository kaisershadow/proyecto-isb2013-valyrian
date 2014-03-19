package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;



public class Jugador extends SerVivo {
	
	private Texture nativoTexture;
	private	float stateTime = 0; //Variable para controlar el tiempo de cada animacion
	private Animation quieto, caminando,saltando,atacando;
	private Vector2 desiredVel = new Vector2(); //Variable para calcular velocidad deseada para moverse
	public Array<Proyectil> disparos;
	
	
	public boolean mirandoDerecha=true;
	public boolean puedeSaltar = true;
	public enum ESTADO_ACTUAL{Atacando, Caminando, Quieto,Saltando}
	public ESTADO_ACTUAL estado=ESTADO_ACTUAL.Quieto;
	
	
	
	//@brief Se crea un nuevo jugador.
	public Jugador(int ancho, int alto,int maxvida,Vector2 vel, Vector2 pos,World mundo){
		super(ancho,alto,maxvida,vel,pos);
		//Definicion de las diferentes animaciones para el personaje
		nativoTexture = new Texture("personajes/nativo.png");
		TextureRegion[] nativo = TextureRegion.split(nativoTexture, 32, 64)[0];
		//OJO: Cambiar esta animacion cuando se tengan los sprites
		quieto = new Animation(0,nativo[2]);
		caminando= new Animation(1/7f, nativo[2],nativo[3],nativo[4],nativo[5],nativo[6],nativo[7]);
		caminando.setPlayMode(Animation.LOOP);
		atacando =new Animation(1/3,nativo[1],nativo[0],nativo[2]);
		//saltando = new Animation(1/3f,nativo[8],nativo[9],nativo[10]);
		saltando = new Animation(1,nativo[10]);
		saltando.setPlayMode(Animation.LOOP);
		atacando.setPlayMode(Animation.LOOP);
		crearCuerpo(mundo,pos,ancho,alto);
		disparos = new Array<Proyectil>();
	}
	
	private void crearCuerpo(World mundo,Vector2 pos,int ancho,int alto) {
		///Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
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
		this.cuerpo.setBullet(true);
		this.cuerpo.setUserData("Personaje");
		//cuerpo.setGravityScale(0);
		
		
	
		 boxShape.setAsBox(ancho/2, 1f,new Vector2(0, -alto/2) , 0);
	     fixtureDef.isSensor = true;
	     cuerpo.createFixture(fixtureDef);
	    // cuerpo.setGravityScale(0);
	     //cuerpo.setLinearDamping(0);
//	     System.out.println(cuerpo.getLinearDamping());
	     boxShape.dispose();
	}

	public void renderJugador(float deltaTime,SpriteBatch batch)
	{
		stateTime+=deltaTime;
		TextureRegion frame = null;
		switch (this.estado)
		{
			case Quieto:
				frame = quieto.getKeyFrame(this.stateTime);
				break;
			case Saltando:
				frame = saltando.getKeyFrame(this.stateTime);
				break;
			case Caminando:
				frame = caminando.getKeyFrame(this.stateTime);
				break;
			case Atacando:
				frame = atacando.getKeyFrame(this.stateTime);
				break;
		}
	//	actualizarPosicionJugador();
		if(mirandoDerecha)
			batch.draw(frame, this.posicion.x, this.posicion.y, this.ancho, this.alto);
		else
			batch.draw(frame, this.posicion.x+this.ancho, this.posicion.y, -this.ancho, this.alto);
		//	System.out.println("Posicion del nativo (x,y): ("+this.posicion.x+","+this.posicion.y+")");
		
		System.out.println("ANTES DE RENDERIZAR LAS BALAS");

		if(disparos.size > 0){
			System.out.println("SE RENDERIZAN  LAS BALAS");
		for (Proyectil bala : this.disparos) {
			bala.renderBala(batch);
			}
		}
	
	}
	
	public void actualizarPosicionJugador(){
//	if(cuerpo.getLinearVelocity().x>0 && cuerpo.getLinearVelocity().x <velocidad.x)
//		{cuerpo.setLinearVelocity(velocidad.x, cuerpo.getLinearVelocity().y);}
//	else {if(cuerpo.getLinearVelocity().x<0 && cuerpo.getLinearVelocity().x >-velocidad.x)
//		cuerpo.setLinearVelocity(-velocidad.x, cuerpo.getLinearVelocity().y);
//	}
	
//	if(cuerpo.getLinearVelocity().y>0 && cuerpo.getLinearVelocity().y <velocidad.y)
//		cuerpo.setLinearVelocity(cuerpo.getLinearVelocity().x,velocidad.y);
		
		
		//	Vector2 impulse = new Vector2();
		//impulse.x =	cuerpo.getMass() * (desiredVel.x - cuerpo.getLinearVelocity().x);
		//impulse.y =	cuerpo.getMass() * (desiredVel.y - cuerpo.getLinearVelocity().y);
		//impulse.y =	cuerpo.getMass() * (desiredVel.y);
		//cuerpo.applyLinearImpulse(impulse.x,0, cuerpo.getWorldCenter().x,cuerpo.getWorldCenter().y, true);	
		//cuerpo.applyLinearImpulse(0,impulse.y, cuerpo.getWorldCenter().x,cuerpo.getWorldCenter().y, true);
		//cuerpo.applyLinearImpulse(impulse, cuerpo.getWorldCenter(), true);
		posicion.x=cuerpo.getPosition().x-ancho/2;
		posicion.y=cuerpo.getPosition().y-alto/2;
		
		if(disparos.size > 0){
				System.out.println("SE ACTUALIZAN LAS BALAS");
				for (Proyectil bala : this.disparos) {
					System.out.println("ACTUALIZE LA BALA NUEVA");
					bala.actualizarPosicionBala();
					System.out.println("ACTUALIZE LA BALA NUEVA 2");
				}			
		}
		
		
		System.out.println("VELOCIDAD :"+cuerpo.getLinearVelocity().x+", "+cuerpo.getLinearVelocity().y);
	}	

	public void actualizarCamara(OrthographicCamera camera,int mapW,int mapH,int tileW,int tileH){
		camera.position.set(cuerpo.getPosition().x, cuerpo.getPosition().y, 0);

		//Correcion posicion X
		if(camera.position.x < camera.viewportWidth/2)
			camera.position.x = camera.viewportWidth/2;

		else if(camera.position.x+camera.viewportWidth/2 > mapW*tileW)
			camera.position.x =-camera.viewportWidth/2+mapW*tileW;
		//Correcion posicion Y
		if(camera.position.y < camera.viewportHeight/2)
			camera.position.y = camera.viewportHeight/2;
		
		else if(camera.position.y+camera.viewportHeight/2 > mapH*tileH)
			camera.position.y =-camera.viewportHeight/2+mapH*tileH;		
	}


	public Vector2 getDesiredVel() {
		return desiredVel;
	}

	public void setDesiredVel(Vector2 desiredVel) {
		this.desiredVel = desiredVel;
	}

	public void dispose(){
		nativoTexture.dispose();
	}
}
