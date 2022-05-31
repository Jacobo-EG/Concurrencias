package mrusa;

import java.util.concurrent.locks.*;

public class Coche implements Runnable {
	private int tam;

	public Coche(int tam) {
		this.tam = tam;
	}

	public void subir(int id) throws InterruptedException {
		// id del pasajero que se sube al coche

	}

	public void bajar(int id) throws InterruptedException {
		// id del pasajero que se baja del coche

	}

	private void esperaLleno() throws InterruptedException {
		// el coche espera a que este lleno para dar una vuelta

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
