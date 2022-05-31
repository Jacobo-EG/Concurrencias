package guarderia;

import java.util.concurrent.Semaphore;

public class GuarderiaSemp implements Guarderia{

    private int numAd = 0;
    private int numAdEsperando = 0;
    private int numBb = 0;
    private int numBbEsperando = 0;


    private Semaphore protectVar = new Semaphore(1,true);
    private Semaphore colaBb = new Semaphore(0,true);
    private Semaphore colaAd = new Semaphore(0,true);

    /**
     * Un bebe que quiere entrar en la guarderia llama a este metodo.
     * Debe esperar hasta que sea seguro entrar, es decir, hasta que
     * cuado entre haya, al menos, 1 adulto por cada 3 bebes
     *
     */
    public void entraBebe(int id) throws InterruptedException{
        protectVar.acquire();
            System.out.println("Quiere entrar un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
            while (numBb + 1 > 3 * numAd) {
                numBbEsperando++;
                protectVar.release();
                colaBb.release();
                protectVar.acquire();
            }
            numBb++;
            System.out.println("Ha entrado un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
        protectVar.release();
    }
    /**
     * Un bebe que quiere irse de la guarderia llama a este metodo *
     */
    public void saleBebe(int id) throws InterruptedException{
        protectVar.acquire();
            numBb--;
            System.out.println("Ha salido un bebe. Status : numBb = " + numBb + " ,numAd = " + numAd);
            if(numBbEsperando > 0) {
                colaBb.release();
                numBbEsperando--;
            }
            if(numAdEsperando > 0){
                colaAd.release();
                numAdEsperando--;
            }
        protectVar.release();
    }
    /**
     * Un adulto que quiere entrar en la guarderia llama a este metodo *
     */
    public void entraAdulto(int id) throws InterruptedException{
        protectVar.acquire();
            numAd++;
            System.out.println("Ha entrado un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
            if(numBbEsperando > 0) {
                colaBb.release();
                numBbEsperando--;
            }
            if(numAdEsperando > 0){
                colaAd.release();
                numAdEsperando--;
            }
        protectVar.release();
    }

    /**
     * Un adulto que quiere irse  de la guarderia llama a este metodo.
     * Debe esperar hasta que sea seguro salir, es decir, hasta que
     * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
     *
     */
    public void saleAdulto(int id) throws InterruptedException{
        protectVar.acquire();
            System.out.println("Quiere salir un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
            while (numBb > 3 * (numAd - 1)) {
                numAdEsperando++;
                protectVar.release();
                colaAd.release();
                protectVar.acquire();
            }
            numAd--;
            System.out.println("Ha salido un adulto. Status : numBb = " + numBb + " ,numAd = " + numAd);
        protectVar.release();
    }
}
