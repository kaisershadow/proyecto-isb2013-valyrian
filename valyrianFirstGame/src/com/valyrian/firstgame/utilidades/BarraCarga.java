package com.valyrian.firstgame.utilidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.valyrian.firstgame.Quetzal;

public class BarraCarga extends Actor {

    //No usar� el loader para cargar las imagenes de cargado.
    private Texture barra,llenado;
    private Quetzal juego;
    private BitmapFont font;
    public BarraCarga(Quetzal game){
        this.juego = game;
        //Instancio imagenes a la antigua.
        barra = new Texture(Gdx.files.internal("images/barra.png"));
        llenado = new Texture(Gdx.files.internal("images/llenado.png"));
        //Lo coloco en el centro, yo lo e puesto en cualquier lado.
        this.setPosition(Gdx.graphics.getWidth()/2-barra.getWidth()/2,0.2f*Gdx.graphics.getHeight());
        font = new BitmapFont();
		font.setColor(Color.WHITE);
//		font.setScale(1f/PIXELSTOMETERS);
    }
    
    //M�todo de dibujo
    public void draw(SpriteBatch batch, float parentAlpha){
        
        //Actualizo en mi m�todo draw el manager.
        if(Quetzal.getManejaRecursos().update()){
        	font.draw(batch, "PRESIONE ESPACIO PARA CONTINUAR O  i PARA EL NIVEL SECRETO", 100, 100);
        	if(Gdx.input.isKeyPressed(Keys.SPACE))
        		juego.setScreen(juego.pantallaNivel);
        	if(Gdx.input.isKeyPressed(Keys.I))
        		juego.setScreen(juego.pantallaSecreto);
        }
        //Dibujo la barra, mas o menos centrada en el contenedor, y la "Estiro" en X, como el valor
        //del progress va de 0 a 1, la multiplico por 400 as� ir� de 0 a 400.
        batch.draw(llenado, getX()+50,getY()+25,Quetzal.getManejaRecursos().getProgress()*400,64);
        //Dibujo Contenedor.
        batch.draw(barra, getX(),getY());
        
    }
    
    public void dispose(){
    	barra.dispose();
    	llenado.dispose();
    	if(debug)
    		System.out.println("SE LLAMO AL DISPOSE DE BARRACARGA");
    }
}
