package recursos;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Control {
	private int NUM;// numero total de recursos
	private int numRec = NUM;
	private List<Integer> esperando = new LinkedList<Integer>();
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore esperaTurno = new Semaphore(0, true);
	private Semaphore esperaRecursos = new Semaphore(0, true);

	

	public Control(int num) {
		this.NUM = num;
		this.numRec = num;
	}

	public void qRecursos(int id, int num) throws InterruptedException {
		mutex.acquire();
		System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);
		esperando.add(id);
		while (esperando.get(0) != id) {
			mutex.release();
			esperaTurno.acquire();
			mutex.acquire();
		}
		while (num > numRec) {
			mutex.release();
			esperaRecursos.acquire();
			mutex.acquire();
		}
		numRec -= num;	
		System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);
		esperando.remove(0);
		mutex.release();
		esperaTurno.release();
	}

	public void libRecursos(int id, int num) throws InterruptedException {
		mutex.acquire();
		numRec += num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		mutex.release();
		esperaRecursos.release();
	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
