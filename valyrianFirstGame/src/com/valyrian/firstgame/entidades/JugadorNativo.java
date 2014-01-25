package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;



public class JugadorNativo extends SerVivo implements InputProcessor {
	
//	public Body cuerpo;
	Texture nativoCamina;
	private boolean mirandoDerecha=true;
	float stateTime = 0,duracionAnim=0.1f;
	private Animation quieto, caminando,corriendo;
	public enum ESTADO_ACTUAL{
		Quieto, Saltando, Corriendo,Caminando	
	}
	public ESTADO_ACTUAL estado=ESTADO_ACTUAL.Quieto;
	
	public JugadorNativo(float velocidad,int maxvida,int posX,int posY, int ancho, int alto){
		super(maxvida);
		this.vida =maxvida;
		this.velocidad=0;
		this.MAX_VELOCIDAD=velocidad;
		posicion.x = posX;
		posicion.y = posY;
		ANCHO =ancho;
		ALTO=alto;
		
//		this.cuerpo.getPosition().x=posX;
//		this.cuerpo.getPosition().y=posY;
		
		nativoCamina = new Texture("personajes/nativoCamina.png");
		TextureRegion[] camina = TextureRegion.split(nativoCamina, 64, 64)[0];
		quieto = new Animation(0,camina[1]);
		caminando= new Animation(duracionAnim, camina);
		caminando.setPlayMode(Animation.LOOP);
		corriendo = caminando;
	}
	
	public void renderNativo(float deltaTime,SpriteBatch batch)
	{
		stateTime+=deltaTime;
		TextureRegion frame = null;
		switch (this.estado)
		{
			case Quieto:
				frame = quieto.getKeyFrame(this.stateTime);
				break;
			case Corriendo:
				frame = corriendo.getKeyFrame(this.stateTime);
				break;
			case Caminando:
				frame = caminando.getKeyFrame(this.stateTime);
				break;
		}
 
		actualizarNativo(deltaTime);
		batch.draw(frame, this.posicion.x, this.posicion.y, this.ANCHO, this.ALTO);
	//	System.out.println("Posicion del nativo (x,y): ("+this.posicion.x+","+this.posicion.y+")");
	}
	
	public void actualizarNativo(float delta){
		//posicion.x+=velocidad*delta;
	//	this.cuerpo.getPosition().set(this.posicion);
	}
	public void liberarMemoria(){
		nativoCamina.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case(Input.Keys.D):
			
			estado=ESTADO_ACTUAL.Caminando;
			
			velocidad=MAX_VELOCIDAD;
			
			break;
		case(Input.Keys.A):
			System.out.println("SE PRESIONO LA TECLA A");
			estado=ESTADO_ACTUAL.Caminando;
			velocidad=-MAX_VELOCIDAD;
			break;
		case(Input.Keys.SPACE):
			if(estado!= ESTADO_ACTUAL.Saltando)
				System.out.println("SE PRESIONO LA TECLA ESPACIO");
				posicion.y+=10;
			break;
		case(Input.Keys.SHIFT_LEFT):
				System.out.println("SE PRESIONO LA TECLA SHIFT");
				duracionAnim=1/30f;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Input.Keys.SHIFT_LEFT:
			System.out.println("SE PRESIONO LA TECLA SHIFT");
			duracionAnim=1/10f;
		break;
		case Input.Keys.A:
		case Input.Keys.D:
			estado=ESTADO_ACTUAL.Quieto;
			velocidad=0;
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
