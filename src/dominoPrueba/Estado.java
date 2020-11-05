package dominoPrueba;

enum AccionEstado {
    MoverPiezaManoJugador,
    PasarTurno,
    SacarFichaSopaPasarTurno,
    SacarFichaSopaInsertarTablero,
    FinalJuego
}

public class Estado {
    /**
     * Representa el estado del juego en un instante de tiempo.
     * Es el mismo objeto que es pasado a las funciones de evaluacion
     */
    private GrupoFichas m1;
    private GrupoFichas m2;
    private Sopa sopa;
    private Tablero tablero;
    private int turno;
    private AccionEstado accionOrigen;

    private String[] accionToStr = {
        "Mover pieza desde mano de jugador a tablero.",
        "Pasar turno.",
        "Sacar ficha(s) de sopa y pasar turno.",
        "Sacar ficha(s) de sopa e insertar en tablero.",
        "Final del juego."
    };
    
    public Estado(GrupoFichas m1, GrupoFichas m2, Sopa sopa, Tablero tablero, int turno, AccionEstado accionOrigen) {
        this.m1 = new GrupoFichas(m1);
        this.m2 = new GrupoFichas(m2);
        this.sopa = new Sopa(sopa);
        this.tablero = new Tablero(tablero);
        this.turno = turno;
        this.accionOrigen = accionOrigen;
    }

    public Estado(Estado estado) {
        this.m1 = new GrupoFichas(estado.getManoJugador1());
        this.m2 = new GrupoFichas(estado.getManoJugador2());
        this.sopa = new Sopa(estado.getSopa());
        this.tablero = new Tablero(estado.getTablero());
        this.turno = estado.getTurno();
        this.accionOrigen = estado.getAccionOrigen();
    }

    public GrupoFichas getManoJugador1() {
        return this.m1;
    }

    public void setManoJugador1(GrupoFichas m) {
        this.m1 = new GrupoFichas(m);
    }

    public GrupoFichas getManoJugador2() {
        return this.m2;
    }

    public void setManoJugador2(GrupoFichas m) {
        this.m2 = new GrupoFichas(m);
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public void setTablero(Tablero t) {
        this.tablero = new Tablero(t);
    }

    public Sopa getSopa() {
        return this.sopa;
    }

    public void setSopa(Sopa s) {
        this.sopa = new Sopa(s);
    }

    public AccionEstado getAccionOrigen() {
        return this.accionOrigen;
    }

    public void setAccionOrigen(AccionEstado accion) {
        this.accionOrigen = accion;
    }

    public void incrTurno() {
        this.turno++;
    }

    public int getTurno() {
        return this.turno;
    }

    @Override
    public String toString() {
        return "Mano1: " + m1 + "\n"
                + "Mano2: " + m2 + "\n"
                + "Sopa: " + sopa + "\n"
                + "Tablero: " + tablero + "\n"
                + "Turno: " + turno + "\n"
                + "Accion origen: " + accionToStr[this.accionOrigen.ordinal()];
    }
}