package parejas;

public class Sala {

	private boolean hayMujer = false;
	private boolean hayHombre = false;
	private boolean puerta = true;

	/**
	 * un hombre llega a la sala para formar una pareja si ya hay otra mujer en la
	 * sala o si a�n no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaHombre(int id) throws InterruptedException {

		while(!puerta)
			wait();

		hayHombre = true;

		System.out.println("El hombre " + id + " espera en la sala.");
		
		if(hayMujer){
			notifyAll();
			puerta = false;
		}else{
			while(!hayMujer)
				wait();
		}

		System.out.println("El hombre " + id + " ha tenido una cita.");

		System.out.println("El hombre " + id + " sale de la sala.");

		hayHombre = false;
		puerta = true;
		notifyAll();
	}

	/**
	 * una mujer llega a la sala para formar una pareja debe esperar si ya hay otra
	 * mujer en la sala o si aún no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaMujer(int id) throws InterruptedException {

		while(!puerta)
			wait();
		hayMujer = true;

		System.out.println("La mujer " + id + " espera en la sala.");

		if(hayHombre){
			notifyAll();
			puerta = false;
		}else{
			while(!hayHombre)
				wait();
		}

		System.out.println("La mujer  " + id + " ha tenido una cita.");

		System.out.println("La mujer " + id + " sale de la sala.");

		hayMujer=false;
		puerta = true;
		notifyAll();

	}
}
