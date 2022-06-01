package Junio2016;

import java.util.concurrent.Semaphore;

public class AseosSemp implements Aseos{
    private boolean quiereLimpiar = false;
	private int numCliente = 0;
    private int clientesEsp = 0;

    private Semaphore protectVar = new Semaphore(1,true);
    private Semaphore colaClientes = new Semaphore(0,true);
    private Semaphore colaLimpieza = new Semaphore(0,true);

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException{
		protectVar.acquire();

		if(quiereLimpiar){
            clientesEsp++;
            protectVar.release();
			colaClientes.acquire();
            protectVar.acquire();
            clientesEsp--;
        }

        if(clientesEsp > 0)
            colaClientes.release();

		numCliente++;
		
        protectVar.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException{
		protectVar.acquire();
		numCliente--;
		if(numCliente == 0 && quiereLimpiar)
			colaLimpieza.release();
		protectVar.release();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * @throws InterruptedException
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException{
		protectVar.acquire();
			quiereLimpiar = true;
			if(numCliente > 0){
                protectVar.release();
				colaLimpieza.acquire();
            }else{
                protectVar.release();
            }
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
     * @throws InterruptedException
	 * 
	 * 
	 */
    public void saleEquipoLimpieza() throws InterruptedException{
    	protectVar.acquire();
		quiereLimpiar = false;
        if(clientesEsp > 0)
			colaClientes.release();
		protectVar.release();
	}
}
