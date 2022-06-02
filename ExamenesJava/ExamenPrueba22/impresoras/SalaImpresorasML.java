import java.util.LinkedList;
import java.util.List;

public class SalaImpresorasML implements SalaImpresoras{

    private int N;
    private List<Integer> impresoras = new LinkedList<>();
    private List<Integer> clientes = new LinkedList<>();

    public SalaImpresorasML(int n){
        this.N = n;
        for(int i = 0;i < N;i++)
            impresoras.add(i);
    }

    public synchronized int quieroImpresora(int id) throws InterruptedException{
        int res;

        System.out.println("El Cliente " + id + " espera impresora ");   

        clientes.add(id);

        while(impresoras.size() == 0 || clientes.indexOf(id) != 0)
            wait();
        
        clientes.remove(0);
        res = impresoras.remove(0);
        System.out.println("    El Cliente " + id + " coge la impresora " + res);   

        return res;
    }

    public synchronized void devuelvoImpresora(int id, int n) throws InterruptedException{
        System.out.println("            El Cliente " + id + " devuelve la impresora " + n);
        impresoras.add(n);
        if(clientes.size() > 0)
            notifyAll();
    }
}
