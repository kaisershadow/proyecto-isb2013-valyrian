package com.valyrian.firstgame.utilidades.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;

public class Joystick implements ControllerListener {

	private Jugador personaje;
	private PrimerJuego juego;
	private OrthographicCamera camera;
	
	public Joystick(Jugador player, OrthographicCamera cam, PrimerJuego game) {
		personaje = player;
		camera = cam;
		juego =game;
	}
	
	
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
		if(personaje.isPaused() && buttonCode !=Xbox360Pad.BUTTON_START)
			return true;
		
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			personaje.Saltar();
			break;
		case Xbox360Pad.BUTTON_B:
			//System.out.println("Se presiona el boton B");
			break;
		case Xbox360Pad.BUTTON_X:
			personaje.Disparar(20);
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
			juego.setScreen(juego.pantallaNiveles);
			break;
		case Xbox360Pad.BUTTON_START:
			personaje.Pausar();
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
		if(personaje.isPaused())
			return true;
		
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			personaje.Aterrizar();
			break;
		case Xbox360Pad.BUTTON_B:
			System.out.println("Se solto el boton B");
			break;
		case Xbox360Pad.BUTTON_X:
			personaje.Enfundar();
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
		if(personaje.isPaused())
			return true;
		
		switch(axisCode){
		case Xbox360Pad.AXIS_LEFT_X:
			System.out.println("Se movio el AXIS LEFT_X a valor: "+ value);
			if(value>0.5)
				personaje.MoverDerecha();
			else if(value<-0.5)
				personaje.MoverIzquierda();
			else 
				personaje.Detener();
			break;
//		case Xbox360Pad.AXIS_LEFT_Y:
//			System.out.println("Se movio el AXIS LEFT_Y a valor: "+ value);
//			break;
//		case Xbox360Pad.AXIS_RIGHT_X:
//			System.out.println("Se movio el AXIS RIGHT_X a valor: "+ value);
//			break;
		case Xbox360Pad.AXIS_RIGHT_Y:
			if(value< -0.5 || value>0.5)
				camera.zoom+=value;
			break;
		case Xbox360Pad.AXIS_LEFT_TRIGGER:
			System.out.println("Se movio el AXIS LT a valor: "+ value);
			break;
		case Xbox360Pad.AXIS_RIGHT_TRIGGER:
			System.out.println("Se movio el AXIS RT a valor: "+ value);
			break;
		default:
			return false;
	}
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
