package pajaritos;

import java.util.concurrent.*;

public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos = 0; // puede tener de 0 a B bichitos

	private Semaphore protectVar = new Semaphore(1,true);
	// private Semaphore[] padre = new Semaphore[2];
	// private Semaphore[] bebe =  new Semaphore[10];

	private Semaphore suelta = new Semaphore(1,true);
	private Semaphore coge = new Semaphore(0,true);

	public Nido(){
		// for(int i = 0;i < 10; i++)
		// 	bebe[i] = new Semaphore(0,true);

		// for(int i = 0;i < 2; i++)
		// 	padre[i] = new Semaphore(0,true);
	}

	public void come(int id) throws InterruptedException {

		coge.acquire();
		protectVar.acquire();

		if(bichitos == B)
			suelta.release();

		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);

		if(bichitos > 0){
			protectVar.release();
			coge.release();
		}else{
			protectVar.release();
		}
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		suelta.acquire();
		protectVar.acquire();
		if(bichitos == 0){
			coge.release();
		}
		bichitos++;
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		if(bichitos < B){
			protectVar.release();
			suelta.release();
		}else{
			protectVar.release();
		}
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
