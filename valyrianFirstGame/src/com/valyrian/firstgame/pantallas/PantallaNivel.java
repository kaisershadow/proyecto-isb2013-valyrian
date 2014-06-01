package com.valyrian.firstgame.pantallas;


import static com.valyrian.firstgame.utilidades.GameVariables.*;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionJugador;
import com.valyrian.firstgame.animaciones.AnimacionRana;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.EntidadDibujable;
import com.valyrian.firstgame.entidades.Hud;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.inteligencia.InteligenciaRana;
import com.valyrian.firstgame.utilidades.ManejadorColisiones;
import com.valyrian.firstgame.utilidades.input.Joystick;
import com.valyrian.firstgame.utilidades.input.Teclado;

public class PantallaNivel implements Screen {
		
	private BitmapFont font; 
	private Joystick js;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera,hudCam;	
	
	private SpriteBatch batch;
	private Jugador jugador;
	private Hud hud;
	
	private static Array<EntidadDibujable> entidades;
	
	private World mundo;
	private OrthogonalTiledMapRenderer otmr;
	private int mapW,mapH,tileW,tileH;
	private ManejadorColisiones manejaColisiones;
	private Box2DMapObjectParser mapParser;
	private Quetzal juego;
	
	private int[] alante={6};
	private int[] atras={2,3};
	
	public PantallaNivel(Quetzal primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!PAUSE){
			mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

			//		Eliminar cuerpos
			if(!mundo.isLocked()){
				Array<Body> cuerpos = manejaColisiones.getCuerposABorrar();
				//for (Body body : cuerpos) {
				for(int i=0;i<cuerpos.size;i++){
					Body b = cuerpos.get(i);
					EntidadDibujable entidad = ((EntidadDibujable)b.getUserData());
					//frog.dispose();
					entidades.removeValue(entidad, true);
					mundo.destroyBody(b);
					//Sumar puntos por la rana o hacer alguna actualizacion (dentro del FOR aun)
				}
				manejaColisiones.getCuerposABorrar().clear();
			}
			//Actualizar Personaje
			jugador.actualizarCamara(camera, mapW, mapH, tileW, tileH);
		}
		
		//Renderizar mapa
		otmr.setView(camera);
		otmr.render(atras);
		
		//Renderizar entidades
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		jugador.render(delta, batch);
		
		for (EntidadDibujable ent : entidades) {
			ent.render(delta,batch);
		}
		batch.end();
		otmr.render(alante);
		
		batch.setProjectionMatrix(hudCam.combined);
		hud.render(batch);
//		
		if(PAUSE){
//			batch.setProjectionMatrix(hudCam.combined);
			batch.begin();
			batch.draw(juego.manejadorRecursos.get("images/pausa.png",Texture.class), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			batch.end();
		}
		if(debug)
			debugRenderer.render(mundo, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
//		camera.viewportWidth = (width/2/ManejadorVariables.PIXELSTOMETERS);
//		camera.viewportHeight =(height/2/ManejadorVariables.PIXELSTOMETERS);
//		camera.update();
	}

	@Override
	public void show() {
		
		entidades = new Array<EntidadDibujable>();
	
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.setScale(1f/PIXELSTOMETERS);
	
		//Inicializacion de las variables
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/tiles/bosque2.tmx"),1/PIXELSTOMETERS);
		
		batch = juego.getSpriteBatch();
		
		
		camera.viewportWidth = (V_WIDTH/PIXELSTOMETERS);
		camera.viewportHeight =(V_HEIGHT/PIXELSTOMETERS);
		camera.update();
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false);
		
	//	ImagenEnFixture= new Box2DSprite(new Texture("mapas/tiles/waspidle.png"));

		
		mapW = otmr.getMap().getProperties().get("width", Integer.class);
		tileW= otmr.getMap().getProperties().get("tilewidth", Integer.class);
		mapH = otmr.getMap().getProperties().get("height", Integer.class);
		tileH= otmr.getMap().getProperties().get("tileheight", Integer.class);

		mundo = new World(new Vector2(0,-9.8f), true);
		
		mapParser= new Box2DMapObjectParser(1/PIXELSTOMETERS);
		mapParser.load(mundo, otmr.getMap());	
	
		
//		personaje = new Jugador(32/PIXELSTOMETERS, 64/PIXELSTOMETERS, 100, new Vector2(2.5f,5.5f), new Vector2(500/PIXELSTOMETERS, 500/PIXELSTOMETERS),mundo);
		//AnimacionJugador maj = new AnimacionJugador(new Texture("personajes/nativo.png"));
		AnimacionJugador maj = new AnimacionJugador(juego.manejadorRecursos.get("personajes/nativo.png",Texture.class));
		jugador = new Jugador(juego,32, 64, 500,500, new Vector2(2.5f,5.5f), 100, mundo,maj);
		hud = new Hud(jugador,juego);
		
		System.out.println("SE CREO EL PERSONAJE");
		Gdx.input.setInputProcessor(new Teclado(this));
//		Gdx.input.setInputProcessor(new Joystick(personaje, camera, juego));
		js = new Joystick(this);
		Controllers.addListener(js);
		
		
		//CREACION DE LAS RANAS ENEMIGAS	
			//Cargar Ranas
			MapLayer layer = otmr.getMap().getLayers().get("SpawnRana");
			
			Texture tx =juego.manejadorRecursos.get("personajes/rana.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionRana mar = new AnimacionRana(tx);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
	//			posAux.y = mapParser.getBodies().get("spawnrana"+i).getPosition().y;
	//			System.out.println("NO SE CREO LA RANA");
	//			System.out.println("POSAUX(X,Y)= ("+posAux.x+", "+posAux.y+")");
				Enemigo rana = new Enemigo(juego,32, 32, posAux.x,posAux.y, new Vector2(0,0),10,100,mundo,mar);
				float lim = 4+(float)Math.random()*(2);
//				float lim = 5;
				InteligenciaRana mir  = new InteligenciaRana(juego,lim, rana);
				rana.setInteligencia(mir);
//				rana = new Enemigo(ancho, alto, posIniX, posIniY, vel, danioAux, vidaMax, m, t, ma, mi)
				System.out.println("SE CREO LA RANA");
				entidades.add(rana);
	//			System.out.println("SE AGREGO LA RANA");
	//			rana =new Rana(32/PIXELSTOMETERS, 32/PIXELSTOMETERS, 100,10, new Vector2(5,6), posAux,mundo);
	//			ranas.insert(i, rana);
			}
//		for(int i=0;i<11;i++){
//		 mapParser.getBodies().get("muerte"+i).setUserData("muerte");
//		}
			
		manejaColisiones= new ManejadorColisiones(jugador);
		mundo.setContactListener(manejaColisiones);

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		//batch.dispose();
	//	personaje.dispose();
		debugRenderer.dispose();
		otmr.getMap().dispose();
		otmr.dispose();
		entidades.clear();
		Controllers.removeListener(js);
//		hud.dispose();
//		for (Rana frog : ranas) {
//			frog.dispose();
//		}
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA NIVEL");
	}

//	Setters y Getters
	
	public OrthographicCamera getCamera(){ return camera; }
	
	public Jugador getJugador(){return jugador; }
	
	public Quetzal getJuego(){ return juego; }
	
	public static Array<EntidadDibujable> getEntidades(){ return entidades; }
	
	public World getWorld() { return mundo; }
	
}
