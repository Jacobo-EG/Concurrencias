package productorconsumidor;

//import java.util.concurrent.Semaphore;

//Condición Productor:
//  No puedo almacenar hasta que no se ha leido.  
//Condición Consumidor:
// No puedo extraer hasta que no se ha almacenado uno nuevo. 
public class RecursoCompartido {
    private int recurso;
    private boolean almacenado = false;

    public RecursoCompartido() {
    }

    public synchronized int extraer() throws InterruptedException {
        while(!almacenado)
            wait();
        almacenado = false;
        System.out.println("Extraído " + recurso);
        notify();                                   //Si tenemos mas de un productor/consumidor entonces debemos poner notifyAll,
        return recurso;                             //pues si no puede haber fallos en la ejecucion pues notify no los saca en orden siempre
    }

    public synchronized void almacenar(int r) throws InterruptedException {
        while(almacenado)
            wait();
        recurso = r;
        almacenado = true;
        System.out.println("Almacenado " + r);
        notify();                               //Si tenemos mas de un productor/consumidor entonces debemos poner notifyAll,
    }                                           //pues si no puede haber fallos en la ejecucion pues notify no los saca en orden siempre

}
