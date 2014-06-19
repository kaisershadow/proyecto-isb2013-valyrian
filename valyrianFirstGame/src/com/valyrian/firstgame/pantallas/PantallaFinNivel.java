package com.valyrian.firstgame.pantallas;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.valyrian.firstgame.Quetzal;

public class PantallaFinNivel implements Screen{
	
	
	private Stage escena;
	private Skin skin;
	private Quetzal juego;
	private SpriteBatch batch;
	private Texture textureFondo, textureAprobado, texturaNoAprobado;
	private Image fondo, imagenAprobado, imagenNoAprobado;
	private Table tabla1, tabla2;
	private int puntuacion;
	private Boolean aprobado;
	private int posicion;
	
	public PantallaFinNivel(Quetzal primerJuego) {
		juego = primerJuego;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		escena.act();
		escena.draw();
		batch.begin();
		batch.end();
		if(debug){
			Table.drawDebug(escena);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		escena.setViewport(width, height);
		
		fondo.setSize(width, height);
		
		tabla1.setBounds(0, height*0.05f, width, height);
		tabla1.setSize(width, height*0.3f);
		
		tabla2.setBounds(0, height*0.18f, width, height);
		tabla2.setSize(width, height*0.3f);
		
		imagenAprobado.setBounds(width*0.2f, height*0.8f, width, height);
		imagenAprobado.setSize(width*0.6f, height*0.1f);
		
		imagenNoAprobado.setBounds(width*0.2f, height*0.8f, width, height);
		imagenNoAprobado.setSize(width*0.6f, height*0.1f);
	}

	@Override
	public void show() {
		inputs();
		
		batch=Quetzal.getSpriteBatch();
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		escena = new Stage();
		textureFondo = Quetzal.getManejaRecursos().get("images/menus/carga_BG.jpg",Texture.class);
		fondo = new Image(textureFondo);

//		Quetzal.getManejaRecursos().load("images/juego_completo.png", Texture.class);
//		Quetzal.getManejaRecursos().load("images/juego_acabado.png", Texture.class);
//		Quetzal.getManejaRecursos().finishLoading();
		
		textureAprobado = Quetzal.getManejaRecursos().get("images/juego_completo.png",Texture.class);
		texturaNoAprobado = Quetzal.getManejaRecursos().get("images/juego_acabado.png",Texture.class);
	
		imagenAprobado = new Image(textureAprobado);
		imagenNoAprobado = new Image(texturaNoAprobado);
		
		tabla1 = new Table(skin);
		tabla2 = new Table(skin);
		tabla1.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderbg.png"))));
		tabla2.setBackground(tabla1.getBackground());
		
		escena.addActor(fondo);
		escena.addActor(tabla1);
		
		if(aprobado){
			escena.addActor(imagenAprobado);
		}
		else{
			escena.addActor(imagenNoAprobado);
		}
		
		chequearPosicionPuntuacion();
		
		if(posicion <= 3 && posicion > 0){
			tabla2.add("Has obtenido la puntuacion mas alta numero " 
					+ String.valueOf(posicion));
			tabla2.row();
			tabla2.add("Tu logro ha quedado registrado");
			escena.addActor(tabla2);
			guardarPuntuacionAlta(posicion, PLAYER_NAME, String.valueOf(puntuacion));
		}
		
		tabla1.add("PRESIONE R PARA REINICIAR EL NIVEL"); 
		tabla1.row();
		tabla1.add("PRESIONE C PARA SELECCIONAR OTRO NIVEL");
		
		if(debug){
			tabla1.debug();
			tabla2.debug();
		}
	}

	
	private void inputs(){
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {
					if(keycode == Keys.R){
						juego.setScreen(juego.pantallaCargaNivel);
						return true;
					}
					else if(keycode == Keys.C){
						juego.setScreen(juego.pantallaSeleccionNivel);
						return true;
					}
					return true;
			}
		});
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
//		Quetzal.getManejaRecursos().load("images/juego_completo.png", Texture.class);
//		Quetzal.getManejaRecursos().load("images/juego_acabado.png", Texture.class);
		if(debug)
			System.out.println("SE LLAMO AL DISPOSE DE PANTALLA FIN NIVEL");
	}
	
	public void setPuntuacion(int puntuacion){
		this.puntuacion = puntuacion;
	}
	
	public void setAprobado(Boolean aprobado){
		this.aprobado = aprobado;
	}
	
	private void chequearPosicionPuntuacion(){
		posicion = 4;
		String string;
		int index;
		for(int i = 3; i > 0; i--){
			string = Gdx.files.internal("data/puntuacion"+String.valueOf(i) +".txt").readString();
			index = string.indexOf("\n");
			string = string.substring(0, index);
			System.out.println(string);
			if(Integer.valueOf(string) < puntuacion){
				posicion = i;
			}
		}
	}
	
	private void guardarPuntuacionAlta(int posicion, String nombre, String puntuacion){
		FileHandle file = Gdx.files.local("data/puntuacion" + String.valueOf(posicion)+ ".txt");
		file.writeString(puntuacion + "\n" + nombre, false);
	}

}
