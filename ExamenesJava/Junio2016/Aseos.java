package Junio2016;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aseos {
	
	private boolean limpiando = false;
	private int numCliente = 0;

	private Lock l = new ReentrantLock();
	Condition limpieza = l.newCondition();
	Condition clientes = l.newCondition();
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException{
		l.lock();
		try{
			while(limpiando)
				clientes.await();
			numCliente++;
		}finally{
			l.unlock();
		}
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public void salgoAseo(int id){
		l.lock();
		try{
			numCliente--;
			if(numCliente == 0)
				limpieza.signal();
		}finally{
			l.unlock();
		}
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * @throws InterruptedException
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException{
		l.lock();
		try{
			while(numCliente>0)
				limpieza.await();
			limpiando = true;
		}finally{
			l.unlock();
		}
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza(){
    	l.lock();
		try{
			limpiando = false;
			clientes.signalAll();
		}finally{
			l.unlock();
		}
	}
}
