package dominoPrueba;
import java.util.Vector;

// Define el comportamiento de un nodo en el algortimo Poda Alfa/Beta
enum Comportamiento {
    Maximizar,
    Minimizar
};

class Nodo {
    /**
     * Representa un nodo en el arbol de expansion
     */
    private Estado estado;
    private Vector<Nodo> childrens;
    private Comportamiento comportamiento;
    private double beta;
    private double alfa;
    private Nodo mejorHijo = null;

    public Nodo(Estado estado, Comportamiento comportamiento, double beta, double alfa) {
        this.estado = new Estado(estado);
        this.childrens = new Vector<Nodo>();
        this.comportamiento = comportamiento;
        this.alfa = alfa;
        this.beta = beta;
    }

    public Nodo(Estado estado, Comportamiento comportamiento) {
        this(estado, comportamiento, Double.MAX_VALUE, Double.MIN_VALUE);
    }

    public Nodo(Nodo nodo) {
        this.estado = new Estado(nodo.getEstado());
        this.childrens = new Vector<Nodo>();

        Vector<Nodo> auxHijos = nodo.getHijos();
        for(Nodo n: auxHijos) {
            this.childrens.add(new Nodo(n));
        }

        this.comportamiento = nodo.getComportamiento();
        this.alfa = nodo.getAlfa();
        this.beta = nodo.getBeta();
    }

    public void setHijos(Vector<Nodo> c) {
        this.childrens = new Vector<Nodo>();
        for(Nodo nodo: c) {
            this.childrens.add(new Nodo(nodo));
        }
    }

    public Vector<Nodo> getHijos() {
        return this.childrens;
    }

    public Comportamiento getComportamiento() {
        return this.comportamiento;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public double getBeta() {
        return this.beta;
    }

    public double getAlfa() {
        return this.alfa;
    }

    public Nodo getMejorHijo() {
        /**
         * Retorna el mejor hijo dependiendo de si es un
         * nodo max o min
         */

        // return this.mejorHijo;
        if(this.childrens.size() == 0) throw new IndexOutOfBoundsException("no es posible elegir un mejor hijo en un nodo sin hijos");
        // Scanner sc = new Scanner(System.in);
        // System.out.println("---------------- NODO ACTUAL ----------------\n" + this);

        Nodo mejorHijo = this.childrens.elementAt(0);
        double valorMejorHijo = (this.comportamiento == Comportamiento.Maximizar)
                                ? mejorHijo.getBeta()
                                : mejorHijo.getAlfa();
        double valorNodoHijo = 0;
        // System.out.println("---------------- HIJO ----------------\n" + mejorHijo);

        for(Nodo nodoHijo: this.childrens.subList(1, this.childrens.size())) {
            // System.out.println("---------------- Nodo hijo ----------------\n" + nodoHijo);
            valorNodoHijo = (this.comportamiento == Comportamiento.Maximizar)
                            ? nodoHijo.getBeta()
                            : nodoHijo.getAlfa();
            if((this.comportamiento == Comportamiento.Maximizar) 
                ? valorMejorHijo < valorNodoHijo
                : valorMejorHijo > valorNodoHijo) {
                valorMejorHijo = valorNodoHijo;
                mejorHijo = nodoHijo;
            }
        }
        // System.out.println("---------------- MEJOR HIJO ----------------\n" + mejorHijo);
        // sc.nextLine();

        return mejorHijo;
    }

    public void setMejorHijo(Nodo nodo) {
        this.mejorHijo = new Nodo(nodo);
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public void setComportamiento(Comportamiento comportamiento) {
        this.comportamiento = comportamiento;
    }

    @Override
    public String toString() {
        return  "Comportamiento: " + comportamiento + "\n"
                + "Beta: " + beta + "\n" 
                + "alfa: " + alfa + "\n"
                + "Estado: \n" + estado + "\n"
                + "Hijos: " + childrens.size() + "\n";
                // + childrens + "\n";
    }
}