package dominoPrueba;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.background.*;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseAudioRenderer;
import com.golden.gamedev.engine.audio.WaveRenderer;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.object.*;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.*;
import java.util.*;

public class Execute extends Game{
	Random random = new Random();
	ArrayList<Ficha> fichas = new ArrayList<Ficha>();
	ArrayList<Ficha> playerFichas = new ArrayList<Ficha>();
	ArrayList<Ficha> pcFichas = new ArrayList<Ficha>();
	LinkedList<Ficha> enJuego = new LinkedList<Ficha>();
	Background  background;
	WaveRenderer errorSound;
	Sprite s1,t1;
	Sprite tcomer;
	boolean text= false;
	int clickX,clickY;
	int in; //recibe isValid para acomodar las fichas al inicioo al final 
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

	
	public void initResources() {

		s1 = new Sprite(getImage("images/background.jpg"));
	    s1.setBackground(background);
		
        // initialization of game variables
	    
	    
	    //sonido
	  
	    
	    //letreros
	    t1 = new Sprite(getImage("images/tiraficha.png"),600,100);
		tcomer = new Sprite(getImage("images/comer.png"),0,0);

		//get all pieces into an array
		
		ArrayList<BufferedImage> imagesArray = new ArrayList<BufferedImage>();
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
			
			//put every image into an object Ficha
			int izq = 0;
			int der = 0;
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
			
			for (Ficha ficha: playerFichas) {
				System.out.println("Izq: "+ ficha.izq +" Der:" + ficha.der);
			}
			
			/* inicial = 300; */
			//asigna fichas a la pc
			for(int i=0;i<7;i++) {
				rand_index = random.nextInt(fichas.size()-1);
				pcFichas.add(fichas.remove(rand_index));
//				inicial += 60;
			}
    }

    public void update(long elapsedTime) {
        // updating the game variables
    	t1.update(elapsedTime);
    	for(Ficha ficha: playerFichas) {
    		ficha.img_ficha.update(elapsedTime);
    	}
//    	for(Sprite ficha: pcFichas) {
//    		ficha.update(elapsedTime);
//    	}
    	
    	if(click()) {
    		clickX = getMouseX();
    		clickY = getMouseY();
    		try {
	    		for(int i=0;i<playerFichas.size();i++) {
	    			if( clickX >= (int) playerFichas.get(i).img_ficha.getX() &&
	    	    			clickX <= (int) playerFichas.get(i).img_ficha.getX() + playerFichas.get(i).img_ficha.getWidth() &&
	    	    			clickY >= (int) playerFichas.get(i).img_ficha.getY() &&
	    	    			clickY <= (int) playerFichas.get(i).img_ficha.getY() + playerFichas.get(i).img_ficha.getHeight()){
	    	    			//selecciono la primer ficha 
	    	    			mover(elapsedTime,i);
	    	    	}
	    		}
    		}catch(Exception e) {
    			System.out.println("Ya no existe ficha en esas coordenadas");
    		}
    		if(clickX >= (int) tcomer.getX() &&
        	   clickX <= (int) tcomer.getX() + tcomer.getWidth() &&
        	   clickY >= (int) tcomer.getY() &&
        	   clickY <= (int) tcomer.getY() + tcomer.getHeight()) {
    			
    			System.out.println("Comiendo...");
    			try {
	    			rand_index = random.nextInt(fichas.size()-1);
	    			fichas.get(rand_index).img_ficha.setLocation(inicialComida, inicialComidaY);
	    			inicialComida+=110;
	    			if(inicialComida >1100) {
	    				inicialComida = 100;
	    				inicialComidaY -=100;
	    			}
	    			playerFichas.add(fichas.remove(rand_index));
    			}catch(Exception e) {
    				System.out.println("Te has acabado las fichas, tragon!");
    			}
    		}
    	}
    	
    	for(Ficha ficha: enJuego) {
    		ficha.img_ficha.update(elapsedTime);
    	}
    	
    	
    	/*Cuando se inicia el juego hay 4 casos posibles:
    	 * la pc no tiene mulas, o sea mulaMayor regresa null, entonces debe mover el jugador
    	 * si no fue null en pc y sí en pl, entonces mueve la pc
    	 * por ultimo si ninguno es null prueba cual mula es la mayor
    	 * Notar que si ambos son null se queda con que debe mover el primero el jugador*/
	    if(enJuego.size()==0) {
    		Ficha pc = mulaMayor(pcFichas);
	    	Ficha pl = mulaMayor(playerFichas);
	    	if(pc == null) //mueve el jugador 
	    		text = true;
	    	else if(pl == null) {
	    		//mueve la pc
	    		pc.img_ficha.setX(newX);
	    		pc.img_ficha.setY(newY);
	    		enJuego.add(pc);
	    		pcFichas.remove(pc);
	    		
	    	}
	    	else {	
		    	if (pc.izq < pl.izq) {
		    		//mueve el jugador
		    		
		    		text = true;
		    		
		    	}else {
		    		//mueve la pc
		    		pc.img_ficha.setX(newX);
		    		pc.img_ficha.setY(newY);
		    		enJuego.add(pc);
		    		pcFichas.remove(pc);
		    	}
	    	}
    	
	    }else {text = false;}
	    
	    if(text)s1.update(elapsedTime);
	    
	    tcomer.update(elapsedTime);
	    
    }

    public void render(Graphics2D g) {
        // rendering to the screen
    	s1.render(g);
    	tcomer.render(g);
    	if(text)t1.render(g);
    	for(Ficha ficha: playerFichas) {
    		if (ficha.visible) ficha.img_ficha.render(g);	
    	}
//    	for(Sprite ficha: pcFichas) {
//    		ficha.render(g);	
//    	}
    	
    	for(Ficha ficha: enJuego) {
    		ficha.img_ficha.render(g);	
    	}
    	
    	
    }
    
    private Ficha mulaMayor(ArrayList<Ficha> juego) {
    	int max = -1;
    	int iMax = -1;
    	int i = 0;
    	for(Ficha ficha: juego) {
    		if(ficha.izq == ficha.der && max < ficha.izq) {
    			max = ficha.izq;
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
    
    private int isValid(LinkedList<Ficha> tablero,Ficha entrada) {
    	
    	if(tablero.getFirst().izq == entrada.der)//o sea que se puede ingresar al inicio 
    		return 1; //1 indica que es movimiento valido a la izquierda
    	if(tablero.getFirst().izq == entrada.izq)
    		return 2; //2 indica que es movimiento valido a la izquierda pero rotando la imagen
    	if(tablero.getLast().der == entrada.izq)
    		return 3; //3 indica que es movimiento valido a la derecha
    	if(tablero.getLast().der == entrada.der)
    		return 4; //3 indica que es movimiento valido a la derecha pero rotando la imagen
    	return 0;
    }
    
	
    
    private void mover(long elapsedTime, int n) {
    	System.out.println("Selecciona ficha " +n);
		if(!enJuego.isEmpty()) {
			switch(isValid(enJuego,playerFichas.get(n))) {
			
			case 0:
				System.out.println("Movimiento no válido");
				//play error sound
				break;
			case 1:
				System.out.println("Inserta a la izquierda");
				if (pAtLeft <= 5) {
					leftX = ingresaIzq(enJuego,playerFichas,leftX,leftY,n);
				}
				
				else if(pAtLeft>5 && pAtLeft <=7) { //caso donde ya llegó al final horizontalmente
					leftY = ingresaIzqHorizontal(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 7 && pAtLeft <= 10){ // al limite "regresando" a la derecha entonces usar metodo de insertar derecha xd
					if (pAtLeft == 8)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_Der(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 10 && pAtLeft <=12){ //de nuevo hacia abajo 
					if (pAtLeft == 11) leftX = leftX + enJuego.getFirst().img_ficha.getWidth()/2;
					leftY = ingresaIzq_DerHorizontal(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 12 && pAtLeft <= 14){
					if (pAtLeft == 13)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq(enJuego,playerFichas,leftX,leftY,n);
				}else if(pAtLeft > 14 && pAtLeft <=16){
					leftY = ingresaIzqHorizontal(enJuego,playerFichas,leftX,leftY,n);
				}
				else {
					if (pAtLeft == 17)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_Der(enJuego,playerFichas,leftX,leftY,n);
				}
				System.out.println(pAtLeft);
				pAtLeft ++; //cada vez aumenta para seguir el paso de cuantas hay cada lado
				break;
			case 2: 
				System.out.println("Inserta a la izquierda rotando");
				if(pAtLeft <5) {
					leftX = ingresaIzqRotando(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft>=5 && pAtLeft <=7) { //caso donde ya llegó al final horizontalmente
				//gira 90°
					leftY = ingresaIzqHorizontalRotando(enJuego,playerFichas,leftX,leftY,n);
				}else if(pAtLeft > 7 && pAtLeft <=10){
					if (pAtLeft == 8)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_DerRotando(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 10 && pAtLeft <=12) {
					if (pAtLeft == 11) leftX = leftX + enJuego.getFirst().img_ficha.getWidth()/2;
					leftY = ingresaIzq_DerHorizontalRotando(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 12 && pAtLeft <= 14) {
					//vuelve a meter hacia la izquierda normalito
					if (pAtLeft == 13)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzqRotando(enJuego,playerFichas,leftX,leftY,n);
				}
				else if(pAtLeft > 14 && pAtLeft <=16){
					leftY = ingresaIzqHorizontalRotando(enJuego,playerFichas,leftX,leftY,n);
				}else {
					if (pAtLeft == 17)leftY = leftY + enJuego.getFirst().img_ficha.getHeight()/2;
					leftX = ingresaIzq_DerRotando(enJuego,playerFichas,leftX,leftY,n);
				}
				System.out.println(pAtLeft);
				pAtLeft ++;
				break;
			case 3:
				System.out.println("Inserta a la derecha");
				if(pAtRight <5) {
					rightX = ingresaDer(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight >=5 && pAtRight <=7) {
					if(pAtRight == 5) rightX = rightX + (enJuego.getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontal(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 7 && pAtRight <=10) {
					if (pAtRight == 8)rightY = rightY + enJuego.getLast().img_ficha.getHeight()/2;
					rightX = ingresaDer_Izq(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 10 && pAtRight <=12) {
					//if (pAtRight == 11) rightX = rightX - enJuego.getLast().img_ficha.getWidth()/2;
					rightY = ingresaDer_IzqHorizontal(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 12 && pAtRight <=14) {
					rightX = ingresaDer(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 14 && pAtRight <=16) {
					rightY = ingresaDerHorizontal(enJuego,playerFichas,rightX,rightY,n);
				}else {
					if (pAtRight == 17)rightY = rightY + enJuego.getLast().img_ficha.getHeight()/2;
					rightX = ingresaDer_Izq(enJuego,playerFichas,rightX,rightY,n);
				}
				System.out.println(pAtRight);
				pAtRight ++;
				break;
			case 4:
				System.out.println("Inserta a la derecha rotando");
				if(pAtRight <5) {
					rightX = ingresaDerRotando(enJuego,playerFichas,rightX,rightY,n);
				}else if(pAtRight >=5 && pAtRight <7) {
					if(pAtRight == 5) rightX = rightX + (enJuego.getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontalRotando(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 7 && pAtRight <=10) {
					if (pAtRight == 8)rightY = rightY + enJuego.getLast().img_ficha.getWidth()/2;
					rightX = ingresaDer_IzqRotando(enJuego,playerFichas,rightX,rightY,n);
				}
				else if(pAtRight > 10 && pAtRight <=12) {
					//if (pAtRight == 11) rightX = rightX + enJuego.getLast().img_ficha.getWidth()/2;
					rightY = ingresaDer_IzqHorizontalRotando(enJuego,playerFichas,rightX,rightY,n);
					
				}
				else if(pAtRight > 12 && pAtRight <=14) {
					rightX = ingresaDerRotando(enJuego,playerFichas,rightX,rightY,n);	
				}
				else if(pAtRight > 14 && pAtRight <=16) {
					if(pAtRight == 15) rightX = rightX + (enJuego.getLast().img_ficha.getWidth()/2); 
					rightY = ingresaDerHorizontalRotando(enJuego,playerFichas,rightX,rightY,n);
				}
				else {
					if (pAtRight == 17)rightY = rightY + enJuego.getLast().img_ficha.getWidth()/2;
					rightX = ingresaDer_IzqRotando(enJuego,playerFichas,rightX,rightY,n);
				}
				System.out.println(pAtRight);
				pAtRight++;
				break;
			}
		}else {
			//esto solo es al inicio en el caso de que no haya mula mayor en el juego o qué el jugador tenga la mula mayor
			Ficha t = mulaMayor(playerFichas);
			if(t!=null) {
				if(t.der == playerFichas.get(n).der && t.izq == playerFichas.get(n).izq) {
					//la ficha deleccionada es la mula mayor
					System.out.println("Inserta al centro la mula mayor");
    				playerFichas.get(n).img_ficha.setLocation(newX, newY);
    				playerFichas.get(n).visible = false;
    				enJuego.addFirst(playerFichas.remove(n)); // saca la ficha para agregarla al inicio y además tiene que rotar la imagen
				}else {
					System.out.println("Debes elegir a la mula mayor!");
					//play error sound
				}
			}else {
				//no hay mula mayor
				System.out.println("Inserta al centro cualquiera");
				playerFichas.get(n).img_ficha.setLocation(newX, newY);
				playerFichas.get(n).visible = false;
				enJuego.addFirst(playerFichas.remove(n));
			}
		}
		
    }
    private int ingresaIzq(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
		x = x-entrada.get(n).img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.addFirst(entrada.remove(n));
		return x;
	}
    
    private int ingresaIzqRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x-entrada.get(n).img_ficha.getWidth();
    	//rota imagen
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		//rota datos
		int tempo = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = tempo;
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.addFirst(entrada.remove(n));
		return x;
    }
    
    private int ingresaIzqHorizontal(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
    	tablero.addFirst(entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzqHorizontalRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	//rota imagen 
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.addFirst(entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzq_Der(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getFirst().img_ficha.getWidth();
		entrada.get(n).visible = false;
		//rotar(playerFichas.get(n));
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		
		tablero.addFirst(entrada.remove(n));
		return x;
    }
    
    private int ingresaIzq_DerRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getFirst().img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
		entrada.get(n).visible = false;
		tablero.addFirst(entrada.remove(n));
		return x;
    }
    private int ingresaIzq_DerHorizontal(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
    	tablero.addFirst(entrada.remove(n));
    	return y;
    }
    
    private int ingresaIzq_DerHorizontalRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getFirst().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.addFirst(entrada.remove(n));
    	return y;
    }
    private int ingresaDer(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getLast().img_ficha.getWidth();
		entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
		tablero.addLast(entrada.remove(n));
		return x;
    }
    
    private int ingresaDerRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x + tablero.getLast().img_ficha.getWidth();
		entrada.get(n).visible = false;
		//rotar(playerFichas.get(n));
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
		tablero.addLast(entrada.remove(n));
		return x;
    }
    
    private int ingresaDerHorizontal(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y =  y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
    	tablero.addLast(entrada.remove(n));
    	return y;
    }
    private int ingresaDerHorizontalRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	//rota imagen 
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.addLast(entrada.remove(n));
    	return y;
    }
    
    private int ingresaDer_Izq(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x - entrada.get(n).img_ficha.getWidth();
    	entrada.get(n).visible = false;
		entrada.get(n).img_ficha = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha.getImage(),180),x,y);
		tablero.addLast(entrada.remove(n));
		return x;
    }
    
    private int ingresaDer_IzqRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	x = x - entrada.get(n).img_ficha.getWidth();
    	entrada.get(n).img_ficha.setLocation(x, y);
		int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
		entrada.get(n).visible = false;
		tablero.addLast(entrada.remove(n));
    	return x;
    }
    
    public int ingresaDer_IzqHorizontal(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	entrada.get(n).img_ficha.setLocation(x, y);
		entrada.get(n).visible = false;
    	tablero.addLast(entrada.remove(n));
    	return y;
    }
    
    public int ingresaDer_IzqHorizontalRotando(LinkedList<Ficha>tablero, ArrayList<Ficha> entrada, int x, int y, int n) {
    	y = y + tablero.getLast().img_ficha.getHeight();
    	entrada.get(n).img_ficha_turned_90 = new Sprite( ImageUtil.rotate(entrada.get(n).img_ficha_turned_90.getImage(),180),x,y);
    	entrada.get(n).img_ficha.setImage(entrada.get(n).img_ficha_turned_90.getImage());
    	//rota datos
    	int temp = entrada.get(n).der;
    	entrada.get(n).der = entrada.get(n).izq;
    	entrada.get(n).izq = temp;
    	entrada.get(n).img_ficha.setLocation(x, y);
    	entrada.get(n).visible = false;
		tablero.addLast(entrada.remove(n));
    	return y;
    }

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		GameLoader game = new GameLoader();
		game.setup(new Execute(), new Dimension(1200,700), false);
		game.start();
		System.out.println("Hola perro mundo");
	}

}
