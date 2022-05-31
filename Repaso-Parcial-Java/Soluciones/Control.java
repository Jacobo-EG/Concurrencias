package recursos;

import java.util.LinkedList;
import java.util.List;

public class Control {
	private int numRec;
	private List<Integer> esperando = new LinkedList<Integer>();

	public Control(int num) {
		this.numRec = num;
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException {

		System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);

		esperando.add(id);
		while (esperando.get(0) != id) // Si no me toca, me duermo
			wait();

		while (num > numRec) { // Si no hay recursos, me duermo a la espera
			System.out.println("El proceso " + id + " espera en la cola.");
			wait();
		}
		// es mi turno para coger recursos.
		numRec = numRec - num;
		System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);

		esperando.remove(0);
		if (esperando.size() > 0) // Mejora, si no hay gente esperando, no despierto a nadie.
			notifyAll();

	}

	public synchronized void libRecursos(int id, int num) {
		numRec = numRec + num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		notifyAll(); // Cuando salgo, puede haber alguno dermido por no tener recursos o esperando
	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
