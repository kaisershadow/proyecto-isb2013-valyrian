package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.utilitarios.ManejadorVariables;
import static com.valyrian.firstgame.utilitarios.ManejadorVariables.*;


public class Jugador extends SerVivo {
	
	private Texture nativoTexture;
	private	float stateTime = 0;	//Variable para controlar el tiempo de cada animacion
	private Animation quieto, caminando,saltando,atacando;
	public Array<Proyectil> disparos;
	
	
	public boolean mirandoDerecha=true;
	public int numContactos;
	public enum ESTADO_ACTUAL{Atacando, Caminando, Quieto,Saltando}
	public ESTADO_ACTUAL estado=ESTADO_ACTUAL.Quieto;
	
	
	//@brief Se crea un nuevo jugador.
	public Jugador(float ancho, float alto,int maxvida,Vector2 vel, Vector2 pos,World mundo){
		super(ancho,alto,maxvida,vel,pos);
		
		numContactos=0;
		
		//Definicion de las diferentes animaciones para el personaje
		nativoTexture = new Texture("personajes/nativo.png");
		TextureRegion[] nativo = TextureRegion.split(nativoTexture, 32, 64)[0];
		
		quieto = new Animation(0,nativo[2]);
		
		caminando= new Animation(1/7f, nativo[2],nativo[3],nativo[4],nativo[5],nativo[6],nativo[7]);
		caminando.setPlayMode(Animation.LOOP);
		
		atacando =new Animation(1/12f,nativo[1],nativo[0]);
		atacando.setPlayMode(Animation.NORMAL);
		
		saltando = new Animation(0,nativo[10]);
		saltando.setPlayMode(Animation.LOOP);

		disparos = new Array<Proyectil>();

		crearCuerpo(mundo,pos,ancho,alto);
	}
	
	private void crearCuerpo(World mundo,Vector2 pos,float ancho,float alto) {
		///Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.x,pos.y);
		bodyDef.angle = 0;
	
		//Definicion de la forma del fixture
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox((ancho/2-3/ManejadorVariables.PIXELSTOMETERS),(alto/2-3/ManejadorVariables.PIXELSTOMETERS));

		//		System.out.println("(ANCHO, ALTO) -- ("+ancho+", "+alto+")");
		
		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_JUGADOR;
		fixtureDef.filter.maskBits = BITS_ENEMIGO |BITS_MUERTE |BITS_ENTORNO;
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setBullet(true);
		this.cuerpo.getFixtureList().first().setUserData("Jugador");
		
		
		//Definicion del sensor para el salto
		 boxShape.setAsBox((ancho/2 -5/ManejadorVariables.PIXELSTOMETERS), 1/ManejadorVariables.PIXELSTOMETERS,new Vector2(0, (-alto/2+3/ManejadorVariables.PIXELSTOMETERS)) , 0);
	     fixtureDef.isSensor = true;
	     cuerpo.createFixture(fixtureDef);
	     this.cuerpo.getFixtureList().get(1).setUserData("Salto");
	     boxShape.dispose();
	}

	public void renderJugador(float deltaTime,SpriteBatch batch) {
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

		if(mirandoDerecha)
			batch.draw(frame, this.posicion.x, this.posicion.y, this.ancho, this.alto);
		else
			batch.draw(frame, (this.posicion.x+this.ancho), this.posicion.y, -this.ancho, this.alto);
		
		if(disparos.size > 0){
		for (Proyectil bala : this.disparos) {
			bala.renderBala(batch);
			}
		}
	}
	
	public void actualizarPosicionJugador(){
		
		posicion.x=(cuerpo.getPosition().x-ancho/2);
		posicion.y=(cuerpo.getPosition().y-alto/2);
		
		if(disparos.size > 0){
			for (Proyectil bala : this.disparos) {
					bala.actualizarPosicionBala();
				}			
		}
	}	

	public void actualizarCamara(OrthographicCamera camera,float mapW,float mapH,float tileW,float tileH){
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
		
		camera.update();
	}

	public void dispose(){
		nativoTexture.dispose();
		System.out.println("SE LLAMO AL DISPOSE DE JUGADOR");
	}
}