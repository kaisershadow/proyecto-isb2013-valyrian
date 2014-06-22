package com.valyrian.firstgame.utilidades.input;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.valyrian.firstgame.entidades.Proyectil;
import com.valyrian.firstgame.pantallas.PantallaNivel;

public class Joystick implements ControllerListener {

	PantallaNivel pantallaNivel;
	
	public Joystick(PantallaNivel p) {
		this.pantallaNivel = p;
	}
	
	@Override
	public void connected(Controller controller) {
	}

	@Override
	public void disconnected(Controller controller) {
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		if(PAUSE && buttonCode !=Xbox360Pad.BUTTON_START)
			return true;
		
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			pantallaNivel.getJugador().Saltar();
			break;
		case Xbox360Pad.BUTTON_B:
			break;
		case Xbox360Pad.BUTTON_X:
			Proyectil bala = pantallaNivel.getJugador().Disparar(pantallaNivel.getWorld(),20);
			if(bala!=null)
				PantallaNivel.getEntidades().add(bala);
			break;
		case Xbox360Pad.BUTTON_Y:
			break;
		case Xbox360Pad.BUTTON_LB:
			break;
		case Xbox360Pad.BUTTON_RB:
			break;
		case Xbox360Pad.BUTTON_BACK:
			
			break;
		case Xbox360Pad.BUTTON_START:
			pantallaNivel.getJugador().Pausar();
			break;
		case Xbox360Pad.BUTTON_L3:
			break;
		case Xbox360Pad.BUTTON_R3:
			break;
		default:
			return false;
	}
		return true;
			
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		if(PAUSE  && buttonCode !=Xbox360Pad.BUTTON_BACK)
			return true;
		
		switch(buttonCode){
		case Xbox360Pad.BUTTON_A:
			pantallaNivel.getJugador().Aterrizar();
			break;
		case Xbox360Pad.BUTTON_B:
			break;
		case Xbox360Pad.BUTTON_X:
			pantallaNivel.getJugador().Enfundar();
			break;
		case Xbox360Pad.BUTTON_Y:
			break;
		case Xbox360Pad.BUTTON_LB:
			break;
		case Xbox360Pad.BUTTON_RB:
			break;
		case Xbox360Pad.BUTTON_BACK:
			if(PAUSE)
				pantallaNivel.getJuego().setScreen(pantallaNivel.getJuego().pantallaSeleccionNivel);
			break;
		case Xbox360Pad.BUTTON_START:
			break;
		case Xbox360Pad.BUTTON_L3:
			break;
		case Xbox360Pad.BUTTON_R3:
			break;
		default:
			return false;
	}
		return true;	
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		if(PAUSE)
			return true;
		
		switch(axisCode){
		case Xbox360Pad.AXIS_LEFT_X:
			if(value>0.5)
				pantallaNivel.getJugador().MoverDerecha();
			else if(value<-0.5)
				pantallaNivel.getJugador().MoverIzquierda();
			else 
				pantallaNivel.getJugador().Detener();
			break;
		case Xbox360Pad.AXIS_RIGHT_Y:
			if(value< -0.5 || value>0.5)
				pantallaNivel.getCamera().zoom+=value/25f;
			break;
		case Xbox360Pad.AXIS_LEFT_TRIGGER:
			break;
		case Xbox360Pad.AXIS_RIGHT_TRIGGER:
			break;
		default:
			return false;
	}
		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {		
		return true;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
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