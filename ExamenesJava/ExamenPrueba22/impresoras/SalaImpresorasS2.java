import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SalaImpresorasS2 implements SalaImpresoras {

    private int numImpresoras;
    private Semaphore esperaImpresora = new Semaphore(1, true);
    private Semaphore sc = new Semaphore(1, true);
    private List<Integer> impresoras;

    public SalaImpresorasS2(int n) {
        numImpresoras = n;
        impresoras = new LinkedList<>();
        for (int i = 0; i < numImpresoras; i++) {
            impresoras.add(i);
        }
    }

    @Override
    public int quieroImpresora(int id) throws InterruptedException {
        System.out.println("Cliente " + id + " quiere impresora ");
        esperaImpresora.acquire();
        sc.acquire();
        int idi = impresoras.remove(0);

        System.out.println("    Cliente " + id + " coge impresora " + idi + " quedan libres " + impresoras.size());

        if (impresoras.size() > 0) {
            sc.release();
            esperaImpresora.release();
        } else
            sc.release();
        return idi;
    }

    @Override
    public void devuelvoImpresora(int id, int n) throws InterruptedException {

        sc.acquire();
        impresoras.add(n);

        System.out.println(
                "        Cliente " + id + " devuelve la impresora " + n + " quedan libres " + impresoras.size());

        if (impresoras.size() == 1) {
            esperaImpresora.release();
        }

        sc.release();

    }

}
