package barca;

public class Barca extends Thread {

	private static final int C = 4;
	private int numAndroid = 0;
	private int numIphone = 0;
	private Boolean viaje=false;
	private Boolean subida=true;

	/**
	 * Un estudiante con móvil android llama a este método cuando quiere cruzar el
	 * río. Debe esperarse a montarse en la barca de forma segura, y a llegar a la
	 * otra orilla del antes de salir del método
	 * 
	 * @param id
	 *            del estudiante android que llama al método
	 * @throws InterruptedException
	 */

	public synchronized void android(int id) throws InterruptedException {
		while (!subida || (numAndroid + numIphone == C) || numIphone == 3 || (numAndroid == 2 && numIphone == 1))
			wait();
		numAndroid++;
		System.out.println("El android " + id + " se sube a la barca.");
		// la barca está llena y podemos viajar
		if (numAndroid + numIphone == C) {
			subida=false;
			viaje=true;
			System.out.println("Llegamos a la orilla");			
			notifyAll();
		} else {// tenemos que esperar a que termine el viaje para desbloquearnos
			while(!viaje)wait();
		}
		numAndroid--;
		if(numAndroid==0 && numIphone==0) {
			viaje=false;
			subida=true;
			notifyAll();
		}

	}

	/**
	 * Un estudiante con móvil android llama a este método cuando quiere cruzar el
	 * río. Debe esperarse a montarse en la barca de forma segura, y a llegar a la
	 * otra orilla del antes de salir del método
	 * 
	 * @param id
	 *            del estudiante android que llama al método
	 * @throws InterruptedException
	 */

	public synchronized void iphone(int id) throws InterruptedException {
		while (!subida || (numAndroid + numIphone == C) || numAndroid == 3 || (numIphone == 2 && numAndroid == 1))
			wait();
		numIphone++;
		System.out.println("El iphone " + id + " se sube a la barca.");
		// la barca está llena y podemos viajar
		if (numAndroid + numIphone == C) {
			subida=false;			
			System.out.println("Llegamos a la orilla.");
			viaje=true;
			notifyAll();
		} else {// tenemos que esperar a que termine el viaje para desbloquearnos
			while(!viaje)wait();
		}
		numIphone--;
		if(numAndroid==0 && numIphone==0) {
			viaje=false;
			subida=true;
			notifyAll();
		}

	}

}

// CS-IPhone: no puede subirse a la barca hasta que está en modo subida, haya
// sitio
// y no sea peligroso

// CS-Android: no puede subirse a la barca hasta que está en modo subida, haya
// sitio
// y no sea peligroso

// CS-Todos: no pueden bajarse de la barca hasta que haya terminado el viaje