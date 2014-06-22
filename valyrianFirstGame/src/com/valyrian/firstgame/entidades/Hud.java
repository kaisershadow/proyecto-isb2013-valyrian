package com.valyrian.firstgame.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.Quetzal;

import static com.valyrian.firstgame.utilidades.GameVariables.*;


public class Hud {

	private Jugador jugador;
	private TextureRegion[] font;
	private Texture tex, texHeart, score;
	
	public Hud(Jugador j){
		jugador = j;
		tex = Quetzal.getManejaRecursos().get("images/hud.png",Texture.class);
		texHeart = Quetzal.getManejaRecursos().get("images/corazon.png",Texture.class);
		score = Quetzal.getManejaRecursos().get("images/calendario_maya.png",Texture.class);
		
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
		drawString(sb, String.valueOf(jugador.getVidaActual())+"/"+String.valueOf(jugador.getMaxVida()), 32, V_HEIGHT-64);
		sb.draw(texHeart, 16, V_HEIGHT-64, 32, 32);
		sb.draw(score, 16, V_HEIGHT-128, 32, 32);
		drawString(sb, String.valueOf(jugador.getPuntaje()), 32, V_HEIGHT-128);
		sb.end();
	}
	
	public void dispose(){
		score.dispose();
		if(debug){
			System.out.println("SE LLAMO AL DISPOSE DE HUD");
		}
	}
	
	private void drawString(SpriteBatch sb, String s, float x, float y) {
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '/') c = 10;
			else if(c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(font[c], 32 + x + i * 32, y, 32, 32);
		}
	}	
}