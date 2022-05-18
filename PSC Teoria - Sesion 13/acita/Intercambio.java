package acita;

//import java.util.concurrent.Semaphore;

/*
Primero resolver con semáforos.
Segundo resolver con wait and notify o notifyAll.
*/

public class Intercambio {
    private int v1, v2;

    // Semaphore s1 = new Semaphore(0,true);      //Solucion con semaforos
    // Semaphore s2 = new Semaphore(0,true);

    private boolean hayDato1 = false;           //Solucion con monitores
    private boolean hayDato2 = false;

    // Acepta el dato generado por w1 y devuelve el de w2, OJO hasta que w2 no ha
    // dejado
    // dato, no se puede devolver
    public synchronized int Intercambio1(int dato) throws InterruptedException {
        v1 = dato;

        hayDato1 = true;                //Solucion con monitores
        while(!hayDato2)
            wait();
        notify();
        hayDato2 = false;
        
        // s2.release();   //Solucion con semaforos
        // s1.acquire();
        return v2;
    }

    // Acepta el dato generado por w2 y devuelve el de w1, OJO hasta que w1 no ha
    // dejado
    // dato, no se puede devolver
    public synchronized int Intercambio2(int dato) throws InterruptedException {
        v2 = dato;

        hayDato2 = true;                //Solucion con monitores
        while(!hayDato1)
            wait();
        notify();
        hayDato1 = false;

        // s1.release();   //Solucion con semaforos
        // s2.acquire();
        return v1;
    }
}