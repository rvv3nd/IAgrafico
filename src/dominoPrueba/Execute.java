package dominoPrueba;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.background.*;
import com.golden.gamedev.util.ImageUtil;

import com.golden.gamedev.object.*;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.*;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Execute extends Game{
	Random random = new Random();
	
	Background  background;
	//Audio audioError,audioOk;
	Sprite s1,t1;
	Sprite tcomer;

	int choisi = 0;
	Sprite l,r;
	boolean text= false;
	int clickX,clickY;

	int inicialComida = 100; 
	int inicialComidaY = 500;
	int rand_index, inicial=300; //inicial de la posicion de las fichas del jugador
	int newX = 600; //x y y para las fichas en juego
	int newY = 50;
	int leftX = newX;
	int leftY = newY;
	int rightX = newX;
	int rightY = newY;
	int temp;
	int pAtLeft = 0;
	int pAtRight = 0;
	
	Estado estadoInicial;
	int jugadorInicial;
	Ficha fichaInicial;
	Tablero tablero;
	Nodo nodoRaiz;
	TreeIterator iterator = null;
    Nodo nodo = null;
    GrupoFichas m1;
    GrupoFichas m2;
    Sopa sopa;
    
    class H1 extends ComponenteEvaluacion {
        public H1(double peso, double rangoI, double rangoS) {
            super(peso, rangoI, rangoS);
        }

        @Override
        public double evaluacion(Estado estado) {
            /**
             * Diferencia suma de fichas
             */
            int total1 = 0;
            int total2 = 0;

            for(Ficha ficha: estado.getManoJugador1().getFichas())
                total1 += ficha.valorDer() + ficha.valorIzq();

            for(Ficha ficha: estado.getManoJugador2().getFichas())
                total2 += ficha.valorDer() + ficha.valorIzq();

            return total2 - total1;
        }
    }

    class H2 extends ComponenteEvaluacion {
        /**
         * Diferencia de mulas
         */
        public H2(double peso, double rangoI, double rangoS) {
            super(peso, rangoI, rangoS);
        }

        @Override
        public double evaluacion(Estado estado) {
            int total1 = 0;
            int total2 = 0;

            for(Ficha ficha: estado.getManoJugador1().getFichas())
                total1 += (ficha.esDoble())? 1: 0;

            for(Ficha ficha: estado.getManoJugador2().getFichas())
                total2 += (ficha.esDoble())? 1: 0;

            return total1 - total2;
        }
    }

    enum TipoFinalJuego {
        Truncado,
        JugadorFinalizo
    }
    
    int profundidad = 10; // profundidad = Integer.parseInt(args[1]);
    FuncionEvaluacion f = new FuncionEvaluacion(
        new H1(1, 0, 65)
        // new H2(.2, 0, 10)
    );
    TreeBuilder builder = new TreeBuilder(f, profundidad);
	
	static public Object[] definirInicio(Estado estado) {
    	/**
    	 * Retorna un arreglo con dos Objetos.
    	 * El primer valor representa el jugador que va realizar el primer movimiento
    	 * 1 para el jugador MAX, 2 para el jugador MIN
    	 * El segundo valor representa la ficha que va mover al tablero, es decir la ficha
    	 * mayor que en la mano de los jugadores, inicialmente se hace una comparacion entre 
    	 * quien tiene la ficha mula mas grande, si ninguno de ellos tiene una mula, entonces
    	 * se retorna la ficha normal mas grande
    	 */
    	Ficha fichaMayorJugador1 = null;
    	Ficha fichaMayorJugador2 = null;
    	GrupoFichas mano1 = estado.getManoJugador1();
    	GrupoFichas mano2 = estado.getManoJugador2();
    	
    	for(Ficha ficha: mano1.getFichas()) {
    		if(fichaMayorJugador1 == null) {
    			fichaMayorJugador1 = ficha;
    		} else {
    			if(fichaMayorJugador1.esDoble()) {
    				if(ficha.esDoble() && fichaMayorJugador1.compareTo(ficha) < 0)
    					fichaMayorJugador1 = ficha;
    			} else {
    				if(ficha.esDoble() || fichaMayorJugador1.compareTo(ficha) < 0)
    					fichaMayorJugador1 = ficha;
    			}
    		}
    	}

    	for(Ficha ficha: mano2.getFichas()) {
    		if(fichaMayorJugador2 == null) {
    			fichaMayorJugador2 = ficha;
    		} else {
    			if(fichaMayorJugador2.esDoble()) {
    				if(ficha.esDoble() && fichaMayorJugador2.compareTo(ficha) < 0)
    					fichaMayorJugador2 = ficha;
    			} else {
    				if(ficha.esDoble() || fichaMayorJugador2.compareTo(ficha) < 0)
    					fichaMayorJugador2 = ficha;
    			}
    		}
    	}

    	// Si ambas son dobles, regresar la mayor, si solo una es doble, entonces regresar la que es
    	// doble, si ninguna es doble, entonces regresar la mayor.
    	if(fichaMayorJugador1.esDoble() && fichaMayorJugador2.esDoble()) {
    		if(fichaMayorJugador1.compareTo(fichaMayorJugador2) > 0)
    			return new Object[]{1, fichaMayorJugador1};
    		else
    			return new Object[]{2, fichaMayorJugador2};
    	} else{
    		if(fichaMayorJugador1.esDoble())
    			return new Object[]{1, fichaMayorJugador1};
    		else if(fichaMayorJugador2.esDoble())
    			return new Object[]{2, fichaMayorJugador2};
			else if(fichaMayorJugador1.compareTo(fichaMayorJugador2) >= 0)
    			return new Object[]{1, fichaMayorJugador1};
    		else
    			return new Object[]{2, fichaMayorJugador2};
    	}

    }

	
	public void initResources() {
		
//		try {
//			audioError =  new Audio("sounds/Frog.wav");
//			audioOk = new Audio("sounds/Pop.wav");
//		}catch(UnsupportedAudioFileException e){
//			
//		}catch(IOException e) {
//			
//		}catch(LineUnavailableException e) {
//			
//		}
		s1 = new Sprite(getImage("images/background.jpg"));
	    s1.setBackground(background);
		
        // initialization of game variables
	    
	    
	    
	    //letreros
		l = new Sprite(getImage("images/leftKey.png"),900,0);
		r = new Sprite(getImage("images/rightKey.png"),1000,0);
	    t1 = new Sprite(getImage("images/tiraficha.png"),600,100);
		tcomer = new Sprite(getImage("images/comer.png"),0,0);
		
		//get all pieces into an array
		
		Vector<BufferedImage> imagesArray = new Vector<BufferedImage>();
				imagesArray.add(getImage("images/piezas/cero-cero.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cero-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-uno.jpg"));
				imagesArray.add(getImage("images/piezas/cero-uno-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-dos.jpg"));
				imagesArray.add(getImage("images/piezas/cero-dos-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-tres.jpg"));
				imagesArray.add(getImage("images/piezas/cero-tres-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cuatro-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cero-seis.jpg"));
				imagesArray.add(getImage("images/piezas/cero-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-uno.jpg"));
				imagesArray.add(getImage("images/piezas/uno-uno-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-dos.jpg"));
				imagesArray.add(getImage("images/piezas/uno-dos-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-tres.jpg"));
				imagesArray.add(getImage("images/piezas/uno-tres-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cuatro-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/uno-seis.jpg"));
				imagesArray.add(getImage("images/piezas/uno-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/dos-dos.jpg"));
				imagesArray.add(getImage("images/piezas/dos-dos-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/dos-tres.jpg"));
				imagesArray.add(getImage("images/piezas/dos-tres-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cuatro-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/dos-seis.jpg"));
				imagesArray.add(getImage("images/piezas/dos-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/tres-tres.jpg"));
				imagesArray.add(getImage("images/piezas/tres-tres-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cuatro-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/tres-seis.jpg"));
				imagesArray.add(getImage("images/piezas/tres-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cuatro-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-seis.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-cinco-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-seis.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-seis-turned-90.jpg"));
				imagesArray.add(getImage("images/piezas/seis-seis.jpg"));
				imagesArray.add(getImage("images/piezas/seis-seis-turned-90.jpg"));
			Vector<Ficha> fichas = new Vector<Ficha>();
			Vector<Ficha> playerFichas = new Vector<Ficha>();
			Vector<Ficha> pcFichas = new Vector<Ficha>();
			Deque<Ficha> enJuego = new LinkedList<Ficha>();
			//put every image into an object Ficha
			byte izq = 0;
			byte der = 0;
			for (int i=0;i<imagesArray.size();i+=2) {
				fichas.add(new Ficha(izq,der,new Sprite (imagesArray.get(i)),new Sprite (imagesArray.get(i+1))));
				//lleva el control de los numeros de cada ficha 
				der++;
				if(der>6) { //asigna el valor de cada ficha
					izq ++;
					der = izq;
				}
			}
			
			//asigna fichas al jugador
			for(int i=0;i<7;i++) {
				rand_index = random.nextInt(fichas.size()-1);
				fichas.get(rand_index).img_ficha.setLocation(inicial, 600);
				playerFichas.add(fichas.remove(rand_index));
				inicial += 110;
			}
			
			
			System.out.println("player:");
			for (Ficha ficha: playerFichas) {
				System.out.println("Izq: "+ ficha.valorIzq() +" Der:" + ficha.valorDer());
			}
			
			/* inicial = 300; */
			//asigna fichas a la pc
			for(int i=0;i<7;i++) {
				rand_index = random.nextInt(fichas.size()-1);
				pcFichas.add(fichas.remove(rand_index));
//				inicial += 60;
			}
			//genera estado inicial
			m1 = new GrupoFichas(playerFichas);
			m2 = new GrupoFichas(pcFichas);
			sopa = new Sopa(fichas);
			tablero = new Tablero(enJuego);
			estadoInicial = new Estado(m1,m2,sopa,tablero,0,AccionEstado.MoverPiezaManoJugador);
            nodoRaiz = new Nodo(estadoInicial, Comportamiento.Maximizar, Double.MAX_VALUE, Double.MIN_VALUE);
			/*
			 * System.out.println("pc:"); for (Ficha ficha: pcFichas) {
			 * System.out.println("Izq: "+ ficha.valorIzq() +" Der:" + ficha.valorDer()); }
			 * System.out.println("sopa:"); for (Ficha ficha: fichas) {
			 * System.out.println("Izq: "+ ficha.valorIzq() +" Der:" + ficha.valorDer()); }
			 */
    }

    public void update(long elapsedTime) {
        // updating the game variables
    	t1.update(elapsedTime);
    	for(Ficha ficha: m1.getFichas()) {
    		ficha.img_ficha.update(elapsedTime);
    	}
//    	for(Sprite ficha: pcFichas) {
//    		ficha.update(elapsedTime);
//    	}
    	
    	if(estadoInicial.getTurno()%2 != 0) {
    		//tiro de pc
    	}
    	
    	if(click() && estadoInicial.getTurno()%2 == 0) { 
    		if(2%2 == 0) {
	    		clickX = getMouseX();
	    		clickY = getMouseY();
	    		try {
		    		for(int i=0;i<m1.getFichas().size();i++) {
		    			if( clickX >= (int) m1.getFichas().get(i).img_ficha.getX() &&
		    	    			clickX <= (int) m1.getFichas().get(i).img_ficha.getX() + m1.getFichas().get(i).img_ficha.getWidth() &&
		    	    			clickY >= (int) m1.getFichas().get(i).img_ficha.getY() &&
		    	    			clickY <= (int) m1.getFichas().get(i).img_ficha.getY() + m1.getFichas().get(i).img_ficha.getHeight()){
		    	    			//selección de ficha por usuario 
		    	    			mover(elapsedTime,i);
		    	    			//llama a mover
		    	    	}
		    		}
	    		}catch(Exception e) {
	    			
	    			System.out.println(e+"Ya no existe ficha en esas coordenadas");
	    		}
	    		if(clickX >= (int) tcomer.getX() &&
	        	   clickX <= (int) tcomer.getX() + tcomer.getWidth() &&
	        	   clickY >= (int) tcomer.getY() &&
	        	   clickY <= (int) tcomer.getY() + tcomer.getHeight()) {
	    			
	    			System.out.println("Comiendo...");
	    			try {
	//	    			rand_index = random.nextInt(fichas.size()-1);
		    			sopa.getFichas().get(0).img_ficha.setLocation(inicialComida, inicialComidaY);
		    			inicialComida+=110;
		    			if(inicialComida >1100) {
		    				inicialComida = 100;
		    				inicialComidaY -=100;
		    			}
		    			m1.getFichas().add(sopa.getFichas().remove(0));
		    			//audioOk.play();
	    			}catch(Exception e) {
	    				//audioError.play(); 
	    				System.out.println("Te has acabado las fichas, tragon!");
	    			}
	    		}
	    		//Cuando escoge dirección en la que entra el movimiento 
				if(clickX >= (int) l.getX() && clickX <= l.getX()+l.getWidth() &&
				   clickY >=0 && clickY <= l.getHeight()) {
					choisi = 1;
					System.out.println(choisi);
				}
				if(clickX >= (int) r.getX() && clickX <= r.getX()+r.getWidth() &&
				   clickY >=0 && clickY <= r.getHeight()) {
					choisi = 2;
					System.out.println(choisi);
				}
    		}else {
    			//play error sound
    		}
    	}
    	
    	for(Ficha ficha: m1.getFichas()) {
    		ficha.img_ficha.update(elapsedTime);
    	}
    	
    	
    	/*Cuando se inicia el juego hay 4 casos posibles:
    	 * la pc no tiene mulas, o sea mulaMayor regresa null, entonces debe mover el jugador
    	 * si no fue null en pc y sí en pl, entonces mueve la pc
    	 * por ultimo si ninguno es null prueba cual mula es la mayor
    	 * Notar que si ambos son null se queda con que debe mover el primero el jugador*/
	    
    	
    	if(tablero.getFichas().size()==0) {
    		
    		Ficha pc = mulaMayor(m2.getFichas());
	    	Ficha pl = mulaMayor(m1.getFichas());
	    	if(pc == null) //mueve el jugador pq pc no tiene mulas
	    		text = true;
	    	else if(pl == null) {
	    		//mueve la pc pq jugador no tiene mulas
 	    		pc.img_ficha.setX(newX);
	    		pc.img_ficha.setY(newY);
	    		tablero.insertar(PosicionTablero.Inicio, pc);
	    		m2.getFichas().remove(pc);
	    		
	    	}
	    	//si ambos tienen mulas
	    	else {	
		    	if (pc.valorIzq() < pl.valorIzq()) {
		    		//mueve el jugador
		    		
		    		text = true;
		    		
		    	}else {
		    		//mueve la pc
		    		pc.img_ficha.setX(newX);
		    		pc.img_ficha.setY(newY);
		    		tablero.insertar(PosicionTablero.Inicio, pc);
		    		m2.getFichas().remove(pc);
		    	}
	    	}
	    	
    	
	    }else {text = false;}
	    
	    l.update(elapsedTime);
	    r.update(elapsedTime);
	    
	    if(text)s1.update(elapsedTime);
	    
	    tcomer.update(elapsedTime);
	    
    }

    public void render(Graphics2D g) {
        // rendering to the screen
    	s1.render(g);
    	
    	l.render(g);
	    r.render(g);
    	tcomer.render(g);
    	if(text)t1.render(g);
    	for(Ficha ficha: m1.getFichas()) {
    		if (ficha.visible) ficha.img_ficha.render(g);	
    	}
//    	for(Sprite ficha: pcFichas) {
//    		ficha.render(g);	
//    	}
    	
    	for(Ficha ficha: tablero.getFichas()) {
    		ficha.img_ficha.render(g);	
    	}
    	
    	
    }
    
    private Ficha mulaMayor(Vector<Ficha> juego) {
    	int max = -1;
    	int iMax = -1;
    	int i = 0;
    	for(Ficha ficha: juego) {
    		if(ficha.valorIzq() == ficha.valorDer() && max < ficha.valorIzq()) {
    			max = ficha.valorIzq();
    			iMax = i;
    		}
    		i++;
    	}
    	if (iMax != -1) {
    		return juego.get(iMax);
    	}else {
    		return null;
    	}
    	
    }
    
    private int isValid(Deque<Ficha> enJuego2,Ficha entrada) {
    	Vector<Integer> validos = new Vector<Integer>();
    	if(enJuego2.getFirst().valorIzq() == entrada.valorDer())//o sea que se puede ingresar al inicio 
    		validos.add(1); //1 indica que es movimiento valido a la izquierda
    	else if(enJuego2.getFirst().valorIzq() == entrada.valorIzq())
    		validos.add(2); //2 indica que es movimiento valido a la izquierda pero rotando la imagen
    	if(enJuego2.getLast().valorDer() == entrada.valorIzq())
    		validos.add(3); //3 indica que es movimiento valido a la derecha
    	else if(enJuego2.getLast().valorDer() == entrada.valorDer())
    		validos.add(4); //4 indica que es movimiento valido a la derecha pero rotando la imagen
    	
    	if (validos.isEmpty()) return 0;
    	if (validos.size() == 1) return validos.get(0);
    	if (validos.size() == 2) {
    		System.out.println("¿Insertar a la Izquierda o derecha?");
    		if(choisi == 0) {
    			//System.out.print(".");
    		}
    		if(choisi == 1) return validos.get(0);
    		else if(choisi == 2) return validos.get(1);
    	}
    	return 0;
    }
    
	
    
    private void mover(long elapsedTime, int n){
    	System.out.println("Selecciona ficha " +n);
		if(!tablero.getFichas().isEmpty()) {
			switch(isValid(tablero.getFichas(),m1.getFichas().get(n))) {
			
			case 0:
				System.out.println("Movimiento no válido");
				//play error sound
				//audioError.play(); 
				break;
			case 1:
				System.out.println("Inserta a la izquierda");
				if (pAtLeft <= 5) {
					leftX = ingresaIzq(tablero,m1.getFichas(),leftX,leftY,n);
				}
				
				else if(pAtLeft>5 && pAtLeft <=7) { //caso donde ya llegó al final horizontalmente
					leftY = ingresaIzqHorizontal(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 7 && pAtLeft <= 10){ // al limite "regresando" a la derecha entonces usar metodo de insertar derecha xd
					if (pAtLeft == 8)leftY = leftY + tablero.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_Der(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 10 && pAtLeft <=12){ //de nuevo hacia abajo 
					if (pAtLeft == 11) leftX = leftX + tablero.getFichas().getFirst().img_ficha.getWidth()/2;
					leftY = ingresaIzq_DerHorizontal(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 12 && pAtLeft <= 14){
					if (pAtLeft == 13)leftY = leftY + tablero.getFichas().getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq(tablero,m1.getFichas(),leftX,leftY,n);
				}else if(pAtLeft > 14 && pAtLeft <=16){
					leftY = ingresaIzqHorizontal(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else {
					if (pAtLeft == 17)leftY = leftY + tablero.getFichas().getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_Der(tablero,m1.getFichas(),leftX,leftY,n);
				}
				System.out.println(pAtLeft);
				//audioOk.play();
				pAtLeft ++; //cada vez aumenta para seguir el paso de cuantas hay cada lado
				break;
			case 2: 
				System.out.println("Inserta a la izquierda rotando");
				if(pAtLeft <5) {
					leftX = ingresaIzqRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft>=5 && pAtLeft <=7) { //caso donde ya llegó al final horizontalmente
				//gira 90°
					leftY = ingresaIzqHorizontalRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}else if(pAtLeft > 7 && pAtLeft <=10){
					if (pAtLeft == 8)leftY = leftY + tablero.getFichas().getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_DerRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 10 && pAtLeft <=12) {
					if (pAtLeft == 11) leftX = leftX + tablero.getFichas().getFirst().img_ficha.getWidth()/2;
					leftY = ingresaIzq_DerHorizontalRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 12 && pAtLeft <= 14) {
					//vuelve a meter hacia la izquierda normalito
					if (pAtLeft == 13)leftY = leftY + tablero.getFichas().getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzqRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}
				else if(pAtLeft > 14 && pAtLeft <=16){
					leftY = ingresaIzqHorizontalRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}else {
					if (pAtLeft == 17)leftY = leftY + tablero.getFichas().getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_DerRotando(tablero,m1.getFichas(),leftX,leftY,n);
				}
				System.out.println(pAtLeft);
				//audioOk.play();
				pAtLeft ++;
				break;
			case 3:
				System.out.println("Inserta a la derecha");
				if(pAtRight <5) {
					rightX = ingresaDer(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight >=5 && pAtRight <=7) {
					if(pAtRight == 5) rightX = rightX + (tablero.getFichas().getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontal(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 7 && pAtRight <=10) {
					if (pAtRight == 8)rightY = rightY + tablero.getFichas().getLast().img_ficha.getHeight()/2;
					rightX = ingresaDer_Izq(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 10 && pAtRight <=12) {
					//if (pAtRight == 11) rightX = rightX - enJuego.getLast().img_ficha.getWidth()/2;
					rightY = ingresaDer_IzqHorizontal(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 12 && pAtRight <=14) {
					rightX = ingresaDer(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 14 && pAtRight <=16) {
					rightY = ingresaDerHorizontal(tablero,m1.getFichas(),rightX,rightY,n);
				}else {
					if (pAtRight == 17)rightY = rightY + tablero.getFichas().getLast().img_ficha.getHeight()/2;
					rightX = ingresaDer_Izq(tablero,m1.getFichas(),rightX,rightY,n);
				}
				System.out.println(pAtRight);
				//audioOk.play();
				pAtRight ++;
				break;
			case 4:
				System.out.println("Inserta a la derecha rotando");
				if(pAtRight <5) {
					rightX = ingresaDerRotando(tablero,m1.getFichas(),rightX,rightY,n);
				}else if(pAtRight >=5 && pAtRight <=7) {
					if(pAtRight == 5) rightX = rightX + (tablero.getFichas().getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontalRotando(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 7 && pAtRight <=10) {
					if (pAtRight == 8)rightY = rightY + tablero.getFichas().getLast().img_ficha.getWidth()/2;
					rightX = ingresaDer_IzqRotando(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else if(pAtRight > 10 && pAtRight <=12) {
					//if (pAtRight == 11) rightX = rightX + enJuego.getLast().img_ficha.getWidth()/2;
					rightY = ingresaDer_IzqHorizontalRotando(tablero,m1.getFichas(),rightX,rightY,n);
					
				}
				else if(pAtRight > 12 && pAtRight <=14) {
					rightX = ingresaDerRotando(tablero,m1.getFichas(),rightX,rightY,n);	
				}
				else if(pAtRight > 14 && pAtRight <=16) {
					if(pAtRight == 15) rightX = rightX + (tablero.getFichas().getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontalRotando(tablero,m1.getFichas(),rightX,rightY,n);
				}
				else {
					if (pAtRight == 17)rightY = rightY + tablero.getFichas().getLast().img_ficha.getWidth()/2;
					rightX = ingresaDer_IzqRotando(tablero,m1.getFichas(),rightX,rightY,n);
				}
				System.out.println(pAtRight);
				//audioOk.play();
				pAtRight++;
				break;
			}
		}else {
			//esto solo es al inicio en el caso de que no haya mula mayor en el juego o qué el jugador tenga la mula mayor
			Ficha t = mulaMayor(m1.getFichas());
			if(t!=null) {
				if(t.valorDer() == m1.getFichas().get(n).valorDer() && t.valorIzq() == m1.getFichas().get(n).valorIzq()) {
					//la ficha deleccionada es la mula mayor
					System.out.println("Inserta al centro la mula mayor");
    				m1.getFichas().get(n).img_ficha.setLocation(newX, newY);
    				m1.getFichas().get(n).visible = false;
    				tablero.insertar(PosicionTablero.Inicio,m1.getFichas().remove(n)); // saca la ficha para agregarla al inicio y además tiene que rotar la imagen
    				//audioOk.play();
				}else {
					System.out.println("Debes elegir a la mula mayor!");
					//audioError.play();
				}
			}else {
				//no hay mula mayor
				System.out.println("Inserta al centro cualquiera");
				m1.getFichas().get(n).img_ficha.setLocation(newX, newY);
				m1.getFichas().get(n).visible = false;
				tablero.getFichas().addFirst(m1.getFichas().remove(n));
				//audioOk.play();
			}
		}
		choisi = 0;
    }
    private int ingresaIzq(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
		x = x-entrada.get(n).img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
		return x;
	}
    
    private int ingresaIzqRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x-entrada.get(n).img_ficha.getWidth();
    	//rota imagen
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		//rota datos
		entrada.get(n).rotar();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
		return x;
    }
    
    private int ingresaIzqHorizontal(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzqHorizontalRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	//rota imagen 
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	entrada.get(n).rotar();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
    	tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzq_Der(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getFirst().img_ficha.getWidth();
		entrada.get(n).visible = false;
		//rotar(playerFichas.get(n));
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
		return x;
    }
    
    private int ingresaIzq_DerRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getFirst().img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).rotar();
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
		return x;
    }
    private int ingresaIzq_DerHorizontal(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzq_DerHorizontalRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	entrada.get(n).rotar();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
    	tablero.insertar(PosicionTablero.Inicio, entrada.remove(n));
    	return y;
    }
    private int ingresaDer(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getLast().img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
		return x;
    }
    
    private int ingresaDerRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getLast().img_ficha.getWidth();
		entrada.get(n).visible = false;
		//rotar(playerFichas.get(n));
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		entrada.get(n).rotar();
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
		return x;
    }
    
    private int ingresaDerHorizontal(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y =  y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
    	return y;
    }
    private int ingresaDerHorizontalRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	//rota imagen 
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	entrada.get(n).rotar();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
    	tablero.insertar(PosicionTablero.Final, entrada.remove(n));
    	return y;
    }
    
    private int ingresaDer_Izq(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x - entrada.get(n).img_ficha.getWidth();
    	entrada.get(n).visible = false;
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
		return x;
    }
    
    private int ingresaDer_IzqRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	x = x - entrada.get(n).img_ficha.getWidth();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).rotar();
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
    	return x;
    }
    
    public int ingresaDer_IzqHorizontal(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.insertar(PosicionTablero.Final, entrada.remove(n));
    	return y;
    }
    
    public int ingresaDer_IzqHorizontalRotando(Tablero tablero, Vector<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	entrada.get(n).rotar();
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
    	tablero.insertar(PosicionTablero.Final, entrada.remove(n));
    	return y;
    }

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		GameLoader game = new GameLoader();
		game.setup(new Execute(), new Dimension(1200,700), false);
		game.start();
	}

}
