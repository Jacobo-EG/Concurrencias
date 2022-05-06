package barrera;

import java.util.concurrent.*;

public class Barrera implements IBarrera {

	private int tam; // Cuantos trabajadores hay en total
	private int num = 0; // Cuantos han terminado en esta iteración
	private Semaphore scNum = new Semaphore(1, true); // Control SC variable NUM
	private Semaphore continua = new Semaphore(0, true); // Permiso para continuar, al principio nadie tiene permiso

	public Barrera(int tam) {
		this.tam = tam;
	}

	public void terminoIteracion(int id) throws InterruptedException {
		scNum.acquire();
		System.out.println("Ha terminado " + id + " y en total son " + num);
		if(num >= tam-1){
			num = 0;
			scNum.release();
			continua.release(tam-1);
		}else{
			num++;
			scNum.release();
			continua.acquire();
		}
		
	}

}