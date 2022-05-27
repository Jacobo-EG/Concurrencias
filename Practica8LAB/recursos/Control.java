package recursos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.apple.laf.resources.aqua;

public class Control {
	private int NUM;	// numero total de recursos
	private int numRec;

	private boolean esperando = false;

	private List<int> lista = new LinkedList<>();

	public Control(int num) {
		this.NUM = num;
		this.numRec = num;
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException {

		System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);

		

		// while(esperando)
		// 	wait();

		// if(num > numRec){
		// 	lista.addLast(id);
		// 	esperando = true;
		// }

		// while(num > numRec){
		// 	wait();
		// }

		// numRec -= num;
		// esperando = false;
			
		System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);

	}

	public synchronized void libRecursos(int id, int num) {

		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);

	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
