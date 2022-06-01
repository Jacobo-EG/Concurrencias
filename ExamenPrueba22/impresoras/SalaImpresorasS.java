import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SalaImpresorasS implements SalaImpresoras {
    private int N;
    private int esperando = 0;

    private Semaphore protectVar = new Semaphore(1,true);
    private Semaphore colaCliente = new Semaphore(0,true);

    //Lo comentado es solucion de josan para evitar tener que soltar y coger el mutex seguido
    //private Semahore espera = new Semaphore(1,true); //equivalente a 'colaCliente' y no usa 'esperando'

    private List<Integer> impresorasId = new LinkedList<>();

    public SalaImpresorasS(int N){
        this.N = N;
        for(int i = 0;i < N;i++)
            impresorasId.add(i);
    }

    public  int quieroImpresora(int id) throws InterruptedException{

        //espera.acquire();                 //Sustituye a (1)

        protectVar.acquire();
        System.out.println("Cliente " + id + " quiere impresora");

        //if(impresorasId.size() > 1)       //Sustituye a (1)
        //  espera.release();

        if(impresorasId.size() == 0){       //
            esperando ++;                   //
            protectVar.release();           //  (1)
            colaCliente.acquire();          //
            protectVar.acquire();           //
        }                                   //

        int res = impresorasId.remove(0);

        System.out.println("    Cliente "+ id+ " coge impresora "+res+". Quedan " + impresorasId.size());

        protectVar.release();
        return res;
    }

    public  void devuelvoImpresora(int id, int n) throws InterruptedException{
        protectVar.acquire();

        //if(impresorasId.size() == 0)              // Sustituye a (2)
        //  espera.release();

        impresorasId.add(n);
        System.out.println("        Cliente "+ id+ " devuelve impresora " + n + ". Quedan "+ impresorasId.size());
        
        if(esperando > 0){                          //
            colaCliente.release();                  // (2)
            esperando--;                            //
        }

        protectVar.release();
    }
    
}
