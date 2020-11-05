package dominoPrueba;
import java.util.Vector;

class TreeIterator {
    /**
     * Iterator sobre un arbol de nodos
     */
    private Nodo nodoActual;
    private boolean firstIteration = true;

    public TreeIterator(Nodo raiz) {
        this.nodoActual = raiz;
    }

    public Nodo getEstado() {
        return this.nodoActual;
    }

    public Nodo avanzarA(Tablero tableroObjetivo) {
        /**
         * Metodo para avanzar hacia el nodo que tiene como
         * tablero el tableroObjetivo.
         */
        Vector<Nodo> hijos = this.nodoActual.getHijos();
        int index = 0;

        while(index < hijos.size() && hijos.get(index).getEstado().getTablero() != tableroObjetivo)
            index ++;

        if(index < hijos.size()) // Se encontro el hijo con el tablero objetivo
            this.nodoActual = hijos.get(index);
        else // No se encontro ningun hijo
            throw new IllegalArgumentException("no se encontro ningun hijo con el tablero objetivo esperado");

        return this.nodoActual;
    }

    public Nodo next() {
        /**
         * Utilizado por el nodo MAX para avanzar a su mejor 
         * hijo, es decir, seguir con la Path. Este metodo debe ser
         * llamado la primera vez para iniciar el el primer elemento
         */
        if(this.nodoActual.getComportamiento() != Comportamiento.Maximizar) {
            System.out.println("Warning. Esta implementacion no soporta el mejor movimiento para el nodo min");
        }

        if(this.firstIteration) {
            this.firstIteration = false;
            return this.nodoActual;
        }

        this.nodoActual = this.nodoActual.getMejorHijo();

        return this.nodoActual;
    }

    public boolean hasNext() {
        return !this.nodoActual.getHijos().isEmpty();
    }
}