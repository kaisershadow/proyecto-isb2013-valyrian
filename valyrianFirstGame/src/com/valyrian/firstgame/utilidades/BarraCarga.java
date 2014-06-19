package com.valyrian.firstgame.utilidades;

import static com.valyrian.firstgame.utilidades.GameVariables.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.valyrian.firstgame.Quetzal;

public class BarraCarga extends Actor {

    //No usaré el loader para cargar las imagenes de cargado.
    public Texture barra,llenado;
    private BitmapFont font;
    private float fWidth;
    private String textoCargado;
    public BarraCarga(){
        //Instancio imagenes a la antigua.
        barra = new Texture(Gdx.files.internal("images/barra.png"));
        llenado = new Texture(Gdx.files.internal("images/llenado.png"));
        
        //Lo coloco en el centro, yo lo e puesto en cualquier lado.
//        this.setBounds(0.5f*V_WIDTH-barra.getWidth()/2,0.2f*V_HEIGHT-barra.getHeight()/2, V_WIDTH*0.8f, 128);
        font = new BitmapFont();
		font.setColor(Color.WHITE);
		textoCargado = "PRESIONE ESPACIO PARA CONTINUAR O  i PARA EL NIVEL SECRETO";
		fWidth = font.getBounds(textoCargado).width;
    }
    
    //Método de dibujo
    @Override
	public void draw (Batch batch, float parentAlpha) {

        //Actualizo en mi método draw el manager.
        if(Quetzal.getManejaRecursos().update())
        	font.draw(batch, "PRESIONE ESPACIO PARA CONTINUAR O  i PARA EL NIVEL SECRETO", (0.5f*Gdx.graphics.getWidth())-fWidth/2f, 0.1f*Gdx.graphics.getHeight());
//        	font.drawWrapped(batch, "PRESIONE ESPACIO PARA CONTINUAR O  i PARA EL NIVEL SECRETO", (0.2f*V_WIDTH), 0.1f*V_HEIGHT,0.6f*V_WIDTH);
        
        //Dibujo la barra, mas o menos centrada en el contenedor, y la "Estiro" en X, como el valor
        //del progress va de 0 a 1, la multiplico por 400 así irá de 0 a 400.
        batch.draw(llenado, getX()+50,getY(),Quetzal.getManejaRecursos().getProgress()*406,128);
        //Dibujo Contenedor.
        batch.draw(barra, getX(),getY());
        
    }
    
    public void dispose(){
    	barra.dispose();
    	llenado.dispose();
    	if(debug)
    		System.out.println("SE LLAMO AL DISPOSE DE BARRA CARGA");
    }
}