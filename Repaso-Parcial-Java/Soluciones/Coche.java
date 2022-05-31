package mrusa;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class Coche implements Runnable {
	private int tam;
	private int numPasajerosCoche = 0; // Numero de pasajeros en el vagón.
	private Semaphore scNuSemaphore = new Semaphore(1, true);
	private int numPasajerosEspera = 0; // Numero de pasajeros esperando en cola
	private Semaphore scnumPasajerosEspera = new Semaphore(1, true);

	private Semaphore[] asientos; // Semaforo con los asientos

	private Semaphore colaEspera = new Semaphore(0, true); // Cola en la que esperan los Pasajeros sin asiento

	private Semaphore muevete = new Semaphore(0, true); // Indica cuando el tren se mueve.

	public Coche(int tam) {
		this.tam = tam;
		asientos = new Semaphore[tam];
		for (int i = 0; i < asientos.length; i++) {
			asientos[i] = new Semaphore(0, true);
		}
	}

	public void subir(int id) throws InterruptedException {
		// id del pasajero que se sube al coche
		scNuSemaphore.acquire();
		while (numPasajerosCoche >= tam) {
			scNuSemaphore.release();
			scnumPasajerosEspera.acquire();
			numPasajerosEspera++;
			System.out.println("Pasajero " + id + " espera con " + numPasajerosEspera + " pasajeros en la cola");
			scnumPasajerosEspera.release();
			colaEspera.acquire();
			scNuSemaphore.acquire();
		}
		int copia = numPasajerosCoche;
		numPasajerosCoche++;
		System.out.println("Pasajero " + id + " se sube al tren con " + numPasajerosCoche + " pasajeros");
		if (numPasajerosCoche == tam)
			muevete.release();
		scNuSemaphore.release();
		asientos[copia].acquire();
	}

	public void bajar(int id) throws InterruptedException {

		scnumPasajerosEspera.acquire();
		System.out.println("Pasajero " + id + " se baja del tren");
		if (numPasajerosEspera > 0) {
			numPasajerosEspera--;
			System.out.println("En la cola de espera quedan " + numPasajerosEspera + " pasajeros en la cola");
			colaEspera.release();
		} else {
			System.out.println(" 1 En la cola de espera quedan " + numPasajerosEspera + " pasajeros en la cola");
		}
		scnumPasajerosEspera.release();

	}

	private void esperaLleno() throws InterruptedException {
		muevete.acquire();
		System.out.println("El tren comienza a moverse");
		System.out.println("El tren PARA!");
		numPasajerosCoche = 0;
		for (int i = 0; i < asientos.length; i++) {
			asientos[i].release();
		}

	}

	public void run() {
		while (true)
			try {
				this.esperaLleno();
				Thread.sleep(200);

			} catch (InterruptedException ie) {
			}

	}
}
// tam pasajeros se suben al coche->el coche da un viaje
// ->tam pasajeros se bajan del coche->......

// CS-Coche: Coche no se pone en marcha hasta que no está lleno
// CS-Pas1: Pasajero no se sube al coche hasta que no hay sitio para el.
// CS-Pas2: Pasajero no se baja del coche hasta que no ha terminado el viaje
