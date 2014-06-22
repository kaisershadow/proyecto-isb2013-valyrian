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
	
    public Texture barra,llenado;
    private BitmapFont font;
    private float fWidth1,fWidth2;
    private String textoCargado1,textoCargado2;
    
    public BarraCarga(){
        barra = new Texture(Gdx.files.internal("images/barra.png"));
        llenado = new Texture(Gdx.files.internal("images/llenado.png"));
        font = new BitmapFont();
		font.setColor(Color.WHITE);
		textoCargado1 = "PRESIONE ENTER (START) PARA CONTINUAR";
		textoCargado2 = "PRESIONE CONTROL IZQ (LB) o CONTROL DER (RB) PARA VOLVERSE FASCISTA";
		fWidth1 = font.getBounds(textoCargado1).width;
		fWidth2 = font.getBounds(textoCargado2).width;
    }
    
    @Override
	public void draw (Batch batch, float parentAlpha) {
        if(Quetzal.getManejaRecursos().update()){
        	font.draw(batch, textoCargado1, (0.5f*Gdx.graphics.getWidth())-fWidth1/2f, 0.1f*Gdx.graphics.getHeight());
        	font.draw(batch, textoCargado2, (0.5f*Gdx.graphics.getWidth())-fWidth2/2f, 0.06f*Gdx.graphics.getHeight());
        }
        batch.draw(llenado, getX()+50,getY(),Quetzal.getManejaRecursos().getProgress()*406,128);
        batch.draw(barra, getX(),getY());
    }
    
    public void dispose(){
    	barra.dispose();
    	llenado.dispose();
    	if(debug)
    		System.out.println("SE LLAMO AL DISPOSE DE BARRA CARGA");
    }
}