package com.valyrian.firstgame.secreto;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.valyrian.firstgame.Quetzal;
import com.valyrian.firstgame.animaciones.AnimacionUnica;
import com.valyrian.firstgame.interfaces.ManejadorAnimacion;

public class ProductFactory {
	private static Producto product = null;
	private static ManejadorAnimacion ma = null;
	private static Texture t = null;
	public static Producto createProduct(float ancho, float alto, Vector2 vel, float posX, float posY, World m, CodigoProducto codigo) {
		
		if(codigo == CodigoProducto.HARINA){
			t = Quetzal.getManejaRecursos().get("secreto/harina.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	 
		}
		
		if(codigo == CodigoProducto.LECHE){
			t = Quetzal.getManejaRecursos().get("secreto/leche.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		if(codigo == CodigoProducto.MAYONESA){
			t = Quetzal.getManejaRecursos().get("secreto/mayonesa.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		if(codigo == CodigoProducto.PAPEL){
			t = Quetzal.getManejaRecursos().get("secreto/papel.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		if(codigo == CodigoProducto.ACEITE){
			t = Quetzal.getManejaRecursos().get("secreto/aceite.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		if(codigo == CodigoProducto.MANTEQUILLA){
			t = Quetzal.getManejaRecursos().get("secreto/mantequilla.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		if(codigo == CodigoProducto.DESHODORANTE){
			t = Quetzal.getManejaRecursos().get("secreto/desodorante.png");
			ma = new AnimacionUnica(t, 32, 32, 0);	
		}
		
		product = new Producto(ancho, alto, vel, posX, posY, m, ma);
		product.setCodigo(codigo);
		product.setPrecio(100);
		return product;
	}
	
	public static Producto createProduct(float ancho, float alto, Vector2 vel, float posX, float posY, World m, int codigo) {
		if(codigo == 1){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.HARINA);
		}
		
		if(codigo == 2){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.LECHE);
		}
		
		if(codigo == 3){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.MAYONESA);
		}
		
		if(codigo == 4){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.PAPEL);	
		}
		
		if(codigo == 5){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.ACEITE);
		}
		
		if(codigo == 6){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.DESHODORANTE);
		}
		
		if(codigo == 7){
			product = createProduct(ancho, alto, vel, posX, posY, m, CodigoProducto.MANTEQUILLA);
		}

		return product;
	}
}