package dominoPrueba;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;

enum PosicionTablero {
    Inicio,
    Final,
    Indefinido
}

public class Tablero {
    /**
     * Representa el tablero de juego con las fichas agregadas por cada jugador.
     */
    private Deque<Ficha> fichas;

    public Tablero() {
        fichas = new LinkedList<Ficha>();
    }
    public Tablero(Deque<Ficha> enJuego) {
        fichas = enJuego;
    }

    public Tablero(Tablero tablero) {
        this.fichas = new LinkedList<Ficha>();
        for(Ficha ficha: tablero.getFichas()) {
            this.fichas.addLast(new Ficha(ficha));
        }
    }
    
    public Ficha getLast() {
    	return fichas.getLast();
    }
    public Ficha getFirst() {
    	return fichas.getFirst();
    }
    
    public Vector<PosicionTablero> posicionInsertar(Ficha ficha) {
        /**
         * Retorna las posiciones donde se puede insertar la ficha. En caso
         * de no poder ser insertada en el Inicio o Final, se retorna 
         * un vector vacio.
         */
        Vector<PosicionTablero> posiciones = new Vector<PosicionTablero>();
        
        if(fichas.isEmpty() || fichas.getLast().valorDer() == ficha.valorIzq())
            posiciones.add(PosicionTablero.Final);
        if(ficha.valorDer() == fichas.getFirst().valorIzq())
            posiciones.add(PosicionTablero.Inicio);
        
        return posiciones;
    }

    public void insertar(PosicionTablero pos, Ficha ficha) {
        /**
         * Inserta la ficha en la posicion especificada
         */
        if(pos == PosicionTablero.Inicio) {
            fichas.addFirst(ficha);
        } else if(pos == PosicionTablero.Final) {
            fichas.addLast(ficha);
        } else {
            throw new IllegalArgumentException("operacion insertar en posicion indefinida en tablero no soportada");
        }
    }

    public Deque<Ficha> getFichas() {
        return this.fichas;
    }

    public int getNumeroFichas() {
        return this.fichas.size();
    }

    @Override
    public boolean equals(Object t) {
        Tablero t2 = (Tablero) t;

        Deque<Ficha> fichasT2 = t2.getFichas();
        if(this.fichas.size() != fichasT2.size()) {
            return false;
        }

        Iterator<Ficha> itr1 = this.fichas.iterator();
        Iterator<Ficha> itr2 = fichasT2.iterator();

        while(itr1.hasNext()) {
            if(itr1.next() != itr2.next())
                return false;
        }

        return true;
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
