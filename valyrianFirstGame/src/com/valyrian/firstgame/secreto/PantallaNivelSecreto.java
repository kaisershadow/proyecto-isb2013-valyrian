package com.valyrian.firstgame.secreto;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionUnica;
import com.valyrian.firstgame.utilidades.input.Xbox360Pad;


public class PantallaNivelSecreto implements Screen {
	
	private Quetzal juego;
	World world;
	Box2DDebugRenderer dbr;
	OrthographicCamera camera, hudCam;
	HudVenezolano hud;
	ColisionesVenezolano dContactListener;
	Music musica;
	
	Body bodyGround;
	Body leftWallB;
	Body rightWallB;
	SpriteBatch batch;
	
	Venezolano venezolano;
	Array<Producto> rainDrop;
	ArrayList<ItemListaDeCompra> listaDeCompra;
	Vector2 listaCompra;
	
	long lastDropTime;
	long score = 0;
	AnimacionUnica animGota;
	
	private ControllerAdapter joystick;
	
	public PantallaNivelSecreto(Quetzal game){
		this.juego = game;
	}
	

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(1/60f, 6, 2);
		dbr.render(world, camera.combined);
		
		if(TimeUtils.nanoTime() - lastDropTime > 800000000) 
			spawnRaindrop();
		if(!world.isLocked()){
			
			//Chequear el carrito
			Array<Body> collected = dContactListener.getCollected();
			Iterator<Body> itCollected = collected.iterator();
			while(itCollected.hasNext()){
				Body b = itCollected.next();
				Producto p = (Producto)b.getUserData();
				//Chequear el elemento que se colecto y descontarlo de la lista
				Iterator<ItemListaDeCompra> itListaCompra = listaDeCompra.iterator();
				while(itListaCompra.hasNext()){
					ItemListaDeCompra item = itListaCompra.next();
					if(item.getCodigo().equals(p.getCodigo())){
						venezolano.setSueldo(venezolano.getSueldo()-p.getPrecio());
						if(item.getCantidad() > 0){
							item.setCantidad(item.getCantidad() - 1);
						}
						hud.actualizar();
					}
				}
			}
			
			//Remover cuerpos
			Array<Body> toRemove = dContactListener.getListToRemove();
			Iterator<Body> itToRemove = toRemove.iterator();
			while(itToRemove.hasNext())
			{
				Body cuerpoABorrar = itToRemove.next();
				rainDrop.removeValue((Producto) cuerpoABorrar.getUserData(), false);
				world.destroyBody(cuerpoABorrar);
			}
		}
		
		dContactListener.getCollected().clear();
		dContactListener.getListToRemove().clear();

		batch.begin();
		
		batch.draw(Quetzal.getManejaRecursos().get("secreto/fondo.jpg", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		venezolano.render(delta, batch);
		
		Iterator<Producto> it = rainDrop.iterator();
		while(it.hasNext())
		{
			Producto b = it.next();
			b.render(delta, batch);
		}
		
		batch.end();
		
		batch.setProjectionMatrix(hudCam.combined);
		hud.render(batch);
		
		if(chequearFin() || isBancaRota()){
			((PantallaFinSecreto)juego.pantallaFinSecreto).setAprobado(!isBancaRota());
			juego.setScreen(juego.pantallaFinSecreto);
		}	
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {

		Quetzal.getManejaRecursos().load("secreto/aceite.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/desodorante.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/mantequilla.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/harina.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/mayonesa.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/leche.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/papel.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/fascista.png", Texture.class);
		Quetzal.getManejaRecursos().load("secreto/fondo.jpg", Texture.class);
		Quetzal.getManejaRecursos().finishLoading();
		
		
		batch = Quetzal.getSpriteBatch();
		world = new World(new Vector2(0, -1f), true);
		dbr = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth()/PIXELSTOMETERS,Gdx.graphics.getHeight()/PIXELSTOMETERS);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false);
		
		musica = Quetzal.getManejaRecursos().get("audio/patria.mp3", Music.class); 
		musica.setVolume(VOLUMEN);
		musica.play();
		//Creando el personaje
		AnimacionUnica ae = new AnimacionUnica(Quetzal.getManejaRecursos().get("secreto/fascista.png", Texture.class),32,64,1/6f);
		venezolano = new Venezolano(64, 64, new Vector2(0,0), Gdx.graphics.getWidth()/2, 64, world, ae);
	
		//Asignar sueldo (minimo)
		venezolano.setSueldo( 4251);
		dContactListener = new ColisionesVenezolano(venezolano);
		world.setContactListener(dContactListener);
	
		
		
		//Creating the ground
		createGround();
		
		//Creating walls
		createWalls();
		
		//Generar el listado de compra
		listaDeCompra = new ArrayList<ItemListaDeCompra>();
		generar_lista_compra();
		
		//Creating Droplets (Only First)
		rainDrop = new Array<Producto>();
		spawnRaindrop();
		
		ArrayList<Image> listaImagenes = new ArrayList<Image>();
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/mayonesa.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/harina.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/leche.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/papel.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/aceite.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/mantequilla.png", Texture.class)));
		listaImagenes.add(new Image(Quetzal.getManejaRecursos().get("secreto/desodorante.png", Texture.class)));
		
		hud = new HudVenezolano(venezolano,listaImagenes,listaDeCompra);
		Gdx.input.setInputProcessor(new TecladoVenezolano(venezolano, camera));
		
		agregarJoystick();
		
	}


	private void agregarJoystick() {
		joystick = new ControllerAdapter(){
			@Override
			public boolean buttonDown (Controller controller, int buttonIndex) {
				switch (buttonIndex) {
				case Xbox360Pad.BUTTON_BACK:
					Gdx.input.getInputProcessor().keyUp(Keys.ESCAPE);
					break;
				}
				return true;
			}
			
			@Override
			public boolean axisMoved (Controller controller, int axisIndex, float value) {
				switch(axisIndex){
				case Xbox360Pad.AXIS_LEFT_X:
					if(value>0.8f){
						Gdx.input.getInputProcessor().keyDown(Keys.D);
					}else if(value<-0.8f){
						Gdx.input.getInputProcessor().keyDown(Keys.A);
					}else
						Gdx.input.getInputProcessor().keyUp(Keys.A);
					break;
				}
				return false;
			}

			@Override
			public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
				switch (value) {
				case west:
					Gdx.input.getInputProcessor().keyDown(Keys.A);
					break;
				case east:
					Gdx.input.getInputProcessor().keyDown(Keys.D);
					break;
				default:
					break;
				}
				return true;
			}
		};
		Controllers.addListener(joystick);	
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
		venezolano.dispose();
		musica.stop();
		Controllers.removeListener(joystick);
		Iterator<Producto> it = rainDrop.iterator();
		while(it.hasNext())
		{
			Producto b = it.next();
			b.dispose();
		}
		world.dispose();
	}

	private void spawnRaindrop() {
		int opc;
		Producto p;
		opc = MathUtils.random(1, 7);
		p = ProductFactory.createProduct(64, 64, new Vector2(), MathUtils.random(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getWidth()-32), Gdx.graphics.getHeight(), world, opc);
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
		leftWallB.createFixture(fixtureDef).setUserData("Ground");
		
		//Right wall
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set((Gdx.graphics.getWidth() - 3)/PIXELSTOMETERS, 11/PIXELSTOMETERS);
		rightWallB = world.createBody(bodyDef);
		rightWallB.setUserData("Ground");
		
		shape.setAsBox(1/PIXELSTOMETERS, 10/PIXELSTOMETERS);

		fixtureDef.shape = shape;
		fixtureDef.isSensor = false;
		rightWallB.createFixture(fixtureDef).setUserData("Ground");
		
		shape.dispose();
	}
	
	public void setScore(){
		 score++;
	}

	public long getScore(){
		return score;
	}
	
	private void generar_lista_compra() {

		for(int i = 0; i < 7; i++){
			int opc;
			opc = MathUtils.random(4, 7);
			ItemListaDeCompra item = new ItemListaDeCompra();
			if(i == 0){
				item.setCodigo(CodigoProducto.MAYONESA);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 1){
				item.setCodigo(CodigoProducto.HARINA);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 2){
				item.setCodigo(CodigoProducto.LECHE);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 3){
				item.setCodigo(CodigoProducto.PAPEL);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 4){
				item.setCodigo(CodigoProducto.ACEITE);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 5){
				item.setCodigo(CodigoProducto.MANTEQUILLA);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
			if(i == 6){
				item.setCodigo(CodigoProducto.DESHODORANTE);
				item.setCantidad(opc);
				listaDeCompra.add(item);
			}
		}
	}
	
	private Boolean chequearFin() {
		Iterator<ItemListaDeCompra> itListaCompra = listaDeCompra.iterator();
		while(itListaCompra.hasNext()){
			ItemListaDeCompra item = itListaCompra.next();
			if(item.getCantidad() > 0)
				return false;
		}
		return true;
		
	}
	
	private boolean isBancaRota() {
		if(venezolano.getSueldo() <= 0)
			return true;
		else
			return false;
	}
}