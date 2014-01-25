package com.valyrian.firstgame.pantallas.pruebas;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import com.valyrian.firstgame.entidades.JugadorNativo;
import com.valyrian.firstgame.entidades.JugadorNativo.ESTADO_ACTUAL;

public class PantallaPruebaPersonaje implements Screen {
	
	private static final float TIMESTEP = 1/60f;
	private static final int VELOCITYITERATIONS = 8;
	private static final int POSITIONITERATIONS = 3;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	//private Array<Body> tmpBodies = new Array<Body>();
	
	private SpriteBatch batch;
	private JugadorNativo personaje;
	private World mundo;
	private Body playerBody;
	private OrthogonalTiledMapRenderer otmr;
	private Vector2 movement = new Vector2();
	private float speed=500000;
	private Box2DMapObjectParser manejocol;
	private Box2DSprite imageneEnObjetosTile;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 
		 
//		playerBody.getPosition().x=personaje.getPosicion().x;
//		playerBody.getPosition().y=personaje.getPosicion().y;
//		
		mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		//camera.position.set(((jugadorNativo)playerBody.getUserData()).getPosicion().x, ((jugadorNativo)playerBody.getUserData()).getPosicion().y, 0);
		camera.update();
		//otmr.setView(camera.combined,camera.position.x-Gdx.graphics.getWidth()/2, camera.position.y-Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		otmr.setView(camera);
		otmr.render();
		//System.out.println("ERROR 3");
		batch.setProjectionMatrix(camera.combined);
		//personaje.setPosicion(playerBody.getPosition());
		personaje.getPosicion().x=playerBody.getPosition().x-32;
		personaje.getPosicion().y=playerBody.getPosition().y-32;
		//otmr.setView(camera);
		batch.begin();
		//System.out.println("ERROR 4");
		
		
			//mundo.getBodies(tmpBodies);
			personaje.renderNativo(delta, batch);
//			for(Body body :tmpBodies)
//				if(body.getUserData()!= null){
//				//	System.out.println("LLEGA AQUI?");
//				
//					
//				}
			
		batch.end();
		System.out.println("EL ORIGGEN DEL BODY ES: X,Y"+playerBody.getPosition().x+", "+playerBody.getPosition().y);
		playerBody.applyForceToCenter(movement, true);
		debugRenderer.render(mundo, camera.combined);
		
		
		
		

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight =height;
	}

	@Override
	public void show() {
		
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/platformer_test.tmx"), 1);
		otmr.setView(camera);
		
		batch = new SpriteBatch();
		
		mundo = new World(new Vector2(0,-9.81f), true);
		
		
		//Creacion del mapa con Box2d
		manejocol= new Box2DMapObjectParser(1);
		manejocol.load(mundo, new TmxMapLoader().load("mapas/platformer_test.tmx"));	
		manejocol.setUnitScale(1);
		
		///Definicicion y creacion del cuerpo
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.DynamicBody;
		//manejocol.getBodies().get("spawnPoint").setAwake(false);
		//El personaje se crea en la posicion de spwan.
		
		bodyDef.position.set(manejocol.getBodies().get("spawnPoint").getPosition().x,manejocol.getBodies().get("spawnPoint").getPosition().y);
		
		//Box Shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(32, 32);
		
		//Fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 10;
		
		playerBody= mundo.createBody(bodyDef);
		playerBody.createFixture(fixtureDef);
		//boxSprite = new Sprite(new Texture(Gdx.files.internal("img/luigi_front.png")));
//		boxSprite = new Sprite(new Texture(Gdx.files.internal("frog_idle.gif")));
//		boxSprite.setSize(10, 20);
//		boxSprite.setOrigin(boxSprite.getWidth()/2, boxSprite.getHeight()/2);
		//box.setUserData(ani);
		//playerBody.applyAngularImpulse(50, true);
		boxShape.dispose();
		//playerBody.setUserData(personaje);
		personaje = new JugadorNativo(100, 110, 100, 100, 64, 64);
		
		
		//Gdx.input.setInputProcessor(personaje);
		
		
		
		Gdx.input.setInputProcessor(new InputProcessor(){
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode){
					case Keys.W:
						movement.y = speed;
						break;
					case Keys.A:
						movement.x = -speed;
						personaje.estado =ESTADO_ACTUAL.Caminando;
						break;
					case Keys.S:
						movement.y = -speed;
						
						break;
					case Keys.D:
						movement.x = speed;
						personaje.estado =ESTADO_ACTUAL.Caminando;
						break;
					default:
						return false;
				}
				return true;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				
				switch(keycode){
				case Keys.W:
				case Keys.S:
					movement.y = 0;
					
					break;
				case Keys.A:
				case Keys.D:
					movement.x = 0;
					personaje.estado =ESTADO_ACTUAL.Quieto;
					break;
				default:
					return false;
			}
			return true;
			}
			
			@Override
			public boolean scrolled(int amount) {
				camera.zoom+=amount/25f;
				return true;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mundo.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				//if(manejocol.getBodies().get("colectible").getUserData)
				
			}
		});
		
		
		
		
		
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
		/*ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]  {new Vector2(0,0), new Vector2(640,0)});
		
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		
		Body ground= mundo.createBody(bodyDef);
		ground.createFixture(fixtureDef);*/
		System.out.println("LA MASA DEL JUGADOR ES: "+playerBody.getMass());
		//playerBody.setTransform(personaje.getPosicion(), 0);
		System.out.println("EL ORIGGEN DEL BODY ES: X,Y"+playerBody.getPosition().x+", "+playerBody.getPosition().y);
		System.out.println("LA MASA DEL JUGADOR AHORA ES: "+playerBody.getMass());
	playerBody.setFixedRotation(true);
	System.out.println("LA MASA DEL JUGADOR AHORA ES: "+playerBody.getMass());
	
	
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
	//	personaje.liberarMemoria();
		debugRenderer.dispose();
		otmr.getMap().dispose();
		otmr.dispose();
	}

}
