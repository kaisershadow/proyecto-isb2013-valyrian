package com.valyrian.firstgame.secreto;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.valyrian.firstgame.entidades.EntidadDibujable;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;


public class Producto extends EntidadDibujable{
	
	private int precio;
	private CodigoProducto codigo;

	public Producto(float ancho, float alto, Vector2 vel,float posX,float posY, World m,ManejadorAnimacion ma) {
		super(ancho, alto,vel, posX,posY, m, ma);
	}

	@Override
	protected void crearCuerpo(World mundo, float ancho, float alto,float posX,float posY) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(posX/PIXELSTOMETERS, posY/PIXELSTOMETERS);
		cuerpo = mundo.createBody(bodyDef);
		cuerpo.setUserData(this);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((ancho/2)/PIXELSTOMETERS, (alto/2)/PIXELSTOMETERS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.isSensor = false;
		
		cuerpo.createFixture(fixtureDef).setUserData("Product");
		shape.dispose();
		
	}

	@Override
	public void render(float deltaTime, SpriteBatch batch) {
		TextureRegion frame = manAnim.getAnimacion(deltaTime);
		batch.draw(frame, cuerpo.getPosition().x*PIXELSTOMETERS-this.ancho/2, cuerpo.getPosition().y*PIXELSTOMETERS-this.alto/2, this.ancho, this.alto);
	}
	
	public void dispose(){
	//	textura.dispose();
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public CodigoProducto getCodigo() {
		return codigo;
	}

	public void setCodigo(CodigoProducto codigo) {
		this.codigo = codigo;
	}

}