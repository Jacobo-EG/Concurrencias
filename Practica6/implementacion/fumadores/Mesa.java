package fumadores;

import java.util.concurrent.*;

public class Mesa {

	// esta es una implementación pasiva para los fumadores
	// los van a despertar cuando tengan que fumar.
	Semaphore[] fuma = new Semaphore[3];
	Semaphore permisoAgente = new Semaphore(1,true);

	public Mesa() {
		for(int i=0;i<fuma.length;i++){
			fuma[i] = new Semaphore(0,true);
		}
	}

	public void qFumar(int id) throws InterruptedException {
		fuma[id].acquire();
		System.out.println("Fumador " + id + " coge los ingredientes");

	}

	public void finFumar(int id) {
		permisoAgente.release();
		System.out.println("Fumador " + id + " ha terminado de fumar");

	}

	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		permisoAgente.acquire();
		System.out.print("El agente ha puesto los ingredientes ");
		fuma[ing].release();

	}

}

// CS-Fumador i: No puede fumar hasta que el fumador anterior no ha terminado
// de fumar y sus ingredientes están sobre la mesa
// CS-Agente: no puede poner nuevos ingredientes hasta que el fumador anterior
// no ha terminado de fumar
