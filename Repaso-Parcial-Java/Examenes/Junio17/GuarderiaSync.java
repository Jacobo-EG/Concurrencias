package Junio17;


public class GuarderiaSync implements  Guarderia{

	private int numAd = 0;
	private int numBb = 0;
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void entraBebe(int id) throws InterruptedException{
		System.out.println("Quiere entrar un bebe. Status : numBb = " + numBb +" ,numAd = " + numAd);
		while (numBb + 1 > 3*numAd)
			wait();
		numBb++;
		System.out.println("Ha entrado un bebe. Status : numBb = " + numBb +" ,numAd = " + numAd);
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public synchronized void saleBebe(int id) throws InterruptedException{
		numBb--;
		System.out.println("Ha salido un bebe. Status : numBb = " + numBb +" ,numAd = " + numAd);
		notifyAll();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public synchronized void entraAdulto(int id) throws InterruptedException{
		numAd++;
		System.out.println("Ha entrado un adulto. Status : numBb = " + numBb +" ,numAd = " + numAd);
		notifyAll();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void saleAdulto(int id) throws InterruptedException{
		System.out.println("Quiere salir un adulto. Status : numBb = " + numBb +" ,numAd = " + numAd);
		while (numBb > 3*(numAd - 1))
			wait();
		numAd--;
		System.out.println("Ha salido un adulto. Status : numBb = " + numBb +" ,numAd = " + numAd);
	}

}
