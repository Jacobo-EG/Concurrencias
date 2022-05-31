import java.util.LinkedList;
import java.util.List;


public class ControlSync implements Control{
    private int numRec;

	private List<Integer> lista = new LinkedList<Integer>();

	public ControlSync(int num) {
		this.numRec = num;
	}

	public synchronized void qRecursos(int id, int num) throws InterruptedException {

		System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);

		lista.add(id);

		while(lista.indexOf(id) != 0)
			wait();

		while(num > numRec)
			wait();
		
		numRec-=num;
		
		System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);

		lista.remove(0);
		if(lista.size()>0)
			notifyAll();
	}

	public synchronized void libRecursos(int id, int num) {

		numRec+=num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		notifyAll();
	}
}
