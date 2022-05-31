package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {

    private Semaphore trabajador = new Semaphore(0,true);
    private Semaphore[] sensor = new Semaphore[3];
    private Semaphore protectVar = new Semaphore(1,true);

    int numLecturas = 0;

    public Mediciones() {
        for(int i = 0; i < 3; i++)
            sensor[i] = new Semaphore(1,true);
    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        sensor[id%3].acquire();
        System.out.println("Sensor " + id + " deja sus mediciones.");
        protectVar.acquire();
        numLecturas++;
        if(numLecturas == 3){
            numLecturas = 0;
            trabajador.release();
        }
        protectVar.release();
    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        trabajador.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");
    }

    /**
     * El trabajador indica que ha terminado sus tareas
     */
    public void finTareas() {
        System.out.println("El trabajador ha terminado sus tareas");
        for(int i = 0; i < 3; i++)
            sensor[i].release();
    }

}
