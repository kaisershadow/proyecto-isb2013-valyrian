package com.valyrian.firstgame.refactor.entidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

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
import com.valyrian.firstgame.refactor.animaciones.AnimacionJugador;
import com.valyrian.firstgame.refactor.entidades.Proyectil;
import com.valyrian.firstgame.refactor.interfaces.ManejadorAnimacion;

public class Jugador extends Entidad{
	
	private int maxVida;
	private int vidaActual;
	private boolean mirandoDerecha;
	private String nombre;
	private ManejadorAnimacion manAnim;

	public ESTADO_ACTUAL estado;
	public int numContactos;
	
	public Jugador(float ancho, float alto, Vector2 pos,Vector2 vel,int vidaMax, World mundo, Texture t) {
		super(ancho, alto, pos,vel, mundo, t);
		this.vidaActual = this.maxVida = vidaMax;
		this.nombre = "Jugador";
		this.mirandoDerecha=true;
		estado=ESTADO_ACTUAL.Quieto;
		manAnim = new AnimacionJugador(t);
	}
	
	public void setmaxVida(int mv){ this.maxVida = mv; }
	
	public int getMaxVida(){ return this.maxVida; }
	
	public int getVidaActual(){ return vidaActual; }
	
	//@brief Metodo para aumentar o reducir la vida en @value unidades
	public int cambiarVidaActual(int value){
		
		vidaActual+=value;
		if(vidaActual>maxVida)
			vidaActual=maxVida;
		else if(vidaActual<0)
			vidaActual=0;				
		return vidaActual;
	}
	
	public boolean estaMuerto(){ return(vidaActual<=0); }
	
	public void setNombre(String n){ this.nombre = n; }
	
	public String getNombre(){ return this.nombre; }

	@Override
	protected void crearCuerpo(World mundo, Vector2 pos, float ancho, float alto) {
	
		//Definicicion del cuerpo
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
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
		fixtureDef.filter.categoryBits = BITS_JUGADOR;
		fixtureDef.filter.maskBits = BITS_ENEMIGO |BITS_MUERTE |BITS_ENTORNO | BITS_META |BITS_PROYECTIL | BITS_PLATAFORMA |BITS_COLECTABLE ;
		
		this.cuerpo= mundo.createBody(bodyDef);
		this.cuerpo.createFixture(fixtureDef);		
		this.cuerpo.setFixedRotation(true);
//		this.cuerpo.setBullet(true);
		this.cuerpo.setUserData(this);
		
		this.cuerpo.getFixtureList().first().setUserData("Jugador");
		
		
		//Definicion del sensor para el salto
		 boxShape.setAsBox(((ancho/2 -5)/PIXELSTOMETERS), 1/PIXELSTOMETERS,new Vector2(0, ((-alto/2+3)/PIXELSTOMETERS)) , 0);
	     fixtureDef.isSensor = true;
	     cuerpo.createFixture(fixtureDef);
	     this.cuerpo.getFixtureList().get(1).setUserData("Salto");
	     boxShape.dispose();
	}
		
	
	
	public void render(float deltaTime,SpriteBatch batch) {
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
		bala = new Proyectil(7, 3, posAux, velAux, mundo, new Texture("personajes/dardo.png"),danio);
		
		Filter filter = bala.getCuerpo().getFixtureList().first().getFilterData();

		
		//OJO SOLO PARA PRUEBA ESTE IF
		if(danio ==10)
			filter.maskBits |=BITS_JUGADOR;
		else
			filter.maskBits |=BITS_ENEMIGO;
		bala.getCuerpo().getFixtureList().first().setFilterData(filter);

		
		
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
