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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
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
import com.valyrian.firstgame.inteligencia.IntMovHorizontal;
import com.valyrian.firstgame.inteligencia.IntDisparoBilateral;
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
			((PantallaFinNivel)juego.pantallaFinNivel).setAprobado(!jugador.estaMuerto());
			((PantallaFinNivel)juego.pantallaFinNivel).setPuntuacion(jugador.getPuntaje());
			((PantallaFinNivel)juego.pantallaFinNivel).setNivel(nivel);
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
			batch.draw(pausa, Gdx.graphics.getWidth()/2-pausa.getWidth()/2, Gdx.graphics.getHeight()/2-pausa.getHeight()/2);
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
		batch.flush();
		
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
		
		//Carga del entorno del mapa
		mapParser= new Box2DMapObjectParser(1/PIXELSTOMETERS);
		mapParser.load(mundo, otmr.getMap());	

		this.cargarJugador();
		
		hud = new Hud(jugador);
		Gdx.input.setInputProcessor(new Teclado(this));
		js = new Joystick(this);
		Controllers.addListener(js);


		this.cargarEnemigos();
		
		this.cargarColeccionables();
		
		this.cargarPlataformas();

		this.cargarPoleas();
		
		this.cargarCalendario();
		
		manejaColisiones= new ManejadorColisiones(jugador);
		if(!nivel.equals("nivel3")){
			manejaColisiones.numCalendarios =3;
			}
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
	//	Funciones de carga
	private void cargarJugador(){
		Vector2 posSpawn = new Vector2();
		posSpawn = mapParser.getBodies().get("spawn").getPosition();
		AnimacionJugador maj = new AnimacionJugador(Quetzal.getManejaRecursos().get("personajes/nativo.png",Texture.class),32,64,1/12f);
		jugador = new Jugador(32, 64, posSpawn.x*PIXELSTOMETERS,posSpawn.y*PIXELSTOMETERS, new Vector2(2.5f,6f),(400-DIFICULTAD*100) , mundo,maj);
		maj.setJugador(jugador);
	}
	
	
	private void cargarEnemigos(){
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
				IntDisparoBilateral mir  = new IntDisparoBilateral(lim, rana);
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
				IntMovHorizontal mia  = new IntMovHorizontal(4,avispa);
				avispa.setInteligencia(mia);
				entidades.add(avispa);
			}
		}
		
		
		//Cargar Murcielagos
		layer = otmr.getMap().getLayers().get("SpawnMurcielago");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("personajes/murcielago.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mav = new AnimacionUnica(tx, 32, 32, 1/5f);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Enemigo bat = new Enemigo(32, 32, posAux.x,posAux.y, new Vector2(1,0),20,DIFICULTAD*20,mundo,mav);
				float lim = 2+(float)Math.random()*(3);
				IntDisparoBilateral mir  = new IntDisparoBilateral(lim, bat);
				bat.setInteligencia(mir);
				IntMovHorizontal mia  = new IntMovHorizontal(lim, bat);
				bat.setInteligencia(mia);
				entidades.add(bat);
			}
		}
		
		
		//Cargar Ratones
		layer = otmr.getMap().getLayers().get("SpawnRaton");
		if(layer != null){
			Texture tx =Quetzal.getManejaRecursos().get("personajes/raton.png",Texture.class);
			for(MapObject mo : layer.getObjects()){
				AnimacionUnica mav = new AnimacionUnica(tx, 32, 32, 1/5f);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Enemigo raton = new Enemigo(32, 32, posAux.x,posAux.y-8, new Vector2(1,0),20,DIFICULTAD*20,mundo,mav);
				IntMovHorizontal mia  = new IntMovHorizontal(4,raton);
				raton.setInteligencia(mia);
				entidades.add(raton);
			}
		}	
	}
	
	
	private void cargarColeccionables(){
		//Cargar Cofres
			MapLayer layer = otmr.getMap().getLayers().get("SpawnCofre");
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
	}
	
	
	private void cargarPlataformas(){
		//		Cargar Plataformas horizontales
		MapLayer layer = otmr.getMap().getLayers().get("BasesMoviblesHorizontal");
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
				Plataforma plat= new Plataforma(anc,alt , new Vector2(1f,0), posAux.x, posAux.y, mundo, mae);
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
				Plataforma plat= new Plataforma(anc,alt , new Vector2(0,1f), posAux.x, posAux.y, mundo, mae);
				entidades.add(plat);
			}
		}
	}
	
	
	private void cargarPoleas(){
		//Cargar cajas de poleas
			MapLayer	layer = otmr.getMap().getLayers().get("Caja");
				if(layer != null){
					MapLayer layer1 = otmr.getMap().getLayers().get("PoleaBase");
					MapLayer layer2 = otmr.getMap().getLayers().get("PoleaPuerta");
					MapLayer layer3 = otmr.getMap().getLayers().get("GroundBase");
					MapLayer layer4 = otmr.getMap().getLayers().get("GroundPuerta");
					
					
					Texture tx =Quetzal.getManejaRecursos().get("images/plat"+nivel+".png",Texture.class);
					int i=0;
					for(MapObject mo : layer.getObjects()){
						MapObject mo1= layer1.getObjects().get(i);
						MapObject mo2= layer2.getObjects().get(i);
						MapObject mo3= layer3.getObjects().get(i);
						MapObject mo4= layer4.getObjects().get(i);				
						AnimacionUnica mae = new AnimacionUnica(tx, 504, 114, 0);
						Vector2 posAux = new Vector2();
						float anc = (float) ((RectangleMapObject)mo).getRectangle().getWidth();
						float alt = (float) ((RectangleMapObject)mo).getRectangle().getHeight();
						posAux.x = (Float) mo.getProperties().get("x");
						posAux.x+= anc/2;
						posAux.y = (Float) mo.getProperties().get("y");
						posAux.y+= alt/2;
						Plataforma plat= new Plataforma(anc,alt , new Vector2(0,0), posAux.x, posAux.y, mundo, mae);
						plat.getCuerpo().setType(BodyType.DynamicBody);
						plat.getCuerpo().setGravityScale(1f);
						plat.getCuerpo().getMassData().mass =1000;
						Filter filtAux = plat.getCuerpo().getFixtureList().first().getFilterData();
						filtAux.maskBits = BITS_JUGADOR  | BITS_ENTORNO|BITS_PLATAFORMA;
						plat.getCuerpo().getFixtureList().first().setFilterData(filtAux);
						plat.getCuerpo().getFixtureList().first().setFriction(0.0001f);
						entidades.add(plat);
										
						anc = (float) ((RectangleMapObject)mo1).getRectangle().getWidth();
						alt = (float) ((RectangleMapObject)mo1).getRectangle().getHeight();
						posAux.x = (Float) mo1.getProperties().get("x");
						posAux.x+= anc/2;
						posAux.y = (Float) mo1.getProperties().get("y");
						posAux.y+= alt/2;
						Plataforma plat1 = new Plataforma(anc,alt , new Vector2(0,0), posAux.x, posAux.y, mundo, mae);
						plat1.getCuerpo().setType(BodyType.DynamicBody);
						plat1.getCuerpo().setGravityScale(1f);
						MassData ma =plat1.getCuerpo().getMassData();
						ma.mass = 200;
						plat1.getCuerpo().setMassData(ma);
					
//						System.out.println("MASA BASE"+i+": "+plat1.getCuerpo().getMassData().mass);
						
						filtAux = plat1.getCuerpo().getFixtureList().first().getFilterData();
						filtAux.maskBits = BITS_JUGADOR  |BITS_PLATAFORMA | BITS_ENTORNO;
						plat1.getCuerpo().getFixtureList().first().setFilterData(filtAux);
						entidades.add(plat1);				
						
						anc = (float) ((RectangleMapObject)mo2).getRectangle().getWidth();
						alt = (float) ((RectangleMapObject)mo2).getRectangle().getHeight();
						posAux.x = (Float) mo2.getProperties().get("x");
						posAux.x+= anc/2;
						posAux.y = (Float) mo2.getProperties().get("y");
						posAux.y+= alt/2;
						Plataforma plat2 = new Plataforma(anc,alt , new Vector2(0,0), posAux.x, posAux.y, mundo, mae);
						plat2.getCuerpo().setType(BodyType.DynamicBody);
						plat2.getCuerpo().setGravityScale(1f);
						ma =plat2.getCuerpo().getMassData();
						ma.mass = 160;
						plat2.getCuerpo().setMassData(ma);
						
//						System.out.println("MASA PUERTA "+i+": "+plat2.getCuerpo().getMassData().mass);
						
						
						filtAux.maskBits = BITS_JUGADOR  |BITS_PROYECTIL |BITS_PLATAFORMA;
						plat2.getCuerpo().getFixtureList().first().setFilterData(filtAux);
						entidades.add(plat2);			

						Vector2 posAux1 = new Vector2();
						posAux1.x = (Float) mo3.getProperties().get("x");
						posAux1.x=posAux1.x/96;
						posAux1.y = (Float) mo3.getProperties().get("y");
						posAux1.y=posAux1.y/96;
						posAux.x = (Float) mo4.getProperties().get("x");
						posAux.x=posAux.x/96;
						posAux.y = (Float) mo4.getProperties().get("y");
						posAux.y=posAux.y/96;

						
						PulleyJointDef a= new PulleyJointDef();
						a.initialize(plat1.getCuerpo(), plat2.getCuerpo(), posAux1, posAux, plat1.getCuerpo().getWorldCenter(), plat2.getCuerpo().getWorldCenter(), 0.77f);	
						mundo.createJoint(a);
						
						layer4.getObjects().iterator().next();
						layer3.getObjects().iterator().next();
						layer2.getObjects().iterator().next();
						layer1.getObjects().iterator().next();
						i++;	
					}
				}
	}
	
	private void cargarCalendario(){
		//Cargar piezas del calendario
		MapLayer layer = otmr.getMap().getLayers().get("Monedas");
		if(layer != null){
			int i=1;
			for(MapObject mo : layer.getObjects()){
				Texture tx =Quetzal.getManejaRecursos().get("images/calendario_maya"+i+".png",Texture.class);
				AnimacionUnica mae = new AnimacionUnica(tx, 762, 762, 0);
				Vector2 posAux = new Vector2();
				posAux.x = (Float) mo.getProperties().get("x");
				posAux.y = (Float) mo.getProperties().get("y");
				Coleccionable col= new Coleccionable(64,64, posAux.x, posAux.y,new Vector2(0,0),0, mundo, mae);
				col.getCuerpo().getFixtureList().first().setUserData("Calendario");
				entidades.add(col);
				i++;
			}
		}
		
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