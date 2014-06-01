package com.valyrian.firstgame.utilidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.valyrian.firstgame.Quetzal;

public class BarraCarga extends Actor {

    //No usaré el loader para cargar las imagenes de cargado.
    private Texture barra,llenado;
    private Quetzal juego;
    
    public BarraCarga(Quetzal game){
        this.juego = game;
	//Lo coloco en el centro, yo lo e puesto en cualquier lado.
        setPosition(Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2);
	//Instancio imagenes a la antigua.
        barra = new Texture(Gdx.files.internal("images/barra.png"));
        llenado = new Texture(Gdx.files.internal("images/llenado.png"));
    }
    
    //Método de dibujo
    public void draw(SpriteBatch batch, float parentAlpha){
        
        //Actualizo en mi método draw el manager.
        if(juego.manejadorRecursos.update()){
        	if(Gdx.input.isKeyPressed(Keys.SPACE))
        		juego.setScreen(juego.pantallaNivel);
        }
        //Dibujo la barra, mas o menos centrada en el contenedor, y la "Estiro" en X, como el valor
        //del progress va de 0 a 1, la multiplico por 400 así irá de 0 a 400.
        batch.draw(llenado, getX()+50,getY()+25,juego.manejadorRecursos.getProgress()*400,64);
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
