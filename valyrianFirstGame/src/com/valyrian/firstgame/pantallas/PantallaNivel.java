package com.valyrian.firstgame.pantallas;


import static com.valyrian.firstgame.utilidades.GameVariables.*;
import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.valyrian.firstgame.animaciones.AnimacionAvispa;
import com.valyrian.firstgame.animaciones.AnimacionEstatica;
import com.valyrian.firstgame.animaciones.AnimacionJugador;
import com.valyrian.firstgame.animaciones.AnimacionMoneda;
import com.valyrian.firstgame.animaciones.AnimacionRana;
import com.valyrian.firstgame.entidades.Coleccionable;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.EntidadDibujable;
import com.valyrian.firstgame.entidades.Hud;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.inteligencia.InteligenciaAvispa;
import com.valyrian.firstgame.inteligencia.InteligenciaRana;
import com.valyrian.firstgame.utilidades.ManejadorColisiones;
import com.valyrian.firstgame.utilidades.input.Joystick;
import com.valyrian.firstgame.utilidades.input.Teclado;

public class PantallaNivel implements Screen {
		
//	private BitmapFont font; 
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
	private Texture pausa;
	private Texture gameover;
	
	private Music musica;
	
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
				for(int i=0;i<cuerpos.size;i++){
					System.out.println("PRUEBA a");
					Body b = cuerpos.get(i);
					System.out.println("PRUEBA b");
					EntidadDibujable entidad = ((EntidadDibujable)b.getUserData());
					System.out.println("PRUEBA c");
					entidades.removeValue(entidad, true);
					mundo.destroyBody(b);
					//Sumar puntos por la rana o hacer alguna actualizacion (dentro del FOR aun)
				}
				manejaColisiones.getCuerposABorrar().clear();
				System.out.println("PRUEBA 2");
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
			batch.begin();
			if(jugador.estaMuerto())
				batch.draw(gameover, Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/2);
			else
				batch.draw(pausa, Gdx.graphics.getWidth()/2-128, Gdx.graphics.getHeight()/2-16);
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
		PAUSE = false;
		musica = Quetzal.getManejaRecursos().get("audio/nivel1.mp3",Music.class);
		musica.setLooping(true);
		musica.setVolume(VOLUMEN);
		musica.play();
		pausa = Quetzal.getManejaRecursos().get("images/pausa.png", Texture.class);
		gameover = Quetzal.getManejaRecursos().get("images/juego_acabado.png", Texture.class);
//		font = new BitmapFont();
//		font.setColor(Color.WHITE);
//		font.setScale(1f/PIXELSTOMETERS);
		//Inicializacion de las variables
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/tiles/bosque2.tmx"),1/PIXELSTOMETERS);
		
		batch = Quetzal.getSpriteBatch();
		
		
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
	
		
		AnimacionJugador maj = new AnimacionJugador(Quetzal.getManejaRecursos().get("personajes/nativo.png",Texture.class));
		jugador = new Jugador(32, 64, 500,500, new Vector2(2.5f,5.5f), 100, mundo,maj);
		hud = new Hud(jugador);
		
		System.out.println("SE CREO EL PERSONAJE");
		Gdx.input.setInputProcessor(new Teclado(this));
//		Gdx.input.setInputProcessor(new Joystick(personaje, camera, juego));
		js = new Joystick(this);
		Controllers.addListener(js);
		
		
		//CREACION DE LAS RANAS ENEMIGAS	
			//Cargar Ranas
			MapLayer layer = otmr.getMap().getLayers().get("SpawnRana");
			
			Texture tx =Quetzal.getManejaRecursos().get("personajes/rana.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionRana mar = new AnimacionRana(tx);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y")-16f;
				Enemigo rana = new Enemigo(32, 32, posAux.x,posAux.y, new Vector2(0,0),10,100,mundo,mar);
				float lim = 4+(float)Math.random()*(2);
				InteligenciaRana mir  = new InteligenciaRana(lim, rana);
				rana.setInteligencia(mir);
				entidades.add(rana);
			}
			
			//Cargar Avispas
			layer = otmr.getMap().getLayers().get("SpawnAvispa");
			
			tx =Quetzal.getManejaRecursos().get("personajes/avispa.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionAvispa mav = new AnimacionAvispa(tx);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Enemigo avispa = new Enemigo(32, 32, posAux.x,posAux.y, new Vector2(1,0),15,100,mundo,mav);
				//float lim = 4+(float)Math.random()*(2);
				InteligenciaAvispa mia  = new InteligenciaAvispa(avispa);
				avispa.setInteligencia(mia);
				entidades.add(avispa);
			}
			
			//Cargar Cofres
			layer = otmr.getMap().getLayers().get("SpawnCofre");
			for(MapObject mo : layer.getObjects()){
				AnimacionMoneda mae = new AnimacionMoneda(Quetzal.getManejaRecursos().get("personajes/moneda.png",Texture.class));
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Coleccionable col= new Coleccionable(32, 32, posAux.x,posAux.y, new Vector2(0,0),10,mundo,mae);
				entidades.add(col);
			}
			
			//Cargar mas cofres
			layer = otmr.getMap().getLayers().get("SpawnArbusto");
			for(MapObject mo : layer.getObjects()){
				AnimacionMoneda mae = new AnimacionMoneda(Quetzal.getManejaRecursos().get("personajes/moneda.png",Texture.class));
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Coleccionable col= new Coleccionable(32, 32, posAux.x,posAux.y, new Vector2(0,0),10,mundo,mae);
				entidades.add(col);
			}
			
			
			
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
		Quetzal.getManejaRecursos().unload("audio/nivel1.mp3");
		otmr.dispose();
		entidades.clear();
		mundo.dispose();
		Controllers.removeListener(js);
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
