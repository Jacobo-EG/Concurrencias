package Junio17;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GuarderiaLock implements Guarderia{

    private int numAd = 0;
    private int numBb = 0;

    private Lock l = new ReentrantLock();
    private Condition colaBb = l.newCondition();
    private Condition colaAd = l.newCondition();

    /**
     * Un bebe que quiere entrar en la guarderia llama a este metodo.
     * Debe esperar hasta que sea seguro entrar, es decir, hasta que
     * cuado entre haya, al menos, 1 adulto por cada 3 bebes
     *
     */
    public void entraBebe(int id) throws InterruptedException{
        l.lock();
        try {
            System.out.println("Quiere entrar un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
            while (numBb + 1 > 3 * numAd)
                colaBb.await();
            numBb++;
            System.out.println("Ha entrado un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
        }finally {
            l.unlock();
        }
    }
    /**
     * Un bebe que quiere irse de la guarderia llama a este metodo *
     */
    public void saleBebe(int id) throws InterruptedException{
        l.lock();
        try {
            numBb--;
            System.out.println("Ha salido un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
            colaBb.signal();
        }finally {
            l.unlock();
        }
    }
    /**
     * Un adulto que quiere entrar en la guarderia llama a este metodo *
     */
    public void entraAdulto(int id) throws InterruptedException{
        l.lock();
        try {
            numAd++;
            System.out.println("Ha entrado un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
            colaAd.signal();
        }finally {
            l.unlock();
        }
    }

    /**
     * Un adulto que quiere irse  de la guarderia llama a este metodo.
     * Debe esperar hasta que sea seguro salir, es decir, hasta que
     * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
     *
     */
    public void saleAdulto(int id) throws InterruptedException{
        l.lock();
        try {
            System.out.println("Quiere salir un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
            while (numBb > 3 * (numAd - 1))
                colaAd.await();
            numAd--;
            System.out.println("Ha salido un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
        }finally {
            l.unlock();
        }
    }
}
