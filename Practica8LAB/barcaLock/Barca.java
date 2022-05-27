package barcaLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Barca extends Thread {

	private static final int C = 4;

	private Lock l = new ReentrantLock(true);
	private Condition subir = l.newCondition();
	private Condition bajar = l.newCondition();

	private int numIph = 0;
	private int numAnd = 0;
	private boolean puedoSubir = true;
	private boolean puedoBajar = false;

	/**
	 * Un estudiante con m�vil android llama a este m�todo cuando quiere cruzar el
	 * r�o. Debe esperarse a montarse en la barca de forma segura, y a llegar a la
	 * otra orilla del antes de salir del m�todo
	 * 
	 * @param id
	 *           del estudiante android que llama al m�todo
	 * @throws InterruptedException
	 */

	public void android(int id) throws InterruptedException {

		l.lock();
		try{
			while(!puedoSubir || numIph >= (C/2+1)  || (numAnd == 2 && numIph == 1))
				subir.await();
			numAnd++;
			System.out.println("El android " + id + " se sube a la barca.");

			while(!puedoBajar)
				bajar.await();

			System.out.println("Llegamos a la orilla.");
			if((numIph+numAnd) == C){
				puedoBajar = true;
				bajar.signalAll();
			}else if((numAnd+numIph) == 1){
				puedoSubir = true;
				puedoBajar = false;
				subir.signalAll();
			}
			
			numAnd--;
		}finally{
			l.unlock();
		}


	}

	/**
	 * Un estudiante con m�vil android llama a este m�todo cuando quiere cruzar el
	 * r�o. Debe esperarse a montarse en la barca de forma segura, y a llegar a la
	 * otra orilla del antes de salir del m�todo
	 * 
	 * @param id
	 *           del estudiante android que llama al m�todo
	 * @throws InterruptedException
	 */

	public void iphone(int id) throws InterruptedException {
		try{
			while(!puedoSubir || numAnd >= (C/2+1) || (numAnd == 1 && numIph == 2))
				subir.await();

			numIph++;
			System.out.println("El iphone " + id + " se sube a la barca.");

			while(!puedoBajar)
				bajar.await();
	
			System.out.println("Llegamos a la orilla.");

			if((numIph+numAnd) == C){
				puedoBajar = true;
				bajar.signalAll();
			}else if((numAnd+numIph) == 1){
				puedoSubir = true;
				puedoBajar = false;
				subir.signalAll();
			}

			numIph--;
		}finally{
			l.unlock();
		}

	}

}

// CS-IPhone: no puede subirse a la barca hasta que est� en modo subida, haya
// sitio
// y no sea peligroso

// CS-Android: no puede subirse a la barca hasta que est� en modo subida, haya
// sitio
// y no sea peligroso

// CS-Todos: no pueden bajarse de la barca hasta que haya terminado el viaje