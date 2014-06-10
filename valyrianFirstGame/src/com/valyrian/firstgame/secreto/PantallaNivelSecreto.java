package com.valyrian.firstgame.secreto;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionUnica;


public class PantallaNivelSecreto implements Screen {
	
	private Quetzal juego;
	
	public PantallaNivelSecreto(Quetzal game){
		this.juego = game;
	}
	
	
	World world;
	Box2DDebugRenderer dbr;
	OrthographicCamera camera, hudCam;
	Hud2 hud;
	DContactListener dContactListener;
	
	Body bodyGround;
	Body leftWallB;
	Body rightWallB;
	SpriteBatch batch;
	
	Bucket bucket;
	Array<Product> rainDrop;
	long lastDropTime;
	long score = 0;
	AnimacionUnica animGota;

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(1/60f, 6, 2);
		dbr.render(world, camera.combined);
		
		if(TimeUtils.nanoTime() - lastDropTime > 800000000) 
			spawnRaindrop();
		if(!world.isLocked()){
			Array<Body> toRemove = dContactListener.getListToRemove();
			Iterator<Body> it = toRemove.iterator();
			while(it.hasNext())
			{
				Body b = it.next();
				rainDrop.removeValue((Product) b.getUserData(), false);
				world.destroyBody(b);
			}
		}
		
		dContactListener.getListToRemove().clear();

		batch.begin();
		
		bucket.render(delta, batch);
		
		Iterator<Product> it = rainDrop.iterator();
		while(it.hasNext())
		{
			Product b = it.next();
			b.render(delta, batch);
		}
		
		batch.end();
		
		batch.setProjectionMatrix(hudCam.combined);
		hud.render(batch);
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		
		batch = Quetzal.getSpriteBatch();
		world = new World(new Vector2(0, -1f), true); 
		dbr = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth()/PIXELSTOMETERS,Gdx.graphics.getHeight()/PIXELSTOMETERS);
//		camera.viewportHeight = Gdx.graphics.getHeight()/GameVariables.scale;
//		camera.viewportWidth = Gdx.graphics.getWidth()/GameVariables.scale;
//		camera.update();
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false);
		
		
		//Creating the Bucket 
		System.out.println("OJO 1");
		AnimacionUnica ae = new AnimacionUnica(Quetzal.getManejaRecursos().get("secreto/balde.png", Texture.class),64,64,0);
		System.out.println("OJO 1.5");
//		bucket = new Bucket(64, 64, new Vector2((Gdx.graphics.getWidth()/2), 64) , new Vector2(0, 0), world, ae);
		bucket = new Bucket(64, 64, new Vector2(0,0), Gdx.graphics.getWidth()/2, 64, world, ae);
		System.out.println("OJO 2");
		dContactListener = new DContactListener(bucket);
		world.setContactListener(dContactListener);
		hud = new Hud2(bucket);
		
		
		animGota = new AnimacionUnica(Quetzal.getManejaRecursos().get("secreto/gota.png",Texture.class),64,64,0);
		//Creating the ground
		createGround();
		
		//Creating walls
		createWalls();
		
		//Creating Droplets (Only First)
		rainDrop = new Array<Product>();
		spawnRaindrop();
		
		Gdx.input.setInputProcessor(new Teclado2(bucket.getCuerpo(), camera));
		System.out.println("OJO 3");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		bucket.dispose();
		Iterator<Product> it = rainDrop.iterator();
		while(it.hasNext())
		{
			Product b = it.next();
			b.dispose();
		}
	}

	private void spawnRaindrop() {
		
		Product p = new Product(64, 64, new Vector2(), MathUtils.random(0, 800-64), 600, world, animGota);
//		Product p = new Product(64, 64, new Vector2(MathUtils.random(0, 800-64), 600), null, world, animGota);
		rainDrop.add(p);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	
	private void createGround(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set((Gdx.graphics.getWidth()/2)/PIXELSTOMETERS, 10/PIXELSTOMETERS);
		bodyGround = world.createBody(bodyDef);
		bodyGround.setUserData("Ground");
		
		PolygonShape shape = new PolygonShape();	
		shape.setAsBox((Gdx.graphics.getWidth()/2)/PIXELSTOMETERS, 5/PIXELSTOMETERS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = false;
//		fixtureDef.filter.categoryBits = 4;
//		fixtureDef.filter.maskBits = 2;
		bodyGround.createFixture(fixtureDef).setUserData("Ground");
		shape.dispose();
	}
	
	private void createWalls(){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(3/PIXELSTOMETERS, 11/PIXELSTOMETERS);
		leftWallB = world.createBody(bodyDef);
		leftWallB.setUserData("Ground");
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1/PIXELSTOMETERS, 10/PIXELSTOMETERS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = false;
//		fixtureDef.filter.categoryBits = 4;
//		fixtureDef.filter.maskBits = 2;
		leftWallB.createFixture(fixtureDef).setUserData("Ground");
		
		//Right wall
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set((Gdx.graphics.getWidth() - 3)/PIXELSTOMETERS, 11/PIXELSTOMETERS);
		rightWallB = world.createBody(bodyDef);
		rightWallB.setUserData("Ground");
		
		shape.setAsBox(1/PIXELSTOMETERS, 10/PIXELSTOMETERS);

		fixtureDef.shape = shape;
		fixtureDef.isSensor = false;
//		fixtureDef.filter.categoryBits = 4;
//		fixtureDef.filter.maskBits = 2;
		rightWallB.createFixture(fixtureDef).setUserData("Ground");
		
		shape.dispose();
	}
	
	public void setScore(){
		 score++;
	}

	public long getScore(){
		return score;
	}
}
