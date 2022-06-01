package Junio18.pizza;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
	
	private int numTrozoZa = 0;
	private boolean pedido = false;
	private boolean servido = false;
	private boolean pagado = false;

	private Lock l = new ReentrantLock();
	private Condition pizzero = l.newCondition();
	private Condition estudiantes = l.newCondition();
	
	
	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	public void nuevaRacion(int id) throws InterruptedException{
		l.lock();
		try{
			if(numTrozoZa == 0 ){
				if(!pedido){
					servido = false;
					pedido = true;
					System.out.println("Estudiante " + id + " pide pizza.");
					pizzero.signal();
					while(!servido)
						estudiantes.await();
					pagado = true;
					pedido = false;
					System.out.println("Estudiante "+ id +" ha pagado al pizzero");
					pizzero.signal();
					numTrozoZa--;
					System.out.println("Estudiante " + id + " se ha comido un trozo. Quedan " + numTrozoZa);
				}else{
					System.out.println("Estudiante " + id + " espera trozo.");
					while(!servido && pedido)
						estudiantes.await();
				}
			}else{
				numTrozoZa--;
				System.out.println("Estudiante " + id + " se ha comido un trozo. Quedan " + numTrozoZa);
			}
			
		}finally{
			l.unlock();
		}
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public void nuevoCliente() throws InterruptedException{
		l.lock();
		try{
			System.out.println("El pizzero entrega la pizza.");
			numTrozoZa = 8;
			servido = true;
			estudiantes.signalAll();
			System.out.println("El pizzero espera el pago.");
			while(!pagado)
				pizzero.await();
			pagado = false;
			System.out.println("El pizzero ha sido pagado.");
		}finally{
			l.unlock();
		}
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public void nuevaPizza() throws InterruptedException{
		l.lock();
		try{
			System.out.println("El pizzero espera un pedido");
			while(!pedido)
				pizzero.await();
		}finally{
			l.unlock();
		}
	}

}
