package productorconsumidorbuffer;

import java.util.concurrent.Semaphore;

//Condición Productor:
//  No puedo almacenar mientras el buffer no tenga sitio.  Un semáforo haySitio se puede encargar de sincronizar cuando hay hueco para almacenar.
//Condición Consumidor:
// No puedo extraer hasta que no hay datos. Un semáforo hayDato se puede encargar de sincronizar cuando hay dato.
//Se necesita un índice indInsertar para conocer dónde se intertará el siguiente elemento.
//Se necesita un índice indExtraer para conocer dónde se extraerá el siguiente elemento.
//Además, el buffer va a ser tratado de forma circular, una variable tam almacenará el tamaño del buffer para actualizar correctamente los índices.

public class RecursoCompartidoBuffer {
    private int[] recurso;
    Semaphore haySitio;
    Semaphore hayDato;
    int indInsertar;
    int indExtraer;
    int tam;

    Semaphore sw;    //write
    Semaphore sr;   //read

    public RecursoCompartidoBuffer(int tam) {
        indInsertar = 0;
        indExtraer = 0;
        recurso = new int[tam];
        this.tam = tam;
        sw = new Semaphore(tam,true);
        sr = new Semaphore(0, true);
    }

    public int extraer() {
        int datoLeido;
        try {
            sr.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        datoLeido = recurso[indExtraer];
        System.out.println("Extraído " + datoLeido);
        indExtraer = (++indExtraer)%tam;  
        
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
        
        recurso[indInsertar] = r;
        System.out.println("Almacenado " + r);
        indInsertar = (++indInsertar)%tam;

        sr.release();
    }

}
