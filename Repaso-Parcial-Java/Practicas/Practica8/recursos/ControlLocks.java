import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControlLocks implements Control {  //Esta solucion la veo regulera por que pienso que se pueden 'colar' procesos

    private int numRec;
    private List<Integer> esperando = new LinkedList<Integer>();

    private Lock l = new ReentrantLock();
    private Condition colaEntrada = l.newCondition();
    private Condition esperoRecursos = l.newCondition();

    private boolean esperandoRecursosB = false;

    public ControlLocks(int num){
        this.numRec = num;
    }

    @Override
    public void qRecursos(int id, int num) throws InterruptedException {
        l.lock();
        try{
            System.out.println("Proceso " + id + " pide turno y pide " + num + " recursos.");
            esperando.add(id);
            while(esperando.indexOf(id)!=0)
                colaEntrada.await();

            while(num > numRec){
                esperandoRecursosB = true;
                System.out.println("	El proceso " + id + " espera por " + num + " recursos. Hay: " + numRec);
                esperoRecursos.await();
            }
            esperandoRecursosB = false;
            numRec-=num;
            System.out.println("		El proceso " + id + " ha cogido " + num + " recursos. Ahora quedan: " + numRec);

            esperando.remove(0);
            if(esperandoRecursosB){
                esperoRecursos.signal();
            }else if(esperando.size() > 0){
                colaEntrada.signalAll();
            }
        }finally{
            l.unlock();
        }
    }

    @Override
    public void libRecursos(int id, int num) {
        l.lock();
        try{
            numRec = numRec + num;
			System.out.println(
					"		El proceso " + id + " ha liberado " + num + " recursos. Recursos totales disponibles: "
							+ numRec);
			if (esperandoRecursosB)
				esperoRecursos.signal(); // Hay proceso esperando
			else if (esperando.size() > 0)
				colaEntrada.signalAll(); // Hay alguien esperando
        }finally{
            l.unlock();
        }
    }
    
}
