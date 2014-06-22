package com.valyrian.firstgame.secreto;



import static com.valyrian.firstgame.utilidades.GameVariables.debug;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.valyrian.firstgame.Quetzal;


public class HudVenezolano {

	private Venezolano jugador;
	private Texture tex, texHeart, score;
	private Stage escena;
	private Table tablaCantidades;
	private Table tablaSueldo;
	private Skin skin;
	private ArrayList<Image> listaCompraImagenes;
	private ArrayList<ItemListaDeCompra> cantidades;
	
	public HudVenezolano(Venezolano j, ArrayList<Image> imagenesListaCompra, ArrayList<ItemListaDeCompra> cantidades){
		jugador = j;
		tex = Quetzal.getManejaRecursos().get("images/hud.png",Texture.class);
		texHeart = Quetzal.getManejaRecursos().get("images/corazon.png",Texture.class);
		score = Quetzal.getManejaRecursos().get("images/calendario_maya.png",Texture.class);
		
		skin = Quetzal.getManejaRecursos().get("ui/skin/uiskin.json");
		escena = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		tablaCantidades = new Table(skin);
		tablaCantidades.setBounds(0, Gdx.graphics.getHeight()*0.5f, Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.5f);
		this.listaCompraImagenes = imagenesListaCompra;
		this.setCantidades(cantidades);
		
		
		tablaSueldo = new Table(skin);
		tablaSueldo.setBounds(Gdx.graphics.getWidth() -  Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.1f);
		
		actualizar();
		
		escena.addActor(tablaSueldo);
		escena.addActor(tablaCantidades);
		
		if(debug)
			tablaCantidades.debug();
		Gdx.input.setInputProcessor(escena);
		
	
	}
	
	public void render(SpriteBatch sb){
		
		
		escena.act();
		escena.draw();
		

		if(debug)
			Table.drawDebug(escena);
		sb.begin();
		sb.end();
	}
	
	public void dispose(){
		
		tex.dispose();
		texHeart.dispose();
		score.dispose();
	}
	
	
	public ArrayList<Image> getListaCompra() {
		return listaCompraImagenes;
	}

	public void setListaCompra(ArrayList<Image> listaCompra) {
		this.listaCompraImagenes = listaCompra;
	}

	public ArrayList<ItemListaDeCompra> getCantidades() {
		return cantidades;
	}

	public void setCantidades(ArrayList<ItemListaDeCompra> cantidades) {
		this.cantidades = cantidades;
	}
	
	public void actualizar(){
		tablaCantidades.clearChildren();
		tablaCantidades.add("Lista de compra").fill().expandX().spaceBottom(10f);
		tablaCantidades.row();
		for (int i = 0; i < listaCompraImagenes.size(); i++) {
			tablaCantidades.add(listaCompraImagenes.get(i)).spaceBottom(10f);
			tablaCantidades.add(String.valueOf(cantidades.get(i).getCantidad())).left().spaceBottom(10f);
			tablaCantidades.row();
		}
		
		tablaSueldo.clearChildren();
		tablaSueldo.add("Sueldo").fill().expandX().spaceBottom(10f);
		tablaSueldo.row();
		tablaSueldo.add(String.valueOf(jugador.getSueldo())).left();
	}
	

	
}
