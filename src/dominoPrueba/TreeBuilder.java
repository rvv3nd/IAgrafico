package dominoPrueba;
import java.util.Vector;

class TreeBuilder {
    /**
     * Constructor de arboles a partir de una funcion de evaluacion y un horizonte limitado
     * Hace uso del algortimo poda alfa/beta para hacer la expansion del arbol.
     */
    private final int HORIZONTE_LIMITADO;
    private FuncionEvaluacion funEval;

    public TreeBuilder(FuncionEvaluacion funEval, int horizonteLimitado) {
        /**
         * Crea un constructor de arboles construido a partir de un horizonte limitado
         * y una funcion de evaluacion
         */
        if(horizonteLimitado%2 != 0) {
            System.err.println("Warning. El horizonte limitado debe ser un numero par.");
        }

        this.funEval = funEval;
        this.HORIZONTE_LIMITADO = horizonteLimitado;
    }

    static Comportamiento comportamientoHijos(Nodo nodo) {
        return (nodo.getComportamiento() == Comportamiento.Maximizar)
            ? Comportamiento.Minimizar
            : Comportamiento.Maximizar;
    }

    static GrupoFichas resolverManoActual(Nodo nodo) {
        Estado estado = nodo.getEstado();

        return (nodo.getComportamiento() == Comportamiento.Maximizar)
            ? estado.getManoJugador1()
            : estado.getManoJugador2();

    }

    static public Vector<Nodo> expandirMovimientosMano(Nodo nodo) {
        /**
         * Expande nodos con los movimientos que son posibles realizar
         * solamente agregando fichas al tablero a partir de la mano
         * del tablero actual.
         */
        Estado estado = nodo.getEstado();
        Tablero tablero = estado.getTablero();
        Nodo hijoAux;
        Vector<Nodo> hijos = new Vector<Nodo>();
        Comportamiento comportamientoHijos = comportamientoHijos(nodo);
        GrupoFichas manoActual = resolverManoActual(nodo);
        
        // Acciones que involucren agregar una ficha de la mano actual al tablero
        for(Ficha ficha: manoActual.getFichas()) {
            int numRotaciones = (ficha.esDoble())
                ? 1
                : 2;
            for(int i=0; i<numRotaciones; i++) {
                ficha.rotar();
                Vector<PosicionTablero> posiciones = tablero.posicionInsertar(ficha);
    
                // Ficha actual se puede insertar en tablero
                if(!posiciones.isEmpty()) {
                    for(PosicionTablero posicion: posiciones) {
                        Estado estadoModificado = new Estado(nodo.getEstado());
                        Tablero tableroModificado = estadoModificado.getTablero();
                        GrupoFichas manoModificada = new GrupoFichas(manoActual);
                        Vector<Ficha> fichasModificadas = manoModificada.getFichas();
        
                        tableroModificado.insertar(posicion, ficha);
                        estadoModificado.setTablero(tableroModificado);
                        fichasModificadas.remove(ficha);
        
                        manoModificada.setFichas(fichasModificadas);
        
                        if(nodo.getComportamiento() == Comportamiento.Maximizar) {
                            estadoModificado.setManoJugador1(manoModificada);
                        } else {
                            estadoModificado.setManoJugador2(manoModificada);
                        }
        
                        estadoModificado.setAccionOrigen(AccionEstado.MoverPiezaManoJugador);
                        estadoModificado.incrTurno();
        
                        hijoAux = new Nodo(estadoModificado, comportamientoHijos, nodo.getBeta(), nodo.getAlfa());
                        hijos.add(hijoAux);
                    }
                }
            }
        }

        return hijos;
    }

    static public Vector<Nodo> expandirMovimientosSopa(Nodo nodo) {
        /**
         * Retorna los nodos generados por acciones como
         * la recoleccion de fichas de la sopa.
         */
        Estado estadoPadre = nodo.getEstado();
        Sopa sopaPadre = estadoPadre.getSopa();
        Vector<Nodo> hijos = new Vector<Nodo>();
     
        // Verficar si es posible extraer fichas de la sopa.
        if(!sopaPadre.estaVacio()) {
            Tablero tableroPadre = estadoPadre.getTablero();            
            Comportamiento comportamientoHijos = comportamientoHijos(nodo);
            Vector<Ficha> fichasSopaPadre = sopaPadre.getFichas();
            Vector<PosicionTablero> posicionesInsertar = null; 

            Estado nuevoEstado = null;
            Sopa sopaAux = null;
            GrupoFichas manoJugadorAux = null;
            Tablero tableroAux = null;

            // Recorrer la sopa del estado padre en busca de una ficha que permita
            // realizar un movimiento
            for(int i=0; i<fichasSopaPadre.size(); i++) {
                // Se encontro una ficha en la sopa que es posible insertar en el tablero.
                if(!(posicionesInsertar = tableroPadre.posicionInsertar(fichasSopaPadre.elementAt(i))).isEmpty()) {
                    for(PosicionTablero posicion: posicionesInsertar) {
                        nuevoEstado = new Estado(estadoPadre);
                        nuevoEstado.setAccionOrigen(AccionEstado.SacarFichaSopaInsertarTablero);
                        nuevoEstado.incrTurno();
                        tableroAux = nuevoEstado.getTablero();
                        sopaAux = nuevoEstado.getSopa();
                        manoJugadorAux = nodo.getComportamiento() == Comportamiento.Maximizar? nuevoEstado.getManoJugador1(): nuevoEstado.getManoJugador2();

                        // Insertar las ficha sacadas anteriormente de la sopa a la mano del jugador
                        // Insertar la ultima ficha sacada al tablero
                        manoJugadorAux.getFichas().addAll(fichasSopaPadre.subList(0, i));
                        tableroAux.insertar(posicion, fichasSopaPadre.elementAt(i));
                        for(int j=0; j<=i; j++) sopaAux.getFichas().remove(0);

                        hijos.add(new Nodo(nuevoEstado, comportamientoHijos, nodo.getBeta(), nodo.getAlfa()));
                    }

                    break;
                }
            }

            /**
             * Verificar si se encontro una ficha en la sopa que fuera
             * posible insertar en el tablero. En caso de no encontrarse
             * el nuevo estado es aquel en donde el jugador actual se come 
             * todas las piezas de la sopa y no inserta nada al tablero finalmente
             * pasando su turno.
             */
            if(hijos.isEmpty()) {
                nuevoEstado = new Estado(estadoPadre);
                nuevoEstado.setAccionOrigen(AccionEstado.SacarFichaSopaPasarTurno);
                nuevoEstado.incrTurno();
                sopaAux = nuevoEstado.getSopa();
                manoJugadorAux = nodo.getComportamiento() == Comportamiento.Maximizar? nuevoEstado.getManoJugador1(): nuevoEstado.getManoJugador1();
                
                // Todas las fichas de la sopa ahora son del jugador
                manoJugadorAux.getFichas().addAll(sopaAux.getFichas());
                sopaAux.getFichas().clear();

                hijos.add(new Nodo(nuevoEstado, comportamientoHijos, nodo.getBeta(), nodo.getAlfa()));
            }
        }

        return hijos;
    }

    public Vector<Nodo> expandir(Nodo nodo) {
        /**
         * Expande los hijos nodo a partir de un nodo.
         */
        Vector<Nodo> hijos = null;

        hijos = expandirMovimientosMano(nodo);
        
        // Accion que involucra agarrar una ficha de la sopa
        if(hijos.isEmpty()) {
            hijos = expandirMovimientosSopa(nodo);
            
            // Solo queda pasar el turno
            if(hijos.isEmpty()) {
                Nodo nodoPasarTurno = new Nodo(nodo);
                Estado estado = nodoPasarTurno.getEstado();
                if(nodo.getEstado().getAccionOrigen() == AccionEstado.PasarTurno || nodo.getEstado().getAccionOrigen() == AccionEstado.SacarFichaSopaPasarTurno)
                    estado.setAccionOrigen(AccionEstado.FinalJuego);
                else
                    estado.setAccionOrigen(AccionEstado.PasarTurno);
                nodoPasarTurno.setComportamiento(
                    (nodo.getComportamiento() == Comportamiento.Maximizar)
                    ? Comportamiento.Minimizar
                    : Comportamiento.Maximizar
                );

                hijos = new Vector<Nodo>(); hijos.add(nodoPasarTurno);
            }
            
        }
        return hijos;
    }

    private boolean esTerminal(Nodo nodo, Vector<Nodo> hijos) {
        /**
         * Verficiacion de estado terminal.
         * 1. Final trunco. La sopa esta vacia y ninguno de los jugadores puede realizar movimiento.
         * 2. Ganador. Una mano de jugador esta vacia.
         * 
         * La verificacion solo puede ser efectuada despues de la expansion. Si el nodo padre fue origen
         * de un movimiento que involucraba pasar turno y su unico hijo es pasar turno, entonces signifca
         * que el nodo padre es un nodo terminal por un juego truncado.
         */
        Estado estadoAux = nodo.getEstado();
        AccionEstado accionAux = estadoAux.getAccionOrigen();
        boolean esTerminal = false;

        if(estadoAux.getManoJugador1().estaVacio() || estadoAux.getManoJugador2().estaVacio())
            esTerminal = true;
        else if(accionAux == AccionEstado.PasarTurno || accionAux == AccionEstado.SacarFichaSopaPasarTurno
            && hijos.size() == 1 && hijos.elementAt(0).getEstado().getAccionOrigen() == AccionEstado.PasarTurno) {
            esTerminal = true;
        }

        return esTerminal;
    }

    public TreeIterator build(Nodo raiz) {
        /**
         * Construye un arbol a partir del algortimo poda
         * alpha beta.
         */
        podaAlphaBeta(raiz, 0, Double.MIN_VALUE, Double.MAX_VALUE);
        return new TreeIterator(raiz);
    }

    private void podaAlphaBeta(Nodo raiz, int nivel, double alfa, double beta) {
        /**
         * Algoritmo recursivo de poda alfa/beta
         */
        Vector<Nodo> hijos = null;

        if(nivel == this.HORIZONTE_LIMITADO) {
            double evalVal = this.funEval.ejecutar(raiz.getEstado());
            if(raiz.getComportamiento() == Comportamiento.Maximizar)
                raiz.setAlfa(evalVal);
            else
                raiz.setBeta(evalVal);

            // System.out.println("-------------- Evaluando hoja: --------------\n");
            // System.out.print(raiz);
            // Scanner sc = new Scanner(System.in);
            // sc.nextLine();
    
            return;
        }

        hijos = expandir(raiz);

        if(esTerminal(raiz, hijos)) {
            double evalVal = this.funEval.ejecutar(raiz.getEstado());
            raiz.getEstado().setAccionOrigen(AccionEstado.FinalJuego);

            if(raiz.getComportamiento() == Comportamiento.Maximizar)
                raiz.setAlfa(evalVal);
            else
                raiz.setBeta(evalVal);

            // System.out.println("-------------- Evaluando hoja: --------------\n");
            // System.out.print(raiz);
        } else {
            raiz.setHijos(hijos);

            // Llamado recursivo sobre hijos.
            for(Nodo hijo: raiz.getHijos()) {
                if(raiz.getComportamiento() == Comportamiento.Maximizar) {
                    podaAlphaBeta(hijo, nivel+1, alfa, beta);
                    alfa = Math.max(alfa, hijo.getBeta());
                    raiz.setAlfa(alfa);
    
                    if(alfa >= beta) {
                        // System.out.println("Podando!!");
                        return;
                    }
                } else {
                    podaAlphaBeta(hijo, nivel+1, alfa, beta);
                    beta = Math.min(beta, hijo.getAlfa());
                    raiz.setBeta(beta);
    
                    if(alfa >= beta) {
                        // System.out.println("Podando!!");
                        return;
                    }
                }
            }
        }
    }
}
