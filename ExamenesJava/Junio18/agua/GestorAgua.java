package Junio18.agua;


import java.util.concurrent.*;


public class GestorAgua {

	private boolean primero = true;
	private Semaphore protectVariables = new Semaphore(1,true);
	private Semaphore oX = new Semaphore(1,true);
	private Semaphore hI = new Semaphore(0,true);
	private Semaphore hI2 = new Semaphore(0,true);
	
	public void hListo(int id) throws InterruptedException{
		protectVariables.acquire();
		if(primero){
			primero = false;
			protectVariables.release();
			System.out.println("Primera de H lista");
			hI.acquire();
		}else{
			primero = true;
			protectVariables.release();
			System.out.println("Segunda de H lista");
			hI2.acquire();
			oX.release();
		}
		
	}
	
	public void oListo(int id) throws InterruptedException{ 
		System.out.println("O lista");
		oX.acquire();
		hI.release();
		hI2.release();
	}
}