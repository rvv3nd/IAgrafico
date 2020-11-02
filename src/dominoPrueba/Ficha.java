package dominoPrueba;

import com.golden.gamedev.object.Sprite;

public class Ficha {
	
	
	int izq;
	int der;
	Sprite img_ficha;
	Sprite img_ficha_turned_90;
	boolean visible;
	
	
	public Ficha(int i, int d, Sprite img, Sprite imgturned) {
		izq = i;
		der = d;
		img_ficha = img;
		img_ficha_turned_90 = imgturned;
		visible = true;
	}

}
