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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionJugador;
import com.valyrian.firstgame.animaciones.AnimacionUnica;
import com.valyrian.firstgame.entidades.Coleccionable;
import com.valyrian.firstgame.entidades.Enemigo;
import com.valyrian.firstgame.entidades.EntidadDibujable;
import com.valyrian.firstgame.entidades.Hud;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Plataforma;
import com.valyrian.firstgame.inteligencia.InteligenciaAvispa;
import com.valyrian.firstgame.inteligencia.InteligenciaRana;
import com.valyrian.firstgame.utilidades.ManejadorColisiones;
import com.valyrian.firstgame.utilidades.input.Joystick;
import com.valyrian.firstgame.utilidades.input.Teclado;

public class PantallaNivel implements Screen {

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
	private  String nivel;
	private Music musica;
	private int[] alante={2};
	private int[] atras={0,1,3};
	
	public PantallaNivel(Quetzal primerJuego) {
		juego = primerJuego;
		this.nivel="nivel1";
	}

	private void eliminarCuerpos(){
		if(!mundo.isLocked()){
			Array<Body> cuerpos = manejaColisiones.getCuerposABorrar();
			for(int i=0;i<cuerpos.size;i++){
				Body b = cuerpos.get(i);
				EntidadDibujable entidad = ((EntidadDibujable)b.getUserData());
				entidades.removeValue(entidad, true);
				mundo.destroyBody(b);
			}
			manejaColisiones.getCuerposABorrar().clear();
		}
	}
	
	private void update(){
		if(PAUSE)
			return;
		mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		this.eliminarCuerpos();
		if(jugador.finJuego){
			juego.setScreen(juego.pantallaFinNivel);
			return;
		}	
		//Actualizar Personaje
		jugador.actualizarCamara(camera, mapW, mapH, tileW, tileH);
	}
	
	@Override
	public void render(float delta) {
		this.update();
		if(jugador.finJuego)
			return;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		
		if(PAUSE){
			batch.begin();
			batch.draw(pausa, Gdx.graphics.getWidth()/2-128, Gdx.graphics.getHeight()/2-16);
			batch.end();
		}
		if(debug)
			debugRenderer.render(mundo, camera.combined);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {
		PAUSE = false;
		musica = Quetzal.getManejaRecursos().get("audio/"+nivel+".mp3",Music.class);
		musica.setLooping(true);
		musica.setVolume(VOLUMEN);
		musica.play();
		pausa = Quetzal.getManejaRecursos().get("images/pausa.png", Texture.class);
		entidades = new Array<EntidadDibujable>();
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/"+nivel+".tmx"),1/PIXELSTOMETERS);
		
		batch = Quetzal.getSpriteBatch();
		
		
		camera.viewportWidth = (V_WIDTH/PIXELSTOMETERS);
		camera.viewportHeight =(V_HEIGHT/PIXELSTOMETERS);
		camera.update();
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false);
	
		mapW = otmr.getMap().getProperties().get("width", Integer.class);
		tileW= otmr.getMap().getProperties().get("tilewidth", Integer.class);
		mapH = otmr.getMap().getProperties().get("height", Integer.class);
		tileH= otmr.getMap().getProperties().get("tileheight", Integer.class);

		mundo = new World(new Vector2(0,-9.8f), true);
		
		mapParser= new Box2DMapObjectParser(1/PIXELSTOMETERS);
		mapParser.load(mundo, otmr.getMap());	

		
		Vector2 posSpawn = new Vector2();
		posSpawn = mapParser.getBodies().get("spawn").getPosition();
	
		AnimacionJugador maj = new AnimacionJugador(Quetzal.getManejaRecursos().get("personajes/nativo.png",Texture.class),32,64,1/12f);
		jugador = new Jugador(32, 64, posSpawn.x*PIXELSTOMETERS,posSpawn.y*PIXELSTOMETERS, new Vector2(2.5f,6f),(400-DIFICULTAD*100) , mundo,maj);
		maj.setJugador(jugador);
	
		hud = new Hud(jugador);
		Gdx.input.setInputProcessor(new Teclado(this));
		js = new Joystick(this);
		Controllers.addListener(js);

		//Cargar Ranas
		MapLayer layer = otmr.getMap().getLayers().get("SpawnRana");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("personajes/rana.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mar = new AnimacionUnica(tx, 32, 32, 1/5f);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y")-16f;
				Enemigo rana = new Enemigo(32, 32, posAux.x,posAux.y, new Vector2(0,0),15,(DIFICULTAD*2-1)*20,mundo,mar);
				float lim = 4+(float)Math.random()*(2);
				InteligenciaRana mir  = new InteligenciaRana(lim, rana);
				rana.setInteligencia(mir);
				entidades.add(rana);
			}
		}

		//Cargar Avispas
		layer = otmr.getMap().getLayers().get("SpawnAvispa");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("personajes/avispa.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mav = new AnimacionUnica(tx, 32, 32, 1/11f);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Enemigo avispa = new Enemigo(32, 32, posAux.x,posAux.y, new Vector2(1,0),20,DIFICULTAD*20,mundo,mav);
				InteligenciaAvispa mia  = new InteligenciaAvispa(avispa);
				avispa.setInteligencia(mia);
				entidades.add(avispa);
			}
		}

		//Cargar Cofres
		layer = otmr.getMap().getLayers().get("SpawnCofre");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("personajes/moneda.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mae = new AnimacionUnica(tx, 168, 168, 1/11f);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Coleccionable col= new Coleccionable(32, 32, posAux.x,posAux.y, new Vector2(0,0),10,mundo,mae);
				entidades.add(col);
			}
		}

		//		Cargar Plataformas horizontales
		layer = otmr.getMap().getLayers().get("BasesMoviblesHorizontal");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("images/plat"+nivel+".png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mae = new AnimacionUnica(tx, 504, 114, 0);
				Vector2 posAux = new Vector2();
				float anc = (float) ((RectangleMapObject)mo).getRectangle().getWidth();
				float alt = (float) ((RectangleMapObject)mo).getRectangle().getHeight();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.x+= anc/2;
				posAux.y = (Float) mo.getProperties().get("y");
				posAux.y+= alt/2;
				Plataforma plat= new Plataforma(anc,alt , new Vector2(1.5f,0), posAux.x, posAux.y, mundo, mae);
				entidades.add(plat);
			}
		}

		//Cargar Plataformas verticales
		layer = otmr.getMap().getLayers().get("BasesMoviblesVertical");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("images/plat"+nivel+".png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mae = new AnimacionUnica(tx, 504, 114, 0);
				Vector2 posAux = new Vector2();
				float anc = (float) ((RectangleMapObject)mo).getRectangle().getWidth();
				float alt = (float) ((RectangleMapObject)mo).getRectangle().getHeight();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.x+= anc/2;
				posAux.y = (Float) mo.getProperties().get("y");
				posAux.y+= alt/2;
				Plataforma plat= new Plataforma(anc,alt , new Vector2(0,1.5f), posAux.x, posAux.y, mundo, mae);
				entidades.add(plat);
			}
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
		musica.stop();
		Quetzal.getManejaRecursos().unload("audio/"+nivel+".mp3");
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
	
	public String getNivel(){return nivel; }
	
	public void setNivel(String level){ this.nivel = level; }
}