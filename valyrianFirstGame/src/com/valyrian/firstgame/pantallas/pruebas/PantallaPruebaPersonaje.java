package com.valyrian.firstgame.pantallas.pruebas;

import java.util.Iterator;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Rana;
import com.valyrian.firstgame.utilitarios.ManejadorColisiones;
import com.valyrian.firstgame.utilitarios.ManejadorUnidades;
import com.valyrian.firstgame.utilitarios.Teclado;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;


public class PantallaPruebaPersonaje implements Screen {
	
	//Variable auxiliar para decir si se hace debug-render o no
	private boolean debug =true;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;	
	
	private SpriteBatch batch;
	private Jugador personaje;

	//private Rana inicir;
	private Array<Rana> ranas;
	private Rana rana;
//	private Box2DSprite ImagenEnFixture;

	
	
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

		mundo.step(ManejadorUnidades.TIMESTEP, ManejadorUnidades.VELOCITYITERATIONS, ManejadorUnidades.POSITIONITERATIONS);
		
		//Actualizar Personaje
		personaje.actualizarPosicionJugador();
		personaje.actualizarCamara(camera, mapW, mapH, tileW/ManejadorUnidades.PIXELSTOMETERS, tileH/ManejadorUnidades.PIXELSTOMETERS);

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
		for (Rana r : ranas) {
			r.renderRana(batch);
		}
		//HASTA AQUI EL OJO
		//Roger hizo esto			
		//Box2DSprite.draw(batch, mundo);
		Iterator<Fixture> i = manejaColisiones.getListaABorrar().iterator();
		if(!mundo.isLocked()){
		   while(i.hasNext()){
			  Fixture b = i.next();
			  if(b.getUserData()!=null){
				  ((Rana)(b.getBody().getUserData())).dispose();
				  mundo.destroyBody(b.getBody());
			  }
		      i.remove();
		   }
		   manejaColisiones.getListaABorrar().clear();
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
		camera.viewportWidth = (width/2/ManejadorUnidades.PIXELSTOMETERS);
		camera.viewportHeight =(height/2/ManejadorUnidades.PIXELSTOMETERS);
		camera.update();
	}

	@Override
	public void show() {
		//Inicializacion de las variables
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/tiles/bosque2.tmx"),1/ManejadorUnidades.PIXELSTOMETERS);
		batch = new SpriteBatch();
		
		
	//	ImagenEnFixture= new Box2DSprite(new Texture("mapas/tiles/waspidle.png"));

		
		mapW = otmr.getMap().getProperties().get("width", Integer.class);
		tileW= otmr.getMap().getProperties().get("tilewidth", Integer.class);
		mapH = otmr.getMap().getProperties().get("height", Integer.class);
		tileH= otmr.getMap().getProperties().get("tileheight", Integer.class);

		mundo = new World(new Vector2(0,-9.8f), true);
		
		mapParser= new Box2DMapObjectParser(1/ManejadorUnidades.PIXELSTOMETERS);
		mapParser.load(mundo, otmr.getMap());	
		
		personaje = new Jugador(32/ManejadorUnidades.PIXELSTOMETERS, 64/ManejadorUnidades.PIXELSTOMETERS, 100, new Vector2(3,6), new Vector2(500/ManejadorUnidades.PIXELSTOMETERS, 500/ManejadorUnidades.PIXELSTOMETERS),mundo);
		
		
		//rana = new Rana(64/ManejadorUnidades.PIXELSTOMETERS, 64/ManejadorUnidades.PIXELSTOMETERS, 100, new Vector2(2,3),new Vector2(300/ManejadorUnidades.PIXELSTOMETERS, 300/ManejadorUnidades.PIXELSTOMETERS),mundo);
		//Gdx.input.setInputProcessor(Teclado);
		Gdx.input.setInputProcessor(new InputMultiplexer(new Teclado(personaje,camera,juego)));
		//OJO CON ESTO
		
		ranas = new Array<Rana>();
		Vector2 posAux = new Vector2();
		for(int i=0;i<4;i++){
			posAux.x = mapParser.getBodies().get("spawnrana"+i).getPosition().x;
			posAux.y = mapParser.getBodies().get("spawnrana"+i).getPosition().y;
			System.out.println("POSICION ANTES DE ESTAR EN LA LISTA: ("+posAux.x+" ,"+posAux.y+")");
			//posAux.x = (64*i+256)/ManejadorUnidades.PIXELSTOMETERS;
			//posAux.y = (64*i+256)/ManejadorUnidades.PIXELSTOMETERS;
			//ranas.add(rana);
			//System.out.println("POSICION AL CREARSE: ("+rana.getPosicion().x+" ,"+rana.getPosicion().y+")");
			rana =new Rana(32/ManejadorUnidades.PIXELSTOMETERS, 64/ManejadorUnidades.PIXELSTOMETERS, 100, new Vector2(5,6), posAux,mundo);
			ranas.insert(i, rana);
			System.out.println("POSICION LUEGO DE ESTAR EN LA LISTA: ("+ranas.get(i).getPosicion().x+" ,"+ranas.get(i).getPosicion().y+")");
			System.out.println("POSICION DEL PRIMERO DENTRO DEL FOR EN LA LISTA: ("+ranas.get(0).getPosicion().x+" ,"+ranas.get(0).getPosicion().y+")");

		}
		for(int i=0;i<11;i++){
		 mapParser.getBodies().get("muerte"+i).setUserData("muerte");
		}
			manejaColisiones= new ManejadorColisiones(personaje);
			mundo.setContactListener(manejaColisiones);
		//}

			

			//HASTA AQUI EL OJO

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
