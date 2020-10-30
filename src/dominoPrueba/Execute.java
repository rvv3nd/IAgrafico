package dominoPrueba;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.background.*;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseAudioRenderer;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.object.*;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.*;
import java.util.*;

public class Execute extends Game{
	ArrayList<Ficha> fichas = new ArrayList<Ficha>();
	ArrayList<Ficha> playerFichas = new ArrayList<Ficha>();
	ArrayList<Ficha> pcFichas = new ArrayList<Ficha>();
	LinkedList<Ficha> enJuego = new LinkedList<Ficha>();
	Background  background;
	Sprite s1,t1;
	boolean text= false;
	int clickX,clickY;
	int in; //recibe isValid para acomodar las fichas al inicioo al final 
	int rand_index, inicial=300; //inicial de la posicion de las fichas del jugador
	int newX = 600; //x y y para las fichas en juego
	int leftX = newX;
	int rightX = leftX;
	int newY = 200;
	int temp;

	
	public void initResources() {

		s1 = new Sprite(getImage("images/background.jpg"));
	    s1.setBackground(background);
		
        // initialization of game variables
		Random random = new Random();
		//get all pieces into an array 
		
		t1 = new Sprite(getImage("images/tiraficha.png"),600,100);
		
		ArrayList<BufferedImage> imagesArray = new ArrayList<BufferedImage>();
				imagesArray.add(getImage("images/piezas/cero-cero.jpg"));
				imagesArray.add(getImage("images/piezas/cero-uno.jpg"));
				imagesArray.add(getImage("images/piezas/cero-dos.jpg"));
				imagesArray.add(getImage("images/piezas/cero-tres.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/cero-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cero-seis.jpg"));
				imagesArray.add(getImage("images/piezas/uno-uno.jpg"));
				imagesArray.add(getImage("images/piezas/uno-dos.jpg"));
				imagesArray.add(getImage("images/piezas/uno-tres.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/uno-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/uno-seis.jpg"));
				imagesArray.add(getImage("images/piezas/dos-dos.jpg"));
				imagesArray.add(getImage("images/piezas/dos-tres.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/dos-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/dos-seis.jpg"));
				imagesArray.add(getImage("images/piezas/tres-tres.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/tres-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/tres-seis.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cuatro.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cuatro-seis.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-cinco.jpg"));
				imagesArray.add(getImage("images/piezas/cinco-seis.jpg"));
				imagesArray.add(getImage("images/piezas/seis-seis.jpg"));
			
			//put every image into an object Ficha
			int izq = 0;
			int der = 0;
			for (BufferedImage image : imagesArray) {
				fichas.add(new Ficha(izq,der,new Sprite (image)));
				//lleva el control de los numeros de cada ficha 
				
				der++;
				if(der>6) {
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
	    		if( clickX >= (int) playerFichas.get(0).img_ficha.getX() &&
	    			clickX <= (int) playerFichas.get(0).img_ficha.getX() + playerFichas.get(0).img_ficha.getWidth() &&
	    			clickY >= (int) playerFichas.get(0).img_ficha.getY() &&
	    			clickY <= (int) playerFichas.get(0).img_ficha.getY() + playerFichas.get(0).img_ficha.getHeight()){
	    			
	    			//selecciono la primer ficha 
	    			mover(elapsedTime,0);
	    		}
	    		else if(clickX >= (int) playerFichas.get(1).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(1).img_ficha.getX() + playerFichas.get(1).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(1).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(1).img_ficha.getY() + playerFichas.get(1).img_ficha.getHeight()) {
	    			mover(elapsedTime,1);
	    		}
	    		else if(clickX >= (int) playerFichas.get(2).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(2).img_ficha.getX() + playerFichas.get(2).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(2).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(2).img_ficha.getY() + playerFichas.get(2).img_ficha.getHeight()) {
	    			mover(elapsedTime,2);
	    		}
	    		else if(clickX >= (int) playerFichas.get(3).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(3).img_ficha.getX() + playerFichas.get(3).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(3).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(3).img_ficha.getY() + playerFichas.get(3).img_ficha.getHeight()) {
	    			mover(elapsedTime,3);
	    		}
	    		else if(clickX >= (int) playerFichas.get(4).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(4).img_ficha.getX() + playerFichas.get(4).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(4).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(4).img_ficha.getY() + playerFichas.get(4).img_ficha.getHeight()) {
	    			mover(elapsedTime,4);
	    		}
	    		else if(clickX >= (int) playerFichas.get(5).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(5).img_ficha.getX() + playerFichas.get(5).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(5).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(5).img_ficha.getY() + playerFichas.get(5).img_ficha.getHeight()) {
	    			mover(elapsedTime,5);
	    		}
	    		else if(clickX >= (int) playerFichas.get(6).img_ficha.getX() &&
	        			clickX <= (int) playerFichas.get(6).img_ficha.getX() + playerFichas.get(6).img_ficha.getWidth() &&
	        			clickY >= (int) playerFichas.get(6).img_ficha.getY() &&
	        			clickY <= (int) playerFichas.get(6).img_ficha.getY() + playerFichas.get(6).img_ficha.getHeight()) {
	    			mover(elapsedTime,6);
	    		}else {
	    			//error sound
	    			System.out.println("No valido we");
	    		}
    		}catch(Exception e) {
    			System.out.println("Ya no existe ficha en esas coordenadas");
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
	    
    }

    public void render(Graphics2D g) {
        // rendering to the screen
    	s1.render(g);
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
    
    
    private void rotar(Ficha ficha) {
    	int temp = ficha.der;
    	ficha.der = ficha.izq;
    	ficha.izq = temp;
    	//falta invertir la imagen
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
				leftX = leftX-playerFichas.get(n).img_ficha.getWidth();
				playerFichas.get(n).img_ficha.setLocation(leftX, newY);
				playerFichas.get(n).visible = false;
				enJuego.addFirst(playerFichas.remove(n)); // saca la ficha para agregarla al inicio de las que estan en juego, o sea se agrega a la izq
				break;
			case 2: 
				System.out.println("Inserta a la izquierda rotando");
				leftX = leftX-playerFichas.get(n).img_ficha.getWidth();
				playerFichas.get(n).img_ficha.setLocation(leftX, newY);
				playerFichas.get(n).visible = false;
				//rotar(playerFichas.get(n));
				temp = playerFichas.get(n).der;
		    	playerFichas.get(n).der = playerFichas.get(n).izq;
		    	playerFichas.get(n).izq = temp;
				enJuego.addFirst(playerFichas.remove(n)); // saca la ficha para agregarla al inicio y además tiene que rotar la imagen
				break;
			case 3:
				System.out.println("Inserta a la derecha");
				rightX = rightX+playerFichas.get(n).img_ficha.getWidth();
				playerFichas.get(n).img_ficha.setLocation(rightX, newY);
				playerFichas.get(n).visible = false;
				enJuego.addLast(playerFichas.remove(n)); // saca la ficha para agregarla al inicio de las que estan en juego, o sea se agrega a la izq
				break;
			case 4:
				System.out.println("Inserta a la derecha rotando");
				rightX = rightX+playerFichas.get(n).img_ficha.getWidth();
				playerFichas.get(n).img_ficha.setLocation(rightX, newY);
				playerFichas.get(n).visible = false;
				//rotar(playerFichas.get(n));
				temp = playerFichas.get(n).der;
		    	playerFichas.get(n).der = playerFichas.get(n).izq;
		    	playerFichas.get(n).izq = temp;
				enJuego.addLast(playerFichas.remove(n)); // saca la ficha para agregarla al inicio y además tiene que rotar la imagen
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

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		GameLoader game = new GameLoader();
		game.setup(new Execute(), new Dimension(1200,700), false);
		game.start();
		System.out.println("Hola perro mundo");
	}

}
