package lectoresescritores;

import java.util.concurrent.*;

public class GestorBD {
	private Semaphore escribiendo = new Semaphore(1, true); // Gestiona si se puede escribir
	private int nLectores = 0;// cuantos lectores hay en la BD
	private Semaphore scNLectores = new Semaphore(1, true); // protege a nLectores
	private Semaphore scNEscritores = new Semaphore(1, true);// protege a scEscritores
	private int nEscritores = 0;// cuantos escritores hay en la BD
	private Semaphore leyendo = new Semaphore(1, true); // permiso para leer

	public void entraLector(int id) throws InterruptedException {
		leyendo.acquire();

		scNLectores.acquire();

		if(nLectores < 1){
			escribiendo.acquire();
		}

		nLectores++;

		System.out.println("Entra lector " + id + " Hay " + nLectores);
		scNLectores.release();

		leyendo.release();
	}

	public void entraEscritor(int id) throws InterruptedException {
		scNEscritores.acquire();

		if(nEscritores < 1)
			leyendo.acquire();

		escribiendo.acquire();

		nEscritores++;
		scNEscritores.release();
		System.out.println("                    Entra escritor " + id + " Hay " + nLectores);
	}

	public void saleLector(int id) throws InterruptedException {
		scNLectores.acquire();
		nLectores--;

		if(nLectores == 0){
			escribiendo.release();
		}

		scNLectores.release();
	}

	public void saleEscritor(int id) throws InterruptedException {
		scNEscritores.acquire();
		nEscritores--;

		if(nEscritores < 1)
			leyendo.release();

		System.out.println("             Sale escritor " + id + " Hay " + nLectores);
		scNEscritores.release();
		escribiendo.release();
	}

}
// CS-Escritores: exclusion mutua
// CS-Lectores: puede haber varios pero nunca con un escritor