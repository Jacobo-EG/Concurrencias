package sensores;


import java.util.concurrent.Semaphore;

public class Mediciones {

    Semaphore[] nuevoPermiso = new Semaphore[3]; //Espera de los sensores despues de haber medido a que el trabajador acabe
    Semaphore completados = new Semaphore(0,true); //Espera del trabajador a que acaben los sensores

    private int numMed = 0;                     //Control numero de sensores que han acabado
    Semaphore med = new Semaphore(1,true);      //Control de escritura sobre el numero

    public Mediciones() {
        for(int i=0;i<nuevoPermiso.length;i++){
            nuevoPermiso[i] = new Semaphore(0,true);
        }
    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        
        med.acquire();
        System.out.println("Sensor " + id + " deja sus mediciones.");
        numMed++;
        if(numMed == nuevoPermiso.length){
            numMed = 0;
            med.release();
            completados.release();
        }else{
            med.release();
        }
        nuevoPermiso[id].acquire();
       
        
        
    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException {
        
        completados.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");
        
    }

    /**
     * El trabajador indica que ha terminado sus tareas
     */
    public void finTareas() {
        System.out.println("El trabajador ha terminado sus tareas");
        for(int i=0;i<3;i++){
            nuevoPermiso[i].release();
        }
    }

}
