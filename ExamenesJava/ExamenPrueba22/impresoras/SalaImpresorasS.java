import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SalaImpresorasS implements SalaImpresoras{
    private int N;
    private List<Integer> impresoras;

    private Semaphore protectVar = new Semaphore(1,true);
    private Semaphore clientes = new Semaphore(0,true);

    private int hayClientes = 0;

    public SalaImpresorasS(int n){
        this.N = n;
        impresoras = new LinkedList<>();
        for(int i = 0;i < N;i++)
            impresoras.add(i);
    }

    public int quieroImpresora(int id) throws InterruptedException{
        int res;

        System.out.println("El Cliente " + id + " espera impresora ");   

        protectVar.acquire();

        if(impresoras.size() == 0){
            hayClientes++;
            protectVar.release();
            clientes.acquire();
            protectVar.acquire();
        }
        
        res = impresoras.remove(0);

        if(hayClientes > 0 && impresoras.size() > 0){
            hayClientes--;
            clientes.release();
        }

        System.out.println("    El Cliente " + id + " coge la impresora " + res);   

        protectVar.release();

        return res;
    }

    public void devuelvoImpresora(int id, int n) throws InterruptedException{
        protectVar.acquire();
        System.out.println("            El Cliente " + id + " devuelve la impresora " + n);
        impresoras.add(n);
        if(hayClientes > 0){
           clientes.release();
            hayClientes--;
        }
        protectVar.release();
    }
}
