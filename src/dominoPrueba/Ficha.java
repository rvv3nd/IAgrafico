package dominoPrueba;

import com.golden.gamedev.object.Sprite;

public class Ficha {
	
	
	int izq;
	int der;
	Sprite img_ficha;
	boolean visible;
	
	
	public Ficha(int i, int d, Sprite img) {
		izq = i;
		der = d;
		img_ficha = img;
		visible = true;
	}

}
