
import java.util.LinkedList;
import java.util.List;



public class SalaImpresorasML implements SalaImpresoras {   //No es justo
    private int N;
    private int numImpresoras;
    private List<Boolean> impresoras = new LinkedList<Boolean>();
    private boolean esperando = false;
    private List<Integer> colaClientes = new LinkedList<>();
    

    private List<Integer> impresorasId = new LinkedList<>(); // Otra implementacion posible

    public SalaImpresorasML(int N){
        this.N = N;
        this.numImpresoras = N;
        for(int i = 0;i < N;i++)
            impresoras.add(true);
            // impresorasId.add(i);
    }

    public synchronized int quieroImpresora(int id) throws InterruptedException{
        colaClientes.add(id);
        System.out.println("Cliente " + id + " quiere impresora");

        // while(numImpresoras == 0){
        //     esperando = true;
        //     wait();
        // }  

        while(!impresoras.contains(true) || colaClientes.indexOf(id)!=0){
            esperando = true;
            wait();
        }

        // int res = impresorasId.remove(0);

        colaClientes.remove(0);

        int res = impresoras.indexOf(true);
        impresoras.set(res, false);

        numImpresoras--;
        System.out.println("    Cliente "+ id+ " coge impresora "+res+". Quedan "+numImpresoras);

        return res;
    }

    public synchronized void devuelvoImpresora(int id, int n) throws InterruptedException{
        // impresorasId.add(n);

        impresoras.set(n, true);
        numImpresoras++;
        System.out.println("        Cliente "+ id+ " devuelve impresora "+n+". Quedan "+ numImpresoras);
        if(esperando)
            notifyAll();
        esperando = false;
    }

}   