package com.valyrian.firstgame.utilidades.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuJoystick implements ControllerListener {

	private Stage escena;
	
	
	public MenuJoystick(Stage e) {
		this.escena = e;
	}
	
	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		switch(buttonCode){
		case Xbox360Pad.BUTTON_LB:
			escena.keyDown(Keys.CONTROL_LEFT);
			break;
		case Xbox360Pad.BUTTON_RB:
			escena.keyDown(Keys.CONTROL_RIGHT);
			break;
		case Xbox360Pad.BUTTON_A:
		case Xbox360Pad.BUTTON_X:
		case Xbox360Pad.BUTTON_START:
			escena.keyDown(Keys.ENTER);
			break;
		case Xbox360Pad.BUTTON_BACK:
			escena.keyDown(Keys.R);
			break;
		}
		return false;
	}
	
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		switch(axisCode){
		case Xbox360Pad.AXIS_LEFT_Y:
			if(value>0.95f){
				escena.keyDown(Keys.DOWN);
			}else if(value<-0.95f){
				escena.keyDown(Keys.UP);
			}
			break;
		}	
		return false;
	}
	
	@Override
	public boolean accelerometerMoved(Controller controller,
			int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		switch (value) {
		case north:
			escena.keyDown(Keys.UP);
			break;
		case south:
			escena.keyDown(Keys.DOWN);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode,
			boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode,
			boolean value) {
		// TODO Auto-generated method stub
		return false;
	}
}