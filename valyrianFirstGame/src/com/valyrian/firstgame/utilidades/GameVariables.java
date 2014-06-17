package com.valyrian.firstgame.utilidades;

public final class  GameVariables {
	
	public static final float PIXELSTOMETERS=96; 	//96 pixeles, equivalen a 1 metro, esto es para efectos de las fisicas en Box2D
	public static final float TIMESTEP = 1/60f;
	public static final int VELOCITYITERATIONS = 8;
	public static final int POSITIONITERATIONS = 3;
	
	public static final short BITS_ENTORNO = 1;
	public static final short BITS_JUGADOR = 2;
	public static final short BITS_ENEMIGO = 4;
	public static final short BITS_MUERTE = 8;
	public static final short BITS_PROYECTIL = 16;
	public static final short BITS_COLECTABLE = 32;
	public static final short BITS_PLATAFORMA = 64;
	public static final short BITS_META = 128;
	
	public static final boolean debug = false;
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 600;
	public static float VOLUMEN = 0.8f;
	public static boolean PAUSE = false;

	public static String PLAYER_NAME = "Quetzal";
}