package com.valyrian.firstgame.utilidades;

public final class  GameVariables {
	
	public static final float PIXELSTOMETERS=96; 	//96 pixeles, equivalen a 1 metro, esto es para efectos de las fisicas en Box2D
	public static final float TIMESTEP = 1/60f;
	public static final int VELOCITYITERATIONS = 8;
	public static final int POSITIONITERATIONS = 3;
	
	public static final short BITS_ENTORNO = 1;
	public static final short BITS_JUGADOR = 2;
	public static final short BITS_ENEMIGO = 4;
	public static final short BITS_MUERTE = 6;
	public static final short BITS_META = 8;
	public static final short BITS_PROYECTIL = 9;
	
	public static final boolean debug = true;
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 400;
}