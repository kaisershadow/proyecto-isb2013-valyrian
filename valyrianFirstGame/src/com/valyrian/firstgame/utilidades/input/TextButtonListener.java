package com.valyrian.firstgame.utilidades.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TextButtonListener extends InputListener{

	private Color colorEnter;
	private Color colorExit;
	
	public TextButtonListener(Color cEnter, Color cExit) {
		this.colorExit = cExit;
		this.colorEnter = cEnter;
	}

	public void setColorEnter(Color c){
		this.colorEnter = c;
	}
	
	public void setColorExit(Color c){
		this.colorExit = c;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
		return super.touchDown(event, x, y, pointer, button);
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		event.getStage().setKeyboardFocus(event.getListenerActor());
		event.getListenerActor().setColor(colorEnter);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		event.getListenerActor().setColor(colorExit);
	}
}