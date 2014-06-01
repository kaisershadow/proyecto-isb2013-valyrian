package com.valyrian.firstgame.secreto;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.valyrian.firstgame.Quetzal;


public class Hud2 {

	private Bucket jugador;
	private TextureRegion[] font;
	private Texture tex, texHeart, score;
	
	public Hud2(Bucket j){
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
		
		
		drawString(sb, String.valueOf(jugador.getPuntuacion()), 16, 16);
		drawString(sb, "100", 16, 16);
		sb.draw(texHeart, 16, 16, 16, 16);
		sb.draw(score, 96, 16, 16, 16);
		drawString(sb, String.valueOf(jugador.getPuntuacion()), 96, 16);
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
