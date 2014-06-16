package com.valyrian.firstgame.utilidades.input;

import java.util.ArrayList;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuListener extends InputListener {
	
	private ArrayList<TextButton> botones;
	private InputEvent eventoSalir;
	private InputEvent eventoEntrar;
	private InputEvent eventoTouchDown;
	private InputEvent eventoKeyDown;
	private Stage escena;
	
	public MenuListener(Stage e, ArrayList<TextButton> b) {
	
	this.escena = e;
	this.botones = b;
	
	this.eventoSalir = new InputEvent();
	this.eventoSalir.setType(Type.exit);
	
	this.eventoEntrar = new InputEvent();
	this.eventoEntrar.setType(Type.enter);
	
	this.eventoTouchDown = new InputEvent();
	eventoTouchDown.setType(Type.touchDown);
	
	this.eventoKeyDown = new InputEvent();
	eventoKeyDown.setType(Type.keyDown);

	}
	
		@Override
	public boolean scrolled(InputEvent event, float x, float y, int amount) {
			if(amount>0)
				keyDown(eventoKeyDown, Keys.DOWN);
			else
				keyDown(eventoKeyDown, Keys.UP);
			
		return true;
	}

	@Override
	public boolean keyDown (InputEvent event, int keycode) {	
		TextButton botonAux;
		switch (keycode) {
			case Keys.UP:
				if(botones.size()<=1)
					break;
				botonAux = botones.remove(botones.size()-1);
				botonAux.fire(eventoEntrar);
				escena.setKeyboardFocus(botonAux);
				botones.get(0).fire(eventoSalir);
				botones.add(0, botonAux);
				break;
			case Keys.DOWN:
				if(botones.size()<=1)
					break;
				botonAux = botones.remove(0);
				botonAux.fire(eventoSalir);
				escena.setKeyboardFocus(botones.get(0));
				botones.get(0).fire(eventoEntrar);
				botones.add(botones.size(),botonAux);
				break;
			case Keys.ENTER:
				escena.getKeyboardFocus().fire(eventoTouchDown);
				break;
			default:
				break;
		}
		return true;
	}
}