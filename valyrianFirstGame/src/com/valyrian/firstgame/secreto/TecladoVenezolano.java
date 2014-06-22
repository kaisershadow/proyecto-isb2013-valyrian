package com.valyrian.firstgame.secreto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.valyrian.firstgame.Quetzal;


public class TecladoVenezolano extends InputAdapter{
	
	Venezolano venezolano;
	OrthographicCamera camera;
	public TecladoVenezolano(Venezolano ven, OrthographicCamera cam){
		venezolano = ven;
		camera = cam;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.LEFT:
		case Input.Keys.A:
			this.venezolano.getCuerpo().setLinearVelocity(-3, 0);
			this.venezolano.mirandoDerecha = false;			
			break;
		case Input.Keys.RIGHT:
		case Input.Keys.D:
			this.venezolano.getCuerpo().setLinearVelocity(3, 0);
			this.venezolano.mirandoDerecha = true;			
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.LEFT:
		case Input.Keys.A:
		case Input.Keys.RIGHT:
		case Input.Keys.D:
			this.venezolano.getCuerpo().setLinearVelocity(0, 0);
			return true;
		case Input.Keys.ESCAPE:
			Quetzal aux = ((Quetzal)Gdx.app.getApplicationListener());
			aux.setScreen(aux.pantallaSeleccionNivel);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		camera.zoom+=amount/20f;
		camera.update();
		return true;
	}
}