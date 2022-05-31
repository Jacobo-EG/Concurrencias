package recursos;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Control {
	private int numRec;
	private List<Integer> esperando = new LinkedList<Integer>();

	private Lock l = new ReentrantLock();
	private Condition colaEntrada = l.newCondition();
	private Condition esperoRecursos = l.newCondition();

	private boolean esperandoRecursosB = false;

	public Control(int num) {
		this.numRec = num;
	}

	public void qRecursos(int id, int num) throws InterruptedException {
		try {
			l.lock();
			System.out.println("Proceso " + id + " pide turno y pide " + num + " recursos.");

			esperando.add(id);
			while (esperando.get(0) != id) // Si no me toca, me duermo
				colaEntrada.await();

			while (num > numRec) { // Si no hay recursos, me duermo a la espera
				esperandoRecursosB = true;
				System.out.println("	El proceso " + id + " espera por " + num + " recursos. Hay: " + numRec);
				esperoRecursos.await();
			}
			esperandoRecursosB = false;
			// es mi turno para coger recursos.
			numRec = numRec - num;
			System.out.println("		El proceso " + id + " ha cogido " + num + " recursos. Ahora quedan: " + numRec);

			esperando.remove(0);
			if (esperandoRecursosB)
				esperoRecursos.signal(); // Hay proceso esperando
			else if (esperando.size() > 0)
				colaEntrada.signalAll(); // Hay alguien esperando

		} finally {
			l.unlock();
		}

	}

	public void libRecursos(int id, int num) {
		try {
			l.lock();
			numRec = numRec + num;
			System.out.println(
					"		El proceso " + id + " ha liberado " + num + " recursos. Recursos totales disponibles: "
							+ numRec);
			if (esperandoRecursosB)
				esperoRecursos.signal(); // Hay proceso esperando
			else if (esperando.size() > 0)
				colaEntrada.signalAll(); // Hay alguien esperando
		} finally {
			l.unlock();
		}

	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
