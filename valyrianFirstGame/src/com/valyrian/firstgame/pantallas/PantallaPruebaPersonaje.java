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
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Hud;
import com.valyrian.firstgame.utilidades.ManejadorColisiones;
import com.valyrian.firstgame.utilidades.input.Joystick;
import com.valyrian.firstgame.utilidades.input.Teclado;
import com.valyrian.firstgame.refactor.entidades.Enemigo;
import com.valyrian.firstgame.refactor.entidades.Entidad;
import com.valyrian.firstgame.refactor.entidades.Jugador;

public class PantallaPruebaPersonaje implements Screen {
		
	private BitmapFont font; 
	private Joystick js;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera,hudCam;	
	
	private SpriteBatch batch;
	//private Jugador personaje;
	private Jugador jugador;
	private Hud hud;
	
	private Array<Entidad> entidades;
	
	private World mundo;
	private OrthogonalTiledMapRenderer otmr;
	private int mapW,mapH,tileW,tileH;
	private ManejadorColisiones manejaColisiones;
	private Box2DMapObjectParser mapParser;
	private PrimerJuego juego;
	
	private int[] alante={6};
	private int[] atras={2,3};
	
	public PantallaPruebaPersonaje(PrimerJuego primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		System.out.println("RENDERIZO");
		if(!PAUSE){
			mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
//		Eliminar cuerpos
		if(!mundo.isLocked()){
		   Array<Body> cuerpos = manejaColisiones.getCuerposABorrar();
			//for (Body body : cuerpos) {
				for(int i=0;i<cuerpos.size;i++){
					Body b = cuerpos.get(i);
					Enemigo enemy = ((Enemigo)b.getUserData());
					//frog.dispose();
					entidades.removeValue(enemy, true);
					mundo.destroyBody(b);
					//Sumar puntos por la rana o hacer alguna actualizacion (dentro del FOR aun)
				}
		}
		manejaColisiones.getCuerposABorrar().clear();
		
//		System.out.println("OJO1");
		
		//Actualizar Personaje
		jugador.actualizarPosicion();
//		personaje.actualizarCamara(camera, mapW, mapH, tileW/PIXELSTOMETERS, tileH/PIXELSTOMETERS);
		jugador.actualizarCamara(camera, mapW, mapH, tileW, tileH);
//		System.out.println("OJO2");
		for (Entidad ent : entidades) {
			ent.actualizarPosicion();
		}
		
		
		}
//		System.out.println("OJO3");
		
		//Renderizar mapa
		otmr.setView(camera);
		otmr.render(atras);
//		otmr.render();
		//		otmr.renderTileLayer((TiledMapTileLayer) otmr.getMap().getLayers().get(name));
//		otmr.renderTileLayer((TiledMapTileLayer) otmr.getMap().getLayers().get("estr"));
	
		//Renderizar personajes, enemigos y recolectables
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
//		System.out.println("VA A RENDER EL PERSONAJES");
//		System.out.println("OJO4");
		jugador.render(delta, batch);
//		System.out.println("YA RENDER EL PERSONAJES");
		//rana.renderRana(batch);
		//OJO
//		System.out.println("OJO5");
		
		
		for (Entidad ent : entidades) {
			ent.render(delta,batch);
		}
//		System.out.println("OJO6");
//		ranas.get(0).renderRana(batch);
//		ranas.get(10).renderRana(batch);
		//HASTA AQUI EL OJO
		//Roger hizo esto			
		//Box2DSprite.draw(batch, mundo);
		//System.out.println("VA A RENDERIZAR LAS RANAS");
//		for (Rana r : ranas) {
//			r.renderRana(batch);
//		}
//		System.out.println("RENDERIZO LAS RANAS");

//		if(manejaColisiones.getGameOver())
//			System.out.println("Se acabo el juego");
//			//hasta aca
		batch.end();
//		otmr.setView(camera);
		otmr.render(alante);
//		otmr.renderTileLayer((TiledMapTileLayer) otmr.getMap().getLayers().get("capaDelanteDelJugador"));
		
		batch.setProjectionMatrix(hudCam.combined);
		hud.render(batch);
//		
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
		
		entidades = new Array<Entidad>();
	
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
		jugador = new Jugador(32, 64, new Vector2(500, 500), new Vector2(2.5f,5.5f), 100, mundo,new Texture(Gdx.files.internal("personajes/nativo.png")));
		hud = new Hud(jugador);
		
//		System.out.println("SE CREO EL PERSONAJE");
		Gdx.input.setInputProcessor(new Teclado(this));
//		Gdx.input.setInputProcessor(new Joystick(personaje, camera, juego));
		js = new Joystick(this);
		Controllers.addListener(js);
		
		
		//CREACION DE LAS RANAS ENEMIGAS	
	//	ranas = new Array<Rana>();
			//Cargar Ranas
			MapLayer layer = otmr.getMap().getLayers().get("SpawnRana");
		
			for(MapObject mo : layer.getObjects()){
			Vector2 posAux = new Vector2();
			posAux.x = (Float) mo.getProperties().get("x");
			posAux.y = (Float) mo.getProperties().get("y");
//			posAux.y = mapParser.getBodies().get("spawnrana"+i).getPosition().y;
//			System.out.println("NO SE CREO LA RANA");
//			System.out.println("POSAUX(X,Y)= ("+posAux.x+", "+posAux.y+")");
			Enemigo rana = new Enemigo(32, 32, posAux, new Vector2(0,0),10,100,mundo,new Texture(Gdx.files.internal("personajes/rana.png")));
//			System.out.println("SE CREO LA RANA");
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
		batch.dispose();
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
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA PRUEBA");
	}

//	Setters y Getters
	
	public OrthographicCamera getCamera(){ return camera; }
	
	public Jugador getJugador(){return jugador; }
	
	public PrimerJuego getJuego(){ return juego; }
	
	public Array<Entidad> getEntidades(){ return entidades; }
	
	public World getWorld() { return mundo; }
	
}
