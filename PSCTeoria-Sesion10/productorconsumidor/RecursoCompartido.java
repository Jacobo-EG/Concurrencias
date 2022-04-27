package productorconsumidor;

import java.util.concurrent.Semaphore;

//Condición Productor:
//  No puedo almacenar hasta que no se ha leido.  Un semáforo haySitio se puede encargar de sincronizar cuando hay hueco para almacenar.
//Condición Consumidor:
// No puedo extraer hasta que no se ha almacenado uno nuevo. Un semáforo hayDato se puede encargar de sincronizar cuando hay dato.

public class RecursoCompartido {
    private int recurso;
    Semaphore sw = new Semaphore(1,true);   //write
    Semaphore sr = new Semaphore(0, true);  //read

    public RecursoCompartido() {
        
    }

    public int extraer() {
        int datoLeido;

        try {
            sr.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        datoLeido = recurso;
        System.out.println("Extraído " + datoLeido);

        sw.release();

        return datoLeido;
    }

    public void almacenar(int r) {
        try {
            sw.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        recurso = r;
        System.out.println("Almacenado " + r);

        sr.release();
    }

}
