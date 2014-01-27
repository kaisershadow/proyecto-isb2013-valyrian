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
import com.valyrian.firstgame.entidades.JugadorNativo;
import com.valyrian.firstgame.entidades.JugadorNativo.ESTADO_ACTUAL;

public class PantallaPruebaPersonaje implements Screen {
	
	private static final float TIMESTEP = 1/60f;
	private static final int VELOCITYITERATIONS = 8;
	private static final int POSITIONITERATIONS = 3;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	//private Array<Body> tmpBodies = new Array<Body>();
	
	
	private float contador=0;
	private SpriteBatch batch;
	private JugadorNativo personaje;
	private World mundo;
	private Body playerBody,platformBody;
	private OrthogonalTiledMapRenderer otmr;
	private Vector2 movement = new Vector2();
	private float speed=500000;
<<<<<<< local
<<<<<<< local
	private Vector2 linVel = new Vector2(32, 0);
=======
=======
>>>>>>> other
	private Box2DMapObjectParser manejocol;
	private Box2DSprite imageneEnObjetosTile;
	
>>>>>>> other
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 
				contador+=delta;
				//System.out.println(contador);
				if(contador>=2){
	//		System.out.println(contador);
			contador=0;
			platformBody.setLinearVelocity(platformBody.getLinearVelocity().x*(-1),linVel.y);
			System.out.println("LINVEL ="+platformBody.getLinearVelocity().x +", "+linVel.y);
		}
		 
//		playerBody.getPosition().x=personaje.getPosicion().x;
//		playerBody.getPosition().y=personaje.getPosicion().y;
//		
		mundo.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
	//ALGORITMO 1 PARA POSICION DE LA CAMARA	
//		if(playerBody.getPosition().x-camera.viewportWidth/2>=0 && playerBody.getPosition().x+camera.viewportWidth/2<=otmr.getMap().getProperties().get("width", Integer.class)*otmr.getMap().getProperties().get("tilewidth", Integer.class))
//		camera.position.x=playerBody.getPosition().x;
//		if(playerBody.getPosition().y-camera.viewportHeight/2>=0 && playerBody.getPosition().y+camera.viewportHeight/2<=otmr.getMap().getProperties().get("height", Integer.class)*otmr.getMap().getProperties().get("tileheight", Integer.class))
//			camera.position.y=playerBody.getPosition().y;
//		
		camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);
		if(camera.position.x<camera.viewportWidth/2)
			camera.position.x =camera.viewportWidth/2;
		else if(camera.position.x+camera.viewportWidth/2>otmr.getMap().getProperties().get("width", Integer.class)*otmr.getMap().getProperties().get("tilewidth", Integer.class))
			camera.position.x =-camera.viewportWidth/2+otmr.getMap().getProperties().get("width", Integer.class)*otmr.getMap().getProperties().get("tilewidth", Integer.class);
		
		if(camera.position.y<camera.viewportHeight/2)
			camera.position.y =camera.viewportHeight/2;
		else if(camera.position.y+camera.viewportHeight/2>otmr.getMap().getProperties().get("height", Integer.class)*otmr.getMap().getProperties().get("tileheight", Integer.class))
			camera.position.y =-camera.viewportHeight/2+otmr.getMap().getProperties().get("height", Integer.class)*otmr.getMap().getProperties().get("tileheight", Integer.class);
		
		camera.update();
		//otmr.setView(camera.combined,camera.position.x-Gdx.graphics.getWidth()/2, camera.position.y-Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//otmr.setView(camera);
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
		//System.out.println("EL ORIGGEN DEL BODY ES: X,Y"+playerBody.getPosition().x+", "+playerBody.getPosition().y);
		//playerBody.applyForceToCenter(movement, true);
		playerBody.setLinearVelocity(movement);
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
		System.out.println("CAMARA POS: "+camera.position.x+", "+camera.position.y);
//		camera.position.x =0;
//		camera.position.y=0;
		System.out.println("CAMARA POS: "+camera.position.x+", "+camera.position.y);

		otmr = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("mapas/platformer_test.tmx"),1);
		
		//otmr.setView(camera.combined,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//camera.zoom=0.5f;
		//camera.zoom=2;
		//otmr.setView(camera);
		batch = new SpriteBatch();
		
		mundo = new World(new Vector2(0,-9.81f), true);
		
<<<<<<< local
		mundo = new World(new Vector2(0,-9.81f*32*3600), true);
=======
		
		//Creacion del mapa con Box2d
		manejocol= new Box2DMapObjectParser(1);
		manejocol.load(mundo, new TmxMapLoader().load("mapas/platformer_test.tmx"));	
		manejocol.setUnitScale(1);
<<<<<<< local
>>>>>>> other
=======
>>>>>>> other
		
		///Definicicion y creacion del cuerpo
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
<<<<<<< local
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(250,200);
		bodyDef.angle =0;
=======
		bodyDef.type = BodyType.DynamicBody;
		//manejocol.getBodies().get("spawnPoint").setAwake(false);
		//El personaje se crea en la posicion de spwan.
<<<<<<< local
=======
		
		bodyDef.position.set(manejocol.getBodies().get("spawnPoint").getPosition().x,manejocol.getBodies().get("spawnPoint").getPosition().y);
>>>>>>> other
		
		bodyDef.position.set(manejocol.getBodies().get("spawnPoint").getPosition().x,manejocol.getBodies().get("spawnPoint").getPosition().y);
		
>>>>>>> other
		//Box Shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(64, 16);
		
		//Fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 1;
		fixtureDef.restitution = 0;
		fixtureDef.density = 1;
		fixtureDef.isSensor =false;
		platformBody= mundo.createBody(bodyDef);
		platformBody.createFixture(fixtureDef);
		
		platformBody.setLinearVelocity(linVel);
	//	System.out.println("LINVEL ="+linVel.x +", "+linVel.y);
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(100,100);
		bodyDef.angle =0;
		boxShape.setAsBox(32, 32);
		fixtureDef.isSensor =false;
		fixtureDef.shape=boxShape;
		//fixtureDef.filter.maskBits=0;
		playerBody =mundo.createBody(bodyDef);
		playerBody.createFixture(fixtureDef);

		boxShape.setAsBox(32, 32,new Vector2(64, 32),0);
		fixtureDef.isSensor =true;
		fixtureDef.shape=boxShape;
		playerBody.createFixture(fixtureDef);
		//		Filter filtro = new Filter();
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
		personaje = new JugadorNativo(100, 110, 100, 100, 64, 64);
		
		
		//Gdx.input.setInputProcessor(personaje);
		
<<<<<<< local
	//	Array<Contact> contacto = mundo.getContactList();
=======
		
		
<<<<<<< local
>>>>>>> other
=======
>>>>>>> other
		Gdx.input.setInputProcessor(new InputProcessor(){
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode){
					case Keys.W:
						movement.y = speed;
						personaje.estado=ESTADO_ACTUAL.Saltando;
						break;
					case Keys.A:
						movement.x = -speed;
						personaje.estado =ESTADO_ACTUAL.Corriendo;
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
		fixtureDef.isSensor =false;
		Body ground= mundo.createBody(bodyDef);
		ground.createFixture(fixtureDef);*/
		System.out.println("LA MASA DEL JUGADOR ES: "+playerBody.getMass());
		//playerBody.setTransform(personaje.getPosicion(), 0);
		System.out.println("EL ORIGGEN DEL BODY ES: X,Y"+playerBody.getPosition().x+", "+playerBody.getPosition().y);
		System.out.println("LA MASA DEL JUGADOR AHORA ES: "+playerBody.getMass());
	playerBody.setFixedRotation(true);
	System.out.println("LA MASA DEL JUGADOR AHORA ES: "+playerBody.getMass());
	
	camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);
//	camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
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
