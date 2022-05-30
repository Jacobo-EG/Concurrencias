
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class ControlSemaphore implements Control{
	private int NUM;	// numero total de recursos
	private int numRec;

	private Semaphore sNumRec = new Semaphore(1,true);
	private Semaphore[] listS;

	private List<Pair<Integer,Integer>> listaN = new LinkedList<>();
	// private List<Integer> lista = new LinkedList<Integer>();

	public ControlSemaphore(int num) {
		this.NUM = num;
		this.numRec = num;
		listS = new Semaphore[NUM];
		for(int i = 0;i<NUM;i++){
			listS[i] = new Semaphore(0,true);
		}
			
	}

	public void qRecursos(int id, int num) throws InterruptedException {
	
			System.out.println("Proceso " + id + " pide " + num + " recursos. Quedan: " + numRec);
			
			sNumRec.acquire();
			// lista.add(id);
			Pair<Integer,Integer> par = new Pair<Integer,Integer>(id,num);
			listaN.add(par);

			if(num > numRec || listaN.indexOf(par) != 0){ // lista.indexOf(id)
				sNumRec.release();
				listS[id%NUM].acquire();
				sNumRec.acquire();
			}

			numRec -= num;
			// lista.remove(0);
			listaN.remove(0);
			System.out.println("El proceso " + id + " ha cogido " + num + " recursos. Quedan: " + numRec);
			sNumRec.release();
		
		

	}

	public void libRecursos(int id, int num) throws InterruptedException {
		sNumRec.acquire();
		numRec += num;
		System.out.println("El proceso " + id + " ha liberado " + num + " recursos. Recursos totales: " + numRec);
		if(!listaN.isEmpty() && numRec >= listaN.get(0).getValue2()){
			listS[listaN.get(0).getValue1()].release();
			// listS[lista.get(0)%NUM].release();
			
		}
		sNumRec.release();
	}
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
