package rusa;

import java.util.concurrent.*;


public class Coche extends Thread{
    private int C;
    private int numPasajeros=0;
    private int numCola=0;
    private Semaphore mutex=new Semaphore(1);
    private Semaphore mutexCola=new Semaphore(1);
    private Semaphore esperaLleno=new Semaphore(0);
    private Semaphore esperaFinViaje=new Semaphore(0);
    private Semaphore esperaEnCola=new Semaphore(0);


    public Coche(int cap){
        C = cap;
    }

    public void nuevoViaje(int id) throws InterruptedException{

        mutex.acquire();
        if(numPasajeros<C) {// el coche tiene sitio
            numPasajeros++;
            if(numPasajeros==C) {
                esperaLleno.release();
            }
            mutex.release();
            System.out.println("El pasajero "+id+" está esperando en el coche.");
            esperaFinViaje.acquire();
            mutex.acquire();
            numPasajeros--;
            if(numPasajeros!=0) {
                esperaFinViaje.release();
            }else {// la última hebra del coche despierta a la primera de la cola
                esperaEnCola.release();
            }
        }else {// el coche está lleno
            mutexCola.acquire();
            numCola++;
            mutexCola.release();
            mutex.release();
            esperaEnCola.acquire();
            mutexCola.acquire();
            numCola--;
            if(numCola>0)esperaEnCola.release();
            mutexCola.release();
            mutex.acquire();
        }
        mutex.release();
    }

    public void esperaLleno() throws InterruptedException{
        esperaLleno.acquire();
        System.out.println("Viaje en la montaña rusa.");
    }

    public void finViaje(){
        // despertamos a las hebras de dentro del coche
        esperaFinViaje.release();
    }

    public void run(){
        while (true){

            try {
                this.esperaLleno();
                Thread.sleep(200);
                this.finViaje();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
} 