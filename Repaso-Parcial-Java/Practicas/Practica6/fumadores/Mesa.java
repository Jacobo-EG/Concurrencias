package fumadores;

import java.util.concurrent.*;

public class Mesa {

	// esta es una implementación pasiva para los fumadores
	// los van a despertar cuando tengan que fumar.

	private Semaphore agente = new Semaphore(1,true);
	private Semaphore[] fumador = new Semaphore[3];

	public Mesa() {
		for(int i = 0; i < 3; i++)
			fumador[i] = new Semaphore(0,true);
	}

	public void qFumar(int id) throws InterruptedException {
		fumador[id].acquire();
		System.out.println("Fumador " + id + " coge los ingredientes");

	}

	public void finFumar(int id) {
		System.out.println("Fumador " + id + " ha terminado de fumar");
		agente.release();

	}

	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		agente.acquire();
		System.out.print("El agente ha puesto los ingredientes ");
		fumador[ing].release();
	}

}

// CS-Fumador i: No puede fumar hasta que el fumador anterior no ha terminado
// de fumar y sus ingredientes están sobre la mesa
// CS-Agente: no puede poner nuevos ingredientes hasta que el fumador anterior
// no ha terminado de fumar
