package parejas;

public class Sala {
	private Boolean hayHombre=false;
	private Boolean hayMujer = false;
	private Boolean cita=false;
	private Boolean puerta=true;


	/**
	 * un hombre llega a la sala para formar una pareja si ya hay otra mujer en la
	 * sala o si aún no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaHombre(int id) throws InterruptedException {
		while(hayHombre || !puerta)wait();
		// podemos pasar a la sala de espera
		hayHombre=true;
		System.out.println("El hombre "+id+" espera en la sala.");
		// ya hay una mujer esperando
		if(hayMujer) {
			puerta=false;
			cita=true;
			System.out.println("El hombre "+id+" ha tenido una cita.");
			notifyAll();// desbloqueamos a la mujer.
		}else {// no hay mujer esperando.
			while(!cita)wait();
			System.out.println("El hombre "+id+" ha tenido una cita.");
			cita=false;
		}
		
		hayHombre=false;
		System.out.println("El hombre "+id+" sale de la sala.");
		if(!hayHombre && !hayMujer) {
			puerta=true;
			notifyAll();
		}
	}

	/**
	 * una mujer llega a la sala para formar una pareja debe esperar si ya hay otra
	 * mujer en la sala o si aÃºn no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaMujer(int id) throws InterruptedException  {
		while(hayMujer || !puerta)wait();
		hayMujer=true;
		System.out.println("La mujer "+id+" espera en la sala.");
		if(hayHombre) {
			puerta=false;
			cita=true;
			System.out.println("La mujer  "+id+" ha tenido una cita.");
			notifyAll();//desbloqueamos al hombre.
		}else {// no hay mujer esperando.
			while(!cita)wait();
			System.out.println("La mujer  "+id+" ha tenido una cita.");
			cita=false;
		}
		hayMujer=false;
		System.out.println("La mujer "+id+" sale de la sala.");
		if(!hayMujer && !hayHombre) {
			puerta=true;
			notifyAll();
		}
	}
}
