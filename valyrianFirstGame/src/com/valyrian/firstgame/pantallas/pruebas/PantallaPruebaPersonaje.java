package com.valyrian.firstgame.pantallas.pruebas;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import com.valyrian.firstgame.PrimerJuego;
import com.valyrian.firstgame.entidades.Jugador;
import com.valyrian.firstgame.entidades.Jugador.ESTADO_ACTUAL;
import com.valyrian.firstgame.utilitarios.Joystick;
import com.valyrian.firstgame.utilitarios.ManejadorUnidades;
import com.valyrian.firstgame.utilitarios.Teclado;

public class PantallaPruebaPersonaje implements Screen {
	
	private static final float TIMESTEP = 1/60f;
	private static final int VELOCITYITERATIONS = 8;
	private static final int POSITIONITERATIONS = 3;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;	
	
	private float contador=0;
	private SpriteBatch batch;
	private Jugador personaje;
	private World mundo;
	private Body platformBody;
	private OrthogonalTiledMapRenderer otmr;
	private int mapW,mapH,tileW,tileH;
	
	private Box2DMapObjectParser manejocol;
	
	private PrimerJuego juego;
	
	//private Vector2 movement = new Vector2();
	//private float speed=500000;
	private Vector2 linVel = new Vector2(32, 32);
	
	
	public PantallaPruebaPersonaje(PrimerJuego primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 
				contador+=delta;
				//System.out.println(contador);
				if(contador>=2){
	//		System.out.println(contador);
			contador=0;
			platformBody.setLinearVelocity(platformBody.getLinearVelocity().x*(-1),platformBody.getLinearVelocity().y*(-1));
			//System.out.println("LINVEL ="+platformBody.getLinearVelocity().x +", "+linVel.y);
		}
		 
//		playerBody.getPosition().x=personaje.getPosicion().x;
//		playerBody.getPosition().y=personaje.getPosicion().y;
//		
		mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		personaje.actualizarPosicionJugador();
		personaje.actualizarCamara(camera, mapW, mapH, tileW, tileH);		
		
		camera.update();
		//otmr.setView(camera.combined,camera.position.x-Gdx.graphics.getWidth()/2, camera.position.y-Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//otmr.setView(camera);
		otmr.setView(camera);
		otmr.render();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
				
		
		
			personaje.renderJugador(delta, batch);
//			for(Body body :tmpBodies)
//				if(body.getUserData()!= null){
//				//	System.out.println("LLEGA AQUI?");
//				
//					
//				}
			
		batch.end();
		debugRenderer.render(mundo, camera.combined);
		
		
		
		

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/2;
		camera.viewportHeight =height/2;
		camera.update();
		//otmr.setView(camera);
	}

	@Override
	public void show() {
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
//		camera.viewportWidth=Gdx.graphics.getWidth();
//		camera.viewportHeight=Gdx.graphics.getHeight();
		camera.update();
		//System.out.println("CAMARA POS: "+camera.position.x+", "+camera.position.y);
//		camera.position.x =0;
//		camera.position.y=0;
	//	System.out.println("CAMARA POS: "+camera.position.x+", "+camera.position.y);

		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/platformer_test.tmx"),1);
		
		//manejocol.setUnitScale(1);
		
//		imageneEnObjetosTile= new Box2DSprite(new Texture("personajes/left1.png"));
		
		//otmr.setView(camera.combined,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//camera.zoom=0.5f;
		//camera.zoom=2;
		//otmr.setView(camera);
		batch = new SpriteBatch();
		
		mapW = otmr.getMap().getProperties().get("width", Integer.class);
		tileW= otmr.getMap().getProperties().get("tilewidth", Integer.class);
		
		mapH = otmr.getMap().getProperties().get("height", Integer.class);
		tileH= otmr.getMap().getProperties().get("tileheight", Integer.class);
		
		mundo = new World(new Vector2(0,-9.81f*ManejadorUnidades.PIXELSTOMETERS), true);
	
		manejocol= new Box2DMapObjectParser(1);
		manejocol.load(mundo, otmr.getMap());	
		
		
		///Definicicion y creacion del cuerpo
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		

		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(200,200);
		bodyDef.angle = 0;
		
		//Box Shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(128, 16);
		
		//Fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 1/32f;
		fixtureDef.isSensor =false;
		platformBody= mundo.createBody(bodyDef);
		platformBody.createFixture(fixtureDef);
		
		platformBody.setLinearVelocity(linVel);

//		boxShape.setAsBox(32, 32,new Vector2(64, 32),0);
//		fixtureDef.isSensor =true;
//		fixtureDef.shape=boxShape;
//		playerBody.createFixture(fixtureDef);
//		//		Filter filtro = new Filter();
//		filtro.maskBits =0;
//		playerBody.getFixtureList().first().setFilterData(filtro);
		//boxSprite = new Sprite(new Texture(Gdx.files.internal("img/luigi_front.png")));
//		boxSprite = new Sprite(new Texture(Gdx.files.internal("frog_idle.gif")));
//		boxSprite.setSize(10, 20);
//		boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
		//box.setUserData(ani);
		//playerBody.applyAngularImpulse(50, true);
		boxShape.dispose();
		//playerBody.setUserData(personaje);
		personaje = new Jugador(32, 64, 100, new Vector2(5*ManejadorUnidades.PIXELSTOMETERS,6*ManejadorUnidades.PIXELSTOMETERS), new Vector2(20, 80),mundo);
		//Gdx.input.setInputProcessor(personaje);

	//	Array<Contact> contacto = mundo.getContactList();
//		Gdx.input.setInputProcessor(new InputProcessor(){
//			@Override
//			public boolean keyDown(int keycode) {
//				switch(keycode){
//					case Keys.W:
//						movement.y = speed;
//						personaje.estado=ESTADO_ACTUAL.Saltando;
//						break;
//					case Keys.A:
//						movement.x = -speed;
//						personaje.estado =ESTADO_ACTUAL.Corriendo;
//						break;
//					case Keys.S:
//						movement.y = -speed;
//						
//						break;
//					case Keys.D:
//						movement.x = speed;
//						personaje.estado =ESTADO_ACTUAL.Caminando;
//						break;
//					default:
//						return false;
//				}
//				return true;
//			}
//			
//			@Override
//			public boolean keyUp(int keycode) {
//				
//				switch(keycode){
//				case Keys.W:
//				case Keys.S:
//					movement.y = 0;
//					
//					break;
//				case Keys.A:
//				case Keys.D:
//					movement.x = 0;
//					personaje.estado =ESTADO_ACTUAL.Quieto;
//					break;
//				default:
//					return false;
//			}
//			return true;
//			}
//			
//			@Override
//			public boolean scrolled(int amount) {
//				camera.zoom+=amount/25f;
//				return true;
//			}
//
//			@Override
//			public boolean keyTyped(char character) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean touchDown(int screenX, int screenY, int pointer,
//					int button) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean touchUp(int screenX, int screenY, int pointer,
//					int button) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean touchDragged(int screenX, int screenY, int pointer) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean mouseMoved(int screenX, int screenY) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
		Gdx.input.setInputProcessor(new InputMultiplexer(new Teclado(personaje)));
		//Gdx.input.setInputProcessor(Teclado);
		
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 40);
		
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]  {new Vector2(0,0), new Vector2(640,0)});
		
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor =false;
		Body ground= mundo.createBody(bodyDef);
		ground.createFixture(fixtureDef);
		//System.out.println("LA MASA DEL JUGADOR ES: "+playerBody.getMass());
		//playerBody.setTransform(personaje.getPosicion(), 0);
		//System.out.println("EL ORIGGEN DEL BODY ES: X,Y"+playerBody.getPosition().x+", "+playerBody.getPosition().y);
		System.out.println("LA MASA DEL JUGADOR AHORA ES: "+personaje.getCuerpo().getMass());
		//personaje.getCuerpo().setFixedRotation(true);
	//System.out.println("LA MASA DEL JUGADOR AHORA ES: "+playerBody.getMass());
	
	camera.position.set(personaje.getCuerpo().getPosition().x, personaje.getCuerpo().getPosition().y, 0);
//	camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0)
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
	}

}
