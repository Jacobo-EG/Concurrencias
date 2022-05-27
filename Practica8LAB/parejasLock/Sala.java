package parejasLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Sala {

	private boolean hayMujer = false;
	private boolean hayHombre = false;
	private boolean puerta = true;

	private Lock l = new ReentrantLock(true);
	private Condition hombre = l.newCondition();
	private Condition mujer = l.newCondition();


	/**
	 * un hombre llega a la sala para formar una pareja si ya hay otra mujer en la
	 * sala o si a�n no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public void llegaHombre(int id) throws InterruptedException {

		l.lock();

		try{
			while(!puerta)
				hombre.await();
		
			hayHombre = true;

			System.out.println("El hombre " + id + " espera en la sala.");
		
			if(hayMujer){
				mujer.signalAll();
			}else{
				while(!hayMujer)
					hombre.await();
			}

			System.out.println("El hombre " + id + " ha tenido una cita.");

			System.out.println("El hombre " + id + " sale de la sala.");

			hayHombre = false;
			puerta = true;
		}finally{
			l.unlock();
		}
		
	}

	/**
	 * una mujer llega a la sala para formar una pareja debe esperar si ya hay otra
	 * mujer en la sala o si aún no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaMujer(int id) throws InterruptedException {

		l.lock();

		try{
			while(!puerta)
				mujer.await();

			hayMujer = true;

			System.out.println("La mujer " + id + " espera en la sala.");

			if(hayHombre){
				hombre.signalAll();
			}else{
				while(!hayHombre)
					mujer.await();
			}

			System.out.println("La mujer  " + id + " ha tenido una cita.");

			System.out.println("La mujer " + id + " sale de la sala.");

			hayMujer=false;
			puerta = false;
		}finally{
			l.unlock();
		}

	}
}
