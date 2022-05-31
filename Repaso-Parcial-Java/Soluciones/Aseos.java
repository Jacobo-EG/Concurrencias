package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {

	private int numClientes = 0;
	private Semaphore scnumClientes = new Semaphore(1, true);

	private Semaphore esperoEntrar = new Semaphore(0, true);
	private Semaphore esperoLimpiar = new Semaphore(1, true);

	private boolean limpiezaEnLaPuerta = false;
	private Semaphore sclimpiezaEnLaPuerta = new Semaphore(1, true);

	private boolean esperandoEntrar = false;

	private Semaphore colaEntrada = new Semaphore(1, true);

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o está esperando para poder limpiar los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		colaEntrada.acquire(); // Donde se duermen todos mientras se limpia, a excepción del primero
		sclimpiezaEnLaPuerta.acquire();
		while (limpiezaEnLaPuerta) { // Si el equipo de limpieza quiere entrar, me duermo
			esperandoEntrar = true; // notifico que hay alguien esperando para entrar, cuando salga el equipo, puede
									// liberar esto
			sclimpiezaEnLaPuerta.release();
			System.out.println("El cliente " + id + " esperando para entrar, dentro quedan " + numClientes);
			esperoEntrar.acquire();
			sclimpiezaEnLaPuerta.acquire();
		}
		scnumClientes.acquire();
		numClientes++;
		if (numClientes == 1)
			esperoLimpiar.acquire(); // Si hay alguien dentro, no dejo pasar a limpiar

		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: " + numClientes);
		scnumClientes.release();
		sclimpiezaEnLaPuerta.release();
		colaEntrada.release();

	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		scnumClientes.acquire();
		numClientes--;
		if (numClientes == 0)
			esperoLimpiar.release();
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo " + numClientes);
		scnumClientes.release();

	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que
	 * no
	 * haya ningún cliente.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		System.out.println("		El equipo de limpieza quiere entrar.");
		sclimpiezaEnLaPuerta.acquire();
		limpiezaEnLaPuerta = true;
		sclimpiezaEnLaPuerta.release();
		esperoLimpiar.acquire(); // Me bloqueo si queda alguien dentro

		System.out.println("		El equipo de limpieza está trabajando.");
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		sclimpiezaEnLaPuerta.acquire();
		limpiezaEnLaPuerta = false;
		if (esperandoEntrar) {
			esperandoEntrar = false;
			esperoEntrar.release();
		}

		sclimpiezaEnLaPuerta.release();
		esperoLimpiar.release();
		System.out.println("El equipo de limpieza ha terminado.");

	}
}
