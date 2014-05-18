package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

import static com.valyrian.firstgame.utilidades.GameVariables.*;


public class Jugador extends SerVivo {
	
	private String nombre; 
	private Texture nativoTexture;
	private	float stateTime = 0;	//Variable para controlar el tiempo de cada animacion
	private Animation quieto, caminando,saltando,atacando;
	public Array<Proyectil> disparos;
	
	
	//OJO VARIABLE DE PAUSE
	private boolean pause;
	
	private int direccion= 1;
	public boolean mirandoDerecha=true;
	public int numContactos;
	public enum ESTADO_ACTUAL{Atacando, Caminando, Quieto,Saltando}
	public ESTADO_ACTUAL estado=ESTADO_ACTUAL.Quieto;
	
	
	//@brief Se crea un nuevo jugador.
	public Jugador(float ancho, float alto,int maxvida,Vector2 vel, Vector2 pos,World mundo){
		super(ancho,alto,maxvida,vel,pos);
		
		nombre = "Pedro";
		numContactos=0;
		pause = false;
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
		boxShape.setAsBox((ancho/2-3/PIXELSTOMETERS),(alto/2-3/PIXELSTOMETERS));
		
		//Definicion del fixture
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_JUGADOR;
		fixtureDef.filter.maskBits = BITS_ENEMIGO |BITS_MUERTE |BITS_ENTORNO | BITS_META |BITS_PROYECTIL;
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setBullet(true);
		this.cuerpo.getFixtureList().first().setUserData("Jugador");
		
		
		//Definicion del sensor para el salto
		 boxShape.setAsBox((ancho/2 -5/PIXELSTOMETERS), 1/PIXELSTOMETERS,new Vector2(0, (-alto/2+3/PIXELSTOMETERS)) , 0);
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
	
	public boolean isPaused(){
		return pause;
	}
	
	//Acciones del Input
	
	public void MoverDerecha(){
		this.getCuerpo().setLinearVelocity(this.velocidad.x, this.cuerpo.getLinearVelocity().y);
		this.mirandoDerecha = true;
		this.direccion=1;
		if(this.estado != ESTADO_ACTUAL.Saltando)
			this.estado=ESTADO_ACTUAL.Caminando;
	}
	
	public void MoverIzquierda(){
		this.getCuerpo().setLinearVelocity(-this.velocidad.x, this.cuerpo.getLinearVelocity().y);
		this.mirandoDerecha = false;
		this.direccion=-1;
		if(this.estado != ESTADO_ACTUAL.Saltando)
			this.estado=ESTADO_ACTUAL.Caminando;
	}
	
	public void Saltar(){
		if(this.numContactos>0){
		//	if(this.estado !=ESTADO_ACTUAL.Saltando){
				//personaje.getCuerpo().applyForceToCenter(0, personaje.getVelocidad().y*50, true);
				this.cuerpo.setLinearVelocity(this.cuerpo.getLinearVelocity().x, this.velocidad.y);
				this.estado = ESTADO_ACTUAL.Saltando;
		}
	}
	
	public void Disparar(int danio){
		
		Proyectil bala;
		bala = new Proyectil(danio);
//		if (this.mirandoDerecha)
//			bala.crearCuerpo(this.getCuerpo().getWorld(), this.getCuerpo().getWorldCenter().x+this.getAncho()+10/ManejadorVariables.PIXELSTOMETERS,this.getCuerpo().getWorldCenter().y+8/ManejadorVariables.PIXELSTOMETERS, 3);
//		else 
//			bala.crearCuerpo(this.getCuerpo().getWorld(), this.getCuerpo().getWorldCenter().x-this.getAncho()-10/ManejadorVariables.PIXELSTOMETERS,this.getCuerpo().getWorldCenter().y+8/ManejadorVariables.PIXELSTOMETERS, -3);				
	
		bala.crearCuerpo(this.cuerpo.getWorld(), this.cuerpo.getWorldCenter().x+ this.direccion*(this.ancho+10/PIXELSTOMETERS),this.cuerpo.getWorldCenter().y+8/PIXELSTOMETERS, 3*this.direccion);
		this.disparos.add(bala);
		this.estado = ESTADO_ACTUAL.Atacando;
	}
	
	public void Pausar(){
		if (this.pause)
			this.pause = false;
		else
			this.pause = true;
	}
	
	public void Detener(){
		if(numContactos==0)
			this.estado=ESTADO_ACTUAL.Saltando;
		else
			this.estado=ESTADO_ACTUAL.Quieto;
		
		this.cuerpo.setLinearVelocity(0, this.cuerpo.getLinearVelocity().y);
	}
	
	public void Aterrizar(){
		if(numContactos==0){
			this.estado = ESTADO_ACTUAL.Saltando;
			return;
		}
		if(this.cuerpo.getLinearVelocity().x!=0)
			this.estado=ESTADO_ACTUAL.Caminando;
		else
			this.estado=ESTADO_ACTUAL.Quieto;	
	}
	
	public void Enfundar(){
		if(this.cuerpo.getLinearVelocity().x!=0)
			this.estado=ESTADO_ACTUAL.Caminando;
		else
			this.estado=ESTADO_ACTUAL.Quieto;
	}
	
	public int getDireccion(){
		return direccion;
	}
	
}