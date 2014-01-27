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



public class Jugador extends SerVivo implements InputProcessor {
	
	private Texture nativoTexture;
	private boolean mirandoDerecha=true;
	private	float stateTime = 0;
	private Animation quieto, caminando,saltando,atacando;
	public enum ESTADO_ACTUAL{Quieto, Saltando, Atacando,Caminando}
	public ESTADO_ACTUAL estado=ESTADO_ACTUAL.Quieto;
	
	
	//@brief Se crea un nuevo jugador. NOTA: ANtes ya debe haber sido definido un cuerpo en el mundo de Box2D
	public Jugador(int ancho, int alto,int maxvida,float maxvel, int posX,int posY, int densidad, Body cuerpo){
		super(ancho,alto,maxvida,maxvel,densidad,new Vector2(posX, posY),cuerpo);
		
		//Deficion de las diferentes animaciones para el personaje
		nativoTexture = new Texture("personajes/nativo.png");
		TextureRegion[] nativo = TextureRegion.split(nativoTexture, 32, 64)[0];
		//OJO: Cambiar esta animacion cuando se tengan los sprites
		quieto = new Animation(0,nativo[2]);
		caminando= new Animation(0.1f, nativo[2],nativo[3],nativo[4],nativo[5],nativo[6],nativo[7]);
		caminando.setPlayMode(Animation.LOOP);
		atacando =new Animation(1/30f,nativo[0],nativo[1]);
		saltando = new Animation(1/20f,nativo[8],nativo[9],nativo[10]);
		saltando.setPlayMode(Animation.NORMAL);
		atacando.setPlayMode(Animation.NORMAL);
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
		actualizarJugador();
		if(mirandoDerecha)
			batch.draw(frame, this.posicion.x, this.posicion.y, this.ancho, this.alto);
		else
			batch.draw(frame, this.posicion.x+this.ancho, this.posicion.y, -this.ancho, this.alto);
		//	System.out.println("Posicion del nativo (x,y): ("+this.posicion.x+","+this.posicion.y+")");
	}
	
	public void actualizarJugador(){
		posicion.x=cuerpo.getPosition().x-ancho/2;
		posicion.y=cuerpo.getPosition().y-alto/2;
	}
	
	public void dispose(){
		nativoTexture.dispose();
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
	
	
	@Override
	public boolean keyDown(int keycode) {
		float cuerpoVelX = cuerpo.getLinearVelocity().x;
		float desiredVel = 0;
	 		
		switch(keycode){
		case(Input.Keys.D):
			desiredVel = MAXVELOCIDAD;
			mirandoDerecha=true;
		if(estado!=ESTADO_ACTUAL.Saltando)
			estado=ESTADO_ACTUAL.Caminando;
			
			break;
		case(Input.Keys.A):
			desiredVel = -MAXVELOCIDAD;
			mirandoDerecha = false;
			estado=ESTADO_ACTUAL.Caminando;
			break;
		case(Input.Keys.SPACE):
			if(estado!= ESTADO_ACTUAL.Saltando)
				estado =ESTADO_ACTUAL.Saltando;
				//IMPLEMENTAR SALTO	
				break;
		case(Input.Keys.J):
			estado=ESTADO_ACTUAL.Atacando;
	//		cuerpo.setLinearVelocity(100, 0);
		break;
		}
	   float impulse = cuerpo.getMass() * (desiredVel - cuerpoVelX); 
	   cuerpo.applyLinearImpulse(impulse, 0, cuerpo.getWorldCenter().x, cuerpo.getWorldCenter().y, true);
	//   cuerpo.setLinearVelocity(new Vector2(100, 0));
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.A:
		case Input.Keys.D:
		//case Input.Keys.J:
			estado=ESTADO_ACTUAL.Quieto;
			break;
	
		
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
