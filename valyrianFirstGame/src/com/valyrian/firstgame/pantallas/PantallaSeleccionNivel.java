package com.valyrian.firstgame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.valyrian.firstgame.PrimerJuego;

public class PantallaSeleccionNivel implements Screen{

	private Stage escena;
	private Table tablaNiveles;
	private Table tablaControles;
	private TextButton botonJugar;
	private TextButton botonRegresar;
	private TextButton niveles[];
	private Skin skin;
	private SpriteBatch batch;
	private Texture textureFondo;
	private Texture textureTitulo;
	private Texture textureSeleccionar;
	private Texture textureCaptura1;
	private Image fondo;
	private Image tituloNiveles;
	private Image subSeleccionar;
	private Image captura;
	private TextField texto;
	private Window zonaTexto;
	private PrimerJuego juego;
	
	public PantallaSeleccionNivel(PrimerJuego primerJuego) {
		juego = primerJuego;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    	
        batch.begin();
        
        fondo.draw(batch, 1f);
        escena.act(delta);
		escena.draw();
		
        batch.end();
	
		Table.drawDebug(escena);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
escena.setViewport(width , height, true);
		
		tablaNiveles.setBounds(width*0.05f, 85, width*0.30f, height*0.3f);
		tablaNiveles.setSize(width*0.30f,height*0.45f);
		tablaNiveles.invalidateHierarchy();
		
		tablaControles.setBounds(width*0.55f , 15, width , height*0.30f);
		tablaControles.setSize(width*0.4f, (int)(height*0.3/3));
		tablaControles.invalidateHierarchy();
		
		fondo.setSize(width, height);
		
		tituloNiveles.setBounds(width*0.05f, height*0.75f, width, height);
		tituloNiveles.setSize(width*0.5f, height*0.2f);
		
		subSeleccionar.setBounds(width*0.18f, height*0.70f, width, height);
		subSeleccionar.setSize(width*0.4f, height*0.10f);
		
		captura.setBounds(width*0.65f, height*0.65f, width, height);
		captura.setSize(width*0.3f, height*0.3f);
		
		zonaTexto.setBounds(width*0.4f, height*0.15f, width, height);
		zonaTexto.setSize(width*0.55f, height*0.45f);
		
		texto.setSize(width*0.55f, height*0.45f);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		FileHandle skinFile = Gdx.files.internal("ui/skin/uiskin.json"); 
        skin = new Skin(skinFile);
        batch = new SpriteBatch();
        textureFondo = new Texture(Gdx.files.internal("images/menus/niveles_BG.jpg"));
        fondo = new Image(textureFondo);
        textureTitulo = new Texture(Gdx.files.internal("images/menus/titulo_niveles.png"));
        tituloNiveles = new Image(textureTitulo);
        textureSeleccionar = new Texture(Gdx.files.internal("images/menus/titulo_seleccionarnivel.png"));
        subSeleccionar = new Image(textureSeleccionar);
        
        textureCaptura1 = new Texture(Gdx.files.internal("images/menus/preview_nivel1.png"));
        captura = new Image(textureCaptura1);
		escena = new Stage();
		tablaNiveles = new Table(skin);
		tablaControles = new Table(skin);
		texto = new TextField(Gdx.files.internal("ui/texto/nivel1.txt").readString(), skin);
		zonaTexto = new Window("Resumen: ", skin);
		
		Gdx.input.setInputProcessor(escena);
		
		
		Color color;
		color = new Color(99, 145, 0, 0.4f);
		
		
		niveles = new TextButton[5];
		for (int i = 0; i < 5 ; i++) {
			niveles[i] = new TextButton("Nivel " + Integer.toString(i+1) , skin);
			niveles[i].setColor(color);
			tablaNiveles.add(niveles[i]).fill().expand().space(10f);
			tablaNiveles.row();
		}
		botonJugar = new TextButton("Jugar", skin);
		botonRegresar = new TextButton("Regresar", skin);
		
		botonRegresar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
			
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				botonRegresar.setText("Vas a salirte pendejo");	
            }
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				botonRegresar.setText("Salir");
            }
				
		});
		
		botonJugar.addListener(new ClickListener(){
			
			public void changed (ChangeEvent event, Actor actor) {
				
                System.out.println("Clicked! Is checked: " + botonJugar.isChecked());
                botonJugar.setText("Good job!");
			}
				
			
		});
		
 
		botonJugar.setColor(color);
		botonRegresar.setColor(color);
		tablaControles.add(botonRegresar).fill().expand().space(10f);
		tablaControles.add(botonJugar).fill().expand().space(10f);
		
		zonaTexto.setColor(color);
		zonaTexto.add(Gdx.files.internal("ui/texto/nivel1.txt").readString());
		
		//tablaNiveles.debug();
		//tablaControles.debug();
		
		//zonaTexto.add(texto).fill().expandY();
		//texto.setFillParent(true);
		
		escena.addActor(fondo);
		escena.addActor(tablaNiveles);
		escena.addActor(tablaControles);
		escena.addActor(tituloNiveles);
		escena.addActor(subSeleccionar);
		escena.addActor(captura);
		escena.addActor(zonaTexto);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		escena.dispose();
		skin.dispose();
		batch.dispose();
		textureCaptura1.dispose();
		textureFondo.dispose();
		textureSeleccionar.dispose();
		textureTitulo.dispose();
	}
	

}
