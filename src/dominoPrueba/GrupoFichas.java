package dominoPrueba;
import java.util.Vector;

public class GrupoFichas {
    protected Vector<Ficha> fichas;

    public GrupoFichas(Vector<Ficha> fichas) {
        this.fichas = fichas;
    }

    public GrupoFichas(GrupoFichas grupoFichas) {
        this.fichas = new Vector<Ficha>();
        for(Ficha ficha: grupoFichas.getFichas()) {
            this.fichas.add(new Ficha(ficha));
        }
    }

    public Vector<Ficha> getFichas() {
        return this.fichas;
    }

    public void setFichas(Vector<Ficha> f) {
        this.fichas = new Vector<Ficha>();
        for(Ficha ficha: f) {
            this.fichas.add(new Ficha(ficha));
        }    
    }
    
    public boolean estaVacio() {
        return this.fichas.isEmpty();
    }

    public int getPuntaje() {
        int total = 0;

        for(Ficha ficha: this.fichas) {
            total += ficha.valorIzq() + ficha.valorDer();
        }
        
        return total;
    }

    @Override
    public String toString() {
        String str = "";
        for(Ficha ficha: this.fichas) {
            str += ficha + ", ";
        }

        return str;
    }
}


class Sopa extends GrupoFichas {
    public Sopa(Vector<Ficha> fichas) {
        super(fichas);
    }

    public Sopa(Sopa sopa) {
        super(sopa);
    }

    public Ficha agarrarAlAzar() {
        if(this.estaVacio())
            throw new IndexOutOfBoundsException("no es posible agarrar un elemento al azar de un grupo de fichas vacia");
        
        return this.fichas.remove(0);
        // int randomIndex = (int)(Math.random() * ((this.fichas.size()-0)+1))+0;
        // return this.fichas.remove(randomIndex);
    }
}