package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {

	private Semaphore s = new Semaphore(1,true);

	//Para tratar de hacerlo justo
	private Semaphore limpiezaQuiereEntrar = new Semaphore(1,true);

	private Semaphore nClientesPermiso = new Semaphore(1,true);
	private int nClientes = 0;

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o
	 * está esperando para poder limpiar los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		limpiezaQuiereEntrar.acquire();
		nClientesPermiso.acquire();

		if(nClientes == 0){
			nClientesPermiso.release();
			s.acquire();
			nClientesPermiso.acquire();
		}

		nClientes++;

		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: "+nClientes);

		nClientesPermiso.release();
		limpiezaQuiereEntrar.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		nClientesPermiso.acquire();
		if(nClientes == 1){
			s.release();
		}
		nClientes--;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: "+nClientes);
		nClientesPermiso.release();
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
		limpiezaQuiereEntrar.acquire();

		s.acquire();
		System.out.println("El equipo de limpieza está trabajando.");

		limpiezaQuiereEntrar.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("El equipo de limpieza ha terminado.");
		s.release();
	}
}
