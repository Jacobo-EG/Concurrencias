package pajaritos;

import java.util.concurrent.*;



public class Nido {
	private int B = 10; // Número máximo de bichos
	private int bichitos; // puede tener de 0 a B bichitos
	Semaphore esperaBichitos = new Semaphore(0,true);
	Semaphore controlBichitos = new Semaphore(1,true);
	Semaphore deposita = new Semaphore(1,true);


	public void come(int id) throws InterruptedException {
		/*esperaBichitos.acquire();
		controlBichitos.acquire();
		if(bichitos == B){
			deposita.release();
		}
		bichitos--;
		System.out.println("El bebé " + id + " ha comido un bichito. Quedan " + bichitos);
		controlBichitos.release();*/
		
		esperaBichitos.acquire();
		controlBichitos.acquire();

		if(bichitos == B){
			deposita.release();
		}

		bichitos--;
		
		System.out.println("El bebé " + id + "se ha comido un bichito. Quedan " + bichitos);
		if(bichitos > 0){
			controlBichitos.release();
			esperaBichitos.release();
		}else{
			controlBichitos.release();
		}
		
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		/*controlBichitos.acquire();

		if(bichitos == B){
			controlBichitos.release();
			deposita.acquire();
			controlBichitos.acquire();
		}

		bichitos++;
		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		controlBichitos.release();
		esperaBichitos.release();*/
		deposita.acquire();
		controlBichitos.acquire();

		if(bichitos == 0){
			esperaBichitos.release();
		}

		bichitos++;

		System.out.println("El papá " + id + " ha añadido un bichito. Hay " + bichitos);
		if(bichitos < B){
			controlBichitos.release();
			deposita.release();
		}else{
			controlBichitos.release();
		}
		
	}
}

// CS-Bebe-i: No puede comer del nido si está vacío
// CS-Papa/Mama: No puede poner un bichito en el nido si está lleno
