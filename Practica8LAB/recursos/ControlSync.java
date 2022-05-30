

import java.util.LinkedList;
import java.util.List;


public class ControlSync implements Control{
	private int NUM;	// numero total de recursos
	private int numRec;


	// private Lock l = new ReentrantLock();
	private List<Integer> lista = new LinkedList<Integer>();

	public ControlSync(int num) {
		this.NUM = num;
		this.numRec = num;
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException {

	
			System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);

			lista.add(id);
			
			while(num > numRec || lista.indexOf(id) != 0)
				wait();

			numRec -= num;
			lista.remove(0);
			System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);
		
		

	}

	public synchronized void libRecursos(int id, int num) {
		numRec += num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		notifyAll();
	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
