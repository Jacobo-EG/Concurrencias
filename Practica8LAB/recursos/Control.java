package recursos;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.apple.laf.resources.aqua;

public class Control {
	private int NUM;	// numero total de recursos
	private int numRec;

	private boolean esperando = false;

	private Lock l = new ReentrantLock();
	private List<Integer> lista = new LinkedList<Integer>();

	public Control(int num) {
		this.NUM = num;
		this.numRec = num;
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException {

	
			System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);

			// while(esperando)
			// 		wait();

			
			lista.addLast(id);
			

			while((num > numRec) || lista.indexOf(id) != 0)
				wait();

			lista.remove(0);
			numRec -= num;
			System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);
		
		

	}

	public void libRecursos(int id, int num) {
		numRec += num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		notifyAll();

	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
