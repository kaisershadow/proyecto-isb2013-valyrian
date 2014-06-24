package com.valyrian.firstgame.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionUnica;
import com.valyrian.firstgame.entidades.Proyectil;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class Jugador extends EntidadDibujable{
	
	private int maxVida;
	private int vidaActual;
	private int puntaje;
	private int puntajeAcum;
	private boolean mirandoDerecha;
	private Sound salto,disparo,dolor,muerte;
	private ManejadorAnimacion mab;
	public enum ESTADO_ACTUAL{Atacando, Moviendose, Quieto,Saltando}
	public ESTADO_ACTUAL estado;
	public int numContactos;
	public boolean finJuego;
	
	
	public Jugador(float ancho, float alto,float posIniX,float posIniY,Vector2 vel,int vidaMax, World mundo,ManejadorAnimacion ma) {
		super(ancho, alto,vel,posIniX,posIniY, mundo,ma);
		this.vidaActual = this.maxVida = vidaMax;
		this.puntaje =0;
		this.puntajeAcum =0;
		this.mirandoDerecha=true;
		this.estado=ESTADO_ACTUAL.Quieto;
		this.finJuego = false;
		Texture tx = Quetzal.getManejaRecursos().get("personajes/dardo.png", Texture.class);
		mab = new AnimacionUnica(tx,7,3,0);
		salto = Quetzal.getManejaRecursos().get("audio/salto.wav",Sound.class);
		disparo = Quetzal.getManejaRecursos().get("audio/disparo.mp3",Sound.class);
		dolor=Quetzal.getManejaRecursos().get("audio/dolor.wav",Sound.class);
		muerte=Quetzal.getManejaRecursos().get("audio/muerte.wav",Sound.class);
	}
	
	public void setmaxVida(int mv){ this.maxVida = mv; }
	
	public int getMaxVida(){ return this.maxVida; }
	
	public int getVidaActual(){ return vidaActual; }
	
	public void setPuntaje(int p){ 
		this.puntaje+=p;
		this.puntajeAcum+=p;
		if(this.puntajeAcum>=100){
			this.puntajeAcum-=100;
			int vidaAux = (int)(this.maxVida*0.1f);
			this.setVidaActual(vidaAux);
		}
			
	}
	
	public int getPuntaje(){ return puntaje; }
	
	public int setVidaActual(int value){
		
		vidaActual+=value;
		if(vidaActual>maxVida)
			vidaActual=maxVida;
		else if(vidaActual<0)
			vidaActual=0;				
		return vidaActual;
	}
	
	public boolean estaMuerto(){ return(vidaActual<=0); }
	
	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX,float posY) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX/PIXELSTOMETERS,posY/PIXELSTOMETERS);
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(((ancho/2-6)/PIXELSTOMETERS),((alto/2-3)/PIXELSTOMETERS));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.density = 100;
		fixtureDef.restitution = 0;
		fixtureDef.friction= 0;
		fixtureDef.isSensor =false;
		fixtureDef.filter.categoryBits = BITS_JUGADOR;
		fixtureDef.filter.maskBits = BITS_ENEMIGO |BITS_MUERTE |BITS_ENTORNO | BITS_META |BITS_PROYECTIL | BITS_PLATAFORMA |BITS_COLECTABLE ;
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);
		this.cuerpo.setFixedRotation(true);
		this.cuerpo.setUserData(this);		
		this.cuerpo.getFixtureList().first().setUserData("Jugador");
		
		//Definicion del sensor para el salto
		 boxShape.setAsBox(((ancho/2 -8)/PIXELSTOMETERS), 1/PIXELSTOMETERS,new Vector2(0, ((-alto/2+3)/PIXELSTOMETERS)) , 0);
		 fixtureDef.density = 10;
		 fixtureDef.isSensor = true;
	     fixtureDef.filter.maskBits = BITS_ENTORNO | BITS_MUERTE | BITS_PLATAFORMA;
	     cuerpo.createFixture(fixtureDef);
	     this.cuerpo.getFixtureList().get(1).setUserData("Salto");
	     boxShape.dispose();
	}
	
	public void render(float deltaTime,SpriteBatch batch) {
		TextureRegion frame = null;
		
		frame = manAnim.getAnimacion(deltaTime);
		if(mirandoDerecha)
			batch.draw(frame, this.cuerpo.getPosition().x - this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y- this.alto/2/PIXELSTOMETERS, this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);
		else
			batch.draw(frame, this.cuerpo.getPosition().x+this.ancho/2/PIXELSTOMETERS, this.cuerpo.getPosition().y-this.alto/2/PIXELSTOMETERS, -this.ancho/PIXELSTOMETERS, this.alto/PIXELSTOMETERS);	
	}

	public void actualizarCamara(OrthographicCamera camera,float mapW,float mapH,float tileW,float tileH){
		camera.position.set(cuerpo.getPosition().x, cuerpo.getPosition().y, 0);

		//Correcion posicion X
		if(camera.position.x < camera.viewportWidth/2)
			camera.position.x = camera.viewportWidth/2;
		else if(camera.position.x+camera.viewportWidth/2 > mapW*tileW/PIXELSTOMETERS)
			camera.position.x =mapW*tileW/PIXELSTOMETERS-camera.viewportWidth/2;
		
		//Correcion posicion Y
		if(camera.position.y < camera.viewportHeight/2)
			camera.position.y = camera.viewportHeight/2;
		else if(camera.position.y+camera.viewportHeight/2 > mapH*tileH/PIXELSTOMETERS)
			camera.position.y =mapH*tileH/PIXELSTOMETERS-camera.viewportHeight/2;		
		
		camera.update();
	}

	public void dispose(){
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE JUGADOR");
	}
	
	public void sonidoMuerte(float volumen){ this.muerte.play(volumen); }
	
	public void sonidoDolor(float volumen){ this.dolor.play(volumen); }
	
	/**************************************************Acciones del Input**************************************************/
	
	public void MoverDerecha(){
		this.cuerpo.setLinearVelocity(this.velocidad.x, this.cuerpo.getLinearVelocity().y);
		this.mirandoDerecha = true;
		this.direccion.x=1;
		if(this.estado != ESTADO_ACTUAL.Saltando)
			this.estado=ESTADO_ACTUAL.Moviendose;
	}
	
	public void MoverIzquierda(){
		this.cuerpo.setLinearVelocity(-this.velocidad.x, this.cuerpo.getLinearVelocity().y);
		this.mirandoDerecha = false;
		this.direccion.x=-1;
		if(this.estado != ESTADO_ACTUAL.Saltando)
			this.estado=ESTADO_ACTUAL.Moviendose;
	}
	
	public void Saltar(){
		if(this.numContactos>0){
			this.cuerpo.setLinearVelocity(this.cuerpo.getLinearVelocity().x, this.velocidad.y);
			this.estado = ESTADO_ACTUAL.Saltando;
			salto.play(VOLUMEN*0.55f);
		}
	}
	
	public Proyectil Disparar(World mundo,int danio){
		
		Proyectil bala;
		Vector2 posAux = new Vector2();
		Vector2 velAux = new Vector2();
		posAux.x =this.cuerpo.getPosition().x+ this.direccion.x*((this.ancho/2+4)/PIXELSTOMETERS);
		posAux.y =this.cuerpo.getPosition().y+8/PIXELSTOMETERS;
		velAux.x = this.direccion.x*3;
		velAux.y = 0;
//		bala = new Proyectil(7, 3, posAux, velAux, mundo, new Texture("personajes/dardo.png"),danio);
		bala = new Proyectil(7, 3, posAux.x, posAux.y, velAux, mundo, mab, danio);
		
		Filter filter = bala.getCuerpo().getFixtureList().first().getFilterData();
		filter.maskBits |=BITS_ENEMIGO;
		bala.getCuerpo().getFixtureList().first().setFilterData(filter);

		disparo.play(VOLUMEN*0.1f);
		this.estado = ESTADO_ACTUAL.Atacando;
		return bala;
	}
	
	public void Pausar(){
		if (PAUSE)
			PAUSE = false;
		else
			PAUSE = true;
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
			this.estado=ESTADO_ACTUAL.Moviendose;
		else
			this.estado=ESTADO_ACTUAL.Quieto;	
	}
	
	public void Enfundar(){
		if(this.cuerpo.getLinearVelocity().x!=0)
			this.estado=ESTADO_ACTUAL.Moviendose;
		else
			this.estado=ESTADO_ACTUAL.Quieto;
	}
}