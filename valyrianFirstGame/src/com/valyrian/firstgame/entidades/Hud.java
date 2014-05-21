package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Hud {

	private Jugador jugador;
	private TextureRegion[] font;
	private Texture tex, texHeart, score;
	
	public Hud(Jugador j){
		jugador = j;
		tex = new Texture("images/hud.png");
		texHeart = new Texture("images/heart.png");
		score = new Texture("images/calendario_maya.png");
		
		//cargar los valores de cada numero
		font = new TextureRegion[11];
		for(int i = 0; i < 6; i++) {
			font[i] = new TextureRegion(tex, 32 + i * 9, 16, 9, 9);
		}

		for(int i = 0; i < 5; i++) {
			font[i + 6] = new TextureRegion(tex, 32 + i * 9, 25, 9, 9);
		}
		
	
	}
	
	public void render(SpriteBatch sb){
		sb.begin();
		
		
		drawString(sb, String.valueOf(jugador.getVidaActual()), 16, 16);
		sb.draw(texHeart, 16, 16, 16, 16);
		sb.draw(score, 96, 16, 16, 16);
		drawString(sb, String.valueOf(jugador.getVidaActual()), 96, 16);
		sb.end();
	}
	
	public void dispose(){
		
		tex.dispose();
		texHeart.dispose();
		score.dispose();
	}
	
	private void drawString(SpriteBatch sb, String s, float x, float y) {
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '/') c = 10;
			else if(c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(font[c], 16 + x + i * 16, y, 16, 16);
		}
	}
	

	
}
