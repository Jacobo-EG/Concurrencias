package barcaSync;

import java.nio.file.StandardWatchEventKinds;

public class Barca extends Thread {

	private static final int C = 4;
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

	public synchronized void android(int id) throws InterruptedException {


		while(!puedoSubir || numIph >= (C/2+1)  || (numAnd == C/2 && numIph == 1))
			wait();

		numAnd++;
		System.out.println("El android " + id + " se sube a la barca.");

		while(!puedoBajar)
			wait();

		if((numIph+numAnd) == C){
			puedoBajar = true;
			notifyAll();
		}else if((numAnd+numIph) == 1){
			puedoSubir = true;
			puedoBajar = false;
			notifyAll();
		}
			
		numAnd--;

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

	public synchronized void iphone(int id) throws InterruptedException {

		while(!puedoSubir || numAnd >= (C/2+1) || (numAnd == 1 && numIph == C/2))
			wait();

		numIph++;
		System.out.println("El iphone " + id + " se sube a la barca.");

		while(!puedoBajar)
			wait();
		
		System.out.println("Llegamos a la orilla.");

		if((numIph+numAnd) == C){
			puedoBajar = true;
			notifyAll();
		}else if((numAnd+numIph) == 1){
			puedoSubir = true;
			puedoBajar = false;
			notifyAll();
		}

		numIph--;
	}

}

// CS-IPhone: no puede subirse a la barca hasta que est� en modo subida, haya
// sitio
// y no sea peligroso

// CS-Android: no puede subirse a la barca hasta que est� en modo subida, haya
// sitio
// y no sea peligroso

// CS-Todos: no pueden bajarse de la barca hasta que haya terminado el viaje