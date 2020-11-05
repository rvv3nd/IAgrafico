package dominoPrueba;
import java.util.Arrays;

import com.golden.gamedev.object.Sprite;

public class Ficha {
    /**
     * Representa una ficha del juego con dos valores(v1, v2) en el rango [0-6]
     */
    private byte[] valores;
	Sprite img_ficha;
	Sprite img_ficha_turned_90;
	boolean visible;
    

    public Ficha(byte vI, byte vD, Sprite img, Sprite imgturned) {
        this.valores = new byte[]{vI, vD,};
        this.img_ficha = img; 
        this.img_ficha_turned_90 = imgturned;
        visible = true;
        // Verificacion de rango
        byte[] aux = this.valores.clone(); Arrays.sort(aux);
        if(aux[0] < 0 || aux[1] > 6) {
            throw new IllegalArgumentException("el valor de las fichas debe se 0 <= n <= 6");
        }
    }

    public Ficha(Ficha f) {
        this.valores = new byte[2];
        this.valores[0] = f.valorIzq();
        this.valores[1] = f.valorDer();
    }

    public byte[] getValores() {
        return this.valores;
    }

    public byte valorIzq() {
        return this.valores[0];
    }

    public byte valorDer() {
        return this.valores[1];
    }

    public void rotar() {
        byte aux = this.valores[0];
        this.valores[0] = this.valores[1];
        this.valores[1] = aux;
    }

    public boolean contiene(byte componente) {
        return this.valores[0] == componente
            || this.valores[1] == componente;
    }

    public boolean esDoble() {
        return this.valores [0] == this.valores[1];
    }
    
    public int compareTo(Ficha ficha) {
    	return this.valores[0] + this.valores[1] - ficha.valorIzq() + ficha.valorDer();
    }
    
    @Override
    public boolean equals(Object o) {
        Ficha f2 = (Ficha) o;

        return this.valores[0] == f2.valorIzq()
            && this.valores[1] == f2.valorDer();
    }

    @Override
    public String toString() {
        return "[" + this.valores[0] + ", " + this.valores[1] + " ]";
    }
}