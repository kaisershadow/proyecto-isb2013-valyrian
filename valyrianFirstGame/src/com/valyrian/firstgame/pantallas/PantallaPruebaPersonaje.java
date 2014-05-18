package com.valyrian.firstgame.pantallas;


import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Rana;
import com.valyrian.firstgame.utilidades.ManejadorColisiones;
import com.valyrian.firstgame.utilidades.input.Joystick;
import com.valyrian.firstgame.utilidades.input.Teclado;
import com.valyrian.firstgame.utilidades.recursos.ManejadorRecursos;
import com.badlogic.gdx.utils.Array;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

public class PantallaPruebaPersonaje implements Screen {
		
	private BitmapFont font; 
	
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;	
	
	private SpriteBatch batch;
	private Jugador personaje;

	private Array<Rana> ranas;
	private Rana rana;
	
	private World mundo;
	private OrthogonalTiledMapRenderer otmr;
	private int mapW,mapH,tileW,tileH;
	private ManejadorColisiones manejaColisiones;
	private Box2DMapObjectParser mapParser;
	private PrimerJuego juego;
	
	public PantallaPruebaPersonaje(PrimerJuego primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		if(!personaje.isPaused()){
			mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		//Eliminar cuerpos
		if(!mundo.isLocked()){
		   Array<Body> cuerpos = manejaColisiones.getCuerposABorrar();
			//for (Body body : cuerpos) {
				for(int i=0;i<cuerpos.size;i++){
					Body b = cuerpos.get(i);
					Rana frog = ((Rana)b.getUserData());
					frog.dispose();
					ranas.removeValue(frog, true);
					mundo.destroyBody(b);
					//Sumar puntos por la rana o hacer alguna actualizacion (dentro del FOR aun)
				}
		}
		manejaColisiones.getCuerposABorrar().clear();
		
		
		
		//Actualizar Personaje
		personaje.actualizarPosicionJugador();
		personaje.actualizarCamara(camera, mapW, mapH, tileW/PIXELSTOMETERS, tileH/PIXELSTOMETERS);
	}
		
		
		//Renderizar mapa
		otmr.setView(camera);
		otmr.render();
	
		//Renderizar personajes, enemigos y recolectables
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		personaje.renderJugador(delta, batch);
		//rana.renderRana(batch);
		//OJO
//		ranas.get(0).renderRana(batch);
//		ranas.get(10).renderRana(batch);
		//HASTA AQUI EL OJO
		//Roger hizo esto			
		//Box2DSprite.draw(batch, mundo);
	
		for (Rana r : ranas) {
			r.renderRana(batch);
		}

		if(manejaColisiones.getGameOver())
			System.out.println("Se acabo el juego");
			//hasta aca

		batch.end();
		
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
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.setScale(1f/PIXELSTOMETERS);
		//Inicializacion de las variables
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/tiles/bosque2.tmx"),1/PIXELSTOMETERS);
		batch = new SpriteBatch();
		
		
		camera.viewportWidth = (V_WIDTH/PIXELSTOMETERS);
		camera.viewportHeight =(V_HEIGHT/PIXELSTOMETERS);
		camera.update();
		
	//	ImagenEnFixture= new Box2DSprite(new Texture("mapas/tiles/waspidle.png"));

		
		mapW = otmr.getMap().getProperties().get("width", Integer.class);
		tileW= otmr.getMap().getProperties().get("tilewidth", Integer.class);
		mapH = otmr.getMap().getProperties().get("height", Integer.class);
		tileH= otmr.getMap().getProperties().get("tileheight", Integer.class);

		mundo = new World(new Vector2(0,-9.8f), true);
		
		mapParser= new Box2DMapObjectParser(1/PIXELSTOMETERS);
		mapParser.load(mundo, otmr.getMap());	
		
		personaje = new Jugador(32/PIXELSTOMETERS, 64/PIXELSTOMETERS, 100, new Vector2(2.5f,5.5f), new Vector2(500/PIXELSTOMETERS, 500/PIXELSTOMETERS),mundo);
		
		Gdx.input.setInputProcessor(new Teclado(personaje, camera, juego));
//		Gdx.input.setInputProcessor(new Joystick(personaje, camera, juego));
		Controllers.addListener(new Joystick(personaje, camera, juego));
		
		
		//CREACION DE LAS RANAS ENEMIGAS	
		ranas = new Array<Rana>();
		for(int i=0;i<17;i++){
			Vector2 posAux = new Vector2();
			posAux.x = mapParser.getBodies().get("spawnrana"+i).getPosition().x;
			posAux.y = mapParser.getBodies().get("spawnrana"+i).getPosition().y;
			rana =new Rana(32/PIXELSTOMETERS, 32/PIXELSTOMETERS, 100,10, new Vector2(5,6), posAux,mundo);
			ranas.insert(i, rana);
		}
		for(int i=0;i<11;i++){
		 mapParser.getBodies().get("muerte"+i).setUserData("muerte");
		}
			manejaColisiones= new ManejadorColisiones(personaje);
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
		batch.dispose();
		personaje.dispose();
		debugRenderer.dispose();
		otmr.getMap().dispose();
		otmr.dispose();
//		for (Rana frog : ranas) {
//			frog.dispose();
//		}
		System.out.println("SE LLAMO AL DISPOSE DE PANTALLA PRUEBA");
	}

}
