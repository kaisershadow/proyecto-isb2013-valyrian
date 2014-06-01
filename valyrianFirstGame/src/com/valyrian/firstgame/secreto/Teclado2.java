package com.valyrian.firstgame.secreto;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;


public class Teclado2 extends InputAdapter{
	
	Body body;
	OrthographicCamera camera;
	public Teclado2(Body b, OrthographicCamera cam){
		body = b;
		camera = cam;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Input.Keys.LEFT){
				this.body.setLinearVelocity(-3, 0);
			}	
		
		if(keycode == Input.Keys.RIGHT){
				this.body.setLinearVelocity(3, 0);
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
			this.body.setLinearVelocity(0, 0);
		return super.keyUp(keycode);
	}
	
	@Override
	public boolean scrolled(int amount) {
		camera.zoom+=amount/20f;
		camera.update();
		return true;
	}

}
