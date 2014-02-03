package com.valyrian.firstgame.utilitarios;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class Joystick implements ControllerListener {

	@Override
	public void connected(Controller controller) {
	System.out.println("Se conecto el control");

	}

	@Override
	public void disconnected(Controller controller) {
		System.out.println("Se desconecto el control");

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			System.out.println("Se presiona el boton A");
			break;
		case Xbox360Pad.BUTTON_B:
			System.out.println("Se presiona el boton B");
			break;
		case Xbox360Pad.BUTTON_X:
			System.out.println("Se presiona el boton X");
			break;
		case Xbox360Pad.BUTTON_Y:
			System.out.println("Se presiona el boton Y");
			break;
		case Xbox360Pad.BUTTON_LB:
			System.out.println("Se presiona el boton LB");
			break;
		case Xbox360Pad.BUTTON_RB:
			System.out.println("Se presiona el boton RB");
			break;
		case Xbox360Pad.BUTTON_BACK:
			System.out.println("Se presiona el boton BACK");
			break;
		case Xbox360Pad.BUTTON_START:
			System.out.println("Se presiona el boton START");
			break;
		case Xbox360Pad.BUTTON_L3:
			System.out.println("Se presiona el boton L3");
			break;
		case Xbox360Pad.BUTTON_R3:
			System.out.println("Se presiona el boton R3");
			break;
		default:
			return false;
	}
		return true;
			
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			System.out.println("Se solto el boton A");
			break;
		case Xbox360Pad.BUTTON_B:
			System.out.println("Se solto el boton B");
			break;
		case Xbox360Pad.BUTTON_X:
			System.out.println("Se solto el boton X");
			break;
		case Xbox360Pad.BUTTON_Y:
			System.out.println("Se solto el boton Y");
			break;
		case Xbox360Pad.BUTTON_LB:
			System.out.println("Se solto el boton LB");
			break;
		case Xbox360Pad.BUTTON_RB:
			System.out.println("Se solto el boton RB");
			break;
		case Xbox360Pad.BUTTON_BACK:
			System.out.println("Se solto el boton BACK");
			break;
		case Xbox360Pad.BUTTON_START:
			System.out.println("Se solto el boton START");
			break;
		case Xbox360Pad.BUTTON_L3:
			System.out.println("Se solto el boton L3");
			break;
		case Xbox360Pad.BUTTON_R3:
			System.out.println("Se solto el boton R3");
			break;
		default:
			return false;
	}
		return true;	
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
//		switch(axisCode){
//		case Xbox360Pad.AXIS_LEFT_X:
//			System.out.println("Se movio el AXIS LEFT_X a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_LEFT_Y:
//			System.out.println("Se movio el AXIS LEFT_Y a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_RIGHT_X:
//			System.out.println("Se movio el AXIS RIGHT_X a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_RIGHT_Y:
//			System.out.println("Se movio el AXIS RIGHT_Y a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_LEFT_TRIGGER:
//			System.out.println("Se movio el AXIS LT a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_RIGHT_TRIGGER:
//			System.out.println("Se movio el AXIS RT a valor: "+ value);
//			break;
//		default:
//			return false;
//	}
		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode,
			PovDirection value) {		
		System.out.println("Se movio el DPAD en la direccion: " + value +" con el POVCODE: "+ povCode);
		return true;
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

	@Override
	public boolean accelerometerMoved(Controller controller,
			int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

}
